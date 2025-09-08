package com.example.fa_request_app.fa;

// this is for DTO (Data Transfer Object -- Data Carrier, Decoupling) and validation in the FA Requests App
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FaRequest(
    @NotBlank(message = "Program name is required")
    String programName,

    @NotBlank(message = "Product name is required")
    String productName,

    @NotBlank(message = "Contact is required")
    String contact,

    @NotBlank(message = "JO Number is required")
    String joNumber,

    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", message="Use YYYY-MM-DD") String submissionDate,
    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", message="Use YYYY-MM-DD") String needByDate,

    @NotBlank(message = "Description is required")
    String description
){}
