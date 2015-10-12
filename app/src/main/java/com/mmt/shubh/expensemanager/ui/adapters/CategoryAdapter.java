package com.mmt.shubh.expensemanager.ui.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmt.shubh.expensemanager.R;
import com.mmt.shubh.expensemanager.database.content.ExpenseCategory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Umang Chamaria
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<ExpenseCategory> mCategories;

    private String mSelectedCategory;

    private static final String CATERGORY_IMAGE_PREFIX = "ic_icon_category_";

    private static Context mContext;


    public CategoryAdapter(List<ExpenseCategory> categories, Context context) {
        mCategories = new ArrayList<>(categories);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExpenseCategory categoryMetaData = mCategories.get(position);
        String fileName = "drawable/" + CATERGORY_IMAGE_PREFIX + categoryMetaData
                .getCategoryImageName();
        int imageResource = mContext.getResources().getIdentifier(fileName, null,
                mContext.getPackageName());
        Drawable res = mContext.getResources().getDrawable(imageResource);
        holder.mCategoryImage.setImageDrawable(res);
        holder.mCategoryName.setText(categoryMetaData.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void animateTo(List<ExpenseCategory> filteredCategory) {
        applyAndAnimateRemovals(filteredCategory);
        applyAndAnimateAdditions(filteredCategory);
        applyAndAnimateMovedItems(filteredCategory);
    }

    private void applyAndAnimateRemovals(List<ExpenseCategory> filteredContactList) {
        for (int i = mCategories.size() - 1; i >= 0; i--) {
            final ExpenseCategory contact = mCategories.get(i);
            if (!filteredContactList.contains(contact)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ExpenseCategory> filteredContactsList) {
        for (int i = 0, count = filteredContactsList.size(); i < count; i++) {
            final ExpenseCategory contact = filteredContactsList.get(i);
            if (!mCategories.contains(contact)) {
                addItem(i, contact);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ExpenseCategory> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ExpenseCategory model = newModels.get(toPosition);
            final int fromPosition = mCategories.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ExpenseCategory removeItem(int position) {
        final ExpenseCategory contact = mCategories.remove(position);
        notifyItemRemoved(position);
        return contact;
    }

    public void addItem(int position, ExpenseCategory model) {
        mCategories.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ExpenseCategory model = mCategories.remove(fromPosition);
        mCategories.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        @Bind(R.id.category_name)
        TextView mCategoryName;

        @Bind(R.id.category_image)
        ImageView mCategoryImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }

        public void bindView(ExpenseCategory categoryMetaData){
/*            mCategoryName.setText(categoryMetaData.getCategoryName());
            if (categoryMetaData.getCategoryType().equalsIgnoreCase(Constants.CATEGORY_TPYE_DEFAULT)){
                mCategoryImage.setImageResource(categoryMetaData.getCategoryResourceId());
            }
            else {
                if (!TextUtils.isEmpty(categoryMetaData.getCategoryImageUrl())){
                    mCategoryImage.setImageURI(Uri.parse(categoryMetaData.getCategoryImageUrl()));
                }else {
                    // TODO: 03-Oct-15 add default image for custom category
                }
            }
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 mSelectedCategory = categoryMetaData.getCategoryName();
                }
            });*/

        }
    }

    public String getSelectedCategory(){
        return mSelectedCategory;
    }
}
