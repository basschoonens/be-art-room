package nl.novi.theartroom.model.artworks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.model.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artworks")
@Inheritance(strategy = InheritanceType.JOINED)
public class Artwork {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artwork_id_seq")
    @SequenceGenerator(name = "artwork_id_seq", sequenceName = "artwork_id_seq",initialValue = 1055, allocationSize = 1)
    private Long artworkId;
    private String title;
    private String artist;
    private String description;
    private LocalDate dateCreated;
    private Double galleryBuyingPrice;
    private String edition;
    @OneToOne(cascade = CascadeType.ALL)
    ArtworkImage artworkImage;

    private String artworkType;
    // Boolean forSale

    @OneToMany(mappedBy = "artwork", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Rating> ratings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = true)
    @JsonIgnore
    private User user;

    @ManyToMany(mappedBy = "artworks", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    public Artwork() {
    }

    public Artwork(String title, String artist, String description, LocalDate dateCreated, Double galleryBuyingPrice, String edition, ArtworkImage artworkImage, String artworkType, List<Rating> ratings, User user, Set<Order> orders) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.dateCreated = dateCreated;
        this.galleryBuyingPrice = galleryBuyingPrice;
        this.edition = edition;
        this.artworkImage = artworkImage;
        this.artworkType = artworkType;
        this.ratings = ratings;
        this.user = user;
        this.orders = orders;
    }

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long id) {
        this.artworkId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Double getGalleryBuyingPrice() {
        return galleryBuyingPrice;
    }

    public void setGalleryBuyingPrice(Double galleryBuyingPrice) {
        this.galleryBuyingPrice = galleryBuyingPrice;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public ArtworkImage getArtworkImage() {
        return artworkImage;
    }

    public void setArtworkImage(ArtworkImage artworkImage) {
        this.artworkImage = artworkImage;
    }

    public String getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(String artworkType) {
        this.artworkType = artworkType;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
