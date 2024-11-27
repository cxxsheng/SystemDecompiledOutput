package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class InputConnectionCommandHeader implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.inputmethod.InputConnectionCommandHeader> CREATOR = new android.os.Parcelable.Creator<com.android.internal.inputmethod.InputConnectionCommandHeader>() { // from class: com.android.internal.inputmethod.InputConnectionCommandHeader.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InputConnectionCommandHeader createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.inputmethod.InputConnectionCommandHeader(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.inputmethod.InputConnectionCommandHeader[] newArray(int i) {
            return new com.android.internal.inputmethod.InputConnectionCommandHeader[i];
        }
    };
    public final int mSessionId;

    public InputConnectionCommandHeader(int i) {
        this.mSessionId = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSessionId);
    }
}
