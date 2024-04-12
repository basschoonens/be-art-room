package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ArtworkOutputAdminDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;


// ADMIN MAPPER METHODS
@Service
public class ArtworkAdminDtoMapper {

    public static ArtworkOutputAdminDto toArtworkAdminDto(Artwork artwork) {
        ArtworkOutputAdminDto artworkOutputAdminDto = new ArtworkOutputAdminDto();
        artworkOutputAdminDto.setId(artwork.getId());
        artworkOutputAdminDto.setTitle(artwork.getTitle());
        artworkOutputAdminDto.setArtist(artwork.getArtist());
        artworkOutputAdminDto.setDescription(artwork.getDescription());
        artworkOutputAdminDto.setDateCreated(artwork.getDateCreated());
        artworkOutputAdminDto.setGalleryBuyingPrice(artwork.getGalleryBuyingPrice());
        //TODO Hier komen berekening voor de verkoopprijs en promoties, aparte service klasse voor maken.
        artworkOutputAdminDto.setEdition(artwork.getEdition());
        artworkOutputAdminDto.setImageUrl(artwork.getImageUrl());
        artworkOutputAdminDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            mapPaintingFields((Painting) artwork, artworkOutputAdminDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            mapDrawingFields((Drawing) artwork, artworkOutputAdminDto);
        }

        return artworkOutputAdminDto;
    }

    private static void mapPaintingFields(Painting painting, ArtworkOutputAdminDto artworkOutputAdminDto) {
        artworkOutputAdminDto.setPaintingPaintType(painting.getPaintingPaintType());
        artworkOutputAdminDto.setPaintingSurface(painting.getPaintingSurface());
        artworkOutputAdminDto.setPaintingMaterial(painting.getPaintingMaterial());
        artworkOutputAdminDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        artworkOutputAdminDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }

    private static void mapDrawingFields(Drawing drawing, ArtworkOutputAdminDto artworkOutputAdminDto) {
        artworkOutputAdminDto.setDrawingDrawType(drawing.getDrawingDrawType());
        artworkOutputAdminDto.setDrawingSurface(drawing.getDrawingSurface());
        artworkOutputAdminDto.setDrawingMaterial(drawing.getDrawingMaterial());
        artworkOutputAdminDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        artworkOutputAdminDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

}
