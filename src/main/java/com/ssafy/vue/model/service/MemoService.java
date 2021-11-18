package com.ssafy.vue.model.service;

import java.util.List;

import com.ssafy.util.PageNavigation;
import com.ssafy.vue.model.MemoDto;
import com.ssafy.vue.model.BoardParameterDto;

public interface MemoService {
	public boolean writeArticle(MemoDto boardDto) throws Exception;
	public List<MemoDto> listArticle(int articleno) throws Exception;
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	
	public MemoDto getArticle(int articleno) throws Exception;
	public void updateHit(int articleno) throws Exception;
	public boolean modifyArticle(MemoDto boardDto) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
}
