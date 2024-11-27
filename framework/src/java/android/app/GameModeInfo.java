package android.app;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class GameModeInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.GameModeInfo> CREATOR = new android.os.Parcelable.Creator<android.app.GameModeInfo>() { // from class: android.app.GameModeInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameModeInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.GameModeInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.GameModeInfo[] newArray(int i) {
            return new android.app.GameModeInfo[i];
        }
    };
    private final int mActiveGameMode;
    private final int[] mAvailableGameModes;
    private final java.util.Map<java.lang.Integer, android.app.GameModeConfiguration> mConfigMap;
    private final boolean mIsDownscalingAllowed;
    private final boolean mIsFpsOverrideAllowed;
    private final int[] mOverriddenGameModes;

    @android.annotation.SystemApi
    public static final class Builder {
        private int mActiveGameMode;
        private boolean mIsDownscalingAllowed;
        private boolean mIsFpsOverrideAllowed;
        private int[] mAvailableGameModes = new int[0];
        private int[] mOverriddenGameModes = new int[0];
        private java.util.Map<java.lang.Integer, android.app.GameModeConfiguration> mConfigMap = new android.util.ArrayMap();

        public android.app.GameModeInfo.Builder setAvailableGameModes(int[] iArr) {
            this.mAvailableGameModes = iArr;
            return this;
        }

        public android.app.GameModeInfo.Builder setOverriddenGameModes(int[] iArr) {
            this.mOverriddenGameModes = iArr;
            return this;
        }

        public android.app.GameModeInfo.Builder setActiveGameMode(int i) {
            this.mActiveGameMode = i;
            return this;
        }

        public android.app.GameModeInfo.Builder setDownscalingAllowed(boolean z) {
            this.mIsDownscalingAllowed = z;
            return this;
        }

        public android.app.GameModeInfo.Builder setFpsOverrideAllowed(boolean z) {
            this.mIsFpsOverrideAllowed = z;
            return this;
        }

        public android.app.GameModeInfo.Builder setGameModeConfiguration(int i, android.app.GameModeConfiguration gameModeConfiguration) {
            this.mConfigMap.put(java.lang.Integer.valueOf(i), gameModeConfiguration);
            return this;
        }

        public android.app.GameModeInfo build() {
            return new android.app.GameModeInfo(this.mActiveGameMode, this.mAvailableGameModes, this.mOverriddenGameModes, this.mIsDownscalingAllowed, this.mIsFpsOverrideAllowed, this.mConfigMap);
        }
    }

    public GameModeInfo(int i, int[] iArr) {
        this(i, iArr, new int[0], true, true, new android.util.ArrayMap());
    }

    private GameModeInfo(int i, int[] iArr, int[] iArr2, boolean z, boolean z2, java.util.Map<java.lang.Integer, android.app.GameModeConfiguration> map) {
        this.mActiveGameMode = i;
        this.mAvailableGameModes = java.util.Arrays.copyOf(iArr, iArr.length);
        this.mOverriddenGameModes = java.util.Arrays.copyOf(iArr2, iArr2.length);
        this.mIsDownscalingAllowed = z;
        this.mIsFpsOverrideAllowed = z2;
        this.mConfigMap = map;
    }

    public GameModeInfo(android.os.Parcel parcel) {
        this.mActiveGameMode = parcel.readInt();
        this.mAvailableGameModes = parcel.createIntArray();
        this.mOverriddenGameModes = parcel.createIntArray();
        this.mIsDownscalingAllowed = parcel.readBoolean();
        this.mIsFpsOverrideAllowed = parcel.readBoolean();
        this.mConfigMap = new android.util.ArrayMap();
        parcel.readMap(this.mConfigMap, getClass().getClassLoader(), java.lang.Integer.class, android.app.GameModeConfiguration.class);
    }

    public int getActiveGameMode() {
        return this.mActiveGameMode;
    }

    public int[] getAvailableGameModes() {
        return java.util.Arrays.copyOf(this.mAvailableGameModes, this.mAvailableGameModes.length);
    }

    public int[] getOverriddenGameModes() {
        return java.util.Arrays.copyOf(this.mOverriddenGameModes, this.mOverriddenGameModes.length);
    }

    public android.app.GameModeConfiguration getGameModeConfiguration(int i) {
        return this.mConfigMap.get(java.lang.Integer.valueOf(i));
    }

    public boolean isDownscalingAllowed() {
        return this.mIsDownscalingAllowed;
    }

    public boolean isFpsOverrideAllowed() {
        return this.mIsFpsOverrideAllowed;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mActiveGameMode);
        parcel.writeIntArray(this.mAvailableGameModes);
        parcel.writeIntArray(this.mOverriddenGameModes);
        parcel.writeBoolean(this.mIsDownscalingAllowed);
        parcel.writeBoolean(this.mIsFpsOverrideAllowed);
        parcel.writeMap(this.mConfigMap);
    }
}
