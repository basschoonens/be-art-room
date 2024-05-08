package nl.novi.theartroom.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtloverDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.ArtworkArtloverDtoMapper;
import nl.novi.theartroom.mappers.ArtworkInputDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.repositories.ArtworkRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final RatingService ratingService;

    public ArtworkService(ArtworkRepository artworkRepository, RatingService ratingService) {
        this.artworkRepository = artworkRepository;
        this.ratingService = ratingService;
    }

    // TODO Add the total ammount of ratings to the artwork

    public List<ArtworkOutputArtloverDto> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        List<ArtworkOutputArtloverDto> artworkDtos = new ArrayList<>();

        for (Artwork artwork : artworks) {
            ArtworkOutputArtloverDto dto = ArtworkArtloverDtoMapper.toArtworkArtloverDto(artwork);
            double averageRating = ratingService.calculateAverageRatingForArtwork(artwork.getId());
            dto.setAverageRating(averageRating);
            artworkDtos.add(dto);
        }

        return artworkDtos;
    }

    public ArtworkOutputArtloverDto getArtworkById(Long id) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            Artwork artwork = optionalArtwork.get();
            double averageRating = ratingService.calculateAverageRatingForArtwork(id);
            ArtworkOutputArtloverDto dto = ArtworkArtloverDtoMapper.toArtworkArtloverDto(artwork);
            dto.setAverageRating(averageRating);

            return dto;
        }
    }

    // GetPhotoForArtwork

    @Transactional
    public ResponseEntity<byte[]> getPhotoForArtwork(Long id) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isEmpty()) {
            throw new RecordNotFoundException("Artwork with ID " + id + " not found.");
        }

        String imagePath = optionalArtwork.get().getImagePath();
        if (imagePath == null) {
            throw new RecordNotFoundException("Artwork with ID " + id + " has no associated image.");
        }

        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);
            headers.setContentDispositionFormData("attachment", path.getFileName().toString());

            return ResponseEntity.ok().headers(headers).body(imageBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }
    }


    public void saveArtwork(ArtworkInputDto dto) {
        Artwork Artwork = ArtworkInputDtoMapper.toArtwork(dto);
        artworkRepository.save(Artwork);
    }

    public void updateArtwork(Long id, ArtworkInputDto dto) {
        Optional<Artwork> artworkFound = artworkRepository.findById(id);
        if (artworkFound.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            artworkRepository.save(ArtworkInputDtoMapper.toArtwork(dto, artworkFound.get()));
        }
    }

    public void deleteArtwork(Long id) {
        Optional<Artwork> artworkFound = artworkRepository.findById(id);
        if (artworkFound.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            artworkRepository.delete(artworkFound.get());
        }
    }
}