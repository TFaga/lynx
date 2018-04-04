package com.github.tfaga.lynx.helper;

import com.github.tfaga.lynx.exceptions.ConversionException;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Tilen Faganel
 * @since 1.2.0
 */
public class ConversionHelper {

    private static final Class PBOOLEAN = boolean.class;
    private static final Class PCHAR = char.class;
    private static final Class APCHAR = char[].class;

    private static final Class OBJECT = Object.class;
    private static final Class BOOLEAN = Boolean.class;
    private static final Class CHAR = Character.class;
    private static final Class ACHAR = Character[].class;
    private static final Class STRING = String.class;

    private static final Class UTIL_DATE = java.util.Date.class;
    private static final Class CALENDAR = java.util.Calendar.class;
    private static final Class GREGORIAN_CALENDAR = java.util.GregorianCalendar.class;
    private static final Class SQL_DATE = java.sql.Date.class;
    private static final Class TIME = java.sql.Time.class;
    private static final Class TIMESTAMP = java.sql.Timestamp.class;

    private static final Class LOCAL_DATE = java.time.LocalDate.class;
    private static final Class LOCAL_TIME = java.time.LocalTime.class;
    private static final Class LOCAL_DATETIME = java.time.LocalDateTime.class;
    private static final Class OFFSET_TIME = java.time.OffsetTime.class;
    private static final Class OFFSET_DATETIME = java.time.OffsetDateTime.class;
    private static final Class ZONED_DATETIME = java.time.ZonedDateTime.class;
    private static final Class INSTANT = java.time.Instant.class;
    private static final Class DURATION = java.time.Duration.class;

    private static final Class UUID = java.util.UUID.class;

    public static Object toTargetObject(Class javaClass, String sourceObject) throws ConversionException {

        if (sourceObject == null) return null;

        if (sourceObject.getClass() == javaClass || javaClass == null || javaClass == STRING || javaClass == OBJECT) {

            return sourceObject;
        }

        try {
            if (javaClass == BOOLEAN || javaClass == PBOOLEAN) {
                return toBoolean(sourceObject);
            } else if (javaClass == CHAR || javaClass == PCHAR) {
                return toCharacter(sourceObject);
            } else if (javaClass == APCHAR) {
                return toCharArray(sourceObject);
            } else if (javaClass == ACHAR) {
                return toCharacterArray(sourceObject);
            } else if (javaClass == UTIL_DATE) {
                return toUtilDate(sourceObject);
            } else if (javaClass == CALENDAR || javaClass == GREGORIAN_CALENDAR) {
                return toCalendar(sourceObject);
            } else if (javaClass == SQL_DATE) {
                return toSqlDate(sourceObject);
            } else if (javaClass == TIME) {
                return toTime(sourceObject);
            } else if (javaClass == TIMESTAMP) {
                return toTimestamp(sourceObject);
            } else if (javaClass == LOCAL_DATE) {
                return toLocalDate(sourceObject);
            } else if (javaClass == LOCAL_TIME) {
                return toLocalTime(sourceObject);
            } else if (javaClass == LOCAL_DATETIME) {
                return toLocalDateTime(sourceObject);
            } else if (javaClass == OFFSET_TIME) {
                return toOffsetTime(sourceObject);
            } else if (javaClass == OFFSET_DATETIME) {
                return toOffsetDateTime(sourceObject);
            } else if (javaClass == ZONED_DATETIME) {
                return toZonedDateTime(sourceObject);
            } else if (javaClass == INSTANT) {
                return toInstant(sourceObject);
            } else if (javaClass == DURATION) {
                return toDuration(sourceObject);
            } else if (javaClass == UUID) {
                return toUuid(sourceObject);
            } else if (javaClass.isEnum()) {
                return Enum.valueOf(javaClass, sourceObject);
            } else {
                return sourceObject;
            }
        } catch (IllegalArgumentException | DateTimeParseException e) {

            throw new ConversionException(e);
        }
    }

    //// Private methods

    private static Boolean toBoolean(String sourceObject) {

        return Boolean.parseBoolean(sourceObject);
    }

    private static Character toCharacter(String sourceObject) {

        return sourceObject.length() < 1 ? Character.MIN_VALUE : sourceObject.charAt(0);
    }

    private static char[] toCharArray(String sourceObject) {

        return sourceObject.toCharArray();
    }

    private static Character[] toCharacterArray(String sourceObject) {

        return sourceObject.chars().mapToObj(s -> (char) s).toArray(Character[]::new);
    }

    private static Date toUtilDate(String sourceObject) {

        return Date.from(Instant.parse(sourceObject));
    }

    private static Calendar toCalendar(String sourceObject) {

        return GregorianCalendar.from(ZonedDateTime.parse(sourceObject));
    }

    private static java.sql.Date toSqlDate(String sourceObject) {

        return java.sql.Date.valueOf(LocalDate.parse(sourceObject));
    }

    private static Time toTime(String sourceObject) {

        return Time.valueOf(LocalTime.parse(sourceObject));
    }

    private static Timestamp toTimestamp(String sourceObject) {

        return Timestamp.from(Instant.parse(sourceObject));
    }

    private static LocalDate toLocalDate(String sourceObject) {

        return LocalDate.parse(sourceObject);
    }

    private static LocalTime toLocalTime(String sourceObject) {

        return LocalTime.parse(sourceObject);
    }

    private static LocalDateTime toLocalDateTime(String sourceObject) {

        return LocalDateTime.parse(sourceObject);
    }

    private static OffsetTime toOffsetTime(String sourceObject) {

        return OffsetTime.parse(sourceObject);
    }

    private static OffsetDateTime toOffsetDateTime(String sourceObject) {

        return OffsetDateTime.parse(sourceObject);
    }

    private static ZonedDateTime toZonedDateTime(String sourceObject) {

        return ZonedDateTime.parse(sourceObject);
    }

    private static Instant toInstant(String sourceObject) {

        return Instant.parse(sourceObject);
    }

    private static Duration toDuration(String sourceObject) {

        return Duration.parse(sourceObject);
    }

    private static java.util.UUID toUuid(String sourceObject) {

        return java.util.UUID.fromString(sourceObject);
    }
}
