//package nl.novi.theartroom.services;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class CartItemService {
//
//}
//    @Autowired
//    private HttpSession httpSession; // Inject HttpSession for session management
//
//    public void addItemToCart(CartItemDTO cartItemDTO) {
//        // Retrieve or create cartItems list in the session
//        List<CartItemDTO> cartItems = (List<CartItemDTO>) httpSession.getAttribute("cartItems");
//        if (cartItems == null) {
//            cartItems = new ArrayList<>();
//            httpSession.setAttribute("cartItems", cartItems);
//        }
//
//        // Add the new item to the cart
//        cartItems.add(cartItemDTO);
//    }