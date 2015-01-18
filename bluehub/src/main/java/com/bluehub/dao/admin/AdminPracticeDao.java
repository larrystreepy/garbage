/**
 * 
 */
package com.bluehub.dao.admin;

import java.util.List;
import java.util.Map;

import com.bluehub.vo.admin.AdminPracticeVO;

public interface AdminPracticeDao {

	public void saveAdminPractice(AdminPracticeVO adminPracticeVO);

	public List<AdminPracticeVO> getAdminPractice(Map map);

	/* public void deleteAdminPractice(AdminPracticeVO adminPracticeVO); */
	public void deleteAdminPractice(Integer practiceid);

	public List<AdminPracticeVO> getPracticeList();

	public List<AdminPracticeVO> editAdminPractice(AdminPracticeVO adminPracticeVO, int id);

	public AdminPracticeVO geAdminPracticeVOById(int id);

	public void updateAdminPractice(AdminPracticeVO adminPracticeVO);

	public AdminPracticeVO findPracticeById(Integer practiceId);

	public Long getAdminPracticeCount(Map map);

}
