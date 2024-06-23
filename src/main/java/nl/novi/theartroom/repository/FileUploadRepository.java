package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.artworks.ArtworkImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<ArtworkImage, String> {

    Optional<ArtworkImage> findByFileName(String fileName);
}
