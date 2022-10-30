package edu.cources.plannote.service;

import edu.cources.plannote.entity.ProjectEntity;
import edu.cources.plannote.entity.SubtaskEntity;
import edu.cources.plannote.entity.TaskEntity;
import edu.cources.plannote.repository.ProjectRepository;
import edu.cources.plannote.repository.SubtaskRepository;
import edu.cources.plannote.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    @Override
    public List<ProjectEntity> projectList() { return projectRepository.findAll(); }

    @Override
    public void createNewProject(ProjectEntity project) { projectRepository.save(project); }

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
    public List<TaskEntity> taskList() {
        return taskRepository.findAll();
    }

    @Override
    public void addNewTask(TaskEntity task) { taskRepository.save(task); }

    @Override
    public void deleteTask(TaskEntity task) { taskRepository.delete(task); }

    @Override
    public void changeTask(UUID id, String newName) { taskRepository.changeTask(id, newName); }
}
