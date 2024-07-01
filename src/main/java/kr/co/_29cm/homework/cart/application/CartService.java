package kr.co._29cm.homework.cart.application;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.cart.domain.CartItem;
import kr.co._29cm.homework.cart.domain.CartRepository;
import kr.co._29cm.homework.cart.payload.CartItemDto;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.payload.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    /**
     * 장바구니에 상품을 추가한다.
     *
     * @param userId 유저 아이디
     * @param productNo 상품 번호
     * @param quantity 수량
     * */
    @Transactional
    public void addItem(String userId, String productNo, int quantity) {
        Cart cart = this.getOne(userId);
        cart.addItem(productNo, quantity);
    }

    /**
     * 장바구니를 비운다
     *
     * @param userId 유저 아이디
     * */
    @Transactional
    public void clearCart(String userId) {
        Cart cart = this.getOne(userId);
        cart.clear();
    }

    /**
     * 장바구니에 담긴 상품 목록을 조회한다.
     *
     * @param userId 유저 아이디
     * @return 장바구니에 담긴 상품 목록
     * */
    public List<CartItemDto> findItems(String userId) {
        Cart cart = this.getOne(userId);
        List<String> productNoList = cart.getItems().stream().map(CartItem::getProductNo).toList();
        List<ProductDto> productDtoList = this.productService.findList(productNoList);

        return cart.getItems().stream()
                .map((item) -> {
                    ProductDto productDto = productDtoList.stream().filter((p) -> p.productNo().equals(item.getProductNo()))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException(item.getProductNo()));
                    return new CartItemDto(productDto.productNo(), productDto.name(), item.getQuantity());
                })
                .toList();
    }

    /**
     * 장바구니를 가져온다. 없을시 생성한다.
     *
     * @param userId 유저 아이디
     * @return 장바구니
     * */
    @Transactional
    public Cart getOne(String userId) {
        Cart cart = this.cartRepository.findByUserId(userId)
                .orElse(null);
        if (cart == null) {
            cart = this.cartRepository.save(Cart.create(userId));
        }
        return cart;
    }

    /**
     * 장바구니에 상품이 존재하는 지 확인한다.
     *
     * @param userId 유저 아이디
     * @return 장바구니 상품 존재 여부
     * */
    public boolean existsCartItems(String userId) {
        Cart cart = this.getOne(userId);
        return !cart.isEmpty();
    }
}
