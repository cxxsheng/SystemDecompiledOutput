package android.window;

/* loaded from: classes4.dex */
public class BackAnimationAdapter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.BackAnimationAdapter> CREATOR = new android.os.Parcelable.Creator<android.window.BackAnimationAdapter>() { // from class: android.window.BackAnimationAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackAnimationAdapter createFromParcel(android.os.Parcel parcel) {
            return new android.window.BackAnimationAdapter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.BackAnimationAdapter[] newArray(int i) {
            return new android.window.BackAnimationAdapter[i];
        }
    };
    private final android.window.IBackAnimationRunner mRunner;

    public BackAnimationAdapter(android.window.IBackAnimationRunner iBackAnimationRunner) {
        this.mRunner = iBackAnimationRunner;
    }

    public BackAnimationAdapter(android.os.Parcel parcel) {
        this.mRunner = android.window.IBackAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
    }

    public android.window.IBackAnimationRunner getRunner() {
        return this.mRunner;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRunner);
    }
}
