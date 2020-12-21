package com.bonfire.az.bonfireaz.repo;

import com.bonfire.az.bonfireaz.model.entity.Store;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepo extends JpaRepository<Store, Long> {
    List<Store> getAllByUser(XUser user);
}
