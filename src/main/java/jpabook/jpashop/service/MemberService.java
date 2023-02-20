package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행되어야 함
@RequiredArgsConstructor    // final이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired
//    public MemberService(MemberRepository memberRepository) { // 생성자 주입 장점 : 불변, 필수 값 설정 가능
//        this.memberRepository = memberRepository;
//    }
    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
