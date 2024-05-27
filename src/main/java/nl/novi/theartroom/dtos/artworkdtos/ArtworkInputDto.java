package nl.novi.theartroom.dtos.artworkdtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class ArtworkInputDto {

    private Long id;

    @NotBlank(message = "Title is required.")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters.")
    private String title;

    @NotBlank(message = "Artist is required.")
    @Size(min = 2, max = 255, message = "Artist must be between 2 and 255 characters.")
    private String artist;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotBlank(message = "Date created is required.")
    private LocalDate dateCreated;

    @NotBlank(message = "Gallery buying price is required.")
    @DecimalMin(value = "0.0", message = "Gallery buying price must be a positive number.")
    private Double galleryBuyingPrice;

    @NotBlank(message = "Please tell us if this is a single, limited or reproduction edition.")
    private String edition;
    private String imageUrl;
    private String artworkType;

    // Painting fields
    private String paintingPaintType;
    private String paintingSurface;
    private String paintingMaterial;
    private Integer paintingDimensionsWidthInCm;
    private Integer paintingDimensionsHeightInCm;

    // Drawing fields
    private String drawingDrawType;
    private String drawingSurface;
    private String drawingMaterial;
    private Integer drawingDimensionsWidthInCm;
    private Integer drawingDimensionsHeightInCm;


    public ArtworkInputDto() {
    }

    public ArtworkInputDto(Long id, String title, String artist, String description, LocalDate dateCreated, Double galleryBuyingPrice, String edition, String imageUrl, String artworkType, String paintingPaintType, String paintingSurface, String paintingMaterial, Integer paintingDimensionsWidthInCm, Integer paintingDimensionsHeightInCm, String drawingDrawType, String drawingSurface, String drawingMaterial, Integer drawingDimensionsWidthInCm, Integer drawingDimensionsHeightInCm) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.dateCreated = dateCreated;
        this.galleryBuyingPrice = galleryBuyingPrice;
        this.edition = edition;
        this.imageUrl = imageUrl;
        this.artworkType = artworkType;
        this.paintingPaintType = paintingPaintType;
        this.paintingSurface = paintingSurface;
        this.paintingMaterial = paintingMaterial;
        this.paintingDimensionsWidthInCm = paintingDimensionsWidthInCm;
        this.paintingDimensionsHeightInCm = paintingDimensionsHeightInCm;
        this.drawingDrawType = drawingDrawType;
        this.drawingSurface = drawingSurface;
        this.drawingMaterial = drawingMaterial;
        this.drawingDimensionsWidthInCm = drawingDimensionsWidthInCm;
        this.drawingDimensionsHeightInCm = drawingDimensionsHeightInCm;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(String artworkType) {
        this.artworkType = artworkType;
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
