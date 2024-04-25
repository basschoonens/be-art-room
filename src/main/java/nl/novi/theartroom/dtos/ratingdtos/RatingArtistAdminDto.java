package nl.novi.theartroom.dtos.ratingdtos;



public class RatingArtistAdminDto {

    private Long id;
    private Integer rating;
    private String comment;

    // TODO Add artworkId

    public RatingArtistAdminDto() {
    }

    public RatingArtistAdminDto(Long id, Integer rating, String comment) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
