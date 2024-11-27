package android.text;

/* loaded from: classes3.dex */
public class Annotation implements android.text.ParcelableSpan {
    private final java.lang.String mKey;
    private final java.lang.String mValue;

    public Annotation(java.lang.String str, java.lang.String str2) {
        this.mKey = str;
        this.mValue = str2;
    }

    public Annotation(android.os.Parcel parcel) {
        this.mKey = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 18;
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
        parcel.writeString(this.mKey);
        parcel.writeString(this.mValue);
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    public java.lang.String getValue() {
        return this.mValue;
    }
}
