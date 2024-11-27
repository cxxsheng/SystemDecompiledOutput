package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Entry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.Entry> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.Entry>() { // from class: android.credentials.selection.Entry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.Entry createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.Entry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.Entry[] newArray(int i) {
            return new android.credentials.selection.Entry[i];
        }
    };
    private android.content.Intent mFrameworkExtrasIntent;
    private final java.lang.String mKey;
    private android.app.PendingIntent mPendingIntent;
    private final android.app.slice.Slice mSlice;
    private final java.lang.String mSubkey;

    private Entry(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        java.lang.String readString82 = parcel.readString8();
        android.app.slice.Slice slice = (android.app.slice.Slice) parcel.readTypedObject(android.app.slice.Slice.CREATOR);
        this.mKey = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mKey);
        this.mSubkey = readString82;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSubkey);
        this.mSlice = slice;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSlice);
        this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
        this.mFrameworkExtrasIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
    }

    public Entry(java.lang.String str, java.lang.String str2, android.app.slice.Slice slice, android.content.Intent intent) {
        this.mKey = str;
        this.mSubkey = str2;
        this.mSlice = slice;
        this.mFrameworkExtrasIntent = intent;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    public java.lang.String getSubkey() {
        return this.mSubkey;
    }

    public android.app.slice.Slice getSlice() {
        return this.mSlice;
    }

    public android.content.Intent getFrameworkExtrasIntent() {
        return this.mFrameworkExtrasIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mKey);
        parcel.writeString8(this.mSubkey);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mPendingIntent, i);
        parcel.writeTypedObject(this.mFrameworkExtrasIntent, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
