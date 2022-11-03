package edu.cources.plannote.controller;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.dto.UserDto;
import edu.cources.plannote.entity.AccountStatusEntity;
import edu.cources.plannote.entity.ScoreEntity;
import edu.cources.plannote.entity.UserEntity;
import edu.cources.plannote.entity.UserStatusEntity;
import edu.cources.plannote.service.CustomUserDetailsService;
import edu.cources.plannote.service.PlannoteService;
import edu.cources.plannote.service.ProjectService;
import org.apache.catalina.User;
import org.springframework.boot.Banner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class GeneralController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PlannoteService plannoteService;
    private final ProjectService projectService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public GeneralController(CustomUserDetailsService customUserDetailsService,
                             PlannoteService plannoteService,
                             ProjectService projectService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.plannoteService = plannoteService;
        this.projectService = projectService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld() { return "Hello World!"; }

    @GetMapping(value = "/tasks/all")
    public String allTasks(@ModelAttribute("model") ModelMap model) {
        List<TaskDto> tasks = projectService.taskList();
        model.addAttribute("taskList", tasks);
        return "/pages/tasks/all_tasks";
    }

    @GetMapping(value = "/users/current-user")
    @ResponseBody
    public String currentUser(
            @AuthenticationPrincipal UserEntity user,
            @ModelAttribute("model") ModelMap model) {
        return user.getIdentifier().toString();
    }

    @GetMapping(value = "/registration")
    public String registration(){ return "/pages/users/add_user"; }

    @PostMapping(value = "/registration")
    public String createNewUser(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userPosition") String userPosition,
            @RequestParam("accountStatus") AccountStatusEntity accountStatus,
            @RequestParam("userScore") ScoreEntity userScore,
            @RequestParam("userStatus") UserStatusEntity userStatus) {
        UserDto userDto = UserDto.builder()
                .userName(userName)
                .userPassword(bCryptPasswordEncoder.encode(userPassword))
                .userPosition(userPosition)
                .accountStatus(accountStatus)
                .score(userScore)
                .userStatus(userStatus)
                .userRole("ROLE_USER")
                .build();
        customUserDetailsService.addNewUser(userDto);
        return "index";
    }

    @GetMapping(value = "/projects/add-projects")
    public String createNewProject(){ return "/pages/projects/add_project"; }

    @PostMapping(value = "/projects/add-projects")
    public String createNewProject(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam("projectName") String projectName) {
        Set<UserEntity> users = Set.of(user);
        ProjectDto projectDto = ProjectDto.builder()
                .projectName(projectName)
                .users(users)
                .build();
        projectService.createNewProject(projectDto);
        return "/pages/projects/add_project";
    }

    @GetMapping(value = "/projects/my-projects")
    public String findProjectsByUserId(
            @AuthenticationPrincipal UserEntity user,
            @ModelAttribute("model") ModelMap model) {
        List<UserDto> projects = customUserDetailsService.getProjectsByUserId(user.getIdentifier());
        model.addAttribute("userList", projects);
        return "/pages/projects/projects_by_user_id";
    }

//    @GetMapping(value = "/projects/my-projects/{project-id}")
//    public ModelAndView openMyProject(
//            @PathVariable(value = "project-id") String projectId,
//            @ModelAttribute("model") ModelMap model) {
//        model.addAttribute("project-id", projectId);
//        return new ModelAndView("/pages/projects/my_project");
//    }

//    @GetMapping(value = "/testing")
//    @ResponseBody
//    public String projectByUserId(
//            @AuthenticationPrincipal UserEntity user,
//            @ModelAttribute("model") ModelMap model) {
//        List<UUID> projectDtos = projectService.getProjectsByUserId(user.getIdentifier());
//        return projectDtos.toString();
//    }

    @GetMapping(value = "/users/find-users-by-name")
    public ModelAndView findUsersByUsername(
            @RequestParam("userName") String userName,
            @ModelAttribute("model") ModelMap model) {
        List<UserDto> users = customUserDetailsService.getUsersByName(userName);
        model.addAttribute("userList", users);
        return new ModelAndView("/pages/users/all_users", model);
    }

    @GetMapping(value = "/users/all-users")
    public String allUsers(@ModelAttribute("model") ModelMap model) {
        List<UserDto> users = customUserDetailsService.userList();
        model.addAttribute("userList", users);
        return "/pages/users/all_users";
    }
}
