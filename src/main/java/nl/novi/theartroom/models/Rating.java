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

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artlover_id", nullable = false)
//    private Artlover artlover;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artist_id", nullable = false)
//    private Artist artist;

    // Als je buiten de range van 1 - 5 gaat, krijg je een validation exception !!

    @Min(1) @Max(5)
    private int stars;

    private String commentText;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int rating) {
        this.stars = rating;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
