package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.ratingdtos.RatingArtistAdminDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
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
    private final RatingDtoMapper ratingDtoMapper;

    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository, UserRepository userRepository, RatingDtoMapper ratingDtoMapper) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
        this.ratingDtoMapper = ratingDtoMapper;
    }

// USER RATINGS METHODS

    // TODO Bij deze methode uitzoeken waarom ik niet via mijn artworkRepository.findById(artworkId) de artwork kan vinden.

    @Transactional
    public void addOrUpdateRatingToArtwork(String username, Long artworkId, RatingUserDto ratingUserDto) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException("User with username " + username + " not found."));

        if (existingRatingOptional.isPresent()) {
            Rating existingRating = existingRatingOptional.get();
            existingRating.setRating(ratingUserDto.getRating());
            existingRating.setComment(ratingUserDto.getComment());
            ratingRepository.save(existingRating);
        } else {
            Artwork artwork = findArtworkById(artworkId);
            Rating newRating = new Rating();
            newRating.setUser(user);
            newRating.setArtwork(artwork);
            newRating.setRating(ratingUserDto.getRating());
            newRating.setComment(ratingUserDto.getComment());
            ratingRepository.save(newRating);
        }
    }

    @Transactional
    public void deleteRatingByUsernameAndArtworkId(String username, Long artworkId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);
        existingRatingOptional.ifPresent(ratingRepository::delete);
    }

    // ARTIST RATING METHODS

    public List<RatingArtistAdminDto> getRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findByArtworkId(artworkId);
        return ratingDtoMapper.toRatingArtistAdminDtoList(ratings);
    }

    public void deleteRatingByArtworkIdAndRatingId(Long artworkId, Long ratingId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByIdAndArtworkId(ratingId, artworkId);
        existingRatingOptional.ifPresent(ratingRepository::delete);
    }


    // CRUD operations for Rating

    public List<RatingArtistAdminDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingArtistAdminDtoList(ratings);
    }

    public RatingArtistAdminDto getRatingById(Long ratingId) {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RecordNotFoundException("Rating with id " + ratingId + " not found."));
        return RatingDtoMapper.toRatingArtistAdminDto(rating);
    }

    public void addRating(RatingUserDto rating) {
            Rating newRating = ratingDtoMapper.toRatingUserDto(rating);
            ratingRepository.save(newRating);
        }

    public void updateRating(Long ratingId, RatingUserDto ratingDto) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);

        if (ratingFound.isEmpty()) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            Rating ratingToUpdate = ratingDtoMapper.toRatingUserDto(ratingDto);
            ratingToUpdate.setId(ratingId);
            ratingRepository.save(ratingToUpdate);
        }
    }

    public void deleteRating(Long ratingId) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        ratingFound.ifPresent(ratingRepository::delete);
    }

    // TODO Waar moet deze komen ?

    // CALCULATIONS

    public double calculateAverageRatingForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findByArtworkId(artworkId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = ratings.stream().mapToInt(Rating::getRating).sum();
        return (double) totalRating / ratings.size();
    }

    private Artwork findArtworkById(Long artworkId) {
        return artworkRepository.findById(artworkId)
                .orElseThrow(() -> new RecordNotFoundException("Artwork with id " + artworkId + " not found."));
    }

}
