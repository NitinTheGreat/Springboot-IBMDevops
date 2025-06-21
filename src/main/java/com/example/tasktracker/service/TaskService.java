package com.example.tasktracker.service;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Optional<Task> getTask(Long id) {
        return repo.findById(id);
    }

    public Task addTask(Task task) {
        return repo.save(task);
    }

    public void deleteTask(Long id) {
        repo.deleteById(id);
    }

    public Optional<Task> markCompleted(Long id) {
        Optional<Task> taskOpt = repo.findById(id);
        taskOpt.ifPresent(task -> {
            task.setCompleted(true);
            repo.save(task);
        });
        return taskOpt;
    }
}
