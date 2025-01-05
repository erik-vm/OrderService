package dao;

import model.Order;
import model.OrderLine;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDao {

    private final DataSource dataSource;

    public OrderDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public Order insertOrder(Order order) {

        String sql = "insert into \"orders\" (order_number) values (?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {

            ps.setString(1, order.getOrderNumber());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (!rs.next()) {
                throw new RuntimeException("Key was not assigned to order!");
            }

            long id = rs.getLong("id");

            double sum = 0.0;
            if (order.getOrderLines() != null && !order.getOrderLines().isEmpty()) {
                for (OrderLine orderLine : order.getOrderLines()) {
                    sum += orderLine.getPrice();
                    addOrderLine(id, orderLine);
                }
            }

            return new Order(rs.getLong("id"), order.getOrderNumber(), order.getOrderLines(), sum);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Order getOrderById(Long id) {

        String sql = "select o.*, ol.* from \"orders\" o left join \"order_lines\" ol on o.id = ol.order_id where o.id = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            Order order = null;
            List<OrderLine> orderLines = new ArrayList<>();
            double sum = 0.0;

            while (rs.next()) {

                if (order == null) {
                    order = new Order();
                    order.setId(rs.getLong("id"));
                    order.setOrderNumber(rs.getString("order_number"));
                    order.setOrderLines(orderLines);

                }

                String description = rs.getString("description");
                if (description != null) {
                    double price = rs.getDouble("price");
                    sum += price;
                    orderLines.add(new OrderLine(description, price));
                }
                order.setTotal(sum);
            }
            return order;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getAllOrders() {

        String sql = "select o.*, ol.* from \"orders\" o left join \"order_lines\" ol on o.id = ol.order_id";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();) {

            Map<Long, Order> orders = new HashMap<>();


            while (rs.next()) {

                long id = rs.getLong("id");

                if (!orders.containsKey(id)) {
                    Order order = new Order(id, rs.getString("order_number"), new ArrayList<>(), rs.getDouble("total"));
                    orders.put(id, order);
                }
                String description = rs.getString("description");
                if (description != null) {
                    double price = rs.getDouble("price");
                    orders.get(id).getOrderLines().add(new OrderLine(description, price));
                }
            }

            return new ArrayList<>(orders.values());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void addOrderLine(long id, OrderLine orderLine) {
        String sql = "insert into \"order_lines\" (order_id, description, price) values (?, ?, ?)";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.setString(2, orderLine.getDescription());
            ps.setDouble(3, orderLine.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


}
