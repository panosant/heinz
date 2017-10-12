package xj_adv.ch5_datastructs.exercise541;

import java.util.*;

public class Programmer implements Comparable<Programmer> {
    private final String name;
    private final double salary;
    private final Collection<String> languages =
            new ArrayList<>();

    public Programmer(String name, double salary,
                      String... languages) {
        this.name = name;
        this.salary = salary;
        Collections.addAll(this.languages, languages);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public Collection<String> getLanguages() {
        return Collections.unmodifiableCollection(languages);
    }

    public String toString() {
        return name +
                ", salary=" + salary +
                ", languages=" + languages;
    }

    public int compareTo(Programmer p) {
        return name.compareTo(p.name);
    }

    // TODO: sort by biggest salary, then largest number of languages, then name
    public static final Comparator<Programmer> RICH_SMART_ORDER =
            Comparator.naturalOrder();
    // TODO: sort by largest number of languages, then biggest salary, then name
    public static final Comparator<Programmer> SMART_RICH_ORDER =
            Comparator.naturalOrder();
}
