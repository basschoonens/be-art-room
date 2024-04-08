package nl.novi.theartroom.controllers;

import nl.novi.theartroom.dtos.ArtworkDto;
import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.services.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;

    @Autowired
    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping()
    public ResponseEntity<List<ArtworkDto>> getAllArtworks() {
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkDto> getArtworkById(@PathVariable Long id) {
        ArtworkDto artwork = artworkService.getArtworkById(id);

        return ResponseEntity.ok(artwork);
    }

    @PostMapping()
    public ResponseEntity<Void> addArtwork(@RequestBody ArtworkInputDto artwork) {
        artworkService.saveArtwork(artwork);
        return ResponseEntity.created(null).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtworkDto> updateArtwork(@PathVariable Long id, @RequestBody ArtworkInputDto artwork) {
        artworkService.updateArtwork(id, artwork);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
        return ResponseEntity.noContent().build();
    }

}
