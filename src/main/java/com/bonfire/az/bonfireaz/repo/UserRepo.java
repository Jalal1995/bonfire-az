package com.bonfire.az.bonfireaz.repo;

import com.bonfire.az.bonfireaz.model.entity.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<XUser, Long> {

    Optional<XUser> findByEmail(String email);
    Optional<XUser> findByUserId(String userId);
}
