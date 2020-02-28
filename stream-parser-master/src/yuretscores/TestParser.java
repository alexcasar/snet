/**
 * 
 * Entry point for the MST-parser. Calls the class and executes the parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package yuretscores;

import mstparser.*;
import org.ojalgo.matrix.store.SparseStore;

import java.io.IOException;
import java.util.HashMap;

public class TestParser {

	public static void main(String[] args) throws IOException {
		int window = 6;

		System.out.println("Stream parser!");

		System.out.println("Reading vocabulary...");
		GetVocabulary vocabTableInstance = new GetVocabulary("src/workdir/Yuret.dict");
		//GetVocabulary vocabTableInstance = new GetVocabulary("data/GC/GC.dict");
		HashMap<String,Integer> vocabTable = vocabTableInstance.getVocabTable();

		System.out.println("Obtaining scores...");
		YuretScores yuretScoresInstance = new YuretScores();
		SparseStore<Double> scoreMatrix = yuretScoresInstance.ReturnScores();

		System.out.println("Creating score function...");
		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		System.out.println("Parsing corpus...");
		testParser.parseSentence("LW these people also want more government money for education . RW", window);
		//testParser.parseCorpus("data/GC/MSL25-2019JUL01", window, "data/GC/parses");
        testParser.printParse();

		System.out.println("DONE!");
	}
}
