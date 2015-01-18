package com.bluehub.dao.practiceadmin;

import java.util.List;

import com.bluehub.bean.admin.SearchPhysicianForm;
import com.bluehub.vo.user.UserMappingVO;

public interface PracticeAdminDao {

	public List<UserMappingVO> getPracticeAdminMap(Integer userId);

	public List<SearchPhysicianForm> getPhysicianMappingRecord(Integer userId,
			Integer organizationId, Integer practiceId);

}
