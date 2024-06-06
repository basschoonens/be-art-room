package nl.novi.theartroom.mapper;

import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.repository.ArtworkRepository;
import org.springframework.stereotype.Service;

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
                .collect(Collectors.toList()));
        return dto;
    }

    public Order toOrder(OrderInputDto dto) {
        Order order = new Order();
        order.setOrderId(dto.getOrderId());
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
                .map(artworkId -> artworkRepository.findById(artworkId).orElseThrow(() -> new RuntimeException("Artwork not found")))
                .collect(Collectors.toSet()));
        return order;
    }
}
