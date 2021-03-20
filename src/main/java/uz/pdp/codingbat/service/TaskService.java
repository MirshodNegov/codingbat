package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Category;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.TaskDto;
import uz.pdp.codingbat.repository.CategoryRepository;
import uz.pdp.codingbat.repository.LanguageRepository;
import uz.pdp.codingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResult add(TaskDto taskDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent()) return new ApiResult("Category not found", false);
        Task task = new Task();
        task.setHint(taskDto.getHint());
        task.setCategory(optionalCategory.get());
        task.setMethod(taskDto.getMethod());
        task.setName(taskDto.getName());
        task.setSolution(taskDto.getSolution());
        task.setText(taskDto.getText());
        taskRepository.save(task);
        return new ApiResult("Successfully added", true);

    }

    public List<Task> getAll() {
        List<Task> all = taskRepository.findAll();
        return all;
    }

    public Task getOneById(Integer id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return null;
        return optionalTask.get();
    }

    public ApiResult delete(Integer id) {
        try {
            taskRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, TaskDto taskDto) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent()) return new ApiResult("Task not found", false);
        Optional<Category> optionalCategory = categoryRepository.findById(taskDto.getCategoryId());
        if (!optionalCategory.isPresent()) return new ApiResult("Category not found", false);

        Task task = optionalTask.get();
        task.setText(taskDto.getText());
        task.setSolution(taskDto.getSolution());
        task.setName(taskDto.getName());
        task.setCategory(optionalCategory.get());
        task.setHint(taskDto.getHint());
        task.setMethod(taskDto.getMethod());
        taskRepository.save(task);
        return new ApiResult("Successfully edited", true);

    }
}
