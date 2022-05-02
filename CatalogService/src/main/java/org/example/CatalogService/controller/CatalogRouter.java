package org.example.CatalogService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.CatalogService.entity.Product;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CatalogRouter {
    public static final String root = "/api/v1/catalog/";

    @RouterOperation(path = root, produces = {
            MediaType.APPLICATION_JSON_VALUE }, method = RequestMethod.PUT, beanClass = CatalogHandler.class, beanMethod = "updateProduct",
            operation = @Operation(operationId = "updateEmployee", responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Product.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid product ID"),
                    @ApiResponse(responseCode = "404", description = "Employee not found")}, parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id")}, requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = Product.class))))
    )
    @Bean
    RouterFunction<ServerResponse> composeCatalogRoutes(CatalogHandler catalogHandler) {
        return route(GET(root), catalogHandler::findAll)
                .andRoute(GET(root.concat("{id}")), catalogHandler::findById)
                .andRoute(POST(root), catalogHandler::createProduct)
                .andRoute(PUT(root), catalogHandler::updateProduct);
    }
}
