package nl.bioinf.adedic.Probedesigner.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Summary {
    private String fileName;
    private String downloadDate;
    private int length,unChanged_Residues, residues_R, residues_N, residues_W, residues_Y;
    private String consensusSequence;
    private ArrayList<String> getSequence;

    /**
     * Constructor, which receives the MSA sequences from the ObtainSequences class
     * @param getSequences, MSA sequences, which will be used to calculate some information
     * @param consensusSequences, consensusSequence, which will be used to calculate some information and to
     * create the probes in the ProbeDesigner class
     */
    public Summary(ObtainSequences getSequences, ObtainConsensusSequences consensusSequences) {
        this.getSequence = getSequences.getResults();
        this.fileName = null;
        this.downloadDate = null;
        this.length = 0;
        this.residues_R = 0;
        this.residues_N = 0;
        this.residues_W = 0;
        this.residues_Y = 0;
        this.consensusSequence = consensusSequences.getConsensusSequences();
    }

    /**
     * Start method, which runs all programs in this class.
     * @param msaSequences, fileName, sequences of the msa file and the msa filename.
     */
    public void runThesePrograms(String msaSequences, String fileName) throws ParseException {
        this.fileName = fileName;
        downloadDate = getDownloadDate();
        length = getSequencesLength(msaSequences);
        unChanged_Residues = getUnchangedResidues();
        getLengthOfResidues();
    }


    /**
     * Obtains the download date of the msa file
     */
    private String getDownloadDate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(dateFormat.parse("2017/06/10"));
    }

    /**
     * Obtains the sequences length of the msa file
     * @param msaSequences, contains the DNA sequences
     */
    private int getSequencesLength(String msaSequences) {
        return Integer.parseInt(Integer.toString(msaSequences.length() -1));
    }

    /**
     * Obtains all the residues which has not been changed. They are then stored into a class variable which
     * can be accessed throughout this class.
     */
    private int getUnchangedResidues() {
        int gaps = getSequence.get(0).length();
        for (int i = 0; i < getSequence.get(0).length(); i++) {
            String comparingChars = null;
            for (String chars : getSequence) {
                chars = String.valueOf(chars.charAt(i));
                if (comparingChars == null) {
                    comparingChars = chars;
                }
                if (comparingChars.equals(chars)) {
                    comparingChars = chars;
                } else {
                    gaps --;
                    break;
                }
            }
        }
        return gaps;
    }

    /**
     * Counts the occurrences of some residues and stores them into a class variable. By splitting the sequence
     * and specifying a pattern for the residue.
     */
    private void getLengthOfResidues(){
        //Sources of inspiration:
        //inspiration source: http://www.baeldung.com/java-count-chars
        //https://stackoverflow.com/questions/6100712/simple-way-to-count-character-occurrences-in-a-string
        residues_R = consensusSequence.split("R", -1).length -1;
        residues_N = consensusSequence.split("N", -1).length -1;
        residues_W = consensusSequence.split("W", -1).length -1;
        residues_Y = consensusSequence.split("Y", -1).length -1;
    }

    @Override
    public String toString() {
        return "You have entered the arguments to view the summary of this file\n\n"
                + "File: \t\t\t\t\t" + fileName
                + "\n" + "Download date: \t\t\t" + downloadDate
                + "\n" + "Length of sequences: \t" + length
                + "\n" + "Unchanged residues: \t" + unChanged_Residues
                + "\n" + "R residue(A/G): \t\t" + residues_R
                + "\n" + "N residue(G/A/T/C): \t" + residues_N
                + "\n" + "W residue(A/T): \t\t" + residues_W
                + "\n" + "Y residue(C/T): \t\t" + residues_Y;
    }
}