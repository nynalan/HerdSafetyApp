package com.example.herdsafety.Similarity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class CrimeSimilarity extends SimilarityStrategy {

    /**
     * Takes in the new posts description and a description of another post
     * and counts how many words are shared out of total words, returns a double
     * that represents how similar the strings are
     */
    public double simple_sim_score(String description, String trg_desc){

        String[] norm_desc = normalizeDescription(description);
        String[] norm_trg = normalizeDescription(trg_desc);

        double similar_words = 0;
        double total_words = norm_desc.length + norm_trg.length;

        for (String value : norm_desc) {

            for (String s : norm_trg) {

                if (Objects.equals(value, s)) {
                    similar_words += 2;
                }

            }

        }

        return similar_words / total_words;

    }

    public int similarPostId(String new_alert, Hashtable<Integer, String> nearby_alerts) {

        int suspect_id = -1;
        double highest_sim = -1;
        ArrayList<Integer> keys = new ArrayList<Integer>(nearby_alerts.keySet());

        for (int i = 0; i < nearby_alerts.size(); i++) {

            double current_score = simple_sim_score(new_alert, nearby_alerts.get(keys.get(i)));

            if (current_score > highest_sim){

                highest_sim = current_score;
                suspect_id = keys.get(i);

            }

        }

        return suspect_id;
    }


}
