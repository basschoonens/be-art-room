package nl.novi.theartroom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.theartroom.dto.artworkdto.ArtworkInputDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputArtistAdminDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;
import nl.novi.theartroom.service.artworksservice.ArtworkImageService;
import nl.novi.theartroom.service.artworksservice.ArtworkService;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.service.userservice.UserService;
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
        private final UserService userService;

        public ArtworkController(ArtworkService artworkService, ArtworkImageService artworkImageService, UserService userService) {
            this.artworkService = artworkService;
            this.artworkImageService = artworkImageService;
            this.userService = userService;
        }

        // USER ARTWORKS METHOD

        @GetMapping()
        public ResponseEntity<List<ArtworkOutputUserDto>> getAllArtworks() {
            List<ArtworkOutputUserDto> artworks = artworkService.getAllArtworks();
            return ResponseEntity.ok(artworks);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ArtworkOutputUserDto> getArtworkById(@PathVariable Long id) {
            ArtworkOutputUserDto artwork = artworkService.getArtworkById(id);
            return ResponseEntity.ok(artwork);
        }

        // ARTIST ARTWORKS METHOD

        @GetMapping("/user/artworks")
        public ResponseEntity<List<ArtworkOutputArtistAdminDto>> getArtworksByArtist() {
            String username = userService.getCurrentLoggedInUsername();
            List<ArtworkOutputArtistAdminDto> artworks = artworkService.getArtworksByArtist(username);
            return ResponseEntity.ok(artworks);
        }

        @PostMapping()
        public ResponseEntity<Void> createArtworkForArtist(@RequestBody @Valid ArtworkInputDto artwork) {
            String username = userService.getCurrentLoggedInUsername();
            Long newArtworkId = artworkService.createArtworkForArtist(artwork, username);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newArtworkId)
                    .toUri();

            return ResponseEntity.created(location).build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> updateArtworkForArtist(@PathVariable Long id, @RequestBody @Valid ArtworkInputDto artwork) {
            String username = userService.getCurrentLoggedInUsername();
            artworkService.updateArtworkForArtist(id, artwork, username);
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
            artworkService.deleteArtwork(id);
            return ResponseEntity.noContent().build();
        }

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
            Resource resource = artworkService.getImageFromArtwork(artworkId);

            String mimeType;

            try{
                mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                mimeType = MediaType.IMAGE_JPEG_VALUE;
            }

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                    .body(resource);
        }
}

