package nl.novi.theartroom.dtos;

public class ArtworkArtloverDto {

    private Long id;
    private String title;
    private String artist;
    private String description;
    private Integer dateCreated;
    private String edition;
    private String imageUrl;
    private String artworkType;

    private PaintingOutputDto paintingOutputDto;
    private DrawingOutputDto drawingOutputDto;

    public ArtworkArtloverDto() {
    }

    public ArtworkArtloverDto(Long id, String title, String artist, String description, Integer dateCreated, String edition, String imageUrl, String artworkType) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.dateCreated = dateCreated;
        this.edition = edition;
        this.imageUrl = imageUrl;
        this.artworkType = artworkType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Integer dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtworkType() {
        return artworkType;
    }

    public void setArtworkType(String artworkType) {
        this.artworkType = artworkType;
    }

    public PaintingOutputDto getPaintingOutputDto() {
        return paintingOutputDto;
    }

    public void setPaintingOutputDto(PaintingOutputDto paintingOutputDto) {
        this.paintingOutputDto = paintingOutputDto;
    }

    public DrawingOutputDto getDrawingOutputDto() {
        return drawingOutputDto;
    }

    public void setDrawingOutputDto(DrawingOutputDto drawingOutputDto) {
        this.drawingOutputDto = drawingOutputDto;
    }
}
