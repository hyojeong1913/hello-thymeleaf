package hello.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
