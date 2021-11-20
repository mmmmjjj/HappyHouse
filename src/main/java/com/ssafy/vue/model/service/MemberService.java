package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.HospitalDto;
import com.ssafy.vue.model.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	
	boolean registerMember(MemberDto memberDto) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	List<MemberDto> listMember(BoardParameterDto boardParameterDto) throws Exception;
	MemberDto getMember(String userId) throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;
	boolean deleteMember(String userId) throws Exception;
	
	List<MemberDto> searchMemberById(String userId) throws Exception;
    List<MemberDto> searchMemberByName(String userName) throws Exception;

	List<HospitalDto> getHospitalList(String curregion) throws Exception;
	List<MemberDto> sortMember() throws Exception;
}
