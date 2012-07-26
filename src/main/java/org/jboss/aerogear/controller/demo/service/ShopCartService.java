package org.jboss.aerogear.controller.demo.service;

import org.jboss.aerogear.controller.demo.model.Car;
import org.jboss.aerogear.security.idm.annotation.CustomSecurityBinding;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ShopCartService {

    @CustomSecurityBinding
    public Car add(Car car) {
        System.out.println("car: " + car.getBrand());
        return car;
    }
}