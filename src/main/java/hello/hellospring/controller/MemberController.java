package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//Component Scan
@Controller
public class MemberController {
/*
    private final MemberService memberService = new MemberService();
    위와 같이 객체를 생성해서 가져오면 수 많은 controller들이 있을 때 서로 다른 멤버 서비스를 이용하게 된다.
    -> 멤버 서비스 생성자를 Autowired를 하면, Spring이 Spring Container안에서 관리되고 있는 멤버 서비스를 가져와 연결시켜 준다.
 */

    private final MemberService memberService;

//  Spring Container에 등록된 MemberService 객체를 가져와 넣어준다.(의존성 주입 _ Dependency Injection)
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
