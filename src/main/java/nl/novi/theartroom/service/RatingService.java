package nl.novi.theartroom.service;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingUserDto;
import nl.novi.theartroom.exception.RecordNotFoundException;
import nl.novi.theartroom.mapper.RatingDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.RatingRepository;
import nl.novi.theartroom.repository.UserRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;
    private final RatingDtoMapper ratingDtoMapper;
    private final UserService userService;

    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository, UserRepository userRepository, RatingDtoMapper ratingDtoMapper, UserService userService) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
        this.ratingDtoMapper = ratingDtoMapper;
        this.userService = userService;
    }

    // USER RATINGS METHODS

    // Ratings by artwork id method

    public List<RatingUserDto> getAllRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingUserDtoList(ratings);
    }

    // All ratings done by user with artworkdetails method

    public List<RatingOutputWithArtworkDto> getAllRatingsWithArtworkDetailsByUser(String username) {
        List<Rating> ratings = ratingRepository.findRatingsListByUserUsername(username);
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

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

    // Get all ratings for an artist method

    public List<RatingOutputWithArtworkDto> getAllRatingsForArtist(String username) {
        List<Artwork> artworks = artworkRepository.findByArtist(username);
        List<Rating> ratings = new ArrayList<>();
        for (Artwork artwork : artworks) {
            ratings.addAll(artwork.getRatings());
        }
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

//    public List<RatingOutputWithArtworkDto> getAllRatingsForArtist(String username) {
//        User user = userService.getUserByUsername(username);
//        List<Rating> ratings = ratingRepository.findRatingsListByUserUsername(username);
//        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
//    }

    // All ratings for an artist by artwork id method

    public List<RatingOutputWithArtworkDto> getAllRatingsForArtworkWithArtworkDetails(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

    public void deleteRatingByArtworkIdAndRatingId(Long artworkId, Long ratingId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByRatingIdAndArtworkId(ratingId, artworkId);
        existingRatingOptional.ifPresent(ratingRepository::delete);
    }

    // CRUD operations for Rating

    public List<RatingOutputWithArtworkDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingWithArtworkDtoList(ratings);
    }

    public RatingOutputWithArtworkDto getRatingById(Long ratingId) {
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