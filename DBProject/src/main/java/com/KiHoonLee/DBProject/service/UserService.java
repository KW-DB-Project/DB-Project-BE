package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.table.IdPassword;
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
    public IsSuccessDto isMember(IdPassword idPassword) {
        Boolean result = true;
        try {
            IdPassword isMember = userRepository.findByIdAndPassword(idPassword);
            if (isMember.getPw().isEmpty()||isMember.getId().isEmpty()) result = false;
        } catch (EmptyResultDataAccessException e) {
            result = false;
        } finally {
            return new IsSuccessDto(result);
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
