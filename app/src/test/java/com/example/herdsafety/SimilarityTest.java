package com.example .herdsafety;

import static org.junit.Assert.assertEquals;

import com.example.herdsafety.Similarity.CautionSimilarity;
import com.example.herdsafety.Similarity.CrimeSimilarity;
import com.example.herdsafety.Similarity.SimilarityStrategy;
import com.example.herdsafety.Similarity.WarningSimilarity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Hashtable;

public class SimilarityTest {

    public String[] SimilarityTestCheck(){
        SimilarityStrategy test_checker = new SimilarityStrategy();
        String[] test = test_checker.normalizeDescription("i Am rEally.. screwed, up!");
        return test;
    }

    public double CrimeSimilarityCheck(){

        CrimeSimilarity test_checker = new CrimeSimilarity();
        String desc_1 = "A car broke down here";
        String desc_2 = "There is a car broke down here";

        return test_checker.simple_sim_score(desc_1, desc_2);
    }

    String[] test_arr = {"i","am","really","screwed","up"};
    @Test
    public void string_is_normal(){ assertEquals(SimilarityTestCheck(), test_arr); }

    @Test
    public void string_size_correct(){assertEquals(SimilarityTestCheck().length, 5);}

    @Test public void crime_sim_correct() {
            System.out.println("Actual " + CrimeSimilarityCheck());
            Assert.assertEquals(0.9,CrimeSimilarityCheck(), 0.05);
    }

    @Test
    public void caution_correct() {
        CautionSimilarity test = new CautionSimilarity();
        //Exact Math test
        Assert.assertEquals(1, test.get_cosine_similarity("a Truck ran Past", "truck RAN past a"), 0);
        //Nothing in common
        Assert.assertEquals(0, test.get_cosine_similarity("a Truck ran Past", "fans run in the wind"), 0);
    }

    @Test
    public void warning_test() {
        WarningSimilarity test = new WarningSimilarity();

        System.out.println(test.letter_sim("Several many men attacked", "I was attacked"));
    }

    //Testing for Crime Similarity
    @Test
    public void crime_sim_testing(){
        CrimeSimilarity test = new CrimeSimilarity();

        Hashtable<Integer, String> example_table = new Hashtable<>();

        example_table.put(1, "There was a soccer game");
        example_table.put(2, "I hate soccer games");
        example_table.put(3, "Football is a better game");
        example_table.put(4, "Yesterday I went to a new soccer game");
        example_table.put(5, "Bad dad sad");
        example_table.put(6, "It seems today, all you see-");

        String ex_new_desc = "Bad dad soccer sad football";

        int id = test.similarPostId(ex_new_desc, example_table);

        System.out.println(id);


    }

    @Test
    public void caution_sim_testing(){
        CautionSimilarity test = new CautionSimilarity();

        Hashtable<Integer, String> example_table = new Hashtable<>();

        example_table.put(1, "There was a soccer game");
        example_table.put(2, "I hate soccer games");
        example_table.put(3, "Football is a better game");
        example_table.put(4, "Yesterday I went to a new soccer game");
        example_table.put(5, "Bad dad sad");
        example_table.put(6, "It seems today, all you see-");

        String ex_new_desc = "It hate games, all new soccer";

        int id = test.similarPostId(ex_new_desc, example_table);

        System.out.println(id);
    }

    @Test
    public void warning_sim_testing(){
        WarningSimilarity test = new WarningSimilarity();

        Hashtable<Integer, String> example_table = new Hashtable<>();

        example_table.put(1, "There was a soccer game");
        example_table.put(2, "I hate soccer games");
        example_table.put(3, "Football is a better game");
        example_table.put(4, "Yesterday I went to a new soccer game");
        example_table.put(5, "Bad dad sad");
        example_table.put(6, "It seems today, all you see-");

        String ex_new_desc = "I say Football is a better game";

        int id = test.similarPostId(ex_new_desc, example_table);

        System.out.println(id);
    }



}