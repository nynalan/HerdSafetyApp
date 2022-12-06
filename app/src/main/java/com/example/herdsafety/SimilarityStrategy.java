package com.example.herdsafety;

import com.example.herdsafety.AppObjects.AAlert;

import java.util.ArrayList;
import java.util.Hashtable;

public class SimilarityStrategy {

    public String[] normalizeDescription(String description){
        String[] normalized_arr = description.replaceAll("[^a-zA-z ]", "").toLowerCase().split("\\s+");
        return normalized_arr;
    }

    public Hashtable<String, Integer> buildVectors(String[] alert_body){
        Hashtable<String, Integer> word_vectors = new Hashtable<String, Integer>();

        for (String word : alert_body){

            if(word_vectors.containsKey(word)) {
                word_vectors.put(word, word_vectors.get(word) + 1);
            } else {
                word_vectors.put(word, 1);
            }

        }

        return word_vectors;

    }

    public ArrayList<Hashtable> correct_vectors(Hashtable<String, Integer> vec_1, Hashtable<String, Integer> vec_2){

        Hashtable<String, Integer> original = new Hashtable<String, Integer>();
        Hashtable<String, Integer> compare = new Hashtable<String, Integer>();

        ArrayList<String> keys = new ArrayList<>();

        keys.addAll((vec_1.keySet()));
        keys.addAll((vec_2.keySet()));

        for (int i = 0; i < keys.size(); i++){

            if((vec_1.containsKey(keys.get(i))) & (vec_2.containsKey(keys.get(i)))){

                original.put(keys.get(i), vec_1.get(keys.get(i)));
                compare.put(keys.get(i), vec_2.get(keys.get(i)));

            }
            else if((vec_1.containsKey(keys.get(i))) & !(vec_2.containsKey(keys.get(i)))){

                original.put(keys.get(i), vec_1.get(keys.get(i)));
                compare.put(keys.get(i), 0);

            }
            else if(!(vec_1.containsKey(keys.get(i))) & (vec_2.containsKey(keys.get(i)))){

                original.put(keys.get(i), 0);
                compare.put(keys.get(i), vec_2.get(keys.get(i)));

            }

        }

        ArrayList<Hashtable> original_compare = new ArrayList<>(2);

        original_compare.add(original);
        original_compare.add(compare);

        return original_compare;

    }

    public int similarPostId(String new_alert, Hashtable<Integer, String> nearby_alerts){
        return -1;
    }


}
