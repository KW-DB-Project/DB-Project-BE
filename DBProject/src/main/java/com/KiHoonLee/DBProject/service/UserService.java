package com.KiHoonLee.DBProject.service;
import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.UserDto;
import com.KiHoonLee.DBProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public IsSuccessDto saveUser(UserDto user) {
        try {
            IsSuccessDto isSuccessDto= userRepository.insertUser(user);
            return isSuccessDto;
        } catch (DuplicateKeyException e) {
            return new IsSuccessDto(false);
        }
    }
}
