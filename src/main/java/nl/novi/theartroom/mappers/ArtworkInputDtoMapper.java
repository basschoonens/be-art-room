package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkInputDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;

@Service
public class ArtworkInputDtoMapper {

    public static Artwork toArtwork(ArtworkInputDto dto) {
//        validateArtworkInput(dto);
        if (dto.getId() == null) {
            return createArtwork(dto);
        } else {
            return updateArtwork(dto, new Artwork());
        }
    }

    public static Artwork toArtwork(ArtworkInputDto dto, Artwork existingArtwork) {
        if (existingArtwork == null) {
            throw new IllegalArgumentException("Existing artwork cannot be null for update operation.");
        }
        return updateArtwork(dto, existingArtwork);
    }

    private static Artwork createArtwork(ArtworkInputDto dto) {
        Artwork artwork;
        if ("painting".equalsIgnoreCase(dto.getArtworkType())) {
            artwork = mapToPainting(dto, new Painting());
        } else if ("drawing".equalsIgnoreCase(dto.getArtworkType())) {
            artwork = mapToDrawing(dto, new Drawing());
        } else {
            throw new IllegalArgumentException("Invalid artwork type: " + dto.getArtworkType());
        }
        setCommonFields(artwork, dto);
        return artwork;
    }

    // TODO updateArtwork method verder uitzoeken en uitwerken zodat ik de juiste gegevens kan updaten en exception kan gooien als het type niet klopt. Exception werkt nu niet want ik kan artworkType ook naar ieder ander random text aanpassen.

//    private static Artwork updateArtwork(ArtworkInputDto dto, Artwork artwork) {
//            setCommonFields(artwork, dto);
//            if ("painting".equalsIgnoreCase(dto.getArtworkType())) {
//                return mapToPainting(dto, (Painting) artwork);
//            } else if ("drawing".equalsIgnoreCase(dto.getArtworkType())) {
//                return mapToDrawing(dto, (Drawing) artwork);
//            } else {
//                throw new IllegalArgumentException("Invalid artwork type: " + dto.getArtworkType());
//            }
//    }

    private static Artwork updateArtwork(ArtworkInputDto dto, Artwork artwork) {
        setCommonFields(artwork, dto);
        if (artwork instanceof Painting) {
            return mapToPainting(dto, (Painting) artwork);
        } else if (artwork instanceof Drawing) {
            return mapToDrawing(dto, (Drawing) artwork);
        } else {
            throw new IllegalArgumentException("Invalid artwork type: " + dto.getArtworkType());
        }
    }

    private static void setCommonFields(Artwork artwork, ArtworkInputDto dto) {
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
        if (dto.getImageUrl() != null) {
            artwork.setImageUrl(dto.getImageUrl());
        }
        if (dto.getArtworkType() != null) {
            artwork.setArtworkType(dto.getArtworkType());
        }
    }

    private static Painting mapToPainting(ArtworkInputDto dto, Painting painting) {
        if (dto.getPaintingPaintType() != null) {
            painting.setPaintingPaintType(dto.getPaintingPaintType());
        }
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

    private static Drawing mapToDrawing(ArtworkInputDto dto, Drawing drawing) {
        if (dto.getDrawingDrawType() != null) {
            drawing.setDrawingDrawType(dto.getDrawingDrawType());
        }
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

    // TODO validateArtworkInput method verder uitwerken en uitbreiden met extra validatie regels voor andere velden.

//    private static void validateArtworkInput(ArtworkInputDto dto) {
//        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
//            throw new InvalidDataException("Title is required");
//        }
        // Add more validation rules as needed for other fields

}


