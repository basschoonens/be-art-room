package nl.novi.theartroom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

// TODO Uitzoeken welke informatie waar wordt opgeslagen. In dto's of in models?
    // Waar moeten gegevens die niet direct op het object van een artwork slaan, zoals prijs, voorraad, datum post gecreerd, etc.

@Entity
@Table(name = "artworks")
public class Artwork {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String artist;
    private String description;
    private String type;
    private String dimensions;
    private Double buyingPrice;
    private Double sellingPrice;
    private Date dateCreated;
    private String edition;
    private String imageUrl;

    // Private String Category, Medium, dateModified, Datepublished, isFeatured, isAvailable, isSold,  ; toevoegen ??

    public Artwork() {
    }

    public Artwork(
            Long id,
            String title,
            String artist,
            String description,
            String type,
            String dimensions,
            Double buyingPrice,
            Double sellingPrice,
            Date dateCreated,
            String edition,
            String imageUrl
    ) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.type = type;
        this.dimensions = dimensions;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.dateCreated = dateCreated;
        this.edition = edition;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(Double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEdition() {
        return edition;

    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
