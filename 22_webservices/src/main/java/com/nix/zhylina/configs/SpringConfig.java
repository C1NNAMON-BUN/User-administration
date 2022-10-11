package com.nix.zhylina.configs;

import com.nix.zhylina.dao.impl.HibernateRoleDao;
import com.nix.zhylina.dao.impl.HibernateUserDao;
import com.nix.zhylina.services.CaptchaService;
import com.nix.zhylina.services.ICaptchaService;
import com.nix.zhylina.services.UserService;
import com.nix.zhylina.services.hibernateService.IRoleService;
import com.nix.zhylina.services.hibernateService.IUserService;
import com.nix.zhylina.services.hibernateService.impl.RoleService;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static com.nix.zhylina.utils.PropertyKeysConstants.PACKAGE_TO_SCAN;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_DIALECT;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_DIALECT_H2;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_DRIVER_CLASS;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_PASSWORD;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_URL;
import static com.nix.zhylina.utils.PropertyKeysConstants.PROPERTY_HIBERNATE_USERNAME;
import static com.nix.zhylina.utils.SpringConfigConstants.ANNOTATION_SPRING_COMPONENT_SCAN;
import static com.nix.zhylina.utils.SpringConfigConstants.ANNOTATION_SPRING_PROPERTY_SOURCE;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_ADD_RESOURCE_HANDLER;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_ADD_RESOURCE_LOCATIONS;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_REGISTER_DATE_FORMAT;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_SET_DATE_FORMAT;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_SET_DATE_TIME_FORMAT;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_VIEW_RESOLVER_PREFIX;
import static com.nix.zhylina.utils.SpringConfigConstants.PROPERTY_SPRING_VIEW_RESOLVER_SUFFIX;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({ANNOTATION_SPRING_COMPONENT_SCAN})
@PropertySource(ANNOTATION_SPRING_PROPERTY_SOURCE)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig implements WebMvcConfigurer {
    private final org.springframework.core.env.Environment environment;

    public SpringConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(PACKAGE_TO_SCAN);
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(environment.getProperty(PROPERTY_HIBERNATE_DRIVER_CLASS));
        dataSource.setUrl(environment.getProperty(PROPERTY_HIBERNATE_URL));
        dataSource.setUsername(environment.getProperty(PROPERTY_HIBERNATE_USERNAME));
        dataSource.setPassword(environment.getProperty(PROPERTY_HIBERNATE_PASSWORD));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty(environment.getProperty(PROPERTY_HIBERNATE_DIALECT),
                environment.getProperty(PROPERTY_HIBERNATE_DIALECT_H2));

        return hibernateProperties;
    }

    @Bean
    public IUserService userService(){
        return new com.nix.zhylina.services.hibernateService.impl.UserService(hibernateUserDao());
    }

    @Bean
    public IRoleService roleService(){
        return new RoleService(hibernateRoleDao());
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(PROPERTY_SPRING_VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(PROPERTY_SPRING_VIEW_RESOLVER_SUFFIX);

        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(PROPERTY_SPRING_ADD_RESOURCE_HANDLER).addResourceLocations(PROPERTY_SPRING_ADD_RESOURCE_LOCATIONS);
    }

    @Bean
    public FormattingConversionService mvcConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);

        DateTimeFormatterRegistrar dateTimeRegistrar = new DateTimeFormatterRegistrar();
        dateTimeRegistrar.setDateFormatter(DateTimeFormatter.ofPattern(PROPERTY_SPRING_SET_DATE_FORMAT));
        dateTimeRegistrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(PROPERTY_SPRING_SET_DATE_TIME_FORMAT));
        dateTimeRegistrar.registerFormatters(conversionService);

        DateFormatterRegistrar dateRegistrar = new DateFormatterRegistrar();
        dateRegistrar.setFormatter(new DateFormatter(PROPERTY_SPRING_REGISTER_DATE_FORMAT));
        dateRegistrar.registerFormatters(conversionService);

        return conversionService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(new UserService(hibernateUserDao()));

        return daoAuthenticationProvider;
    }

    @Bean
    public HibernateUserDao hibernateUserDao() {
        return new HibernateUserDao(sessionFactory().getObject());
    }

    @Bean
    public HibernateRoleDao hibernateRoleDao() {
        return new HibernateRoleDao(sessionFactory().getObject());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ICaptchaService getCaptcha() {
        return new CaptchaService();
    }
}