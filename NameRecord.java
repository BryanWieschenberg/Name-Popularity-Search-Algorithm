/* 
 * Created by Bryan Wieschenberg
 * NameRecord.java records data about each recorded name from 1880-2021
 */

public class NameRecord {
    // Starting value of the years (1880-2021)
    public final int START = 1880;
    // Ending value of the years (1880-2021)
    public final int END = 2021;
    // Stores the name from the name file
    private String name;
    // Stores the popularity rank value for each year
    private int[] rank = new int[END-START+1];

    // Constructor takes a String name parameter. Initializes the NameRecord object
    public NameRecord(String name) {
        this.name = name;
    }
    
    // getName returns the name
    public String getName() {
        return name;
    }

    // setRank sets the popularity rank of the name in the given year
    public void setRank(int year, int rank) {
        this.rank[year] = rank;
    }

    // getRank returns the popularity rank of the name in the given year
    public int getRank(int year) {
        return rank[year];
    }

    // bestYear returns the actual year where the name was most popular, using the earliest year in the event of a tie
    public int bestYear() {
        int bestYear = 0;
        for (int i = 0; i <= END-START; i++) {
            if ((rank[i] < rank[bestYear] && rank[i] > 0) || (rank[bestYear] == 0 && rank[i] > 0)) bestYear = i;
        }
        return bestYear + START;
    }
}
