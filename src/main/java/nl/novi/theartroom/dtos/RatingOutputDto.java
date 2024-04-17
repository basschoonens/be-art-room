package nl.novi.theartroom.dtos;

public class RatingDto {

    private Long id;
    private Long artworkId;
    private Integer rating;
    private String comment;

    public RatingDto() {
    }

    public RatingDto(Long id, Long artworkId, Integer rating, String comment) {
        this.id = id;
        this.artworkId = artworkId;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
