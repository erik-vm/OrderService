package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletRegistration.Dynamic orderFormServlet = sce.getServletContext().addServlet("OrderFormServlet", new OrderFormServlet());

        orderFormServlet.addMapping("/orders/form");
    }
}
