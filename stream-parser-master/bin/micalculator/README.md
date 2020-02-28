# package: micalculator

Provides a matrix of e^PMI values, based on indexes of the given vocabulary.

PMI is as defined in [Pointwise Mutual Information](https://en.wikipedia.org/wiki/Pointwise_mutual_information):

PMI = log[N(x,y) * N(*,*) / N(x,*) / N(*,y)]

The word-pair PMI values are calculated based on co-ocurrence of words within a given observation window.

The PMI values can be calculated by observing a given corpus, then stored to file.
They can also be loaded from file, to avoid recalculating when more sentences in the same
corpus/language need to be parsed.
The matrix is saved in text format, using the indexes of the provided vocabulary.


