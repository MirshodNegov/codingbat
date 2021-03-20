package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.TaskDto;
import uz.pdp.codingbat.service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<ApiResult> add(@Valid @RequestBody TaskDto taskDto) {
        ApiResult add = taskService.add(taskDto);

        if (add.isActive()) {
            return ResponseEntity.status(202).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }


    @GetMapping
    public List<Task> getAll() {
        List<Task> all = taskService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    public Task getOneById(@PathVariable Integer id) {
        Task oneById = taskService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Integer id) {
        ApiResult delete = taskService.delete(id);
        if (delete.isActive()) {
            return ResponseEntity.status(202).body(delete);
        }
        return ResponseEntity.status(409).body(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> edit(@Valid @RequestBody TaskDto taskDto, @PathVariable Integer id) {
        ApiResult edit = taskService.edit(id, taskDto);
        if (edit.isActive()) {
            return ResponseEntity.status(202).body(edit);
        }
        return ResponseEntity.status(409).body(edit);
    }


    //for displaying validation message on console
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
