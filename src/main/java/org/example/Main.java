package org.example;

import org.example.models.Task;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        showTask(2);
        completeTask(2);
    }

    public static void listTasks() {
        try {
            Call<List<Task>> call = TDSClient.getTasksService().listTasks();
                Response<List<Task>> resp = call.execute();
                List<Task> tdsResponse = resp.body();
                if (tdsResponse != null) {
                    List<Task> tasks = tdsResponse;
                    for (Task task : tasks) {
                        System.out.println("Nome da tarefa: " + task.getTitle());
                        System.out.println("Situação: " + task.isCompleted());
                        System.out.println("");
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    public static void showTask(int taskId) {
        try {
            Call<Task> call = TDSClient.getTasksService().showTask(taskId);
            Response<Task> resp = call.execute();
            Task tdsResponse = resp.body();
            System.out.println("Id do usuario: " + tdsResponse.getUserId());
            System.out.println("Id da tarefa: " + tdsResponse.getId());
            System.out.println("Nome da tarefa: " + tdsResponse.getTitle());
            System.out.println("Situação: " + tdsResponse.isCompleted());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTask() {
        Task newTask = new Task("Estudar", 3);

        Call<Task> call = TDSClient.getTasksService().addTask(newTask);

        try {
            Response<Task> response = call.execute();

            if (response.isSuccessful()) {
                Task createdTask = response.body();
                System.out.println("Tarefa adicionada.");
                System.out.println("");
                System.out.println("Id do usuario: " + createdTask.getUserId());
                System.out.println("Id da tarefa: " + createdTask.getId());
                System.out.println("Nome da tarefa: " + createdTask.getTitle());
                System.out.println("Situação: " + createdTask.isCompleted());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void completeTask(int taskId) {
        try {
            Call<Task> call = TDSClient.getTasksService().showTask(2);
            Response<Task> resp = call.execute();
            Task existingTask = resp.body();

            if (existingTask != null && resp.isSuccessful()) {
                existingTask.markAsCompleted();

                Call<Task> updateCall = TDSClient.getTasksService().updateTask(taskId, existingTask);
                Response<Task> updateResp = updateCall.execute();

                if (updateResp.isSuccessful()) {
                    System.out.println("Id do usuario: " + existingTask.getUserId());
                    System.out.println("Id da tarefa: " + existingTask.getId());
                    System.out.println("Nome da tarefa: " + existingTask.getTitle());
                    System.out.println("Situação: " + existingTask.isCompleted());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void filterNonCompletedTasks() {
        try {
            Call<List<Task>> call = TDSClient.getTasksService().nonCompletedTasks();
            Response<List<Task>> resp = call.execute();
            List<Task> mtgResponse = resp.body();
            if (mtgResponse != null) {
                List<Task> tasks = mtgResponse;
                for (Task task : tasks) {
                    System.out.println("Nome da tarefa: " + task.getTitle());
                    System.out.println("Situação: " + task.isCompleted());
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
