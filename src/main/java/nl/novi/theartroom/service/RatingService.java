package nl.novi.theartroom.service;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingUserDto;
import nl.novi.theartroom.exception.ArtworkNotFoundException;
import nl.novi.theartroom.exception.RatingNotFoundException;
import nl.novi.theartroom.exception.RecordNotFoundException;
import nl.novi.theartroom.mapper.RatingDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.RatingRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final ArtworkRepository artworkRepository;
    private final UserService userService;
    private final RatingDtoMapper ratingDtoMapper;

    public RatingService(RatingRepository ratingRepository, ArtworkRepository artworkRepository, UserService userService, RatingDtoMapper ratingDtoMapper) {
        this.ratingRepository = ratingRepository;
        this.artworkRepository = artworkRepository;
        this.userService = userService;
        this.ratingDtoMapper = ratingDtoMapper;
    }

    // USER RATINGS METHODS

    // Ratings by artwork id method

    public List<RatingUserDto> getAllRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingDtoList(ratings);
    }

    // All ratings done by user with artworkdetails method

    public List<RatingOutputWithArtworkDto> getAllRatingsByUserWithArtworkDetails(String username) {
        List<Rating> ratings = ratingRepository.findRatingsListByUserUsername(username);
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

//    public Rating addOrUpdateRatingToArtwork(String username, Long artworkId, RatingUserDto ratingUserDto) {
//        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);
//
//        User user = userRepository.findById(username)
//                .orElseThrow(() -> new RecordNotFoundException("User with username " + username + " not found."));
//
//        Rating rating;
//        if (existingRatingOptional.isPresent()) {
//            rating = existingRatingOptional.get();
//            rating.setRating(ratingUserDto.getRating());
//            rating.setComment(ratingUserDto.getComment());
//            ratingRepository.save(rating);
//        } else {
//            Artwork artwork = artworkRepository.findById(artworkId)
//                    .orElseThrow(() -> new RecordNotFoundException("Artwork with id " + artworkId + " not found."));
//            rating = ratingDtoMapper.toRatingDto(ratingUserDto);
//            rating.setUser(user);
//            rating.setArtwork(artwork);
//            ratingRepository.save(rating);
//        }
//        return rating;
//    }

    public RatingOutputWithArtworkDto addOrUpdateRatingToArtwork(String username, Long artworkId, RatingUserDto ratingUserDto) {
        User user = userService.getUserByUsername(username);
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new ArtworkNotFoundException("Artwork with id " + artworkId + " not found."));

        Rating rating = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId)
                .orElse(new Rating());

        rating.setRating(ratingUserDto.getRating());
        rating.setComment(ratingUserDto.getComment());
        rating.setUser(user);
        rating.setArtwork(artwork);

        Rating savedRating = ratingRepository.save(rating);

        return ratingDtoMapper.toRatingDtoWithArtworkDto(savedRating);
    }


    public void deleteRatingByUsernameAndArtworkId(String username, Long artworkId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkId(username, artworkId);
        if (existingRatingOptional.isPresent()) {
            ratingRepository.delete(existingRatingOptional.get());
        } else {
            throw new RatingNotFoundException("Rating with username " + username + " and artworkId " + artworkId + " not found.");
        }
    }

    // ARTIST RATING METHODS

    // Get all ratings for an artist method

    public List<RatingOutputWithArtworkDto> getAllRatingsForArtist(String username) {
        List<Artwork> artworks = artworkRepository.findByArtist(username);
        List<Rating> ratings = new ArrayList<>();
        for (Artwork artwork : artworks) {
            ratings.addAll(artwork.getRatings());
        }
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    // All ratings for an artist by artwork id method

    public List<RatingOutputWithArtworkDto> getAllRatingsForArtworkWithArtworkDetails(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkId(artworkId);
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public void deleteRatingByArtworkIdAndRatingId(Long artworkId, Long ratingId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByRatingIdAndArtworkId(ratingId, artworkId);
        if (existingRatingOptional.isEmpty()) {
            throw new RatingNotFoundException("Rating with id " + ratingId + " and artworkId " + artworkId + " not found.");
        } else {
            ratingRepository.delete(existingRatingOptional.get());
        }
    }

    // CRUD operations for Rating

    public List<RatingOutputWithArtworkDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public RatingOutputWithArtworkDto getRatingById(Long ratingId) {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        return rating.map(ratingDtoMapper::toRatingDtoWithArtworkDto)
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + ratingId + " not found."));
    }

    public Rating addRating(RatingUserDto rating) {
        Rating newRating = ratingDtoMapper.toRating(rating);
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