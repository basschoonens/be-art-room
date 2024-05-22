//package nl.novi.theartroom.controllers;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/cart-items")
//public class CartItemController {
//
//    private CartItemService cartItemService;
//
//    public CartItemController(CartItemService cartItemService) {
//        this.cartItemService = cartItemService;
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {
//        cartItemService.addItemToCart(cartItemDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<?> updateCartItemQuantity(@RequestBody CartItemDTO cartItemDTO) {
//        cartItemService.updateCartItemQuantity(cartItemDTO);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/remove")
//    public ResponseEntity<?> removeItemFromCart(@RequestBody CartItemDTO cartItemDTO) {
//        cartItemService.removeItemFromCart(cartItemDTO);
//        return ResponseEntity.ok().build();
//    }
//}