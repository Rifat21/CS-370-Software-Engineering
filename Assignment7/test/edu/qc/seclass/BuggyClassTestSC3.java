package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestSC3 {

	//This test has a 100% statement coverage of buggyMethod3 less than 100% branch coverage and reveals the fault. 
	@Test
	void test() {
        BuggyClass buggyClass = new BuggyClass();
        assertEquals(10, buggyClass.buggyMethod3(100));
        assertEquals(0, buggyClass.buggyMethod3(4));
	}

}
