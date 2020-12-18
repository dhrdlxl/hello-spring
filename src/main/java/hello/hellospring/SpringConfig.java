package hello.hellospring;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.JdbcTemplateMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        /*
        Spring Boot가 application.properties(DB 커넥션 정보)를 바탕으 DataSource(DB 커넥션 획득할 때 사용하는 객체)를 빈으로 만들어 둔다.
        따라서 아래와 같이 DI를 통해 사용하면 된다.
         */
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
//        return new MemoryMemberRepository();

        /*
        OCP(Open-Closed Principle) 적용 -> 확장에는 열려있고, 수정 및 변경에는 닫혀있다.
        DI를 이용해 기존 코드를 전혀 손대지 않고(Member Service 코드 수정 x) 설정(SpringConfig 수정)만으로 구현 클래스 변경(메모리 -> H2 DB)
         */
//        return new JdbcMemberRepository(dataSource);

        return new JdbcTemplateMemberRepository(dataSource);
    }
}
