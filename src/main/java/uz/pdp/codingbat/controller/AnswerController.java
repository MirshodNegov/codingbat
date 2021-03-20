package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Answer;
import uz.pdp.codingbat.payload.AnswerDto;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.service.AnswerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    @Autowired
    AnswerService answerService;

    @PostMapping
    public ResponseEntity<ApiResult> add(@Valid @RequestBody AnswerDto answerDto) {
        ApiResult add = answerService.add(answerDto);
        if (add.isActive()) {
            return ResponseEntity.status(202).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<Answer> getAll() {
        List<Answer> all = answerService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    public Answer getOneById(@PathVariable Integer id) {
        Answer oneById = answerService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Integer id) {
        ApiResult delete = answerService.delete(id);
        if (delete.isActive()) {
            return ResponseEntity.status(202).body(delete);
        }
        return ResponseEntity.status(409).body(delete);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> edit(@Valid @RequestBody AnswerDto answerDto, @PathVariable Integer id) {
        ApiResult edit = answerService.edit(id, answerDto);
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
