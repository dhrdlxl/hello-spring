package hello.hellospring.controller;

public class MemberForm {
    /*
    createMemberForm.html의 input 태그의 name과 동일하게 변수명을 설정한다.
    (서버로 값이 넘어올 때 키가 되는 값이 name 이다.)
    이 경우에는 name이 name 이다.

    MemberController의 create 함수가 실행되면 MemberForm을 생성할 때 스프링에서 createMemberForm.html에서
    post로 받아 온 값을 setName()을 통해 MemberForm의 name에 등록한다.
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
