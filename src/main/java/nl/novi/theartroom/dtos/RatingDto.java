package nl.novi.theartroom.dtos;

public class RatingDto {

    private long id;
    private int rating;
    private String comment;
    private long artworkId;

//    private long userId;

    public RatingDto() {
    }

    public RatingDto(long id, int rating, String comment, long artworkId, long userId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.artworkId = artworkId;
//        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public long getArtworkId() {
        return artworkId;
    }

//    public long getUserId() {
//        return userId;
//    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setArtworkId(long artworkId) {
        this.artworkId = artworkId;
    }

//    public void setUserId(long userId) {
//        this.userId = userId;
//    }
}
