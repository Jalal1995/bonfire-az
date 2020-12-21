package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.response.ErrorMessages;
import com.bonfire.az.bonfireaz.model.response.UserDto;
import com.bonfire.az.bonfireaz.exception.UserServiceException;
import com.bonfire.az.bonfireaz.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;

    public XUser findXUserByUserId(String userId) {
        return userRepo.findByUserId(userId)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    public XUser findXUserByUserEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    public UserDto findByUserId(String userId) {
        XUser xuser = findXUserByUserId(userId);
        return mapper.map(xuser, UserDto.class);
    }
}
