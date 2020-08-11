package channelpopularity.driver;

import java.nio.file.InvalidPathException;
import channelpopularity.util.FileProcessor;
import channelpopularity.context.HelperClass;
import java.util.HashMap;
import channelpopularity.state.AbstractState;
import channelpopularity.util.Results;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * @author Jasmeet Kaur Dua
 *
 */
public class Driver {
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 2;

	public static int CurrentMetrics = 0;
	public static int NumberOfVideos = 0;
	public static HashMap<String, Integer> resultmap = new HashMap<>();
	public static HashMap<String, Integer> likemap = new HashMap<>();
	public static HashMap<String, Integer> dislikemap = new HashMap<>();

	public static void main(String[] args) throws Exception {
		String input = args[0];
		//System.out.println(input);
		String output = args[1];
		String Returnline;
		try {
			if (output.isEmpty()) {
				System.out.println("output file name is empty");
				System.exit(0);
			}
			if (input.isEmpty()) {
				System.out.println("input file name is empty");
				System.exit(0);
			}
			/*
			 * As the build.xml specifies the arguments as input,output or metrics, in case the
			 * argument value is not given java takes the default value specified in
			 * build.xml. To avoid that, below condition is used
			 */

			if ((args.length != 2) || (args[0].equals("${input}")) || (args[1].equals("${output}"))) {
				System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
				System.exit(0);
			}
			/***
			 * passing input file to the file processor.
			 */
			System.out.println((args.length != 2));
			System.out.println((args[0].equals("${input}")));
			System.out.println((args[1].equals("${output}")));

			FileProcessor fileprocessor = new FileProcessor(input);
			/***
			 * forwarding input lines to the helper class.
			 */
			HelperClass helper = new HelperClass();
			int flag = 0;
			while((Returnline = fileprocessor.poll())!= null){
				flag++;
				helper.helper(Returnline);
			}
			if(flag == 0 && fileprocessor.poll() == null){
				System.out.println("Input file is empty");
				System.exit(0);
			}
		}
		/***
		 * checking all the exceptions.
		 */
		catch (FileNotFoundException e) {
			System.out.println("Missing Input File");
			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("IO Exception");
			System.exit(0);
		}
		catch (SecurityException e){
			System.out.println("You do not have the read permissions to the input file");
			System.exit(0);
		}
		catch (InvalidPathException e){
			System.out.println("Invalid path");
			System.exit(0);
		}
		//System.out.println("Hello World! Lets get started with the assignment");
		/***
		 * writing the results and displaying it.
		 */
			Results results = new Results(output);
			results.writeToFile();
			results.writeToStdout();
		}

	}


