package android.service.credentials;

/* loaded from: classes3.dex */
public final class Action implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.Action> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.Action>() { // from class: android.service.credentials.Action.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.Action createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.Action(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.Action[] newArray(int i) {
            return new android.service.credentials.Action[i];
        }
    };
    private final android.app.slice.Slice mSlice;

    public Action(android.app.slice.Slice slice) {
        java.util.Objects.requireNonNull(slice, "slice must not be null");
        this.mSlice = slice;
    }

    private Action(android.os.Parcel parcel) {
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

    public android.app.slice.Slice getSlice() {
        return this.mSlice;
    }
}
