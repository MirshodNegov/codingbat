package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Language;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.LanguageDto;
import uz.pdp.codingbat.service.LanguageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    LanguageService languageService;


    @PostMapping
    public ResponseEntity<ApiResult> add(@Valid @RequestBody LanguageDto languageDto) {
        ApiResult add = languageService.add(languageDto);
        if (add.isActive()) {
            return ResponseEntity.status(200).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<Language> getAll() {
        List<Language> all = languageService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    private Language getOneById(@PathVariable Integer id) {
        Language oneById = languageService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Integer id) {
        ApiResult delete = languageService.delete(id);
        if (delete.isActive()) {
            return ResponseEntity.status(200).body(delete);
        }
        return ResponseEntity.status(409).body(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> edit(@Valid @PathVariable Integer id, @RequestBody LanguageDto languageDto) {

        ApiResult edit = languageService.edit(id, languageDto);

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
