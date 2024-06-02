package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.artworkdtos.ArtworkOutputArtistDto;
import nl.novi.theartroom.dtos.artworkdtos.DrawingOutputDto;
import nl.novi.theartroom.dtos.artworkdtos.PaintingOutputDto;
import nl.novi.theartroom.helpers.PriceCalculationHelper;
import nl.novi.theartroom.helpers.RatingCalculationHelper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Drawing;
import nl.novi.theartroom.models.Painting;
import org.springframework.stereotype.Service;

@Service
public class ArtworkArtistDtoMapper {

    private final RatingCalculationHelper ratingCalculationHelper;
    private final PriceCalculationHelper priceCalculationHelper;

    public ArtworkArtistDtoMapper(RatingCalculationHelper ratingCalculationHelper, PriceCalculationHelper priceCalculationHelper) {
        this.ratingCalculationHelper = ratingCalculationHelper;
        this.priceCalculationHelper = priceCalculationHelper;
    }

    public ArtworkOutputArtistDto toArtworkArtistDto(Artwork artwork) {
        ArtworkOutputArtistDto artworkOutputArtistDto = new ArtworkOutputArtistDto();
        artworkOutputArtistDto.setId(artwork.getId());
        artworkOutputArtistDto.setTitle(artwork.getTitle());
        artworkOutputArtistDto.setArtist(artwork.getArtist());
        artworkOutputArtistDto.setDescription(artwork.getDescription());
        artworkOutputArtistDto.setDateCreated(artwork.getDateCreated());
        artworkOutputArtistDto.setGalleryBuyingPrice(artwork.getGalleryBuyingPrice());
        artworkOutputArtistDto.setEdition(artwork.getEdition());
        artworkOutputArtistDto.setArtworkType(artwork.getArtworkType());

        if ("painting".equalsIgnoreCase(artwork.getArtworkType())) {
            PaintingOutputDto paintingDto = new PaintingOutputDto();
            mapPaintingFields((Painting) artwork, paintingDto);
            artworkOutputArtistDto.setPaintingData(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkOutputArtistDto.setDrawingData(drawingDto);
        }

        artworkOutputArtistDto.setAverageRating(ratingCalculationHelper.calculateAverageRatingForArtwork(artwork.getId()));
        artworkOutputArtistDto.setTotalAmountOfRatings(ratingCalculationHelper.countRatingsForArtwork(artwork.getId()));
        artworkOutputArtistDto.setSellingPrice(priceCalculationHelper.calculateSellingPrice(artwork));

        return artworkOutputArtistDto;
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
