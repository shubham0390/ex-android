package com.mmt.shubh.expensemanager.ui.fragment.base;

import android.os.Bundle;

/**
 * Created by Subham Tyagi,
 * on 11/Sep/2015,
 * 4:37 PM
 * TODO:Add class comment.
 */
public interface IFragmentSwitcher {

    void replaceFragment(String tag, Bundle bundle);

    void replaceFragment(int id, Bundle bundle);

    void removeFragment(String tag, Bundle bundle);

    void removeFragment(int id, Bundle bundle);
}