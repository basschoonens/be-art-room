package nl.novi.theartroom.helpers;

import nl.novi.theartroom.models.Artwork;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculationHelper {

    public double calculateSellingPrice(Artwork artwork) {
        return artwork.getGalleryBuyingPrice() * 1.25;
    }

}
