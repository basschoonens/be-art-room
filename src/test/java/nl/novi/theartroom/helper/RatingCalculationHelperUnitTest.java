package nl.novi.theartroom.helper;

import nl.novi.theartroom.repository.RatingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class RatingCalculationHelperUnitTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingCalculationHelper ratingCalculationHelper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateAverageRatingForArtwork() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    void countRatingsForArtwork() {

        // Arrange

        // Act

        // Assert

    }
}