package com.m30.saphira.config.apiresponse;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ProblemDetail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Simplifies API documentation by bundling common GET responses.
@Target({ElementType.METHOD, ElementType.TYPE}  )
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resource(s) found successfully."),
        @ApiResponse(responseCode = "404", description = "Resource not found.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "500", description = "An unexpected server error occurred.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
})
public @interface GetResponses {
}
