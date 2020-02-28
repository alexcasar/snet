/**
 * ScorerFn class

 * Main attributes:
 * 
 * Scoring function that searches the provided HashMap
 * for the score of the two given words.
 * It determines the hash table index of the words. This allows
 * to accomodate word-pair global scores, as well as 
 * sentence-specific ones.
 * THIS SCORER WORKS FOR CONTEXT-INDEPENDENT SCORES
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;

public class ScorerFn {
	private HashMap<String,Float> scores; // structure with word-pair scores
	private HashMap<String,Integer> vocabulary;

	// Class constructor
	public ScorerFn(HashMap vocab, HashMap scores) {
		this.vocabulary = vocab;
		this.scores = scores;
	}

	public float getScore(String w1, String w2) {
		float curr_score = -1e10f; // bad score for words not in vocabulary
		if (vocabulary.containsKey(w1) && vocabulary.containsKey(w2)) {
			int w1_index = vocabulary.get(w1);
			int w2_index = vocabulary.get(w2);
			curr_score = scores.get(w1_index + "_" + w2_index);
		}

		return curr_score;
	}
}
