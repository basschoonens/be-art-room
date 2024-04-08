package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ArtworkInputDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Component;

@Component
public class ArtworkTypeMapper {

    public static Artwork mapArtworkType(ArtworkInputDto artworkInputDto) {

        Artwork artwork;
        if ("painting".equalsIgnoreCase(artworkInputDto.getArtworkType())) {
            artwork = mapToPainting(artworkInputDto);
        } else if ("drawing".equalsIgnoreCase(artworkInputDto.getArtworkType())) {
            artwork = mapToDrawing(artworkInputDto);
        } else {
            throw new IllegalArgumentException("Invalid artwork type: " + artworkInputDto.getArtworkType());
        }

        artwork.setTitle(artworkInputDto.getTitle());
        artwork.setArtist(artworkInputDto.getArtist());
        artwork.setDescription(artworkInputDto.getDescription());
        artwork.setDateCreated(artworkInputDto.getDateCreated());
        artwork.setGalleryBuyingPrice(artworkInputDto.getGalleryBuyingPrice());
        artwork.setEdition(artworkInputDto.getEdition());
        artwork.setImageUrl(artworkInputDto.getImageUrl());

        return artwork;
    }

    private static Painting mapToPainting(ArtworkInputDto artworkInputDto) {
        Painting painting = new Painting();
        painting.setPaintingPaintType(artworkInputDto.getPaintingPaintType());
        painting.setPaintingSurface(artworkInputDto.getPaintingSurface());
        painting.setPaintingMaterial(artworkInputDto.getPaintingMaterial());
        painting.setPaintingDimensionsWidthInCm(artworkInputDto.getPaintingDimensionsWidthInCm());
        painting.setPaintingDimensionsHeightInCm(artworkInputDto.getPaintingDimensionsHeightInCm());

        return painting;
    }

    private static Drawing mapToDrawing(ArtworkInputDto artworkInputDto) {
        Drawing drawing = new Drawing();
        drawing.setDrawingDrawType(artworkInputDto.getDrawingDrawType());
        drawing.setDrawingMaterial(artworkInputDto.getDrawingMaterial());
        drawing.setDrawingDimensionsWidthInCm(artworkInputDto.getDrawingDimensionsWidthInCm());
        drawing.setDrawingDimensionsHeightInCm(artworkInputDto.getDrawingDimensionsHeightInCm());

        return drawing;
    }
}
