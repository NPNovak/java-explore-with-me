package ru.practicum.main.service.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.service.dto.user.NewUserRequest;
import ru.practicum.main.service.dto.user.UserResponse;
import ru.practicum.main.service.dto.user.UserShortResponse;
import ru.practicum.main.service.model.User;

@Component
public class UserMapper {

    public User toUser(NewUserRequest newUserRequest) {
        User user = new User();
        if (newUserRequest.getEmail() != null) {
            user.setEmail(newUserRequest.getEmail());
        }
        if (newUserRequest.getName() != null) {
            user.setName(newUserRequest.getName());
        }
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        if (user != null) {
            userResponse.setId(user.getId());
            if (user.getEmail() != null) {
                userResponse.setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                userResponse.setName(user.getName());
            }
            userResponse.setFriends(user.getFriends());
        }
        return userResponse;
    }

    public UserShortResponse toUserShortResponse(User user) {
        UserShortResponse userShortResponse = new UserShortResponse();
        if (user != null) {
            userShortResponse.setId(user.getId());
            if (user.getName() != null) {
                userShortResponse.setName(user.getName());
            }
        }
        return userShortResponse;
    }
}
