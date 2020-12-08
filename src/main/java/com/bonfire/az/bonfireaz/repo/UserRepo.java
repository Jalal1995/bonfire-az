package com.bonfire.az.bonfireaz.repo;

import com.bonfire.az.bonfireaz.entity.db.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<XUser, Integer> {
    Optional<XUser> findByEmail(String email);

    Optional<XUser> findByUserId(String userId);
}
