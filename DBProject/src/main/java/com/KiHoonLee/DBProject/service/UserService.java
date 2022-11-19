package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.table.IdPassword;
import com.KiHoonLee.DBProject.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //아이디와 비번을 통해 회원인지 확인
    public User isMember(IdPassword idPassword) {
        Boolean result = true;
        User user = new User();
        try {
            user = userRepository.findByIdAndPassword(idPassword);
            if (user.getPw().isEmpty()||user.getId().isEmpty()) result = false;
        } catch (EmptyResultDataAccessException e) {
            result = false;
        } finally {
            return user;//new IsSuccessDto(result);
        }
    }
    public IsSuccessDto saveUser(UserDto user) {
        try {
            IsSuccessDto isSuccessDto = userRepository.insertUser(user);
            return isSuccessDto;
        } catch (DuplicateKeyException e) {
            return new IsSuccessDto(false);
        }
    }
}
