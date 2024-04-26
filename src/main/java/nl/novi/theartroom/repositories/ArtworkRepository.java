package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    Artwork findByArtworkId(long id);

}
