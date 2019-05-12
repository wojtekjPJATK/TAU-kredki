package pl.tau.lab9wj.domain;

import java.sql.Date;

public class MyDate {

    public Long getCurrentTime() {
        Date date = new Date(0);
        Long timestamp = date.getTime();
        return timestamp;
    }
}
