package nl.novi.theartroom.dtos;

public class RatingAverageOutputDto {

    private Double averageRating;

    public RatingAverageOutputDto() {
    }

    public RatingAverageOutputDto(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
