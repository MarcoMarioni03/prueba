package com.marco.marioni.controllers.interfaces;

import com.marco.marioni.domain.dtos.ShipDTO;
import com.marco.marioni.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ship API", description = "API for managing Ships")
public interface ShipRestApi {
    String BASE_URL = "/ships";

    @Operation(
            summary = "Find a Ship by Id",
            description = "Retrieves an existing Ship by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ship Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @GetMapping(path = BASE_URL + "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    ShipDTO findById(@PathVariable Integer id);

    @Operation(
            summary = "Save a new Ship",
            description = "Creates and saves a new Ship. Requires a null value in id field.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Ship created successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @PostMapping(path = BASE_URL, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ShipDTO save(@RequestBody ShipDTO shipDTO);

    @Operation(
            summary = "Update an existing Ship",
            description = "Updates an existing Ship.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ship updated successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @PutMapping(path = BASE_URL, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    ShipDTO update(@RequestBody ShipDTO shipDTO);

    @Operation(
            summary = "Delete an existing Ship",
            description = "Deletes an existing Ship.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ship deleted successfully", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @DeleteMapping(path = BASE_URL + "/{id}")
    Integer deleteById(@PathVariable Integer id);

    @Operation(
            summary = "Retrieve a list of Ships",
            description = "Retrieves a list of Ships based on 1-indexed pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ships Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @GetMapping(path = BASE_URL + "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ShipDTO> findAll(
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    );

    @Operation(
            summary = "Retrieve a list of Ships based on name",
            description = "Retrieves a list of Ships whose name contains the provided value, based on 1-indexed pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ships Found", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ShipDTO.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    })
            }
    )
    @GetMapping(path = BASE_URL + "/contains-name", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ShipDTO> findByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "1") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    );
}
