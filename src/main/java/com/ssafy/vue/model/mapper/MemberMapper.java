package com.ssafy.vue.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.vue.model.BoardParameterDto;
import com.ssafy.vue.model.HospitalDto;
import com.ssafy.vue.model.MemberDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	
	boolean registerMember(MemberDto memberDto) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	List<MemberDto> listMember(BoardParameterDto boardParameterDto) throws Exception;
	MemberDto getMember(String userId) throws Exception;
	boolean updateMember(MemberDto memberDto) throws Exception;
	boolean deleteMember(String userId) throws Exception;
	
	List<MemberDto> searchMemberById(String userId) throws Exception;
	List<MemberDto> searchMemberByName(String userName) throws Exception;

	List<HospitalDto> searchHospital(String curregion) throws Exception;
	List<MemberDto> sortMember() throws Exception;

}
