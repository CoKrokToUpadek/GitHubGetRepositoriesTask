package com.cokroktoupadek.atiperatask.service;

import com.cokroktoupadek.atiperatask.client.CommunicationClient;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.branch.GitHubBranchDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.repository.GitHubRepositoryDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.output.AppRepositoryDto;
import com.cokroktoupadek.atiperatask.errorhandlers.InternalApplicationException;
import com.cokroktoupadek.atiperatask.errorhandlers.NoUserFoundException;
import com.cokroktoupadek.atiperatask.errorhandlers.NotAcceptableException;
import com.cokroktoupadek.atiperatask.mapper.OutputMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GitHubRequestService {

    private final CommunicationClient communicationClient;

    private final OutputMapper outputMapper;

    private final String headerKey="Accept";
    private final String invalidKeyValue="application/xml";
    private final String validKeyValue="application/json";

    public List<AppRepositoryDto> getUserGitHubRepositories(String username, HttpServletRequest request) throws NoUserFoundException, InternalApplicationException, NotAcceptableException {
         HttpStatusCode statusCode = checkForGitHubUser(username);
        if (checkForHeader(request, headerKey, invalidKeyValue)) {
            throw new NotAcceptableException();
        } else if (checkForHeader(request, headerKey, validKeyValue)) {
          if (statusCode.equals(HttpStatusCode.valueOf(404))) {
                throw new NoUserFoundException();
            } else {
                List<GitHubRepositoryDto> repositoryDtoList = getUserRepositoriesWithForkStatus(username);
                repositoryDtoList = repositoryDtoList.stream().filter(e -> !e.getFork()).collect(Collectors.toList());

                repositoryDtoList.forEach(e -> {
                            List<GitHubBranchDto> branchListDto = getGitHubRepositoryBranches(e.getOwnerDto().getLogin(), e.getRepositoryName());
                            e.setBranchDtoList(branchListDto);
                        }
                );
                return outputMapper.mapGitHubResponseToAppResponseDtoList(repositoryDtoList);
            }
        } else {
            throw new InternalApplicationException();
        }

    }

    private List<GitHubRepositoryDto> getUserRepositoriesWithForkStatus(String username){
        return communicationClient.getUserRepositoriesWithForkStatus(username);
    }

    private HttpStatusCode checkForGitHubUser(String username) throws NoUserFoundException, InternalApplicationException {
        return communicationClient.checkForUser(username).getStatusCode();
    }

    private List<GitHubBranchDto> getGitHubRepositoryBranches(String username, String repositoryName) {
        return communicationClient.getUserRepositoryBranches(username, repositoryName);
    }

    private boolean checkForHeader(HttpServletRequest request, String headerKey, String headerValue) {
        List<String> headers = Collections.list(request.getHeaderNames());
        for (String header : headers) {
            if (header.equalsIgnoreCase(headerKey) && request.getHeader(headerKey).equalsIgnoreCase(headerValue)) {
                return true;
            }
        }
        return false;
    }
}

