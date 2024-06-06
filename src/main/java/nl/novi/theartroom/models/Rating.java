package nl.novi.theartroom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_username", "artwork_id"})
)
public class Rating {

    @Id
    @GeneratedValue
    private Long ratingId;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artlover_id", nullable = false)
//    private Artlover artlover;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artist_id", nullable = false)
//    private Artist artist;

    // TODO Koppeling user/artwork/rating
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artwork_id")
    @JsonIgnore
    Artwork artwork;

    @ManyToOne
    @JoinColumn(name = "user_username")
    private User user;


    // TODO Koppeling user/artwork/rating

    // Als je buiten de range van 1 - 5 gaat, krijg je een validation exception !!
    @Min(1) @Max(5)
    private int rating;

    private String comment;

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long id) {
        this.ratingId = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
