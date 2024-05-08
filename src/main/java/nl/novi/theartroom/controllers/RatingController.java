package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.ratingdtos.RatingArtistAdminDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.services.RatingService;
import nl.novi.theartroom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // USER RATINGS METHODS

    // TODO Moet hij een void returnen of niet?
    // TODO location created URI returnen

    @PostMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> addOrUpdateRatingToArtworkByUser(@PathVariable Long artworkId, @RequestBody RatingUserDto ratingUserDto) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.addOrUpdateRatingToArtwork(username, artworkId, ratingUserDto);
        return ResponseEntity.created(null).build();
    }

    @DeleteMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> deleteRatingToArtworkByUser(@PathVariable Long artworkId) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.deleteRatingByUsernameAndArtworkId(username, artworkId);
        return ResponseEntity.noContent().build();
    }

    // ARTIST RATING METHODS
    // Artist moet per artwork alle ratings kunnen ophalen, inzien en/of verwijderen.

    @GetMapping("/{artworkId}/ratings")
    public ResponseEntity<List<RatingArtistAdminDto>> getAllRatingsForArtworkByArtistAdmin(@PathVariable Long artworkId) {
        List<RatingArtistAdminDto> ratings = ratingService.getRatingsForArtwork(artworkId);

        return ResponseEntity.ok(ratings);
    }

    @DeleteMapping("/{artworkId}/ratings/{ratingId}")
    public ResponseEntity<Void> deleteRatingByArtistAdmin(@PathVariable Long artworkId, @PathVariable Long ratingId) {
        ratingService.deleteRatingByArtworkIdAndRatingId(artworkId, ratingId);
        return ResponseEntity.noContent().build();
    }

    // CRUD OPERATIONS

    @GetMapping()
    public ResponseEntity<List<RatingArtistAdminDto>> getAllRatings() {
        List<RatingArtistAdminDto> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingArtistAdminDto> getRatingById(@PathVariable Long ratingId) {
        RatingArtistAdminDto rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping()
    public ResponseEntity<Void> addRating(@RequestBody RatingUserDto rating) {
        ratingService.addRating(rating);
        return ResponseEntity.created(null).build();
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
