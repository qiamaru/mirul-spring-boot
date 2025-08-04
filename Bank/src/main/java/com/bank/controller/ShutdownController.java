package com.bank.controller;

import javax.swing.Spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PreDestroy;

@RestController
public class ShutdownController implements ApplicationContextAware {
    // INFO - implements ApplicationContextAware
    // This allow access to the ApplicationContext (Spring container) of this app.”

    private ApplicationContext context;
    // INFO - Stores the application context instance (Spring's internal container
    // managing beans, configs, etc.).

    @GetMapping("/shutdownContext")
    public void shutdownContext() {
        ((ConfigurableApplicationContext) context).close();
    }
    // Here’s the core logic:
    // The Spring application context is cast to ConfigurableApplicationContext,
    // which provides the .close() method.
    // Calling .close() will:
    // Trigger @PreDestroy hooks
    // Release resources
    // Terminate background threads
    // Shut down the application gracefully
    // This is the equivalent of "asking Spring to shut itself down from within."

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
    // This is the method from the ApplicationContextAware interface.
    // When Spring initializes this controller, it automatically calls this method
    // and passes the current application context (ctx) into it.
    // This is how the controller gets access to the app context.
}