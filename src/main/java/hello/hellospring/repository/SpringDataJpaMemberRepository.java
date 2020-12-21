package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
JpaRepository를 상속받으면 Spring Data Jpa가 구현체를 자동으로 만들어 주고 Spring Bean에 자동 등록해준다.
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    /*
    JpaRepository 내부에 기본 공통 CRUD에 대한 구현이 제공되어 비지니스에 따라 필요한 함수만 정의해주면 된다.
    인터페이스 함수의 이름, 반환타입, 파라미터를 분석해 JPQL을 만들어 준다.
    ex. 아래의 메소드 select m from Member m where m.name = ?
     */
    @Override
    Optional<Member> findByName(String name);
}
