package kr.co._29cm.homework.order.payload;

import java.util.List;

public record OrderRequest(List<OrderRequestItem> products, String userId) {}

