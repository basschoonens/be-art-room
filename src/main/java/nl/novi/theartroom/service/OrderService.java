package nl.novi.theartroom.service;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.exception.DatabaseException;
import nl.novi.theartroom.exception.MappingException;
import nl.novi.theartroom.exception.OrderNotFoundException;
import nl.novi.theartroom.exception.UserNotFoundException;
import nl.novi.theartroom.mapper.OrderDtoMapper;
import nl.novi.theartroom.mapper.artworkmappers.ArtworkUserDtoMapper;
import nl.novi.theartroom.model.artworks.Artwork;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.ArtworkRepository;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.repository.UserRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final ArtworkRepository artworkRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, ArtworkRepository artworkRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;
        this.artworkRepository = artworkRepository;
        this.userService = userService;
    }

    public List<OrderOutputDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public OrderOutputDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with " + id + " not found"));
        return orderDtoMapper.toOrderDto(order);
    }

    @Transactional
    public OrderOutputDto createOrderForUser(String username, OrderInputDto orderInputDto) {
        try {
            User user = userService.getUserByUsername(username);
            Order order = orderDtoMapper.toOrder(orderInputDto);
            order.setUser(user);
            order = saveOrderWithArtworks(order, orderInputDto.getArtworkIds());
            return orderDtoMapper.toOrderDto(order);
        } catch (MappingException e) {
            throw new MappingException("Error mapping order to the database", e);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error saving order to the database", e);
        }
    }

    @Transactional
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {
        try {
            Order order = orderDtoMapper.toOrder(orderInputDto);
            order.setOrderId(id);
            order = orderRepository.save(order);
            return orderDtoMapper.toOrderDto(order);
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException("Error updating order with id " + id, e);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<OrderOutputDto> getOrdersForUser(String username) {
        User user = userService.getUserByUsername(username);
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