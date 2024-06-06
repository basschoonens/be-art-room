package nl.novi.theartroom.mapper.artworkmappers;

import nl.novi.theartroom.dto.artworkdto.ArtworkOutputAdminDto;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.Drawing;
import nl.novi.theartroom.model.artworks.Painting;
import org.springframework.stereotype.Service;


// ADMIN MAPPER METHODS
@Service
public class ArtworkAdminDtoMapper {

    // TODO implement if null check in all my mappers.
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
        artworkOutputAdminDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            mapPaintingFields((Painting) artwork, artworkOutputAdminDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            mapDrawingFields((Drawing) artwork, artworkOutputAdminDto);
        }

        return artworkOutputAdminDto;
    }

    private static void mapPaintingFields(Painting painting, ArtworkOutputAdminDto artworkOutputAdminDto) {
        artworkOutputAdminDto.setPaintingSurface(painting.getPaintingSurface());
        artworkOutputAdminDto.setPaintingMaterial(painting.getPaintingMaterial());
        artworkOutputAdminDto.setPaintingDimensionsWidthInCm(painting.getPaintingDimensionsWidthInCm());
        artworkOutputAdminDto.setPaintingDimensionsHeightInCm(painting.getPaintingDimensionsHeightInCm());
    }

    private static void mapDrawingFields(Drawing drawing, ArtworkOutputAdminDto artworkOutputAdminDto) {
        artworkOutputAdminDto.setDrawingSurface(drawing.getDrawingSurface());
        artworkOutputAdminDto.setDrawingMaterial(drawing.getDrawingMaterial());
        artworkOutputAdminDto.setDrawingDimensionsWidthInCm(drawing.getDrawingDimensionsWidthInCm());
        artworkOutputAdminDto.setDrawingDimensionsHeightInCm(drawing.getDrawingDimensionsHeightInCm());
    }

}
