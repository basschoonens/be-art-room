package nl.novi.theartroom.model.artworks;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.model.users.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "paintings")
public class Painting extends Artwork {

    private String paintingSurface;
    private String paintingMaterial;
    private Integer paintingDimensionsWidthInCm;
    private Integer paintingDimensionsHeightInCm;

    public Painting() {
    }

    public Painting(String title, String artist, String description, LocalDate dateCreated, Double galleryBuyingPrice, String edition, ArtworkImage artworkImage, String artworkType, List<Rating> ratings, User user, Set<Order> orders, String paintingSurface, String paintingMaterial, Integer paintingDimensionsWidthInCm, Integer paintingDimensionsHeightInCm) {
        super(title, artist, description, dateCreated, galleryBuyingPrice, edition, artworkImage, artworkType, ratings, user, orders);
        this.paintingSurface = paintingSurface;
        this.paintingMaterial = paintingMaterial;
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
    }

    public String getPaintingSurface() {
        return paintingSurface;
    }

    public void setPaintingSurface(String paintingSurface) {
        this.paintingSurface = paintingSurface;
    }

    public String getPaintingMaterial() {
        return paintingMaterial;
    }

    public void setPaintingMaterial(String paintingMaterial) {
        this.paintingMaterial = paintingMaterial;
    }

    public Integer getPaintingDimensionsWidthInCm() {
        return paintingDimensionsWidthInCm;
    }

    public void setPaintingDimensionsWidthInCm(Integer paintingDimensionsWidthInCm) {
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
    }

    public Integer getPaintingDimensionsHeightInCm() {
        return paintingDimensionsHeightInCm;
    }

    public void setPaintingDimensionsHeightInCm(Integer paintingDimensionsHeightInCm) {
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
    }
}
