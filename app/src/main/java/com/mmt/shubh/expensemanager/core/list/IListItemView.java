package com.mmt.shubh.expensemanager.core.list;

public interface IListItemView<T> {

    void setSelected(boolean selected);

    void onCreateView();

    void onBindView(T data);
}
