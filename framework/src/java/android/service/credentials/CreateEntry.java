package android.service.credentials;

/* loaded from: classes3.dex */
public final class CreateEntry implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.CreateEntry> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.CreateEntry>() { // from class: android.service.credentials.CreateEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CreateEntry createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.CreateEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CreateEntry[] newArray(int i) {
            return new android.service.credentials.CreateEntry[i];
        }
    };
    private final android.app.slice.Slice mSlice;

    private CreateEntry(android.os.Parcel parcel) {
        this.mSlice = (android.app.slice.Slice) parcel.readTypedObject(android.app.slice.Slice.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mSlice, i);
    }

    public CreateEntry(android.app.slice.Slice slice) {
        this.mSlice = slice;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSlice);
    }

    public android.app.slice.Slice getSlice() {
        return this.mSlice;
    }
}
