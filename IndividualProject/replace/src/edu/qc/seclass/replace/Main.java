package edu.qc.seclass.replace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class Main {

	public static void main(String[] args) {
		boolean bOption = false, fOption = false, lOption = false, iOption = false;
		int invalidOptions = 0, invalidStrings = 0, counter = 0;
		ArrayList<String> stringArgs = new ArrayList<>();
		ArrayList<String> optionArgs = new ArrayList<>();
		ArrayList<String> validOptions = new ArrayList<>();
		ArrayList<String> validStrings = new ArrayList<>();
		ArrayList<String> inputFiles = new ArrayList<>();

		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("--") && ((args.length - i) > 3) && (args[i + 3].equals("--"))) {
				counter += 2;
				stringArgs.add(args[i + 1]);
				stringArgs.add(args[i + 2]);
				i += 2;
			} else if (args[i].startsWith("-") && !args[i].equals("--")) {
				if (stringArgs.size() >= 1) {
					stringArgs.add(args[i]);
				} else {
					optionArgs.add(args[i]);
				}
			} else if (args[i].endsWith("tmp") || args[i].endsWith("tmpfile") || args[i].endsWith(".bck")) {
				inputFiles.add(args[i]);
			} else if (!args[i].equals("--")) {
				stringArgs.add(args[i]);
			} else if (args[i].equals("--")) {
				counter++;
			}
		}

		if (inputFiles.size() < 1 || counter < 1) {
			usage();
			return;
		}

		for (String str : stringArgs) {
			if (stringArgs.size() % 2 != 0) {
				invalidStrings++;
			} else
				validStrings.add(str);
		}

		for (String options : optionArgs) {
			switch (options) {
			case "-b":
				bOption = true;
				validOptions.add(options);
				break;
			case "-f":
				fOption = true;
				validOptions.add(options);
				break;
			case "-l":
				lOption = true;
				validOptions.add(options);
				break;
			case "-i":
				iOption = true;
				validOptions.add(options);
				break;
			case "--":
				break;
			default:
				invalidOptions++;
				break;
			}
		}

		if (invalidOptions >= 1) {
			usage();
			return;
		}

		if (invalidStrings >= 1 || validStrings.size() < 2 || validStrings.size() % 2 != 0) {
			usage();
			return;
		}

		if (validOptions.size() == 0 || (validOptions.size() == 1 && iOption)) {
			if (validStrings.get(0).equals("")) {
				usage();
				return;
			}
			for (String file : inputFiles) {
				File tmp = new File(file);
				String path = tmp.getPath();
				String content = "";
				try {
					content = new String(Files.readAllBytes(Paths.get(tmp.getPath())));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.err.println("File " + tmp.getName() + " not found");
				}
				if (iOption) {
					for (int i = 0; i <= validStrings.size() - 2; i += 2) {
						content = content.replaceAll("(?i)" + Pattern.quote(validStrings.get(i)),
								validStrings.get(i + 1));
					}
				} else {
					for (int i = 0; i <= validStrings.size() - 2; i += 2) {
						content = content.replace(validStrings.get(i), validStrings.get(i + 1));
					}
				}
				tmp.delete();

				File edited = new File(path);
				PrintWriter pw;
				try {
					pw = new PrintWriter(edited);
					pw.print(content);
					pw.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (validOptions.size() == 1 && bOption) {
			for (String file : inputFiles) {
				File tmp = new File(file);
				String path = tmp.getPath();
				String content = "";
				try {
					content = new String(Files.readAllBytes(Paths.get(file)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File bckup = new File(tmp.getPath() + ".bck");
				if (bckup.exists()) {
					System.err.println("Not performing replace for " + tmp.getName() + ": Backup file already exists");
				} else {
					try {
						PrintWriter pw = new PrintWriter(bckup);
						pw.print(content);
						pw.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (int i = 0; i <= validStrings.size() - 2; i += 2) {
						content = content.replace(validStrings.get(i), validStrings.get(i + 1));
					}
					tmp.delete();

					File edited = new File(path);
					PrintWriter pw;
					try {
						pw = new PrintWriter(edited);
						pw.print(content);
						pw.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {

			if (bOption) {
				for (String file : inputFiles) {
					File tmp = new File(file);
					String path = tmp.getPath();
					String content = "";
					try {
						content = new String(Files.readAllBytes(Paths.get(file)));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					File bckup = new File(tmp.getPath() + ".bck");
					try {
						PrintWriter pw = new PrintWriter(bckup);
						pw.print(content);
						pw.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (fOption) {
				for (String file : inputFiles) {
					File tmp = new File(file);
					String path = tmp.getPath();
					String content = "";
					try {
						content = new String(Files.readAllBytes(Paths.get(tmp.getPath())));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (iOption) {
						for (int i = 0; i < validStrings.size() - 1; i += 2) {
							content = content.replaceFirst("(?i)" + Pattern.quote(validStrings.get(i)),
									validStrings.get(i + 1));
						}
					} else {
						for (int i = 0; i < validStrings.size() - 1; i += 2) {
							content = content.replaceFirst(Pattern.quote(validStrings.get(i)), validStrings.get(i + 1));
						}
					}
					tmp.delete();

					File edited = new File(path);
					PrintWriter pw;
					try {
						pw = new PrintWriter(edited);
						pw.print(content);
						pw.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (lOption) {
				for (String file : inputFiles) {
					File tmp = new File(file);
					String path = tmp.getPath();
					String content = "";
					String replace = validStrings.get(0);
					String replacement = validStrings.get(1);
					try {
						content = new String(Files.readAllBytes(Paths.get(tmp.getPath())));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (iOption) {
						String tmpContent = content.toLowerCase();
						String tmpReplace = replace.toLowerCase();
						if (tmpContent.contains(tmpReplace)) {
							int start = tmpContent.lastIndexOf(tmpReplace);
							StringBuilder builder = new StringBuilder();

							builder.append(content.substring(0, start));
							builder.append(replacement);
							builder.append(content.substring(start + validStrings.get(0).length()));

							tmp.delete();
							File edited = new File(path);
							PrintWriter pw;
							try {
								pw = new PrintWriter(edited);
								pw.print(builder);
								pw.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					} else if (content.contains(replace)) {
						int start = content.lastIndexOf(replace);
						StringBuilder builder = new StringBuilder();

						builder.append(content.substring(0, start));
						builder.append(replacement);
						builder.append(content.substring(start + validStrings.get(0).length()));

						tmp.delete();
						File edited = new File(path);
						PrintWriter pw;
						try {
							pw = new PrintWriter(edited);
							pw.print(builder);
							pw.close();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	private static void usage() {
		System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*");
	}

}
