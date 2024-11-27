package android.app.wearable;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class WearableSensingDataRequest implements android.os.Parcelable {
    private static final int MAX_REQUEST_SIZE = 200;
    private static final int RATE_LIMIT = 30;
    public static final java.lang.String REQUEST_BUNDLE_KEY = "android.app.wearable.WearableSensingDataRequestBundleKey";
    public static final java.lang.String REQUEST_STATUS_CALLBACK_BUNDLE_KEY = "android.app.wearable.WearableSensingDataRequestStatusCallbackBundleKey";
    private final int mDataType;
    private final android.os.PersistableBundle mRequestDetails;
    private static final java.time.Duration RATE_LIMIT_WINDOW_SIZE = java.time.Duration.ofMinutes(1);
    public static final android.os.Parcelable.Creator<android.app.wearable.WearableSensingDataRequest> CREATOR = new android.os.Parcelable.Creator<android.app.wearable.WearableSensingDataRequest>() { // from class: android.app.wearable.WearableSensingDataRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wearable.WearableSensingDataRequest[] newArray(int i) {
            return new android.app.wearable.WearableSensingDataRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.wearable.WearableSensingDataRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.wearable.WearableSensingDataRequest(parcel.readInt(), (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR));
        }
    };

    private WearableSensingDataRequest(int i, android.os.PersistableBundle persistableBundle) {
        this.mDataType = i;
        this.mRequestDetails = persistableBundle;
    }

    public int getDataType() {
        return this.mDataType;
    }

    public android.os.PersistableBundle getRequestDetails() {
        return this.mRequestDetails;
    }

    public int getDataSize() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeToParcel(obtain, describeContents());
            return obtain.dataSize();
        } finally {
            obtain.recycle();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDataType);
        parcel.writeTypedObject(this.mRequestDetails, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "WearableSensingDataRequest { dataType = " + this.mDataType + ", requestDetails = " + this.mRequestDetails + " }";
    }

    public java.lang.String toExpandedString() {
        if (this.mRequestDetails != null) {
            this.mRequestDetails.getBoolean("PlaceholderForWearableSensingDataRequest#toExpandedString()");
        }
        return toString();
    }

    public static int getMaxRequestSize() {
        return 200;
    }

    public static java.time.Duration getRateLimitWindowSize() {
        return RATE_LIMIT_WINDOW_SIZE;
    }

    public static int getRateLimit() {
        return 30;
    }

    public static final class Builder {
        private int mDataType;
        private android.os.PersistableBundle mRequestDetails;

        public Builder(int i) {
            this.mDataType = i;
        }

        public android.app.wearable.WearableSensingDataRequest.Builder setRequestDetails(android.os.PersistableBundle persistableBundle) {
            this.mRequestDetails = persistableBundle;
            return this;
        }

        public android.app.wearable.WearableSensingDataRequest build() {
            if (this.mRequestDetails == null) {
                this.mRequestDetails = android.os.PersistableBundle.EMPTY;
            }
            return new android.app.wearable.WearableSensingDataRequest(this.mDataType, this.mRequestDetails);
        }
    }
}
