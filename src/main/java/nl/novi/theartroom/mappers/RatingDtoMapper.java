package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.ratingdtos.RatingArtistAdminDto;
import nl.novi.theartroom.dtos.ratingdtos.RatingUserDto;
import nl.novi.theartroom.models.Rating;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingDtoMapper {

    public Rating toRatingUserDto(RatingUserDto ratingUserDto) {
        Rating rating = new Rating();
        rating.setRating(ratingUserDto.getRating());
        rating.setComment(ratingUserDto.getComment());

        return rating;
    }

    public RatingUserDto toRatingUserDto(Rating rating) {
        RatingUserDto ratingUserDto = new RatingUserDto();
        ratingUserDto.setRating(rating.getRating());
        ratingUserDto.setComment(rating.getComment());

        return ratingUserDto;
    }

    public List<RatingUserDto> toRatingUserDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingUserDto).collect(Collectors.toList());
    }

    public RatingArtistAdminDto toRatingArtistAdminDto(Rating rating) {
        RatingArtistAdminDto ratingArtistAdminDto = new RatingArtistAdminDto();
        ratingArtistAdminDto.setId(rating.getId());
        ratingArtistAdminDto.setRating(rating.getRating());
        ratingArtistAdminDto.setComment(rating.getComment());

        return ratingArtistAdminDto;
    }

    public List<RatingArtistAdminDto> toRatingArtistAdminDtoList(List<Rating> ratings) {
        return ratings.stream().map(this::toRatingArtistAdminDto).collect(Collectors.toList());
    }

}