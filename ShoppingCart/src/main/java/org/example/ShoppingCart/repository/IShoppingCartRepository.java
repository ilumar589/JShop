package org.example.ShoppingCart.repository;

import reactor.core.publisher.Mono;

public interface IShoppingCartRepository {
    Mono<Long> addProductToShoppingCart(String userName, String productId);
    Mono<Long> removeProductFromShoppingCart(String userName, String productId);
}
