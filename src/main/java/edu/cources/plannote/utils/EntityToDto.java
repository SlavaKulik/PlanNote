package edu.cources.plannote.utils;
import edu.cources.plannote.dto.*;
import edu.cources.plannote.entity.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class EntityToDto {

    public static TaskDto taskEntityToDto(TaskEntity task) {
        return TaskDto.builder()
                .id(String.valueOf(task.getTaskId()))
                .taskName(task.getTaskName())
                .projectTask(task.getProjectTask().getProjectName())
                .userTask(task.getUserTask().getUsername())
                .statusTask(task.getTaskStatus().getStatusId())
                .endTime(String.valueOf(task.getTaskTimeEnd()).replace("T", " "))
                .priorityTask(task.getTaskPriority().getPriorityId())
                .transactions(task.getTransactions())
                .subtasks(task.getSubtasks())
                .sum(transactionsSum(task.getTransactions()))
                .build();
    }
    public static SubtaskDto subtaskEntityToDto(SubtaskEntity subtask) {
        return SubtaskDto.builder()
                .id(String.valueOf(subtask.getSubtaskId()))
                .subtaskName(subtask.getSubtaskName())
                .task(subtask.getTaskSubtask())
                .endTime(String.valueOf(subtask.getSubtaskTimeEnd()).replace("T", " "))
                .build();
    }

    public static TransactionDto transactionEntityToDto(TransactionEntity transaction) {
        return TransactionDto.builder()
                .transactionId(String.valueOf(transaction.getTransactionId()))
                .transactionName(transaction.getTransactionName())
                .transactionMoneyFlow(String.valueOf(transaction.getTransactionMoneyFlow()))
                .task(transaction.getTaskTransaction())
                .build();
    }
    public static UserDto userEntityToDto(UserEntity user) {
        return UserDto.builder()
                .id(String.valueOf(user.getIdentifier()))
                .userName(user.getUsername())
                .userPassword(user.getUserPassword())
                .userPosition(user.getUserPosition())
                .accountStatus(user.getAccountStatus())
                .userScore(user.getUserScore().getScore())
                .projects(user.getProjects())
                .projectName(getProjectNames(user))
                .projectId(getProjectsId(user))
                .tasks(user.getTasks())
                .build();
    }

    private static Iterator<ProjectEntity> getProjectEntityIterator(UserEntity user) {
        Set<ProjectEntity> projects = Set.copyOf(user.getProjects());
        Stream<ProjectEntity> stream = projects.stream();
        return stream.iterator();
    }

    private static List<String> getProjectNames(UserEntity user) {
        Iterator<ProjectEntity> iterator = getProjectEntityIterator(user);
        List<String> names = new ArrayList<>();
        while (iterator.hasNext()) {
            names.add(iterator.next().getProjectName());
        }

        return names;
    }

    private static List<UUID> getProjectsId(UserEntity user) {
        Iterator<ProjectEntity> iterator = getProjectEntityIterator(user);
        List<UUID> ids = new ArrayList<>();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getProjectId());
        }
        return ids;
    }

    private static String transactionsSum(List<TransactionEntity> transactions) {
        BigDecimal sum = BigDecimal.ZERO;
        if (Objects.nonNull(transactions)) {
            List<BigDecimal> numbers = new ArrayList<>();
            Stream<TransactionEntity> stream = transactions.stream();
            Iterator<TransactionEntity> iterator = stream.iterator();

            while (iterator.hasNext()) {
                numbers.add(iterator.next().getTransactionMoneyFlow());
            }
            for (BigDecimal number : numbers) {
                sum = sum.add(number);
            }
            return sum.toString();
        }
        return sum.toString();
    }

 }
