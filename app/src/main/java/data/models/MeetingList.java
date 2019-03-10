package data.models;

import java.util.ArrayList;

/**
 * Created by hyun on 2015-07-31.
 */
public class MeetingList {

    static public ArrayList<Meeting_Temp> meetingList = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_view = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_new = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_my = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_region = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_like = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_temp = new ArrayList<Meeting_Temp>();
    static public ArrayList<Meeting_Temp> meetingList_view_temp = new ArrayList<Meeting_Temp>();


    public static ArrayList<Meeting_Temp> getMeetingList_my() {
        return meetingList_my;
    }

    public static void setMeetingList_my(ArrayList<Meeting_Temp> meetingList_my) {
        MeetingList.meetingList_my = meetingList_my;
    }



    public static ArrayList<Meeting_Temp> getMeetingList_region() {
        return meetingList_region;
    }

    public static void setMeetingList_region(ArrayList<Meeting_Temp> meetingList_region) {
        MeetingList.meetingList_region = meetingList_region;
    }

    public static ArrayList<Meeting_Temp> getMeetingList_like() {
        return meetingList_like;
    }

    public static void setMeetingList_like(ArrayList<Meeting_Temp> meetingList_like) {
        MeetingList.meetingList_like = meetingList_like;
    }



    public static ArrayList<Meeting_Temp> getMeetingList_new() {

        return meetingList_new;
    }

    public static void setMeetingList_new(ArrayList<Meeting_Temp> meetingList_new) {
        MeetingList.meetingList_new = meetingList_new;
    }


    public static ArrayList<Meeting_Temp> getMeetingList_view() {
        return meetingList_view;
    }

    public static void setMeetingList_view(ArrayList<Meeting_Temp> meetingList_view) {
        MeetingList.meetingList_view = meetingList_view;
    }

    public static ArrayList<Meeting_Temp> getMeetingList() {
        return meetingList;
    }

    public static void setMeetingList(ArrayList<Meeting_Temp> meetingList) {
        MeetingList.meetingList = meetingList;
    }


}
