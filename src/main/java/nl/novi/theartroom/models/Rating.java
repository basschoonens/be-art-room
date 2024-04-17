package nl.novi.theartroom.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artwork_id")
    Artwork artwork;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artlover_id", nullable = false)
//    private Artlover artlover;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artist_id", nullable = false)
//    private Artist artist;

    // Als je buiten de range van 1 - 5 gaat, krijg je een validation exception !!

    @Min(1) @Max(5)
    private int rating;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String commentText) {
        this.comment = commentText;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

}
