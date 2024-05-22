package nl.novi.theartroom.models.orders;

import jakarta.persistence.*;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.User;

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

    public CartItem() {
    }

    public CartItem(Artwork artwork, int quantity, double price, User user) {
        this.artwork = artwork;
        this.quantity = quantity;
        this.price = price;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
