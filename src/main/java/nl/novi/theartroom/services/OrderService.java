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

import java.util.HashSet;
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
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toOrderDto(order);
    }


    public OrderDto createOrderForUser(String username, OrderDto orderDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Order order = orderMapper.toOrder(orderDto);
        order.setUser(user);
        order = saveOrderWithArtworks(order, orderDto.getArtworkIds());
        return orderMapper.toOrderDto(order);
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
        return orderMapper.toOrderDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderDto> getOrdersForUser(String username) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return orderRepository.findAllByUser(user).stream()
                .map(orderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    private Order saveOrderWithArtworks(Order order, List<Long> artworkIds) {
        List<Artwork> artworks = artworkRepository.findAllById(artworkIds);
        order.setArtworks(new HashSet<>(artworks));
        return orderRepository.save(order);
    }
}