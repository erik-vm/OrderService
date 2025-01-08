package servlet;

import config.Config;
import config.HsqlDataSource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class AppContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        var ctx = new AnnotationConfigApplicationContext(Config.class, HsqlDataSource.class);
        ServletContext context = sce.getServletContext();
        context.setAttribute("context", ctx);

        ServletRegistration.Dynamic bulkServlet = sce.getServletContext().addServlet("OrderBulkServlet", new OrdersBulkServlet());
        bulkServlet.addMapping("/api/orders/bulk");

    }
}
