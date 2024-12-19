package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.task.SimpleTaskService;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final SimpleTaskService taskService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        var savedTask = taskService.create(task);
        if (savedTask.isEmpty()) {
            model.addAttribute("errorMessage", "Не удалось сохранить задачу!");
            return "errors/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, Model model) {
        boolean isDeleted = taskService.delete(id);
        if (!isDeleted) {
            model.addAttribute("errorMessage", "Не удалось удалить задачу!");
            return "errors/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.updateDone(task);
        if (!isUpdated) {
            model.addAttribute("errorMessage", "Не удалось обновить задачу!");
            return "errors/error";
        }
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
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/details";
    }

    @GetMapping("/edit")
    public String editTaskForm(@RequestParam("id") Integer id, Model model) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task, Model model) {
        boolean isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("errorMessage", "Не удалось обновить задачу!");
            return "errors/error";
        }
        return "redirect:/tasks";
    }
}

