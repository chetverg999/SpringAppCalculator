package ru.ivanov.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ivanov Alexandr on 06.10.2022
 */

@Controller
@RequestMapping("/first")
public class FirstController {

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "name", required = false) String name, Model model,
                           @RequestParam (value = "surname", required = false) String surname) {
        model.addAttribute("name", name);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String sayGoodbye(@RequestParam(value = "name", required = false) String name, Model model,
                             @RequestParam (value = "surname", required = false) String surname) {
        model.addAttribute("name", name);
        return "first/goodbye";
    }

    @GetMapping("/exit")
    public String exit(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "first/exit";
    }
}
