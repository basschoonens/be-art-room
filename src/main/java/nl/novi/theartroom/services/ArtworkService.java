package nl.novi.theartroom.services;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtistDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputUserDto;
import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.exceptions.RecordNotFoundException;
import nl.novi.theartroom.mappers.ArtworkArtistDtoMapper;
import nl.novi.theartroom.mappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.mappers.ArtworkInputDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.ArtworkImage;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.FileUploadRepository;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final FileUploadRepository uploadRepository;
    private final ArtworkImageService photoService;
    private final UserService userService;
    private final ArtworkInputDtoMapper artworkInputDtoMapper;
    private final ArtworkUserDtoMapper artworkUserDtoMapper;
    private final ArtworkArtistDtoMapper artworkArtistDtoMapper;

    public ArtworkService(ArtworkRepository artworkRepository, FileUploadRepository uploadRepository, ArtworkImageService photoService, UserService userService, ArtworkInputDtoMapper artworkInputDtoMapper, ArtworkUserDtoMapper artworkUserDtoMapper, ArtworkArtistDtoMapper artworkArtistDtoMapper) {
        this.artworkRepository = artworkRepository;
        this.uploadRepository = uploadRepository;
        this.photoService = photoService;
        this.userService = userService;
        this.artworkInputDtoMapper = artworkInputDtoMapper;
        this.artworkUserDtoMapper = artworkUserDtoMapper;
        this.artworkArtistDtoMapper = artworkArtistDtoMapper;
    }

    public List<ArtworkOutputUserDto> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(artworkUserDtoMapper::toArtworkArtloverDto)
                .collect(Collectors.toList());
    }

    public ArtworkOutputUserDto getArtworkById(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Artwork with id " + id + " not found."));
        return artworkUserDtoMapper.toArtworkArtloverDto(artwork);
    }

    // save artwork + return URI location
    public Long saveArtwork(ArtworkInputDto dto) {
        Artwork artwork = artworkInputDtoMapper.toArtwork(dto);
        Artwork savedArtwork = artworkRepository.save(artwork);
        return savedArtwork.getId();
    }

    // Save artwork for Artist
    public Long saveArtworkForArtist(ArtworkInputDto dto, String username) {
        Artwork artwork = artworkInputDtoMapper.toArtwork(dto);
        User user = userService.getUserByUsername(username);
        artwork.setUser(user);
        Artwork savedArtwork = artworkRepository.save(artwork);
        return savedArtwork.getId();
    }

    public List<ArtworkOutputArtistDto> getArtworksByArtist(String username) {
        User user = userService.getUserByUsername(username);
        List<Artwork> artworks = artworkRepository.findAllByUser(user);
        return artworks.stream()
                .map(artworkArtistDtoMapper::toArtworkArtistDto)
                .collect(Collectors.toList());
    }

    public void updateArtwork(Long id, ArtworkInputDto dto) {
        Optional<Artwork> artworkFound = artworkRepository.findById(id);
        if (artworkFound.isEmpty()) {
            throw new RecordNotFoundException("Artwork with id " + id + " not found.");
        } else {
            artworkRepository.save(artworkInputDtoMapper.toArtwork(dto, artworkFound.get()));
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

    // Image methods

    @Transactional
    public Resource getImageFromArtwork(Long artworkId){
        Optional<Artwork> optionalArtwork = artworkRepository.findById(artworkId);
        if(optionalArtwork.isEmpty()){
            throw new RecordNotFoundException("Artwork with artwork number " + artworkId + " not found.");
        }
        ArtworkImage photo = optionalArtwork.get().getArtworkImage();
        if(photo == null){
            throw new RecordNotFoundException("Artwork " + artworkId + " had no photo.");
        }
        return photoService.downLoadFile(photo.getFileName());
    }

    @Transactional
    public Artwork assignImageToArtwork(String filename, Long artworkId) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(artworkId);
        Optional<ArtworkImage> optionalPhoto = uploadRepository.findByFileName(filename);

        if (optionalArtwork.isPresent() && optionalPhoto.isPresent()) {
            ArtworkImage photo = optionalPhoto.get();
            Artwork artwork = optionalArtwork.get();
            artwork.setArtworkImage(photo);
            return artworkRepository.save(artwork);
        } else {
            throw new RecordNotFoundException("artwork of foto niet gevonden");
        }
    }

}