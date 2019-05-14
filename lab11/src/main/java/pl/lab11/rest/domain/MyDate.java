package pl.lab11.rest.domain;

import java.sql.Date;

public class MyDate {

    public Long getCurrentTime() {
        Date date = new Date(0);
        Long timestamp = date.getTime();
        return timestamp;
    }
}
