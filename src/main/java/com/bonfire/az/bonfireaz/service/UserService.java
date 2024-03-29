package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.response.ErrorMessages;
import com.bonfire.az.bonfireaz.model.response.UserDto;
import com.bonfire.az.bonfireaz.exception.UserServiceException;
import com.bonfire.az.bonfireaz.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    public XUser findXUserByUserId(String userId) {
        return userRepo.findByUserId(userId)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    public XUser findXUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
    }

    public UserDto findByUserId(String userId) {
        XUser xuser = findXUserByUserId(userId);
        return mapper.map(xuser, UserDto.class);
    }


    public Optional<XUser> findOpUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public XUser register(String email, String password, String name) {
        return userRepo.save(new XUser(email, encoder.encode(password), name, new String[]{"USER"}));
    }

    public boolean isUserExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    public void changePassword(XUser user, String password) {
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
    }
}
