package com.kh.wassupSeoul.street.model.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.wassupSeoul.hobby.model.vo.Hobby;
import com.kh.wassupSeoul.member.model.vo.Member;
import com.kh.wassupSeoul.street.model.vo.Board;
import com.kh.wassupSeoul.street.model.vo.Street;

@Repository
public class StreetDAO {

	@Autowired	
	private SqlSessionTemplate sqlSession;

	/** 골목 조회용 DAO
	 * @param streetNo
	 * @return street
	 * @throws Exception
	 */
	public Street selectStreet(Integer streetNo) throws Exception{
		return sqlSession.selectOne("streetMapper.selectStreet", streetNo );
	}

	/** 게시글 조회용 DAO
	 * @param streetNo
	 * @return list
	 * @throws Exception
	 */
	public List<Board> selectBoard(Integer streetNo) throws Exception{
		return sqlSession.selectList("streetMapper.selectBoardList", streetNo );
	}

	/** 게시글 등록용 DAO
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Board board) throws Exception {
		return sqlSession.insert("streetMapper.insertBoard", board );
	}

	/** 좋아요 체크용 DAO
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	public String likeCheck(Member loginMember) throws Exception {
		return sqlSession.selectOne("streetMapper.likeCheck", loginMember );
	}

	/** 좋아요 기록용 DAO
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	public int recordLike(Member loginMember) throws Exception {
		
		return sqlSession.insert("streetMapper.recordLike", loginMember );
	}

	/** 좋아요 업데이트용 DAO
	 * @param loginMember
	 * @return result
	 * @throws Exception
	 */
	public int updateLike(Member loginMember) throws Exception {
		return sqlSession.update("streetMapper.updateLike", loginMember );
	}

	/** 좋아요, 댓글수 조회용 DAO
	 * @param postNo
	 * @return 
	 * @throws Exception
	 */
	public int[] checkLikeReplyNum(int postNo) throws Exception{
		return sqlSession.selectOne("streetMapper.checkLikeReplyNum", postNo );
	}

	/** 게시글 삭제용 DAO
	 * @param postNo
	 * @return result
	 * @throws Exception
	 */
	public int deletePost(int postNo) throws Exception{
		return sqlSession.update("streetMapper.deletePost", postNo );
	}

	/** 골목 가입용 DAO
	 * @param map
	 * @return result
	 */
	public int streetJoin(Map<String, Object> map) {
		return sqlSession.insert("streetMapper.streetJoin", map);
	}

	/** 회원 관심사 조회용 DAO
	 * @param memberNo
	 * @return myHobby
	 * @throws Exception
	 */
	public List<Hobby> selectHobby(int memberNo) throws Exception{
		return sqlSession.selectList("memberMapper.selectHobby", memberNo);
	}

	
	/** 추천 친구 목록 조회용 DAO
	 * @param map
	 * @return mList
	 * @throws Exception
	 */
	public List<Member> selectRecommendList(Map<String, Object> map) throws Exception{
		return sqlSession.selectList("streetMapper.selectRecommendList", map);
	}

	/** 가입한 골목 수 조회용 Service
	 * @param memberNo
	 * @return myStreetCount
	 */
	public int myStreetCount(int memberNo){
		return sqlSession.selectOne("streetMapper.myStreetCount", memberNo);
	}

	/**
	 * @param mList
	 * @return
	 * @throws Exception
	 */
	public List<Hobby> selectHobbyList(List<Member> mList) throws Exception{
		return sqlSession.selectList("streetMapper.selectHobbyList", mList);
	}


}
