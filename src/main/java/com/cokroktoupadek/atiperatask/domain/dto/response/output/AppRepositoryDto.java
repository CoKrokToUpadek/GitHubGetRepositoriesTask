package com.cokroktoupadek.atiperatask.domain.dto.response.output;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AppRepositoryDto {
    @JsonProperty("Repository Name")
    String repositoryName;

    @JsonProperty("Owner Login")
    String ownerLogin;

    @JsonProperty("Branches")
    List<AppBranchDto> branchDtoList;

}
