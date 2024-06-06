package nl.novi.theartroom.helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nl.novi.theartroom.models.Artwork;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculationHelperUnitTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateSellingPriceWithFractionalGalleryBuyingPrice() {
        // Arrange
        double galleryBuyingPrice = 10.50;
        Artwork artwork = new Artwork();
        artwork.setGalleryBuyingPrice(galleryBuyingPrice);
        PriceCalculationHelper priceHelper = new PriceCalculationHelper();

        // Act
        double sellingPrice = priceHelper.calculateSellingPrice(artwork);

        // Assert
        assertEquals(13.125, sellingPrice, 0.001);
    }

    @Test
    void calculateSellingPriceWithCustomValues() {
        // Arrange
        double galleryBuyingPrice = 50.0;
        Artwork artwork = new Artwork();
        artwork.setGalleryBuyingPrice(galleryBuyingPrice);
        PriceCalculationHelper priceHelper = new PriceCalculationHelper();

        // Act
        double sellingPrice = priceHelper.calculateSellingPrice(artwork);

        // Assert
        double expectedSellingPrice = galleryBuyingPrice * 1.25;
        assertEquals(expectedSellingPrice, sellingPrice, 0.001);
    }
}