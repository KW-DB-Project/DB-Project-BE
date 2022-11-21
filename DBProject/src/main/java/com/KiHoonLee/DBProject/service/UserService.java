package com.KiHoonLee.DBProject.service;

import com.KiHoonLee.DBProject.dto.IsSuccessDto;
import com.KiHoonLee.DBProject.dto.user.*;
import com.KiHoonLee.DBProject.repository.UserRepository;
import com.KiHoonLee.DBProject.table.IdPassword;
import com.KiHoonLee.DBProject.table.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import com.KiHoonLee.DBProject.dto.UserDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //아이디와 비번을 통해 회원인지 확인
    public User isMember(IdPassword idPassword) {
        User user = new User();
        try {
            user = userRepository.findByIdAndPassword(idPassword);
        } catch (EmptyResultDataAccessException e) {
        } finally {
            return user;//new IsSuccessDto(result);
        }
    }
    //회원가입 처리 오류 구현
    public IsSuccessDto saveUser(UserDto user) {
        try {
            IsSuccessDto isSuccessDto = userRepository.insertUser(user);
            return isSuccessDto;
        } catch (DuplicateKeyException e) {
            return new IsSuccessDto(false);
        }
    }

    //예수금
    public DepositReceivedDto getDepositReceived(Map<String,String> body) {
        DepositReceivedDto depositReceivedDto = userRepository.findDepositReceived(body);
        return depositReceivedDto;
    }
    //관심종목
    public List<MyInterestDto> getMyInteresting(Map<String,String> body) {
        List<MyInterestDto> myInterestDto = userRepository.findMyInteresting(body);
        return myInterestDto;
    }
    //작성글
    public List<MyWritingDto> getMyWriting(Map<String,String> body) {
        List<MyWritingDto> myWritingDto = userRepository.findMyWriting(body);
        return myWritingDto;
    }

    //보유 주식
    public AllGainDto getMyStock(Map<String,String> body) {

        float rateOfReturn = userRepository.findRateOfReturn(body);
        int appraisalAmount = userRepository.findAppraisalAmount(body);
        List<MyStockDto> myStockDto= userRepository.findMyStockDto(body);

        AllGainDto allGainDto = new AllGainDto(rateOfReturn,appraisalAmount,myStockDto);
        return allGainDto;
    }


}
