package com.productivity.web.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LabelRequest {

    @NotBlank(message = "Label name cannot be blank")
    @Size(max = 100, message = "Label name must be less than or equal to 100 characters")
    private String name;

    @Size(max = 7, message = "Color hex must be less than or equal to 7 characters")
    private String colorHex;
}
