package controller;


import jakarta.validation.Valid;
import model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import service.OrdersService;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {


    private final OrdersService service;

    public OrderController(OrdersService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order insertOrder(@RequestBody @Valid Order order) {
        return service.insertOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable("id") Long id) {
        return service.getOrderById(id);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    @DeleteMapping("/{id}")
    public void deleteOrderById(@PathVariable("id") Long id) {
        service.deleteOrderById(id);
    }

}
