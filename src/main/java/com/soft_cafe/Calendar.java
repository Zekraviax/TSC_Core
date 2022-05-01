package com.soft_cafe;

public class Calendar {
    // Track the World Age
    // 1 Tick = 1 Minecraft Second = 50 Real-Life Milliseconds
    // 20 Ticks = 1 Minecraft Minute = 1 Real-Life Second
    // 1,000 Ticks = 1 Minecraft Hour
    // 24,000 Ticks = 1 Minecraft Day
    private int hour = 6;
    private int minute = 1;
    private String meridian = "AM";

    private int year = 1;
    private String month = "January";
    private int day = 1;
    private String dayAsString = "Monday";

    //
    private int displayDay = 1;
    private int displayMonth = 1;
    private int displayYear = 1;


    private long adjustedTime = 0;


    // Getters and setters
    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() { return minute; }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayAsString() {
        return dayAsString;
    }

    public void setDayAsString(String dayAsString) {
        this.dayAsString = dayAsString;
    }

    public int getDisplayDay() { return displayDay; }

    public void setDisplayDay(int displayDay) {
        this.displayDay = displayDay;
    }

    public int getDisplayMonth() {
        return displayMonth;
    }

    public void setDisplayMonth(int displayMonth) {
        this.displayMonth = displayMonth;
    }

    public int getDisplayYear() {
        return displayYear;
    }

    public void setDisplayYear(int displayYear) {
        this.displayYear = displayYear;
    }


    public void newHour() {
        if (hour >= 12 && hour < 24) {
            meridian = "PM";
        } else {
            meridian = "AM";
        }
    }


    public void newDay() {
        displayDay++;

        switch(dayAsString) {
            case("Monday"):
                dayAsString = "Tuesday";
                break;
            case("Tuesday"):
                dayAsString = "Wednesday";
                break;
            case("Wednesday"):
                dayAsString = "Thursday";
                break;
            case("Thursday"):
                dayAsString = "Friday";
                break;
            case("Friday"):
                dayAsString = "Saturday";
                break;
            case("Saturday"):
                dayAsString = "Sunday";
                break;
            case("Sunday"):
                dayAsString = "Monday";
                break;
        }
    }


    public void newMonth() {
        switch (month) {
            case ("January"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "February";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("February"):
                if (displayDay > 28) {
                    displayDay = 1;
                    displayMonth++;
                    month = "March";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("March"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "April";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("April"):
                if (displayDay > 30) {
                    displayDay = 1;
                    displayMonth++;
                    month = "May";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("May"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "June";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("June"):
                if (displayDay > 30) {
                    displayDay = 1;
                    displayMonth++;
                    month = "July";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("July"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "August";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("August"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "September";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("September"):
                if (displayDay > 30) {
                    displayDay = 1;
                    displayMonth++;
                    month = "October";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("October"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "November";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("November"):
                if (displayDay > 30) {
                    displayDay = 1;
                    displayMonth++;
                    month = "December";
                    Atmosphere.newMonthEvent();
                }
                break;
            case ("December"):
                if (displayDay > 31) {
                    displayDay = 1;
                    displayMonth++;
                    month = "January";
                    Atmosphere.newMonthEvent();

                    displayYear++;
                    year++;
                    Atmosphere.LOGGER.info("New year!");
                }
                break;
            default:
                break;
        }
    }


    public String getTimeAsFormattedString() {
        String minuteString = Integer.toString(minute);
        if (minuteString.length() < 2)
            minuteString = "0" + minuteString;

        String hourString = Integer.toString(hour);

        return hourString + ":" + minuteString + " " + meridian;
    }


    public String getDateAsFormattedString() {
        return getDayAsString() + "\n" + getDisplayMonth() + " " + getDisplayDay() + "\nYear " + getDisplayYear() + " AS";
    }
}