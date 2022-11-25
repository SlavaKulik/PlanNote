package edu.courses.plannote.utils;

import edu.courses.plannote.dto.TaskDto;
import edu.courses.plannote.dto.UserDto;
import edu.courses.plannote.entity.TaskEntity;
import edu.courses.plannote.entity.UserEntity;
import edu.courses.plannote.service.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class EnrichControllerMethods {

    public static ProjectService PROJECT_SERVICE;

    public EnrichControllerMethods(ProjectService projectService) {
        PROJECT_SERVICE = projectService;
    }

    public static void enrichAssignToProjectMethods(
            @PathVariable(value = "projectId") String projectId,
            @ModelAttribute("model") ModelMap model) {
        UUID id = decodeProjectId(projectId);
        List<UserDto> users = PROJECT_SERVICE.findUsersByProjectId(id);
        model.addAttribute("projectId", projectId);
        model.addAttribute("userList", users);
    }

    public static void enrichDeleteUserFromProjectMethods(
            @PathVariable(value = "projectId") String project,
            @ModelAttribute("model") ModelMap model) {
        UUID projectId = decodeProjectId(project);
        List<UserDto> users = PROJECT_SERVICE.findUsersByProjectId(projectId);
        model.addAttribute("userList", users);
        model.addAttribute("projectId", project);
    }

    public static void enrichCreateNewTaskMethods(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @AuthenticationPrincipal UserEntity user) {
        UUID projectId = decodeProjectId(project);
        List<TaskDto> tasks = PROJECT_SERVICE.findTasksByProjectIdAndUserId(projectId, user.getIdentifier());
        List<TaskDto> todoTasks = tasks.stream()
                .filter(task -> Objects.equals(task.getStatusTask(), "To do"))
                .toList();
        List<TaskDto> progressTasks = tasks.stream()
                .filter(task -> Objects.equals(task.getStatusTask(), "In-progress"))
                .toList();
        List<TaskDto> completedTasks = tasks.stream()
                .filter(task -> Objects.equals(task.getStatusTask(), "Complete"))
                .toList();
        String projectName = PROJECT_SERVICE.findProjectNameById(projectId);
        model.addAttribute("projectName", projectName);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("taskList", tasks);
        model.addAttribute("todoTasks", todoTasks);
        model.addAttribute("progressTasks", progressTasks);
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("projectId", project);
    }

    public static void enrichModelByProjectAndTask(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task) {
        model.addAttribute("projectId", project);
        model.addAttribute("taskId", task);
    }

    public static void enrichModelByProjectAndTaskAndSubtask(
            @ModelAttribute("model") ModelMap model,
            @PathVariable("projectId") String project,
            @PathVariable("taskId") String task,
            @PathVariable("subtaskId") String subtask) {
        model.addAttribute("projectId", project);
        model.addAttribute("taskId", task);
        model.addAttribute("subtasksId", subtask);
    }

    public static void enrichModelForAdmin(
            @ModelAttribute("model") ModelMap model,
            @PathVariable(value = "userId") String user) {
        model.addAttribute("userId", user);
    }

    public static TaskEntity addIdToTask(@PathVariable("taskId") String task) {
        TaskEntity taskEntity = new TaskEntity();
        UUID taskId = decodeTaskId(task);
        taskEntity.setTaskId(taskId);
        return taskEntity;
    }

    public static UUID decodeProjectId(@PathVariable(value = "projectId") String project) {
        String decodedProjectId = CodeDecodeId.idDecoder(project);
        return UUID.fromString(decodedProjectId);
    }

    public static UUID decodeUserId(@PathVariable(value = "userId") String user) {
        String decodedUserId = CodeDecodeId.idDecoder(user);
        return UUID.fromString(decodedUserId);
    }

    public static UUID decodeTaskId(@PathVariable("taskId") String task) {
        String decodedTaskId = CodeDecodeId.idDecoder(task);
        return UUID.fromString(decodedTaskId);
    }

}
