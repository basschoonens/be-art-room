package nl.novi.theartroom.helper;

import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingCalculationHelper {

    private final RatingRepository ratingRepository;

    public RatingCalculationHelper(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public double calculateAverageRatingForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkArtworkId(artworkId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = ratings.stream().mapToInt(Rating::getRating).sum();
        return totalRating / ratings.size();
    }

    public int countRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkArtworkId(artworkId);
        return ratings.size();
    }


}
