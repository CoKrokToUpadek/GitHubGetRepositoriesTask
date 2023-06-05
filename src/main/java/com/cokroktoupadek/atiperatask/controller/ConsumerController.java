package com.cokroktoupadek.atiperatask.controller;

import com.cokroktoupadek.atiperatask.domain.dto.response.output.AppRepositoryDto;
import com.cokroktoupadek.atiperatask.errorhandlers.InternalApplicationException;
import com.cokroktoupadek.atiperatask.errorhandlers.NoUserFoundException;
import com.cokroktoupadek.atiperatask.errorhandlers.NotAcceptableException;
import com.cokroktoupadek.atiperatask.service.GitHubRequestService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ConsumerController {

    GitHubRequestService gitHubRequestService;


    @Autowired
    public ConsumerController(GitHubRequestService gitHubRequestService) {
        this.gitHubRequestService = gitHubRequestService;
    }

    @GetMapping("/get_user_github_repositories")
    public ResponseEntity<List<AppRepositoryDto>> getUserGitHubRepositories(@RequestParam String login, HttpServletRequest request) throws NoUserFoundException, InternalApplicationException, NotAcceptableException {
        return ResponseEntity.ok(gitHubRequestService.getUserGitHubRepositories(login, request));
    }
}
