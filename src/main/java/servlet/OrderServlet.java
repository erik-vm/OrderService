package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;

import java.io.IOException;

@WebServlet("/api/orders")
public class OrderServlet extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException {

        if (request.getParameterMap().containsKey("id")) {
            long id = Long.parseLong(request.getParameter("id"));
            Order order = new Order().getOrderById(id);
            if (order != null) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                mapper.writeValue(response.getOutputStream(), order);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Order order = mapper.readValue(req.getInputStream(), Order.class);

        Order savedOrder = order.saveOrder(order);

        resp.addHeader("Content-Type", "application/json");
        resp.setStatus(HttpServletResponse.SC_OK);

        String output = mapper.writeValueAsString(savedOrder);
        resp.getWriter().print(output);
    }
}