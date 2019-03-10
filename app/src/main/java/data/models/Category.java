package data.models;

/**
 * Created by hyun on 2015-07-30.
 */
public class Category {
    String name;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String count;

    public String toString(){
        return "Category{+"+
                "name="+name+
                ",count="+count+'\n'+'}';
    }

}
