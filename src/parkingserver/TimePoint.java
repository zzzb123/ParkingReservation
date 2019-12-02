package parkingserver;
public class TimePoint implements Comparable<TimePoint>{
    private int year;
    private int month;
    private int day;
    private int hour;
    public TimePoint(int year, int month, int day, int hour){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    public String getTime(){
        return "" + year +  "," + month + "," + day + "," + hour;
    }

    public TimePoint(String timeString){
        String[] splitString = timeString.split(",");
        year = Integer.parseInt(splitString[0]);
        month = Integer.parseInt(splitString[1]);
        day = Integer.parseInt(splitString[2]);
        hour = Integer.parseInt(splitString[3]);
    }

    public int compareTo(TimePoint arg0){
        int res = 0;
        if ((res = year - arg0.year) != 0)
            return res;
        if ((res = month - arg0.month) != 0)
            return res;
        if ((res = day - arg0.day) != 0)
            return res;
        return hour - arg0.hour;
    }
}