package com.cokroktoupadek.atiperatask.client.config;

import lombok.Getter;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
@Getter
public class EndpointConfig {

    @Value("${github.base_endpoint}")
    private String githubBaseEndpoint;

    @Value("${github.selection}")
    private String githubEndpointSelection;

    @Value("${github.filter_for_repos}")
    private String githubEndpointFilterForRepositories;

    @Value("${github.filter_for_branches}")
    private String githubEndpointFilterForBranches;
}
