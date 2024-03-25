import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class S40195862_detector{

    public static ArrayList<String> stopwordsdict = new ArrayList<>();

    public static void stopWords()
    {
        String [] stopWords ={"a","about","above","after","against","again","all","am","an","and","any","are","arent","as","at","be","because","been","before","being","below","between","both","but","by","cant","cannot","could","couldnt","did","didnt","do","does","doesnt","doing","dont","down","during","each","few","for","from","further","had","hadnt","has","hasnt","have","havent","having","he","hed","hell","hes","her","here","heres","hers","herself","him","himself","his","how","hows","i","id","ill","im","ive","if","in","into","is","isnt","it","its","its","itself","lets","me","more","most","mustnt","my","myself","no","nor","not","of","off","on","once","only","or","other","ought","our","ours	ourselves","out","over","own","same","shant","she","shed","shell","shes","should","shouldnt","so","some","such","than","that","thats","the","their","theirs","them","themselves","then","there","theres","these","they","theyd","theyll","theyre","theyve","this","those","through","to","too","under","until","up","very","was","wasnt","we","we'd","well","were","weve","were","werent","what","whats","when","whens","where","wheres","which","while","who","whos","whom","why","whys","with","wont","would","wouldnt","you","youd","youll","youre","youve","your","yours","yourself","yourselves"};

        for(int i=0; i<stopWords.length; i++)
            stopwordsdict.add(stopWords[i]);
    }

    private static ArrayList<String> ReadFile(String filePath) {
        try {
            File fileHandle = new File(filePath);
            Scanner fileScanner = new Scanner(fileHandle);

            ArrayList<String> wordBag = new ArrayList<>();
            while (fileScanner.hasNext()) {
                String word = fileScanner.next().toLowerCase().replaceAll("\\W", "");
                wordBag.add(word);
                if (word.isEmpty())
                    wordBag.remove(word);
            }
            fileScanner.close();

            return wordBag;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> removeStopWords(ArrayList<String> wordBag)
    {
        ArrayList<String> temp = new ArrayList<>(wordBag);
        for(String word : wordBag) {
            if (stopwordsdict.contains(word))
                temp.remove(word);
        }
        return temp;
    }

    public static int OptimizedLCS( String wordBag1[], String wordBag2[])
    {
        int m = wordBag1.length;
        int n = wordBag2.length;
        int dp[][] = new int[2][n+1];
        int currentIndex=0;
        for (int i = 0; i <= m; i++)
        {
            currentIndex = i & 1;
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                    dp[currentIndex][j] = 0;

                else {
                    if ((wordBag1[i - 1]).equals(wordBag2[j - 1]))
                        dp[currentIndex][j] = dp[1 - currentIndex][j - 1] + 1;
                    else {
                        if(dp[1 - currentIndex][j] > dp[currentIndex][j - 1])
                            dp[currentIndex][j] = dp[1 - currentIndex][j];
                        else
                            dp[currentIndex][j] = dp[currentIndex][j - 1];
                    }

                }
            }
        }
        return dp[currentIndex][n];
    }

    public static ArrayList<String> UniqueWords(ArrayList<String> wordBag)
    {
        ArrayList<String> UniqueWordsBag = new ArrayList<>();
        for (String word:wordBag)
        {
            if(UniqueWordsBag.contains(word) == false)
                UniqueWordsBag.add(word);
        }
        return UniqueWordsBag;
    }

    public static ArrayList<String> Stemming(ArrayList<String> wordBag) {
        for(int i=0; i<wordBag.size(); i++)
        {
            String word = wordBag.get(i).replaceAll("s$", "")
                    .replaceAll("ly$","")
                    .replaceAll("ic$","")
                    .replaceAll("ess$","")
                    .replaceAll("ler?$","")
                    .replaceAll("ing$","")
                    .replaceAll("ism$","");

            wordBag.set(i, word);
        }
        return wordBag;
    }

    public static void main(String[] args) {
        stopWords();

        String f1path = args[0];
        String f2path = args[1];

        ArrayList<String> WordBagFile1 = ReadFile(f1path);
        ArrayList<String> WordBagFile2 = ReadFile(f2path);

        WordBagFile1 = removeStopWords(WordBagFile1);
        WordBagFile2 = removeStopWords(WordBagFile2);

        WordBagFile1 = Stemming(WordBagFile1);
        WordBagFile2 = Stemming(WordBagFile2);

        ArrayList<String> uniqueWordsBag1 = UniqueWords(WordBagFile1);
        ArrayList<String> uniqueWordsBag2 = UniqueWords(WordBagFile2);

        Collections.sort(uniqueWordsBag1);
        Collections.sort(uniqueWordsBag2);

        double lcsLength = OptimizedLCS(uniqueWordsBag1.toArray(new String[0]), uniqueWordsBag2.toArray(new String[0]));

        int minlenFile = Math.min(uniqueWordsBag1.size(), uniqueWordsBag2.size());

        double similarityRatio = lcsLength/minlenFile;

        if(Double.compare(similarityRatio,0.60) >= 0)
            System.out.println("1");
        else
            System.out.println("0");
    }
}
