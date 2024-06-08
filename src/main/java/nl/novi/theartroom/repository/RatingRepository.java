package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findRatingsListByArtworkArtworkId(Long artworkId);

    Optional<Rating> findByUserUsernameAndArtworkArtworkId(String username, Long artworkId);

    List<Rating> findRatingsListByUserUsername(String username);

    Optional<Rating> findByArtworkArtistAndArtworkArtworkIdAndRatingId(String username, Long artworkId, Long ratingId);

}
