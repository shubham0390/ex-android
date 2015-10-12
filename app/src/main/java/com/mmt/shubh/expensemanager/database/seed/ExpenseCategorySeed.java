package com.mmt.shubh.expensemanager.database.seed;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;
import com.mmt.shubh.expensemanager.database.dataadapters.CategorySQLDataAdapter;
import com.mmt.shubh.expensemanager.task.AbstractTask;
import com.mmt.shubh.expensemanager.task.TaskResult;

/**
 * @author Umang Chamaria
 */
public class ExpenseCategorySeed extends AbstractTask {
    Context mContext;
    public ExpenseCategorySeed(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public TaskResult execute() {
        String categoryName[] = {"Air/Railway Ticket", "Alcohol"};
        String categoryImageFile[] = {"tickets", "alcohol"};
        String categoryType = "default";
        for (int i = 0; i<categoryName.length; i++){
            ExpenseCategory expenseCategory = new ExpenseCategory(categoryName[i],
                    categoryType, categoryImageFile[i]);
            CategorySQLDataAdapter categorySQLDataAdapter = new CategorySQLDataAdapter(mContext);
            categorySQLDataAdapter.save(expenseCategory);
        }
        return null;
    }

    @Override
    public String getTaskAction() {
        return null;
    }
}
