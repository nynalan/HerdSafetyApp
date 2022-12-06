package com.example.herdsafety.Similarity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CautionSimilarity extends SimilarityStrategy {


    //Adapted from https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-similarity-of-two-vectors Alphaaa
    private double cosine_similarity(List<Hashtable> original_compare){
        double d_p = 0.0;
        double n_org = 0.0;
        double n_com = 0.0;

        ArrayList<String> keys = new ArrayList<>();

        if(original_compare.get(0).size() >= original_compare.get(1).size()) {
            keys.addAll(original_compare.get(1).keySet());
        }
        else{
            keys.addAll(original_compare.get(0).keySet());
        }

        for (int i = 0; i < keys.size(); i++){
            int original_int = (int) original_compare.get(0).get(keys.get(i));
            int compare_int = (int) original_compare.get(1).get(keys.get(i));

            double original = original_int;
            double compare = compare_int;

            d_p += original * compare;
            n_org += Math.pow(original, 2);
            n_com += Math.pow(compare, 2);
        }

        return d_p / ((Math.sqrt(n_org)) * (Math.sqrt(n_com)));
    }

    public double get_cosine_similarity(String original_desc, String target_desc){

        //format the strings to remove punc and make lowercase, vectorize the strings into hashtables
        Hashtable<String, Integer> t_1_vec = buildVectors(normalizeDescription(original_desc));
        Hashtable<String, Integer> t_2_vec = buildVectors(normalizeDescription(target_desc));

        //order the vectors by natural order and list occurrences for both on every word
        List<Hashtable> hash_tuple = correct_vectors(t_1_vec,t_2_vec);

        //calculate the cosine similarity
        return cosine_similarity(hash_tuple);

    }

    public int similarPostId(String new_alert, Hashtable<Integer, String> nearby_alerts) {

        int suspect_id = -1;
        double highest_sim = -1;
        ArrayList<Integer> keys = new ArrayList<Integer>(nearby_alerts.keySet());

        for (int i = 0; i < nearby_alerts.size(); i++) {

            double current_score = get_cosine_similarity(new_alert, nearby_alerts.get(keys.get(i)));

            if (current_score > highest_sim){

                highest_sim = current_score;
                suspect_id = keys.get(i);

            }

        }

        return suspect_id;
    }

}
