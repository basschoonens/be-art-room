package nl.novi.theartroom.services;

import nl.novi.theartroom.dtos.OrderDto;
import nl.novi.theartroom.mappers.OrderDtoMapper;
import nl.novi.theartroom.models.Order;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderMapper;
    private final ArtworkRepository artworkRepository;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderMapper, ArtworkRepository artworkRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.artworkRepository = artworkRepository;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDto(order);
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderDate(orderDto.getOrderDate());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setBillingAddress(orderDto.getBillingAddress());
        order.setArtworks(orderDto.getArtworkIds().stream()
                .map(artworkId -> artworkRepository.findById(artworkId).orElseThrow(() -> new RuntimeException("Artwork not found")))
                .collect(Collectors.toList()));
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}