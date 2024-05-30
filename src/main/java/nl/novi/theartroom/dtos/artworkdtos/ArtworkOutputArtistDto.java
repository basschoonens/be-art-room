package nl.novi.theartroom.dtos.artworkdtos;

import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.ArtworkImage;

import java.time.LocalDate;
import java.util.List;

public class ArtworkOutputArtistDto {

    private Long id;
    private String title;
    private String artist;
    private String description;
    private LocalDate dateCreated;
    private String edition;
    private ArtworkImage image;
    private String artworkType;
    private List<RatingUserDto> ratings;
    private double averageRating;
    private double totalAmountOfRatings;
    private double galleryBuyingPrice;
    private double sellingPrice;
    private PaintingOutputDto paintingData;
    private DrawingOutputDto drawingData;


    public ArtworkOutputArtistDto() {
    }

    public ArtworkOutputArtistDto(Long id, String title, String artist, String description, LocalDate dateCreated, String edition, ArtworkImage image, String artworkType, List<RatingUserDto> ratings, double averageRating, double totalAmountOfRatings, double sellingPrice, PaintingOutputDto paintingData, DrawingOutputDto drawingData) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.dateCreated = dateCreated;
        this.edition = edition;
        this.image = image;
        this.artworkType = artworkType;
        this.ratings = ratings;
        this.averageRating = averageRating;
        this.totalAmountOfRatings = totalAmountOfRatings;
        this.sellingPrice = sellingPrice;
        this.paintingData = paintingData;
        this.drawingData = drawingData;
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public ArtworkImage getImage() {
        return image;
    }

    public void setImage(ArtworkImage image) {
        this.image = image;
    }

    public String getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(String artworkType) {
        this.artworkType = artworkType;
    }

    public List<RatingUserDto> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingUserDto> ratings) {
        this.ratings = ratings;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getTotalAmountOfRatings() {
        return totalAmountOfRatings;
    }

    public void setTotalAmountOfRatings(double totalAmountOfRatings) {
        this.totalAmountOfRatings = totalAmountOfRatings;
    }

    public double getGalleryBuyingPrice() {
        return galleryBuyingPrice;
    }

    public void setGalleryBuyingPrice(double galleryBuyingPrice) {
        this.galleryBuyingPrice = galleryBuyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public PaintingOutputDto getPaintingData() {
        return paintingData;
    }

    public void setPaintingData(PaintingOutputDto paintingData) {
        this.paintingData = paintingData;
    }

    public DrawingOutputDto getDrawingData() {
        return drawingData;
    }

    public void setDrawingData(DrawingOutputDto drawingData) {
        this.drawingData = drawingData;
    }
}
