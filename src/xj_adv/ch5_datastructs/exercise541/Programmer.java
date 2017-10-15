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

    public int getNumberOfLanguages() {
        return getLanguages().size();
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
    public static final Comparator<Programmer> RICH_SMART_ORDER = (programmer1, programmer2) -> {
        int comparison;
        int salaryComparison = Double.valueOf(programmer1.getSalary()).compareTo(programmer2.getSalary());
        comparison = salaryComparison;
        if (comparison == 0 ) {
            int languageComparison = Integer.valueOf(programmer1.getLanguages().size()).compareTo(programmer2.getLanguages().size());

            comparison = languageComparison;
            if (comparison == 0 ) {
                comparison = programmer1.getName().compareTo(programmer2.getName());
            }
        }
        return comparison;
    };

    // TODO: sort by largest number of languages, then biggest salary, then name
    // Comparator.naturalOrder();
    public static final Comparator<Programmer> SMART_RICH_ORDER = Comparator
                    .<Programmer>comparingInt(p -> p.getLanguages().size())
                    .thenComparingDouble(Programmer::getSalary)
                    .reversed()
                    .thenComparing(Programmer::getName);
}
