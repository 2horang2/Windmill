package ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Meeting;
import windmill.windmill.R;


public class MeetingLikeListFragment extends Fragment {

    @Bind(R.id.scroll)
    RecyclerView userRecipeListView;

    private UserRecipeListAdapter userRecipeListAdapter;

    static int index2;
    public static Fragment newInstance() {
        MeetingLikeListFragment fragment = new MeetingLikeListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_recipe_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scroll);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.WHITE)
                        .sizeResId(R.dimen.divider)
                        .build());
        ButterKnife.bind(this, view);
        setupViews();
        return view;
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void setupViews() {

        userRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecipeListView.setHasFixedSize(false);
        userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(Index.LIKE_INDEX));
        userRecipeListView.addItemDecoration(new DividerItemDecoration(getActivity()));
        userRecipeListView.setAdapter(userRecipeListAdapter);
    }
}
