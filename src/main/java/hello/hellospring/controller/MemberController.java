package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();

        //사용자가 이름을 등록하면 home.html의 name
        member.setName(form.getName());
        memberService.join(member);

        //홈 화면으로 돌려보낸다.
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}