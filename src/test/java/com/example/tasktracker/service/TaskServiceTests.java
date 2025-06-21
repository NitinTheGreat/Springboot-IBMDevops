package com.example.tasktracker.service;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTests {

    private TaskRepository repo;
    private TaskService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(TaskRepository.class);
        service = new TaskService(repo);
    }

    @Test
    void testAddTask() {
        Task t = new Task(null, "Read a book", false);
        Task saved = new Task(1L, "Read a book", false);

        when(repo.save(t)).thenReturn(saved);

        Task result = service.addTask(t);

        assertEquals(saved, result);
        verify(repo, times(1)).save(t);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(
                new Task(1L, "A", false),
                new Task(2L, "B", true)
        );
        when(repo.findAll()).thenReturn(tasks);

        List<Task> result = service.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getDescription());
    }

    @Test
    void testMarkCompleted() {
        Task t = new Task(1L, "Wash car", false);
        when(repo.findById(1L)).thenReturn(Optional.of(t));
        when(repo.save(any(Task.class))).thenReturn(new Task(1L, "Wash car", true));

        Optional<Task> result = service.markCompleted(1L);

        assertTrue(result.isPresent());
        assertTrue(result.get().isCompleted());
        verify(repo, times(1)).save(t);
    }

    @Test
    void testDeleteTask() {
        doNothing().when(repo).deleteById(1L);

        service.deleteTask(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}
