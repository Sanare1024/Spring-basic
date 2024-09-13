package hello.hellospring.serviece;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){ //Dependency Injection
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void aftereach(){ // 이걸 넣음으로써 순서에 관계없이 한 테스트가 끝나면 store를 clear하게 되어 순서로 인한 오류가 사라짐
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {//테스트의 경우 과감하게 한글로 바꿔도 괜찮음 (실제 빌드 될때 테스트는 코드에 포함되지않음)
        //given - 무엇가가 주어졌는데
        Member member = new Member();
        member.setName("hello");

        //when - 이걸 실행했을 때
        Long saveId = memberService.join(member);

        //then - 결과가 이게 나와야해
        //우리가 저장한게 repository에 있는게 맞아? -> repository를 꺼내봐야함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //멤버에서 가져온 Name이 repository에서 꺼내온 findMember의 Name과 같니?
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//여기도 람다
        // () -> memberService.join(member2) 이 로직을 실행할건데 이때 왼쪽의 예외가 터져야해

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


/*
        try {
            memberService.join(member2); // 이름이 같으니까 validate에서 걸려서 예외가 터져야함
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}