package org.sam.kiosk.cart.application.payload;

public record CartItemDto(String productNo, String productName, int quantity) {}
