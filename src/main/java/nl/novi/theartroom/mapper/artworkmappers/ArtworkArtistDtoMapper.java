package nl.novi.theartroom.mapper.artworkmappers;

import nl.novi.theartroom.dto.artworkdto.ArtworkOutputArtistAdminDto;
import nl.novi.theartroom.dto.artworkdto.DrawingOutputDto;
import nl.novi.theartroom.dto.artworkdto.PaintingOutputDto;
import nl.novi.theartroom.helper.PriceCalculationHelper;
import nl.novi.theartroom.helper.RatingCalculationHelper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.Drawing;
import nl.novi.theartroom.model.artworks.Painting;
import org.springframework.stereotype.Service;

@Service
public class ArtworkArtistDtoMapper {

    private final RatingCalculationHelper ratingCalculationHelper;
    private final PriceCalculationHelper priceCalculationHelper;

    public ArtworkArtistDtoMapper(RatingCalculationHelper ratingCalculationHelper, PriceCalculationHelper priceCalculationHelper) {
        this.ratingCalculationHelper = ratingCalculationHelper;
        this.priceCalculationHelper = priceCalculationHelper;
    }

    public ArtworkOutputArtistAdminDto toArtworkArtistDto(Artwork artwork) {
        ArtworkOutputArtistAdminDto artworkOutputArtistAdminDto = new ArtworkOutputArtistAdminDto();
        artworkOutputArtistAdminDto.setArtworkId(artwork.getArtworkId());
        artworkOutputArtistAdminDto.setTitle(artwork.getTitle());
        artworkOutputArtistAdminDto.setArtist(artwork.getArtist());
        artworkOutputArtistAdminDto.setDescription(artwork.getDescription());
        artworkOutputArtistAdminDto.setDateCreated(artwork.getDateCreated());
        artworkOutputArtistAdminDto.setGalleryBuyingPrice(artwork.getGalleryBuyingPrice());
        artworkOutputArtistAdminDto.setEdition(artwork.getEdition());
        artworkOutputArtistAdminDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            PaintingOutputDto paintingDto = new PaintingOutputDto();
            mapPaintingFields((Painting) artwork, paintingDto);
            artworkOutputArtistAdminDto.setPaintingData(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkOutputArtistAdminDto.setDrawingData(drawingDto);
        }

        artworkOutputArtistAdminDto.setAverageRating(ratingCalculationHelper.calculateAverageRatingForArtwork(artwork.getArtworkId()));
        artworkOutputArtistAdminDto.setTotalAmountOfRatings(ratingCalculationHelper.countRatingsForArtwork(artwork.getArtworkId()));
        artworkOutputArtistAdminDto.setSellingPrice(priceCalculationHelper.calculateSellingPrice(artwork));

        return artworkOutputArtistAdminDto;
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
