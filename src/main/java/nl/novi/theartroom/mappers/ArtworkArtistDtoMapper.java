package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtistDto;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;

@Service
public class ArtworkArtistDtoMapper {

    public static ArtworkOutputArtistDto toArtworkArtistDto(Artwork artwork) {
        ArtworkOutputArtistDto artworkOutputArtistDto = new ArtworkOutputArtistDto();
        artworkOutputArtistDto.setId(artwork.getId());
        artworkOutputArtistDto.setTitle(artwork.getTitle());
        artworkOutputArtistDto.setArtist(artwork.getArtist());
        artworkOutputArtistDto.setDescription(artwork.getDescription());
        artworkOutputArtistDto.setDateCreated(artwork.getDateCreated());
        artworkOutputArtistDto.setGalleryBuyingPrice(artwork.getGalleryBuyingPrice());
        artworkOutputArtistDto.setEdition(artwork.getEdition());
        artworkOutputArtistDto.setImageUrl(artwork.getImagePath());
        artworkOutputArtistDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            mapPaintingFields((Painting) artwork, artworkOutputArtistDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            mapDrawingFields((Drawing) artwork, artworkOutputArtistDto);
        }

        return artworkOutputArtistDto;
    }

    private static void mapPaintingFields(Painting painting, ArtworkOutputArtistDto artworkOutputArtistDto) {
        artworkOutputArtistDto.setPaintingPaintType(painting.getPaintingPaintType());
        artworkOutputArtistDto.setPaintingSurface(painting.getPaintingSurface());
        artworkOutputArtistDto.setPaintingMaterial(painting.getPaintingMaterial());
        artworkOutputArtistDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        artworkOutputArtistDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }

    private static void mapDrawingFields(Drawing drawing, ArtworkOutputArtistDto artworkOutputArtistDto) {
        artworkOutputArtistDto.setDrawingDrawType(drawing.getDrawingDrawType());
        artworkOutputArtistDto.setDrawingSurface(drawing.getDrawingSurface());
        artworkOutputArtistDto.setDrawingMaterial(drawing.getDrawingMaterial());
        artworkOutputArtistDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        artworkOutputArtistDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }
}
