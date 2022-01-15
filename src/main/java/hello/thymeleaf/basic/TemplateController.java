package hello.thymeleaf.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    /**
     * 템플릿 조각
     *
     * thymeleaf 는 템플릿 조각과 레이아웃 기능을 지원
     *
     * @return
     */
    @GetMapping("fragment")
    public String template() {

        return "template/fragment/fragmentMain";
    }

    /**
     * 템플릿 레이아웃1
     * 
     * @return
     */
    @GetMapping("layout")
    public String layout() {

        return "template/layout/layoutMain";
    }
}
