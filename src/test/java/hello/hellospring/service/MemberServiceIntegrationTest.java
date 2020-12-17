package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    @SpringBootTest는 스프링 컨테이너와 테스트를 함께 실행해 진짜 스프링을 띄워 테스트를 할 수 있다.
 */
/*
    Transactional annotation을 사용하면 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후 항상 롤백한다.
    따라서, DB에 데이터가 남지 않아 다음 테스트에 영향을 주지 않는다.
    (이를 통해 여기서는 MemberServiceTest에서 AfterEach를 사용해 초기화 해주는 과정을 따로 만들지 않아도 된다.)
 */
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    //테스트 시 생성자 주입이 아닌 필드 주입으로 편하게 사용해도 된다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given(주어진 것)
        Member member = new Member();
        member.setName("spring");

        //when(실행했을 때)
        Long saveId = memberService.join(member);

        //then(어떤 결과가 나와야 하는가)
        Member findMember = memberService.findOne(saveId).get();

        //Assertions를 static import 하면 바로 assertThat을 쓸 수 있다.
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);

        //then
        //memberService.join(member2)를 했을 때 앞의 오류가 발생해야 성공
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //에러 메세지 까지 검증 가능
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberService.join(member2);

        //when, then
        assertThat(memberService.findMembers().size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        //given
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);

        //when
        Member result = memberService.findOne(member.getId()).get();

        //then
        assertThat(member.getName()).isEqualTo(result.getName());
    }
}