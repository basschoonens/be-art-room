package nl.novi.theartroom.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String orderDate;

    private String orderStatus;

    private String paymentMethod;

    private double totalPrice;

    private String name;

    private String address;

    private String postalCode;

    private String city;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Artwork> artworks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;

    public Order() {
    }

    public Order(Long id, String orderNumber, String orderDate, String orderStatus, String paymentMethod, double totalPrice, String Address, List<Artwork> artworks) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.address = Address;
        this.artworks = artworks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Artwork> getArtworks() {
        return artworks;
    }

    public void setArtworks(List<Artwork> artworks) {
        this.artworks = artworks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
