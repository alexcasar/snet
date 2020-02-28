/**
 * MSTparser class

 * Main attributes:
 * 
 * Unlabeled dependency parser for streams of tokens
 *
 * Java implementation of the Minimum Spanning Tree (MST) parser specified by Deniz Yuret in his PhD thesis: Yuret, Deniz.
 * "Discovery of linguistic relations using lexical attraction." arXiv preprint cmp-lg/9805009 (1998).
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 * @param		(ScoreFn) scorer: instance of a class with a method that returns a score given a word-pair
*/
package mstparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class MSTparser {

	private ArrayList<Link> stack;
	private ArrayList<Link> minlink; // stores min valued Links
	private ArrayList<Link> links;
	private ArrayList<String> procSentence;
	private ScorerFn scorer;

	// Class constructor
	public MSTparser(ScorerFn scorer) {
		PrepareNewSentence();
		this.scorer = scorer;
	}

	// Resets structures, ready to parse a new sentence
	public void PrepareNewSentence() {
		stack = new ArrayList<Link>();
		minlink = new ArrayList<Link>();
		links = new ArrayList<Link>();
		procSentence = new ArrayList<String>();
	}

	// Process folder with text files (corpus), sending one file to parseFile at a time
	public void parseCorpus(String corpusFolderPath, int window, String parseFolderPath) throws IOException {
		File corpusFolder = new File(corpusFolderPath);
		for (final File fileEntry : corpusFolder.listFiles()) {
			String fileEntryPath = fileEntry.getPath();
			if (fileEntry.isDirectory()) {
				parseCorpus(fileEntryPath, window, parseFolderPath);
			} else {
				parseFile(fileEntryPath, window, parseFolderPath);
			}
		}
	}

	// Process file, sending sentence by sentence to parseSentence and exporting to file
    public void parseFile(String textFilePath, int window, String parseFolderPath) throws IOException {
		if (!Files.exists(Paths.get(parseFolderPath))) {
			Files.createDirectory(Paths.get(parseFolderPath)); // Create parseFolder if doesn't exist
		}
		try {
		    File textFile = new File(textFilePath);
			Scanner scanner = new Scanner(textFile);
			String parseFilePath = parseFolderPath + "/" + textFile.getName() + ".ull";
			System.out.print.ln(parseFolderPath + "/" + textFile.getName() + ".ull");
			Files.deleteIfExists(Paths.get(parseFilePath)); // Reset parseFile
			while (scanner.hasNextLine()) {
				String currLine = scanner.nextLine();
				if (!currLine.trim().equals("")) { // Skip empty lines
					parseSentence(currLine, window);
					exportParse(parseFilePath);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

    // Processes sentence, sending word by word to parseWord
	public void parseSentence(String sentence, int window) {
		PrepareNewSentence(); // Reset data from previous sentence
		if (!sentence.trim().equals("")) { // Skip empty lines
			for(String currWord : sentence.split("\\s+")){
				parseWord(currWord, window);
			}
		}
		printParse();
		
	}

	// This is the actual parser algorithm at work, for one word.
	// Adds one word to the current parsed sentence if other words have been
	// processed. If this is the first word in parse, it just gets added to
	// the processed sentence.
    private void parseWord(String word, int window) {
		procSentence.add(word);
		int word_num = procSentence.size();

		// if this is the first word parsed, just add it to proc_sentece
		if (word_num < 2) { // initialize sentence elsewhere to remove this conditional??
			return;
		}

		minlink.add(new Link()); // initialize position with dummy link

		// try to connect new word with every word to its left, within parsing window
		int windowBoundary = Math.max(0, word_num - 2 - window + 1);
		for (int i = word_num - 2; i >= 0; i--) {
			Link last = popRightLinks(i);
			if (i >= windowBoundary) { // Only continue if i is within parsing window
				if (last != null) {
					minlink.set(i, getMinLink(last, minlink.get(last.getRi())));
				}
				double currScore = scorer.getScore(procSentence.get(i), procSentence.get(word_num - 1));
				if (
						(currScore > 0)
								&& (currScore > getLinkScore(minlink.get(i)))
								&& (currScore > getStackMaxScore())) {
					stack.forEach(this::unLink); // unlink weakest crossing links
					stack = new ArrayList<Link>();
					unLink(minlink.get(i)); // unlink weakest link in loop
					Link new_link = new Link(i, word_num - 1, currScore);
					minlink.set(i, new_link); // assign weakest link from i to word
					links.add(new_link);
				}
				pushLeftLinks(i);
			}
		}
	}

	private double getLinkScore(Link this_link) {
		return this_link.getScore();
	}

	// Return the highest score from the stack, or 0 if it's empty
	private double getStackMaxScore() {
		double curr_max = 0;
		for (Link curr_link : stack) {
			if (curr_link.getScore() > curr_max) {
				curr_max = curr_link.getScore();
			}
		}
		return curr_max;
	}

	// Removes dead_link from current links
	private void unLink(Link dead_link) {
		int dead_id = links.indexOf(dead_link);
		if (dead_id >= 0) {
			links.remove(dead_id);
		}
	}

	// Searches the stack from right to left for the right links
	// of the given index. If there's a match, pops the link.
	// Returns the last link popped (the leftmost).
	private Link popRightLinks(int index) {
		Link result = null;
		// Traverse the stack in reverse, to function as stack
		if (stack.size() > 0) {
			for (int j = stack.size() - 1; j >= 0; j--) {
				Link curr_link = stack.get(j);
				if (curr_link.getLi() == index) {
					result = curr_link;
					stack.remove(j);
				}
			}
		}
		return result;
	}

	// Pushes to the stack all left links of the given index.
	// Yuret's algorithm is not clear on the order to push them, so
	// it is done here from left to right in the current link list.
	private void pushLeftLinks(int index) {
		links.forEach(link->{
			if(index == link.getRi()){
				stack.add(link);
			}
		});
	}

	// Given two links, returns the one with minimum score
	private Link getMinLink(Link l1, Link l2) {
		return (l1.getScore() >= l2.getScore()) ? l2 : l1;
	}

	// Adds the current sentence and its parse to file
	private void exportParse(String parseFilename) {
        try {
			// If the file doesn't exists, create and write to it
			// If the file exists, truncate (remove all content) and write to it
			String textSentence = String.join(" ", procSentence);
            String block = textSentence + "\n" + links2ULL() + "\n";
            Files.write(Paths.get(parseFilename), block.getBytes(),
					StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
	}

	// Converts links structure to ull format
	private String links2ULL() {
		LinkSorter linkSorter = new LinkSorter(links);
		ArrayList<Link> sortedLinks = linkSorter.getSortedLinkByPosition();
		String linksULL = "";
		for (Link currLink : sortedLinks) {
			String wl = procSentence.get(currLink.getLi());
			String wr = procSentence.get(currLink.getRi());
			String textScore = String.valueOf(currLink.getScore());
			// Here, we add one to the currLink sentence positions to have 1-based positions,
			// as the ULL format
			linksULL += String.join(" ", String.valueOf(currLink.getLi() + 1), wl,
					String.valueOf(currLink.getRi() + 1), wr, textScore);
			linksULL += "\n";
		}
		return linksULL;
	}

	// Prints the current sentence and its parse
	#public void printParse() {
		//System.out.println("Current parse:");
		//System.out.println(procSentence.toString());
		//System.out.println(links.toString());
	}
}
