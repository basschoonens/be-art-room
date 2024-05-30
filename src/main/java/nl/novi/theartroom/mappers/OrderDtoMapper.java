package nl.novi.theartroom.mappers;

import nl.novi.theartroom.dtos.OrderDto;
import nl.novi.theartroom.models.Order;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.repositories.ArtworkRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {

    private final ArtworkRepository artworkRepository;

    public OrderDtoMapper(ArtworkRepository artworkRepository) {
        this.artworkRepository = artworkRepository;
    }

    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setName(order.getName());
        dto.setAddress(order.getAddress());
        dto.setPostalCode(order.getPostalCode());
        dto.setCity(order.getCity());
        dto.setArtworkIds(order.getArtworks().stream()
                .map(Artwork::getId)
                .collect(Collectors.toList()));
        return dto;
    }

    public Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderNumber(dto.getOrderNumber());
        order.setOrderDate(dto.getOrderDate());
        order.setOrderStatus(dto.getOrderStatus());
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setTotalPrice(dto.getTotalPrice());
        order.setName(dto.getName());
        order.setAddress(dto.getAddress());
        order.setPostalCode(dto.getPostalCode());
        order.setCity(dto.getCity());
        order.setArtworks(dto.getArtworkIds().stream()
                .map(id -> artworkRepository.findById(id).orElseThrow(() -> new RuntimeException("Artwork not found")))
                .collect(Collectors.toList()));
        return order;
    }
}
