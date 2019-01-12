package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestBC2 {

	//This test covers more than 50% branch coverage.
	@Test
	void test() {
		BuggyClass buggyClass = new BuggyClass();
		assertEquals(2, buggyClass.buggyMethod2(6, 2));
		assertEquals(0, buggyClass.buggyMethod2(0, 4));
	}

}
