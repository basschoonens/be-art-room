package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.ArtworkOutputArtloverDto;
import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.ArtworkArtloverDtoMapper;
import nl.novi.theartroom.mappers.ArtworkInputDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Rating;
import nl.novi.theartroom.repositories.ArtworkRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;

    public ArtworkService(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public List<ArtworkOutputArtloverDto> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        List<ArtworkOutputArtloverDto> artworkDtos = new ArrayList<>();

        for (Artwork artwork : artworks) {
            ArtworkOutputArtloverDto dto = ArtworkArtloverDtoMapper.toArtworkArtloverDto(artwork);
            artworkDtos.add(dto);
        }

        return artworkDtos;
    }

    public ArtworkOutputArtloverDto getArtworkById(Long id) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            return ArtworkArtloverDtoMapper.toArtworkArtloverDto(optionalArtwork.get());
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