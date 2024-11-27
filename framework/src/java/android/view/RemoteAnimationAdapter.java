package android.view;

/* loaded from: classes4.dex */
public class RemoteAnimationAdapter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.RemoteAnimationAdapter> CREATOR = new android.os.Parcelable.Creator<android.view.RemoteAnimationAdapter>() { // from class: android.view.RemoteAnimationAdapter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationAdapter createFromParcel(android.os.Parcel parcel) {
            return new android.view.RemoteAnimationAdapter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.RemoteAnimationAdapter[] newArray(int i) {
            return new android.view.RemoteAnimationAdapter[i];
        }
    };
    private android.app.IApplicationThread mCallingApplication;
    private int mCallingPid;
    private int mCallingUid;
    private final boolean mChangeNeedsSnapshot;
    private final long mDuration;
    private final android.view.IRemoteAnimationRunner mRunner;
    private final long mStatusBarTransitionDelay;

    public RemoteAnimationAdapter(android.view.IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2, boolean z) {
        this.mRunner = iRemoteAnimationRunner;
        this.mDuration = j;
        this.mChangeNeedsSnapshot = z;
        this.mStatusBarTransitionDelay = j2;
    }

    public RemoteAnimationAdapter(android.view.IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2) {
        this(iRemoteAnimationRunner, j, j2, false);
    }

    public RemoteAnimationAdapter(android.view.IRemoteAnimationRunner iRemoteAnimationRunner, long j, long j2, android.app.IApplicationThread iApplicationThread) {
        this(iRemoteAnimationRunner, j, j2, false);
        this.mCallingApplication = iApplicationThread;
    }

    public RemoteAnimationAdapter(android.os.Parcel parcel) {
        this.mRunner = android.view.IRemoteAnimationRunner.Stub.asInterface(parcel.readStrongBinder());
        this.mDuration = parcel.readLong();
        this.mStatusBarTransitionDelay = parcel.readLong();
        this.mChangeNeedsSnapshot = parcel.readBoolean();
        this.mCallingApplication = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
    }

    public android.view.IRemoteAnimationRunner getRunner() {
        return this.mRunner;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public long getStatusBarTransitionDelay() {
        return this.mStatusBarTransitionDelay;
    }

    public boolean getChangeNeedsSnapshot() {
        return this.mChangeNeedsSnapshot;
    }

    public void setCallingPidUid(int i, int i2) {
        this.mCallingPid = i;
        this.mCallingUid = i2;
    }

    public int getCallingPid() {
        return this.mCallingPid;
    }

    public int getCallingUid() {
        return this.mCallingUid;
    }

    public android.app.IApplicationThread getCallingApplication() {
        return this.mCallingApplication;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongInterface(this.mRunner);
        parcel.writeLong(this.mDuration);
        parcel.writeLong(this.mStatusBarTransitionDelay);
        parcel.writeBoolean(this.mChangeNeedsSnapshot);
        parcel.writeStrongInterface(this.mCallingApplication);
    }
}
