package org.example.ShoppingCart.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ShoppingCartRepository implements IShoppingCartRepository {
    private final ReactiveListOperations<String, String> productListOperations;

    public ShoppingCartRepository(@Qualifier("productListOperations") ReactiveListOperations<String, String> productListOperations) {
        this.productListOperations = productListOperations;
    }

    @Override
    public Mono<Long> addProductToShoppingCart(String userName, String productId) {
         return productListOperations.rightPush(userName, productId);
    }

    @Override
    public Mono<Long> removeProductFromShoppingCart(String userName, String productId) {
        return productListOperations.remove(userName, 1, productId);
    }
}
