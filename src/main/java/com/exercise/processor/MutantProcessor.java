package com.exercise.processor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Created by vgerb on 3/9/18.
 */
@Component
public class MutantProcessor {

    public MutantProcessor(){
        this.nitrogenBaseMap = new HashMap<>();
        nitrogenBaseMap.put('A',1);
        nitrogenBaseMap.put('C',3);
        nitrogenBaseMap.put('T',5);
        nitrogenBaseMap.put('G',7);
    }

    private Map<Character,Integer> nitrogenBaseMap;

    private int checkDNAHorizontal(String s, int indexOf){
        if(indexOf +1 < s.length() && indexOf+2 < s.length() && indexOf+3 < s.length()){
            if(s.charAt(indexOf)==s.charAt(indexOf+1) &&
                    s.charAt(indexOf)==s.charAt(indexOf+2) &&
                    s.charAt(indexOf)==s.charAt(indexOf+3)){
                return 1;
            } else {
                return 0;
            }
        }

        return 0;
    }

    private int checkDNAVertical(String [] dna, int dnaIndex, int charIndex){
        if(dnaIndex + 1 < dna.length && dnaIndex + 2 < dna.length && dnaIndex + 3 < dna.length){
            if(dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+1].charAt(charIndex) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+2].charAt(charIndex) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+3].charAt(charIndex) ){
                return 1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    private int checkDNADiagonalDown(String [] dna, int dnaIndex, int charIndex){
        if(dnaIndex + 1 < dna.length && dnaIndex + 2 < dna.length && dnaIndex + 3 < dna.length &&
                charIndex +1 < dna[dnaIndex].length() && charIndex+2 < dna[dnaIndex].length() && charIndex+3 < dna[dnaIndex].length()){
            if(dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+1].charAt(charIndex+1) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+2].charAt(charIndex+2) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex+3].charAt(charIndex+3)){
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private int checkDNADiagonalUp(String [] dna, int dnaIndex, int charIndex){
        if(dnaIndex - 1 >= 0 && dnaIndex - 2 >= 0 && dnaIndex - 3 >= 0 &&
                charIndex + 1 < dna[dnaIndex-1].length() && charIndex + 2 < dna[dnaIndex-2].length() && charIndex + 3 < dna[dnaIndex-3].length()){
            if(dna[dnaIndex].charAt(charIndex)==dna[dnaIndex-1].charAt(charIndex+1) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex-2].charAt(charIndex+2) &&
                    dna[dnaIndex].charAt(charIndex)==dna[dnaIndex-3].charAt(charIndex+3)){
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }


    public boolean isMutant(String[] dna) {
        int dnaChainCounter = 0;
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna[i].length(); j++) {
                dnaChainCounter += checkDNAHorizontal(dna[i], j);
                if (dnaChainCounter > 1)
                    break;
                dnaChainCounter += checkDNAVertical(dna, i, j);
                if (dnaChainCounter > 1)
                    break;
                dnaChainCounter += checkDNADiagonalDown(dna, i, j);
                if (dnaChainCounter > 1)
                    break;
                dnaChainCounter += checkDNADiagonalUp(dna, i, j);
            }
            if (dnaChainCounter > 1) {
                break;
            }
        }
        return dnaChainCounter > 1 ? true : false;
    }

    public long hash(String [] dna){
        long hash = 0L;
        for(int i=0 ; i < dna.length ; i++){
            for(int j = 0 ; j < dna[i].length() ; j++){
                Character chr = new Character(dna[i].charAt(j));
                Long num = new Long(nitrogenBaseMap.get(chr));
                hash = hash * num + Long.valueOf(dna[i].charAt(j));
            }
        }
        return hash;

    }
}
