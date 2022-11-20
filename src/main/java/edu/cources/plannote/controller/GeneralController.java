package edu.cources.plannote.controller;

import edu.cources.plannote.dto.*;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.service.CustomUserDetailsService;
import edu.cources.plannote.service.TransactionService;
import edu.cources.plannote.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Controller
public class GeneralController {

    private final CustomUserDetailsService customUserDetailsService;
    private final TransactionService transactionService;
    private final ProjectService projectService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public GeneralController(CustomUserDetailsService customUserDetailsService,
                             TransactionService transactionService,
                             ProjectService projectService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.transactionService = transactionService;
        this.projectService = projectService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/users/find-users-by-name")
    public ModelAndView findUsersByUsername(
            @RequestParam("userName") String userName,
            @ModelAttribute("model") ModelMap model) {
        List<UserDto> users = customUserDetailsService.getUsersByName(userName);
        model.addAttribute("userList", users);
        return new ModelAndView("/pages/users/all_users", model);
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
        return "redirect:/logout";
    }

    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam Optional<String> error) {
        String errorIsPresent = "The username or password you have entered is invalid, try again.";
        String errorIsNotPresent = "";
        if (error.isEmpty()) {
            return new ModelAndView("/pages/login", "error", errorIsNotPresent);
        }
        else return new ModelAndView("/pages/login", "error", errorIsPresent);
    }

    @GetMapping(value = "/my-projects/add-projects")
    public String createNewProject(){ return "/pages/projects/add_project"; }

    @PostMapping(value = "/my-projects/add-projects")
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

    @GetMapping(value = "/my-projects")
    public String findProjectsByUserId(
            @AuthenticationPrincipal UserEntity user,
            @ModelAttribute("model") ModelMap model) {
        List<UserDto> projects = customUserDetailsService.getProjectsByUserId(user.getIdentifier());
        model.addAttribute("userList", projects);
        return "/pages/projects/projects_by_user_id";
    }

    @GetMapping(value = "/my-projects/{projectId}/assign-members")
    public ModelAndView openMyProject(
            @PathVariable(value = "projectId") String projectId,
            @ModelAttribute("model") ModelMap model) {
        UUID id = UUID.fromString(projectId);
        List<TaskDto> tasks = projectService.findTasksByProjectId(id);
        model.addAttribute("taskList", tasks);
        return new ModelAndView("/pages/tasks/assign_user_to_project", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/assign-members")
    public String addUserToProject(
            @PathVariable(value = "projectId") String project,
            @RequestParam(value = "userName") String user,
            @ModelAttribute("model") ModelMap model) {
        UUID projectId = UUID.fromString(project);
        projectService.addUserToProject(user, projectId);
        model.addAttribute("projectId", projectId);
        return "/pages/tasks/assign_user_to_project";
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @PathVariable("projectId") String project){
        UUID projectId = UUID.fromString(project);
        List<TaskDto> tasks = projectService.findTasksByProjectIdAndUserId(projectId, user.getIdentifier());
        String projectName = projectService.getProjectNameById(projectId);
        model.addAttribute("projectName", projectName);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("taskList", tasks);
        return new ModelAndView("/pages/tasks/tasks_main_page", model); }

    @PostMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @PathVariable("projectId") String projectId,
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @RequestParam("taskName") String taskName,
            @RequestParam("taskStatus") StatusEntity taskStatus,
            @RequestParam("taskTimeStart") String taskTimeStart,
            @RequestParam("taskTimeEnd") String taskTimeEnd,
            @RequestParam("taskPriority") PriorityEntity taskPriority) {
        ProjectEntity projectTask = new ProjectEntity();
        UUID project = UUID.fromString(projectId);
        projectTask.setProjectId(project);
        TaskDto taskDto = TaskDto.builder()
                .taskName(taskName)
                .user(user)
                .project(projectTask)
                .status(taskStatus)
                .startTime(taskTimeStart)
                .endTime(taskTimeEnd)
                .priority(taskPriority)
                .build();
        projectService.addNewTask(taskDto);
        String projectName = projectService.getProjectNameById(project);
        List<TaskDto> tasks = projectService.findTasksByProjectIdAndUserId(project, user.getIdentifier());
        model.addAttribute("projectId", projectTask.getProjectId());
        model.addAttribute("projectName", projectName);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("taskList", tasks);
        return new ModelAndView("/pages/tasks/tasks_main_page", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-name")
    public ModelAndView updateTaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskName") String taskName) {
        UUID projectId = UUID.fromString(project);
        UUID taskId = UUID.fromString(task);
        TaskDto taskDto = TaskDto.builder()
                .id(task)
                .taskName(taskName)
                .build();
        projectService.updateTaskFromDto(taskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-status")
    public ModelAndView updateTaskStatus(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskStatus") String taskStatus) {
        UUID projectId = UUID.fromString(project);
        UUID taskId = UUID.fromString(task);
        TaskDto taskDto = TaskDto.builder()
                .id(task)
                .statusTask(taskStatus)
                .build();
        projectService.updateTaskFromDto(taskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-start-time")
    public ModelAndView updateTaskTimeStart(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskTimeStart") String taskTimeStart) {
        UUID projectId = UUID.fromString(project);
        UUID taskId = UUID.fromString(task);
        TaskDto taskDto = TaskDto.builder()
                .id(task)
                .startTime(taskTimeStart)
                .build();
        projectService.updateTaskFromDto(taskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-end-time")
    public ModelAndView updateTaskTimeEnd(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskTimeEnd") String taskTimeEnd) {
        UUID projectId = UUID.fromString(project);
        UUID taskId = UUID.fromString(task);
        TaskDto taskDto = TaskDto.builder()
                .id(task)
                .endTime(taskTimeEnd)
                .build();
        projectService.updateTaskFromDto(taskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-priority")
    public ModelAndView updateTaskPriority(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskPriority") String taskPriority) {
        UUID projectId = UUID.fromString(project);
        UUID taskId = UUID.fromString(task);
        TaskDto taskDto = TaskDto.builder()
                .id(task)
                .priorityTask(taskPriority)
                .build();
        projectService.updateTaskFromDto(taskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}")
    public ModelAndView getMoreInfoOfTask(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @ModelAttribute("model") ModelMap model) {
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        List<TransactionDto> transactions = transactionService.getTransactionsByTaskId(taskId);
        List<SubtaskDto> subtasks = projectService.findSubtasksByTaskId(taskId);
        model.addAttribute("transactionList", transactions);
        model.addAttribute("subtaskList", subtasks);
        model.addAttribute("taskId", taskId);
        model.addAttribute("projectId", projectId);
        return new ModelAndView("/pages/tasks/more_info_about_project", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-transaction")
    public String addNewTransactions(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task) {
        return "/pages/tasks/add_transaction";
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-transaction")
    public ModelAndView addNewTransactions(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("transactionName") String transactionName,
            @RequestParam("transactionMoneyFlow") String transactionMoneyFlow) {
        TaskEntity taskEntity = new TaskEntity();
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        taskEntity.setTaskId(taskId);
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionName(transactionName)
                .transactionMoneyFlow(transactionMoneyFlow)
                .task(taskEntity)
                .build();
        transactionService.addNewTransaction(transactionDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("/pages/tasks/add_transaction", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-subtask")
    public String addNewSubtask(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task) {
        return "/pages/subtasks/add_subtask";
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-subtask")
    public ModelAndView addNewSubtask(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("subtaskName") String subtaskName,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(taskId);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .task(taskEntity)
                .subtaskName(subtaskName)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        projectService.addNewSubtask(subtaskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("/pages/subtasks/add_subtask", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-name")
    public ModelAndView updateSubtaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask,
            @RequestParam("subtaskName") String subtaskName) {
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        UUID subtaskId = UUID.fromString(subtask);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .id(subtask)
                .subtaskName(subtaskName)
                .build();
        projectService.updateSubtaskFromDto(subtaskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("subtasksId", subtaskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-start-time")
    public ModelAndView updateSubtaskTimeFrom(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask,
            @RequestParam("startTime") String startTime) {
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        UUID subtaskId = UUID.fromString(subtask);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .id(subtask)
                .startTime(startTime)
                .build();
        projectService.updateSubtaskFromDto(subtaskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("subtasksId", subtaskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-end-time")
    public ModelAndView updateSubtaskTimeTill(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask,
            @RequestParam("endTime") String endTime) {
        UUID taskId = UUID.fromString(task);
        UUID projectId = UUID.fromString(project);
        UUID subtaskId = UUID.fromString(subtask);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .id(subtask)
                .endTime(endTime)
                .build();
        projectService.updateSubtaskFromDto(subtaskDto);
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        model.addAttribute("subtasksId", subtaskId);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }
}