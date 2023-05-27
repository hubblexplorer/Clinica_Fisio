package com.example.test_login2.controller;

import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class AgendaWeek {
    public Date LastWeekDay(){
        LocalDate data = LocalDate.now();
        LocalDate ultimoDiaSemana = data.with(DayOfWeek.SUNDAY);
        LocalDateTime localDateTime = ultimoDiaSemana.atStartOfDay();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        Date LastDay = Date.from(instant);
        return LastDay;
    }
    public Date LastWeekDay(int outset) {
        LocalDate data = LocalDate.now();
        LocalDate ultimoDiaSemana = data.with(DayOfWeek.SUNDAY);

        if (outset < 0) {
            ultimoDiaSemana = ultimoDiaSemana.minusWeeks(Math.abs(outset));
        } else if (outset > 0) {
            ultimoDiaSemana = ultimoDiaSemana.plusWeeks(outset);
        }

        LocalDateTime localDateTime = ultimoDiaSemana.atTime(LocalTime.MAX);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        Date lastDay = Date.from(instant);
        return lastDay;
    }


    public Date FirstWeekDay(){
        LocalDate data = LocalDate.now();
        LocalDate primeiroDiaSemana = data.with(DayOfWeek.MONDAY);
        LocalDateTime localDateTime = primeiroDiaSemana.atStartOfDay();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        Date FristDay = Date.from(instant);
        return FristDay;
    }

    public Date FirstWeekDay(int outset) {
        LocalDate data = LocalDate.now();
        LocalDate primeiroDiaSemana = data.with(DayOfWeek.MONDAY);

        if (outset < 0) {
            primeiroDiaSemana = primeiroDiaSemana.minusWeeks(Math.abs(outset));
        } else if (outset > 0) {
            primeiroDiaSemana = primeiroDiaSemana.plusWeeks(outset);
        }

        LocalDateTime localDateTime = primeiroDiaSemana.atStartOfDay();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        Date firstDay = Date.from(instant);
        return firstDay;
    }

    public Date DayNow(){
        LocalDate data = LocalDate.now();
        LocalDateTime localDateTime = data.atStartOfDay();
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    public String DayWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY -> "2";
            case Calendar.TUESDAY -> "3";
            case Calendar.WEDNESDAY -> "4";
            case Calendar.THURSDAY -> "5";
            case Calendar.FRIDAY -> "6";
            case Calendar.SATURDAY -> "Saturday";
            case Calendar.SUNDAY -> "Sunday";
            default -> "NULL";
        };
    }
}
