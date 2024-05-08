package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtloverDto;
import nl.novi.theartroom.dtos.artworkdtos.DrawingOutputDto;
import nl.novi.theartroom.dtos.artworkdtos.PaintingOutputDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkArtloverDtoMapper {

    public static ArtworkOutputArtloverDto toArtworkArtloverDto(Artwork artwork) {

        ArtworkOutputArtloverDto artworkOutputArtloverDto = new ArtworkOutputArtloverDto();
        artworkOutputArtloverDto.setId(artwork.getId());
        artworkOutputArtloverDto.setTitle(artwork.getTitle());
        artworkOutputArtloverDto.setArtist(artwork.getArtist());
        artworkOutputArtloverDto.setDescription(artwork.getDescription());
        artworkOutputArtloverDto.setDateCreated(artwork.getDateCreated());
        artworkOutputArtloverDto.setEdition(artwork.getEdition());
        artworkOutputArtloverDto.setImagePath(artwork.getImagePath());
        artworkOutputArtloverDto.setArtworkType(artwork.getArtworkType());

        if (artwork.getRatings() != null) {
            List<RatingUserDto> ratingUserDtos = RatingDtoMapper.toRatingUserDtoList(artwork.getRatings());
            artworkOutputArtloverDto.setRatings(ratingUserDtos);
        }

        // TODO uitzoeken welke excepties hier gegooid kunnen worden

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            PaintingOutputDto paintingDto = new PaintingOutputDto();
            mapPaintingFields((Painting) artwork, paintingDto);
            artworkOutputArtloverDto.setPaintingOutputDto(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkOutputArtloverDto.setDrawingOutputDto(drawingDto);
//        } else {
//            throw new IllegalArgumentException("Invalid artwork type: " + artwork.getArtworkType());
//            throw new ClassCastException("Invalid artwork type: " + artwork.getArtworkType());
        }

        return artworkOutputArtloverDto;
    }

    public static void mapDrawingFields(Drawing drawing, DrawingOutputDto drawingOutputDto) {
        drawingOutputDto.setDrawingSurface(drawing.getDrawingSurface());
        drawingOutputDto.setDrawingMaterial(drawing.getDrawingMaterial());
        drawingOutputDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        drawingOutputDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

    public static void mapPaintingFields(Painting painting, PaintingOutputDto paintingOutputDto) {
        paintingOutputDto.setPaintingSurface(painting.getPaintingSurface());
        paintingOutputDto.setPaintingMaterial(painting.getPaintingMaterial());
        paintingOutputDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        paintingOutputDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }


}
