package hello.hellospring.domain;

import javax.persistence.*;

//JPA가 관리하는 Entity가 된다.
@Entity
public class Member {

    //DB에 값을 넣으면 DB가 Id 값을 알아서 생성해주는 전략을 Identity 전략이라 한다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name")
    //DB의 실제 컬럼명과 변수명이 같을 경우 위의 구문을 안해줘도 자동으로 된다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
