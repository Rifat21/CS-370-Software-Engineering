package edu.qc.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(7, mycustomstring.countNumbers());
    }
    
    /*
     * Test to check whether the method works when the starting and ending character is a digit"
     */
    @Test
    public void testCountNumbers2() {
        mycustomstring.setString("123456");
        assertEquals(1, mycustomstring.countNumbers());
    }

    // Test to check if we get the correct output when the string is an empty string. 
    @Test
    public void testCountNumbers3() {
    	mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }

    //Test to check if we the correct output when surrounding digits between special characters. 
    @Test
    public void testCountNumbers4() {
        mycustomstring.setString("..4.+35.87-13+23+ 1 0 0");
        assertEquals(8, mycustomstring.countNumbers());
    }

    //Test to check if it returns the correct number if there are no numbers. 
    @Test
    public void testCountNumbers5() {
       mycustomstring.setString("There are zero digits in this string.");
       assertEquals(0, mycustomstring.countNumbers());
    }

    //Test to check the method properly throws a NullPointerException when the string is Null. 
    @Test (expected = NullPointerException.class)
    public void testCountNumbers6() {
        mycustomstring.countNumbers();
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    //Test to check if it returns the empty string when the string provided is empty itself.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, true));
    }

  //Test to check if it returns the empty string when the n value is greater than the length of the string. 
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {
        mycustomstring.setString("four");
        assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(5, true));
    }

    //Test to check if it returns the last character when given the input n.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
        mycustomstring.setString("Hello World");
        assertEquals("d", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(11, false));
    }

    //Test to check if it returns the first letter when the input is the string length.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
    	mycustomstring.setString("Hello World");
    	assertEquals("H", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(11, true));
    }
    
    //Test to check if it returns the empty string if n is larger than the string provided.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
        mycustomstring.setString("N is larger than this string.");
        assertEquals("", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(35, true));
    }

    //Test to check if it returns the last letter if n is equal to the length of the string when starting from the front. 
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
        mycustomstring.setString("The last letter is A");
        assertEquals("A", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(20, false));
    }

    //Test to check if it returns the first letter if n is equal to the length of the string when starting from the end. 
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {
    	mycustomstring.setString("The last letter is A");
        assertEquals("T", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(20, true));
    }

    //Test to check if it returns the middle character when given half of string length as input when starting from the from front.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {
        mycustomstring.setString("Middle Char");
        assertEquals("e", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(6, false));
    }

    //Test to check if it returns the same string when n is 1.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {
        mycustomstring.setString("Hello World");
        assertEquals("Hello World", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(1, false));
    }

    @Test (expected = NullPointerException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {
        mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {
        mycustomstring.setString("Inputing invalid N");
        mycustomstring.getEveryNthCharacterFromBeginningOrEnd(-5, false);
    }

  //Test to check if it returns the middle character when given half of string length as input when starting from the from end.
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd14() {
        mycustomstring.setString("Middle Char");
        assertEquals("e",mycustomstring.getEveryNthCharacterFromBeginningOrEnd(6, true));
    }

    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test to see if it returns the same string when given 1 as the starting point and 1 as the ending point.
    @Test
    public void testConvertDigitsToNamesInSubstring2() {
       mycustomstring.setString("OneTwoThree");
       mycustomstring.convertDigitsToNamesInSubstring(1, 1);
       assertEquals("OneTwoThree", mycustomstring.getString());
    }

    //Test to check the starting corner case, when starting point is 1 and ending point but with a digit. 
    @Test
    public void testConvertDigitsToNamesInSubstring3() {
        mycustomstring.setString("1TwoThree");
        mycustomstring.convertDigitsToNamesInSubstring(1, 1);
        assertEquals("OneTwoThree", mycustomstring.getString());
    }
    
    //Test to check the ending corner case, when starting point and ending point is the length of the string and the last char is a digit. 
    @Test
    public void testConvertDigitsToNamesInSubstring4() {
    	mycustomstring.setString("OneTwo3");
        mycustomstring.convertDigitsToNamesInSubstring(7, 7);
        assertEquals("OneTwoThree", mycustomstring.getString());
    }

    //Test to check if it returns the correct output when given the full length of the string as parameters. 
    @Test
    public void testConvertDigitsToNamesInSubstring5() {
    	mycustomstring.setString("123456");
        mycustomstring.convertDigitsToNamesInSubstring(1, 6);
        assertEquals("OneTwoThreeFourFiveSix", mycustomstring.getString());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring6() {
       mycustomstring.setString("Hello World");
       mycustomstring.convertDigitsToNamesInSubstring(4, 3);
       mycustomstring.getString();
    }

    @Test (expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring7() {
    	mycustomstring.setString("Hello World");
        mycustomstring.convertDigitsToNamesInSubstring(17, 18);
        mycustomstring.getString();
    }

    @Test (expected = NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring8() {
        mycustomstring.convertDigitsToNamesInSubstring(1, 3);
        mycustomstring.getString();
    }

}
