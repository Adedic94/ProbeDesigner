package nl.bioinf.adedic.Probedesigner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class ObtainConsensusSequences {
    private ArrayList<String> seqList;
    private String consensusSequences;

    /**
     * Constructor, which receives the MSA sequences from the ObtainSequences class
     * @param getSequences, sequences which will be used to generate the consensusSequences
     */
    public ObtainConsensusSequences(ObtainSequences getSequences){
        seqList = getSequences.getResults();
        consensusSequences = createConservationProfile();
    }

    /**
     * Creating consensus Sequence codes using HashMap. Which will be used in the createConservationProfile method to
     * determine the consensus sequences.
     */
    private HashMap<String, String> consensusMapIuPAC;
    {
        consensusMapIuPAC = new HashMap<>();
        consensusMapIuPAC.put("[G]", "G");
        consensusMapIuPAC.put("[C]", "C");
        consensusMapIuPAC.put("[T]", "T");
        consensusMapIuPAC.put("[A]", "A");
        consensusMapIuPAC.put("[A, T]", "W");
        consensusMapIuPAC.put("[C, G]", "S");
        consensusMapIuPAC.put("[A, C]", "M");
        consensusMapIuPAC.put("[G, T]", "K");
        consensusMapIuPAC.put("[A, G]", "R");
        consensusMapIuPAC.put("[C, T]", "Y");
        consensusMapIuPAC.put("[-]", "gap");
        consensusMapIuPAC.put("[.]", "gap");
        consensusMapIuPAC.put("[C, G, T]", "B");
        consensusMapIuPAC.put("[A, G, T]", "D");
        consensusMapIuPAC.put("[A, C, T]", "H");
        consensusMapIuPAC.put("[A, C, G, T]", "N");
    }

    /**
     * Going through the obtained sequences which is accessed by the class variable seqList. It fills an
     * empty string by obtaining the corresponding IuPAC character.
     * @return consensusSequence, contains the translated version of the non-DNA to IuPAC codes.
     */
    private String createConservationProfile() {
        StringBuilder consensusSequence = new StringBuilder();
        Set<Character> uniqueDNA = new TreeSet<>();
        for(int j = 0; j < seqList.get(0).length(); j++) {
            for(String sequence : seqList){
                uniqueDNA.add(sequence.charAt(j));
            }
                //All IuPAC codes are generated which corresponds to the Unique DNA characters.
            consensusSequence.append(consensusMapIuPAC.get(uniqueDNA.toString()));

            uniqueDNA.clear();
        }
        return consensusSequence.toString();
    }

    /**
     * @return consensusSequences
     */
    String getConsensusSequences(){
        return consensusSequences;
    }
}
