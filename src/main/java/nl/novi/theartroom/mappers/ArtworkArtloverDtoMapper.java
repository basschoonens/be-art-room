package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ArtworkArtloverDto;
import nl.novi.theartroom.dtos.DrawingOutputDto;
import nl.novi.theartroom.dtos.PaintingOutputDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;


public class ArtworkArtloverDtoMapper {

    public static ArtworkArtloverDto toArtworkArtloverDto(Artwork artwork) {

        ArtworkArtloverDto artworkArtloverDto = new ArtworkArtloverDto();
        artworkArtloverDto.setId(artwork.getId());
        artworkArtloverDto.setTitle(artwork.getTitle());
        artworkArtloverDto.setArtist(artwork.getArtist());
        artworkArtloverDto.setDescription(artwork.getDescription());
        artworkArtloverDto.setDateCreated(artwork.getDateCreated());
        artworkArtloverDto.setEdition(artwork.getEdition());
        artworkArtloverDto.setImageUrl(artwork.getImageUrl());
        artworkArtloverDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            PaintingOutputDto paintingDto = new PaintingOutputDto();
            mapPaintingFields((Painting) artwork, paintingDto);
            artworkArtloverDto.setPaintingOutputDto(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkArtloverDto.setDrawingOutputDto(drawingDto);
        }

        return artworkArtloverDto;
    }

    public static void mapDrawingFields(Drawing drawing, DrawingOutputDto drawingOutputDto) {
        drawingOutputDto.setDrawingDrawType(drawing.getDrawingDrawType());
        drawingOutputDto.setDrawingSurface(drawing.getDrawingSurface());
        drawingOutputDto.setDrawingMaterial(drawing.getDrawingMaterial());
        drawingOutputDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        drawingOutputDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

    public static void mapPaintingFields(Painting painting, PaintingOutputDto paintingOutputDto) {
        paintingOutputDto.setPaintingPaintType(painting.getPaintingPaintType());
        paintingOutputDto.setPaintingSurface(painting.getPaintingSurface());
        paintingOutputDto.setPaintingMaterial(painting.getPaintingMaterial());
        paintingOutputDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        paintingOutputDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }
}
