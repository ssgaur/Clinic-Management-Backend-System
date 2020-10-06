package com.ectosense.nightowl.controller.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Generic API message
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomApiResponse
{
    @JsonProperty("status")
    private int status;
    @JsonProperty("message")
    private  String message;
    @JsonProperty("data")
    private Object data;
    @JsonProperty("errors")
    private Object errors;
}
