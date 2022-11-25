package edu.courses.plannote.utils;
import edu.courses.plannote.dto.*;
import edu.courses.plannote.entity.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class EntityToDto {

    public static TaskDto taskEntityToDto(TaskEntity task) {
        return TaskDto.builder()
                .id(CodeDecodeId.idEncoder(valueOfString(task.getTaskId())))
                .taskName(task.getTaskName())
                .projectTask(task.getProjectTask().getProjectName())
                .userTask(task.getUserTask().getUsername())
                .statusTask(task.getTaskStatus().getStatusId())
                .endTime(replaceSign(valueOfString(task.getTaskTimeEnd())))
                .priorityTask(task.getTaskPriority().getPriorityId())
                .transactions(task.getTransactions())
                .subtasks(task.getSubtasks())
                .sum(transactionsSum(task.getTransactions()))
                .build();
    }
    public static SubtaskDto subtaskEntityToDto(SubtaskEntity subtask) {
        return SubtaskDto.builder()
                .id(CodeDecodeId.idEncoder(valueOfString(subtask.getSubtaskId())))
                .subtaskName(subtask.getSubtaskName())
                .task(subtask.getTaskSubtask())
                .endTime(replaceSign(valueOfString(subtask.getSubtaskTimeEnd())))
                .build();
    }

    public static TransactionDto transactionEntityToDto(TransactionEntity transaction) {
        return TransactionDto.builder()
                .transactionId(CodeDecodeId.idEncoder(valueOfString(transaction.getTransactionId())))
                .transactionName(transaction.getTransactionName())
                .transactionMoneyFlow(valueOfString(transaction.getTransactionMoneyFlow()))
                .task(transaction.getTaskTransaction())
                .build();
    }
    public static UserDto userEntityToDto(UserEntity user) {
        return UserDto.builder()
                .id(CodeDecodeId.idEncoder(valueOfString(user.getIdentifier())))
                .userName(user.getUsername())
                .userPassword(user.getUserPassword())
                .userPosition(user.getUserPosition())
                .accountStatus(user.getAccountStatus())
                .userScore(user.getUserScore().getScore())
                .projects(user.getProjects())
                .accStatus(user.getAccountStatus().getAccountStatusId())
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

    private static List<String> getProjectsId(UserEntity user) {
        Iterator<ProjectEntity> iterator = getProjectEntityIterator(user);
        List<UUID> ids = new ArrayList<>();
        while (iterator.hasNext()) {
            ids.add(iterator.next().getProjectId());
        }
        return ids.stream()
                .map(id -> CodeDecodeId.idEncoder(String.valueOf(id)))
                .toList();
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

    private static<T> String valueOfString(T t) {
        return String.valueOf(t);
    }

    private static String replaceSign(String time) {
        return time.replace("T", " ");
    }
 }
