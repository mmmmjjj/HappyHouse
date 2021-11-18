package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.HospitalDto;
import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	
	void registerMember(MemberDto memberDto) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	List<MemberDto> listMember() throws Exception;
	MemberDto getMember(String userId) throws Exception;
	void updateMember(MemberDto memberDto) throws Exception;
	void deleteMember(String userId) throws Exception;
	
	List<MemberDto> searchMemberById(String userId) throws Exception;
    List<MemberDto> searchMemberByName(String userName) throws Exception;

	List<HospitalDto> getHospitalList(String curregion) throws Exception;
	List<MemberDto> sortMember() throws Exception;
}
