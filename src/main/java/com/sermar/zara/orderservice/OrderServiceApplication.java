package com.sermar.zara.orderservice;

import com.sermar.zara.orderservice.dao.TestSqlDaoUpgrade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderServiceApplication.class, args);

		TestSqlDaoUpgrade test = TestSqlDaoUpgrade.getInstance();

		test.getMaxUserOrderId(3);
	}

}
