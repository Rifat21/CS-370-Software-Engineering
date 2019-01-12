package edu.qc.seclass.replace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MyMainTest {

	private ByteArrayOutputStream outStream;
	private ByteArrayOutputStream errStream;
	private PrintStream outOrig;
	private PrintStream errOrig;
	private Charset charset = StandardCharsets.UTF_8;

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		outStream = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(outStream);
		errStream = new ByteArrayOutputStream();
		PrintStream err = new PrintStream(errStream);
		outOrig = System.out;
		errOrig = System.err;
		System.setOut(out);
		System.setErr(err);
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(outOrig);
		System.setErr(errOrig);
	}

	// Some utilities

	private File createTmpFile() throws IOException {
		File tmpfile = temporaryFolder.newFile();
		tmpfile.deleteOnExit();
		return tmpfile;
	}

	private File createInputFile1() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("Howdy Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!");

		fileWriter.close();
		return file1;
	}

	private File createInputFile2() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write(
				"Howdy Bill,\n" + "This is another test file for the replace utility\n" + "that contains a list:\n"
						+ "-a) Item 1\n" + "-b) Item 2\n" + "...\n" + "and says \"howdy Bill\" twice");

		fileWriter.close();
		return file1;
	}

	private File createInputFile3() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter
				.write("Howdy Bill, have you learned your abc and 123?\n" + "It is important to know your abc and 123,"
						+ "so you should study it\n" + "and then repeat with me: abc and 123");

		fileWriter.close();
		return file1;
	}

	private File createInputFile4() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("I can picture in my mind a world without war, " + "a world without hate. "
				+ "And I can picture us attacking that world, " + "because they'd never expect it.");
		fileWriter.close();
		return file1;
	}

	private File createInputFile5() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("! @ # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n");
		fileWriter.close();
		return file1;
	}

	private File createInputFile6() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("I can picture in my mind a w0r1D$ without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that w0r1D$,\n" + "because they'd never !@#$ it.");
		fileWriter.close();
		return file1;
	}

	private File createAlphanumeric() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("h3ll0 h0w 4r3 y0u 6uy5 d01n6?");
		fileWriter.close();
		return file1;
	}

	private File createSpecialCharacter() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("!!! ### %%% &&& *** $$$");
		fileWriter.close();
		return file1;
	}

	private File createAlphaSpecial() throws Exception {
		File file1 = createTmpFile();
		FileWriter fileWriter = new FileWriter(file1);

		fileWriter.write("h3!!0 h0w 4r3 y0u 6uy$ d01n6?");
		fileWriter.close();
		return file1;
	}

	private String getFileContent(String filename) {
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filename)), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	// The first 6 test cases are from the MainTest, since these tests HAVE to pass
	// for deliverable two I am using them as part of my test cases.

	@Test
	public void mainTest1() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		File inputFile3 = createInputFile3();

		String args[] = { "-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(),
				inputFile3.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"Hello bill\" again!";
		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"Hello Bill\" twice";
		String expected3 = "Hello Bill, have you learned your abc and 123?\n"
				+ "It is important to know your abc and 123," + "so you should study it\n"
				+ "and then repeat with me: abc and 123";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		String actual3 = getFileContent(inputFile3.getPath());

		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertEquals("The files differ!", expected3, actual3);

		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
	}

	@Test
	public void mainTest2() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();

		String args[] = { "-b", "-f", "Bill", "William", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Howdy William,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";
		String expected2 = "Howdy William,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());

		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	@Test
	public void mainTest3() throws Exception {
		File inputFile = createInputFile3();

		String args[] = { "-f", "-l", "abc", "ABC", "--", inputFile.getPath() };
		Main.main(args);

		String expected = "Howdy Bill, have you learned your ABC and 123?\n"
				+ "It is important to know your abc and 123," + "so you should study it\n"
				+ "and then repeat with me: ABC and 123";

		String actual = getFileContent(inputFile.getPath());

		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	@Test
	public void mainTest4() throws Exception {
		File inputFile = createInputFile3();

		String args[] = { "123", "<numbers removed>", "--", inputFile.getPath() };
		Main.main(args);

		String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n"
				+ "It is important to know your abc and <numbers removed>," + "so you should study it\n"
				+ "and then repeat with me: abc and <numbers removed>";

		String actual = getFileContent(inputFile.getPath());

		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	@Test
	public void mainTest5() throws Exception {
		File inputFile = createInputFile2();

		String args1[] = { "-b", "--", "-a", "1", "--", inputFile.getPath() };
		Main.main(args1);
		String args2[] = { "--", "-b", "2", "--", inputFile.getPath() };
		Main.main(args2);

		String expected = "Howdy Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "1) Item 1\n" + "2) Item 2\n" + "...\n" + "and says \"howdy Bill\" twice";

		String actual = getFileContent(inputFile.getPath());

		assertEquals("The files differ!", expected, actual);
		assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	@Test
	public void mainTest6() {
		String args[] = { "blah", };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test frame #1. This is testing that we should get an error
	// when we do not input a file.
	@Test
	public void mainTest7() throws Exception {
		File inputFile1 = createTmpFile();
		String args[] = { "", "", "", "--" };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #2. This is to test when the file corresponding
	// to the name is not present.
	@Test
	public void mainTest8() throws Exception {
		File inputFile1 = createTmpFile();
		String args[] = { "", "", "", "--", inputFile1.getPath() };
		inputFile1.delete();
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #3. This to test when the file sizes are empty.
	@Test
	public void mainTest9() throws Exception {
		File inputFile1 = createTmpFile();
		String args[] = { "", "", "", "--", inputFile1.getPath() };
		Main.main(args);

		String actual = getFileContent(inputFile1.getPath());
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
	}

	// Implementation of Test Frame #4. This is to test when you have more than 4
	// valid options chosen.
	@Test
	public void mainTest10() throws Exception {
		File inputFile1 = createInputFile4();
		String args[] = { "-b", "-f", "-l", "-i", "-b", "world", "ball", "--", inputFile1.getPath() };
		Main.main(args);
		
		String expected = "I can picture in my mind a ball without war, " + "a world without hate. "
				+ "And I can picture us attacking that ball, " + "because they'd never expect it.";
		
		String actual = getFileContent(inputFile1.getPath());
		assertEquals("The files differ!", expected, actual);
		
		assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
	}

	// Implementation of Test Frame #5. This is to test when an invalid operation is
	// given.
	@Test
	public void mainTest11() throws Exception {
		File inputFile1 = createTmpFile();
		String args[] = { "-p", "Howdy", "Hi", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #11. To test when String input is zero.
	@Test
	public void mainTest12() throws Exception {
		File inputFile1 = createTmpFile();

		String args[] = { "-f", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #11. To test when String input is zero.
	@Test
	public void mainTest13() throws Exception {
		File inputFile1 = createTmpFile();

		String args[] = { "-f", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #12. To test when there's only one string input.
	@Test
	public void mainTest14() throws Exception {
		File inputFile1 = createTmpFile();

		String args[] = { "-f", "howdy", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #13. To test when there's more than two string
	// inputs.
	@Test
	public void mainTest15() throws Exception {
		File inputFile1 = createTmpFile();

		String args[] = { "-f", "howdy", "hello", "hi", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Newly created test case. Purpose: This is to test when the option is left
	// blank.
	@Test
	public void mainTest16() throws Exception {
		File inputFile1 = createTmpFile();

		String args[] = { "", "howdy", "hello", "--", inputFile1.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #14
	@Test
	public void mainTest17() throws Exception {
		File inputFile = createAlphanumeric();
		// "h3ll0 h0w 4r3 y0u 6uy5 d01n6?"
		String args[] = { "0", "o", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "h3llo how 4r3 you 6uy5 do1n6?";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		System.out.println(inputFile.getPath());
	}

	// Implementation of Test Frame #15
	@Test
	public void mainTest18() throws Exception {
		File inputFile = createSpecialCharacter();
		// "!!! ### %%% &&& *** $$$"
		String args[] = { "!", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "111 ### %%% &&& *** $$$";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
	}

	// Implementation of Test Frame #16
	@Test
	public void mainTest19() throws Exception {
		File inputFile = createAlphaSpecial();
		// "h3!!0 h0w 4r3 y0u 6uy$ d01n6?"
		String args[] = { "6uy$", "guys", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "h3!!0 h0w 4r3 y0u guys d01n6?";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
	}

	// Implementation of Test Frame #19
	@Test
	public void mainTest20() throws Exception {
		File inputFile = createAlphaSpecial();
		// "h3!!0 h0w 4r3 y0u 6uy$ d01n6?"
		String args[] = { "-b", "6uy$", "guys", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "h3!!0 h0w 4r3 y0u guys d01n6?";
		String actual = getFileContent(inputFile.getPath());


		assertEquals("The files differ!", expected, actual);
		assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #20
	@Test
	public void mainTest21() throws Exception {
		File inputFile = createInputFile1();
		String args[] = { "-f", "Howdy", "Hello", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #21
	@Test
	public void mainTest22() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-f", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "1 # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #22
	@Test
	public void mainTest23() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-f", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a world without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that w0r1D$,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #23
	@Test
	public void mainTest24() throws Exception {
		File inputFile = createInputFile1();
		String args[] = { "-l", "Howdy", "Hello", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #24
	@Test
	public void mainTest25() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-l", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "! @ # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #25
	@Test
	public void mainTest26() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-l", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a w0r1D$ without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that world,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #26
	@Test
	public void mainTest27() throws Exception {
		File inputFile = createInputFile1();
		String args[] = { "-i", "Howdy", "Hello", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"Hello bill\" again!";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #27
	@Test
	public void mainTest28() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-i", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "1 # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame 28
	@Test
	public void mainTest29() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-i", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a world without war,\n" + "a world without !@#$.\n"
				+ "And I can picture us attacking that world,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #29
	@Test
	public void mainTest30() throws Exception {
		File inputFile = createInputFile4();
		String args[] = { "-f", "-l", "world", "ball", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a ball without war, " + "a world without hate. "
				+ "And I can picture us attacking that ball, " + "because they'd never expect it.";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #30
	@Test
	public void mainTest31() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-f", "-l", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "1 # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #31
	@Test
	public void mainTest32() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-f", "-l", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a world without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that world,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #32
	@Test
	public void mainTest33() throws Exception {
		File inputFile = createInputFile4();
		String args[] = { "-f", "-l", "-i", "world", "ball", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a ball without war, " + "a world without hate. "
				+ "And I can picture us attacking that ball, " + "because they'd never expect it.";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #33
	@Test
	public void mainTest34() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-f", "-l", "-i", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "1 # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #34
	@Test
	public void mainTest35() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-f", "-l", "-i", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a world without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that world,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #35
	@Test
	public void mainTest36() throws Exception {
		File inputFile = createInputFile4();
		String args[] = { "-b", "-f", "-l", "-i", "world", "ball", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a ball without war, " + "a world without hate. "
				+ "And I can picture us attacking that ball, " + "because they'd never expect it.";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #36
	@Test
	public void mainTest37() throws Exception {
		File inputFile = createInputFile5();
		String args[] = { "-b", "-f", "-l", "-i", "! @", "1", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "1 # $ % ^ & * ( ) _ +\n" + "! @ # $ % ^ & * ( ) _ +\n" + "1 # $ % ^ & * ( ) _ +\n";

		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Implementation of Test Frame #37
	@Test
	public void mainTest38() throws Exception {
		File inputFile = createInputFile6();
		String args[] = { "-b", "-f", "-l", "-i", "w0r1D$", "world", "--", inputFile.getPath() };
		Main.main(args);
		String expected = "I can picture in my mind a world without war,\n" + "a w0r1D$ without !@#$.\n"
				+ "And I can picture us attacking that world,\n" + "because they'd never !@#$ it.";
		String actual = getFileContent(inputFile.getPath());
		assertEquals("The files differ!", expected, actual);
		assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
	}

	// Newly created test case. Purpose: This is to test when the option is left
	// blank but with multiple files
	@Test
	public void mainTest39() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();

		String args[] = { "", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);
		assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*",
				errStream.toString().trim());
	}

	// Implementation of Test Frame #38
	@Test
	public void mainTest40() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	// Implementation of Test Frame #41
	@Test
	public void mainTest41() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-b", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	// Implementation of Test Frame #44
	@Test
	public void mainTest42() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-f", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	// Implementation of Test Frame #47
	@Test
	public void mainTest43() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-l", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	// Implementation of Test Frame #50
	@Test
	public void mainTest44() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"Hello bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"Hello Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}

	//Implementation of Test Frame #53
	@Test
	public void mainTest45() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-f", "-l", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}
	
	//Implementation of Test Frame #58
	@Test
	public void mainTest46() throws Exception {
		File inputFile1 = createInputFile1();
		File inputFile2 = createInputFile2();
		String args[] = { "-b", "-f", "-l", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath() };
		Main.main(args);

		String expected1 = "Hello Bill,\n" + "This is a test file for the replace utility\n"
				+ "Let's make sure it has at least a few lines\n"
				+ "so that we can create some interesting test cases...\n" + "And let's say \"howdy bill\" again!";

		String expected2 = "Hello Bill,\n" + "This is another test file for the replace utility\n"
				+ "that contains a list:\n" + "-a) Item 1\n" + "-b) Item 2\n" + "...\n"
				+ "and says \"howdy Bill\" twice";

		String actual1 = getFileContent(inputFile1.getPath());
		String actual2 = getFileContent(inputFile2.getPath());
		assertEquals("The files differ!", expected1, actual1);
		assertEquals("The files differ!", expected2, actual2);
		assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
		assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
	}


}
