/**
 * 
 * Entry point for the MST-parser. Calls the class and executes the parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 * @param		windowObserve: (int) size of window to calculate PMI
 * @param		windowParse: (int) size of window for parsing
 * @param		calculateScores: (bool) Flag to calculate PMI. False implies scores are imported
 * @param		exportScores: (bool) Flag to import PMI. Only relevant if calculateScores is true
 * @param		vocabFilePath: (string) Path to vocab file
 * @param		scoresFilePath: (string) Path to export/import PMI
 * @param		observeCorpusPath: (string) Path for corpus to observe (for PMI). Only relevant if calculateScores is true
 * @param		corpusPath: (string) Path for corpus to parse
 * @param		parsesPath: (string) Path to store parses
 *
*/
package mstparser;

import micalculator.MICalculator;
import org.ojalgo.matrix.store.SparseStore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RunParser {

	public static void main(String[] args) throws IOException {
		int windowObserve = Integer.parseInt(args[0]);
		int windowParse = Integer.parseInt(args[1]);
		boolean calculateScores = Boolean.parseBoolean(args[2]);
		boolean exportScores =  Boolean.parseBoolean(args[3]);
		String vocabFilePath = args[4];
		String scoresFilePath = args[5];
		String observeCorpusPath = args[6];
		String corpusPath = args[7];
		String parsesPath = args[8];

		SparseStore<Double> scoreMatrix;

		System.out.println("Stream parser!");
		System.out.println("Reading vocabulary...");
		GetVocabulary vocabTableInstance = new GetVocabulary(vocabFilePath);
		HashMap<String,Integer> vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		if (calculateScores) {
			System.out.println("Calculating PMI values...");
			calculatorInstance.ObserveDirectory(new File(observeCorpusPath), windowObserve);
			scoreMatrix = calculatorInstance.CalculateExpPMI();
			if (exportScores) {
				System.out.println("Exporting PMI values...");
				calculatorInstance.ExportPMIMatrix(scoresFilePath);
			}
		} else {
			System.out.println("Importing PMI values...");
			scoreMatrix = calculatorInstance.ImportPMIMatrix(scoresFilePath);
		}

		System.out.println("Creating score function...");
		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		System.out.println("Parsing corpus...");
		testParser.parseCorpus(corpusPath, windowParse, parsesPath);

		System.out.println("DONE!");
	}
}
