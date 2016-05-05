package com.epam.robot;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotTests {

	@Test
	public void testInitializatingBookstores(){
		NewRobot robot = new NewRobot();
		Bookstores bookstores = robot.bookstores();
		assertThat(bookstores.size()).isGreaterThan(0);
	}
	
}
