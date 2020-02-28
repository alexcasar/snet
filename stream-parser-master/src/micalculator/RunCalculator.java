/**
 * 
 * Entry point for the MI calculator. Calls the class to process a text file
 * and calculate pair-wise MI from it.
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package micalculator;

import mstparser.*;

import java.io.File;
import java.util.HashMap;

public class RunCalculator {

	public static void main(String[] args) {
		System.out.println("MI Calculator!");

		GetVocabulary vocabTableInstance = new GetVocabulary("data/sample.vocab");
		HashMap vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveDirectory(new File("data/sample_corpus/"), 3);

		calculatorInstance.CalculateExpPMI();
	}
}
