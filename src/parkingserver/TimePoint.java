package parkingserver;
public class TimePoint implements Comparable<TimePoint>{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    public TimePoint(int year, int month, int day, int hour, int minute){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public String getTime(){
        return "" + year +  "," + month + "," + day + "," + hour + "," + minute;
    }

    public TimePoint(String timeString){
        String[] splitString = timeString.split(",");
        year = Integer.parseInt(splitString[0]);
        month = Integer.parseInt(splitString[1]);
        day = Integer.parseInt(splitString[2]);
        hour = Integer.parseInt(splitString[3]);
        minute = Integer.parseInt(splitString[4]);
    }

    public int compareTo(TimePoint arg0){
        int res = 0;
        if ((res = year - arg0.year) != 0)
            return res;
        if ((res = month - arg0.month) != 0)
            return res;
        if ((res = day - arg0.day) != 0)
            return res;
        if ((res = hour - arg0.hour) != 0)
            return res;
        return minute - arg0.minute;
    }

    @Override
    public boolean equals(Object arg0){
        return compareTo((TimePoint)arg0) == 0;
    }

    @Override
    public int hashCode(){
        return getTime().hashCode();
    }
}