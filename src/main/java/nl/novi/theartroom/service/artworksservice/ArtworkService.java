package nl.novi.theartroom.service.artworksservice;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputArtistAdminDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkInputDto;
import nl.novi.theartroom.exception.database.DatabaseException;
import nl.novi.theartroom.exception.model.ArtworkNotFoundException;
import nl.novi.theartroom.exception.model.InvalidArtworkTypeException;
import nl.novi.theartroom.exception.auth.UnauthorizedAccessException;
import nl.novi.theartroom.exception.util.MappingException;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkArtistAdminDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkInputDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.ArtworkImage;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.FileUploadRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
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
    private final ArtworkArtistAdminDtoMapper artworkArtistAdminDtoMapper;

    public ArtworkService(ArtworkRepository artworkRepository, FileUploadRepository uploadRepository, ArtworkImageService photoService, UserService userService, ArtworkInputDtoMapper artworkInputDtoMapper, ArtworkUserDtoMapper artworkUserDtoMapper, ArtworkArtistAdminDtoMapper artworkArtistAdminDtoMapper) {
        this.artworkRepository = artworkRepository;
        this.uploadRepository = uploadRepository;
        this.photoService = photoService;
        this.userService = userService;
        this.artworkInputDtoMapper = artworkInputDtoMapper;
        this.artworkUserDtoMapper = artworkUserDtoMapper;
        this.artworkArtistAdminDtoMapper = artworkArtistAdminDtoMapper;
    }

    // UNAUTHENTICATED ARTWORKS METHOD

    public List<ArtworkOutputUserDto> getAllArtworks() {
        List<Artwork> artworks = artworkRepository.findAll();
        return artworks.stream()
                .map(artworkUserDtoMapper::toArtworkUserDto)
                .collect(Collectors.toList());
    }

    public ArtworkOutputUserDto getArtworkById(Long artworkId) {
        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found."));
        return artworkUserDtoMapper.toArtworkUserDto(artwork);
    }

    // ARTIST ARTWORKS METHOD

    public List<ArtworkOutputArtistAdminDto> getArtworksByArtist(String username) {
        User user = userService.getUserByUsername(username);
        List<Artwork> artworks = artworkRepository.findAllByUser(user);

        return artworks.stream()
                .map(artworkArtistAdminDtoMapper::toArtworkArtistDto)
                .collect(Collectors.toList());
    }

    public ArtworkOutputArtistAdminDto getArtworkByArtist(Long artworkId, String username) {
        Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
        if (artworkFound.isEmpty()) {
            throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
        }
        Artwork artwork = artworkFound.get();
        if (!artwork.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("User not authorized to view this artwork");
        }
        return artworkArtistAdminDtoMapper.toArtworkArtistDto(artwork);
    }

    @Transactional
    public Long createArtworkForArtist(ArtworkInputDto dto, String username) {
        try {
            Artwork artwork = artworkInputDtoMapper.toArtwork(dto);
            User user = userService.getUserByUsername(username);
            artwork.setUser(user);
            Artwork savedArtwork = artworkRepository.save(artwork);
            return savedArtwork.getArtworkId();
        } catch (MappingException e) {
            throw new MappingException("Error mapping artwork to the database", e);
        } catch (DataAccessException e) {
            throw new DatabaseException("Error saving artwork to the database", e);
        }
    }

    @Transactional
    public void updateArtworkForArtist(Long artworkId, ArtworkInputDto dto, String username) {
        try {
            Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
            if (artworkFound.isEmpty()) {
                throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
            }
            Artwork existingArtwork = artworkFound.get();
            if (!existingArtwork.getUser().getUsername().equals(username)) {
                throw new UnauthorizedAccessException("User not authorized to update this artwork");
            }
            if (!existingArtwork.getArtworkType().equals(dto.getArtworkType())) {
                throw new InvalidArtworkTypeException("Artwork type cannot be changed. Please review your inputdata and try again, or create a new artwork.");
            }
            Artwork updatedArtwork = artworkInputDtoMapper.toArtwork(dto, existingArtwork);
            artworkRepository.save(updatedArtwork);
        } catch (MappingException e) {
            throw new MappingException("Error mapping artwork to the database", e);
        } catch (DataAccessException e) {
            throw new DatabaseException("Error saving artwork to the database", e);
        }
    }

    public void deleteArtworkForArtist(Long artworkId, String username) {
        Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
        if (artworkFound.isEmpty()) {
            throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
        } else {
            Artwork artwork = artworkFound.get();
            if (!artwork.getArtist().equals(username))
                throw new UnauthorizedAccessException("User not authorized to delete this artwork");
            if (!artwork.getUser().getUsername().equals(username)) {
                throw new UnauthorizedAccessException("User not authorized to delete this artwork");
            }
            if (artwork.getArtworkImage() != null) {
                uploadRepository.delete(artwork.getArtworkImage());
            }
            artworkRepository.delete(artwork);
        }
    }

//    public void deleteArtworkForArtist(Long artworkId, String username) {
//        Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
//
//        if (artworkFound.isEmpty()) {
//            throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
//        }
//
//        Artwork artwork = artworkFound.get();
//
//        // Ensure the logged-in user is the artist
//        if (!artwork.getArtist().equals(username)) {
//            throw new UnauthorizedAccessException("User not authorized to delete this artwork");
//        }
//
//        // Optionally, check if the artwork image exists and delete it from the repository
//        if (artwork.getArtworkImage() != null) {
//            uploadRepository.delete(artwork.getArtworkImage());
//        }
//
//        // Finally, delete the artwork itself
//        artworkRepository.delete(artwork);
//    }

    // IMAGE METHODS

    @Transactional
    public Artwork addOrUpdateImageToArtwork(String filename, Long artworkId) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(artworkId);
        Optional<ArtworkImage> optionalPhoto = uploadRepository.findByFileName(filename);

        if (optionalArtwork.isPresent() && optionalPhoto.isPresent()) {
            ArtworkImage photo = optionalPhoto.get();
            Artwork artwork = optionalArtwork.get();
            artwork.setArtworkImage(photo);
            return artworkRepository.save(artwork);
        } else {
            throw new ArtworkNotFoundException("Artwork or Photo not found.");
        }
    }

    @Transactional
    public Resource getImageForArtwork(Long artworkId) {
        Optional<Artwork> optionalArtwork = artworkRepository.findById(artworkId);
        if (optionalArtwork.isEmpty()) {
            throw new ArtworkNotFoundException("Artwork with artwork number " + artworkId + " not found.");
        }
        ArtworkImage photo = optionalArtwork.get().getArtworkImage();
        if (photo == null) {
            throw new ArtworkNotFoundException("Artwork " + artworkId + " had no photo.");
        }
        return photoService.downLoadFile(photo.getFileName());
    }

    // ADMIN ARTWORKS METHOD

    public void updateArtworkForAdmin(Long artworkId, ArtworkInputDto dto) {
        try {
            Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
            if (artworkFound.isEmpty()) {
                throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
            }
            Artwork existingArtwork = artworkFound.get();
            if (!existingArtwork.getArtworkType().equals(dto.getArtworkType())) {
                throw new InvalidArtworkTypeException("Artwork type cannot be changed. Please review your inputdata and try again, or create a new artwork.");
            }
            Artwork updatedArtwork = artworkInputDtoMapper.toArtwork(dto, existingArtwork);
            artworkRepository.save(updatedArtwork);
        } catch (MappingException e) {
            throw new MappingException("Error mapping artwork to the database", e);
        } catch (DataAccessException e) {
            throw new DatabaseException("Error saving artwork to the database", e);
        }
    }

    public void deleteArtworkForAdmin(Long artworkId) {
        Optional<Artwork> artworkFound = artworkRepository.findById(artworkId);
        if (artworkFound.isEmpty()) {
            throw new ArtworkNotFoundException("Artwork with artworkId " + artworkId + " not found.");
        } else {
            Artwork artwork = artworkFound.get();
            if (artwork.getArtworkImage() != null) {
                uploadRepository.delete(artwork.getArtworkImage());
            }
            artworkRepository.delete(artwork);
        }
    }
}