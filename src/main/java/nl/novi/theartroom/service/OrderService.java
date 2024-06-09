package nl.novi.theartroom.service;

import jakarta.transaction.Transactional;
import nl.novi.theartroom.dto.orderdto.OrderInputDto;
import nl.novi.theartroom.dto.orderdto.OrderOutputDto;
import nl.novi.theartroom.exception.*;
import nl.novi.theartroom.mapper.OrderDtoMapper;
import nl.novi.theartroom.model.Order;
import nl.novi.theartroom.model.users.User;
import nl.novi.theartroom.repository.OrderRepository;
import nl.novi.theartroom.service.userservice.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderDtoMapper;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderDtoMapper, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDtoMapper = orderDtoMapper;

        this.userService = userService;
    }

    // USER METHOD

    public List<OrderOutputDto> getOrdersForUser(String username) {
        return orderRepository.findAllByUserUsername(username).stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderOutputDto createOrderForUser(String username, OrderInputDto orderInputDto) {
        try {
            Order order = orderDtoMapper.toOrder(orderInputDto);
            User user = userService.getUserByUsername(username);
            order.setUser(user);
            order.setOrderStatus("NEW");
            order.setOrderNumber("ORD" + System.currentTimeMillis());
            return orderDtoMapper.toOrderDto(orderRepository.save(order));
        } catch (MappingException e) {
            throw new MappingException("Error mapping order to the database", e);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error saving order to the database", e);
        }
    }

    // ADMIN METHOD

    public List<OrderOutputDto> getAllOrdersForAdmin() {
        return orderRepository.findAll().stream()
                .map(orderDtoMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    public OrderOutputDto getOrderByIdForAdmin(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with " + id + " not found"));
        return orderDtoMapper.toOrderDto(order);
    }

    public OrderOutputDto approveOrderForAdmin(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with " + id + " not found"));
        order.setOrderStatus("APPROVED");
        return orderDtoMapper.toOrderDto(orderRepository.save(order));
    }

    public void deleteOrderForAdmin(Long id) {
        orderRepository.deleteById(id);
    }

    // CRUD METHOD FOR TESTING

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
    public OrderOutputDto createOrder(OrderInputDto orderInputDto) {
        try {
            Order order = orderDtoMapper.toOrder(orderInputDto);
            order.setOrderStatus("PENDING");
            Order savedOrder = orderRepository.save(order);
            return orderDtoMapper.toOrderDto(savedOrder);
        } catch (MappingException e) {
            throw new MappingException("Error mapping order to the database", e);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error saving order to the database", e);
        }
    }

    @Transactional
    public OrderOutputDto updateOrder(Long id, OrderInputDto orderInputDto) {
        try {
            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new OrderNotFoundException("Order with " + id + " not found"));
            orderDtoMapper.updateOrderFromDto(order, orderInputDto);
            Order savedOrder = orderRepository.save(order);
            return orderDtoMapper.toOrderDto(savedOrder);
        } catch (MappingException e) {
            throw new MappingException("Error mapping order to the database", e);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error saving order to the database", e);
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}