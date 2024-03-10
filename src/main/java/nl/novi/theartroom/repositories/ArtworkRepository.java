package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    public Artwork findArtworkByArtist(String artist);
    public Artwork findArtworkByType(String type);
    public Artwork findArtworkBySellingPrice(Double sellingPrice);
    public Artwork findArtworkByEdition(String edition);

}
