package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.repository.ProjectRepository;
import edu.cources.plannote.repository.SubtaskRepository;
import edu.cources.plannote.repository.TaskRepository;
import edu.cources.plannote.utils.DtoToEntity;
import edu.cources.plannote.utils.EntityToDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImplementation implements ProjectService{
    private final ProjectRepository projectRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public ProjectServiceImplementation(ProjectRepository projectRepository,
                                        SubtaskRepository subtaskRepository,
                                        TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }
//    @Override
//    public List<ProjectEntity> projectList() { return projectRepository.findAll(); }

    @Override
    public void createNewProject(ProjectDto projectDto) {
        ProjectEntity project = DtoToEntity.projectDtoToEntity(projectDto);
        projectRepository.save(project);
    }

    @Override
    public void addUserToProject(String userName, UUID projectId) {
        projectRepository.addUserToProject(userName, projectId);
    }

    //    @Override
//    public List<UUID> getProjectsByUserId(UUID id) {
//        return projectRepository.getProjectsByUserId(id);
//    }

    @Override
    public List<SubtaskEntity> subtaskList() {
        return subtaskRepository.findAll();
    }

    @Override
    public void addNewSubtask(SubtaskEntity subtask) { subtaskRepository.save(subtask); }

    @Override
    public void deleteSubtask(SubtaskEntity subtask) { subtaskRepository.delete(subtask); }

    @Override
    public void changeSubtaskName(UUID id, String newName) { subtaskRepository.changeSubtaskName(id, newName); }

    @Override
    public void changeSubtaskStartTime(UUID id, Instant newTime) { subtaskRepository.changeSubtaskStartTime(id, newTime); }

    @Override
    public void changeSubtaskEndTime(UUID id, Instant newTime) { subtaskRepository.changeSubtaskEndTime(id, newTime); }

    @Override
    public List<TaskDto> taskList() {
        return taskRepository.findAll().stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public void addNewTask(TaskDto task) {
        TaskEntity newTask = DtoToEntity.taskDtoToEntity(task);
        taskRepository.save(newTask); }

    @Override
    public void changeTaskName(UUID id, String newName) { taskRepository.changeTaskName(id, newName); }

    @Override
    public void changeTaskStatus(UUID id, StatusEntity newStatus) { taskRepository.changeTaskStatus(id, newStatus); }

    @Override
    public void changeTaskTimeFrom(UUID id, LocalDateTime newTime) { taskRepository.changeTaskTimeFrom(id, newTime); }

    @Override
    public void changeTaskTimeEnd(UUID id, LocalDateTime newTime) { taskRepository.changeTaskTimeEnd(id, newTime); }

    @Override
    public void changeTaskPriority(UUID id, PriorityEntity newPriority) { taskRepository.changeTaskPriority(id, newPriority); }

    @Override
    public List<TaskDto> findTasksByProjectId(UUID projectId) {
        return taskRepository.findTasksByProjectId(projectId).stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public List<TaskDto> findTasksByProjectIdAndUserId(UUID projectId, UUID userId) {
        return taskRepository.findTasksByProjectIdAndUserId(projectId, userId).stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public String getProjectNameById(UUID projectId) {
        return projectRepository.getProjectNameById(projectId);
    }

    //    @Override
//    public List<TaskDto> findTasksByProjectName(UUID userId, String projectName) {
//        return taskRepository.findTasksByProjectName(userId, projectName).stream()
//                .map(EntityToDto::taskEntityToDto)
//                .toList();
//    }
}
