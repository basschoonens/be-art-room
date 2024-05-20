package nl.novi.theartroom.controllers;

import jakarta.servlet.http.HttpServletRequest;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtloverDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.services.ArtworkImageService;
import nl.novi.theartroom.services.ArtworkService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/artworks")
public class ArtworkController {

    private final ArtworkService artworkService;
    private final ArtworkImageService artworkImageService;

    public ArtworkController(ArtworkService artworkService, ArtworkImageService artworkImageService) {
        this.artworkService = artworkService;
        this.artworkImageService = artworkImageService;
    }

    @GetMapping()
    public ResponseEntity<List<ArtworkOutputArtloverDto>> getAllArtworks() {
        List<ArtworkOutputArtloverDto> artworks = artworkService.getAllArtworks();
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkOutputArtloverDto> getArtworkById(@PathVariable Long id) {
        ArtworkOutputArtloverDto artwork = artworkService.getArtworkById(id);
        return ResponseEntity.ok(artwork);
    }

    // Get Artwork Photo

//    @PostMapping()
//    public ResponseEntity<Void> addArtwork(@RequestBody ArtworkInputDto artwork) {
//        artworkService.saveArtwork(artwork);
//        return ResponseEntity.created(null).build();
//    }

    //Add Artwork + return URI of the new artwork
    @PostMapping()
    public ResponseEntity<Void> addArtwork(@RequestBody ArtworkInputDto artwork) {
        Long newArtworkId = artworkService.saveArtwork(artwork);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newArtworkId)
                .toUri();

        return ResponseEntity.created(location).build();
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

    // TODO Add a method to update an image from an artwork
    // TODO Add a method to delete an image from an artwork

    @PostMapping("/{id}/image")
    public ResponseEntity<Artwork> addImageToArtwork(@PathVariable("id") Long artworkId, @RequestBody MultipartFile file)
            throws IOException {
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/artworks/")
                .path(Objects.requireNonNull(artworkId.toString()))
                .path("/image")
                .toUriString();
        String fileName = artworkImageService.storeFile(file);
        Artwork artwork = artworkService.assignImageToArtwork(fileName, artworkId);

        return ResponseEntity.created(URI.create(url)).body(artwork);

    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> getArtworkImage(@PathVariable("id") Long artworkId, HttpServletRequest request){
        Resource resource = artworkService.getImageFromStudent(artworkId);

        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            /*
            "application/octet-steam" is de generieke mime type voor byte data.
            Het is beter om een specifiekere mimetype te gebruiken, zoals "image/jpeg".
            Mimetype is nodig om de frontend te laten weten welke soort data het is.
            Met de juiste MimeType en Content-Disposition, kun je de plaatjes of PDFs die je upload
            zelfs in de browser weergeven.
             */
//            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            mimeType = MediaType.IMAGE_JPEG_VALUE;
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }
}
