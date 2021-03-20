package uz.pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.codingbat.entity.User;
import uz.pdp.codingbat.payload.ApiResult;
import uz.pdp.codingbat.payload.UserDto;
import uz.pdp.codingbat.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ApiResult add(UserDto userDto) {
        boolean existsByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existsByEmail)
            return new ApiResult("User already exist", false);
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new ApiResult("Successfully added", true);
    }

    public List<User> getAll() {
        List<User> all = userRepository.findAll();
        return all;
    }

    public User getOneById(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) return null;
        return byId.get();
    }

    public ApiResult delete(Integer id) {
        try {
            userRepository.deleteById(id);
            return new ApiResult("Successfully deleted", true);
        } catch (Exception e) {
            return new ApiResult("Error in deleting", false);
        }
    }

    public ApiResult edit(Integer id, UserDto userDto) {

        boolean byEmailAndIdNot = userRepository.existsByEmailAndIdNot(userDto.getEmail(), id);
        if (byEmailAndIdNot)
            return new ApiResult("Email is already exist", false);

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new ApiResult("User not found", false);
        User user = optionalUser.get();
        user.setPassword(user.getPassword());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return new ApiResult("Successfully edited", true);
    }
}
