package kr.or.bo.mypage.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.member.model.vo.Member;
import kr.or.bo.product.model.vo.ProductRowMapper;

@Repository
public class MypageDao {
	@Autowired
	private JdbcTemplate jdbc;
	//찜목록 RowMapper추가 필요
	@Autowired
	private ProductRowMapper productRowMapper;

	//회원정보 수정
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		
		//변수 총 4개
		//비번, 휴대폰, 이메일 3개 수정 아이디받은곳에서
		String query= "UPDATE MEMBER SET MEMBER_PW=? , MEMBER_PHONE=? , MEMBER_EMAIL=? WHERE MEMBER_ID = ?";
		Object[] params = {member.getMemberPw(), member.getMemberPhone(), member.getMemberEmail(), member.getMemberId()};
		int result = jdbc.update(query,params);
		
		return result;
	}

	//내가 판매중인 도서 조회
	public List selectMySellBook(String memberId, int reqPage) {
		// TODO Auto-generated method stub
		
		String query = "select * from(select ROWNUM AS RNUM,N.* from(select * from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ? order by 1 DESC)N) where rnum between 1 and 10";
		
		List list = jdbc.query(query, productRowMapper, memberId);
		
		return list;
	}

	//내가 판매중인 도서 토탈 카운트 세오기
	public int selectMySellBookTotalCount(String memberId) {
		// TODO Auto-generated method stub
		String query = "select count(*) as cnt from PRODUCT_BOARD where PRODUCT_BOARD_WRITER = ?";
		//jdbc.query(query, rowMapper, 위치홀더값 ,,,)
		//query메소드를 사용하는 경우 매번 rowMapper를 제작 (결과가 1개여도)
		//단일 값(행1, 열1)을 조회하는 경우
		int totalCount = jdbc.queryForObject(query, Integer.class, memberId);
				
		return totalCount;
	}
	
}
