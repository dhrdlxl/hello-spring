package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    //TestCode는 한글로 적으도 된다.(Build시 testCode는 포함되지 않음)

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    //현재 예제에서는 store가 static으로 선언되어 영향이 없지만
    //static이 아닐 경우 아래 BeforeEach 구문 처럼 memberService의 생성자를 통해 DB를 주입하지 않고
    //위에서 주입하면 Main과는 다른 DB를 참조하는 경우가 발생한다. 따라서 아래와 같은 방법으로 사용하는 것을 권장함
    @BeforeEach
    public void beforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository);
    }

    @AfterEach
    public void afterEach(){
        memoryMemberRepository.clearStore();
    }

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


/*
        //try catch 방법도 있지만 위의 방법을 주로 사용한다.
        try {
            memberService.join(member2);
            fail();
        }
        catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
 */
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