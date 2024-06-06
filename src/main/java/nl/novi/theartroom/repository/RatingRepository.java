package nl.novi.theartroom.repository;

import nl.novi.theartroom.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findRatingsListByArtworkId(Long artworkId);

    Optional<Rating> findByRatingIdAndArtworkId(Long ratingId, Long artworkId);

    Optional<Rating> findByUserUsernameAndArtworkId(String username, Long artworkId);

    List<Rating> findRatingsListByUserUsername(String username);

//     find all ratings for an artist made by a user
//    List<Rating> findRatingsListByArtworkArtistUsername(String username);

}
