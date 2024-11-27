package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AuthenticationEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.AuthenticationEntry> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.AuthenticationEntry>() { // from class: android.credentials.selection.AuthenticationEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.AuthenticationEntry createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.AuthenticationEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.AuthenticationEntry[] newArray(int i) {
            return new android.credentials.selection.AuthenticationEntry[i];
        }
    };
    public static final int STATUS_LOCKED = 0;
    public static final int STATUS_UNLOCKED_BUT_EMPTY_LESS_RECENT = 1;
    public static final int STATUS_UNLOCKED_BUT_EMPTY_MOST_RECENT = 2;
    private android.content.Intent mFrameworkExtrasIntent;
    private final java.lang.String mKey;
    private final android.app.slice.Slice mSlice;
    private final int mStatus;
    private final java.lang.String mSubkey;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private AuthenticationEntry(android.os.Parcel parcel) {
        this.mKey = parcel.readString8();
        this.mSubkey = parcel.readString8();
        this.mStatus = parcel.readInt();
        this.mSlice = (android.app.slice.Slice) parcel.readTypedObject(android.app.slice.Slice.CREATOR);
        this.mFrameworkExtrasIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mKey);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSubkey);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSlice);
    }

    public AuthenticationEntry(java.lang.String str, java.lang.String str2, android.app.slice.Slice slice, int i, android.content.Intent intent) {
        this.mKey = str;
        this.mSubkey = str2;
        this.mSlice = slice;
        this.mStatus = i;
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

    public int getStatus() {
        return this.mStatus;
    }

    public android.content.Intent getFrameworkExtrasIntent() {
        return this.mFrameworkExtrasIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mKey);
        parcel.writeString8(this.mSubkey);
        parcel.writeInt(this.mStatus);
        parcel.writeTypedObject(this.mSlice, i);
        parcel.writeTypedObject(this.mFrameworkExtrasIntent, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
