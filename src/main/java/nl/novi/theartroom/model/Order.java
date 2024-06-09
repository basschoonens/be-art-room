package nl.novi.theartroom.model;

import jakarta.persistence.*;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.users.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private String orderNumber;

    //TODO Aanpassen naar LocalDate
    private String orderDate;

    private String orderStatus;

    private String paymentMethod;

    private double totalPrice;

    private String name;

    private String address;

    private String postalCode;

    private String city;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "order_artwork",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "artwork_id")
    )
    private Set<Artwork> artworks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "username", nullable = true)
    private User user;

    public Order() {
    }

    public Order(long orderId, String orderNumber, String orderDate, String orderStatus, String paymentMethod, double totalPrice, String name, String address, String postalCode, String city, Set<Artwork> artworks, User user) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.artworks = artworks;
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String shippingAddress) {
        this.address = shippingAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(Set<Artwork> artworks) {
        this.artworks = artworks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
