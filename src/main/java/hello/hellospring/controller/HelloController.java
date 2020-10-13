package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hi!!");
        return "hello";//resources/templates 아래의 hello.html로 가서 렌더링 하라는 의미(model안에 data를 담아 넘기면서)
        //viewResolver가 view(hello.html)를 찾아 템플릿(thymeleaf)엔진에 넘겨주고 템플릿 엔진이 렌더링을 해서 변환된 HTML을 웹브라우저에 넘긴다.
    }

    //mvc 방식
    //html에 model을 통해 데이터를 전달해 보여준다.
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    //api 방식
    //html이 아닌 http 메세지로 데이터를 직접 보여준다.
    @GetMapping("hello-string")
    @ResponseBody//http 통신에서 body부분에 데이터를 직접 넣어주겠다는 의미.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody//viewResolver가 아닌 HttpMessageConverter를 호출, 이때 단순 문자열이면 StringConverter가 동작, 객체면 JsonConverter가 동작
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setNames(name);
        return hello;
    }

    static class Hello {
        private String names;

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }




    }
}