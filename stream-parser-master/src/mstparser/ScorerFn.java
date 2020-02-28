/**
 * ScorerFn class

 * Main attributes:
 *
 * Scoring function that searches the provided SparseArray
 * for the score of the two given words.
 * It determines the vocabulary indexes of the words. This allows
 * to accommodate word-pair global scores, as well as
 * sentence-specific ones.
 * THIS SCORER WORKS FOR CONTEXT-INDEPENDENT SCORES
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 * @param		(HashMap) vocabulary: Mapping between words and indexes
 * @param		(SparseStore) scores: matrix with word-pair scores, indexed according to vocab mapping
*/
package mstparser;

import java.util.HashMap;
import org.ojalgo.matrix.store.SparseStore;

public class ScorerFn {
	private SparseStore<Double> scores; // structure with word-pair scores
	private HashMap<String,Integer> vocabulary;

	// Class constructor
	public ScorerFn(HashMap<String,Integer> vocab, SparseStore<Double> scores) {
		this.vocabulary = vocab;
		this.scores = scores;
	}

	double getScore(String w1, String w2) {
		double curr_score = -1e10; // bad score for words not in vocabulary
		if (vocabulary.containsKey(w1) && vocabulary.containsKey(w2)) {
			int w1_index = vocabulary.get(w1);
			int w2_index = vocabulary.get(w2);
			curr_score = scores.get(w1_index, w2_index);
		}

		return Math.log(curr_score); // THIS ASSUMES THAT scores obtained are e^PMI
	}
}
