package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputUserDto;
import nl.novi.theartroom.dtos.artworkdtos.DrawingOutputDto;
import nl.novi.theartroom.dtos.artworkdtos.PaintingOutputDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkUserDtoMapper {

    public ArtworkOutputUserDto toArtworkArtloverDto(Artwork artwork) {

        ArtworkOutputUserDto artworkOutputUserDto = new ArtworkOutputUserDto();
        artworkOutputUserDto.setId(artwork.getId());
        artworkOutputUserDto.setTitle(artwork.getTitle());
        artworkOutputUserDto.setArtist(artwork.getArtist());
        artworkOutputUserDto.setDescription(artwork.getDescription());
        artworkOutputUserDto.setDateCreated(artwork.getDateCreated());
        artworkOutputUserDto.setEdition(artwork.getEdition());
        artworkOutputUserDto.setImage(artwork.getArtworkImage());
        artworkOutputUserDto.setArtworkType(artwork.getArtworkType());

        if (artwork.getRatings() != null) {
            RatingDtoMapper ratingDtoMapper = new RatingDtoMapper();
            List<RatingUserDto> ratingUserDtos = ratingDtoMapper.toRatingUserDtoList(artwork.getRatings());
            artworkOutputUserDto.setRatings(ratingUserDtos);
        }

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            PaintingOutputDto paintingDto = new PaintingOutputDto();
            mapPaintingFields((Painting) artwork, paintingDto);
            artworkOutputUserDto.setPaintingOutputDto(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkOutputUserDto.setDrawingOutputDto(drawingDto);
        }

        return artworkOutputUserDto;
    }

    private void mapDrawingFields(Drawing drawing, DrawingOutputDto drawingOutputDto) {
        drawingOutputDto.setDrawingSurface(drawing.getDrawingSurface());
        drawingOutputDto.setDrawingMaterial(drawing.getDrawingMaterial());
        drawingOutputDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        drawingOutputDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

    private void mapPaintingFields(Painting painting, PaintingOutputDto paintingOutputDto) {
        paintingOutputDto.setPaintingSurface(painting.getPaintingSurface());
        paintingOutputDto.setPaintingMaterial(painting.getPaintingMaterial());
        paintingOutputDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        paintingOutputDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }
}