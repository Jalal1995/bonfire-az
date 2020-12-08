package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.entity.db.XUser;
import com.bonfire.az.bonfireaz.entity.response.ErrorMessages;
import com.bonfire.az.bonfireaz.entity.response.UserDto;
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

    public UserDto findByUserId(String userId) {
        XUser xuser = userRepo.findByUserId(userId)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        return mapper.map(xuser, UserDto.class);
    }
}
