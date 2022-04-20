package org.example.ShoppingCart.controller;

import dto.ApplicationResponse;
import lombok.RequiredArgsConstructor;
import org.example.ShoppingCart.repository.IShoppingCartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(path ="/api/v1/shopping-cart/")
public class ShoppingCartController {

    private final IShoppingCartRepository shoppingCartRepository;

    public Mono<ResponseEntity<ApplicationResponse<Boolean>>> sd() {
        return shoppingCartRepository
                .addProductToShoppingCart()
                .log()
                .map(indexOfProduct -> {

                });
    }
}
