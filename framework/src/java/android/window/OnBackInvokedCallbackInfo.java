package android.window;

/* loaded from: classes4.dex */
public final class OnBackInvokedCallbackInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.OnBackInvokedCallbackInfo> CREATOR = new android.os.Parcelable.Creator<android.window.OnBackInvokedCallbackInfo>() { // from class: android.window.OnBackInvokedCallbackInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.OnBackInvokedCallbackInfo createFromParcel(android.os.Parcel parcel) {
            return new android.window.OnBackInvokedCallbackInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.OnBackInvokedCallbackInfo[] newArray(int i) {
            return new android.window.OnBackInvokedCallbackInfo[i];
        }
    };
    private final android.window.IOnBackInvokedCallback mCallback;
    private final boolean mIsAnimationCallback;
    private int mPriority;

    public OnBackInvokedCallbackInfo(android.window.IOnBackInvokedCallback iOnBackInvokedCallback, int i, boolean z) {
        this.mCallback = iOnBackInvokedCallback;
        this.mPriority = i;
        this.mIsAnimationCallback = z;
    }

    private OnBackInvokedCallbackInfo(android.os.Parcel parcel) {
        this.mCallback = android.window.IOnBackInvokedCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mPriority = parcel.readInt();
        this.mIsAnimationCallback = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mCallback);
        parcel.writeInt(this.mPriority);
        parcel.writeBoolean(this.mIsAnimationCallback);
    }

    public boolean isSystemCallback() {
        return this.mPriority == -1;
    }

    public android.window.IOnBackInvokedCallback getCallback() {
        return this.mCallback;
    }

    public int getPriority() {
        return this.mPriority;
    }

    public boolean isAnimationCallback() {
        return this.mIsAnimationCallback;
    }

    public java.lang.String toString() {
        return "OnBackInvokedCallbackInfo{mCallback=" + this.mCallback + ", mPriority=" + this.mPriority + ", mIsAnimationCallback=" + this.mIsAnimationCallback + '}';
    }
}
