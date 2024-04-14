package nl.novi.theartroom.repositories;

import nl.novi.theartroom.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    public Rating findRatingByRating(int rating);

}
