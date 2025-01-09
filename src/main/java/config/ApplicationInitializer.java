package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{Config.class, HsqlDataSource.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/api/*"};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{Config.class};
    }
}
