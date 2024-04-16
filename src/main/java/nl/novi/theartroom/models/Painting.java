package nl.novi.theartroom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "paintings")
public class Painting extends Artwork {

    private String paintingPaintType;
    private String paintingSurface;
    private String paintingMaterial;
    private Integer paintingDimensionsWidthInCm;
    private Integer paintingDimensionsHeightInCm;

    public Painting() {
    }

    public Painting(Long id, String title, String artist, String description, Integer dateCreated, Double galleryBuyingPrice, String edition, String imageUrl, String artworkType, List<Rating> ratings, String paintingPaintType, String paintingSurface, String paintingMaterial, Integer paintingDimensionsWidthInCm, Integer paintingDimensionsHeightInCm) {
        super(id, title, artist, description, dateCreated, galleryBuyingPrice, edition, imageUrl, artworkType, ratings);
        this.paintingPaintType = paintingPaintType;
        this.paintingSurface = paintingSurface;
        this.paintingMaterial = paintingMaterial;
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
    }

    public String getPaintingPaintType() {
        return paintingPaintType;
    }

    public void setPaintingPaintType(String paintingPaintType) {
        this.paintingPaintType = paintingPaintType;
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
