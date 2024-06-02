package nl.novi.theartroom.dtos.ratingdtos;

public class RatingUserDto {

    private Long ratingId;
    private Integer rating;
    private String comment;

    public RatingUserDto() {
    }

    public RatingUserDto(Long ratingId, Integer rating, String comment) {
        this.ratingId = ratingId;
        this.rating = rating;
        this.comment = comment;
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
}
