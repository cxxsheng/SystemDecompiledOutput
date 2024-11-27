package com.android.ims.internal.uce.common;

/* loaded from: classes4.dex */
public class StatusCode implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.ims.internal.uce.common.StatusCode> CREATOR = new android.os.Parcelable.Creator<com.android.ims.internal.uce.common.StatusCode>() { // from class: com.android.ims.internal.uce.common.StatusCode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.common.StatusCode createFromParcel(android.os.Parcel parcel) {
            return new com.android.ims.internal.uce.common.StatusCode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.ims.internal.uce.common.StatusCode[] newArray(int i) {
            return new com.android.ims.internal.uce.common.StatusCode[i];
        }
    };
    public static final int UCE_FAILURE = 1;
    public static final int UCE_FETCH_ERROR = 6;
    public static final int UCE_INSUFFICIENT_MEMORY = 8;
    public static final int UCE_INVALID_FEATURE_TAG = 15;
    public static final int UCE_INVALID_LISTENER_HANDLE = 4;
    public static final int UCE_INVALID_PARAM = 5;
    public static final int UCE_INVALID_SERVICE_HANDLE = 3;
    public static final int UCE_LOST_NET = 9;
    public static final int UCE_NOT_FOUND = 11;
    public static final int UCE_NOT_SUPPORTED = 10;
    public static final int UCE_NO_CHANGE_IN_CAP = 13;
    public static final int UCE_REQUEST_TIMEOUT = 7;
    public static final int UCE_SERVICE_AVAILABLE = 16;
    public static final int UCE_SERVICE_UNAVAILABLE = 12;
    public static final int UCE_SERVICE_UNKNOWN = 14;
    public static final int UCE_SUCCESS = 0;
    public static final int UCE_SUCCESS_ASYC_UPDATE = 2;
    private int mStatusCode;

    public StatusCode() {
        this.mStatusCode = 0;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setStatusCode(int i) {
        this.mStatusCode = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatusCode);
    }

    private StatusCode(android.os.Parcel parcel) {
        this.mStatusCode = 0;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mStatusCode = parcel.readInt();
    }
}
