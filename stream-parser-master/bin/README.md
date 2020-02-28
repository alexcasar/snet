# src

This folder contains the code of the project, structured as follows:

+ `micalculator` is a package to estimate the pointwise mutual information (PMI) of word-pairs from observations 
of a given corpus.

+ The `mstparser` package is the parser itself, which consumes scores calculated beforehand.

+ The `yuretscores` package contains a simple example on how to integrate scores that are not coming from
micalculator (e.g. context-sensitive scores coming from a DNN).

+ The `scripts` folder contains various bash utilities to process corpora (e.g to lowercase the given corpus, as well as
 to generate a dictionary file).
 
