package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.Answer;
import uz.pdp.codingbat.entity.Task;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.AnswerDto;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.repository.AnsweRepository;
import uz.pdp.codingbat.repository.TaskRepository;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnsweRepository answeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    public ApiResult add(AnswerDto answerDto) {
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalTask.isPresent() || !optionalUser.isPresent())
            return new ApiResult("Error", false);
        Answer answer = new Answer();

        answer.setTask(optionalTask.get());
        answer.setUser(optionalUser.get());
        answer.setText(answerDto.getText());
        answeRepository.save(answer);
        return new ApiResult("Successfully added", true);
    }

    public List<Answer> getAll() {
        List<Answer> all = answeRepository.findAll();
        return all;
    }

    public Answer getOneById(Integer id) {
        Optional<Answer> optionalAnswer = answeRepository.findById(id);
        if (!optionalAnswer.isPresent()) return null;
        return optionalAnswer.get();
    }

    public ApiResult delete(Integer id) {
        try {
            answeRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception r) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, AnswerDto answerDto) {
        Optional<Answer> optionalAnswer = answeRepository.findById(id);
        Optional<Task> optionalTask = taskRepository.findById(answerDto.getTaskId());
        Optional<User> optionalUser = userRepository.findById(answerDto.getUserId());
        if (!optionalTask.isPresent() || !optionalUser.isPresent() || !optionalAnswer.isPresent())
            return new ApiResult("Error", false);

        Answer answer = optionalAnswer.get();
        answer.setText(answerDto.getText());
        answer.setTask(optionalTask.get());
        answer.setUser(optionalUser.get());
        answeRepository.save(answer);
        return new ApiResult("Successfully edited", true);

    }
}
