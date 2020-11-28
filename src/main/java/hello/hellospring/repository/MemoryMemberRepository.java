package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//Option + Enter로 implement method를 편하게 할 수 있다.
public class MemoryMemberRepository implements MemberRepository{

    //실무에서는 동시성 문제로 ConcurrentHashMap, AtomicLong을 사용한다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;//key값 생성(0,1,2...)

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        //findAny -> 하나라도 찾으면 반환한다. (결과 값은 Optional로 반환된다.)
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
