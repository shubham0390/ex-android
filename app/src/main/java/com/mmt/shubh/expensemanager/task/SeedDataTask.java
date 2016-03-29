package com.mmt.shubh.expensemanager.task;

import android.content.Context;

import com.mmt.shubh.expensemanager.database.api.ExpenseBookDataAdapter;
import com.mmt.shubh.expensemanager.database.api.MemberDataAdapter;
import com.mmt.shubh.expensemanager.database.api.exceptions.AccountDataAdapter;
import com.mmt.shubh.expensemanager.database.content.Account;
import com.mmt.shubh.expensemanager.database.content.Expense;
import com.mmt.shubh.expensemanager.database.content.ExpenseBook;
import com.mmt.shubh.expensemanager.database.content.Member;
import com.mmt.shubh.expensemanager.database.content.contract.AccountContract;
import com.mmt.shubh.expensemanager.database.content.contract.ExpenseBookContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberContract;
import com.mmt.shubh.expensemanager.database.content.contract.MemberExpenseBookContract;
import com.mmt.shubh.expensemanager.debug.Logger;
import com.mmt.shubh.expensemanager.expense.ExpenseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rx.schedulers.Schedulers;
import timber.log.Timber;

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
        addBankAccounts(getJsonStringFromUrl("https://www.mockaroo.com/cad293a0/download?count=5&key=327934b0"));
        parseJsonMemberString(getJsonStringFromUrl("https://www.mockaroo.com/cad8aad0/download?count=10&key=327934b0"));
        addExpenseBook(getJsonStringFromUrl("https://www.mockaroo.com/6831cac0/download?count=10&key=327934b0"));
        addExpenseBookMember(getJsonStringFromUrl("https://www.mockaroo.com/1d05f980/download?count=50&key=327934b0"));
        createExpense();
        createTaskResult(true, 12, "");
        return mTaskResult;
    }


    private String getJsonStringFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int code = con.getResponseCode();
                if (code == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                }
            } catch (IOException e) {
                Timber.e(e.getMessage());
            }

        } catch (MalformedURLException e) {
            Timber.e(e.getMessage());
        }
        return null;
    }

    private void parseJsonMemberString(String s) {
        Logger.methodStart(LOG_TAG, "addmember");
        try {
            JSONArray jsonArray = new JSONArray(s);
            List<Member> members = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Member member = new Member();
                member.setMemberName(jsonObject.getString(MemberContract.MEMBER_NAME));
                member.setMemberEmail(jsonObject.getString(MemberContract.MEMBER_EMAIL));
                member.setProfilePhotoUrl(jsonObject.getString(MemberContract.MEMBER_IMAGE_URI));
                member.setCoverPhotoUrl(jsonObject.getString(MemberContract.MEMBER_COVER_IMAGE_URL));
                member.setMemberPhoneNumber(1234567890 + i);
                members.add(member);
            }
            MemberDataAdapter memberDataAdapter = mExpenseModel.getMemberDataAdapter();
            memberDataAdapter.create(members).subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
                    .subscribe(d -> {
                        Timber.i("Member added successfully" + d.size());
                    });
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Logger.methodEnd(LOG_TAG, "addMembr");


    }

    private void createExpense() {
        List<Expense> expenses = new ArrayList<>();
        for (int i = 1; i < 300; i++) {
            Expense expense = new Expense();
            expense.setExpenseAmount(1000);
            expense.setExpenseBookId(getExpenseBookId());
            expense.setExpenseCategoryId(i % 2);
            expense.setExpenseDate(new Date().getTime());
            expense.setExpenseDescription("This is testing expense");
            expense.setDistrubtionType(Expense.DISTRIBUTION_EQUALLY);
            expense.setExpenseName("Testing Expense " + i);
            expense.setExpensePlace("Delhi");
            expense.setOwnerId(getMemberId());
            expense.setAccountKey(getAccountId());
            Map map = new HashMap<>();
            map.put(getMemberId(), getAmount(getBoolean(i, 1)));
            map.put(getMemberId(), getAmount(getBoolean(i, 2)));
            map.put(getMemberId(), getAmount(getBoolean(i, 3)));
            map.put(getMemberId(), getAmount(getBoolean(i, 4)));
            expense.setMemberMap(map);
            expenses.add(expense);

        }
        mExpenseModel.createExpense(expenses)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(d -> {
                    Timber.i("Expenses added successfully");
                });
        ;
    }

    private boolean getBoolean(int i, int i2) {
        if (i2 == 1) {
            if (isPrimeNumber(i)) {
                return true;
            }
        } else {
            if (i % i2 == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean isPrimeNumber(int number) {

        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }


    private double getAmount(boolean value) {
        return value ? 1000D : 0D;
    }

    private void addExpenseBook(String jsonString) {
        Logger.methodStart(LOG_TAG, "addExpenseBook");
        List<ExpenseBook> expenseBooks = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ExpenseBook expenseBook = new ExpenseBook();
                expenseBook.setType(jsonObject.getString(ExpenseBookContract.EXPENSE_BOOK_TYPE));
                expenseBook.setDescription(jsonObject.getString(ExpenseBookContract.EXPENSE_BOOK_NAME));
                expenseBook.setName(jsonObject.getString(ExpenseBookContract.EXPENSE_BOOK_NAME));
                expenseBook.setProfileImagePath(jsonObject.getString(ExpenseBookContract.EXPENSE_BOOK_PROFILE_IMAGE_URI));
                expenseBook.setOwner(jsonObject.getLong(ExpenseBookContract.OWNER_KEY));
                expenseBook.setCreationTime(jsonObject.getLong(ExpenseBookContract.EXPENSE_BOOK_CREATION_TIME));
                expenseBooks.add(expenseBook);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mExpenseBookDataAdapter.create(expenseBooks)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(d -> {
                    Timber.i("Expense book addes successfully");
                });
        Logger.methodEnd(LOG_TAG, "addExpenseBook");
    }

    private void addExpenseBookMember(String jsonString) {
        Logger.methodStart(LOG_TAG, "addExpenseBookMember");
        Map<Long, Long> expenseBooks = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                expenseBooks.put(jsonObject.getLong(MemberExpenseBookContract.EXPENSE_BOOK_KEY),
                        jsonObject.getLong(MemberExpenseBookContract.MEMBER_KEY));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mExpenseBookDataAdapter.addMembers(expenseBooks);
    }

    private int getExpenseBookId() {
        Random random = new Random();
        return random.nextInt(9 - 1);
    }

    private int getMemberId() {
        Random random = new Random();
        return random.nextInt(19 - 1);
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


    private String getMemberCoverImage(int id) {

        String[] image = {
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

    private void addBankAccounts(String jsonStringFromUrl) {
        AccountDataAdapter accountDataAdapter = mExpenseModel.getAccountDataAdapter();
        List<Account> accounts = new ArrayList<>();
        Logger.methodStart(LOG_TAG, "addBankAccounts");
        try {
            JSONArray jsonArray = new JSONArray(jsonStringFromUrl);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Account account = new Account();
                account.setAccountName(jsonObject.getString(AccountContract.ACCOUNT_NAME));
                account.setAccountBalance(jsonObject.getDouble(AccountContract.ACCOUNT_BALANCE));
                account.setAccountNumber(jsonObject.getString(AccountContract.ACCOUNT_NUMBER));
                account.setBankName(jsonObject.getString(AccountContract.ACCOUNT_NAME));
                account.setType(jsonObject.getString(AccountContract.ACCOUNT_TYPE));
                accounts.add(account);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        accountDataAdapter.create(accounts)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate()).subscribe(d -> {
            Timber.i("Account Created successfully ");
        });
        Logger.methodEnd(LOG_TAG, "addBankAccounts");
    }

    private int getAccountId() {
        Random random = new Random();
        return random.nextInt(5 - 1);
    }

    @Override
    public String getTaskAction() {
        return ACTION_SEED;
    }
}
