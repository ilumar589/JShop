package org.example.CatalogService.controller;

import error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.CatalogService.entity.Product;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.example.CatalogService.utils.CatalogUtils.root;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CatalogRouter {

    @RouterOperations(
            {
                    @RouterOperation(path = root,
                            produces = MediaType.APPLICATION_JSON_VALUE,
                            method = RequestMethod.GET, beanClass = CatalogHandler.class, beanMethod = "findAll",
                            operation = @Operation(operationId = "findAll",
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Product.class)))),
                                            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content(schema = @Schema(implementation = ApiError.class))),
                                            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ApiError.class)))

                                    })),
                    @RouterOperation(path = root,
                            produces = MediaType.APPLICATION_JSON_VALUE,
                            method = RequestMethod.PUT, beanClass = CatalogHandler.class, beanMethod = "updateProduct",
                            operation = @Operation(operationId = "updateProduct",
                                    responses = {
                                            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Product.class))),
                                            @ApiResponse(responseCode = "400", description = "Invalid product id", content = @Content(schema = @Schema(implementation = ApiError.class))),
                                            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(schema = @Schema(implementation = ApiError.class))),
                                            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content(schema = @Schema(implementation = ApiError.class)))
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "id")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Product.class))))
                    )
            }
    )
    @Bean
    RouterFunction<ServerResponse> composeCatalogRoutes(CatalogHandler catalogHandler) {
        return route(GET(root), catalogHandler::findAll)
                .andRoute(GET(root.concat("{id}")), catalogHandler::findById)
                .andRoute(POST(root), catalogHandler::createProduct)
                .andRoute(PUT(root), catalogHandler::updateProduct);
    }
}
