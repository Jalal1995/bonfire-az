package com.bonfire.az.bonfireaz.model.response;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {

    private String userId;
    private String name;
    private String email;
    private List<CampaignDto> campaigns;
    private List<StoreDto> stores;
}
