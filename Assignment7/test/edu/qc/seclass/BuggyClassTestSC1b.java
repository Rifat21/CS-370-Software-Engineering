package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestSC1b {

	@Test
	void test() {
		BuggyClass buggyClass = new BuggyClass();
		assertEquals(0, buggyClass.buggyMethod1(10, 0));
	}

}
