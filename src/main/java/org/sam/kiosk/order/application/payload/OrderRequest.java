package org.sam.kiosk.order.application.payload;

import java.util.List;

public record OrderRequest(List<OrderRequestItem> products, String userId) {}

