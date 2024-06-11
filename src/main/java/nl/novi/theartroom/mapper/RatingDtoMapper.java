package nl.novi.theartroom.mapper;

import nl.novi.theartroom.dto.ratingdto.RatingOutputWithArtworkDto;
import nl.novi.theartroom.dto.ratingdto.RatingInputUserDto;
import nl.novi.theartroom.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingDtoMapper {

    public Rating toRating(RatingInputUserDto ratingInputUserDto) {
        Rating rating = new Rating();
        rating.setRatingId(ratingInputUserDto.getRatingId());
        rating.setRating(ratingInputUserDto.getRating());
        rating.setComment(ratingInputUserDto.getComment());

        return rating;
    }

    public RatingInputUserDto toRatingDto(Rating rating) {
        RatingInputUserDto ratingInputUserDto = new RatingInputUserDto();
        ratingInputUserDto.setRatingId(rating.getRatingId());
        ratingInputUserDto.setRating(rating.getRating());
        ratingInputUserDto.setComment(rating.getComment());

        return ratingInputUserDto;
    }

    public List<RatingInputUserDto> toRatingDtoList(List<Rating> ratings) {
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