package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtloverDto;
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

    // TODO Calculatie average rating eruit halen en verplaatsten naar aparte service

    @GetMapping()
    public ResponseEntity<List<ArtworkOutputArtloverDto>> getAllArtworks() {
        List<ArtworkOutputArtloverDto> artworks = artworkService.getAllArtworks();

        for (ArtworkOutputArtloverDto artwork : artworks) {
            double averageRating = ratingService.calculateAverageRatingForArtwork(artwork.getId());
            artwork.setAverageRating(averageRating);
        }

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

}
