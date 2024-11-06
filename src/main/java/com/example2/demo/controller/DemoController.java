// 이 클래스가 속한 패키지를 선언합니다.
package com.example2.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
// Spring Framework에서 제공하는 Controller 애노테이션을 임포트합니다.
import org.springframework.stereotype.Controller;
// Spring의 Model 인터페이스를 임포트합니다. 이는 뷰에 데이터를 전달하는 데 사용됩니다.
import org.springframework.ui.Model;
// HTTP GET 요청을 특정 메서드에 매핑하기 위한 애노테이션을 임포트합니다.
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example2.demo.model.domain.TestDB;
import com.example2.demo.model.service.TestService;


// 이 클래스가 Spring MVC의 컨트롤러임을 나타냅니다.
// 이 애노테이션은 Spring에게 이 클래스가 웹 요청을 처리할 수 있음을 알려줍니다.
@Controller

// DemoController 클래스를 정의합니다.
public class DemoController {
    
    // // "/hello" URL로 들어오는 HTTP GET 요청을 이 메서드가 처리하도록 매핑합니다.
    // @GetMapping("/hello")
    // // hello 메서드를 정의합니다. Model 객체를 파라미터로 받습니다.
    // public String hello(Model model) {
    //     // Model 객체에 "data"라는 이름으로 " 방갑습니다." 문자열을 추가합니다.
    //     // 이 데이터는 뷰(여기서는 hello.html)에서 사용할 수 있습니다.
    //     model.addAttribute("data", " 방갑습니다.");
        
    //     // "hello"라는 문자열을 반환합니다. 
    //     // 이는 ViewResolver에 의해 처리되어 "hello.html" 템플릿을 찾아 렌더링합니다.
    //     return "hello";
    // }
//==========================2주차 추가문제=======================================>
    @GetMapping("/hello2")
    public String hello2(Model model2) {
        model2.addAttribute("data2", "홍길동님.");
        model2.addAttribute("data3", "방갑습니다.");
        model2.addAttribute("data4", "오늘.");
        model2.addAttribute("data5", "날씨는.");
        model2.addAttribute("data6", "매우 좋습니다.");
        return "hello2";
    }
//================================================================================>

    @GetMapping("/about_detailed")
    public String about() {
        return "about_detailed";
    }
//========================3주차 내용========================>
    @GetMapping("/test1")
    public String thymeleaf_test1(Model model){
        model.addAttribute("data1", "<h2> 반갑습니다 </h2>");
        model.addAttribute("data2", "태그의 속성 값");
        model.addAttribute("link", 15);
        model.addAttribute("name", "rasdf");
        model.addAttribute("para1", "001");
        model.addAttribute("para2", 002);
        return "thymleaf_test1";

    }

    @Autowired
    private TestService testService;

    @GetMapping("/testdb")
    public String getAllTestDBs(Model model) {
        TestDB test = testService.findByName("홍길동");
        TestDB test2 = testService.findByName("아저씨");
        TestDB test3 = testService.findByName("꾸러기");
        model.addAttribute("data4", test);
        model.addAttribute("data5", test2);
        model.addAttribute("data6", test3);

        System.out.println("데이터 출력 디버그 : " + test);
        return "testdb";
    }
    
    
}