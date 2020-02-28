# stream-parser
## Unlabeled dependency parser for streams of tokens

Java implementation of the Maximum Spanning Tree (MST) parser specified by Deniz Yuret in his PhD thesis:
Yuret, Deniz. "Discovery of linguistic relations using lexical attraction." arXiv preprint cmp-lg/9805009 (1998).

Code available in the `src` folder:

+ `micalculator` is a package to estimate the pointwise mutual information (PMI) of word-pairs from observations 
of a given corpus.

+ The `mstparser` package is the parser itself, which consumes scores calculated beforehand.

+ The `yuretscores` package contains a simple example on how to integrate scores that are not coming from
micalculator (e.g. context-sensitive scores coming from a DNN).

+ The `scripts` folder contains various bash utilities to process corpora (e.g to lowercase the given corpus, as well as
 to generate a dictionary file).
 
The `data` folder contains sample data to try directly with the `stream-parser`. 
It can be used as a working directory for your project.

## TODO

- Parallelize MI calculation
- Implement distance weights when observing
- Implement distance weights when parsing?
