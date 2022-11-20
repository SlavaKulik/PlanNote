package edu.cources.plannote.service;

import edu.cources.plannote.dto.ProjectDto;
import edu.cources.plannote.dto.SubtaskDto;
import edu.cources.plannote.dto.TaskDto;
import edu.cources.plannote.entity.*;
import edu.cources.plannote.repository.ProjectRepository;
import edu.cources.plannote.repository.SubtaskRepository;
import edu.cources.plannote.repository.TaskRepository;
import edu.cources.plannote.utils.DtoToEntity;
import edu.cources.plannote.utils.EntityToDto;
import edu.cources.plannote.utils.SubtaskMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    @Override
    public void createNewProject(ProjectDto projectDto) {
        ProjectEntity project = DtoToEntity.projectDtoToEntity(projectDto);
        projectRepository.save(project);
    }

    @Override
    public void addUserToProject(String userName, UUID projectId) {
        projectRepository.addUserToProject(userName, projectId);
    }

    @Override
    public List<SubtaskEntity> subtaskList() {
        return subtaskRepository.findAll();
    }

    @Override
    public void addNewSubtask(SubtaskDto subtask) {
        SubtaskEntity subtaskEntity = DtoToEntity.subtaskDtoToEntity(subtask);
        subtaskRepository.save(subtaskEntity); }

    @Override
    public List<SubtaskDto> findSubtasksByTaskId(UUID taskId) {
        return subtaskRepository.findSubtasksByTaskId(taskId).stream()
                .map(EntityToDto::subtaskEntityToDto)
                .toList();
    }

    @Override
    public void changeSubtaskName(UUID id, String newName) { subtaskRepository.changeSubtaskName(id, newName); }

//    @Override
//    public void updateSubtaskNameFromDto(SubtaskDto subtaskDto) {
//        System.out.println("start");
//        Optional<SubtaskEntity> subtask = subtaskRepository.findById(UUID.fromString(subtaskDto.getId()));
//        SubtaskEntity subtask1 = DtoToEntity.subtaskDtoToEntity(subtaskDto);
//        subtask.get().setSubtaskName(subtask1.getSubtaskName());
//        subtaskRepository.save(subtask.get());
//        System.out.println("end");
//    }

    @Override
    public void updateSubtaskNameFromDto(SubtaskDto subtaskDto) {
        System.out.println("start");
        SubtaskEntity subtask = subtaskRepository.getReferenceById(UUID.fromString(subtaskDto.getId()));
        subtask.setSubtaskName(subtaskDto.getSubtaskName());
        subtaskRepository.save(subtask);
        System.out.println("end");
    }

    @Override
    public void changeSubtaskStartTime(UUID id, LocalDateTime newTime) { subtaskRepository.changeSubtaskStartTime(id, newTime); }

    @Override
    public void changeSubtaskEndTime(UUID id, LocalDateTime newTime) { subtaskRepository.changeSubtaskEndTime(id, newTime); }

    @Override
    public List<TaskDto> taskList() {
        return taskRepository.findAll().stream()
                .map(EntityToDto::taskEntityToDto)
                .toList();
    }

    @Override
    public void addNewTask(TaskDto task) {
        TaskEntity newTask = DtoToEntity.taskDtoToEntity(task);
        taskRepository.save(newTask);
    }

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

}