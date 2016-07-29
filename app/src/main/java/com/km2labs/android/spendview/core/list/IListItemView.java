package com.km2labs.android.spendview.core.list;

public interface IListItemView<T> {

    void setSelected(boolean selected);

    void onCreateView();

    void onBindView(T data);
}
