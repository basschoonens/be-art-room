package nl.novi.theartroom.mapper;

import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.exception.model.ArtworkNotFoundException;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.repository.ArtworkRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderDtoMapper {

    private final ArtworkRepository artworkRepository;
    private final ArtworkUserDtoMapper artworkUserDtoMapper;

    public OrderDtoMapper(ArtworkRepository artworkRepository, ArtworkUserDtoMapper artworkUserDtoMapper) {
        this.artworkRepository = artworkRepository;
        this.artworkUserDtoMapper = artworkUserDtoMapper;
    }

    public OrderOutputDto toOrderDto(Order order) {
        OrderOutputDto dto = new OrderOutputDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setName(order.getName());
        dto.setAddress(order.getAddress());
        dto.setPostalCode(order.getPostalCode());
        dto.setCity(order.getCity());
        dto.setArtworks(order.getArtworks().stream()
                .map(artworkUserDtoMapper::toArtworkUserDto)
                .collect(Collectors.toSet()));
        return dto;
    }

    public Order toOrder(OrderInputDto orderInputDto) {
        Order order = new Order();
        if (orderInputDto.getOrderNumber() != null) {
            order.setOrderNumber(orderInputDto.getOrderNumber());
        }
        if (orderInputDto.getOrderDate() != null) {
            order.setOrderDate(orderInputDto.getOrderDate());
        }
        updateOrderFromDto(order, orderInputDto);
        return order;
    }

    public void updateOrderFromDto(Order order, OrderInputDto orderInputDto) {
        if (orderInputDto.getOrderId() != 0) {
            order.setOrderId(orderInputDto.getOrderId());
        }
        if (orderInputDto.getOrderDate() != null) {
            order.setOrderDate(orderInputDto.getOrderDate());
        }
        if (orderInputDto.getOrderNumber() != null) {
            order.setOrderNumber(orderInputDto.getOrderNumber());
        }
        if (orderInputDto.getOrderStatus() != null) {
            order.setOrderStatus(orderInputDto.getOrderStatus());
        }
        if (orderInputDto.getPaymentMethod() != null) {
            order.setPaymentMethod(orderInputDto.getPaymentMethod());
        }
        if (orderInputDto.getTotalPrice() != 0.0) {
            order.setTotalPrice(orderInputDto.getTotalPrice());
        }
        if (orderInputDto.getName() != null) {
            order.setName(orderInputDto.getName());
        }
        if (orderInputDto.getAddress() != null) {
            order.setAddress(orderInputDto.getAddress());
        }
        if (orderInputDto.getPostalCode() != null) {
            order.setPostalCode(orderInputDto.getPostalCode());
        }
        if (orderInputDto.getCity() != null) {
            order.setCity(orderInputDto.getCity());
        }
        Set<Artwork> artworks = orderInputDto.getArtworkId().stream()
                .map(artworkId -> artworkRepository.findById(artworkId)
                        .orElseThrow(() -> new ArtworkNotFoundException("Artwork not found with ID: " + artworkId)))
                .collect(Collectors.toSet());
        order.setArtworks(artworks);
        artworks.forEach(artwork -> artwork.getOrders().add(order));
    }
}
