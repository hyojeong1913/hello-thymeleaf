package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    /**
     * th:text 를 사용하여 HTML 의 콘텐츠(content)에 데이터를 출력
     *
     * 컨텐츠 안에서 직접 출력 = [[${data}]]
     *
     * @param model
     * @return
     */
    @GetMapping("/text-basic")
    public String textBasic(Model model) {

        model.addAttribute("data", "Hello Spring!");

        return "basic/text-basic";
    }

    /**
     * Escape
     *
     * HTML 문서는 <,> 같은 특수문자를 기반으로 정의되므로
     * 뷰 템플릿으로 HTML 화면을 생성할 때는 출력하는 데이터에 이러한 특수 문자가 있는 것을 주의해서 사용
     *
     * 이스케이프 (escape)
     * : HTML 에서 사용하는 특수 문자를 HTML 엔티티로 변경하는 것
     *
     * @param model
     * @return
     */
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {

        model.addAttribute("data", "Hello <b>Spring!</b>");

        return "basic/text-unescape";
    }

    /**
     * SpringEL 다양한 표현식 사용
     *
     * Object
     * - user.username : user 의 username 을 프로퍼티 접근 => user.getUsername()
     * - user['username'] : user 의 username 을 프로퍼티 접근 => user.getUsername()
     * - user.getUsername() : user 의 getUsername() 을 직접 호출
     *
     * List
     * - users[0].username : List 에서 첫 번째 회원을 찾고 username 프로퍼티 접근 => list.get(0).getUsername()
     * - users[0]['username'] : List 에서 첫 번째 회원을 찾고 username 프로퍼티 접근 => list.get(0).getUsername()
     * - users[0].getUsername() : List 에서 첫 번째 회원을 찾고 메서드 직접 호출
     *
     * Map
     * - userMap['userA'].username : Map 에서 userA 를 찾고, username 프로퍼티 접근 => map.get("userA").getUsername()
     * - userMap['userA']['username'] : Map 에서 userA 를 찾고, username 프로퍼티 접근 => map.get("userA").getUsername()
     * - userMap['userA'].getUsername() : Map 에서 userA 를 찾고 메서드 직접 호출
     *
     * @param model
     * @return
     */
    @GetMapping("/variable")
    public String variable(Model model) {

        User userA = new User("userA", 10);
        User userB = new User("userB", 10);

        List<User> list = new ArrayList<>();

        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();

        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "basic/variable";
    }

    /**
     * thymeleaf 는 기본 객체들을 제공
     *
     * ${#request}
     * ${#response}
     * ${#session}
     * ${#servletContext}
     * ${#locale}
     *
     * HTTP 요청 파라미터 접근: param
     * - 예) ${param.paramData}
     *
     * HTTP 세션 접근: session
     * - 예) ${session.sessionData}
     *
     * 스프링 빈 접근: @
     * - 예) ${@helloBean.hello('Spring!')}
     *
     * @param session
     * @return
     */
    @GetMapping("/basic-objects")
    public String basicObject(HttpSession session) {

        session.setAttribute("sessionData", "Hello Session");

        return "basic/basic-objects";
    }

    /**
     * 타임리프는 문자, 숫자, 날짜, URI 등을 편리하게 다루는 다양한 유틸리티 객체들을 제공
     * 
     * @param model
     * @return
     */
    @GetMapping("/date")
    public String date(Model model) {

        model.addAttribute("localDateTime", LocalDateTime.now());

        return "basic/date";
    }

    /**
     * URL 링크
     *
     * @param model
     * @return
     */
    @GetMapping("/link")
    public String link(Model model) {

        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");

        return "basic/link";
    }

    /**
     * 리터럴
     *
     * thymeleaf 에서 문자 리터럴은 항상 ' (작은 따옴표) 로 감싸야 한다.
     * 공백 없이 문자열이 이어진다면 하나의 의미있는 토큰으로 인지해서 작은 따옴표 생략 가능
     *
     * 리터럴 대체 (Literal substitutions)
     * => | 문자열 |
     *
     * @param model
     * @return
     */
    @GetMapping("/literal")
    public String literal(Model model) {

        model.addAttribute("data", "Spring!");

        return "basic/literal";
    }

    @Component("helloBean")
    static class HelloBean {

        public String hello(String data) {

            return "Hello " + data;
        }
    }

    @Data
    static class User {

        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
