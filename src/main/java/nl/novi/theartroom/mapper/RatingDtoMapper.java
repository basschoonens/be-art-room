package nl.novi.theartroom.mapper;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingUserDto;
import nl.novi.theartroom.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingDtoMapper {

    public Rating toRating(RatingUserDto ratingUserDto) {
        Rating rating = new Rating();
        rating.setRatingId(ratingUserDto.getRatingId());
        rating.setRating(ratingUserDto.getRating());
        rating.setComment(ratingUserDto.getComment());

        return rating;
    }

    public RatingUserDto toRatingDto(Rating rating) {
        RatingUserDto ratingUserDto = new RatingUserDto();
        ratingUserDto.setRatingId(rating.getRatingId());
        ratingUserDto.setRating(rating.getRating());
        ratingUserDto.setComment(rating.getComment());

        return ratingUserDto;
    }

    public List<RatingUserDto> toRatingDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingDto).collect(Collectors.toList());
    }

    public RatingOutputWithArtworkDto toRatingDtoWithArtworkDto(Rating rating) {
        RatingOutputWithArtworkDto dto = new RatingOutputWithArtworkDto();
        dto.setRatingId(rating.getRatingId());
        dto.setRating(rating.getRating());
        dto.setComment(rating.getComment());
        dto.setArtworkId(rating.getArtwork().getArtworkId());
        dto.setArtworkTitle(rating.getArtwork().getTitle());
        dto.setArtworkArtist(rating.getArtwork().getArtist());
        return dto;
    }

    public List<RatingOutputWithArtworkDto> toRatingDtoWithArtworkDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingDtoWithArtworkDto).collect(Collectors.toList());
    }

}