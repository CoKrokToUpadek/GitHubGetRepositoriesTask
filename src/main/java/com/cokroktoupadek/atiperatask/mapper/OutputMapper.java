package com.cokroktoupadek.atiperatask.mapper;

import com.cokroktoupadek.atiperatask.domain.dto.response.github.repository.GitHubRepositoryDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.output.AppBranchDto;
import com.cokroktoupadek.atiperatask.domain.dto.response.output.AppRepositoryDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OutputMapper {

    public List<AppRepositoryDto> mapGitHubResponseToAppResponseDtoList(List<GitHubRepositoryDto> dto) {
        return dto.stream().map(this::mapGitHubResponseToAppResponseDto).collect(Collectors.toList());
    }

    private AppRepositoryDto mapGitHubResponseToAppResponseDto(GitHubRepositoryDto dto) {
        AppRepositoryDto output = new AppRepositoryDto();
        output.setRepositoryName(dto.getRepositoryName());
        output.setOwnerLogin(dto.getOwnerDto().getLogin());
        output.setBranchDtoList(new ArrayList<>());
        dto.getBranchDtoList().forEach(e -> {
            output.getBranchDtoList().add(new AppBranchDto(e.getCommitName(), e.getCommitDto().getCommitSha()));
        });
        return output;
    }

}
