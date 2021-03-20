package uz.pdp.codingbat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.codingbat.entity.Example;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.ExampleDto;
import uz.pdp.codingbat.service.ExampleService;

import java.util.List;

@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @Autowired
    ExampleService exampleService;


    @PostMapping
    public ResponseEntity<ApiResult> add(@RequestBody ExampleDto exampleDto) {
        ApiResult add = exampleService.add(exampleDto);
        if (add.isActive()) {
            return ResponseEntity.status(200).body(add);
        }
        return ResponseEntity.status(409).body(add);
    }

    @GetMapping
    public List<Example> getAll() {
        List<Example> all = exampleService.getAll();
        return all;
    }

    @GetMapping("/{id}")
    public Example getOneById(@PathVariable Integer id) {
        Example oneById = exampleService.getOneById(id);
        return oneById;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult> delete(@PathVariable Integer id) {
        ApiResult delete = exampleService.delete(id);
        if (delete.isActive()) {
            return ResponseEntity.status(200).body(delete);
        }
        return ResponseEntity.status(409).body(delete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult> edit(@PathVariable Integer id, @RequestBody ExampleDto exampleDto) {
        ApiResult edit = exampleService.edit(id, exampleDto);
        if (edit.isActive()) {
            return ResponseEntity.status(202).body(edit);
        }
        return ResponseEntity.status(409).body(edit);
    }
}

