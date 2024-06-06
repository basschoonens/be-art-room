package nl.novi.theartroom.mapper.artworkmappers;

import nl.novi.theartroom.dto.artworkdto.ArtworkOutputArtistDto;
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
