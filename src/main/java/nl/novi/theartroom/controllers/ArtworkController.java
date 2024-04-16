package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.ArtworkOutputArtloverDto;
import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.dtos.RatingAverageOutputDto;
import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.services.ArtworkService;
import nl.novi.theartroom.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;
    private final RatingService ratingService;

    @Autowired
    public ArtworkController(ArtworkService artworkService, RatingService ratingService) {
        this.artworkService = artworkService;
        this.ratingService = ratingService;
    }

    @GetMapping()
    public ResponseEntity<List<ArtworkOutputArtloverDto>> getAllArtworks() {
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkOutputArtloverDto> getArtworkById(@PathVariable Long id) {
        ArtworkOutputArtloverDto artwork = artworkService.getArtworkById(id);
        return ResponseEntity.ok(artwork);
    }

    @PostMapping()
    public ResponseEntity<Void> addArtwork(@RequestBody ArtworkInputDto artwork) {
        artworkService.saveArtwork(artwork);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtworkOutputArtloverDto> updateArtwork(@PathVariable Long id, @RequestBody ArtworkInputDto artwork) {
        artworkService.updateArtwork(id, artwork);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }

    // Add rating to artwork
    // TODO Nog bedenken of dit op de goede plek staat.

    @GetMapping("/{artworkId}/ratings")
    public ResponseEntity<List<RatingDto>> getRatingsForArtwork(@PathVariable Long artworkId) {
        List<RatingDto> ratings = ratingService.getRatingsForArtwork(artworkId);

        return ResponseEntity.ok(ratings);
    }

    @PostMapping("/{artworkId}/ratings")
    public ResponseEntity<Void> addRatingToArtwork(@PathVariable Long artworkId, @RequestBody Rating rating) {
        ratingService.addRatingToArtwork(artworkId, rating.getStars(), rating.getCommentText());
        return ResponseEntity.created(null).build();
    }

    // Get an average rating for an artwork
    @GetMapping("/{artworkId}/ratings/average")
    public ResponseEntity<RatingAverageOutputDto> getAverageRatingForArtwork(@PathVariable Long artworkId) {
        double averageRating = ratingService.calculateAverageRatingForArtwork(artworkId);
        RatingAverageOutputDto outputDto = new RatingAverageOutputDto(averageRating);
        return ResponseEntity.ok(outputDto);
    }
}
