package edu.cources.plannote.controller;

import edu.cources.plannote.service.CustomUserDetailsService;
import edu.cources.plannote.service.PlannoteService;
import edu.cources.plannote.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PlannoteService plannoteService;
    private final ProjectService projectService;

    public GeneralController(CustomUserDetailsService customUserDetailsService,
                             PlannoteService plannoteService,
                             ProjectService projectService) {
        this.customUserDetailsService = customUserDetailsService;
        this.plannoteService = plannoteService;
        this.projectService = projectService;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld()
    {
        return "Hello World!";
    }
}
