package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Example;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.ExampleDto;
import uz.pdp.codingbat.repository.ExampleRepository;
import uz.pdp.codingbat.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleService {
    @Autowired
    ExampleRepository exampleRepository;
    @Autowired
    TaskRepository taskRepository;

    public ApiResult add(ExampleDto exampleDto) {
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent()) return new ApiResult("Task not found", false);
        Example example = new Example();
        example.setTask(optionalTask.get());
        example.setText(exampleDto.getText());
        exampleRepository.save(example);
        return new ApiResult("Successfully added", true);
    }

    public List<Example> getAll() {
        List<Example> all = exampleRepository.findAll();
        return all;
    }

    public Example getOneById(Integer id) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent()) return null;
        return optionalExample.get();
    }

    public ApiResult delete(Integer id) {
        try {
            exampleRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception r) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, ExampleDto exampleDto) {
        Optional<Example> optionalExample = exampleRepository.findById(id);
        if (!optionalExample.isPresent()) return new ApiResult("Example not found", false);
        Optional<Task> optionalTask = taskRepository.findById(exampleDto.getTaskId());
        if (!optionalTask.isPresent())
            return new ApiResult("Task not found", false);
        Example example = optionalExample.get();

        example.setText(exampleDto.getText());
        example.setTask(optionalTask.get());
        exampleRepository.save(example);
        return new ApiResult("Successfully edited", true);


    }
}
