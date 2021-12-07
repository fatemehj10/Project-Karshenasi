package ir.beheshti.bpms.model.event_log;

import lombok.*;

import java.util.Arrays;
import java.util.Date;

//*** lombok ***//
@Getter
@Setter
@ToString
//**************//

public class EventLogElement implements Comparable<EventLogElement> {
    private String caseId;
    private String timestamp;//format : 2012-04-03 07:55:38
    private String activityName;

    public EventLogElement(String caseId, String timestamp, String activityName) {
        this.caseId = caseId;
        this.timestamp = timestamp;
        this.activityName = activityName;
    }

    public Date getTimestamp() {
        String[] dateFormatString = timestamp.split("[- :]");
        Integer[] date = Arrays.stream(dateFormatString)
                .mapToInt(Integer::parseInt)
                .boxed()
                .toArray(Integer[]::new);
            return new Date(date[0], date[1], date[2], date[3], date[4], date[5]);
    }

    @Override
    public int compareTo(EventLogElement o) {
        if (this.caseId.equals(o.caseId)) {
            Date date1 = this.getTimestamp();
            Date date2 = o.getTimestamp();
            return date1.compareTo(date2);
        }
        return this.caseId.compareTo(o.caseId);
    }
}
