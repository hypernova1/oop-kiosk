package org.sam.kiosk.product.application;

import org.sam.kiosk.product.application.payload.ProductQuantityDto;

import java.util.List;

public record StockRollbackEvent(List<ProductQuantityDto> productQuantities) {}
