package com.android.framework.protobuf.nano.android;

/* loaded from: classes4.dex */
public abstract class ParcelableMessageNano extends com.android.framework.protobuf.nano.MessageNano implements android.os.Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        com.android.framework.protobuf.nano.android.ParcelableMessageNanoCreator.writeToParcel(getClass(), this, parcel);
    }
}
