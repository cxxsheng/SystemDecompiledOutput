package android.app;

/* compiled from: FragmentManager.java */
/* loaded from: classes.dex */
final class FragmentManagerState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.FragmentManagerState> CREATOR = new android.os.Parcelable.Creator<android.app.FragmentManagerState>() { // from class: android.app.FragmentManagerState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.FragmentManagerState createFromParcel(android.os.Parcel parcel) {
            return new android.app.FragmentManagerState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.FragmentManagerState[] newArray(int i) {
            return new android.app.FragmentManagerState[i];
        }
    };
    android.app.FragmentState[] mActive;
    int[] mAdded;
    android.app.BackStackState[] mBackStack;
    int mNextFragmentIndex;
    int mPrimaryNavActiveIndex;

    public FragmentManagerState() {
        this.mPrimaryNavActiveIndex = -1;
    }

    public FragmentManagerState(android.os.Parcel parcel) {
        this.mPrimaryNavActiveIndex = -1;
        this.mActive = (android.app.FragmentState[]) parcel.createTypedArray(android.app.FragmentState.CREATOR);
        this.mAdded = parcel.createIntArray();
        this.mBackStack = (android.app.BackStackState[]) parcel.createTypedArray(android.app.BackStackState.CREATOR);
        this.mPrimaryNavActiveIndex = parcel.readInt();
        this.mNextFragmentIndex = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedArray(this.mActive, i);
        parcel.writeIntArray(this.mAdded);
        parcel.writeTypedArray(this.mBackStack, i);
        parcel.writeInt(this.mPrimaryNavActiveIndex);
        parcel.writeInt(this.mNextFragmentIndex);
    }
}
