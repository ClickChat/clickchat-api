package org.acactown.clickchat.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.mangofactory.swagger.plugin.EnableSwagger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.DateFormatter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.view.InternalResourceViewResolver

import static com.fasterxml.jackson.annotation.JsonInclude.Include

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableWebMvc
@EnableSwagger
@ComponentScan([
        "org.acactown.clickchat.service.config",
        "org.acactown.clickchat.api"
])
@PropertySource("classpath:api.properties")
class APIConfigurerAdapter extends WebMvcConfigurerAdapter {

    /**
     * Adding a JSP View Resolver to process our JSP version of Swagger's home page.
     */
    @Bean
    ViewResolver jspResolver() {
        return new InternalResourceViewResolver(
                prefix: "/swagger/",
                suffix: ".jsp"
        )
    }

    /**
     * This is equivalent to mvc:default-servlet-handler.
     * It's needed to support static resources and other features.
     */
    @Override
    void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable()
    }

    @Override
    void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("dd/MM/yyyy"))
    }

    @Override
    void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final ObjectMapper mapper = new ObjectMapper(serializationInclusion: Include.NON_NULL)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
                objectMapper: mapper
        )

        converters.add(converter)

        super.configureMessageConverters(converters)
    }

    /**
     * These static resources enable Swagger's UI.
     */
    @Override
    void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/lib/**").addResourceLocations("/swagger/lib/")
        registry.addResourceHandler("/css/**").addResourceLocations("/swagger/css/")
        registry.addResourceHandler("/images/**").addResourceLocations("/swagger/images/")
        registry.addResourceHandler("/*.ico").addResourceLocations("/swagger/")
        registry.addResourceHandler("/*.js").addResourceLocations("/swagger/")
    }

    /**
     * This maps the / path to the 'index' view, which will act as welcome page
     * when somebody accesses the API URL without any specific path.
     */
    @Override
    void addViewControllers(ViewControllerRegistry registry) {
        ViewControllerRegistration addViewController = registry.addViewController("/")
        addViewController.setViewName("index")
    }

}
