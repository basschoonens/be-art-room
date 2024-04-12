package nl.novi.theartroom.models;

import jakarta.persistence.Entity;

@Entity
public class Drawing extends Artwork {

    private String drawingDrawType;
    private String drawingSurface;
    private String drawingMaterial;
    private Integer drawingDimensionsWidthInCm;
    private Integer drawingDimensionsHeightInCm;

    public Drawing() {
    }

    public Drawing(Long id, String title, String artist, String description, Integer dateCreated, Double galleryBuyingPrice, String edition, String imageUrl, String drawingDrawType, String drawingSurface, String drawingMaterial, Integer drawingDimensionsWidthInCm, Integer drawingDimensionsHeightInCm) {
        super(id, title, artist, description, dateCreated, galleryBuyingPrice, edition, imageUrl, "drawing");
        this.drawingDrawType = drawingDrawType;
        this.drawingSurface = drawingSurface;
        this.drawingMaterial = drawingMaterial;
        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
    }

    public String getDrawingDrawType() {
        return drawingDrawType;
    }

    public void setDrawingDrawType(String drawingDrawType) {
        this.drawingDrawType = drawingDrawType;
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
