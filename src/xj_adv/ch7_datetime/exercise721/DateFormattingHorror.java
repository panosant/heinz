package xj_adv.ch7_datetime.exercise721;

import java.text.*;
import java.util.*;

public class DateFormattingHorror {
    private final DateFormat format =
            new SimpleDateFormat("dd.MM.yyyy");

    public Date parse(String textDate) {
        try {
            Date result = format.parse(textDate);
            Calendar calendar = new GregorianCalendar();
            int cutoffyear = calendar.get(Calendar.YEAR) % 100 + 5;
            // adjust in case someone uses 2-digit years
            calendar.setTime(result);

            if (calendar.get(Calendar.YEAR) < cutoffyear) {
                calendar.add(Calendar.YEAR, 2000);
            } else if (calendar.get(Calendar.YEAR) < 100) {
                calendar.add(Calendar.YEAR, 1900);
            }
            return calendar.getTime();
        } catch (ParseException e) {
            throw new IllegalStateException(e);
        }
    }
}
