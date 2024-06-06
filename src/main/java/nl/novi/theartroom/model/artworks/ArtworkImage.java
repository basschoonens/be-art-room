package nl.novi.theartroom.model.artworks;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artwork_images")
public class ArtworkImage {

    @Id
    private String fileName;

    public ArtworkImage(String fileName, Artwork artwork) {
        this.fileName = fileName;
    }

    public ArtworkImage() {
    }

    public ArtworkImage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
