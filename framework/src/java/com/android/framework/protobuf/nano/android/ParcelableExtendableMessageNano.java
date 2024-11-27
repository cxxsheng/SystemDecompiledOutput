package com.android.framework.protobuf.nano.android;

/* loaded from: classes4.dex */
public abstract class ParcelableExtendableMessageNano<M extends com.android.framework.protobuf.nano.ExtendableMessageNano<M>> extends com.android.framework.protobuf.nano.ExtendableMessageNano<M> implements android.os.Parcelable {
    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        com.android.framework.protobuf.nano.android.ParcelableMessageNanoCreator.writeToParcel(getClass(), this, parcel);
    }
}
