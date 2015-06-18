package org.acactown.clickchat.api.config

import org.acactown.clickchat.api.filter.CORSFilter
import org.springframework.web.WebApplicationInitializer
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.FilterRegistration
import javax.servlet.ServletContext
import javax.servlet.ServletException
import javax.servlet.ServletRegistration

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
class APIInitializer implements WebApplicationInitializer {

    private final ServletContainerSettingsEnsurance settingsInsurance = new ServletContainerSettingsEnsurance()

    @Override
    void onStartup(ServletContext servletContext) throws ServletException {
        //settingsInsurance.safelyVerifyServlet()

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext()
        rootContext.register(APIConfig)
        
        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext))
        
        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext()
        dispatcherContext.register(APIConfigurerAdapter)
        
        // Register and map the dispatcher servlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext)
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet)
        dispatcher.setAsyncSupported(true)
        dispatcher.setLoadOnStartup(1)
        dispatcher.addMapping("/")
        
        // Add CORs filter
        FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CORSFilter)
        corsFilter.addMappingForUrlPatterns(null, false, "/*")
    }

}
