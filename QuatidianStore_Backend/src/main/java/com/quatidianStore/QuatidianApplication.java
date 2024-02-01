package com.quatidianStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuatidianApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuatidianApplication.class, args);
    }

}
/*Here’s the dependency that you may need to add in pom.xml

<!-- razor pay dependency-->
		<dependency>
			<groupId>com.razorpay</groupId>
			<artifactId>razorpay-java</artifactId>
			<version>1.4.3</version>
		</dependency>

Here’s the dependency that you may need to add in the package.json file.
		
		"razorpay": "^2.8.3"

Below script you need to add in the index.html file
		
		<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

To test payments, please use below UPI id’s
#success@razorpay
#failure@razorpay
*/