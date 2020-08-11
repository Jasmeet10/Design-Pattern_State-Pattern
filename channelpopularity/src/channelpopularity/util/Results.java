package channelpopularity.util;

import  java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import channelpopularity.driver.Driver;
import channelpopularity.state.AbstractState;
//import java.nio.file.InvalidPathException;

/***
 * Result class invokes the methods to print the result in the file as well as on the display.
 */
public class Results implements FileDisplayInterface, StdoutDisplayInterface {
    private String output = "";


    public Results(String output) {
        this.output = output;
    }

    /***
     * write to file methods creates a file if not exist and write the results into the file
     */
    public void writeToFile() {

        try {
            File file = new File(output);
            if (file.createNewFile()) ;
            FileWriter fileWriter = new FileWriter(file);
            for(int i=0;i<AbstractState.myArray.size();i++) {
                fileWriter.write(AbstractState.myArray.get(i)+"\n");
            }
            //AbstractState.myArray.forEach((n) -> fileWriter.write(n));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    /***
     * writeToStdout method display the results on the screen.
     */

    public void writeToStdout(){
        for(int i=0;i<AbstractState.myArray.size();i++) {
            System.out.println(AbstractState.myArray.get(i));
        }
    }
}
