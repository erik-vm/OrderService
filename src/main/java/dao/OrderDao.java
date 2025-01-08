package dao;

import model.Order;
import model.OrderLine;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OrderDao {

    private final JdbcClient client;

    public OrderDao(JdbcClient client) {
        this.client = client;
    }

    public Order insertOrder(Order order) {
        if (order == null || order.getOrderNumber() == null || order.getOrderLines() == null) {
            throw new IllegalArgumentException("Order and required fields must not be null");
        }
        try {
            String sql = "insert into orders (order_number) values ( ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            client.sql(sql)
                    .param(order.getOrderNumber())
                    .update(keyHolder, "id");

            long orderId = Objects.requireNonNull(keyHolder.getKey()).longValue();

            double total = 0.0;

            if (!order.getOrderLines().isEmpty()) {
                for (OrderLine orderLine : order.getOrderLines()) {
                    String description = orderLine.getDescription();
                    double price = orderLine.getPrice();
                    total += price;
                    insertOrderLine(orderId, description, price);
                }
            }

            String updateTotalSql = "UPDATE orders SET total = ? WHERE id = ?";
            client.sql(updateTotalSql)
                    .param(total)
                    .param(orderId)
                    .update();

            return order.withId(orderId).withTotal(total);
        } catch (Exception e) {
            throw new RuntimeException("Inserting new order failed!");
        }
    }

    public Order getOrderById(Long id) {

        String sql = "select * from orders where orders.id = ?";

        Order order = client.sql(sql).param(id).query(Order.class).single();

        order.setOrderLines(getOrderLines(order.getId()));

        return order;
    }

    public List<Order> getAllOrders() {

        String sql = "select * from orders;";

        List<Order> orders = client.sql(sql).query(Order.class).list();
        for (Order order : orders) {
            order.setOrderLines(getOrderLines(order.getId()));
        }

        return orders;
    }

    public int deleteOrderById(Long id) {

        String sql = "delete from orders where id = (?)";

        return client.sql(sql).param(id).update();

    }


    private void insertOrderLine(long orderId, String description, double price) {

        String sql = "insert into order_lines (order_id, description, price)  values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        client.sql(sql)
                .param(orderId)
                .param(description)
                .param(price)
                .update(keyHolder, "id");
    }

    private List<OrderLine> getOrderLines(Long id) {
        String sql = "select * from order_lines where order_id = ?";
        return client.sql(sql).param(id).query(OrderLine.class).list();
    }


}
