package com.cokroktoupadek.atiperatask.domain.dto.response.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppBranchDto {

    @JsonProperty("Branch name")
    String branchName;

    @JsonProperty("Last commit sha")
    String lastCommitSha;
}
