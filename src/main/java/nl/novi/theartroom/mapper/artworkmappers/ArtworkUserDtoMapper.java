package nl.novi.theartroom.mapper.artworkmappers;

import nl.novi.theartroom.dto.artworkdto.ArtworkOutputUserDto;
import nl.novi.theartroom.dto.artworkdto.DrawingOutputDto;
import nl.novi.theartroom.dto.artworkdto.PaintingOutputDto;
import nl.novi.theartroom.dto.ratingdto.RatingUserDto;
import nl.novi.theartroom.helper.PriceCalculationHelper;
import nl.novi.theartroom.helper.RatingCalculationHelper;
import nl.novi.theartroom.mapper.RatingDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.artworks.Drawing;
import nl.novi.theartroom.model.artworks.Painting;
import nl.novi.theartroom.repository.ArtworkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtworkUserDtoMapper {

    private final ArtworkRepository artworkRepository;
    private final RatingCalculationHelper ratingCalculationHelper;
    private final PriceCalculationHelper priceCalculationHelper;

    public ArtworkUserDtoMapper(ArtworkRepository artworkRepository, RatingCalculationHelper ratingCalculationHelper, PriceCalculationHelper priceCalculationHelper) {
        this.artworkRepository = artworkRepository;
        this.ratingCalculationHelper = ratingCalculationHelper;
        this.priceCalculationHelper = priceCalculationHelper;
    }

    public ArtworkOutputUserDto toArtworkUserDto(Artwork artwork) {

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
            artworkOutputUserDto.setPaintingData(paintingDto);
        } else if ("drawing".equalsIgnoreCase(artwork.getArtworkType())) {
            DrawingOutputDto drawingDto = new DrawingOutputDto();
            mapDrawingFields((Drawing) artwork, drawingDto);
            artworkOutputUserDto.setDrawingData(drawingDto);
        }

        artworkOutputUserDto.setAverageRating(ratingCalculationHelper.calculateAverageRatingForArtwork(artwork.getId()));
        artworkOutputUserDto.setTotalAmountOfRatings(ratingCalculationHelper.countRatingsForArtwork(artwork.getId()));
        artworkOutputUserDto.setSellingPrice(priceCalculationHelper.calculateSellingPrice(artwork));

        return artworkOutputUserDto;
    }

    public Artwork toArtwork(ArtworkOutputUserDto artworkOutputUserDto) {
        return artworkRepository.findById(artworkOutputUserDto.getId())
                .orElseThrow(() -> new RuntimeException("Artwork not found"));
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