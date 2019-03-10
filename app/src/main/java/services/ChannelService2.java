package services;

import java.util.ArrayList;
import java.util.List;

import data.models.Channel2;
import rx.Observable;

public class ChannelService2 {
    public Observable<List<Channel2>> getList() {
        List<Channel2> channels = new ArrayList<Channel2>() {{
            add(Channel2.dummy());
            add(Channel2.dummy());
        }};
        return Observable.just(channels);
    }
}
