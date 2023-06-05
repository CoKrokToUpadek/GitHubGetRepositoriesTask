package com.cokroktoupadek.atiperatask.service;

import com.cokroktoupadek.atiperatask.client.CommunicationClient;
import com.cokroktoupadek.atiperatask.controller.ConsumerController;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.branch.GitHubBranchDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.branch.GitHubCommitDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.repository.GitHubOwnerDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.github.repository.GitHubRepositoryDto;
import com.cokroktoupadek.atiperatask.mapper.OutputMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;


@WebMvcTest(ConsumerController.class)
@Import({OutputMapper.class, GitHubRequestService.class})
public class ServiceTestSuite {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    CommunicationClient communicationClient;


    @Test
    void getUserGitHubRepositoriesTest() throws Exception {
        //given
        GitHubBranchDto branch = new GitHubBranchDto("testName", new GitHubCommitDto("testSha"));
        GitHubRepositoryDto obj = new GitHubRepositoryDto("testRepositoryName", new GitHubOwnerDto("testLogin"), false, new ArrayList<>());

        List<GitHubRepositoryDto> dtoList = new ArrayList<>();
        dtoList.add(obj);
        dtoList.add(obj);
        when(communicationClient.getUserRepositoriesWithForkStatus(Mockito.any())).thenReturn(List.copyOf(dtoList));
        when(communicationClient.checkForUser(Mockito.any())).thenReturn(new ResponseEntity<>(HttpStatusCode.valueOf(200)));
        when(communicationClient.getUserRepositoryBranches(Mockito.any(),Mockito.any())).thenReturn(List.of(branch,branch));
        //when & then
         MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/get_user_github_repositories")
                        .param("login", "testLogin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2))).andReturn();
        System.out.println(result.getResponse().getContentAsString());


    }
}
