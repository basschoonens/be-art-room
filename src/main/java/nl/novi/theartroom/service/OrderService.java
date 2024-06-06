package nl.novi.theartroom.service;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.exception.UserNotFoundException;
import nl.novi.theartroom.mapper.OrderDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final ArtworkRepository artworkRepository;
    private final ArtworkUserDtoMapper artworkUserDtoMapper;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, ArtworkRepository artworkRepository, ArtworkUserDtoMapper artworkUserDtoMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
        this.artworkRepository = artworkRepository;
        this.artworkUserDtoMapper = artworkUserDtoMapper;
        this.userRepository = userRepository;
    }

    public List<OrderOutputDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public OrderOutputDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderDtoMapper.toOrderDto(order);
    }

    @Transactional
    public OrderOutputDto createOrderForUser(String username, OrderInputDto orderInputDto) {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        Order order = orderDtoMapper.toOrder(orderInputDto);
        order.setUser(user);
        order = saveOrderWithArtworks(order, orderInputDto.getArtworkIds());

        return orderDtoMapper.toOrderDto(order);
    }

    @Transactional
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderNumber(orderInputDto.getOrderNumber());
        order.setOrderDate(orderInputDto.getOrderDate());
        order.setOrderStatus(orderInputDto.getOrderStatus());
        order.setPaymentMethod(orderInputDto.getPaymentMethod());
        order.setTotalPrice(orderInputDto.getTotalPrice());
        order.setName(orderInputDto.getName());
        order.setAddress(orderInputDto.getAddress());
        order.setPostalCode(orderInputDto.getPostalCode());
        order.setCity(orderInputDto.getCity());
        order = saveOrderWithArtworks(order, orderInputDto.getArtworkIds());

        order = orderRepository.save(order);
        return orderDtoMapper.toOrderDto(order);
    }

    public List<OrderOutputDto> getOrdersForUser(String username) {
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

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}