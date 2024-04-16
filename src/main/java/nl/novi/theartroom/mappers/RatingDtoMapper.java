package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.RatingDto;
import nl.novi.theartroom.models.Rating;

import java.util.List;
import java.util.stream.Collectors;

public class RatingDtoMapper {

    public static RatingDto toRatingOutputDto(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setId(rating.getId());
        ratingDto.setArtworkId(rating.getArtwork().getId());
        ratingDto.setRating(rating.getStars());
        ratingDto.setComment(rating.getCommentText());

        return ratingDto;
    }

    public static List<RatingDto> toRatingOutputDtoList(List<Rating> ratings) {
        return ratings.stream().map(RatingDtoMapper::toRatingOutputDto).collect(Collectors.toList());
    }

}
