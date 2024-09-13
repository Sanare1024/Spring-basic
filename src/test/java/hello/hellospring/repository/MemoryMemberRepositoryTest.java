package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*; // static import화 되어서 Assertions안하고 바로 assertThat 가능

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void aftereach(){ // 이걸 넣음으로써 순서에 관계없이 한 테스트가 끝나면 store를 clear하게 되어 순서로 인한 오류가 사라짐
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result); // 3. 최신문법 / 실무에선 빌드 툴과 엮어서 통과못하면 다음 단계 못가게 막음
        //System.out.println("result = " + (result == member)); - 1. 직접보는방법
        //Assertions.assertEquals(member, result); - 2.직접보이진 않지만 오류가 뜨지않으면 둘이 같다는 뜻
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

    //테스트 순서는 컴퓨터 마음대로
    //따라서 무엇이간 앞서서 한 테스트때문에 생성된 객체가 남아서 따로따로 테스트할땐 괜찮았지만
    // 클래스 단위로 돌리게 되면 무엇인가 test가 failed하게 된다.
    // 따라서 test가 하나 끝나면 깨끗하게 clear해줘야함
}
