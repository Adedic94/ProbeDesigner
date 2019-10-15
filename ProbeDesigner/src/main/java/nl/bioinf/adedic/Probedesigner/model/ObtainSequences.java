package nl.bioinf.adedic.Probedesigner.model;

import java.util.ArrayList;

public class ObtainSequences {
    private ArrayList<String> seqList;

    /**
     * Constructor, which stores DNA sequences in a class variable
     * @param msaSequences, contains DNA sequences without headers.
     */
    public ObtainSequences(String msaSequences){
        seqList = getSequences(msaSequences);
    }

    /**
     * This method splits the msaSequences and stores only the sequences without the > character in a list.
     * @param msaSequences, contains the sequences of the msa file including the header.
     * @return msaList, contains only DNA sequences without headers.
     */
    private ArrayList<String> getSequences(String  msaSequences){
        String [] sequenceSplit = msaSequences.split("\\r\\n");
        ArrayList<String> msaList = new ArrayList<>();
        StringBuilder storageForNewSequence = new StringBuilder();
        for (String aSequenceSplit : sequenceSplit) {
            if (aSequenceSplit.startsWith(">")) {
                if (!storageForNewSequence.toString().equals("")) {
                    msaList.add(storageForNewSequence.toString());
                }
                storageForNewSequence = new StringBuilder();
            } else {
                storageForNewSequence.append(aSequenceSplit);
            }
        }
        msaList.add(storageForNewSequence.toString());
        return msaList;
    }

    /**
     * getter method, which can provide the obtained sequences to other classes
     * @return seqList, which contains msa sequences without header.
     */
    ArrayList<String> getResults(){
        return seqList;
    }
}
