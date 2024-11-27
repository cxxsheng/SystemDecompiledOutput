package android.app;

/* loaded from: classes.dex */
public class ResultInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ResultInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ResultInfo>() { // from class: android.app.ResultInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ResultInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.ResultInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ResultInfo[] newArray(int i) {
            return new android.app.ResultInfo[i];
        }
    };
    public final android.os.IBinder mCallerToken;
    public final android.content.Intent mData;
    public final int mRequestCode;
    public final int mResultCode;
    public final java.lang.String mResultWho;

    public ResultInfo(java.lang.String str, int i, int i2, android.content.Intent intent) {
        this(str, i, i2, intent, null);
    }

    public ResultInfo(java.lang.String str, int i, int i2, android.content.Intent intent, android.os.IBinder iBinder) {
        this.mResultWho = str;
        this.mRequestCode = i;
        this.mResultCode = i2;
        this.mData = intent;
        this.mCallerToken = iBinder;
    }

    public java.lang.String toString() {
        return "ResultInfo{who=" + this.mResultWho + ", request=" + this.mRequestCode + ", result=" + this.mResultCode + ", data=" + this.mData + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mResultWho);
        parcel.writeInt(this.mRequestCode);
        parcel.writeInt(this.mResultCode);
        if (this.mData != null) {
            parcel.writeInt(1);
            this.mData.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStrongBinder(this.mCallerToken);
    }

    public ResultInfo(android.os.Parcel parcel) {
        this.mResultWho = parcel.readString();
        this.mRequestCode = parcel.readInt();
        this.mResultCode = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mData = android.content.Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.mData = null;
        }
        this.mCallerToken = parcel.readStrongBinder();
    }

    public boolean equals(java.lang.Object obj) {
        boolean filterEquals;
        if (obj == null || !(obj instanceof android.app.ResultInfo)) {
            return false;
        }
        android.app.ResultInfo resultInfo = (android.app.ResultInfo) obj;
        if (this.mData == null) {
            filterEquals = resultInfo.mData == null;
        } else {
            filterEquals = this.mData.filterEquals(resultInfo.mData);
        }
        return filterEquals && java.util.Objects.equals(this.mResultWho, resultInfo.mResultWho) && this.mResultCode == resultInfo.mResultCode && this.mRequestCode == resultInfo.mRequestCode && this.mCallerToken == resultInfo.mCallerToken;
    }

    public int hashCode() {
        int hashCode = ((((527 + this.mRequestCode) * 31) + this.mResultCode) * 31) + java.util.Objects.hashCode(this.mResultWho);
        if (this.mData != null) {
            hashCode = (hashCode * 31) + this.mData.filterHashCode();
        }
        return (hashCode * 31) + java.util.Objects.hashCode(this.mCallerToken);
    }
}
