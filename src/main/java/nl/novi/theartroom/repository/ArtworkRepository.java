package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> findAllByUser(User user);

    List<Artwork> findByArtist(String artist);

}
