package com.cokroktoupadek.atiperatask.domain.dto.response.github.repository;

import com.cokroktoupadek.atiperatask.domain.dto.response.github.branch.GitHubBranchDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepositoryDto {

    @JsonProperty("name")
    String repositoryName;

    @JsonProperty("owner")
    GitHubOwnerDto ownerDto;

    @JsonProperty("fork")
    Boolean fork;

    List<GitHubBranchDto> branchDtoList;


}
