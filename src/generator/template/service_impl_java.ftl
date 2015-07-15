package com.sohu.sc.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.sohu.sc.dao.ActivityUserDao;
import com.sohu.sc.dto.ActivityDto;
import com.sohu.sc.dto.ActivityUserDto;
import com.sohu.sc.model.Activity;
import com.sohu.sc.model.ActivityUser;
import com.sohu.sc.model.SystemParam;
import com.sohu.sc.model.Voucher;
import com.sohu.sc.service.ActivityService;
import com.sohu.sc.service.ActivityUserService;
import com.sohu.sc.service.SystemParamService;
import com.sohu.sc.service.VoucherService;


@Service(value = "scActivityUserService")
public class ActivityUserServiceImpl extends BaseServiceImpl implements ActivityUserService {

	@Resource(name = "scActivityUserDao")
	private ActivityUserDao activityUserDao;

	@Resource(name = "scActivityService")
	private ActivityService activityService;

	@Resource(name = "scSystemParamService")
	private SystemParamService systemParamService;

	@Resource(name = "scVoucherService")
	private VoucherService voucherService;

	@Override
	public Integer addActivityUser(ActivityUser activityUser) {
		return activityUserDao.addActivityUser(activityUser);
	}

	@Override
	public ActivityUser findActivityUser(Integer id) {
		return activityUserDao.findActivityUser(id);
	}

	@Override
	public Integer deleteActivityUser(Integer id) {
		return activityUserDao.deleteActivityUser(id);
	}

	@Override
	public Integer updateActivityUser(ActivityUser activityUser) {
		return activityUserDao.updateActivityUser(activityUser);
	}

	@Override
	public Integer updateActivityUserSelective(ActivityUser activityUser) {
		return activityUserDao.updateActivityUserSelective(activityUser);
	}

	@Override
	public List<ActivityUser> findActivityUserList(ActivityUserDto dto) {
		return activityUserDao.findActivityUserList(dto);
	}

	@Override
	public List<ActivityUser> findActivityUserWithPg(ActivityUserDto dto, Long startNo, Integer pageSize) {
		this.getUserIdListByUserNameOrId(dto);
		return warpList(activityUserDao.findActivityUserWithPg(dto, startNo, pageSize));
	}

	@Override
	public Integer countFindActivityUserWithPg(ActivityUserDto dto) {
		return activityUserDao.countFindActivityUserWithPg(dto);
	}

	@Override
	public ActivityUser getGiftByType(Integer activityId, Integer userId) {
		if (activityId == null || userId == null) {
			return null;
		}

		Activity activity = activityService.findActivity(activityId);
		if (activity == null || activity.isExpire()) {
			return null;
		}

		// 判断用户是否已经领取了奖品
		ActivityUserDto dto = new ActivityUserDto();
		dto.setActivityId(activityId);
		dto.setUserId(userId);
		List<ActivityUser> activityUserList = this.findActivityUserList(dto);
		if (CollectionUtils.isNotEmpty(activityUserList)) { // 用户已经参加了活动，则直接返回
			return activityUserList.get(0);
		}

		String giftName = null;
		String giftCode = null;
		Integer giftNum = 1;
		if (activity.getGiftType() == Activity.GIFT_TYPE_VOUCHER) { // 发放抵用卷
			Integer denomination = 10;
			if (activity.getGiftNum() != null && activity.getGiftNum() > 0) {
				denomination = activity.getGiftNum();
			}
			giftName = ActivityUser.GIFT_VOUCHER;
			giftCode = Voucher.generateVoucherCode();

			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			calendar.add(Calendar.MONTH, 3);
			Date invalidTime = calendar.getTime();

			Voucher voucher = new Voucher();
			voucher.setActivityId(activityId);
			voucher.setCreateTime(now);
			voucher.setDenomination(denomination);
			voucher.setInvalidTime(invalidTime);
			voucher.setReceiveTime(now);
			voucher.setReceiveUserId(userId);
			voucher.setVoucherCode(giftCode);
			voucher.setVoucherType(Voucher.VOUCHER_TYPE_SAME_ACCOUNT);
			voucherService.addVoucher(voucher);

			giftNum = denomination;
		} else if (activity.getGiftType() == Activity.GIFT_TYPE_BONG) {// 发放bong2
			giftName = ActivityUser.GIFT_BONG2;
			giftCode = "";
		} else if (activity.getGiftType() == Activity.GIFT_TYPE_MULT) {// 发放多种类型的奖品
			return this.getGift(activityId, userId);
		} else {
			giftName = ActivityUser.GIFT_OTHER;
			giftCode = "";
		}

		ActivityUser activityUser = new ActivityUser();
		activityUser.setActivityId(activityId);
		activityUser.setUserId(userId);
		activityUser.setGiftName(giftName);
		activityUser.setGiftCode(giftCode);
		activityUser.setGiftNum(giftNum);

		this.addActivityUser(activityUser);
		return activityUser;
	}

	@Override
	public ActivityUser getGift(Integer activityId, Integer userId) {
		// 判断用户是否已经领取了奖品
		ActivityUserDto dto = new ActivityUserDto();
		dto.setActivityId(activityId);
		dto.setUserId(userId);
		List<ActivityUser> activityUserList = this.findActivityUserList(dto);
		if (CollectionUtils.isNotEmpty(activityUserList)) { // 用户已经参加了活动，则直接返回
			return activityUserList.get(0);
		}

		String giftName = null;
		String giftCode = null;
		Integer giftNum = 1;

		if (getRealGift(ActivityUser.GIFT_BONG2, userId)) { // bong2领取成功
			giftName = ActivityUser.GIFT_BONG2;
			giftCode = "";
		} else if (getRealGift(ActivityUser.GIFT_HULI, userId)) { // 狐狸领取成功
			giftName = ActivityUser.GIFT_HULI;
			giftCode = "";
		} else {// 抵用卷领取
			String voucherMoneyStr = getVirtualGift(ActivityUser.GIFT_VOUCHER);// 抵用卷面值
			if (StringUtils.isNotBlank(voucherMoneyStr)) {
				Integer denomination = 10;
				try {
					denomination = Integer.parseInt(voucherMoneyStr);
				} catch (Exception e) {
					denomination = 10;
				}

				giftName = ActivityUser.GIFT_VOUCHER;
				giftCode = Voucher.generateVoucherCode();

				Calendar calendar = Calendar.getInstance();
				Date now = calendar.getTime();
				calendar.add(Calendar.MONTH, 3);
				Date invalidTime = calendar.getTime();

				Voucher voucher = new Voucher();
				voucher.setActivityId(activityId);
				voucher.setCreateTime(now);
				voucher.setDenomination(denomination);
				voucher.setInvalidTime(invalidTime);
				voucher.setReceiveTime(now);
				voucher.setReceiveUserId(userId);
				voucher.setVoucherCode(giftCode);
				voucher.setVoucherType(Voucher.VOUCHER_TYPE_SAME_ACCOUNT);

				giftNum = denomination;

				voucherService.addVoucher(voucher);
			} else {
				return null;
			}
		}

		ActivityUser activityUser = new ActivityUser();
		activityUser.setActivityId(activityId);
		activityUser.setUserId(userId);
		activityUser.setGiftName(giftName);
		activityUser.setGiftCode(giftCode);
		activityUser.setGiftNum(giftNum);

		this.addActivityUser(activityUser);
		return activityUser;
	}

	/**
	 * 获取虚拟的礼品卷
	 * 
	 * @param gitName　礼品的名称
	 * @return null 领取失败，其他为虚拟面值
	 */
	private String getVirtualGift(String gitName) {
		SystemParam systemParam = systemParamService.findSystemParam(gitName);
		if (systemParam != null && systemParam.getParamValue() > 0) { // 判断是否有奖品
			systemParam.setOldParamValue(systemParam.getParamValue());
			systemParam.setParamValue(systemParam.getParamValue() - 1);
			boolean flag = systemParamService.updateWithOldValue(systemParam);
			if (flag) {// 领取成功
				String paramData = systemParam.getParamData();
				if (StringUtils.isBlank(paramData)) {// 默认10元
					return "10";
				} else {
					String[] strings = paramData.split(",");
					int nextInt = RandomUtils.nextInt(strings.length);
					return strings[nextInt];
				}
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取实物的礼品<br>
	 * 判断用户id是否在列表中，不在则无法领取
	 * 
	 * @param gitName　礼品的名称
	 * @param userId　userId
	 * @return false 领取失败，true成功
	 */
	private boolean getRealGift(String gitName, Integer userId) {
		SystemParam systemParam = systemParamService.findSystemParam(gitName);
		if (systemParam == null || systemParam.getParamValue() <= 0) { // 判断是否有奖品
			return false;
		}

		String paramData = systemParam.getParamData(); // 判断userId是否在列表中
		if (StringUtils.isBlank(paramData)) {// 无用户id列表
			return false;
		} else {
			String[] strings = paramData.split(",");
			for (String userIds : strings) {
				if (userIds.equals(String.valueOf(userId))) {// 在列表中
					systemParam.setOldParamValue(systemParam.getParamValue());
					systemParam.setParamValue(systemParam.getParamValue() - 1);
					boolean update = systemParamService.updateWithOldValue(systemParam);
					if (update) { // 领取成功
						return true;
					} else {
						return getRealGift(gitName, userId);
					}
				}
			}
		}
		return false;
	}

	/**
	 * 设置活动信息
	 * 
	 * @param list
	 * @return
	 */
	private List<ActivityUser> warpList(List<ActivityUser> list) {
		ActivityDto adto = new ActivityDto();
		Map<Integer, Activity> map = activityService.findActivityMapList(adto);
		for (ActivityUser activityUser : list) {
			activityUser.setActivity(map.get(activityUser.getActivityId()));
		}
		return list;
	}
}