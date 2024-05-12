package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.ArtworkImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<ArtworkImage, String> {

    Optional<ArtworkImage> findByFileName(String fileName);

}
