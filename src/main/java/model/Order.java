package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;
    private String orderNumber;
    private List<OrderLine> orderLines;
    private Double total = 0.0;

    private static long currentId = 1L;
    private static Map<Long, Order> orders = new HashMap<>();


    public Order(String orderNumber, List<OrderLine> orderLines) {
        this.id = currentId++;
        this.orderNumber = orderNumber;
        this.orderLines = orderLines;
        calculateTotal();
    }

    public Order saveOrder(Order order) {
        Order orderToSave = new Order(order.getOrderNumber(), order.getOrderLines());
        orders.put(orderToSave.getId(), orderToSave);
        return orderToSave;
    }

    public Order getOrderById(Long id) {
        return orders.get(id);
    }

    private void calculateTotal() {
        total = orderLines.stream()
                .mapToDouble(OrderLine::getPrice)
                .sum();
    }
}



