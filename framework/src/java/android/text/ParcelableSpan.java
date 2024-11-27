package android.text;

/* loaded from: classes3.dex */
public interface ParcelableSpan extends android.os.Parcelable {
    int getSpanTypeId();

    int getSpanTypeIdInternal();

    void writeToParcelInternal(android.os.Parcel parcel, int i);
}
