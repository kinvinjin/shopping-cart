package com.kinvinjin.shoppingcart;

import com.kinvinjin.shoppingcart.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@SpringBootApplication
@RestController
public class ShoppingCartApplication {
	@Autowired
	private CheckoutService checkoutService;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@GetMapping("/index")
	public String index() {
		return "Welcome!";
	}

	@GetMapping("/checkout")
	public String checkout(@RequestParam(value = "order") String order) {
		try {
			BigDecimal cost = checkoutService.calculate(order);
			return String.format("$%s", cost);
		} catch (IllegalArgumentException exception) {
			return exception.getMessage();
		} catch (Exception exception) {
			return String.format("cannot process the order %s!", order);
		}
	}
}
