#!/bin/bash

# Adapted from github.com/sbos/AdaGram.jl utils
# Creates a dictionary file with word counts from a directory containing tokenized text files

# Usage: dictionary.sh directory dictionary_file

cat $1/* | tr ' ' '\n' | sort -S 10G | uniq -c | awk '{print $2" "$1}' > $2
