package nl.novi.theartroom.dtos.ratingdtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class RatingUserDto {

    private Long ratingId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @Size(message = "Comment should not exceed 500 characters", max = 500)
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
