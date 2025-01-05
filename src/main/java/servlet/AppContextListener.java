package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import util.ConfigUtil;
import util.ConnectionInfo;
import util.DataSourceProvider;
import util.FileUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebListener
public class AppContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionInfo connectionInfo = ConfigUtil.readConnectionInfo();
        DataSourceProvider.setConnectionInfo(connectionInfo);
        DataSource dataSource = DataSourceProvider.getDataSource();
        try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

            String schema = FileUtil.readFileFromClasspath("schema.sql");
            String data = FileUtil.readFileFromClasspath("data.sql");

            stmt.executeUpdate(schema);
            stmt.executeUpdate(data);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
