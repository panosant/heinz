package xj_adv.ch7_datetime.solution721;

import java.time.*;
import java.time.format.*;

public class DateFormattingHorror {
    private final static DateTimeFormatter date =
            DateTimeFormatter.ofPattern("[d.M.yyyy][d.M.yy]");

    public LocalDate parse(String textDate) {
        return LocalDate.parse(textDate, date);
    }
}
