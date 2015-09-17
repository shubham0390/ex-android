package com.mmt.shubh.expensemanager.ui;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mmt.shubh.expensemanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Subham Tyagi,
 * on 23/Jul/2015,
 * 5:35 PM
 * TODO:Add class comment.
 */
public class Dialoge {

    public void onCreateDialog(Activity activity) {

        final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(activity);
        helpBuilder.setTitle("Select Bank");

        LayoutInflater inflater = LayoutInflater.from(activity);
        final View PopupLayout = inflater.inflate(R.layout.choose_bank_dialog, null);
        helpBuilder.setView(PopupLayout);


        ListView jobList = (ListView) PopupLayout.findViewById(R.id.list);

        ArrayList<HashMap<String, String>> mylist = new ArrayList<>();
        String[] bankName = activity.getResources().getStringArray(R.array.bank_name);
        Arrays.sort(bankName);
        HashMap<String, String> map;

        for (int i = 0; i < bankName.length; i++) {
            map = new HashMap<>();
            map.put("name", bankName[i]);
            mylist.add(map);
        }
        SimpleAdapter sd = new SimpleAdapter(activity, mylist, R.layout.list_item_bank,
                new String[]{"name"}, new int[]{R.id.bank_name});
        jobList.setAdapter(sd);

        final AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();

    }

}
