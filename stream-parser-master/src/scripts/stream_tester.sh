#!/bin/bash

# Processes a corpus from a given grammar with stream-parser and evaluates the results:

# Usage stream_tester.sh <grammar_name> <maxWinObserve> <maxWinParse>

if [ $# -lt 3 ]
then
  echo "Usage: ./stream_tester.sh <grammar_name> <maxWinObserve> <maxWinParse>"
  exit 0
fi

# Parameters
gram_name=$1
maxWinObserve=$2
maxWinParse=$3

HOME="/home/alexcasar"
rangram_workdir="$HOME/Desktop/snet/gits/stream-parser-master/data/"
SPPath="$HOME/Desktop/snet/gits/stream-parser-master/src/scripts"

workdir_path=$rangram_workdir/$gram_name
vocab_filename=$gram_name.vocab

cd $workdir_path
mkdir -p stream-parser
cd stream-parser

# Create vocabulary file
$SPPath/dictionary_dir.sh ../corpus $vocab_filename

# Parse using SP and evaluate
/home/alexcasar/Desktop/snet/gits/stream-parser-master/src/scripts/dynsym_evaluate.sh "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/dynsym/dynsym.vocab" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/dynsym/corpus/" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/dynsym/corpus/" "/home/alexcasar/Desktop/snet/gits/stream-parser-master/data/dynsym/matrix.score" 3 3

# Append SP results to all_results file
printf "Stream-parser results:\n\n" >> ../all_results.txt
cat results.dat >> ../all_results.txt
printf "#################### \n\n" >> ../all_results.txt
