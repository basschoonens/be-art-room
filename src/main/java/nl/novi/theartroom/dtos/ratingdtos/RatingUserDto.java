package nl.novi.theartroom.dtos.ratingdtos;

public class RatingUserDto {

    private Integer rating;
    private String comment;

    public RatingUserDto() {
    }

    public RatingUserDto(Integer rating, String comment) {
        this.rating = rating;
        this.comment = comment;
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
