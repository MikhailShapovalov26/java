package ru.netology.servlet;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import javax.servlet.ServletContext;


public class MainServlet  implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext){
        final var context = new AnnotationConfigWebApplicationContext();
        context.scan("ru.netology");
        context.refresh();

        final var servlet = new DispatcherServlet(context);
        final var registrator = servletContext.addServlet("registrator", servlet);
        registrator.setLoadOnStartup(1);
        registrator.addMapping("/");



    }
}