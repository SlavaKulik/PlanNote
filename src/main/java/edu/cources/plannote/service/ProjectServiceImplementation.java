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
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public void addNewSubtask(SubtaskDto subtask) {
        SubtaskEntity subtaskEntity = DtoToEntity.subtaskDtoToEntity(subtask);
        subtaskRepository.save(subtaskEntity); }


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
        String timeFrom = subtaskDto.getStartTime();
        String timeTill = subtaskDto.getEndTime();
        if (Objects.nonNull(name)) {
            subtask.setSubtaskName(name);
        }
        else if (Objects.nonNull(timeFrom)) {
            subtask.setSubtaskTimeStart(LocalDateTime.parse(timeFrom));
        }
        else
            subtask.setSubtaskTimeEnd(LocalDateTime.parse(timeTill));
        subtaskRepository.save(subtask);
    }

    @Override
    public void addNewTask(TaskDto task) {
        TaskEntity newTask = DtoToEntity.taskDtoToEntity(task);
        taskRepository.save(newTask);
    }

    @Override
    public void updateTaskFromDto(TaskDto taskDto) {
        TaskEntity task = taskRepository.getReferenceById(UUID.fromString(taskDto.getId()));
        String name = taskDto.getTaskName();
        String statusStr = taskDto.getStatusTask();
        String timeFrom = taskDto.getStartTime();
        String timeTill = taskDto.getEndTime();
        String priorityStr = taskDto.getPriorityTask();
        if (Objects.nonNull(name)) {
            task.setTaskName(name);
        } else if (Objects.nonNull(statusStr)) {
            StatusEntity status = new StatusEntity();
            status.setStatusId(statusStr);
            task.setTaskStatus(status);
        } else if (Objects.nonNull(timeFrom)) {
            task.setTaskTimeStart(LocalDateTime.parse(timeFrom));
        } else if (Objects.nonNull(timeTill)) {
            task.setTaskTimeEnd(LocalDateTime.parse(timeTill));
        } else {
            PriorityEntity priority = new PriorityEntity();
            priority.setPriorityId(priorityStr);
            task.setTaskPriority(priority);
        }
        taskRepository.save(task);
    }

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