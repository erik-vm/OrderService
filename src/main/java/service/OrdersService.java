package service;

import model.Order;

import java.util.List;

public interface OrdersService {

    public Order insertOrder(Order order);

    public Order getOrderById(Long id);

    public List<Order> getAllOrders();

    public void deleteOrderById(Long id);

}
