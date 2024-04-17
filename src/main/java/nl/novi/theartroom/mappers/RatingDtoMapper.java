package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.models.Rating;

import java.util.List;
import java.util.stream.Collectors;

public class RatingDtoMapper {
    public static RatingDto toRatingOutputDto(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setRating(rating.getRating());
        ratingDto.setComment(rating.getComment());

        return ratingDto;
    }

    public static List<RatingDto> toRatingOutputDtoList(List<Rating> ratings) {
        return ratings.stream().map(RatingDtoMapper::toRatingOutputDto).collect(Collectors.toList());
    }

}
