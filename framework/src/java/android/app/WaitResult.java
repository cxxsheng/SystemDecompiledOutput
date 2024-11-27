package android.app;

/* loaded from: classes.dex */
public class WaitResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.WaitResult> CREATOR = new android.os.Parcelable.Creator<android.app.WaitResult>() { // from class: android.app.WaitResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WaitResult createFromParcel(android.os.Parcel parcel) {
            return new android.app.WaitResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.WaitResult[] newArray(int i) {
            return new android.app.WaitResult[i];
        }
    };
    public static final int INVALID_DELAY = -1;
    public static final int LAUNCH_STATE_COLD = 1;
    public static final int LAUNCH_STATE_HOT = 3;
    public static final int LAUNCH_STATE_RELAUNCH = 4;
    public static final int LAUNCH_STATE_UNKNOWN = 0;
    public static final int LAUNCH_STATE_WARM = 2;
    public int launchState;
    public int result;
    public boolean timeout;
    public long totalTime;
    public android.content.ComponentName who;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LaunchState {
    }

    public WaitResult() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeInt(this.timeout ? 1 : 0);
        android.content.ComponentName.writeToParcel(this.who, parcel);
        parcel.writeLong(this.totalTime);
        parcel.writeInt(this.launchState);
    }

    private WaitResult(android.os.Parcel parcel) {
        this.result = parcel.readInt();
        this.timeout = parcel.readInt() != 0;
        this.who = android.content.ComponentName.readFromParcel(parcel);
        this.totalTime = parcel.readLong();
        this.launchState = parcel.readInt();
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "WaitResult:");
        printWriter.println(str + "  result=" + this.result);
        printWriter.println(str + "  timeout=" + this.timeout);
        printWriter.println(str + "  who=" + this.who);
        printWriter.println(str + "  totalTime=" + this.totalTime);
        printWriter.println(str + "  launchState=" + this.launchState);
    }

    public static java.lang.String launchStateToString(int i) {
        switch (i) {
            case 1:
                return "COLD";
            case 2:
                return "WARM";
            case 3:
                return "HOT";
            case 4:
                return "RELAUNCH";
            default:
                return "UNKNOWN (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
