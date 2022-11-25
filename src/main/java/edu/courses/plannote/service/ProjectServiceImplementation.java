package edu.courses.plannote.service;

import edu.courses.plannote.dto.ProjectDto;
import edu.courses.plannote.dto.SubtaskDto;
import edu.courses.plannote.dto.TaskDto;
import edu.courses.plannote.dto.UserDto;
import edu.courses.plannote.entity.*;
import edu.courses.plannote.repository.ProjectRepository;
import edu.courses.plannote.repository.SubtaskRepository;
import edu.courses.plannote.repository.TaskRepository;
import edu.courses.plannote.repository.UserRepository;
import edu.courses.plannote.utils.DtoToEntity;
import edu.courses.plannote.utils.EntityToDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProjectServiceImplementation implements ProjectService{

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImplementation.class);

    private final ProjectRepository projectRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ProjectServiceImplementation(ProjectRepository projectRepository,
                                        SubtaskRepository subtaskRepository,
                                        TaskRepository taskRepository,
                                        UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createNewProject(ProjectDto projectDto) {
        ProjectEntity project = DtoToEntity.projectDtoToEntity(projectDto);
        projectRepository.save(project);
        log.info("Added new project");
    }

    @Override
    public void assignUserToProject(ProjectDto projectDto) {
        ProjectEntity project = projectRepository.getReferenceById(UUID.fromString(projectDto.getId()));
        String userName = projectDto.getUser().getUsername();
        UserEntity newUser = userRepository.findByUserName(userName);
        Set<UserEntity> users = project.getUsers();
        users.add(newUser);
        project.setUsers(users);
        projectRepository.save(project);
        log.info("Added new user to project");
    }

    @Override
    public void addNewSubtask(SubtaskDto subtask) {
        SubtaskEntity subtaskEntity = DtoToEntity.subtaskDtoToEntity(subtask);
        subtaskRepository.save(subtaskEntity);
        log.info("Added new subtask");
    }


    @Override
    public List<SubtaskDto> findSubtasksByTaskId(UUID taskId) {
        TaskEntity task = new TaskEntity();
        task.setTaskId(taskId);
        return subtaskRepository.findSubtaskEntityByTaskSubtask(task).stream()
                .map(EntityToDto::subtaskEntityToDto)
                .toList();
    }


    @Override
    public void updateSubtaskFromDto(SubtaskDto subtaskDto) {
        SubtaskEntity subtask = subtaskRepository.getReferenceById(UUID.fromString(subtaskDto.getId()));
        String name = subtaskDto.getSubtaskName();
        String timeTill = subtaskDto.getEndTime();
        if (Objects.nonNull(name)) {
            subtask.setSubtaskName(name);
        }
        else
            subtask.setSubtaskTimeEnd(LocalDateTime.parse(timeTill));
        subtaskRepository.save(subtask);
        log.info("Subtask updated");
    }

    @Override
    public void addNewTask(TaskDto task) {
        TaskEntity newTask = DtoToEntity.taskDtoToEntity(task);
        taskRepository.save(newTask);
        log.info("Added new task");
    }

    @Override
    public List<TaskDto> findTaskByTaskId(UUID id) {
        return taskRepository.findTaskEntitiesByTaskId(id).stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public void updateTaskFromDto(TaskDto taskDto) {
        TaskEntity task = taskRepository.getReferenceById(UUID.fromString(taskDto.getId()));
        String name = taskDto.getTaskName();
        String statusStr = taskDto.getStatusTask();
        String timeTill = taskDto.getEndTime();
        String priorityStr = taskDto.getPriorityTask();
        if (Objects.nonNull(name)) {
            task.setTaskName(name);
        } else if (Objects.nonNull(statusStr)) {
            StatusEntity status = new StatusEntity();
            status.setStatusId(statusStr);
            task.setTaskStatus(status);
        } else if (Objects.nonNull(timeTill)) {
            task.setTaskTimeEnd(LocalDateTime.parse(timeTill));
        } else {
            PriorityEntity priority = new PriorityEntity();
            priority.setPriorityId(priorityStr);
            task.setTaskPriority(priority);
        }
        taskRepository.save(task);
        log.info("Task updated");
    }

    @Override
    public List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId) {
        return taskRepository.findTasksByProjectIdAndUserId(projectId, userId)
                .stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public String findProjectNameById(UUID projectId) {
        ProjectEntity project = projectRepository.findProjectByProjectId(projectId);
        return project.getProjectName();
    }

    @Override
    public List<UserDto> findUsersByProjectId(UUID projectId) {
        ProjectEntity project = projectRepository.findProjectByProjectId(projectId);
        Set<UserEntity> users = project.getUsers();
        return users.stream()
                .map(EntityToDto::userEntityToDto)
                .toList();
    }

    @Override
    public void deleteUserFromProject(UUID projectId, UUID userId) {
        projectRepository.deleteUserFromProject(projectId, userId);
        log.info("User deleted from project");
    }

    @Override
    public void deleteProjectById(UUID projectId) {
        projectRepository.deleteProjectEntityByProjectId(projectId);
        log.info("Project deleted");
    }
}