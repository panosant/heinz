package xj_adv.ch5_datastructs.solution541;

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

    public int compareTo(Programmer p) {
        return name.compareTo(p.name);
    }

    public String toString() {
        return name +
                ", salary=" + salary +
                ", languages=" + languages;
    }

    public static final Comparator<Programmer> RICH_SMART_ORDER =
            Comparator.comparingDouble(Programmer::getSalary)
                    .thenComparingInt(p -> p.getLanguages().size()).reversed()
                    .thenComparing(Programmer::getName);
    public static final Comparator<Programmer> SMART_RICH_ORDER =
            Comparator.<Programmer>comparingInt(p -> p.getLanguages().size())
                    .thenComparingDouble(Programmer::getSalary).reversed()
                    .thenComparing(Programmer::getName);
}
