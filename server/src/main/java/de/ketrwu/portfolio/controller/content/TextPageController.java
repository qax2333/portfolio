package de.ketrwu.portfolio.controller.content;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping
public class TextPageController {

    @GetMapping("/page/{page}")
    public String getTextPage(@PathVariable String page) {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            for (Resource resource : resolver.getResources("classpath*:/templates/content/text/*.html")) {
                if (resource.getFilename().replace(".html", "").equalsIgnoreCase(page)) {
                    return "content/text/" + page.toLowerCase();
                }
            }
        } catch (IOException e) {
            log.error("Failed to load text-pages from classpath", e);
        }
        return "content/text/error";
    }

}
