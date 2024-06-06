package nl.novi.theartroom.helper;

import nl.novi.theartroom.model.artworks.Artwork;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculationHelper {

    public double calculateSellingPrice(Artwork artwork) {
        return artwork.getGalleryBuyingPrice() * 1.25;
    }

}
