package com.kh.wassupSeoul.member.model.service;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.wassupSeoul.hobby.model.vo.Hobby;
import com.kh.wassupSeoul.hobby.model.vo.MemberHobby;
import com.kh.wassupSeoul.member.model.dao.MemberDAO;
import com.kh.wassupSeoul.member.model.vo.Member;
import com.kh.wassupSeoul.member.model.vo.ProfileStreet;
import com.kh.wassupSeoul.street.model.vo.Keyword;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired 
	private MemberDAO memberDAO;

	// 암호화를 위한 객체를 DI(의존성 주입)
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	
	/** 회원가입용 Service
	 * @param signUpMember
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int signUp(Member signUpMember) throws Exception {
		String encPwd = bcryptPasswordEncoder.encode(signUpMember.getMemberPwd());
		signUpMember.setMemberPwd(encPwd);
		return memberDAO.signUp(signUpMember);
	}
	
	
	/** 로그인용 Service
	 * @param member
	 * @return Member
	 * @throws Exception
	 */
	@Override
	public Member loginMember(Member member) throws Exception {
		
		Member loginMember = memberDAO.selectMember(member);
		
		if (loginMember != null) { 
			if(!bcryptPasswordEncoder.matches(member.getMemberPwd(), loginMember.getMemberPwd())) {
				loginMember = null;
			}
		}
		return loginMember;
	}
	

	/** 이메일 찾기용 Service
	 * @param member
	 * @return memberEmail
	 * @throws Exception
	 */
	@Override
	public String findEmail(Member member) throws Exception {
		return memberDAO.findEmail(member);

	}


	/** 비밀번호 찾기용 Service
	 * @param member
	 * @return memberPassword
	 * @throws Exception
	 */
	@Override
	public String findPassword(Member member) throws Exception {
		return memberDAO.findPassword(member);
	}
	
	


	/** 이메일 중복 체크
	 * @param memberEmail
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int emailDupCheck(String memberEmail) throws Exception {
		return memberDAO.emailDupcheck(memberEmail);
	}


	/** 닉네임 중복 체크
	 * @param memberNickname
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int nickNameDupCheck(String memberNickname) throws Exception {
		return memberDAO.nickNameDupcheck(memberNickname);
	}


	/** 랜덤 비밀번호 발생 Service
	 * @param buf
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int makeRandomPwd(Map <String,String> randomMap) throws Exception {
		String meme = randomMap.get("randomPwd");
		String newPwd = bcryptPasswordEncoder.encode(meme);
		randomMap.put("newPwd", newPwd);
		return memberDAO.makeRandomPwd(randomMap);
	}

	/** 프로필 관심사 조회용 Service
	 * @param memberNo
	 * @return myHobby
	 * @throws Exception
	 */
	@Override
	public List<Hobby> selectHobby(int memberNo) throws Exception{
		return memberDAO.selectHobby(memberNo);
	}

	/** 내골목 조회용 Service
	 * @param memberNo
	 * @return myStreet
	 * @throws Exception
	 */
	@Override
	public List<ProfileStreet> selectProfileStreet(int memberNo) throws Exception {
		return memberDAO.selectProfileStreet(memberNo);
	}

	/** 내골목 골목대장 조회용 Service
	 * @param streetNo
	 * @return streetMaster
	 * @throws Exception
	 */
	@Override
	public String selectStreetMaster(int streetNo) throws Exception {
		return memberDAO.selectStreetMaster(streetNo);
	}

	/** 내골목 키워드 조회용 Service
	 * @param i
	 * @return myKeyword
	 * @throws Exception
	 */
	@Override
	public List<Keyword> selectMyKeyword(int i) throws Exception {
		int streetNo = i;
		return memberDAO.selecyMyKeyword(streetNo);
	}

	/** 현재 비밀번호 일치여부 조회용 Service
	 * @param beforePwd
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int beforePwdCheck(String beforePwd, int memberNo) throws Exception {
		// 현재 비밀번호 가져오기
		String currentPwd = memberDAO.selectMemberPwd(memberNo);
		
		// 비밀번호 일치여부 검사
		if(!bcryptPasswordEncoder.matches(beforePwd, currentPwd)) {
			return 0;
		} else {
			return 1;
		}
	}

	/** 검색된 관심사 조회용 Service
	 * @param searchHobbyContent
	 * @return hobbyList
	 * @throws Exception
	 */
	@Override
	public List<Hobby> searchHobby(String searchHobbyContent) throws Exception {
		return memberDAO.searchHobby(searchHobbyContent);
	}

	/** 검색된 관심사 지정 회원수 조회용 Service
	 * @param hobbyNm
	 * @return count
	 * @throws Exception
	 */
	@Override
	public int selectHobbyCount(String hobbyName) throws Exception {
		return memberDAO.selectHobbyCount(hobbyName);
	}

	/** 현재 비밀번호 조회용 Service
	 * @param memberNo
	 * @return memberPwd
	 * @throws Exception
	 */
	@Override
	public String selectMemberPwd(int memberNo) throws Exception {
		return memberDAO.selectMemberPwd(memberNo);
	}

	/** 회원 정보 수정용 Service
	 * @param member
	 * @param flag
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateMember(Member member, int flag) throws Exception {
		if(flag > 0) { // 비밀번호 변경할 경우
			String encPwd = bcryptPasswordEncoder.encode(member.getMemberPwd());
			member.setMemberPwd(encPwd);			
		} 
		int result = memberDAO.updateMember(member);
		return result;
	}

	/** 직접 작성한 관심사 중복 여부 조회용 Service
	 * @param hobbyName
	 * @return hobby
	 */
	@Override
	public Hobby hobbyDupCheck(String hobbyName){
		return memberDAO.hobbyDupCheck(hobbyName);
	}

	/** 기존 관심사 삭제용 Service
	 * @return result1
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteMemberHobby(int memberNo) throws Exception {
		return memberDAO.deleteMemberHobby(memberNo);
	}

	/** 변경된 관심사 추가용 Service 
	 * @param myHobby
	 * @return result1
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateMemberHobby(List<MemberHobby> changeHobby) throws Exception {
		int result = 0;
		for(int i=0;i<changeHobby.size();i++) {
			result = memberDAO.updateMemberHobby(changeHobby.get(i));
			if(result == 0) {
				return 0;
			}
		}
		return result;
	}
	
	/** 해당 관심사 번호 조회용 Service
	 * @param string
	 * @return hobbyNo
	 * @throws Exception
	 */
	@Override
	public int getInsertHobbyNo(String hobbyName) throws Exception {
		return memberDAO.getInsertHobbyNo(hobbyName);
	}

	/** 관심사 추가용 Service
	 * @param string
	 * @return addResult
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int insertHobby(String hobbyName) throws Exception {
		return memberDAO.insertHobby(hobbyName);
	}
	
	/** 생성된 회원번호 조회용 Service
	 * @return memberNo
	 * @throws Exception
	 */
	@Override
	public int selectMemberNo() throws Exception {
		return memberDAO.selectMemberNo();
	}
	
	/** 회원 탈퇴용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteMember(int memberNo) throws Exception {
		return memberDAO.deleteMember(memberNo);
	}

	/** 가입한 골목수 조회
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int selectJoinStreetList(int memberNo) throws Exception {
		return memberDAO.selectJoinStreetList(memberNo);
	}

	/** 회원 가입 골목 목록 삭제용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteJoinStreetList(int memberNo) throws Exception{
		return memberDAO.deleteJoinStreetList(memberNo);
	}

	/** 회원 알람 목록수 조회용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int selectAlarmList(int memberNo) throws Exception {
		return memberDAO.selectAlarmList(memberNo);
	}

	/** 회원 알람목록 삭제용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteAlarmList(int memberNo) throws Exception{
		return memberDAO.deleteAlarmList(memberNo);
	}

	/** 회원 친구 목록수 조회용  Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Override
	public int selectFriendList(int memberNo) throws Exception {
		return memberDAO.selectFriendList(memberNo);
	}
	
	/** 회원 친구 목록 삭제용 Service
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteFriendList(int memberNo) throws Exception {
		return memberDAO.deleteFriendList(memberNo);
	}
	
	/** 회원 정보 조회용 Service
	 * @param memberNo
	 * @return member
	 * @throws Exception
	 */
	@Override
	public Member selectProfileMember(int memberNo) throws Exception {
		return memberDAO.selectProfileMember(memberNo);
	}

	/** 현재 주민 수 조회용 Service
	 * @param streetNo
	 * @return citizenCount
	 * @throws Exception
	 */
	@Override
	public int selectCitizenCount(int streetNo) throws Exception {
		return memberDAO.selectCitizenCount(streetNo);
	}

	/*------------------------ 정승환 추가코드(20.03.28,29)시작-----------------------------------*/
	/** 가입 대기 골목 정보 조회용 Service
	 * @param memberNo
	 * @return waitList
	 * @throws Exception
	 */
	@Override
	public List<ProfileStreet> selectWaitStreet(int memberNo) throws Exception {
		return memberDAO.selectWaitStreet(memberNo);
	}
	
	/** 골목 가입 대기 인원수 조회용 Service
	 * @param streetNo
	 * @return waitStreetCount
	 * @throws Exception
	 */
	@Override
	public int selectWaitStreetCount(int streetNo) throws Exception {
		return memberDAO.selectWaitStreetCount(streetNo);
	}
	
	/** 가입 실패 및 탈퇴 골목 정보 조회용 Service
	 * @param memberNo
	 * @return outStreetList
	 * @throws Exception
	 */
	@Override
	public List<ProfileStreet> selectOutStreet(int memberNo) throws Exception {
		return memberDAO.selectOutStreet(memberNo);
	}
	/*------------------------ 정승환 추가코드(20.03.28,29)끝-----------------------------------*/



	
	
	
	
	
	
	
	
} // ServiceImpl 종료
