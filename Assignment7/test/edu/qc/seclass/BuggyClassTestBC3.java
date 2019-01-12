package edu.qc.seclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BuggyClassTestBC3 {

	//This test achieves 100% branch coverage. 
	@Test
	void test() {
		BuggyClass buggyClass = new BuggyClass();
		assertEquals(10, buggyClass.buggyMethod3(100));
        assertEquals(47, buggyClass.buggyMethod3(45));
        assertEquals(1, buggyClass.buggyMethod3(22));
	}

}
