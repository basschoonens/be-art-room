package nl.novi.theartroom.models;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artworks")
@Inheritance(strategy = InheritanceType.JOINED)
public class Artwork {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String artist;
    private String description;
    private LocalDate dateCreated;
    private Double galleryBuyingPrice;
    private String edition;
    private String imagePath;
    private String artworkType;

    // Boolean forSale

    // TODO add a function to automatically see if a painting is square, portrait or landscape

//    public void setArtworkType() {
//        if (this.imageUrl != null) {
//            String[] dimensions = this.imageUrl.split("x");
//            if (dimensions.length == 2) {
//                int width = Integer.parseInt(dimensions[0]);
//                int height = Integer.parseInt(dimensions[1]);
//                if (width == height) {
//                    this.artworkType = "square";
//                } else if (width > height) {
//                    this.artworkType = "landscape";
//                } else {
//                    this.artworkType = "portrait";
//                }
//            }
//        }
//    }

    @OneToMany(mappedBy = "artwork")
    List<Rating> ratings = new ArrayList<>();

    public Artwork() {
    }

    public Artwork(
            Long id,
            String title,
            String artist,
            String description,
            LocalDate dateCreated,
            Double galleryBuyingPrice,
            String edition,
            String imagePath,
            String artworkType,
            List<Rating> ratings
    ) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.dateCreated = dateCreated;
        this.galleryBuyingPrice = galleryBuyingPrice;
        this.edition = edition;
        this.imagePath = imagePath;
        this.artworkType = artworkType;
        this.ratings = ratings;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imageUrl) {
        this.imagePath = imageUrl;
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


}
