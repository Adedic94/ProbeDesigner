package nl.bioinf.adedic.Probedesigner.io;
import nl.bioinf.adedic.Probedesigner.model.ObtainConsensusSequences;
import nl.bioinf.adedic.Probedesigner.model.ProbeDesigner;
import nl.bioinf.adedic.Probedesigner.model.ObtainSequences;
import nl.bioinf.adedic.Probedesigner.model.Summary;
/*
 * Copyright (c) 2017 Armin Dedic [a.dedic@st.hanze.nl].
 * All rights reserved.
 */

import org.apache.commons.cli.*;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.DefaultParser;
import java.io.*;

/*
 * @author Armin Dedic [a.dedic@st.hanze.nl]
 * @date 17-11-2017
 * @version 0.0.1
 * code inspiration sources: https://www.programcreek.com/java-api-examples/org.apache.commons.cli.CommandLineParser
 *                           http://www.techrepublic.com/article/process-the-command-line-with-cli-in-java/
 */



/**
 * ApacheCliParser, which parses the cli and depending on the given arguments creates objects to
 * design probes and provides a summary of the msa file which has to be provided in the arguments.
 */
public class ApacheCliParser {
    private String msaSequences = "";
    private Options options;

    /**
     * Building options for the cli
     */
    public void buildCliOptions(){
        this.options = new Options();
        options.addOption("f", "file", true, "MSA file")
                .addOption("d", "designer", false, "get designer")
                .addOption("s", "summary", false, "get summary of this file")
                .addOption("h", "Help", false, "show help for the user")
                .addOption("m", "maxSize", true, "states the maximum size of the probes")
                .addOption("n", "minSize", true, "states the minimum size of the probes")
                .addOption("a", "amount", true, "states the maximum amount of probes");
    }

    /**
     * Parses the arguments which can be provided to create probes or views a summary of the given file
     * @param args, the arguments which can be provided the cli
     */
    public void parseCMD(String[] args) throws java.text.ParseException {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption("f") && commandLine.getOptionValue("f") != null){
                openAndRead(commandLine.getOptionValue("f"));
                ObtainSequences getSequences = new ObtainSequences(msaSequences);
                ObtainConsensusSequences consensus = new ObtainConsensusSequences(getSequences);
                if (commandLine.hasOption("s")){
                    Summary summary = new Summary(getSequences, consensus);
                    summary.runThesePrograms(msaSequences, commandLine.getOptionValue("f"));
                    System.out.println(summary);
                }
                if (commandLine.hasOption("d")){
                    ProbeDesigner probedesigner = new ProbeDesigner(consensus);
                    probedesigner.runPrograms(commandLine);
                    System.out.println(probedesigner);
                    return;
                }
            }
            if (commandLine.hasOption("h")){
                showHelp();
            }
        } catch (ParseException e) {
            showHelp();
        }
    }

    /**
     * Prints the help message.
     */
    private void showHelp(){
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("These are the valid argument options", options);
    }

    /**
     * Opens and reads the msa file. Then returns the sequences in a String.
     * @param msaFile, file which has been provided hy parameter -f
     */
    private void openAndRead(String msaFile){
        try{
            BufferedReader br = new BufferedReader(new FileReader(msaFile));
            String line;
            while ((line = br.readLine()) != null){
                msaSequences = String.format("%s%s%s", msaSequences, System.lineSeparator(), line);
            }
            br.close();
        }catch(Exception e){
            System.err.format("Exception occurred trying to read '%s'.", msaFile);
        }
    }
}
