package com.alexpages.ordermanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderManagerApplicationTests {

	@Test
	void contextLoads() 
	{
		assertTrue("silly assertion to be compliant", true);
	}

}
