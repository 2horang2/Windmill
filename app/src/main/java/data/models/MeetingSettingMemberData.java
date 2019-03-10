package data.models;

/**
 * Created by man on 2015-09-09.
 */
public class MeetingSettingMemberData {
    String meeting_idx;

    public String getMember_img() {
        return member_img;
    }

    public void setMember_img(String member_img) {
        this.member_img = member_img;
    }

    public String getMeeting_idx() {
        return meeting_idx;
    }

    public void setMeeting_idx(String meeting_idx) {
        this.meeting_idx = meeting_idx;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    String member_id;
    String member_img;

}
