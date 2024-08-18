package ru.practicum.main.service.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.event.EventShortResponse;
import ru.practicum.main.service.dto.user.NewUserRequest;
import ru.practicum.main.service.dto.user.UserResponse;
import ru.practicum.main.service.model.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getUsers(List<Long> ids, int from, int size);

    List<EventShortResponse> getFriendRequests(long userId, long friendId);

    List<EventShortResponse> getFriendsRequests(long userId);

    @Transactional
    UserResponse addUser(NewUserRequest newUserRequest);

    @Transactional
    UserResponse addFriend(long userId, long friendId);

    @Transactional
    void deleteUser(long userId);

    @Transactional
    void deleteFriend(long userId, long friendId);

    User checkUser(long userId);
}
