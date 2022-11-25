package edu.courses.plannote.controller;

import edu.courses.plannote.dto.*;
import edu.courses.plannote.entity.*;
import edu.courses.plannote.service.CustomUserDetailsService;
import edu.courses.plannote.service.TransactionService;
import edu.courses.plannote.service.ProjectService;
import edu.courses.plannote.utils.CodeDecodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.*;

import static edu.courses.plannote.utils.EnrichControllerMethods.*;

@Controller
@Validated
public class GeneralController {

    private static final Logger log = LoggerFactory.getLogger(GeneralController.class);

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
            @RequestParam("userScore") String userScore) {
        AccountStatusEntity accountStatusEntity = new AccountStatusEntity();
        accountStatusEntity.setAccountStatusId(accountStatus);
        ScoreEntity scoreEntity = new ScoreEntity();
        scoreEntity.setScore(userScore);
        UserDto userDto = UserDto.builder()
                .userName(userName)
                .userPassword(bCryptPasswordEncoder.encode(userPassword))
                .userPosition(userPosition)
                .accountStatus(accountStatusEntity)
                .score(scoreEntity)
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
            log.info("User login");
            return new ModelAndView("/pages/login", "error", errorIsNotPresent);
        }
        else {
            log.warn("Invalid password or login. User can't login");
            return new ModelAndView("/pages/login", "error", errorIsPresent);
        }
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
        log.warn("Invalid data insertion");
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

    @PostMapping(value = "/my-projects/{projectId}/delete")
    public ModelAndView deleteProject(
            @PathVariable("projectId") String projectId,
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user) {
        String decodedProjectId = CodeDecodeId.idDecoder(projectId);
        UUID project = UUID.fromString(decodedProjectId);
        projectService.deleteProjectById(project);
        List<UserDto> projects = customUserDetailsService.getProjectsByUserId(user.getIdentifier());
        model.addAttribute("projectList", projects);
        model.addAttribute("projectId", projectId);
        return new ModelAndView("redirect:/my-projects", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/assign-members")
    public ModelAndView openMyProject(
            @PathVariable(value = "projectId") String projectId,
            @ModelAttribute("model") ModelMap model) {
        enrichAssignToProjectMethods(projectId, model);
        return new ModelAndView("/pages/projects/assign_user_to_project", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/assign-members")
    public String addUserToProject(
            @PathVariable(value = "projectId") String project,
            @RequestParam(value = "userName") @NotBlank(message = "Username can't be blank") String user,
            @ModelAttribute("model") ModelMap model) {
        String decodedProjectId = CodeDecodeId.idDecoder(project);
        UserEntity newUser = new UserEntity();
        newUser.setUserName(user);
        ProjectDto projectDto = ProjectDto.builder()
                .id(decodedProjectId)
                .user(newUser)
                .build();
        projectService.assignUserToProject(projectDto);
        enrichAssignToProjectMethods(project, model);
        return "/pages/projects/assign_user_to_project";
    }

    @PostMapping(value = "/my-projects/{projectId}/delete/{userId}")
    public ModelAndView deleteUserFromProject(
            @PathVariable(value = "projectId") String project,
            @PathVariable(value = "userId") String user,
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity authUser) {
        UUID projectId = decodeProjectId(project);
        UUID userId = decodeUserId(user);
        if (Objects.equals(authUser.getIdentifier(), userId)) {
            String error = "You can't remove yourself :)";
            enrichDeleteUserFromProjectMethods(project, model);
            model.addAttribute("error", error);
            return new ModelAndView("/pages/projects/assign_user_to_project", model);
        }
        projectService.deleteUserFromProject(projectId, userId);
        enrichDeleteUserFromProjectMethods(project, model);
        return new ModelAndView("redirect:/my-projects/{projectId}/assign-members", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @PathVariable("projectId") String project){
        enrichCreateNewTaskMethods(model, project, user);
        return new ModelAndView("/pages/tasks/tasks_main_page", model); }

    @PostMapping(value = "/my-projects/{projectId}/tasks")
    public ModelAndView createNewTask(
            @PathVariable("projectId") String project,
            @ModelAttribute("model") ModelMap model,
            @AuthenticationPrincipal UserEntity user,
            @RequestParam("taskName") @Valid String taskName,
            @RequestParam("taskStatus") String taskStatus,
            @RequestParam("taskTimeEnd") @NotBlank(message = "Task end time is mandatory!") String taskTimeEnd,
            @RequestParam("taskPriority") String taskPriority) {
        UUID projectId = decodeProjectId(project);
        ProjectEntity projectTask = new ProjectEntity();
        StatusEntity status = new StatusEntity();
        PriorityEntity priority = new PriorityEntity();
        priority.setPriorityId(taskPriority);
        status.setStatusId(taskStatus);
        projectTask.setProjectId(projectId);
        TaskDto taskDto = TaskDto.builder()
                .taskName(taskName)
                .user(user)
                .project(projectTask)
                .status(status)
                .endTime(taskTimeEnd)
                .priority(priority)
                .build();
        projectService.addNewTask(taskDto);
        enrichCreateNewTaskMethods(model, project, user);
        return new ModelAndView("/pages/tasks/tasks_main_page", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-name")
    public ModelAndView updateTaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task) {
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-name")
    public ModelAndView updateTaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskName") @Valid String taskName) {
        String decodedTaskId = CodeDecodeId.idDecoder(task);
        TaskDto taskDto = TaskDto.builder()
                .id(decodedTaskId)
                .taskName(taskName)
                .build();
        projectService.updateTaskFromDto(taskDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-status")
    public ModelAndView updateTaskStatus(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskStatus") String taskStatus) {
        String decodedTaskId = CodeDecodeId.idDecoder(task);
        TaskDto taskDto = TaskDto.builder()
                .id(decodedTaskId)
                .statusTask(taskStatus)
                .build();
        projectService.updateTaskFromDto(taskDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-end-time")
    public ModelAndView updateTaskTimeEnd(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task) {
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-end-time")
    public ModelAndView updateTaskTimeEnd(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskTimeEnd") @NotBlank(message = "Task end time is mandatory!") String taskTimeEnd) {
        String decodedTaskId = CodeDecodeId.idDecoder(task);
        TaskDto taskDto = TaskDto.builder()
                .id(decodedTaskId)
                .endTime(taskTimeEnd)
                .build();
        projectService.updateTaskFromDto(taskDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/edit-priority")
    public ModelAndView updateTaskPriority(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("taskPriority") String taskPriority) {
        String decodedTaskId = CodeDecodeId.idDecoder(task);
        TaskDto taskDto = TaskDto.builder()
                .id(decodedTaskId)
                .priorityTask(taskPriority)
                .build();
        projectService.updateTaskFromDto(taskDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}")
    public ModelAndView getMoreInfoOfTask(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @ModelAttribute("model") ModelMap model) {
        UUID taskId = decodeTaskId(task);
        List<TransactionDto> transactions = transactionService.getTransactionsByTaskId(taskId);
        List<SubtaskDto> subtasks = projectService.findSubtasksByTaskId(taskId);
        List<TaskDto> tasks = projectService.findTaskByTaskId(taskId);
        model.addAttribute("transactionList", transactions);
        model.addAttribute("taskList", tasks);
        model.addAttribute("subtaskList", subtasks);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("/pages/tasks/more_info_about_project", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-transaction")
    public ModelAndView addNewTransactions(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @ModelAttribute("model") ModelMap model) {
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("/pages/tasks/add_transaction", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-transaction")
    public ModelAndView addNewTransactions(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("transactionName") @Valid String transactionName,
            @RequestParam("transactionMoneyFlow") @NotBlank(message = "Transaction money flow is mandatory!") String transactionMoneyFlow) {
        TaskEntity taskEntity = addIdToTask(task);
        TransactionDto transactionDto = TransactionDto.builder()
                .transactionName(transactionName)
                .transactionMoneyFlow(transactionMoneyFlow)
                .task(taskEntity)
                .build();
        transactionService.addNewTransaction(transactionDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("/pages/tasks/add_transaction", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-subtask")
    public ModelAndView addNewSubtask(
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @ModelAttribute("model") ModelMap model) {
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("/pages/subtasks/add_subtask", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/add-subtask")
    public ModelAndView addNewSubtask(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @RequestParam("subtaskName") @Valid String subtaskName,
            @RequestParam("endTime") @NotBlank(message = "Subtask end time is mandatory!") String endTime) {
        TaskEntity taskEntity = addIdToTask(task);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .task(taskEntity)
                .subtaskName(subtaskName)
                .endTime(endTime)
                .build();
        projectService.addNewSubtask(subtaskDto);
        enrichModelByProjectAndTask(model, project, task);
        return new ModelAndView("/pages/subtasks/add_subtask", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-name")
    public ModelAndView updateSubtaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask) {
        enrichModelByProjectAndTaskAndSubtask(model, project, task, subtask);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-name")
    public ModelAndView updateSubtaskName(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask,
            @RequestParam("subtaskName") @Valid String subtaskName) {
        String decodedSubtaskId = CodeDecodeId.idDecoder(subtask);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .id(decodedSubtaskId)
                .subtaskName(subtaskName)
                .build();
        projectService.updateSubtaskFromDto(subtaskDto);
        enrichModelByProjectAndTaskAndSubtask(model, project, task, subtask);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @GetMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-end-time")
    public ModelAndView updateSubtaskTimeTill(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask) {
        enrichModelByProjectAndTaskAndSubtask(model, project, task, subtask);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }

    @PostMapping(value = "/my-projects/{projectId}/tasks/{taskId}/{subtaskId}/edit-end-time")
    public ModelAndView updateSubtaskTimeTill(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask,
            @RequestParam("endTime") @NotBlank(message = "Subtask end time is mandatory!") String endTime) {
        String decodedSubtaskId = CodeDecodeId.idDecoder(subtask);
        SubtaskDto subtaskDto = SubtaskDto.builder()
                .id(decodedSubtaskId)
                .endTime(endTime)
                .build();
        projectService.updateSubtaskFromDto(subtaskDto);
        enrichModelByProjectAndTaskAndSubtask(model, project, task, subtask);
        return new ModelAndView("redirect:/my-projects/{projectId}/tasks/{taskId}", model);
    }
}