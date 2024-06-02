package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ratingdtos.RatingWithArtworkDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingDtoMapper {

    public Rating toRatingUserDto(RatingUserDto ratingUserDto) {
        Rating rating = new Rating();
        rating.setId(ratingUserDto.getRatingId());
        rating.setRating(ratingUserDto.getRating());
        rating.setComment(ratingUserDto.getComment());

        return rating;
    }

    public RatingUserDto toRating(Rating rating) {
        RatingUserDto ratingUserDto = new RatingUserDto();
        ratingUserDto.setRatingId(rating.getId());
        ratingUserDto.setRating(rating.getRating());
        ratingUserDto.setComment(rating.getComment());

        return ratingUserDto;
    }

    public List<RatingUserDto> toRatingUserDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRating).collect(Collectors.toList());
    }

    public RatingWithArtworkDto toRatingWithArtworkDto(Rating rating) {
        RatingWithArtworkDto dto = new RatingWithArtworkDto();
        dto.setRatingId(rating.getId());
        dto.setRating(rating.getRating());
        dto.setComment(rating.getComment());
        dto.setArtworkId(rating.getArtwork().getId());
        dto.setArtworkTitle(rating.getArtwork().getTitle());
        dto.setArtworkArtist(rating.getArtwork().getArtist());
        return dto;
    }

    public List<RatingWithArtworkDto> toRatingWithArtworkDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingWithArtworkDto).collect(Collectors.toList());
    }

}