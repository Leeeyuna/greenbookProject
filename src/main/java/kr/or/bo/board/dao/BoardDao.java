package kr.or.bo.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.board.vo.BoardRowMapper;

@Repository
public class BoardDao {
	
	
	
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired 
	private BoardRowMapper boardRowMapper;
	
	/*
	public List selectBoardList() {
		String query="select * from Board";
		List boardList = jdbc.query(query,boardRowMapper);
		return boardList;
	}
	*/
	
	//1.한 페이지당 게시물 수 지정  ->> 10개
	public List selectBoardList(int start, int end) {
		String query = "select * from(select rownum as rnum, n.* from(select * from board order by 1 desc)n)where rnum between ? and ?";
		List boardList = jdbc.query(query, boardRowMapper, start,end); //boardRowMapper >> rowMapper 조회
		return boardList;
	}
	
	//2.페이지 네비게이션 제작 ->> 끝 번호 뽑아오기
	public int selectBoardTotalCount() {
		String query = "select count(*) from board";
		int totalCount = jdbc.queryForObject(query, Integer.class);
		return totalCount;
	}
	
	
	
}//종료