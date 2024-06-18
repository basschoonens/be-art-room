package nl.novi.theartroom.mapper.artworkmappers;

import nl.novi.theartroom.dto.artworkdto.ArtworkInputDto;
import nl.novi.theartroom.exception.model.InvalidArtworkTypeException;
import nl.novi.theartroom.exception.util.MappingException;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.Drawing;
import nl.novi.theartroom.model.artworks.Painting;
import org.springframework.stereotype.Service;

@Service
public class ArtworkInputDtoMapper {

    public Artwork toArtwork(ArtworkInputDto dto) {
        if (dto.getArtworkId() == null) {
            return createArtwork(dto);
        } else {
            return updateArtwork(dto, new Artwork());
        }
    }

    public Artwork toArtwork(ArtworkInputDto dto, Artwork existingArtwork) {
        if (existingArtwork == null) {
            throw new MappingException("Existing artwork cannot be null for update operation.");
        }
        return updateArtwork(dto, existingArtwork);
    }

    private Artwork createArtwork(ArtworkInputDto dto) {
        Artwork artwork;
        if ("painting".equalsIgnoreCase(dto.getArtworkType())) {
            artwork = mapToPainting(dto, new Painting());
        } else if ("drawing".equalsIgnoreCase(dto.getArtworkType())) {
            artwork = mapToDrawing(dto, new Drawing());
        } else {
            throw new InvalidArtworkTypeException("Invalid artwork type: " + dto.getArtworkType());
        }
        setCommonFields(artwork, dto);
        return artwork;
    }

    private Artwork updateArtwork(ArtworkInputDto dto, Artwork artwork) {
        setCommonFields(artwork, dto);
        if (artwork instanceof Painting) {
            return mapToPainting(dto, (Painting) artwork);
        } else if (artwork instanceof Drawing) {
            return mapToDrawing(dto, (Drawing) artwork);
        } else {
            throw new InvalidArtworkTypeException("Invalid artwork type: " + dto.getArtworkType());
        }
    }

    private void setCommonFields(Artwork artwork, ArtworkInputDto dto) {
        if (dto.getTitle() != null) {
            artwork.setTitle(dto.getTitle());
        }
        if (dto.getArtist() != null) {
            artwork.setArtist(dto.getArtist());
        }
        if (dto.getDescription() != null) {
            artwork.setDescription(dto.getDescription());
        }
        if (dto.getDateCreated() != null) {
            artwork.setDateCreated(dto.getDateCreated());
        }
        if (dto.getGalleryBuyingPrice() != null) {
            artwork.setGalleryBuyingPrice(dto.getGalleryBuyingPrice());
        }
        if (dto.getEdition() != null) {
            artwork.setEdition(dto.getEdition());
        }
        if (dto.getArtworkType() != null) {
            artwork.setArtworkType(dto.getArtworkType());
        }
    }

    private Painting mapToPainting(ArtworkInputDto dto, Painting painting) {
        if (dto.getPaintingSurface() != null) {
            painting.setPaintingSurface(dto.getPaintingSurface());
        }
        if (dto.getPaintingMaterial() != null) {
            painting.setPaintingMaterial(dto.getPaintingMaterial());
        }
        if (dto.getPaintingDimensionsWidthInCm() != null) {
            painting.setPaintingDimensionsWidthInCm(dto.getPaintingDimensionsWidthInCm());
        }
        if (dto.getPaintingDimensionsHeightInCm() != null) {
            painting.setPaintingDimensionsHeightInCm(dto.getPaintingDimensionsHeightInCm());
        }
        return painting;
    }

    private Drawing mapToDrawing(ArtworkInputDto dto, Drawing drawing) {
        if (dto.getDrawingSurface() != null) {
            drawing.setDrawingSurface(dto.getDrawingSurface());
        }
        if (dto.getDrawingMaterial() != null) {
            drawing.setDrawingMaterial(dto.getDrawingMaterial());
        }
        if (dto.getDrawingDimensionsWidthInCm() != null) {
            drawing.setDrawingDimensionsWidthInCm(dto.getDrawingDimensionsWidthInCm());
        }
        if (dto.getDrawingDimensionsHeightInCm() != null) {
            drawing.setDrawingDimensionsHeightInCm(dto.getDrawingDimensionsHeightInCm());
        }
        return drawing;
    }
}