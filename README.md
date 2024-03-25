# Plagiarism_Detection
The description of the designed plagiarism detection tool:

Input: Two text files to check for plagiarism

Steps in the Algorithm:

Contents of the two input files are read word by word by removing non-word characters in the current word using
a regex, and these are added into two bags of words only if they are not empty strings, using File class and Scanner class.

Preprocessing of these word bags are done as follows:
The bags of words are then passed to a function where all the stop words are removed from them.
All words in these two bags are converted to their root word form, by the process of stemming.
These two word bags are now updated to contain only unique words in them, i.e duplicate entries are removed.
Now both the word bags containing unique words of the input files are sorted.

Longest Common Subsequence algorithm is performed on these word bags containing sorted unique words. A version of LCS
that optimizes space used by the problem, is implemented. At any point during the algorithm we only need values from columns
of the above/previous row, so this optimization is done. This reduces the space complexity from O(m*n) of storing the entire
matrix of order m*n, where m and n are sizes of the two word bags, to space complexity O(n), using the above optimized version
of LCS. This space optimization ensures that Java heap space error is not encountered.

The length of the Longest Common Subsequence returned from this algorithm, gives the number of common words between the two
files. Now we obtain the ratio of similarity between these two files by diving the lcs length by size of the shortest preprocessed
word bag.

We are taking the threshold value of this ratio to be 0.60 for classifying the input files. We compare this ratio with
the value 0.60. If the ratio is greater than or equal to the threshold, the input files are considered plagiarised and
the program returns 1. If the ratio is lesser than this threshold input files are considered not to be plagiarised and
the program returns 0.