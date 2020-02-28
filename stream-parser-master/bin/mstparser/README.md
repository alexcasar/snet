# package: mstparser

Unlabeled dependency parser for streams of tokens

Java implementation of the Minimum Spanning Tree (MST) parser specified by Deniz Yuret in his PhD thesis: Yuret, Deniz.
"Discovery of linguistic relations using lexical attraction." arXiv preprint cmp-lg/9805009 (1998).

As opposed to the original Yuret algorithm to parse natural language sentences, this parser intends to handle
longer streams of tokens.
Hence, this parser implements a parsing window as a parameter, which limits the length of the separation between linked words.
This maintains the links local, and allows very long streams to be parsed efficiently.

This parser requires scores for possible links as input. Those scores have to be calculated elsewhere and provided as 
a database. For a way to calculate the scores, check the [micalculator](../micalculator) package.

The [RunParser](RunParser.java) class gives an example on how to run the parser, with its necessary pre-steps.