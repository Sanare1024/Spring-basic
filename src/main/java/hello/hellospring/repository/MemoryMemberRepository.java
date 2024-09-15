package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>(); //실무에서는 동시성 문제 때문에 concurrentHashmap을 사용
    private static long sequence = 0L; // 실무에선 AutomicLong을 사용

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // 이걸 감싸주면 null이여도 클라이언트에서 뭔가 할 수 있다.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream() // 람다 사용 이거 뭔소린지 하나도 모르겠음
                .filter(member -> member.getName().equals(name))
                .findAny(); // 맵에서 루프를 돌면서 멤버에서 가져온 name과 equal인지 해서 찾으면 그것을 반환
                            // 만약 끝까지 해서 못찾으면 null을 optinal해서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());// store의 Member들을 반환
    }

    public void clearStore(){
        store.clear();
    }
}
