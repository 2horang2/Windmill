package data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import ui.fragment.MainFragment2temp;
import ui.fragment.MainFragment3;
import ui.fragment.MeetingSearchFragment;

/**
 * Created by hyun on 2015-07-31.
 */
public abstract class Meeting extends Model {

    @JsonProperty("idx")
    public abstract String idx();

    @JsonProperty("img")
    public abstract String img();

    @JsonProperty("mountain")
    public abstract String mountain();

    @JsonProperty("date")
    public abstract String date();

    @JsonProperty("title")
    public abstract String title();

    @JsonProperty("sub")
    public abstract String sub();

    @JsonProperty("user")
    public abstract String user();

    @JsonProperty("userimg")
    public abstract String userimg();

    @JsonProperty("category")
    public abstract String category();

    @JsonProperty("view")
    public abstract String view();

    @JsonProperty("member")
    public abstract String member();

    @JsonProperty("text")
    public abstract String text();

    @JsonProperty("member_imgs")
    public abstract String member_imgs();

    @JsonCreator
    public static Meeting create(@JsonProperty("idx") String idx,
                                 @JsonProperty("img") String img,
                                 @JsonProperty("mountain") String mountain,
                                 @JsonProperty("date") String date,
                                 @JsonProperty("title") String title,
                                 @JsonProperty("sub") String sub,
                                 @JsonProperty("user") String user,
                                 @JsonProperty("userimg") String userimg,
                                 @JsonProperty("category") String category,
                                 @JsonProperty("view") String view,
                                 @JsonProperty("member") String member,
                                 @JsonProperty("text") String text,
                                 @JsonProperty("member_imgs") String member_imgs) {
        return new AutoValue_Meeting(idx, img, mountain, date, title, sub, user, userimg, category, view, member, text,member_imgs);
    }

    // return dummy2;


    public static Meeting dummy(int index, ArrayList<Meeting_Temp> meetingList) {

            Meeting dummy = new AutoValue_Meeting(meetingList.get(index).getIdx(),
                    meetingList.get(index).getImg(),
                    meetingList.get(index).getMountain(),
                    meetingList.get(index).getDate(),
                    meetingList.get(index).getTitle(),
                    meetingList.get(index).getSub(),
                    meetingList.get(index).getUser(),
                    meetingList.get(index).getUserimg(),
                    meetingList.get(index).getCategory(),
                    meetingList.get(index).getView(),
                    meetingList.get(index).getMember(),
                    meetingList.get(index).getText(),
                    meetingList.get(index).getMemberImgs()
            );
            return dummy;

    }





    public static ArrayList<Meeting> dummies(int sort) {
        ArrayList<Meeting> aaa = new ArrayList<Meeting>();
        ArrayList<Meeting_Temp> meetingList = new ArrayList<Meeting_Temp>(MeetingList.meetingList.size());
        meetingList = MeetingList.meetingList_temp;

        //hot
        if (sort == 0) {
            for (int i = 0; i <= MeetingList.meetingList_view.size() - 2; i++) {
                for (int j = i + 1; j <= MeetingList.meetingList_view.size() - 1; j++) {
                    if (Integer.parseInt(MeetingList.meetingList_view.get(i).getView()) < Integer.parseInt(MeetingList.meetingList_view.get(j).getView())) {
                        Meeting_Temp tmp = MeetingList.meetingList_view.get(i);
                        MeetingList.meetingList_view.set(i, MeetingList.meetingList_view.get(j));
                        MeetingList.meetingList_view.set(j, tmp);
                    }
                }
            }
            meetingList = MeetingList.meetingList_view;
            for (int i = 0; i < MeetingList.meetingList.size(); i++) {
                aaa.add(Meeting.dummy(i, meetingList));
            }
        }
        else if (sort == 2) {
            meetingList = MeetingList.meetingList_region;
            for (int i = 0; i < MeetingList.meetingList_region.size(); i++) {
                aaa.add(Meeting.dummy(i, meetingList));
            }
        }
        else if (sort == 1){
            for (int i = 0; i < MeetingList.meetingList.size(); i++) {
                aaa.add(Meeting.dummy(i, meetingList));
            }
        }
        else if (sort ==4){ //날짜
            String user_date = MainFragment3.user_date;
            for (int i = 0; i < MeetingList.meetingList_temp.size(); i++) {
                if(MeetingList.meetingList_temp.get(i).getDate().equals(user_date))
                    aaa.add(Meeting.dummy(i, meetingList));
            }
            PREF.date_cnt = aaa.size();


        }
        else if (sort ==5){//MYMEETING
            for (int i = 0; i < MeetingList.meetingList_temp.size(); i++) {
                if(MeetingMyList.meetingmylist.contains(MeetingList.meetingList_temp.get(i).getIdx()))
                    aaa.add(Meeting.dummy(i, meetingList));
            }
        }
        else if (sort ==6){
            for (int i = 0; i < MeetingList.meetingList_temp.size(); i++) {
                if(MeetingList.meetingList_temp.get(i).getTitle().contains(MainFragment2temp.search_st)
                        || MeetingList.meetingList_temp.get(i).getUser().contains(MainFragment2temp.search_st)
                        || MeetingList.meetingList_temp.get(i).getMountain().contains(MainFragment2temp.search_st)
                        || MeetingList.meetingList_temp.get(i).getCategory().contains(MainFragment2temp.search_st))


                        aaa.add(Meeting.dummy(i, meetingList));
            }
        }

        return aaa;
    }

    public void view_sort() {

        for (int i = 0; i <= MeetingList.meetingList.size() - 1; i++) {
            for (int j = i + 1; j <= MeetingList.meetingList.size(); j++) {
                if (Integer.parseInt(MeetingList.meetingList.get(i).getView()) > Integer.parseInt(MeetingList.meetingList.get(j).getView())) {
                    Meeting_Temp tmp = MeetingList.meetingList.get(i);
                    MeetingList.meetingList.add(i, MeetingList.meetingList.get(j));
                    MeetingList.meetingList.add(j, tmp);
                }//if
            }
        }
    }

}
