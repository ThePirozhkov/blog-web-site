package by.baby.blogwebsite.util;

import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@UtilityClass
public class HeaderUtil {

    public static String getExpiresHeader(int minutes) {
        final long currentTime = System.currentTimeMillis();
        final long expiresTime = currentTime + (long) minutes * 60 * 1000;

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        return dateFormat.format(new Date(expiresTime));
    }

    public static String getMaxAgeHeader(int minutes) {
        return "max-age: " + minutes * 60;
    }

}
