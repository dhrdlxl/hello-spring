package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    /*
        Jpa는 EntityManager로 모든 동작을 한다.
        Gradle을 통해 data-jpa 라이브러리를 받으면 스프링 부트가 자동으로 EntityManager를 생성해준다.
        따라서 Entitymanager를 주입 받기만 하면 된다.
     */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        /*
            persist()를 하면 Jpa가 insert 쿼리를 통해 DB에 넣고, member에 setId() 까지 해준다.
         */
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//find (조회할 type, 식별자)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("Select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member as m", Member.class).getResultList();
    }
}
