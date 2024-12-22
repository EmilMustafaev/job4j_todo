package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.priority.PriorityService;
import ru.job4j.todo.service.task.SimpleTaskService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final SimpleTaskService taskService;

    private final PriorityService priorityService;

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("errorMessage", "Вы должны быть авторизованы для просмотра задач!");
            return "errors/error";
        }
        List<Priority> priorities = priorityService.findAll();
        model.addAttribute("priorities", priorities);
        model.addAttribute("tasks", taskService.findAll(currentUser));
        return "tasks/list";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("errorMessage", "Вы должны быть авторизованы для создания задачи!");
            return "errors/error";
        }
        task.setUser(currentUser);
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
    public String getCompleted(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("errorMessage", "Вы должны быть авторизованы для просмотра задач!");
            return "errors/error";
        }
        model.addAttribute("tasks", taskService.findCompleted(currentUser));
        return "tasks/list";
    }

    @GetMapping("/new")
    public String getNew(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            model.addAttribute("errorMessage", "Вы должны быть авторизованы для просмотра задач!");
            return "errors/error";
        }
        model.addAttribute("tasks", taskService.findNew(currentUser));
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

