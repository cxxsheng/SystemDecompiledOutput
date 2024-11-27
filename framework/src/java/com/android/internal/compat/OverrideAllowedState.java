package com.android.internal.compat;

/* loaded from: classes4.dex */
public final class OverrideAllowedState implements android.os.Parcelable {
    public static final int ALLOWED = 0;
    public static final android.os.Parcelable.Creator<com.android.internal.compat.OverrideAllowedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.compat.OverrideAllowedState>() { // from class: com.android.internal.compat.OverrideAllowedState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.OverrideAllowedState createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.compat.OverrideAllowedState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.compat.OverrideAllowedState[] newArray(int i) {
            return new com.android.internal.compat.OverrideAllowedState[i];
        }
    };
    public static final int DEFERRED_VERIFICATION = 4;
    public static final int DISABLED_NON_TARGET_SDK = 2;
    public static final int DISABLED_NOT_DEBUGGABLE = 1;
    public static final int DISABLED_TARGET_SDK_TOO_HIGH = 3;
    public static final int LOGGING_ONLY_CHANGE = 5;
    public static final int PLATFORM_TOO_OLD = 6;
    public final int appTargetSdk;
    public final int changeIdTargetSdk;
    public final int state;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    private OverrideAllowedState(android.os.Parcel parcel) {
        this.state = parcel.readInt();
        this.appTargetSdk = parcel.readInt();
        this.changeIdTargetSdk = parcel.readInt();
    }

    public OverrideAllowedState(int i, int i2, int i3) {
        this.state = i;
        this.appTargetSdk = i2;
        this.changeIdTargetSdk = i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.state);
        parcel.writeInt(this.appTargetSdk);
        parcel.writeInt(this.changeIdTargetSdk);
    }

    public void enforce(long j, java.lang.String str) throws java.lang.SecurityException {
        switch (this.state) {
            case 0:
            case 4:
                return;
            case 1:
                throw new java.lang.SecurityException("Cannot override a change on a non-debuggable app and user build.");
            case 2:
                throw new java.lang.SecurityException("Cannot override a default enabled/disabled change on a user build.");
            case 3:
                throw new java.lang.SecurityException(java.lang.String.format("Cannot override %1$d for %2$s because the app's targetSdk (%3$d) is above the change's targetSdk threshold (%4$d)", java.lang.Long.valueOf(j), str, java.lang.Integer.valueOf(this.appTargetSdk), java.lang.Integer.valueOf(this.changeIdTargetSdk)));
            case 5:
                throw new java.lang.SecurityException(java.lang.String.format("Cannot override %1$d because it is marked as a logging-only change.", java.lang.Long.valueOf(j)));
            case 6:
                throw new java.lang.SecurityException(java.lang.String.format("Cannot override %1$d for %2$s because the change's targetSdk threshold (%3$d) is above the platform sdk.", java.lang.Long.valueOf(j), str, java.lang.Integer.valueOf(this.changeIdTargetSdk)));
            default:
                return;
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof com.android.internal.compat.OverrideAllowedState)) {
            return false;
        }
        com.android.internal.compat.OverrideAllowedState overrideAllowedState = (com.android.internal.compat.OverrideAllowedState) obj;
        if (this.state == overrideAllowedState.state && this.appTargetSdk == overrideAllowedState.appTargetSdk && this.changeIdTargetSdk == overrideAllowedState.changeIdTargetSdk) {
            return true;
        }
        return false;
    }

    private java.lang.String stateName() {
        switch (this.state) {
            case 0:
                return "ALLOWED";
            case 1:
                return "DISABLED_NOT_DEBUGGABLE";
            case 2:
                return "DISABLED_NON_TARGET_SDK";
            case 3:
                return "DISABLED_TARGET_SDK_TOO_HIGH";
            case 4:
                return "DEFERRED_VERIFICATION";
            case 5:
                return "LOGGING_ONLY_CHANGE";
            case 6:
                return "PLATFORM_TOO_OLD";
            default:
                return "UNKNOWN";
        }
    }

    public java.lang.String toString() {
        return "OverrideAllowedState(state=" + stateName() + "; appTargetSdk=" + this.appTargetSdk + "; changeIdTargetSdk=" + this.changeIdTargetSdk + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
