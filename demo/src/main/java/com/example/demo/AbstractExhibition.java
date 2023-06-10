package shchepinda.labfourth.task3;

/**
 * Basic abstract class that represents the first of the entities of an individual task<br>
 * Implements Comparable interface of class ExhibitionWithArray.
 * @author Shchepin Daniel
 */
public abstract class AbstractExhibition {
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setArtSurname(String artSurname) {
        this.artSurname = artSurname;
    }

    protected String artSurname;

    public abstract boolean addDay(int visitorsNum, String comment);
    public abstract boolean addDay(Day day);

    /**
     * Constructor of class AbstractExhibition
     * @param name name of exhibition
     * @param artSurname surname of artist
     */
    public AbstractExhibition(String name, String artSurname) {
        this.name = name;
        this.artSurname = artSurname;
    }

    public AbstractExhibition() {}

    /**
     * Function to create exhibition for test
     * @return AbstractExhibition
     */
    public AbstractExhibition createExhibition() {
        setName("Transcendence");
        setArtSurname("Alexander Lee");
        System.out.println(addDay( 115, "The exhibition was a feast for the senses, with vibrant colors and stunning compositions."));
        System.out.println(addDay( 189, "The artist's work was thought-provoking and challenged me to consider new perspectives."));
        System.out.println(addDay( 167, "The exhibition was well-curated and provided an excellent showcase for the artist's work."));
        System.out.println(addDay( 94, "\"The exhibition was thoughtfully presented."));
        return this;
    }

    public abstract String getName();
    public abstract Day[] getDays();
    public abstract Day getDay(int day);
    public abstract void sortByVisitors();
    public abstract void sortByComments();
    public abstract void setAllDays(Day[] days);
    public abstract int getDaysCount();
    public abstract void test();

    /**
     * Function that finds the total number of visitors to the exhibition
     * @param days object Day array
     * @return total number of visitors
     */
    public int allVisitors(Day[] days){
        int sum = 0;
        for(int i = 0; i < days.length; i++){
            sum += days[i].getVisitorsNum();
        }
        return sum;
    }

    /**
     * Function that finds the total number of visitors to the exhibition
     * @return total number of visitors
     */
    public int allVisitors(){
        int sum = 0;
        Day days = getDays()[0];
        for(int i = 0; i < getDays().length; i++){
            sum += getDays()[i].getVisitorsNum();
        }
        return sum;
    }

    /**
     * The function that finds the day with the least number of visitors
     * @param days object Day array
     * @return day with lowest num of visitors
     */
    public int findLowestVisitors(Day[] days){
        if (days == null && days.length == 0) return -1;

        int lowestDay = 1;
        int low = days[0].getVisitorsNum();
        for(int i = 1; i < days.length; i++){
            if(days[i].getVisitorsNum() < low){
                low = days[i].getVisitorsNum();
                lowestDay = i + 1;
            }
        }
        return lowestDay;
    }
    /**
     * The function that finds the day with the least number of visitors
     * @return day with lowest num of visitors
     */
    public int findLowestVisitors(){
        Day days = getDays()[0];
        if (days == null && getDays().length == 0) return -1;

        int lowestDay = 1;
        int low = getDays()[0].getVisitorsNum();
        for(int i = 1; i < getDays().length; i++){
            if(getDays()[i].getVisitorsNum() < low){
                low = getDays()[i].getVisitorsNum();
                lowestDay = i + 1;
            }
        }
        return lowestDay;
    }

    /**
     * A function that displays a list of comments with a specific word in the console
     * @param word word to find
     */
    public void findWord(String word) {
        Day[] days = getDays();
        for (Day day : days) {
            if (day.containsWord(word)) {
                System.out.println(day.getComment());
            }
        }
    }

    /**
     * Sorts the array of days objects by increasing number of visitors<br>
     * By bubble sorting
     * @param days object Day array
     * @return sorted array of objects (or null if the search did not return results)
     */
    public Day[] sortByVisitors(Day[] days) {
        if (days == null && days.length == 0) return null;

        Day[] sorted = days;
        boolean mustSort;
        do {
            mustSort = false;
            for (int i = 0; i < sorted.length - 1; i++) {
                if (sorted[i].getVisitorsNum() > sorted[i + 1].getVisitorsNum()) {
                    Day temp = sorted[i];
                    sorted[i] = sorted[i + 1];
                    sorted[i + 1] = temp;
                    mustSort = true;
                }
            }
        }
        while (mustSort);
        return sorted;
    }

    /**
     * Sorts an array of days objects by comment alphabetically<br>
     * By inclusion sorting method
     * @param days object Day array
     * @return sorted array of objects (or null if the search did not return results)
     */
    public Day[] sortByComment(Day [] days) {
        if (days == null && days.length == 0) return null;

        Day[] sorted = days;
        for (int i = 1; i < sorted.length; i++) {
            int j = i - 1;
            Day temp = sorted[i];
            while (j >= 0 && temp.getComment().compareTo(sorted[j].getComment()) <= 0) {
                sorted[j + 1] = sorted[j];
                sorted[j] = temp;
                --j;
            }
        }
        return sorted;
    }

    /**
     * Prints information about exhibition and unsorted array days
     * @param days object Day array
     */
    public void printExhibition(Day[] days){
        System.out.println(this);
        System.out.println("Days:");
        if (days != null){
            printDays(days);
        }
    }

    /**
     * Prints all information of the object days to the console
     * @param days object Day array
     */
    public void printDays(Day[] days) {
        for (int i = 0; i < days.length; i++) {
            System.out.println("Number of visitors: " + days[i].getVisitorsNum() + "\t"
                    + '\"' + days[i].getComment() + '\"');
        }
    }

    /**
     * Overrode function toString()
     * @return Converts objects parameters into string format sentences and returns it.
     */
    @Override
    public String toString() {
        return "Name of exhibition = \"" + name + '\"' +
                ", Artist surname = \"" + artSurname + '\"' + '.';
    }

    /**
     * Function to access the artist's surname
     * @return artist's surname
     */
    public String getArtSurname() {
        return artSurname;
    }
}
