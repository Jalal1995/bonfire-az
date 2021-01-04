package com.bonfire.az.bonfireaz.repo;

import com.bonfire.az.bonfireaz.model.entity.PasswordResetToken;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String passwordResetToken);

    Optional<PasswordResetToken> findByUser(XUser user);

}
