package io.mtc.common.config;

/**
 * Created by majun on 2018/9/23.
 */

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by majun on 2018/9/21.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebRootConfig implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("crystal_image_uri","/crystalreportviewers");
        servletContext.setInitParameter("crystal_image_use_relative","webapp");
    }
}