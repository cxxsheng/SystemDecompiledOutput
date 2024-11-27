package android.service.games;

/* loaded from: classes3.dex */
public final class GameScreenshotResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.games.GameScreenshotResult> CREATOR = new android.os.Parcelable.Creator<android.service.games.GameScreenshotResult>() { // from class: android.service.games.GameScreenshotResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameScreenshotResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.games.GameScreenshotResult(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.games.GameScreenshotResult[] newArray(int i) {
            return new android.service.games.GameScreenshotResult[0];
        }
    };
    public static final int GAME_SCREENSHOT_ERROR_INTERNAL_ERROR = 1;
    public static final int GAME_SCREENSHOT_SUCCESS = 0;
    private final int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GameScreenshotStatus {
    }

    public static android.service.games.GameScreenshotResult createSuccessResult() {
        return new android.service.games.GameScreenshotResult(0);
    }

    public static android.service.games.GameScreenshotResult createInternalErrorResult() {
        return new android.service.games.GameScreenshotResult(1);
    }

    private GameScreenshotResult(int i) {
        this.mStatus = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
    }

    public int getStatus() {
        return this.mStatus;
    }

    public java.lang.String toString() {
        return "GameScreenshotResult{mStatus=" + this.mStatus + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.service.games.GameScreenshotResult) && this.mStatus == ((android.service.games.GameScreenshotResult) obj).mStatus;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStatus));
    }
}
