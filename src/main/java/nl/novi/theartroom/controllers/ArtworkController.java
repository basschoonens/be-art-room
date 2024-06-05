//package nl.novi.theartroom.controllers;
//
//import jakarta.servlet.http.HttpServletRequest;
//import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
//import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtistDto;
//import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputUserDto;
//import nl.novi.theartroom.exceptions.ArtworkNotFoundException;
//import nl.novi.theartroom.models.Artwork;
//import nl.novi.theartroom.services.ArtworkImageService;
//import nl.novi.theartroom.services.ArtworkService;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.List;
//import java.util.Objects;
//
//@RestController
//@RequestMapping(value = "/artworks")
//public class ArtworkController {
//
//    private final ArtworkService artworkService;
//    private final ArtworkImageService artworkImageService;
//
//    public ArtworkController(ArtworkService artworkService, ArtworkImageService artworkImageService) {
//        this.artworkService = artworkService;
//        this.artworkImageService = artworkImageService;
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<ArtworkOutputUserDto>> getAllArtworks() {
//        List<ArtworkOutputUserDto> artworks = artworkService.getAllArtworks();
//        return ResponseEntity.ok(artworkService.getAllArtworks());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ArtworkOutputUserDto> getArtworkById(@PathVariable Long id) {
//        ArtworkOutputUserDto artwork = artworkService.getArtworkById(id);
//        return ResponseEntity.ok(artwork);
//    }
//
//    //Add Artwork + return URI of the new artwork
//    @PostMapping()
//    public ResponseEntity<Void> addArtwork(@RequestBody ArtworkInputDto artwork) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//
//        Long newArtworkId = artworkService.saveArtworkForArtist(artwork, username);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newArtworkId)
//                .toUri();
//
//        System.out.println("Location URI: " + location);
//
//        return ResponseEntity.created(location).build();
//    }
//
////    @PostMapping("/user")
////    public ResponseEntity<Void> addArtworkForArtist(@RequestBody ArtworkInputDto artwork) {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        String username = auth.getName();
////
////        Long newArtworkId = artworkService.saveArtworkForArtist(artwork, username);
////
////        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
////                .path("/{id}")
////                .buildAndExpand(newArtworkId)
////                .toUri();
////
////        System.out.println("Location URI: " + location); // Add this line to log the location
////
////        return ResponseEntity.created(location).build();
////    }
//
//    // Deze post is alleen in mijn frontend uit te voeren, maar niet in Postman
//    // TODO huidige tijd en seconden toevoegen aan elke file die geupload wordt
//
//
////    @PostMapping("/user")
////    public ResponseEntity<Void> addArtworkForArtist(@ModelAttribute ArtworkInputDto artwork) throws IOException {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        String username = auth.getName();
////
////        // Save the artwork
////        Long newArtworkId = artworkService.saveArtworkForArtist(artwork, username);
////
////        // Store the image file
////        String fileName = artworkImageService.storeFile(artwork.getFile());
////        artworkService.assignImageToArtwork(fileName, newArtworkId);
////
////        // Create the location URI
////        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
////                .path("/{id}")
////                .buildAndExpand(newArtworkId)
////                .toUri();
////
////        return ResponseEntity.created(location).build();
////    }
//
////    @PostMapping("/user")
////    public ResponseEntity<Void> addArtworkForArtist(@RequestPart("data") String artwork, @RequestPart("file") MultipartFile file) throws IOException {
////        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////        String username = auth.getName();
////
////        // Save the artwork
////        Long newArtworkId = artworkService.saveArtworkForArtist(new ArtworkInputDto(), username);
////
////        // Store the image file
////        String fileName = artworkImageService.storeFile(file);
////        artworkService.assignImageToArtwork(fileName, newArtworkId);
////
////        // Create the location URI
////        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
////                .path("/{id}")
////                .buildAndExpand(newArtworkId)
////                .toUri();
////
////        return ResponseEntity.created(location).build();
////    }
//
//    @GetMapping("/user/artworks")
//    public ResponseEntity<List<ArtworkOutputArtistDto>> getArtworksForArtist() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String username = auth.getName();
//
//        List<ArtworkOutputArtistDto> artworks = artworkService.getArtworksByArtist(username);
//
//        return ResponseEntity.ok(artworks);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ArtworkOutputUserDto> updateArtwork(@PathVariable Long id, @RequestBody ArtworkInputDto artwork) {
//        artworkService.updateArtwork(id, artwork);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
//        artworkService.deleteArtwork(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // TODO Add a method to update an image from an artwork
//    // TODO Add a method to delete an image from an artwork
//
//    @PostMapping("/{id}/image")
//    public ResponseEntity<Artwork> addImageToArtwork(@PathVariable("id") Long artworkId, @RequestBody MultipartFile file)
//            throws IOException {
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/artworks/")
//                .path(Objects.requireNonNull(artworkId.toString()))
//                .path("/image")
//                .toUriString();
//        String fileName = artworkImageService.storeFile(file);
//        Artwork artwork = artworkService.assignImageToArtwork(fileName, artworkId);
//
//        return ResponseEntity.created(URI.create(url)).body(artwork);
//    }
//
//    @GetMapping("/{id}/image")
//    public ResponseEntity<Resource> getArtworkImage(@PathVariable("id") Long artworkId, HttpServletRequest request){
//        Resource resource = artworkService.getImageFromArtwork(artworkId);
//
//        String mimeType;
//
//        try{
//            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException e) {
////            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//            mimeType = MediaType.IMAGE_JPEG_VALUE;
//        }
//
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.parseMediaType(mimeType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
//                .body(resource);
//    }
//
    package nl.novi.theartroom.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtistDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputUserDto;
import nl.novi.theartroom.exceptions.ArtworkNotFoundException;
import nl.novi.theartroom.exceptions.UsernameNotFoundException;
import nl.novi.theartroom.services.ArtworkImageService;
import nl.novi.theartroom.services.ArtworkService;
import nl.novi.theartroom.models.Artwork;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        public ResponseEntity<List<ArtworkOutputUserDto>> getAllArtworks() {
            List<ArtworkOutputUserDto> artworks = artworkService.getAllArtworks();
            return ResponseEntity.ok(artworks);
        }

        @GetMapping("/{id}")
        public ResponseEntity<ArtworkOutputUserDto> getArtworkById(@PathVariable Long id) {
            ArtworkOutputUserDto artwork = artworkService.getArtworkById(id);
            if (artwork == null) {
                throw new ArtworkNotFoundException("Artwork with id " + id + " not found");
            }
            return ResponseEntity.ok(artwork);
        }

        @PostMapping()
        public ResponseEntity<Void> addArtwork(@RequestBody @Valid ArtworkInputDto artwork) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                throw new UsernameNotFoundException("User not authenticated");
            }
            String username = auth.getName();

            Long newArtworkId = artworkService.saveArtworkForArtist(artwork, username);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newArtworkId)
                    .toUri();

            return ResponseEntity.created(location).build();
        }

        @GetMapping("/user/artworks")
        public ResponseEntity<List<ArtworkOutputArtistDto>> getArtworksForArtist() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            List<ArtworkOutputArtistDto> artworks = artworkService.getArtworksByArtist(username);

            return ResponseEntity.ok(artworks);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> updateArtwork(@Valid @PathVariable Long id, @RequestBody ArtworkInputDto artwork) {
            artworkService.updateArtwork(id, artwork);
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteArtwork(@PathVariable Long id) {
            artworkService.deleteArtwork(id);
            return ResponseEntity.noContent().build();
        }

        @PostMapping("/{id}/image")
        public ResponseEntity<Artwork> addImageToArtwork(@Valid @PathVariable("id") Long artworkId, @RequestBody MultipartFile file)
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

