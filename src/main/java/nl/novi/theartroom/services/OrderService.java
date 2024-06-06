package nl.novi.theartroom.services;

import nl.novi.theartroom.dtos.OrderDto;
import nl.novi.theartroom.exceptions.UserNotFoundException;
import nl.novi.theartroom.mappers.OrderDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Order;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.OrderRepository;
import nl.novi.theartroom.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, ArtworkRepository artworkRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderDtoMapper.toOrderDto(order);
    }


    public OrderDto createOrderForUser(String username, OrderDto orderDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        Order order = orderDtoMapper.toOrder(orderDto);
        order.setUser(user);
        order = saveOrderWithArtworks(order, orderDto.getArtworkIds());
        return orderDtoMapper.toOrderDto(order);
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderNumber(orderDto.getOrderNumber());
        order.setOrderDate(orderDto.getOrderDate());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setName(orderDto.getName());
        order.setAddress(orderDto.getAddress());
        order.setPostalCode(orderDto.getPostalCode());
        order.setCity(orderDto.getCity());
        order = saveOrderWithArtworks(order, orderDto.getArtworkIds());

        order = orderRepository.save(order);
        return orderDtoMapper.toOrderDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderDto> getOrdersForUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return orderRepository.findAllByUser(user).stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private Order saveOrderWithArtworks(Order order, List<Long> artworkIds) {
        List<Artwork> artworks = artworkRepository.findAllById(artworkIds);
        order.setArtworks(new HashSet<>(artworks));
        return orderRepository.save(order);
    }
}