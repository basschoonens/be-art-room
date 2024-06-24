package nl.novi.theartroom.service.artworksservice;

import nl.novi.theartroom.dto.artworkdto.ArtworkInputDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputArtistAdminDto;
import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;
import nl.novi.theartroom.exception.database.DatabaseException;
import nl.novi.theartroom.exception.model.ArtworkNotFoundException;
import nl.novi.theartroom.exception.model.InvalidArtworkTypeException;
import nl.novi.theartroom.exception.auth.UnauthorizedAccessException;
import nl.novi.theartroom.exception.util.MappingException;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkArtistAdminDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkInputDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.ArtworkImage;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.FileUploadRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtworkServiceTest {

    @Mock
    private ArtworkRepository artworkRepository;

    @Mock
    private FileUploadRepository uploadRepository;

    @Mock
    private ArtworkImageService photoService;

    @Mock
    private UserService userService;

    @Mock
    private ArtworkInputDtoMapper artworkInputDtoMapper;

    @Mock
    private ArtworkUserDtoMapper artworkUserDtoMapper;

    @Mock
    private ArtworkArtistAdminDtoMapper artworkArtistAdminDtoMapper;

    @InjectMocks
    private ArtworkService artworkService;

    private Artwork artwork;
    private ArtworkImage artworkImage;
    private ArtworkInputDto artworkInputDto;
    private ArtworkOutputUserDto artworkOutputUserDto;
    private ArtworkOutputArtistAdminDto artworkOutputArtistAdminDto;
    private User user;

    @BeforeEach
    void setUp() {
        artwork = new Artwork();
        artwork.setArtworkId(1L);
        artwork.setArtworkType("Painting");

        artworkImage = new ArtworkImage();
        artworkImage.setFileName("image.jpg");

        user = new User();
        user.setUsername("artist");

        artwork.setUser(user);
        artwork.setArtworkImage(artworkImage);
        artwork.setArtist("artist");

        artworkInputDto = new ArtworkInputDto();
        artworkInputDto.setArtworkType("Painting");

        artworkOutputUserDto = new ArtworkOutputUserDto();
        artworkOutputUserDto.setArtworkId(1L);

        artworkOutputArtistAdminDto = new ArtworkOutputArtistAdminDto();
        artworkOutputArtistAdminDto.setArtworkId(1L);
    }

    // Tests for getAllArtworks
    @Test
    void getAllArtworks_shouldReturnListOfArtworkOutputUserDtos() {
        // Arrange
        when(artworkRepository.findAll()).thenReturn(List.of(artwork));
        when(artworkUserDtoMapper.toArtworkUserDto(any())).thenReturn(artworkOutputUserDto);

        // Act
        List<ArtworkOutputUserDto> result = artworkService.getAllArtworks();

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getArtworkId());
    }

    // Tests for getArtworkById
    @Test
    void getArtworkById_shouldReturnArtworkOutputUserDto() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkUserDtoMapper.toArtworkUserDto(any())).thenReturn(artworkOutputUserDto);

        // Act
        ArtworkOutputUserDto result = artworkService.getArtworkById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getArtworkId());
    }

    @Test
    void getArtworkById_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> artworkService.getArtworkById(1L));
    }

    // Tests for getArtworksByArtist
    @Test
    void getArtworksByArtist_shouldReturnListOfArtworkOutputArtistAdminDtos() {
        // Arrange
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(artworkRepository.findAllByUser(any())).thenReturn(List.of(artwork));
        when(artworkArtistAdminDtoMapper.toArtworkArtistDto(any())).thenReturn(artworkOutputArtistAdminDto);

        // Act
        List<ArtworkOutputArtistAdminDto> result = artworkService.getArtworksByArtist("artist");

        // Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getArtworkId());
    }

    // Tests for getArtworkByArtist
    @Test
    void getArtworkByArtist_shouldReturnArtworkOutputArtistAdminDto() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkArtistAdminDtoMapper.toArtworkArtistDto(any())).thenReturn(artworkOutputArtistAdminDto);

        // Act
        ArtworkOutputArtistAdminDto result = artworkService.getArtworkByArtist(1L, "artist");

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getArtworkId());
    }

    @Test
    void getArtworkByArtist_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> artworkService.getArtworkByArtist(1L, "artist"));
    }

    @Test
    void getArtworkByArtist_shouldThrowUnauthorizedAccessException() {
        // Arrange
        artwork.getUser().setUsername("otherArtist");
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> artworkService.getArtworkByArtist(1L, "artist"));
    }

    // Tests for createArtworkForArtist
    @Test
    void createArtworkForArtist_shouldReturnArtworkId() {
        // Arrange
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(artworkInputDtoMapper.toArtwork(any())).thenReturn(artwork);
        when(artworkRepository.save(any())).thenReturn(artwork);

        // Act
        Long result = artworkService.createArtworkForArtist(artworkInputDto, "artist");

        // Assert
        assertEquals(1L, result);
    }

    @Test
    void createArtworkForArtist_shouldThrowMappingException() {
        // Arrange
        when(artworkInputDtoMapper.toArtwork(artworkInputDto)).thenThrow(new MappingException("Error mapping artwork to the database"));

        // Act & Assert
        MappingException exception = assertThrows(MappingException.class, () ->
                artworkService.createArtworkForArtist(artworkInputDto, "artist")
        );

        // Assert
        assertEquals("Error mapping artwork to the database", exception.getMessage());
    }

    @Test
    void createArtworkForArtist_shouldThrowDatabaseException() {
        // Arrange
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(artworkInputDtoMapper.toArtwork(any())).thenReturn(artwork);
        when(artworkRepository.save(any())).thenThrow(new DataAccessException("") {});

        // Act & Assert
        assertThrows(DatabaseException.class, () -> artworkService.createArtworkForArtist(artworkInputDto, "artist"));
    }

    // Tests for updateArtworkForArtist
    @Test
    void updateArtworkForArtist_shouldUpdateArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenReturn(artwork);

        // Act
        artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist");

        // Assert
        assertEquals("Painting", artwork.getArtworkType());
    }

    @Test
    void updateArtworkForArtist_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Throwable exception = assertThrows(ArtworkNotFoundException.class, () ->
                artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist")
        );

        // Assert
        assertNotNull(exception);
    }

    @Test
    void updateArtworkForArtist_shouldThrowUnauthorizedAccessException() {
        // Arrange
        artwork.getUser().setUsername("otherArtist");
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act
        Throwable exception = assertThrows(UnauthorizedAccessException.class, () ->
                artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist")
        );

        // Assert
        assertNotNull(exception);
    }

    @Test
    void updateArtworkForArtist_shouldThrowInvalidArtworkTypeException() {
        // Arrange
        artwork.setArtworkType("Sculpture");
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act & Assert
        assertThrows(InvalidArtworkTypeException.class, () -> {
            artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist");
        });
    }

    @Test
    void updateArtworkForArtist_shouldThrowMappingException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenThrow(new MappingException(""));

        // Act & Assert
        assertThrows(MappingException.class, () -> {
            artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist");
        });
    }

    @Test
    void updateArtworkForArtist_shouldThrowDatabaseException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenReturn(artwork);
        when(artworkRepository.save(any())).thenThrow(new DataAccessException("") {});

        // Act & Assert
        assertThrows(DatabaseException.class, () -> {
            artworkService.updateArtworkForArtist(1L, artworkInputDto, "artist");
        });
    }

    @Test
    void deleteArtworkForArtist_shouldDeleteArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        doNothing().when(artworkRepository).delete(any());

        // Act
        artworkService.deleteArtworkForArtist(1L, "artist");

        // Assert
        verify(artworkRepository, times(1)).delete(any());
    }

    @Test
    void deleteArtworkForArtist_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.deleteArtworkForArtist(1L, "artist");
        });
    }

    @Test
    void deleteArtworkForArtist_shouldThrowUnauthorizedAccessException() {
        // Arrange
        artwork.getUser().setUsername("otherArtist");
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> {
            artworkService.deleteArtworkForArtist(1L, "artist");
        });
    }

    @Test
    void deleteArtworkForArtist_shouldThrowUnauthorizedAccessExceptionForDifferentArtist() {
        // Arrange
        artwork.setArtist("differentArtist");
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () -> {
            artworkService.deleteArtworkForArtist(1L, "artist");
        });
    }

    @Test
    void addOrUpdateImageToArtwork_shouldReturnUpdatedArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(uploadRepository.findByFileName(anyString())).thenReturn(Optional.of(artworkImage));
        when(artworkRepository.save(any())).thenReturn(artwork);

        // Act
        Artwork result = artworkService.addOrUpdateImageToArtwork("image.jpg", 1L);

        // Assert
        assertNotNull(result);
        assertEquals(artworkImage, result.getArtworkImage());
    }

    @Test
    void addOrUpdateImageToArtwork_shouldThrowRecordNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.addOrUpdateImageToArtwork("image.jpg", 1L);
        });
    }

    @Test
    void getImageForArtwork_shouldReturnResource() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(photoService.downLoadFile(anyString())).thenReturn(mock(Resource.class));

        // Act
        Resource result = artworkService.getImageForArtwork(1L);

        // Assert
        assertNotNull(result);
    }

    @Test
    void getImageForArtwork_shouldThrowRecordNotFoundExceptionForArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.getImageForArtwork(1L);
        });
    }

    @Test
    void getImageForArtwork_shouldThrowRecordNotFoundExceptionForImage() {
        // Arrange
        artwork.setArtworkImage(null);
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.getImageForArtwork(1L);
        });
    }

    @Test
    void updateArtworkForAdmin_shouldUpdateArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenReturn(artwork);

        // Act
        artworkService.updateArtworkForAdmin(1L, artworkInputDto);

        // Assert
        verify(artworkRepository, times(1)).save(any(Artwork.class));
    }

    @Test
    void updateArtworkForAdmin_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.updateArtworkForAdmin(1L, artworkInputDto);
        });
    }

    @Test
    void updateArtworkForAdmin_shouldThrowInvalidArtworkTypeException() {
        // Arrange
        Artwork existingArtwork = new Artwork();
        existingArtwork.setArtworkId(1L);
        existingArtwork.setArtworkType("Painting");

        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(existingArtwork));
        artworkInputDto.setArtworkType("Sculpture");

        // Act & Assert
        assertThrows(InvalidArtworkTypeException.class, () -> {
            artworkService.updateArtworkForAdmin(1L, artworkInputDto);
        });

        verify(artworkRepository, never()).save(any());
    }

    @Test
    void updateArtworkForAdmin_shouldThrowMappingException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenThrow(new MappingException(""));

        // Act & Assert
        assertThrows(MappingException.class, () -> {
            artworkService.updateArtworkForAdmin(1L, artworkInputDto);
        });
    }

    @Test
    void updateArtworkForAdmin_shouldThrowDatabaseException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));
        when(artworkInputDtoMapper.toArtwork(any(), any())).thenReturn(artwork);
        when(artworkRepository.save(any())).thenThrow(new DataAccessException("") {});

        // Act & Assert
        assertThrows(DatabaseException.class, () -> {
            artworkService.updateArtworkForAdmin(1L, artworkInputDto);
        });
    }

    @Test
    void deleteArtworkForAdmin_shouldDeleteArtwork() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.of(artwork));

        // Act
        artworkService.deleteArtworkForAdmin(1L);

        // Assert
        verify(artworkRepository, times(1)).delete(any(Artwork.class));
    }

    @Test
    void deleteArtworkForAdmin_shouldThrowArtworkNotFoundException() {
        // Arrange
        when(artworkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ArtworkNotFoundException.class, () -> {
            artworkService.deleteArtworkForAdmin(1L);
        });
    }
}