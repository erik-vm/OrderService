package servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import util.ConnectionPoolFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersBulkServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

        ObjectMapper mapper = new ObjectMapper();

        List<Order> orders = mapper.readValue(req.getInputStream(), new TypeReference<>() {
        });
        List<Order> savedOrders = new ArrayList<>();
        for (Order order : orders) {
            Order savedOrder = new OrderDao(new ConnectionPoolFactory().createConnectionPool()).insertOrder(order);
            savedOrders.add(savedOrder);
        }

        resp.addHeader("Content-Type", "application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);

        String output = mapper.writeValueAsString(savedOrders);
        resp.getWriter().print(output);
    }

}
