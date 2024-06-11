package nl.novi.theartroom.service;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingInputUserDto;
import nl.novi.theartroom.exception.ArtworkNotFoundException;
import nl.novi.theartroom.exception.RatingNotFoundException;
import nl.novi.theartroom.exception.RecordNotFoundException;
import nl.novi.theartroom.exception.UnauthorizedAccessException;
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

    public List<RatingInputUserDto> getAllRatingsForArtwork(Long artworkId) {
        List<Rating> ratings = ratingRepository.findRatingsListByArtworkArtworkId(artworkId);
        return ratingDtoMapper.toRatingDtoList(ratings);
    }

    // All ratings done by user with artworkdetails method

    public List<RatingOutputWithArtworkDto> getAllRatingsByUserWithArtworkDetails(String username) {
        List<Rating> ratings = ratingRepository.findRatingsListByUserUsername(username);
        if (ratings == null || ratings.isEmpty()) {
            return List.of();
        }
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public RatingOutputWithArtworkDto addOrUpdateRatingToArtwork(String username, Long artworkId, RatingInputUserDto ratingInputUserDto) {
        User user = userService.getUserByUsername(username);
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new ArtworkNotFoundException("Artwork with id " + artworkId + " not found."));

        Rating rating = ratingRepository.findByUserUsernameAndArtworkArtworkId(username, artworkId)
                .orElse(new Rating());

        rating.setRating(ratingInputUserDto.getRating());
        rating.setComment(ratingInputUserDto.getComment());
        rating.setUser(user);
        rating.setArtwork(artwork);

        Rating savedRating = ratingRepository.save(rating);

        return ratingDtoMapper.toRatingDtoWithArtworkDto(savedRating);
    }


    public void deleteRatingToArtworkDoneByUser(String username, Long artworkId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByUserUsernameAndArtworkArtworkId(username, artworkId);
        if (existingRatingOptional.isPresent()) {
            ratingRepository.delete(existingRatingOptional.get());
        } else {
            throw new RatingNotFoundException("Rating with username " + username + " and artworkId " + artworkId + " not found.");
        }
    }

    // ARTIST RATING METHODS

    public List<RatingOutputWithArtworkDto> getAllRatingsForAllArtworksByArtist(String username) {
        List<Artwork> artworks = artworkRepository.findByArtist(username);
        if (artworks.isEmpty()) {
            throw new ArtworkNotFoundException("No artworks found for the artist with username: " + username);
        }
        List<Rating> ratings = new ArrayList<>();
        for (Artwork artwork : artworks) {
            if (!artwork.getArtist().equals(username)) {
                throw new UnauthorizedAccessException("The provided username does not match the artist of the artworks.");
            }
            ratings.addAll(artwork.getRatings());
        }
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public void updateRatingByArtistAndArtworkId(String username, Long artworkId, Long ratingId, RatingInputUserDto rating) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByArtworkArtistAndArtworkArtworkIdAndRatingId(username, artworkId, ratingId);
        if (existingRatingOptional.isEmpty()) {
            throw new RatingNotFoundException("Rating with id " + ratingId + " and artworkId " + artworkId + " not found or not owned by user " + username);
        } else {
            Rating ratingToUpdate = existingRatingOptional.get();
            ratingToUpdate.setRating(rating.getRating());
            ratingToUpdate.setComment(rating.getComment());
            ratingRepository.save(ratingToUpdate);
        }
    }

    public void deleteRatingByArtistAndArtworkId(String username, Long artworkId, Long ratingId) {
        Optional<Rating> existingRatingOptional = ratingRepository.findByArtworkArtistAndArtworkArtworkIdAndRatingId(username, artworkId, ratingId);
        if (existingRatingOptional.isEmpty()) {
            throw new RatingNotFoundException("Rating with id " + ratingId + " and artworkId " + artworkId + " not found or not owned by user " + username);
        } else {
            ratingRepository.delete(existingRatingOptional.get());
        }
    }

    //Admin Methods

    public List<RatingOutputWithArtworkDto> getAllRatingsForAdmin() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public void updateRatingForAdmin(Long ratingId, RatingInputUserDto rating) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        if (ratingFound.isEmpty()) {
            throw new RecordNotFoundException("Rating with id " + ratingId + " not found.");
        } else {
            Rating ratingToUpdate = ratingFound.get();
            ratingToUpdate.setRating(rating.getRating());
            ratingToUpdate.setComment(rating.getComment());
            ratingRepository.save(ratingToUpdate);
        }
    }

    public void deleteRatingForAdmin(Long ratingId) {
        Optional<Rating> ratingFound = ratingRepository.findById(ratingId);
        ratingFound.ifPresent(ratingRepository::delete);
    }

    // CRUD OPERATIONS FOR RATING

    public List<RatingOutputWithArtworkDto> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratingDtoMapper.toRatingDtoWithArtworkDtoList(ratings);
    }

    public RatingOutputWithArtworkDto getRatingById(Long ratingId) {
        Optional<Rating> rating = ratingRepository.findById(ratingId);
        return rating.map(ratingDtoMapper::toRatingDtoWithArtworkDto)
                .orElseThrow(() -> new RatingNotFoundException("Rating with id " + ratingId + " not found."));
    }

    public Rating addRating(RatingInputUserDto rating) {
        Rating newRating = ratingDtoMapper.toRating(rating);
        ratingRepository.save(newRating);
        return newRating;
    }

    public void updateRating(Long ratingId, RatingInputUserDto ratingDto) {
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