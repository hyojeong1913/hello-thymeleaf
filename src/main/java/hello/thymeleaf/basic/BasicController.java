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

    /**
     * 연산
     *
     * @param model
     * @return
     */
    @GetMapping("/operation")
    public String operation(Model model) {

        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring!");

        return "basic/operation";
    }

    /**
     * 속성 값 설정
     *
     * thymeleaf 는 주로 HTML 태그에 th:* 속성을 지정하는 방식으로 동작
     *
     * th:* 로 속성을 적용하면 기존 속성을 대체하고, 기존 속성이 없으면 새로 만든다.
     *
     * @param model
     * @return
     */
    @GetMapping("/attribute")
    public String attribute(Model model) {

        return "basic/attribute";
    }

    /**
     * 반복
     *
     * @param model
     * @return
     */
    @GetMapping("/each")
    public String each(Model model) {

        addUsers(model);

        return "basic/each";
    }

    /**
     * 조건부 평가
     *
     * thymeleaf 는 해당 조건이 맞지 않으면 태그 자체를 렌더링하지 않는다.
     *
     * switch
     *  : * 은 만족하는 조건이 없을 때 사용하는 디폴트
     *
     * @param model
     * @return
     */
    @GetMapping("/condition")
    public String condition(Model model) {

        addUsers(model);

        return "basic/condition";
    }

    /**
     * 주석
     *
     * 표준 HTML 주석
     * : 자바스크립트의 표준 HTML 주석은 thymeleaf 가 렌더링 하지 않고, 그대로 남겨둔다.
     *
     * 타임리프 파서 주석
     * : 타임리프 파서 주석은 thymeleaf 의 진짜 주석이다. 렌더링에서 주석 부분을 제거한다.
     *
     * 타임리프 프로토타입 주석
     * : HTML 파일을 그대로 열어보면 주석처리가 되지만, thymeleaf 를 렌더링 한 경우에만 보이는 기능
     *
     * @param model
     * @return
     */
    @GetMapping("/comments")
    public String comments(Model model) {

        model.addAttribute("data", "Spring!");

        return "basic/comments";
    }

    /**
     * 블록
     *
     * <th:block> 은 HTML 태그가 아닌 thymeleaf 의 유일한 자체 태그
     *
     * @param model
     * @return
     */
    @GetMapping("/block")
    public String block(Model model) {

        addUsers(model);

        return "basic/block";
    }

    /**
     * 자바스크립트 인라인
     *
     * thymeleaf 는 자바스크립트에서 타임리프를 편리하게 사용할 수 있는 자바스크립트 인라인 기능을 제공
     *
     * 인라인 사용 후 렌더링 결과를 보면 문자타입인 경우 "를 포함해주며
     * 추가로 자바스크립트에서 문제가 될 수 있는 문자가 포함되어 있으면 이스케이프 처리도 해준다.
     *
     * thymeleaf 는 HTML 파일을 직접 열어도 동작하는 내추럴 템플릿 기능을 제공
     *
     * thymeleaf 의 자바스크립트 인라인 기능을 사용하면 객체를 JSON 으로 자동으로 변환
     *
     * 인라인 사용 전은 객체의 toString() 이 호출된 값
     * 인라인 사용 후는 객체를 JSON 으로 변환해준다.
     *
     * @param model
     * @return
     */
    @GetMapping("/javascript")
    public String javascript(Model model) {

        model.addAttribute("user", new User("userA", 10));

        addUsers(model);

        return "basic/javascript";
    }

    private void addUsers(Model model) {

        List<User> list = new ArrayList<>();

        list.add(new User("userA", 10));
        list.add(new User("userB", 20));
        list.add(new User("userC", 30));

        model.addAttribute("users", list);
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
