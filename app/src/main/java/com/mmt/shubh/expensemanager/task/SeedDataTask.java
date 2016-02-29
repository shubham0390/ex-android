package com.mmt.shubh.expensemanager.task;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.dataadapters.MemberSQLDataAdapter;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.schedulers.Schedulers;

/**
 * Created by Subham Tyagi,
 * on 09/Nov/2015,
 * 3:07 PM
 * TODO:Add class comment.
 */
public class SeedDataTask extends AbstractTask {

    public static final String ACTION_SEED = "com.SeedData";
    ExpenseBookDataAdapter mExpenseBookDataAdapter;
    private String LOG_TAG = getClass().getName();
    private ExpenseModel mExpenseModel;

    public SeedDataTask(Context context, ExpenseModel expenseModel, ExpenseBookDataAdapter expenseBookDataAdapter) {
        super(context);
        mExpenseModel = expenseModel;
        mExpenseBookDataAdapter = expenseBookDataAdapter;
    }

    @Override
    public TaskResult execute() {
        addBankAccounts();
        addExpenseBook(addMembers());
        createExpense();
        createTaskResult(true, 12, "");
        return mTaskResult;
    }

    private void createExpense() {
        for (int i = 1; i < 30; i++) {
            Expense expense = new Expense();
            expense.setExpenseAmount(1000);
            expense.setExpenseBookId(getExpenseBookId());
            expense.setExpenseCategoryId(1 % 2);
            expense.setExpenseDate(new Date().getTime());
            expense.setExpenseDescription("ajsedflojsldfjg");
            expense.setDistrubtionType(Expense.DISTRIBUTION_EQUALLY);
            expense.setExpenseName("sdjjfhksdjfhk");
            expense.setExpensePlace("sdkjfcksjdnfks");
            expense.setOwnerId(getMemberId());
            expense.setAccountKey(getAccountId());
            Map map = new HashMap<>();
            map.put(1L, 1D);
            expense.setMemberMap(map);
            mExpenseModel.createExpense(1, expense);
        }
    }

    private void addExpenseBook(List<Member> members) {
        Logger.methodStart(LOG_TAG, "addExpenseBook");

        for (int i = 1; i < 6; i++) {
            ExpenseBook expenseBook = new ExpenseBook();
            expenseBook.setType("Private");
            expenseBook.setDescription("This is personal expense book of" + members.get(i));
            expenseBook.setName("ExpenseBook" + i);
            expenseBook.setProfileImagePath(getMemberImage(i));
            expenseBook.setMemberList(members);
            expenseBook.setOwner(members.get(i));
            expenseBook.setCreationTime(System.currentTimeMillis());
            mExpenseBookDataAdapter.create(expenseBook)
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
                    .subscribe(d -> mExpenseBookDataAdapter.addMember(expenseBook));
        }
        Logger.methodEnd(LOG_TAG, "addExpenseBook");
    }

    private int getExpenseBookId() {
        Random random = new Random();
        return random.nextInt(6 - 1);
    }

    private List<Member> addMembers() {
        Logger.methodStart(LOG_TAG, "addMembers");
        MemberDataAdapter memberDataAdapter = new MemberSQLDataAdapter(mContext);
        List<Member> members = new ArrayList<>();
        for (int i = 1; i < 11; i++) {

            Member member = new Member();
            member.setMemberName("Member " + i);
            member.setMemberEmail("aasdad@asdas.com" + i);
            member.setProfilePhotoUrl(getMemberImage(i));
            member.setCoverPhotoUrl(getMemberImage(i));
            member.setMemberPhoneNumber(123456789 + i);
            members.add(member);
            memberDataAdapter.create(member);
        }
        Logger.methodEnd(LOG_TAG, "addMembers");
        return members;
    }

    private int getMemberId() {
        Random random = new Random();
        return random.nextInt(11 - 1);
    }

    private String getMemberImage(int id) {

        String[] image = {
                "http://www.fhm.com/media/2015/05/instagram-follow-more-thumb.jpg",
                "http://c300221.r21.cf1.rackcdn.com/hollywood-actress-hot-photos-two-hollywood-actress-hot-photos-two-1390244986_org.jpg",
                "http://www.cineforest.com/Photos/Hollywood%20Actress%20Hot%20Clevage%20Show/3.jpg",
                "http://cdn23.us1.fansshare.com/celebrity/photos/934_hot-hollywood-actress-scarlett-johansson-hot-2024535078.jpg",
                "http://host2post.com/server13/photos/IDU0SdzmuUNw2M~/hollywood-actress-hot-wallpapers-mix-gossip.jpg",
                "http://host2post.com/server13/photos/nr3uUT2lnEXQDM~/wallpapers-hot-sexy-hollywood-actress-jennifer-lamiraqu.jpg",
                "http://cdn23.us1.fansshare.com/photos/hotwallpaper/neha-sharma-hd-wallpaper-free-download-of-hollywood-actress-243469819.jpg",
                "http://4.bp.blogspot.com/-CwA-F7klqMc/Tmh5cYfLmpI/AAAAAAAAFdc/e6dzal-Hrm4/s1600/Pictures_of_hollywood_actress+3.jpg",
                "http://www.nicehdwallpaper.com/wp-content/uploads/2013/12/Hot-hollywood-actress-demi-lovato-hd-wallpaper-free.jpg",
                "http://cdn2.stillgalaxy.com/ori/2013/03/26/hollywood-actress-hot-looking-wallpaper-0.jpg",
                "http://www.finewallpaperes.com/wp-content/uploads/2013/03/very-beautiful-actress-penelope-cruz-high-quality-wide-photo-and-wallpapers.jpg",
                "http://3.bp.blogspot.com/-lkFA9Y-plYM/UTql94X1-cI/AAAAAAAAiww/XSK5sd51DYY/s1600/Celebrities+HD+wallpaper+(48).jpg",
                "http://4.bp.blogspot.com/-MP5gSXqwWlg/UTqmF71oQ3I/AAAAAAAAiw4/F2kzs2ADNZ8/s1600/Celebrities+HD+wallpaper+(45).jpg",
                "http://4.bp.blogspot.com/--YnDbL1EDXA/UTqlPubalGI/AAAAAAAAivw/m19CLBPD3UE/s1600/Celebrities+HD+wallpaper+(42).jpg",
                "http://4.bp.blogspot.com/-LfYDDt-6Ke4/UTqlTUyRJPI/AAAAAAAAiwA/qQpSbkpMbsQ/s1600/Celebrities+HD+wallpaper+(43).jpg",
                "http://2.bp.blogspot.com/-5s2-8_lArt8/UXYAjnmoTLI/AAAAAAAAsRs/pryO_6f5DXc/s1600/Celebrity+HD+Wallpapers+(1).jpg",
                "http://4.bp.blogspot.com/-5INA6S--bHY/UXYAzu9csOI/AAAAAAAAsSQ/CK9qSYf6eio/s1600/Celebrity+HD+Wallpapers+(14).jpg",
                "http://4.bp.blogspot.com/-9e1NnX-bp3I/UXYA1LjPvxI/AAAAAAAAsSY/YLJ5ZZZAI_c/s1600/Celebrity+HD+Wallpapers+(15).jpg",
                "http://3.bp.blogspot.com/-zEIBY6iZ4Ng/UXYBAXRt8cI/AAAAAAAAsSg/Md6HqyT5Twc/s1600/Celebrity+HD+Wallpapers+(3).jpg",
                "http://2.bp.blogspot.com/-hYwBSp3Lwek/UXYBUb8ibnI/AAAAAAAAsTI/cA6bl_VBRac/s1600/Celebrity+HD+Wallpapers+(7).jpg",
                "http://4.bp.blogspot.com/-mNLQlApDiYo/UXYBkCbpZMI/AAAAAAAAsTY/UJIoCjHJs9E/s1600/Celebrity+HD+Wallpapers+(9).jpg",
                "http://3.bp.blogspot.com/-biGguElCUN8/UXX36AlPBwI/AAAAAAAAsNo/iAKH71VMZY4/s1600/Actress+HD+Wallpapers+(12).jpg",
                "http://1.bp.blogspot.com/-E1rAeSVjtgM/UXX3-6uBqVI/AAAAAAAAsNw/Ph-VzVilF9U/s1600/Actress+HD+Wallpapers+(11).jpg",
                "http://4.bp.blogspot.com/-xqjINTJ4Ho4/UXX3uiWfMLI/AAAAAAAAsNg/qIMlH3bbIRE/s1600/Actress+HD+Wallpapers+(1).jpg",
                "http://2.bp.blogspot.com/-pGIdP1W8xSE/UXX42sbxVxI/AAAAAAAAsPA/xBZY8TRZ2Bg/s1600/Actress+HD+Wallpapers+(6).jpg",
                "http://4.bp.blogspot.com/-IgMKsd0nfaE/UXX48DQrMDI/AAAAAAAAsPQ/O-kHh8MHwj0/s1600/Actress+HD+Wallpapers+(7).jpg",
                "https://s-media-cache-ak0.pinimg.com/736x/d7/2d/dc/d72ddc7e1c7f78ffeecd666d82b04191.jpg",
                "https://s-media-cache-ak0.pinimg.com/736x/9d/16/34/9d1634409d6fc20419933662808d1954.jpg",
        };
        return image[id];
    }

    private void addBankAccounts() {
        Logger.methodStart(LOG_TAG, "addBankAccounts");
        AccountDataAdapter accountDataAdapter = mExpenseModel.getAccountDataAdapter();
        for (int i = 1; i < 6; i++) {
            Account account = new Account();
            account.setAccountName("Account " + i);
            account.setAccountBalance(12345);
            account.setAccountNumber("xx-xx-xx-xx-" + i);
            account.setBankName("Bank " + 1);
            if (i % 2 == 0) {
                account.setType(Account.TYPE_CREDIT_CARD);
            } else
                account.setType(Account.TYPE_BANK);
            accountDataAdapter.create(account);
        }
        Logger.methodEnd(LOG_TAG, "addBankAccounts");
    }

    private int getAccountId() {
        Random random = new Random();
        return random.nextInt(6 - 1);
    }

    @Override
    public String getTaskAction() {
        return ACTION_SEED;
    }
}
