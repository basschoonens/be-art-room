package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    private final RatingService ratingService;

    // TODO Aan het einde van de opdracht alle niet gebruikte CRUD methods verwijderen.

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
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

    @PutMapping("/{ratingId}")
    public ResponseEntity<Void> updateRating(@PathVariable Long ratingId, @RequestBody RatingDto rating) {
        ratingService.updateRating(ratingId, rating);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.noContent().build();
    }
}
