package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestSC1a {

	//The following test has a 100% coverage of buggyMehtod1 however does not catch division by 0 error.  
	@Test
	void test() {
		BuggyClass buggyClass = new BuggyClass();
		assertEquals(10, buggyClass.buggyMethod1(50, 5));
		assertEquals(5, buggyClass.buggyMethod1(5, 25));
		assertEquals(1, buggyClass.buggyMethod1(10, 10));
	}

}
