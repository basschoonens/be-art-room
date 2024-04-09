package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ArtworkAdminDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;

public class ArtworkAdminDtoMapper {

    public static ArtworkAdminDto toArtworkAdminDto(Artwork artwork) {
        ArtworkAdminDto artworkAdminDto = new ArtworkAdminDto();
        artworkAdminDto.setId(artwork.getId());
        artworkAdminDto.setTitle(artwork.getTitle());
        artworkAdminDto.setArtist(artwork.getArtist());
        artworkAdminDto.setDescription(artwork.getDescription());
        artworkAdminDto.setDateCreated(artwork.getDateCreated());
        artworkAdminDto.setGalleryBuyingPrice(artwork.getGalleryBuyingPrice());
        artworkAdminDto.setEdition(artwork.getEdition());
        artworkAdminDto.setImageUrl(artwork.getImageUrl());
        artworkAdminDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            mapPaintingFields((Painting) artwork, artworkAdminDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            mapDrawingFields((Drawing) artwork, artworkAdminDto);
        }

        return artworkAdminDto;
    }

    private static void mapPaintingFields(Painting painting, ArtworkAdminDto artworkAdminDto) {
        artworkAdminDto.setPaintingPaintType(painting.getPaintingPaintType());
        artworkAdminDto.setPaintingSurface(painting.getPaintingSurface());
        artworkAdminDto.setPaintingMaterial(painting.getPaintingMaterial());
        artworkAdminDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        artworkAdminDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }

    private static void mapDrawingFields(Drawing drawing, ArtworkAdminDto artworkAdminDto) {
        artworkAdminDto.setDrawingDrawType(drawing.getDrawingDrawType());
        artworkAdminDto.setDrawingSurface(drawing.getDrawingSurface());
        artworkAdminDto.setDrawingMaterial(drawing.getDrawingMaterial());
        artworkAdminDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        artworkAdminDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

}
