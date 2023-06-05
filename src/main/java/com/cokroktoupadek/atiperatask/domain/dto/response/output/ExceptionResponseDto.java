package com.cokroktoupadek.atiperatask.domain.dto.response.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionResponseDto {

    @JsonProperty("status")
    Integer statusCode;

    @JsonProperty("Message")
    String message;

}
