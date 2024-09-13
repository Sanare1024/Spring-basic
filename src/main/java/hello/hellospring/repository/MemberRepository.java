package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id); // 가져오는데 null이면 null 그대로 반환하는게 아니라 optional로 감싸서 반환 ==> 나중에 설명
    Optional<Member> findByName(String name);
    List<Member> findAll();


}
