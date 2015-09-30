package com.mmt.shubh.expensemanager.database.content;

import android.os.Parcel;

import org.parceler.Parcels;

// Specific class for a RealmList<Bar> property
public class MemberParcelConverter extends RealmListParcelConverter<Member> {

    @Override
    public void itemToParcel(Member input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Member itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Member.class.getClassLoader()));
    }
}