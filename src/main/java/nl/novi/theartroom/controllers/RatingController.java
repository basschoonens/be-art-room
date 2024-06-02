package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.ratingdtos.RatingWithArtworkDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.services.RatingService;
import nl.novi.theartroom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    private final RatingService ratingService;

    private final UserService userService;

    @Autowired
    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    // USER RATINGS METHOD

    // Ratings by artwork id method

    @GetMapping("/{artworkId}/ratings")
    public ResponseEntity<List<RatingUserDto>> getAllRatingsForArtwork(@PathVariable Long artworkId) {
        List<RatingUserDto> ratings = ratingService.getAllRatingsForArtwork(artworkId);

        return ResponseEntity.ok(ratings);
    }

    // All ratings done by user with artworkdetails method

    @GetMapping("/user/artwork")
    public ResponseEntity<List<RatingWithArtworkDto>> getAllRatingsWithArtworkByUser() {
        String username = userService.getCurrentLoggedInUsername();
        List<RatingWithArtworkDto> ratings = ratingService.getAllRatingsWithArtworkDetailsByUser(username);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> addOrUpdateRatingToArtworkByUser(@PathVariable Long artworkId, @RequestBody RatingUserDto ratingUserDto) {
        String username = userService.getCurrentLoggedInUsername();
        Rating rating = ratingService.addOrUpdateRatingToArtwork(username, artworkId, ratingUserDto);

        // Construct the URI for the created/updated resource
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ratingId}")
                .buildAndExpand(rating.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> deleteRatingToArtworkByUser(@PathVariable Long artworkId) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.deleteRatingByUsernameAndArtworkId(username, artworkId);
        return ResponseEntity.noContent().build();
    }

    // ARTIST RATING METHODS
    // Artist moet per artwork alle ratings kunnen inzien en/of verwijderen.

    // All ratings for an artist by artwork id method

    @GetMapping("/{artworkId}/ratings/artist")
    public ResponseEntity<List<RatingWithArtworkDto>> getAllRatingsForArtworkWithArtworkDetails(@PathVariable Long artworkId) {
        List<RatingWithArtworkDto> ratings = ratingService.getAllRatingsForArtworkWithArtworkDetails(artworkId);

        return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/{artworkId}/ratings/{ratingId}")
    public ResponseEntity<Void> deleteRatingByArtistAdmin(@PathVariable Long artworkId, @PathVariable Long ratingId) {
        ratingService.deleteRatingByArtworkIdAndRatingId(artworkId, ratingId);
        return ResponseEntity.noContent().build();
    }

    // ADMIN METHODS
    // All ratings for the admin done by users with artwork details method

    @GetMapping("/{artworkId}/ratings/admin")
    public ResponseEntity<List<RatingWithArtworkDto>> getAllRatingsForAdmin(@PathVariable Long artworkId) {
        List<RatingWithArtworkDto> ratings = ratingService.getAllRatingsForArtworkWithArtworkDetails(artworkId);

        return ResponseEntity.ok(ratings);
    }

    // CRUD OPERATIONS

    @GetMapping()
    public ResponseEntity<List<RatingWithArtworkDto>> getAllRatings() {
        List<RatingWithArtworkDto> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingWithArtworkDto> getRatingById(@PathVariable Long ratingId) {
        RatingWithArtworkDto rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping()
    public ResponseEntity<Void> addRating(@RequestBody RatingUserDto rating) {
        Rating newRating = ratingService.addRating(rating);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ratingId}")
                .buildAndExpand(newRating.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<Void> updateRating(@PathVariable Long ratingId, @RequestBody RatingUserDto rating) {
        ratingService.updateRating(ratingId, rating);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

}
