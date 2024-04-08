package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    public Artwork findArtworkByArtist(String artist);

}
