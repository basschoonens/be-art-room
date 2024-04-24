package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.RatingAverageOutputDto;
import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.models.User;
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

    // TODO Aan het einde van de opdracht alle niet gebruikte CRUD methods verwijderen.

    @Autowired
    public RatingController(RatingService ratingService, UserService userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<RatingDto>> getAllRatings() {
        List<RatingDto> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) {
        Rating rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    @PostMapping()
    public ResponseEntity<Void> addRating(@RequestBody RatingDto rating) {
        ratingService.addRating(rating);
        return ResponseEntity.created(null).build();
    }

//    @PutMapping("/{ratingId}")
//    public ResponseEntity<Void> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto rating) {
//        ratingService.updateRating(ratingId, rating.getArtworkId(), rating);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/{artworkId}/{ratingId}")
    public ResponseEntity<Void> updateRating(@PathVariable Long artworkId, @PathVariable Long ratingId, @RequestBody RatingDto ratingDto) {
        ratingService.updateRating(ratingId, artworkId, ratingDto);
        return ResponseEntity.noContent().build();
    }

    // TODO uitzoeken of een delete alleen op basis van ratingId mogelijk is of dat er ook een artworkId nodig is.

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }

    // TODO Kijken of het handiger is deze weg te halen aangezien de ratings voor een artwork al meegeven worden bij een Artwork Get request.
    // TODO Individuele user rating nog maken zodra er User functionaliteit is.

    @GetMapping("/{artworkId}/ratings")
    public ResponseEntity<List<RatingDto>> getRatingsForArtwork(@PathVariable Long artworkId) {
        List<RatingDto> ratings = ratingService.getRatingsForArtwork(artworkId);

        return ResponseEntity.ok(ratings);
    }

//    @PostMapping("/{artworkId}/ratings")
//    public ResponseEntity<Void> addRatingToArtwork(@PathVariable Long artworkId, @RequestBody RatingDto rating) {
//        ratingService.addOrUpdateRatingToArtwork(artworkId, rating.getRating(), rating.getComment());
//        return ResponseEntity.created(null).build();
//    }

    @PostMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> addRatingToArtwork(@PathVariable Long artworkId, @RequestBody RatingDto ratingDto) {
        String username = userService.getCurrentLoggedInUsername();
        ratingService.addOrUpdateRatingToArtwork(username, artworkId, ratingDto.getRating(), ratingDto.getComment());
        return ResponseEntity.created(null).build();
    }

    // TODO Kijken of het handiger is deze weg te halen aangezien de average meegeven wordt bij een Artwork Get request.

    @GetMapping("/{artworkId}/ratings/average")
    public ResponseEntity<RatingAverageOutputDto> getAverageRatingForArtwork(@PathVariable Long artworkId) {
        double averageRating = ratingService.calculateAverageRatingForArtwork(artworkId);
        RatingAverageOutputDto outputDto = new RatingAverageOutputDto(averageRating);
        return ResponseEntity.ok(outputDto);
    }
}
