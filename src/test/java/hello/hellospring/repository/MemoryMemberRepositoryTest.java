package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    //Test시 실행 순서는 보장이 안된다!!(어떤 method가 먼저 실행될지 모른다.) -> test 마다 데이터를 클리어 해주는 과정이 필요하다.
    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        //option + command + v 로 반환타입을 볼 수 있다.
        //Optional에서 get, orElse, orElseGet 등으로 내부 값을 가져올 수 있다.
        //get을 사용 할 때 null 값이 들어올 경우 오류가 발생한다.(NoSuchElementException)
        Member result = repository.findById(member.getId()).get();
        //assertj의 Assertions을 이용해 같은지 검사한다.
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName()
    {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

         Member result1 = repository.findByName("spring1").get();
         Assertions.assertThat(member1).isEqualTo(result1);
    }

    @Test
    public void findAll()
    {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
