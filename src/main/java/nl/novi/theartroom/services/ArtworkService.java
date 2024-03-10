package nl.novi.theartroom.services;

import nl.novi.theartroom.dtos.ArtworkDto;
import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.models.Artwork;
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

    public List<ArtworkDto> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        List<ArtworkDto> artworkDtos = new ArrayList<>();

        for (Artwork artwork : artworks) {
            ArtworkDto dto = toDto(artwork);
            artworkDtos.add(dto);
        }

        return artworkDtos;
    }

    public ArtworkDto getArtworkById(Long id) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(id);
        if (optionalArtwork.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            return toDto(optionalArtwork.get());
        }
    }

    public void saveArtwork(ArtworkInputDto dto) {
        Artwork artwork = toArtwork(dto);
        artworkRepository.save(artwork);
    }

    public void updateArtwork(Long id, ArtworkInputDto dto) {
        Optional<Artwork> artworkFound = artworkRepository.findById(id);
        if (artworkFound.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            artworkRepository.save(toArtwork(dto, artworkFound.get()));
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

    //TODO Mappers naar aparte mappers map verplaatsen

    private ArtworkDto toDto(Artwork artwork) {
        ArtworkDto dto = new ArtworkDto();
        dto.setId(artwork.getId());
        dto.setTitle(artwork.getTitle());
        dto.setArtist(artwork.getArtist());
        dto.setDescription(artwork.getDescription());
        dto.setType(artwork.getType());
        dto.setDimensions(artwork.getDimensions());
        dto.setBuyingPrice(artwork.getBuyingPrice());
        dto.setSellingPrice(artwork.getSellingPrice());
        dto.setDateCreated(artwork.getDateCreated());
        dto.setEdition(artwork.getEdition());
        dto.setImageUrl(artwork.getImageUrl());

        return dto;
    }

    private Artwork toArtwork(ArtworkInputDto dto) {
        return toArtwork(dto, new Artwork());
    }

    private Artwork toArtwork(ArtworkInputDto dto, Artwork artwork) {
        if (dto.getId() != null) {
            artwork.setId(dto.getId());
        }
        if (dto.getTitle() != null) {
            artwork.setTitle(dto.getTitle());
        }
        if (dto.getArtist() != null) {
            artwork.setArtist(dto.getArtist());
        }
        if (dto.getDescription() != null) {
            artwork.setDescription(dto.getDescription());
        }
        if (dto.getType() != null) {
            artwork.setType(dto.getType());
        }
        if (dto.getDimensions() != null) {
            artwork.setDimensions(dto.getDimensions());
        }
        if (dto.getBuyingPrice() != null) {
            artwork.setBuyingPrice(dto.getBuyingPrice());
        }
        if (dto.getSellingPrice() != null) {
            artwork.setSellingPrice(dto.getSellingPrice());
        }
        if (dto.getDateCreated() != null) {
            artwork.setDateCreated(dto.getDateCreated());
        }
        if (dto.getEdition() != null) {
            artwork.setEdition(dto.getEdition());
        }
        if (dto.getImageUrl() != null) {
            artwork.setImageUrl(dto.getImageUrl());
        }

        return artwork;
    }
}
