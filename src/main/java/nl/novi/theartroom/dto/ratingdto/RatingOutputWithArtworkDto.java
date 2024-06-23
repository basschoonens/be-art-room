package nl.novi.theartroom.dto.ratingdto;

public class RatingOutputWithArtworkDto {

    private Long ratingId;
    private Integer rating;
    private String comment;
    private Long artworkId;
    private String artworkTitle;
    private String artworkArtist;

    public RatingOutputWithArtworkDto() {
    }

    public RatingOutputWithArtworkDto(Long ratingId, Integer rating, String comment, Long artworkId, String artworkTitle, String artworkArtist) {
        this.ratingId = ratingId;
        this.rating = rating;
        this.comment = comment;
        this.artworkId = artworkId;
        this.artworkTitle = artworkTitle;
        this.artworkArtist = artworkArtist;
    }

    public Long getRatingId() {
        return ratingId;
    }

    public void setRatingId(Long ratingId) {
        this.ratingId = ratingId;
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

    public Long getArtworkId() {
        return artworkId;
    }

    public void setArtworkId(Long artworkId) {
        this.artworkId = artworkId;
    }

    public String getArtworkTitle() {
        return artworkTitle;
    }

    public void setArtworkTitle(String artworkTitle) {
        this.artworkTitle = artworkTitle;
    }

    public String getArtworkArtist() {
        return artworkArtist;
    }

    public void setArtworkArtist(String artworkArtist) {
        this.artworkArtist = artworkArtist;
    }


}
