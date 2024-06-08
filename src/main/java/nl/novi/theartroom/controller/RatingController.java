package nl.novi.theartroom.controller;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingUserDto;
import nl.novi.theartroom.model.Rating;
import nl.novi.theartroom.service.RatingService;
import nl.novi.theartroom.service.userservice.UserService;
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

    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    // Ratings by artwork id method for all users

    @GetMapping("/artwork/{artworkId}")
    public ResponseEntity<List<RatingUserDto>> getAllRatingsForArtwork(@PathVariable Long artworkId) {
        List<RatingUserDto> ratings = ratingService.getAllRatingsForArtwork(artworkId);
        return ResponseEntity.ok(ratings);
    }

    // USER RATINGS METHOD

    @GetMapping("/user")
    public ResponseEntity<List<RatingOutputWithArtworkDto>> getAllRatingsForArtworksDoneByUser() {
        String username = userService.getCurrentLoggedInUsername();
        List<RatingOutputWithArtworkDto> ratings = ratingService.getAllRatingsByUserWithArtworkDetails(username);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/user/{artworkId}")
    public ResponseEntity<Void> addOrUpdateRatingToArtworkByUser(@PathVariable Long artworkId, @RequestBody RatingUserDto ratingUserDto) {
        String username = userService.getCurrentLoggedInUsername();
        RatingOutputWithArtworkDto ratingDto = ratingService.addOrUpdateRatingToArtwork(username, artworkId, ratingUserDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ratingId}")
                .buildAndExpand(ratingDto.getRatingId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/user/{artworkId}")
    public ResponseEntity<Void> deleteRatingToArtworkDoneByUser(@PathVariable Long artworkId) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.deleteRatingToArtworkDoneByUser(username, artworkId);
        return ResponseEntity.noContent().build();
    }

    // ARTIST RATING METHODS

    @GetMapping("/artist")
    public ResponseEntity<List<RatingOutputWithArtworkDto>> getAllRatingsForAllArtworksByArtist() {
        String username = userService.getCurrentLoggedInUsername();
        List<RatingOutputWithArtworkDto> ratings = ratingService.getAllRatingsForAllArtworksByArtist(username);
        return ResponseEntity.ok(ratings);
    }

    @PutMapping("/artist/{artworkId}/{ratingId}")
    public ResponseEntity<Void> updateRatingByArtistAndArtworkId(@PathVariable Long artworkId, @PathVariable Long ratingId, @RequestBody RatingUserDto rating) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.updateRatingByArtistAndArtworkId(username, artworkId, ratingId, rating);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/artist/{artworkId}/{ratingId}")
    public ResponseEntity<Void> deleteRatingByArtistAndArtworkId(@PathVariable Long artworkId, @PathVariable Long ratingId) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.deleteRatingByArtistAndArtworkId(username, artworkId, ratingId);
        return ResponseEntity.noContent().build();
    }

    // ADMIN METHODS
    // All ratings for the admin done by users with artwork details method

    @GetMapping("/admin")
    public ResponseEntity<List<RatingOutputWithArtworkDto>> getAllRatingsForAdmin() {
        List<RatingOutputWithArtworkDto> ratings = ratingService.getAllRatingsForAdmin();
        return ResponseEntity.ok(ratings);
    }

    @PutMapping("/admin/{ratingId}")
    public ResponseEntity<Void> updateRatingForAdmin(@PathVariable Long ratingId, @RequestBody RatingUserDto rating) {
        ratingService.updateRatingForAdmin(ratingId, rating);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/{ratingId}")
    public ResponseEntity<Void> deleteRatingByAdmin(@PathVariable Long ratingId) {
        ratingService.deleteRatingForAdmin(ratingId);
        return ResponseEntity.noContent().build();
    }

    // CRUD OPERATIONS FOR RATING (FOR TESTING PURPOSES, NOT USED IN THE APPLICATION)

    @GetMapping()
    public ResponseEntity<List<RatingOutputWithArtworkDto>> getAllRatings() {
        List<RatingOutputWithArtworkDto> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingOutputWithArtworkDto> getRatingById(@PathVariable Long ratingId) {
        RatingOutputWithArtworkDto rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping()
    public ResponseEntity<Void> addRating(@RequestBody RatingUserDto rating) {
        Rating newRating = ratingService.addRating(rating);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ratingId}")
                .buildAndExpand(newRating.getRatingId())
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
