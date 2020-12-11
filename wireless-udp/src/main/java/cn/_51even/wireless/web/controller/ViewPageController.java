package cn._51even.wireless.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/viewPage")
public class ViewPageController {

    @GetMapping("/sysDict")
    public String sysDict(){
        return "sysDict";
    }

    @GetMapping("/sysConfig")
    public String sysConfig(){
        return "sysConfig";
    }
}
