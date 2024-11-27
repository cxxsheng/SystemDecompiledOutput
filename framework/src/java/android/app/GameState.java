package android.app;

/* loaded from: classes.dex */
public final class GameState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.GameState> CREATOR = new android.os.Parcelable.Creator<android.app.GameState>() { // from class: android.app.GameState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameState createFromParcel(android.os.Parcel parcel) {
            return new android.app.GameState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameState[] newArray(int i) {
            return new android.app.GameState[i];
        }
    };
    public static final int MODE_CONTENT = 4;
    public static final int MODE_GAMEPLAY_INTERRUPTIBLE = 2;
    public static final int MODE_GAMEPLAY_UNINTERRUPTIBLE = 3;
    public static final int MODE_NONE = 1;
    public static final int MODE_UNKNOWN = 0;
    private final boolean mIsLoading;
    private final int mLabel;
    private final int mMode;
    private final int mQuality;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface GameStateMode {
    }

    public GameState(boolean z, int i) {
        this(z, i, -1, -1);
    }

    public GameState(boolean z, int i, int i2, int i3) {
        this.mIsLoading = z;
        this.mMode = i;
        this.mLabel = i2;
        this.mQuality = i3;
    }

    private GameState(android.os.Parcel parcel) {
        this.mIsLoading = parcel.readBoolean();
        this.mMode = parcel.readInt();
        this.mLabel = parcel.readInt();
        this.mQuality = parcel.readInt();
    }

    public boolean isLoading() {
        return this.mIsLoading;
    }

    public int getMode() {
        return this.mMode;
    }

    public int getLabel() {
        return this.mLabel;
    }

    public int getQuality() {
        return this.mQuality;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mIsLoading);
        parcel.writeInt(this.mMode);
        parcel.writeInt(this.mLabel);
        parcel.writeInt(this.mQuality);
    }
}
