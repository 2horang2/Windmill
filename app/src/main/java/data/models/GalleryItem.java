package data.models;

import java.util.ArrayList;

/**
 * Created by USER on 2015-09-08.
 */
public class GalleryItem {


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }

    public String date;
    public ArrayList<Integer> index;


}
