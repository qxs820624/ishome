package org.isotope.boxy.fad.business.ts;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.isotope.boxy.common.AGameBussinessService;
import org.isotope.boxy.fad.bean.FADPlayerRoleBean;
import org.isotope.boxy.fad.business.role.FADPlayerRoleServiceImpl;
import org.isotope.jfp.framework.beans.common.RESTResultBean;
import org.springframework.stereotype.Service;

/**
 * 温泉泡点
 * 
 * @author fucy
 * @version 0.0.1
 * @since 3.1.2 2017/01/20
 *
 */
@Service("10101234")
public class FADThermalSpringServiceImpl extends AGameBussinessService {
	@Resource
	FADPlayerRoleServiceImpl PlayerRoleServiceImpl_;
	ArrayList<String> thermalRoles = new ArrayList<String>();

	int timeSplit = 60 * 1000;

	/**
	 * 根据幸运值计算增加点数
	 * 
	 * @return
	 */
	public int loadVigour() {
		// TODO
		return 1;
	}

	/**
	 * 进入温泉
	 * 
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	public boolean joinTherma(String roleID) throws Exception {
		// 获得当前角色
		FADPlayerRoleBean role = PlayerRoleServiceImpl_.loadRole(roleID);
		if (role != null)
			thermalRoles.add(role.getRoleID());
		else
			return false;
		return true;
	}

	/**
	 * 离开温泉
	 * 
	 * @param roleID
	 * @return
	 * @throws Exception
	 */
	public boolean leaveTherma(String roleID) throws Exception {
		// 获得当前角色
		FADPlayerRoleBean role = PlayerRoleServiceImpl_.loadRole(roleID);
		if (role != null)
			thermalRoles.remove(role.getRoleID());
		else
			return false;
		return true;
	}

	/**
	 * 增加体力(玩家泡点)
	 */
	public boolean addVigour(String roleID) throws Exception {
		// 获得当前角色
		FADPlayerRoleBean role = PlayerRoleServiceImpl_.loadRole(roleID);
		// 获得系统时间
		long nowTime = System.currentTimeMillis();
		// 获得玩家最后获取点数时间
		long lastTime = role.getVigourTime();
		// 计算时间间隔
		long split = lastTime - nowTime;
		// 判断当前时间间隔
		if (split < timeSplit) {
			result.setCode(ONE);
			result.setMessage("时间太短没有活力");
			return false;
		}
		// 获得当前活力值
		int vigour = role.getVigour();
		// 计算增加测试
		while (split > timeSplit) {
			// 计算增加次数
			split = split - timeSplit;
			// 修正最后运行时间
			lastTime = lastTime + timeSplit;
			// 计算增加点数
			int num = loadVigour();
			// 增加玩家数值,计算增加活力值
			vigour = vigour + num;
		}
		// 修正保存时间
		role.setVigourTime(lastTime);
		// 保存数据计算结果
		PlayerRoleServiceImpl_.setVigour(role, vigour);
		// 保存最终结果,保存玩家数据
		PlayerRoleServiceImpl_.updateRole(role);
		result.setMessage("活力恢复");
		return true;
	}

	@Override
	public boolean doGameAction() throws Exception {
		result = new RESTResultBean();
		if ("10109000".equals(tokenBean.getBizId())) {
			return addVigour(tokenBean.getUserId());
		} else if ("10101000".equals(tokenBean.getBizId())) {
			return joinTherma(tokenBean.getUserId());
		} else if ("10102000".equals(tokenBean.getBizId())) {
			return leaveTherma(tokenBean.getUserId());
		}

		return false;
	}

}