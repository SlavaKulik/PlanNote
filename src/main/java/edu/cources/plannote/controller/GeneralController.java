package edu.cources.plannote.controller;

import edu.cources.plannote.dto.*;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.service.CustomUserDetailsService;
import edu.cources.plannote.service.TransactionService;
import edu.cources.plannote.service.ProjectService;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.*;

@Controller
@Validated
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

    @GetMapping(value = "/registration")
    public String registration(){ return "/pages/users/add_user"; }

    @PostMapping(value = "/registration")
    public String createNewUser(
            @RequestParam("userName") @Valid String userName,
            @RequestParam("userPassword") @Pattern(regexp = "^[a-zA-Z0-9]{6,12}$",
                    message = "password must be of 6 to 12 length with no special characters") String userPassword,
            @RequestParam("userPosition") @Valid String userPosition,
            @RequestParam("accountStatus") String accountStatus,
            @RequestParam("userScore") String userScore,
            @RequestParam("userStatus") String userStatus) {
        AccountStatusEntity accountStatusEntity = new AccountStatusEntity();
        accountStatusEntity.setAccountStatusId(accountStatus);
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setScore(userScore);
        UserStatusEntity userStatusEntity = new UserStatusEntity();
        userStatusEntity.setUserStatusId(userStatus);
        UserDto userDto = UserDto.builder()
                .userName(userName)
                .userPassword(bCryptPasswordEncoder.encode(userPassword))
                .userPosition(userPosition)
                .accountStatus(accountStatusEntity)
                .score(scoreEntity)
                .userStatus(userStatusEntity)
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
    public String createNewProject(){

        return "/pages/projects/add_project";
    }

    @PostMapping(value = "/my-projects/add-projects")
    public String createNewProject(
            @AuthenticationPrincipal UserEntity user,
            @RequestParam("projectName") @Valid String projectName) {
        Set<UserEntity> users = Set.of(user);
        ProjectDto projectDto = ProjectDto.builder()
                .projectName(projectName)
                .users(users)
                .build();
        projectService.createNewProject(projectDto);
        return "/pages/projects/add_project";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ModelAndView handleConstraintViolationExceptionForProjectAdding(
            ConstraintViolationException e,
            HttpServletRequest request) {
        String error = new ArrayList<>(e.getConstraintViolations()).get(0).getMessage();
        String url = String.valueOf(request.getRequestURL());
        ModelMap model = new ModelMap();
        model.addAttribute("error", error);
        model.addAttribute("url", url);
        return new ModelAndView("/pages/errors/argument_error", model);
    }

    @GetMapping(value = "/my-projects")
    public String findProjectsByUserId(
            @AuthenticationPrincipal UserEntity user,
            @ModelAttribute("model") ModelMap model) {
        List<UserDto> projects = customUserDetailsService.getProjectsByUserId(user.getIdentifier());
        model.addAttribute("projectList", projects);
        return "/pages/projects/projects_by_user_id";
    }

    @GetMapping(value = "/my-projects/{projectId}/assign-members")
    public ModelAndView openMyProject(
            @PathVariable(value = "projectId") String projectId,
            @ModelAttribute("model") ModelMap model) {
        UUID id = UUID.fromString(projectId);
        List<UserDto> users = projectService.findUsersByProjectId(id);
        model.addAttribute("projectId", id);
        model.addAttribute("userList", users);
        return new ModelAndView("/pages/tasks/assign_user_to_project", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/assign-members")
    public String addUserToProject(
            @PathVariable(value = "projectId") String project,
            @RequestParam(value = "userName") @NotBlank(message = "Username can't be blank") String user,
            @ModelAttribute("model") ModelMap model) {
        UUID projectId = UUID.fromString(project);
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user);
        ProjectDto projectDto = ProjectDto.builder()
                .id(project)
                .user(newUser)
                .build();
        projectService.assignUserToProject(projectDto);
        List<UserDto> users = projectService.findUsersByProjectId(projectId);
        model.addAttribute("userList", users);
        model.addAttribute("projectId", projectId);
        return "/pages/tasks/assign_user_to_project";
    }

    @PostMapping(value = "/my-projects/{projectId}/delete/{userId}")
    public String deleteUserFromProject(
            @PathVariable(value = "projectId") String project,
            @PathVariable(value = "userId") String user,
            @ModelAttribute("model") ModelMap model) {
        UUID projectId = UUID.fromString(project);
        UUID userId = UUID.fromString(user);
        projectService.deleteUserFromProject(projectId, userId);
        List<UserDto> users = projectService.findUsersByProjectId(projectId);
        model.addAttribute("userList", users);
        model.addAttribute("projectId", projectId);
        return "redirect:/my-projects/{projectId}/assign-members";
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @PathVariable("projectId") String project){
        UUID projectId = UUID.fromString(project);
        List<TaskDto> tasks = projectService.findTasksByProjectIdAndUserId(projectId, user.getIdentifier());
        String projectName = projectService.findProjectNameById(projectId);
        model.addAttribute("projectName", projectName);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("taskList", tasks);
        return new ModelAndView("/pages/tasks/tasks_main_page", model); }

    @PostMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @PathVariable("projectId") String projectId,
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @RequestParam("taskName") @Valid String taskName,
            @RequestParam("taskStatus") String taskStatus,
            @RequestParam("taskTimeStart") @NotBlank(message = "Task start time is mandatory!") String taskTimeStart,
            @RequestParam("taskTimeEnd") @NotBlank(message = "Task end time is mandatory!") String taskTimeEnd,
            @RequestParam("taskPriority") String taskPriority) {
        ProjectEntity projectTask = new ProjectEntity();
        UUID project = UUID.fromString(projectId);
        StatusEntity status = new StatusEntity();
        PriorityEntity priority = new PriorityEntity();
        priority.setPriorityId(taskPriority);
        status.setStatusId(taskStatus);
        projectTask.setProjectId(project);
        TaskDto taskDto = TaskDto.builder()
                .taskName(taskName)
                .user(user)
                .project(projectTask)
                .status(status)
                .startTime(taskTimeStart)
                .endTime(taskTimeEnd)
                .priority(priority)
                .build();
        projectService.addNewTask(taskDto);
        String projectName = projectService.findProjectNameById(project);
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
            @RequestParam("taskName") @Valid String taskName) {
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
            @RequestParam("taskTimeStart") @NotBlank(message = "Task start time is mandatory!") String taskTimeStart) {
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
            @RequestParam("taskTimeEnd") @NotBlank(message = "Task end time is mandatory!") String taskTimeEnd) {
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
    public ModelAndView addNewTransactions(
            @PathVariable("projectId") String projectId,
            @PathVariable("taskId") String taskId,
            @ModelAttribute("model") ModelMap model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("/pages/tasks/add_transaction", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-transaction")
    public ModelAndView addNewTransactions(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("transactionName") @Valid String transactionName,
            @RequestParam("transactionMoneyFlow") @NotBlank(message = "Transaction money flow is mandatory!") String transactionMoneyFlow) {
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
    public ModelAndView addNewSubtask(
            @PathVariable("projectId") String projectId,
            @PathVariable("taskId") String taskId,
            @ModelAttribute("model") ModelMap model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("taskId", taskId);
        return new ModelAndView("/pages/subtasks/add_subtask", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-subtask")
    public ModelAndView addNewSubtask(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("subtaskName") @Valid String subtaskName,
            @RequestParam("startTime") @NotBlank(message = "Subtask start time is mandatory!") String startTime,
            @RequestParam("endTime") @NotBlank(message = "Subtask end time is mandatory!") String endTime) {
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
            @RequestParam("subtaskName") @Valid String subtaskName) {
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
            @RequestParam("startTime") @NotBlank(message = "Subtask start time is mandatory!") String startTime) {
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
            @RequestParam("endTime") @NotBlank(message = "Subtask end time is mandatory!") String endTime) {
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