package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.ratingdtos.RatingWithArtworkDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.RatingDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.RatingRepository;
import nl.novi.theartroom.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;
    private final RatingDtoMapper ratingDtoMapper;

    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository, UserRepository userRepository, RatingDtoMapper ratingDtoMapper) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
        this.ratingDtoMapper = ratingDtoMapper;
    }

    // USER RATINGS METHODS

    // Ratings by artwork id method

    public List<RatingUserDto> getAllRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingUserDtoList(ratings);
    }

    // All ratings done by user with artworkdetails method

    public List<RatingWithArtworkDto> getAllRatingsWithArtworkDetailsByUser(String username) {
        List<Rating> ratings = ratingRepository.findRatingsListByUserUsername(username);
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

    @Transactional
    public Rating addOrUpdateRatingToArtwork(String username, Long artworkId, RatingUserDto ratingUserDto) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);

        User user = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User with username " + username + " not found."));

        Rating rating;
        if (existingRatingOptional.isPresent()) {
            rating = existingRatingOptional.get();
            rating.setRating(ratingUserDto.getRating());
            rating.setComment(ratingUserDto.getComment());
            ratingRepository.save(rating);
        } else {
            Artwork artwork = artworkRepository.findById(artworkId)
                    .orElseThrow(() -> new RecordNotFoundException("Artwork with id " + artworkId + " not found."));
            rating = ratingDtoMapper.toRatingUserDto(ratingUserDto);
            rating.setUser(user);
            rating.setArtwork(artwork);
            ratingRepository.save(rating);
        }
        return rating;
    }

    public void deleteRatingByUsernameAndArtworkId(String username, Long artworkId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);
        existingRatingOptional.ifPresent(ratingRepository::delete);
    }

    // ARTIST RATING METHODS

    // All ratings for an artist by artwork id method

    public List<RatingWithArtworkDto> getAllRatingsForArtworkWithArtworkDetails(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

    public void deleteRatingByArtworkIdAndRatingId(Long artworkId, Long ratingId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByIdAndArtworkId(ratingId, artworkId);
        existingRatingOptional.ifPresent(ratingRepository::delete);
    }

    // CRUD operations for Rating

    public List<RatingWithArtworkDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

    public RatingWithArtworkDto getRatingById(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RecordNotFoundException("Rating with id " + ratingId + " not found."));
        return ratingDtoMapper.toRatingWithArtworkDto(rating);
    }

    public Rating addRating(RatingUserDto rating) {
        Rating newRating = ratingDtoMapper.toRatingUserDto(rating);
        ratingRepository.save(newRating);
        return newRating;
    }

    public void updateRating(Long ratingId, RatingUserDto ratingDto) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);

        if (ratingFound.isEmpty()) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            Rating ratingToUpdate = ratingFound.get();
            ratingToUpdate.setRating(ratingDto.getRating());
            ratingToUpdate.setComment(ratingDto.getComment());
            ratingRepository.save(ratingToUpdate);
        }
    }

    public void deleteRating(Long ratingId) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        ratingFound.ifPresent(ratingRepository::delete);
    }
}