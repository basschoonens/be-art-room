package nl.novi.theartroom.services;

import nl.novi.theartroom.dtos.ArtworkDto;
import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.ArtworkTypeMapper;
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
        Artwork Artwork = ArtworkTypeMapper.mapArtworkType(dto);
        artworkRepository.save(Artwork);
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

    private ArtworkDto toDto(Artwork Artwork) {
        ArtworkDto dto = new ArtworkDto();
        dto.setId(Artwork.getId());
        dto.setTitle(Artwork.getTitle());
        dto.setArtist(Artwork.getArtist());
        dto.setDescription(Artwork.getDescription());
        dto.setDateCreated(Artwork.getDateCreated());
        dto.setGalleryBuyingPrice(Artwork.getGalleryBuyingPrice());
        dto.setEdition(Artwork.getEdition());
        dto.setImageUrl(Artwork.getImageUrl());
        dto.setArtworkType(Artwork.getArtworkType());

        return dto;
    }

    private Artwork toArtwork(ArtworkInputDto dto) {
        return toArtwork(dto, new Artwork());
    }

    private Artwork toArtwork(ArtworkInputDto dto, Artwork Artwork) {
        if (dto.getId() != null) {
            Artwork.setId(dto.getId());
        }
        if (dto.getTitle() != null) {
            Artwork.setTitle(dto.getTitle());
        }
        if (dto.getArtist() != null) {
            Artwork.setArtist(dto.getArtist());
        }
        if (dto.getDescription() != null) {
            Artwork.setDescription(dto.getDescription());
        }
        if (dto.getDateCreated() != null) {
            Artwork.setDateCreated(dto.getDateCreated());
        }
        if (dto.getGalleryBuyingPrice() != null) {
            Artwork.setGalleryBuyingPrice(dto.getGalleryBuyingPrice());
        }
        if (dto.getEdition() != null) {
            Artwork.setEdition(dto.getEdition());
        }
        if (dto.getImageUrl() != null) {
            Artwork.setImageUrl(dto.getImageUrl());
        }
        if (dto.getArtworkType() != null) {
            Artwork.setArtworkType(dto.getArtworkType());
        }

        return Artwork;
    }
}
