package nl.novi.theartroom.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)
    private Artwork artwork;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artlover_id", nullable = false)
//    private Artlover artlover;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artist_id", nullable = false)
//    private Artist artist;

    @Column(nullable = false)
    private int stars;

    public Rating() {
    }

    public Rating(Long id, Artwork artwork, int stars) {
        this.id = id;
        this.artwork = artwork;
        this.stars = stars;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int rating) {
        this.stars = rating;
    }
}
