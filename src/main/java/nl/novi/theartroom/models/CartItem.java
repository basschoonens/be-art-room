package nl.novi.theartroom.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artwork_id")
    private Artwork artwork;

    private int quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;

}
