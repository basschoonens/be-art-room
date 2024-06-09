package nl.novi.theartroom.dto.orderdto;

import jakarta.validation.constraints.*;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;

import java.util.Set;

public class OrderOutputDto {

    private long orderId;

    @NotBlank(message = "Order number is mandatory")
    private String orderNumber;

    @NotBlank(message = "Order date is mandatory")
    private String orderDate;

    @NotBlank(message = "Order status is mandatory")
    private String orderStatus;

    @NotBlank(message = "Payment method is mandatory")
    private String paymentMethod;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be greater than 0")
    private double totalPrice;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not exceed 100 characters")
    private String name;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Postal code is mandatory")
    @Pattern(regexp = "\\d{4}[A-Z]{2}", message = "Postal code should be in the format 1234AB")
    private String postalCode;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotNull(message = "Artworks are mandatory")
    @Size(min = 1, message = "At least one artwork is required")
    private Set<ArtworkOutputUserDto> artworks;


    public OrderOutputDto() {
    }

    public OrderOutputDto(long orderId, String orderNumber, String orderDate, String orderStatus, String paymentMethod, double totalPrice, String name, String address, String postalCode, String city, Set<ArtworkOutputUserDto> artworks) {
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

    public void setAddress(String address) {
        this.address = address;
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

    public Set<ArtworkOutputUserDto> getArtworks() {
        return artworks;
    }

    public void setArtworks(Set<ArtworkOutputUserDto> artworks) {
        this.artworks = artworks;
    }
}