package data.models;

import java.util.ArrayList;

public class MountainList {

    static public ArrayList<Mountain_Temp> mountainList = new ArrayList<Mountain_Temp>();
    static public ArrayList<Mountain_Temp> mountainList_temp = new ArrayList<Mountain_Temp>();
    static public ArrayList<Mountain_Temp> mountainList_like = new ArrayList<Mountain_Temp>();
    static public ArrayList<Mountain_Temp> mountainList_reviews = new ArrayList<Mountain_Temp>();

    static public ArrayList<String> mymountainlist = new ArrayList<String>();

    public ArrayList<Mountain_Temp> getMountainList() {
        return MountainList.mountainList;
    }

    public void setMountainList(ArrayList<Mountain_Temp> mountainList) {
        MountainList.mountainList = mountainList;
    }
}
