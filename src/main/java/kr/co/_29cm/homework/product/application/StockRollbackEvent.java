package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.payload.ProductQuantityDto;

import java.util.List;

public record StockRollbackEvent(List<ProductQuantityDto> productQuantities) {}
