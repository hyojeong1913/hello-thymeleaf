package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
