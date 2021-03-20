package uz.pdp.codingbat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

    @NotNull(message = "Text must not be empty")
    private String text;
    private Integer taskId;
    private Integer userId;
}
