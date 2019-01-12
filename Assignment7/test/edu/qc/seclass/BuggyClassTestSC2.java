package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestSC2 {

	@Test
	void test() {
		BuggyClass buggyClass = new BuggyClass();
		assertEquals(4, buggyClass.buggyMethod2(4, 12));
		assertEquals(4, buggyClass.buggyMethod2(12, 4));
		
	}

}
