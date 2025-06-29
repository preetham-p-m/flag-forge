package com.pmp.flag_forge.Service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pmp.flag_forge.Exception.Error.FlagForgeNotFoundException;
import com.pmp.flag_forge.Model.User;
import com.pmp.flag_forge.Model.UserDto;
import com.pmp.flag_forge.Model.UserHelper;
import com.pmp.flag_forge.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomerService customerService;

    public User create(UserDto userDto) {
        var customer = customerService.getById(userDto.getCustomerId());
        var user = UserHelper.toEntity(userDto, customer);
        return userRepository.save(user);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new FlagForgeNotFoundException("User not found for userId " + id));
    }
}
