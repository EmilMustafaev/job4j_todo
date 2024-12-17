package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        taskService.create(task);
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        taskService.delete(id);
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task) {
        Task existingTask = taskService.findById(task.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + task.getId()));
        existingTask.setDone(true);
        taskService.update(existingTask);
        return "redirect:/tasks";
    }

    @GetMapping("/completed")
    public String getCompleted(Model model) {
        model.addAttribute("tasks", taskService.findCompleted());
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findNew());
        return "tasks/list";
    }

    @GetMapping("/{id}")
    public String getTaskDetails(@PathVariable("id") Integer id, Model model) {
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        model.addAttribute("task", task);
        return "tasks/details";
    }

    @GetMapping("/edit")
    public String editTaskForm(@RequestParam("id") Integer id, Model model) {
        Task task = taskService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID: " + id));
        model.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }
}

