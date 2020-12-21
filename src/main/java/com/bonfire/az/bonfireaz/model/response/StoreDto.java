package com.bonfire.az.bonfireaz.model.response;

import lombok.Data;
import java.util.List;

@Data
public class StoreDto {

    private Long id;
    private String title;
    private String subtitle;
    private String about;
    private List<CampaignDto> campaignDtoList;
}
