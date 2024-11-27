package android.text.style;

/* loaded from: classes3.dex */
public class EasyEditSpan implements android.text.ParcelableSpan {
    public static final java.lang.String EXTRA_TEXT_CHANGED_TYPE = "android.text.style.EXTRA_TEXT_CHANGED_TYPE";
    public static final int TEXT_DELETED = 1;
    public static final int TEXT_MODIFIED = 2;
    private boolean mDeleteEnabled;
    private final android.app.PendingIntent mPendingIntent;

    public EasyEditSpan() {
        this.mPendingIntent = null;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(android.app.PendingIntent pendingIntent) {
        this.mPendingIntent = pendingIntent;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(android.os.Parcel parcel) {
        this.mPendingIntent = (android.app.PendingIntent) parcel.readParcelable(null, android.app.PendingIntent.class);
        this.mDeleteEnabled = parcel.readByte() == 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mPendingIntent, 0);
        parcel.writeByte(this.mDeleteEnabled ? (byte) 1 : (byte) 0);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 22;
    }

    public boolean isDeleteEnabled() {
        return this.mDeleteEnabled;
    }

    public void setDeleteEnabled(boolean z) {
        this.mDeleteEnabled = z;
    }

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }
}
