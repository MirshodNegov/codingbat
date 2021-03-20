package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull(message = "name must not be empty")
    private String name;
    @NotNull(message = "text must not empty")
    private String text;
    @NotNull(message = "solution must not empty")
    private String solution;
    @NotNull(message = "hint must not empty")
    private String hint;
    private String method;
    private Integer categoryId;

}
