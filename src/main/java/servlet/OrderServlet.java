package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import util.ConnectionPoolFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    DataSource pool = new ConnectionPoolFactory().createConnectionPool();
    OrderDao dao = new OrderDao(pool);

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");

        if (request.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(request.getParameter("id"));
            Order order = dao.getOrderById(id);
            if (order != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(response.getOutputStream(), order);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Order not found\"}");
            }
        } else {
            List<Order> orders = dao.getAllOrders();
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getOutputStream(), orders);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Order order = mapper.readValue(req.getInputStream(), Order.class);

        Order savedOrder = dao.insertOrder(order);

        resp.addHeader("Content-Type", "application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String output = mapper.writeValueAsString(savedOrder);
        resp.getWriter().print(output);
    }
}