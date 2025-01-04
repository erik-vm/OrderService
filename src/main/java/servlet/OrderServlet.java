package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.OrderLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {


    private Long currentId = 1L;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        response.getWriter().print("{}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String input = req.getReader().lines().collect(Collectors.joining("\n"));

        Order order = new ObjectMapper().readValue(input, Order.class);
        List<OrderLine> orderLines = order.getOrderLines();

        order.setId(currentId);
        currentId++;

        OrderLine tShirt = new OrderLine("Blue t-shirt", 9.99);
        OrderLine hat = new OrderLine("Black hat", 5.0);

        orderLines.addAll(List.of(tShirt, hat));

        order.setOrderLines(orderLines);

        String output = new ObjectMapper().writeValueAsString(order);

        resp.addHeader("Content-Type", "application/json");

        resp.getWriter().print(output);


    }
}