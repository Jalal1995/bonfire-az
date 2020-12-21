package com.bonfire.az.bonfireaz.repo;

import com.bonfire.az.bonfireaz.model.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepo extends JpaRepository<Campaign, Long> {
}
