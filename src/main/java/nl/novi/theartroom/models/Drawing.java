package nl.novi.theartroom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "drawings")
public class Drawing extends Artwork {

    private String drawingSurface;
    private String drawingMaterial;
    private Integer drawingDimensionsWidthInCm;
    private Integer drawingDimensionsHeightInCm;

    public Drawing() {
    }

//    public Drawing(Long id, String title, String artist, String description, LocalDate dateCreated, Double galleryBuyingPrice, String edition, String imageUrl, String artworkType, List<Rating> ratings, String drawingSurface, String drawingMaterial, Integer drawingDimensionsWidthInCm, Integer drawingDimensionsHeightInCm) {
//        super(id, title, artist, description, dateCreated, galleryBuyingPrice, edition, artworkType, ratings);
//        this.drawingSurface = drawingSurface;
//        this.drawingMaterial = drawingMaterial;
//        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
//        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
//    }

    public Drawing(Long id, String title, String artist, String description, LocalDate dateCreated, Double galleryBuyingPrice, String edition, String imagePath, ArtworkImage artworkImage, String artworkType, List<Rating> ratings, String drawingSurface, String drawingMaterial, Integer drawingDimensionsWidthInCm, Integer drawingDimensionsHeightInCm) {
        super(id, title, artist, description, dateCreated, galleryBuyingPrice, edition, artworkImage, artworkType, ratings);
        this.drawingSurface = drawingSurface;
        this.drawingMaterial = drawingMaterial;
        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
    }

    public String getDrawingSurface() {
        return drawingSurface;
    }

    public void setDrawingSurface(String drawingSurface) {
        this.drawingSurface = drawingSurface;
    }

    public String getDrawingMaterial() {
        return drawingMaterial;
    }

    public void setDrawingMaterial(String drawingMaterial) {
        this.drawingMaterial = drawingMaterial;
    }

    public Integer getDrawingDimensionsWidthInCm() {
        return drawingDimensionsWidthInCm;
    }

    public void setDrawingDimensionsWidthInCm(Integer drawingDimensionsWidthInCm) {
        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
    }

    public Integer getDrawingDimensionsHeightInCm() {
        return drawingDimensionsHeightInCm;
    }

    public void setDrawingDimensionsHeightInCm(Integer drawingDimensionsHeightInCm) {
        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
    }
}
