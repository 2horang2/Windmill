package data.models;

import java.util.ArrayList;

/**
 * Created by hyun on 2015-08-08.
 */
public class MeetingUserList {
    public ArrayList<String> getMeetinguserlist() {
        return meetinguserlist;
    }

    public void setMeetinguserlist(ArrayList<String> meetinguserlist) {
        this.meetinguserlist = meetinguserlist;
    }

    static public ArrayList<String> meetinguserlist = new ArrayList<String>();
}
