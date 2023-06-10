package shchepinda.labfourth.task3;

import java.util.*;

/**
 * Class that represents the first of the entities of an individual task <br>
 * And implemented by using ArrayList <br>
 * Extends AbstractExhibition
 * @author Shchepin Daniel
 */
public class ExhibitionWithList extends AbstractExhibition {
    private List<Day> list = new ArrayList<>();

    /**
     * Constructor for ExhibitionWithList
     * @param name - name of exhibition
     * @param artSurname - surname of artist
     * @param list - list of days
     */
    public ExhibitionWithList(String name, String artSurname, List<Day> list) {
        super(name, artSurname);
        this.list = list;
    }

    protected List<Day> getList() {
        return list;
    }

    protected void setList(List<Day> list) {
        this.list = list;
    }

    public ExhibitionWithList() {}

    /**
     * Overrode function to sort days by visitors
     */
    @Override
    public void sortByVisitors() {
        list.sort(Comparator.comparing(Day::getVisitorsNum));
    }
    /**
     * Overrode function to sort days by comments
     */
    @Override
    public void sortByComments() {
        Collections.sort(list);
    }

    /**
     * Overrode function to get name of exhibition
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Overrode function to get array of Days
     * @return array of Days
     */
    @Override
    public Day[] getDays() {
        Day[] days = new Day[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        return days;
    }

    /**
     * Overrode function to get specific Day from list
     * @param day - number of day, which you want to get
     * @return Day
     */
    @Override
    public Day getDay(int day) {
        return list.get(day);
    }

    /**
     * Overrode function to set all list of Days
     * @param days - array which you want to set
     */
    @Override
    public void setAllDays(Day[] days) {
        list = new ArrayList<>(Arrays.asList(days));
    }

    /**
     * Overrode function to get size of Day list
     * @return size of Day list
     */
    @Override
    public int getDaysCount() {
        return list.size();
    }

    /**
     * Add a new Day to end of list
     * @param day - object of the Day class that you want to add to the list
     * @return true, if the reference was added successfully<br>
     * false otherwise
     */
    @Override
    public boolean addDay(Day day) {
        if (list.contains(day)) return false;
        return list.add(day);
    }

    /**
     * Add a new Day to end of list
     * @param visitorsNum - number of visitors
     * @param comment - comment to exhibition
     * @return true, if the reference was added successfully<br>
     * false otherwise
     */
    @Override
    public boolean addDay(int visitorsNum, String comment) {
        return addDay(new Day(visitorsNum, comment));
    }
    @Override
    public void test() {}

    public static void main(String[] args) {
        System.out.println("===With List===");
        List<Day> list = new ArrayList<Day>();
        list.add(new Day(154, "People liked most of the pictures"));
        list.add(new Day(201, "Some visitors were outraged by several paintings"));
        list.add(new Day(91, "The least crowded day"));
        list.add(new Day(111, "Typical day"));
        ExhibitionWithList Salvador = new ExhibitionWithList("Exhibition of the great spanish artists.",
                "Salvador Dali", list);

        Salvador.printExhibition(Salvador.getDays());
        System.out.println("\nAll visitors number: " + Salvador.allVisitors(Salvador.getDays()));
        System.out.println("Day with the least number of visitors: " + Salvador.findLowestVisitors(Salvador.getDays()));
        System.out.println("List of comments with a word <" + "day" + ">:");
        Salvador.findWord("day");
        System.out.println("\nSorted days by visitors number: ");
        Salvador.sortByVisitors();
        Salvador.printDays(Salvador.getDays());
        System.out.println("\nSorted comments by alphabet: ");
        Salvador.sortByComments();
        Salvador.printDays(Salvador.getDays());
    }
}

