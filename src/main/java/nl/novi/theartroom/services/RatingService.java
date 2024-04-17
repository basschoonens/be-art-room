package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.RatingDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkRepository artworkRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
    }

    public List<RatingDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return RatingDtoMapper.toRatingOutputDtoList(ratings);
    }

    public Rating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RecordNotFoundException("Rating with id " + ratingId + " not found."));
    }

    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    public void updateRating(Long ratingId, Rating rating) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        if (ratingFound.isEmpty()) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            rating.setId(ratingId);
            ratingRepository.save(rating);
        }
    }

    public void deleteRating(Long ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            ratingRepository.deleteById(ratingId);
        }
    }

    @Transactional
    public void addRatingToArtwork(Long artworkId, int stars, String comment) {
        Artwork artwork = findArtworkById(artworkId);
        Rating rating = new Rating();
        rating.setRating(stars);
        rating.setComment(comment);
        rating.setArtwork(artwork);
        ratingRepository.save(rating);
    }

    public List<RatingDto> getRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findByArtworkId(artworkId);
        return RatingDtoMapper.toRatingOutputDtoList(ratings);
    }

    private Artwork findArtworkById(Long artworkId) {
        return artworkRepository.findById(artworkId)
                .orElseThrow(() -> new RecordNotFoundException("Artwork with id " + artworkId + " not found."));
    }

    public double calculateAverageRatingForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findByArtworkId(artworkId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = ratings.stream().mapToInt(Rating::getRating).sum();
        return (double) totalRating / ratings.size();
    }
}
