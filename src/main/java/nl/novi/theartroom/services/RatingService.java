package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.RatingDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.RatingRepository;
import nl.novi.theartroom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
    }

    public List<RatingDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return RatingDtoMapper.toRatingOutputDtoList(ratings);
    }

    public Rating getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RecordNotFoundException("Rating with id " + ratingId + " not found."));
    }

    public void addRating(RatingDto rating) {
        Rating newRating = RatingDtoMapper.toRating(rating);
        ratingRepository.save(newRating);
    }

//    public void updateRating(Long ratingId, Rating rating) {
//        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
//        if (ratingFound.isEmpty()) {
//            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
//        } else {
//            rating.setId(ratingId);
//            ratingRepository.save(rating);
//        }
//    }

    // TODO Create a new updateRating method that connects to Artwork Id and Rating Id

    public void updateRating(Long ratingId, Long artworkId, RatingDto ratingDto) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        Artwork artwork = findArtworkById(artworkId);

        if (ratingFound.isEmpty()) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            Rating existingRating = ratingFound.get();
            Rating ratingToUpdate = RatingDtoMapper.toRating(ratingDto);

            // Update the fields of the existing rating
            existingRating.setRating(ratingToUpdate.getRating());
            existingRating.setComment(ratingToUpdate.getComment());
            existingRating.setArtwork(artwork); // Update the artwork association

            ratingRepository.save(existingRating);
        }
    }

//    public void updateRating(Long ratingId, RatingDto ratingDto) {
//        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
//
//        if (ratingFound.isEmpty()) {
//            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
//        } else {
//            Rating ratingToUpdate = RatingDtoMapper.toRating(ratingDto);
//            ratingToUpdate.setId(ratingId);
//            ratingRepository.save(ratingToUpdate);
//        }
//    }

    public void deleteRating(Long ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            ratingRepository.deleteById(ratingId);
        }
    }

//    @Transactional
//    public void addRatingToArtwork(Long artworkId, int rated, String comment) {
//        Artwork artwork = findArtworkById(artworkId);
//        Rating rating = new Rating();
//        rating.setRating(rated);
//        rating.setComment(comment);
//        rating.setArtwork(artwork);
//        ratingRepository.save(rating);
//    }

    // TODO User adds or updates a rating for an artwork

    @Transactional
    public void addOrUpdateRatingToArtwork(String username, Long artworkId, int rated, String comment) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User with username " + username + " not found."));

        if (existingRatingOptional.isPresent()) {
            Rating existingRating = existingRatingOptional.get();
            existingRating.setRating(rated);
            existingRating.setComment(comment);
            ratingRepository.save(existingRating);
        } else {
            Artwork artwork = findArtworkById(artworkId);
            Rating newRating = new Rating();
            newRating.setUser(user);
            newRating.setArtwork(artwork);
            newRating.setRating(rated);
            newRating.setComment(comment);
            ratingRepository.save(newRating);
        }
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
