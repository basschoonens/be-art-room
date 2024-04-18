package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByArtworkId(Long artworkId);

}
