package ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alertdialogpro.AlertDialogPro;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import adapter.UserRecipeListAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import components.DividerItemDecoration;
import data.models.Category;
import data.models.Meeting;
import data.models.MeetingList;
import windmill.windmill.R;

public class MeetingRegionListFragment extends Fragment {

    @Bind(R.id.scroll)
    RecyclerView userRecipeListView;

    private UserRecipeListAdapter userRecipeListAdapter;

    static int index2;
    public static Fragment newInstance() {
        MeetingRegionListFragment fragment = new MeetingRegionListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region_recipe_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scroll);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.WHITE)
                        .sizeResId(R.dimen.divider)
                        .build());
        ButterKnife.bind(this, view);
        setupViews();

        Button btn = (Button)view.findViewById(R.id.category_sel);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showCustomViewDialog();
            }
        });

        return view;
    }


    private List<String> mCheckedItems = new ArrayList<String>();
    int mTheme = R.style.Theme_AlertDialogPro_Material_Light;
    private static final int NATIVE_THEME = Integer.MIN_VALUE;
    ArrayList<Integer> SelectItems = new ArrayList<Integer>();
    ArrayList<String> list_region = new ArrayList<String>();

    ViewAdapter adapter1;
    private void showCustomViewDialog() {

        list_region.clear();
        list_region.add("경기도");
        list_region.add("강원도");
        list_region.add("충청도");
        list_region.add("전라도");
        list_region.add("경상도");
        list_region.add("제주도");

        adapter1 = new ViewAdapter(getActivity(), R.layout.input_view, list_region);
        ListView aa = (ListView)getActivity().getLayoutInflater().inflate(R.layout.input_view,null);
        aa.setAdapter(adapter1);
        createAlertDialogBuilder()
                .setView(aa)
                .setNegativeButton("취소", null)
                .setPositiveButton("저장", new ButtonClickedListener(" ")).show();

    }


    class ViewAdapter extends ArrayAdapter<String> {

        public ViewAdapter(Context context, int resource, ArrayList<String> objects) {
            super(context, resource, objects);
            list_region = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater linf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = linf.inflate(R.layout.input_region_item, null);
            String Category = list_region.get(position);
            if (Category != null) {

                final TextView title = (TextView) convertView.findViewById(R.id.cate_name);
                title.setText(Category);

                final CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.cate_check);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            SelectItems.add(position);
                        }
                        else{
                            for(int i=0;i<SelectItems.size();i++){
                                if(SelectItems.get(i)==position)
                                    SelectItems.remove(i);
                            }
                        }
                    }
                });
            }

            return convertView;
        }
    }

    private AlertDialog.Builder createAlertDialogBuilder() {
        if (mTheme == NATIVE_THEME) {
            return new AlertDialog.Builder(getActivity());
        }

        return new AlertDialogPro.Builder(getActivity(), mTheme);
    }

    private class ButtonClickedListener implements DialogInterface.OnClickListener {
        private CharSequence mShowWhenClicked;

        public ButtonClickedListener(CharSequence showWhenClicked) {
            mShowWhenClicked = showWhenClicked;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            String items = "";
            for(int i=0;i<SelectItems.size()-1;i++){
                items = items + list_region.get(i).toString()+",";
            }
            items = items +"\n 를 선택하셨습니다";
            sort_region();
            SelectItems.clear();
            showToast(items);


        }
    }

    public void sort_region(){
        for(int i=0;i< MeetingList.meetingList.size();i++){
            for(int j=0;j<SelectItems.size();j++) {
                if (MeetingList.meetingList.get(i).getCategory().contains(list_region.get(SelectItems.get(j)))){
                    MeetingList.meetingList_region.add(MeetingList.meetingList.get(i));
                }
            }
        }
        setupViews();
    }
    private Toast mToast = null;
    private void showToast(CharSequence toastText) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT);
        mToast.show();
    }


    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }



    private void setupViews() {

        userRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecipeListView.setHasFixedSize(false);
        userRecipeListAdapter = new UserRecipeListAdapter(Meeting.dummies(Index.REGION_INDEX));
        userRecipeListView.addItemDecoration(new DividerItemDecoration(getActivity()));
        userRecipeListView.setAdapter(userRecipeListAdapter);
    }
}
