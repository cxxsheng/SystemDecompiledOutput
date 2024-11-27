package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppTargetEvent implements android.os.Parcelable {
    public static final int ACTION_DISMISS = 2;
    public static final int ACTION_LAUNCH = 1;
    public static final int ACTION_PIN = 3;
    public static final int ACTION_UNDISMISS = 5;
    public static final int ACTION_UNPIN = 4;
    public static final android.os.Parcelable.Creator<android.app.prediction.AppTargetEvent> CREATOR = new android.os.Parcelable.Creator<android.app.prediction.AppTargetEvent>() { // from class: android.app.prediction.AppTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTargetEvent createFromParcel(android.os.Parcel parcel) {
            return new android.app.prediction.AppTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTargetEvent[] newArray(int i) {
            return new android.app.prediction.AppTargetEvent[i];
        }
    };
    private final int mAction;
    private final java.lang.String mLocation;
    private final android.app.prediction.AppTarget mTarget;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    private AppTargetEvent(android.app.prediction.AppTarget appTarget, java.lang.String str, int i) {
        this.mTarget = appTarget;
        this.mLocation = str;
        this.mAction = i;
    }

    private AppTargetEvent(android.os.Parcel parcel) {
        this.mTarget = (android.app.prediction.AppTarget) parcel.readParcelable(null, android.app.prediction.AppTarget.class);
        this.mLocation = parcel.readString();
        this.mAction = parcel.readInt();
    }

    public android.app.prediction.AppTarget getTarget() {
        return this.mTarget;
    }

    public java.lang.String getLaunchLocation() {
        return this.mLocation;
    }

    public int getAction() {
        return this.mAction;
    }

    public boolean equals(java.lang.Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.prediction.AppTargetEvent appTargetEvent = (android.app.prediction.AppTargetEvent) obj;
        return this.mTarget.equals(appTargetEvent.mTarget) && this.mLocation.equals(appTargetEvent.mLocation) && this.mAction == appTargetEvent.mAction;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mTarget, 0);
        parcel.writeString(this.mLocation);
        parcel.writeInt(this.mAction);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private int mAction;
        private java.lang.String mLocation;
        private android.app.prediction.AppTarget mTarget;

        public Builder(android.app.prediction.AppTarget appTarget, int i) {
            this.mTarget = appTarget;
            this.mAction = i;
        }

        public android.app.prediction.AppTargetEvent.Builder setLaunchLocation(java.lang.String str) {
            this.mLocation = str;
            return this;
        }

        public android.app.prediction.AppTargetEvent build() {
            return new android.app.prediction.AppTargetEvent(this.mTarget, this.mLocation, this.mAction);
        }
    }
}
