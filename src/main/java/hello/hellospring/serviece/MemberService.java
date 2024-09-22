package hello.hellospring.serviece;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원가입
    public Long join(Member member){

        validateDuplicateMember(member);//같은 이름이 있는 중복회원은 안됨
        memberRepository.save(member);
        return member.getId();
    }

    // result가 Optional로 감싸져 있으니까 null값이여도 괜찮
    //과거였으면 밑이 if(m == null) 이면 이런식으로 짰음
        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { // result에 이미 값이 존재한다면 밑에 로직이 동작
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/ //이렇게 쓰면 이뻐보이지 않으니까

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())// 이런식으로도 쓸수 있음
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById((memberId));
    }
}
