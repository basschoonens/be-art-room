package nl.novi.theartroom.services;

import nl.novi.theartroom.dtos.OrderDto;
import nl.novi.theartroom.exceptions.UsernameNotFoundException;
import nl.novi.theartroom.mappers.OrderDtoMapper;
import nl.novi.theartroom.models.Artwork;
import nl.novi.theartroom.models.Order;
import nl.novi.theartroom.models.User;
import nl.novi.theartroom.repositories.ArtworkRepository;
import nl.novi.theartroom.repositories.OrderRepository;
import nl.novi.theartroom.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderMapper;
    private final ArtworkRepository artworkRepository;
    private final UserRepository userRepository;
    private final OrderDtoMapper orderDtoMapper;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderMapper, ArtworkRepository artworkRepository, UserRepository userRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.artworkRepository = artworkRepository;
        this.userRepository = userRepository;
        this.orderDtoMapper = orderDtoMapper;
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
        order.setAddress(orderDto.getAddress());
        order.setArtworks(orderDto.getArtworkIds().stream()
                .map(artworkId -> artworkRepository.findById(artworkId).orElseThrow(() -> new RuntimeException("Artwork not found")))
                .collect(Collectors.toList()));
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderDto createOrderForUser(String username, OrderDto orderDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Order order = orderMapper.toEntity(orderDto);
        order.setUser(user);
        order = saveOrderWithArtworks(order, orderDto.getArtworkIds());
        return orderMapper.toDto(order);
    }

    public List<OrderDto> getOrdersForUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return orderRepository.findAllByUser(user).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    private Order saveOrderWithArtworks(Order order, List<Long> artworkIds) {
        List<Artwork> artworks = artworkRepository.findAllById(artworkIds);
        for (Artwork artwork : artworks) {
            artwork.setOrder(order); // Set the order reference in each artwork
        }
        order.setArtworks(artworks);
        return orderRepository.save(order);
    }
}