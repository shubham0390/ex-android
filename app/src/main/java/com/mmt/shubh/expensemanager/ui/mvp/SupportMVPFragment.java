package com.mmt.shubh.expensemanager.ui.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmt.shubh.expensemanager.ExpenseApplication;
import com.mmt.shubh.expensemanager.dagger.MainComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import icepick.Icepick;

/**
 * A base fragment that uses Icepick, Butterknife and FragmentArgs.
 * Instead of overriding {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)} you can also
 * simply have
 * override {@link #getLayoutRes()} and return your desired layout resource, which will be
 * inflated.
 * <p>
 * Future initialization can be done in {@link #onViewCreated(View, Bundle)} method <b>(don't
 * forget
 * to
 * call super.onViewCreated())</b>, which is called after
 * the view has been created, Butterknife has "injected" views, FragmentArgs has been set and
 * Icepick has restored savedInstanceState.
 * <code>init()</code> is called from {@link #onViewCreated(View, Bundle)} which is called after
 * {@link #onCreateView(LayoutInflater, ViewGroup,
 * Bundle)}
 * </p>
 * <p/>
 * <p>
 * If you want to use dependency injection libraries like dagger you can override {@link
 * #injectDependencies(MainComponent)} and implement dependency injection right there
 * </p>
 *
 * @author Hannes Dorfmann
 * @since 1.0.0
 */
public abstract class SupportMVPFragment<V extends MVPView, P extends MVPPresenter> extends Fragment implements MVPView{

    @Inject
    protected P mPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutRes = getLayoutRes();
        if (layoutRes == 0) {
            throw new IllegalArgumentException(
                    "getLayoutRes() returned 0, which is not allowed. "
                            + "If you don't want to use getLayoutRes() but implement your own view for this "
                            + "fragment manually, then you have to override onCreateView();");
        } else {
            View v = inflater.inflate(layoutRes, container, false);

            return v;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainComponent component = (MainComponent) ExpenseApplication.component();
        injectDependencies(component);
        ButterKnife.bind(this, view);
        if(mPresenter==null){
            mPresenter = getPresenter();
        }
        mPresenter.attachView(this);

    }

    protected P getPresenter(){
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mPresenter.detachView(getRetainInstance());
    }

    /**
     * This method will be called from {@link #onViewCreated(View, Bundle)} and this is the right place to
     * inject
     * dependencies (i.e. by using dagger)
     */
    protected void injectDependencies(MainComponent mainComponent) {

    }

    /**
     * Return the layout resource like R.layout.my_layout
     *
     * @return the layout resource or zero ("0"), if you don't want to have an UI
     */
    @LayoutRes
    protected int getLayoutRes() {
        return 0;
    }

}