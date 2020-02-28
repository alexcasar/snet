#!/bin/bash

# Runs stream parser on given corpus, and evaluates resulting parses against given GS using SingNet's parse-evaluator
# at github.com/singnet/language-learning
#
# NOTE: language-learning conda environment needs to be active before running this script
#
# Usage:  stream_evaluate.sh <vocabFilePath> <observeCorpusPath> <corpusPath> <GSPath> <maxWinObserve> <maxWinParse>

#/home/alexcasar/Desktop/snet/gits/stream-parser-master/src/scripts/stream_evaluate.sh "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/sample.vocab" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/corpus/sample_corpus1.txt" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/corpus/sample_corpus1.txt" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/parses/sample_corpus1.txt.ull" 5 5

#java -classpath "~/Desktop/snet/gits/stream-parser-master/src/jar_files/ojalgo-48.0.0.jar" ~/Desktop/snet/gits/stream-parser-master/src/mstparser/RunParser 5 5 true true "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/sample.vocab" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/matrix.score" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/corpus/sample_corpus1.txt" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/corpus/sample_corpus1.txt" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/sample/parses/sample_corpus1.txt.ull"

# Evaluate current parses against the gold standard
#parse-evaluator -i -r "$GSPath" -t "$parsesPath"

STREAMPATH='~/Desktop/snet/gits/stream-parser-master';
vocabFilePath=$1;
observeCorpusPath=$2;
corpusPath=$3;
GSPath=$4;
maxWinObserve=$5;
maxWinParse=$6;

echo "# Parse evaluations" > results.dat # write header
echo "# winObserve winParse Recall Precision F1" > results.dat # write header

mkdir -p parses
mkdir -p stats
mkdir -p scores

bestF1=0
bestWinObserve=0
bestWinParse=0

#source activate ull # conda environment needed for parse-evaluator

# Loop to parse and evaluate with range of windows
for ((winObserve=1; winObserve<=maxWinObserve; winObserve++))
do
  calculateScores=true; # in first pass for each winObserve, store scores
  exportScores=true;
  scoresFilePath="scores/${winObserve}_1.fmi"; # Path to store/import scores
  for ((winParse=1; winParse<=maxWinParse; winParse++))
  do
    echo "Evaluating: winObserve=${winObserve} winParse=${winParse}"
    currParses="wObs${winObserve}_wPar${winParse}";
    parsesPath="parses/${currParses}";

    # Parse the corpus with current window sizes
    echo $STREAMPATH ~/Desktop/snet/gits/stream-parser-master/src/mstparser/RunParser.java "$winObserve" "$winParse" $calculateScores $exportScores "$vocabFilePath" "$scoresFilePath" "$observeCorpusPath" "$corpusPath" "$parsesPath"
    javac -cp "~/Desktop/snet/gits/stream-parser-master/src/jar_files/ojalgo-48.0.0.jar" "~/Desktop/snet/gits/stream-parser-master/src/mstparser/RunParser.java" "$winObserve" "$winParse" $calculateScores $exportScores "$vocabFilePath" "$scoresFilePath" "$observeCorpusPath" "$corpusPath" "$parsesPath"

    # Evaluate current parses against the gold standard
    parse-evaluator -i -r "$GSPath" -t "$parsesPath"

    # Obtain scores from evaluation
    scores=() # scores array
    grep -E 'Recall|Precision|F1' < "${currParses}.stat"  > tmp.file # tmp.file used to avoid subshell from piping
    while read -r f1 f2
    do
      scores+=("$f2")
    done < tmp.file
    mv ${currParses}.stat stats
    rm tmp.file

    # Keep track of best score
    if (( $(echo "$bestF1 < ${scores[2]}" |bc -l) ));
    then
	    bestF1=${scores[2]};
	    bestWinObserve=$winObserve;
	    bestWinParse=$winParse;
    fi

    # Store results in file
    echo "$winObserve" "$winParse" "${scores[@]}" >> results.dat

    calculateScores=false; # after first pass, switch to false and import stored scores
    exportScores=false;
  done
  echo "" >> results.dat # newline, useful for splot in gnuplot
done

echo "Best F1-score was: ${bestF1}, with params winObserve: ${bestWinObserve}; winParse: ${bestWinParse}"

# Plotting results with gnuplot
mkdir -p plots
gnuplot -persist << -EOFMarker
  reset
  set terminal png

  # Plotting contour map
  set pm3d
  unset surface
  set view map
  set key outside

  # Preparing three different plots
  set cbrange [0.3:0.9]
  set xlabel "winObserve"
  set ylabel "winParse"
  set title "Recall"
  set output "plots/recall.png"
  splot 'results.dat' using 1:2:3 with lines notitle
  set title "Precision"
  set output "plots/precision.png"
  splot 'results.dat' using 1:2:4 with lines notitle
  set title "Stream parser F1 scores"
  set output "plots/f1score.png"
  splot 'results.dat' using 1:2:5 with lines notitle
-EOFMarker
echo "Results plots stored in 'plots' folder"

eog plots/f1score.png &
