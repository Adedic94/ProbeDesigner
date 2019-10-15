package nl.bioinf.adedic.Probedesigner.model;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.CommandLine;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProbeDesigner {
    private String consensusSequence;
    private static int maxSizeOfProbe = 27;
    private static int minSizeOfProbe = 12;
    private static int maxNumberOfProbes = 5;
    private static int[] probesLocation;
    private static String[] completedProbesArray;
    private static int[] probesLength;


    /**
     * Constructor, which receives the consensusSequences from the ObtainConsensusSequences class
     * @param consensusSequences, which will be used to create probes.
     */
    public ProbeDesigner(ObtainConsensusSequences consensusSequences){
        consensusSequence = consensusSequences.getConsensusSequences();
    }

    /**
     * Start method, which runs all programs of this class.
     */
    public void runPrograms(CommandLine commandLine){
        setArgumentsForProbe(commandLine);
        ArrayList<String> probes = createProbe();
        ArrayList<String> workingProbes = probeControl(probes);
        ArrayList<String> sortedWorkingProbes = sortingProbes(workingProbes);
        String[] completedProbesArray = getProbesEqualToAskedAmount(sortedWorkingProbes);
        getLengthOfProbe(completedProbesArray);
        getStartLocationOfProbe(completedProbesArray);
    }

    /**
     * Setting options for the probe
     * @param commandLine, to get the value of the arguments for the probes
     */
    private void setArgumentsForProbe(CommandLine commandLine){
        try {
            if (commandLine.hasOption("n")) {
                minSizeOfProbe = Integer.valueOf(commandLine.getOptionValue("n"));
            }
            if (commandLine.hasOption("m")) {
                maxSizeOfProbe = Integer.valueOf(commandLine.getOptionValue("m"));
            }
            if (commandLine.hasOption("a")) {
                maxNumberOfProbes = Integer.valueOf(commandLine.getOptionValue("a"));
            }
        }catch(NumberFormatException e){
            showHelp();
        }
    }

    /**
     *Prints the help message if no arguments are provided for the probe
     */
    private void showHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("User needs to provide arguments to get the probe", null);
    }

    /**
     *Creates all possible probes
     * @return probes
     */
    private ArrayList<String> createProbe() {
        ArrayList<Character> DNA = new ArrayList<>();
        ArrayList<String> probes = new ArrayList<>();
        StringBuilder probe = new StringBuilder();
        boolean conserved = false;
        DNA.add('A');
        DNA.add('T');
        DNA.add('G');
        DNA.add('C');
        DNA.add('-');
        DNA.add('.');
        for (int i = 0; i < consensusSequence.length(); i++){
            if (!DNA.contains(consensusSequence.charAt(i))) {
                if (conserved) {
                    probes.add(probe.toString());
                }
                probe = new StringBuilder();
                conserved = false;
            }else {
                probe.append(consensusSequence.charAt(i));
                conserved = true;
            }
            if (i == consensusSequence.length() - 1 && conserved) {
                probes.add(probe.toString());
            }
        }
        return probes;
    }

    /**
     * Controls the probes if they meet the requirements (the minimum and maximum size and number of probes)
     * If user asked for more probes then there actually is, an explanation will be given.
     * @param probes, all probes which passed the control
     */
    private ArrayList<String> probeControl(ArrayList<String> probes){
        ArrayList<String> workingProbes = new ArrayList<>();
        for (String probe : probes) {
            if (probe.length() >= minSizeOfProbe && probe.length() <= maxSizeOfProbe) {
                workingProbes.add(probe);
            }
        }
        if (maxNumberOfProbes > workingProbes.size()){
            System.out.println("\nNOTE!!:");
            System.out.println("You have asked for " + maxNumberOfProbes + " probes, but there are only "
                    + workingProbes.size() + " found");
            maxNumberOfProbes = workingProbes.size();
        }
        return workingProbes;
    }

    /**
     * This method sorts the probes from large to small.
     * @param workingProbes, workingProbes which met the requirements.
     * @return workingProbes, These probes are now sorted.
     */
    private ArrayList<String> sortingProbes(ArrayList<String> workingProbes){
        //https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property
        workingProbes.sort(Comparator.comparing(String::length).reversed());
        return workingProbes;
    }

    /**
     * This method filters the probes which has been asked by the user. If the user asks for 2 probes and there are
     * more, the user will then only get the two largest probes.
     * @param sortedWorkingProbes, probes which are sorted from largest to smallest.
     * @return completedProbesArray
     */
    private String[] getProbesEqualToAskedAmount(ArrayList<String> sortedWorkingProbes){
        //Source: https://stackoverflow.com/questions/18313392/selection-of-first-l-items-of-arraylist-of-size-n-l-and-
        //insertion-to-another-a
        List completedProbes = new ArrayList();
        int start = 0;
        completedProbes = sortedWorkingProbes.subList(start, maxNumberOfProbes);
        completedProbesArray = new String[completedProbes.size()];
        for(int i = 0; i < completedProbesArray.length; i++){
            completedProbesArray[i] = completedProbes.get(i).toString();
        }
        return completedProbesArray;
    }

    /**
     * This method determines the length of each probe from the completedProbesArray.
     * @param completedProbesArray, are the amount of probes which met the requirements and are sorted.
     */
    private void getLengthOfProbe(String[] completedProbesArray){
        probesLength = new int[completedProbesArray.length];
        for (int i = 0; i < completedProbesArray.length; i++){
            probesLength[i] = completedProbesArray[i].length();
        }
    }

    /**
     * This method determines the start position of each probe that has been found for this request.
     * @param completedProbesArray, are the amount of probes which met the requirements and are sorted.
     */
    private void getStartLocationOfProbe(String[] completedProbesArray){
        //Source: https://shekhargulati.com/2010/05/04/finding-all-the-indexes-of-a
        // -whole-word-in-a-given-string-using-java/
        probesLocation = new int[completedProbesArray.length];
        for (int i = 0; i < completedProbesArray.length; i++){
            probesLocation[i] = consensusSequence.indexOf(completedProbesArray[i]);
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("ConsensusSequences: \t").append(consensusSequence).
                append("\nStart locations: \t\tProbe length: \t\tProbe Sequence:\t\t");
        for (int i = 0; i < completedProbesArray.length; i++){
            builder.append("\n").append(probesLocation[i]).append("\t\t\t\t\t\t").
                    append(probesLength[i]).append("\t\t\t\t\t").append(completedProbesArray[i]);
        }
        return builder.toString();
    }
}
