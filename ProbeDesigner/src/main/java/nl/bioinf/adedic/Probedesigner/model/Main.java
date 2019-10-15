package nl.bioinf.adedic.Probedesigner.model;
import nl.bioinf.adedic.Probedesigner.io.ApacheCliParser;

public class Main {
    /**
     * main, which starts the program.
     * @param args, the arguments which can be provided on the cli.
     */
    public static void main(String[] args) throws java.text.ParseException {
        if(args.length == 0){
            System.out.println("No input file has been provided");
            return;
        }
        ApacheCliParser apacheCliParser = new ApacheCliParser();
        apacheCliParser.buildCliOptions();
        apacheCliParser.parseCMD(args);
    }
}
