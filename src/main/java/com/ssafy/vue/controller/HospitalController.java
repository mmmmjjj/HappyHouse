package com.ssafy.vue.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.vue.model.HospitalDto;
import com.ssafy.vue.model.MemberDto;
import com.ssafy.vue.model.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/hos")
@CrossOrigin("*")
@Api("병원 컨트롤러  API V1")
public class HospitalController {

	private static final Logger logger = LoggerFactory.getLogger(HospitalController.class);
	
	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "회원목록", notes = "회원의 <big>전체 목록</big>을 반환해 줍니다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원목록 OK!!"),
		@ApiResponse(code = 404, message = "페이지없어!!"),
		@ApiResponse(code = 500, message = "서버에러!!")
	})
	@GetMapping(value = "/hospitalList")
	public ResponseEntity<List<HospitalDto>> hospitalList(HttpSession session) throws Exception {
		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
		String curregion = memberDto.getRegion();
		System.out.println(curregion);
		List<HospitalDto> list = memberService.getHospitalList(curregion);
		System.out.println("HospitalList를 불러왔습니다.");
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<HospitalDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	


}
