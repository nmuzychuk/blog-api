package com.nmuzychuk.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * Controller used to show relevant endpoints.
 */
@RestController
public class RootController {

    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    RootController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Set<?> readEndpoints() {
        Set<String> endpoints = new HashSet<>();

        handlerMapping.getHandlerMethods().forEach((k, v) -> endpoints.add(k.toString()));

        return endpoints;
    }
}
