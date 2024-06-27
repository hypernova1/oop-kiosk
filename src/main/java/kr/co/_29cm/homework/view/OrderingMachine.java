package kr.co._29cm.homework.view;

import kr.co._29cm.homework.cart.domain.Cart;
import kr.co._29cm.homework.order.application.OrderService;
import kr.co._29cm.homework.product.application.ProductService;
import kr.co._29cm.homework.product.domain.ProductNotFoundException;
import kr.co._29cm.homework.product.domain.SoldOutException;
import kr.co._29cm.homework.product.payload.ProductDto;

import java.util.List;

public class OrderingMachine {

    private final ProductService productService;
    private final OrderService orderService;

    public OrderingMachine(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    public void run() {
        boolean exit = false;
        List<ProductDto> products = productService.getAll();
        OutputView.printProducts(products);
        Cart cart = new Cart();
        while (!exit) {
            try {
                String productNo = InputView.inputProductNo();
                int quantity = InputView.inputQuantity();

                validateInput(productNo, quantity);

                boolean completeOrder = productNo.isEmpty() && quantity == 0;
                if (!completeOrder) {
                    ProductDto product = this.productService.findOne(productNo);
                    cart.addProduct(product, quantity);
                    continue;
                }

                OutputView.printCartProducts(cart.getProducts());
                orderService.create(cart.getProducts());
                cart.clear();

                exit = InputView.isContinue();
            } catch (BadCommandException e) {
                OutputView.printBadInput();
            } catch (ProductNotFoundException | SoldOutException e) {
                OutputView.printException(e.getMessage());
            }
        }

        OutputView.thanksToCustomer();
    }


    public void validateInput(String productNo, int quantity) {
        boolean isValid = (productNo.isEmpty() && quantity == 0) || (!productNo.isEmpty() && quantity > 0);
        if (!isValid) {
            throw new BadCommandException();
        }
    }
}
