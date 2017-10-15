package xj_adv.ch7_datetime.exercise721;

import java.text.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class DateFormattingHorror {
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("[d.M.yyyy][d.M.yy]");
//          DateFormat format =   new SimpleDateFormat("dd.MM.yyyy");

    public LocalDate parse(String textDate) {
        return LocalDate.parse(textDate, format);
    }
}
