package com.Ali.PharmacistsApp.Database.DataStore;


import com.Ali.PharmacistsApp.Database.ModelDB.Cart;

import java.util.List;

import io.reactivex.Flowable;

public interface ICartDataSource {
    Flowable<List<Cart>> getCartItem();

    Flowable<List<Cart>> getCartItemById(int cartItemId);

    int countCartItems();

    void emptyCart();

    void insertToCart(Cart...carts);

    void updateToCart(Cart...carts);

    void deleteToCart(Cart cart);

    float sumPrice();
}
