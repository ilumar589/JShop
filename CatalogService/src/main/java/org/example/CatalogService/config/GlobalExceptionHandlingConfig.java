package org.example.CatalogService.config;

import error.ApiError;
import error.ApiErrorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Configuration
public class GlobalExceptionHandlingConfig {

    @Bean
    WebFilter dataNotFoundToBadRequest() {
        return (exchange, next) -> next.filter(exchange)
                .onErrorResume(ApiErrorException.class, e -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.NOT_FOUND);
                    ApiError error = e.getError();
                    byte[] errorMsg = error.msg().getErrorMessage().getBytes(StandardCharsets.UTF_8); //TODO send the whole object and move to reusable function
                    DataBuffer errorMsgBuffer = response.bufferFactory().wrap(errorMsg);
                    return response.writeWith(Flux.just(errorMsgBuffer));
                });
    }

//    @Bean /TODO this doesn't work for error handling with functional endpoints
    public WebExceptionHandler exceptionHandler() {
        return (ServerWebExchange exchange, Throwable ex) -> {
          if (ex instanceof ApiErrorException) {

              ServerHttpResponse response = exchange.getResponse();
              ApiError error = ((ApiErrorException) ex).getError();

              switch (error.status()) {
                  case "NOT_FOUND" -> response.setStatusCode(HttpStatus.NOT_FOUND);
                  case "BAD_REQUEST" -> response.setStatusCode(HttpStatus.BAD_REQUEST);
                  default -> {
                      return Mono.error(ex);
                  }
              }

              byte[] errorMsg = error.msg().getErrorMessage().getBytes(StandardCharsets.UTF_8);
              DataBuffer errorMsgBuffer = response.bufferFactory().wrap(errorMsg);
              return response.writeWith(Flux.just(errorMsgBuffer));
          }

          return Mono.error(ex);
        };
    }
}
