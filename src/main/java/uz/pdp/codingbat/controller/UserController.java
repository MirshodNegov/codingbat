package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.UserDto;
import uz.pdp.codingbat.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResult> add(@Valid @RequestBody UserDto userDto) {
        ApiResult add = userService.add(userDto);
        if (add.isActive()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();
        if (!all.isEmpty()) {
            return ResponseEntity.status(200).body(all);
        }
        return ResponseEntity.status(204).body(all);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneById(@PathVariable Integer id) {

        User oneById = userService.getOneById(id);
        if (oneById != null) {
            return ResponseEntity.status(200).body(oneById);
        }
        return ResponseEntity.status(204).body(oneById);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Integer id) {
        ApiResult delete = userService.delete(id);
        if (delete.isActive()) {
            return ResponseEntity.status(200).body(delete);
        }
        return ResponseEntity.status(409).body(delete);

    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> edit(@Valid @PathVariable Integer id, @RequestBody UserDto userDto) {
        ApiResult edit = userService.edit(id, userDto);
        if (edit.isActive()) {
            return ResponseEntity.status(200).body(edit);
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
