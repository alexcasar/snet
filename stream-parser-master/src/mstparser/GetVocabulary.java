/**
 * GetVocabulary class

 * Main attributes:
 * 
 * Generator of a vocabulary HashMap from a list of vocabulary in text format.
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 * @param		(String) vocabFilePath: file path to vocabulary file
*/
package mstparser;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetVocabulary {

	private HashMap<String,Integer> vocabularyTable;

	// The constructor opens the file and builds the hashmap from it
	public GetVocabulary(String vocabFilePath) {
		vocabularyTable = new HashMap<>();
		try {
			Scanner scanner = new Scanner(new File(vocabFilePath));
			int index = 0;
			while (scanner.hasNextLine()) {
				String[] split_line = scanner.nextLine().split("\\s+");
				this.vocabularyTable.put(split_line[0], index++);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
      
    public HashMap getVocabTable() {
    	return vocabularyTable;
    }
}