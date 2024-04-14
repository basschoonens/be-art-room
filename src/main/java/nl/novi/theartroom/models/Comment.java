package nl.novi.theartroom.models;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artwork_id", nullable = false)

    private Artwork artwork;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "artlover_id", nullable = false)
//    private Artlover artlover;

    @Column(nullable = false)
    private String commentText;

    public Comment() {
    }

    public Comment(Long id, Artwork artwork, String commentText) {
        this.id = id;
        this.artwork = artwork;
        this.commentText = commentText;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}