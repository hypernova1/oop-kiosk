package kr.co._29cm.homework.cart.domain;

import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cart {

    private final List<CartProduct> cartProducts = new ArrayList<>();

    public List<CartProduct> getProducts() {
        return cartProducts;
    }

    /**
     * 장바구니 상품을 추가한다. 이미 존재하는 상품이라면 수량을 추가한다.
     *
     * @param productDto 상품 정보
     * @param quantity 주문 수량
     * */
    public void addProduct(ProductDto productDto, int quantity) {
        Optional<CartProduct> optionalCartProduct = findCartProductByProductDto(productDto);
        if (optionalCartProduct.isPresent()) {
            optionalCartProduct.get().addQuantity(quantity);
            return;
        }
        this.cartProducts.add(CartProduct.of(productDto, quantity));
    }

    /**
     * 상품이 이미 장바구니에 담겨있는지 찾는다.
     *
     * @param productDto 상품 정보
     * @return 동일한 상품
     * */
    private Optional<CartProduct> findCartProductByProductDto(ProductDto productDto) {
        return this.cartProducts.stream()
                .filter(cartProduct -> cartProduct.getProduct().equals(productDto))
                .findFirst();
    }

    /**
     * 장바구니를 비운다.
     * */
    public void clear() {
        this.cartProducts.clear();
    }

    /**
     * 장바구니가 비어있는지 확인한다.
     *
     * @return 장바구니 비어있음 여부
     * */
    public boolean isEmpty() {
        return this.cartProducts.isEmpty();
    }
}
