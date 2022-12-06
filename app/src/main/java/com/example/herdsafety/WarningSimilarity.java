package com.example.herdsafety;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

public class WarningSimilarity extends SimilarityStrategy {

    public double letter_sim(String description, String trg_desc){

        String no_space_desc = String.valueOf(description).replace(" ", "");
        String no_space_trg_desc = String.valueOf(trg_desc).replace(" ", "");

        char[] no_space_original = no_space_desc.toCharArray();
        char[] no_space_target = no_space_trg_desc.toCharArray();

        int shorter_string = 0;

        if(no_space_original.length >= no_space_target.length){
            shorter_string = no_space_target.length;
        } else {
            shorter_string = no_space_original.length;
        }

        int count = 0;

        for(int i = 0; i < shorter_string; i++){
            if (no_space_original[i] != no_space_target[i]) {
                count++;
            }
        }

        return 1 - ((double) count / (double) shorter_string);
    }

    public int similarPostId(String new_alert, Hashtable<Integer, String> nearby_alerts){

        int suspect_id = -1;
        double highest_sim = -1;
        ArrayList<Integer> keys = new ArrayList<Integer>(nearby_alerts.keySet());

        for(int i = 0; i < nearby_alerts.size(); i++){

            double current_score = letter_sim(new_alert, nearby_alerts.get(keys.get(i)));

            if(current_score > highest_sim){

                highest_sim = current_score;
                suspect_id = keys.get(i);


            }


        }


        return suspect_id;
    }


}
