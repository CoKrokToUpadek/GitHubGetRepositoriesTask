package com.cokroktoupadek.atiperatask.client;

import com.cokroktoupadek.atiperatask.client.config.EndpointConfig;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.branch.GitHubBranchDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.repository.GitHubRepositoryDto;
import com.cokroktoupadek.atiperatask.errorhandlers.InternalApplicationException;
import com.cokroktoupadek.atiperatask.errorhandlers.NoUserFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;


@Component
@AllArgsConstructor
public class CommunicationClient {

    private final RestTemplate restTemplate;

    private final EndpointConfig endpointConfig;

    public List<GitHubRepositoryDto> getUserRepositoriesWithForkStatus(String username) {
        URI url = buildUriForUserRepositoriesWithForkStatus(username);
        GitHubRepositoryDto[] requestDtoList = restTemplate.getForObject(url, GitHubRepositoryDto[].class);
        Optional<GitHubRepositoryDto[]> optionalRequestDtoList = Optional.ofNullable(requestDtoList);
        if (optionalRequestDtoList.isEmpty()) {
            return new ArrayList<>();
        } else return Arrays.asList(requestDtoList);

    }

    public ResponseEntity<String> checkForUser(String username) throws NoUserFoundException, InternalApplicationException {
        URI url = buildUriForUser(username);
        try {
            return restTemplate.getForEntity(url, String.class);
        } catch (RestClientException e) {
            HttpStatusCodeException exception = (HttpStatusCodeException) e;
            HttpStatusCode statusCode = exception.getStatusCode();
            if (statusCode.equals(HttpStatusCode.valueOf(404))) {
                throw new NoUserFoundException();
            } else throw new InternalApplicationException();
        }

    }

    public List<GitHubBranchDto> getUserRepositoryBranches(String username, String repositoryName) {
        URI url = buildUriForUserRepositoriesBranches(username, repositoryName);
        GitHubBranchDto[] requestDtoList = restTemplate.getForObject(url, GitHubBranchDto[].class);
        Optional<GitHubBranchDto[]> optionalRequestDtoList = Optional.ofNullable(requestDtoList);
        if (optionalRequestDtoList.isEmpty()) {
            return new ArrayList<>();
        } else return Arrays.asList(requestDtoList);
    }


    private URI buildUriForUserRepositoriesWithForkStatus(String username) {
        return UriComponentsBuilder.fromHttpUrl(endpointConfig.getGithubBaseEndpoint())
                .pathSegment(endpointConfig.getGithubEndpointSelection())
                .pathSegment(username)
                .pathSegment(endpointConfig.getGithubEndpointFilterForRepositories())
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForUser(String username) {
        return UriComponentsBuilder.fromHttpUrl(endpointConfig.getGithubBaseEndpoint())
                .pathSegment(endpointConfig.getGithubEndpointSelection())
                .pathSegment(username)
                .build()
                .encode()
                .toUri();
    }

    private URI buildUriForUserRepositoriesBranches(String username, String repositoryName) {
        return UriComponentsBuilder.fromHttpUrl(endpointConfig.getGithubBaseEndpoint())
                .pathSegment(endpointConfig.getGithubEndpointFilterForRepositories())
                .pathSegment(username)
                .pathSegment(repositoryName)
                .pathSegment(endpointConfig.getGithubEndpointFilterForBranches())
                .build()
                .encode()
                .toUri();
    }

}
