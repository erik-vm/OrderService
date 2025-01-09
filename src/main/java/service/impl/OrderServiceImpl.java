package service.impl;

import dao.OrderDao;
import model.Order;
import org.springframework.stereotype.Service;
import service.OrdersService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrdersService {

    private final OrderDao dao;

    public OrderServiceImpl(OrderDao dao) {
        this.dao = dao;
    }

    @Override
    public Order insertOrder(Order order) {
        return dao.insertOrder(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return dao.getOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return dao.getAllOrders();
    }

    @Override
    public void deleteOrderById(Long id) {
        dao.deleteOrderById(id);
    }
}
