package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    Optional<Artwork> findById(long id);

    List<Artwork> findAllByUser(User user);

}
