package com.mmt.shubh.expensemanager.database.content;

import android.os.Parcel;

import org.parceler.Parcels;

// Specific class for a RealmList<Bar> property
public class BankCardParcelConverter extends RealmListParcelConverter<BankCard> {

    @Override
    public void itemToParcel(BankCard input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public BankCard itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(BankCard.class.getClassLoader()));
    }
}