package com.android.server.audio;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes.dex */
public final class AdiDeviceState {
    private static final java.lang.String SETTING_FIELD_SEPARATOR = ",";
    private static final java.lang.String TAG = "AS.AdiDeviceState";

    @android.annotation.NonNull
    private final java.lang.String mDeviceAddress;
    private final android.util.Pair<java.lang.Integer, java.lang.String> mDeviceId;
    private final int mDeviceType;
    private boolean mHeadTrackerEnabled;
    private final int mInternalDeviceType;
    private boolean mSAEnabled;
    private int mAudioDeviceCategory = 0;
    private boolean mAutoBtCategorySet = false;
    private boolean mHasHeadTracker = false;

    AdiDeviceState(int i, int i2, @android.annotation.Nullable java.lang.String str) {
        this.mDeviceType = i;
        if (i2 != 0) {
            this.mInternalDeviceType = i2;
        } else {
            this.mInternalDeviceType = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(i);
        }
        if (android.media.AudioSystem.isBluetoothDevice(this.mInternalDeviceType)) {
            java.util.Objects.requireNonNull(str);
        } else {
            str = "";
        }
        this.mDeviceAddress = str;
        this.mDeviceId = new android.util.Pair<>(java.lang.Integer.valueOf(this.mInternalDeviceType), this.mDeviceAddress);
    }

    public synchronized android.util.Pair<java.lang.Integer, java.lang.String> getDeviceId() {
        return this.mDeviceId;
    }

    public synchronized int getDeviceType() {
        return this.mDeviceType;
    }

    public synchronized int getInternalDeviceType() {
        return this.mInternalDeviceType;
    }

    @android.annotation.NonNull
    public synchronized java.lang.String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public synchronized void setSAEnabled(boolean z) {
        this.mSAEnabled = z;
    }

    public synchronized boolean isSAEnabled() {
        return this.mSAEnabled;
    }

    public synchronized void setHeadTrackerEnabled(boolean z) {
        this.mHeadTrackerEnabled = z;
    }

    public synchronized boolean isHeadTrackerEnabled() {
        return this.mHeadTrackerEnabled;
    }

    public synchronized void setHasHeadTracker(boolean z) {
        this.mHasHeadTracker = z;
    }

    public synchronized boolean hasHeadTracker() {
        return this.mHasHeadTracker;
    }

    public synchronized int getAudioDeviceCategory() {
        return this.mAudioDeviceCategory;
    }

    public synchronized void setAudioDeviceCategory(int i) {
        this.mAudioDeviceCategory = i;
    }

    public synchronized boolean isBtDeviceCategoryFixed() {
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        updateAudioDeviceCategory();
        return this.mAutoBtCategorySet;
    }

    public synchronized boolean updateAudioDeviceCategory() {
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        if (!android.media.AudioSystem.isBluetoothDevice(this.mInternalDeviceType)) {
            return false;
        }
        if (this.mAutoBtCategorySet) {
            return false;
        }
        int btDeviceCategory = com.android.server.audio.BtHelper.getBtDeviceCategory(this.mDeviceAddress);
        if (btDeviceCategory == 0) {
            return false;
        }
        this.mAudioDeviceCategory = btDeviceCategory;
        this.mAutoBtCategorySet = true;
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || com.android.server.audio.AdiDeviceState.class != obj.getClass()) {
            return false;
        }
        com.android.server.audio.AdiDeviceState adiDeviceState = (com.android.server.audio.AdiDeviceState) obj;
        if (this.mDeviceType == adiDeviceState.mDeviceType && this.mInternalDeviceType == adiDeviceState.mInternalDeviceType && this.mDeviceAddress.equals(adiDeviceState.mDeviceAddress) && this.mSAEnabled == adiDeviceState.mSAEnabled && this.mHasHeadTracker == adiDeviceState.mHasHeadTracker && this.mHeadTrackerEnabled == adiDeviceState.mHeadTrackerEnabled && this.mAudioDeviceCategory == adiDeviceState.mAudioDeviceCategory) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDeviceType), java.lang.Integer.valueOf(this.mInternalDeviceType), this.mDeviceAddress, java.lang.Boolean.valueOf(this.mSAEnabled), java.lang.Boolean.valueOf(this.mHasHeadTracker), java.lang.Boolean.valueOf(this.mHeadTrackerEnabled), java.lang.Integer.valueOf(this.mAudioDeviceCategory));
    }

    public java.lang.String toString() {
        return "type: " + this.mDeviceType + " internal type: 0x" + java.lang.Integer.toHexString(this.mInternalDeviceType) + " addr: " + android.media.Utils.anonymizeBluetoothAddress(this.mInternalDeviceType, this.mDeviceAddress) + " bt audio type: " + android.media.AudioManager.audioDeviceCategoryToString(this.mAudioDeviceCategory) + " enabled: " + this.mSAEnabled + " HT: " + this.mHasHeadTracker + " HTenabled: " + this.mHeadTrackerEnabled;
    }

    public synchronized java.lang.String toPersistableString() {
        java.lang.StringBuilder sb;
        try {
            sb = new java.lang.StringBuilder();
            sb.append(this.mDeviceType);
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mDeviceAddress);
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mSAEnabled ? "1" : "0");
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mHasHeadTracker ? "1" : "0");
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mHeadTrackerEnabled ? "1" : "0");
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mInternalDeviceType);
            sb.append(SETTING_FIELD_SEPARATOR);
            sb.append(this.mAudioDeviceCategory);
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return sb.toString();
    }

    public static int getPeristedMaxSize() {
        return 39;
    }

    @android.annotation.Nullable
    public static com.android.server.audio.AdiDeviceState fromPersistedString(@android.annotation.Nullable java.lang.String str) {
        int i;
        int i2;
        if (str == null || str.isEmpty()) {
            return null;
        }
        java.lang.String[] split = android.text.TextUtils.split(str, SETTING_FIELD_SEPARATOR);
        if (split.length < 5 || split.length > 7) {
            return null;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(split[0]);
            if (split.length < 6) {
                i = -1;
            } else {
                i = java.lang.Integer.parseInt(split[5]);
            }
            if (split.length != 7) {
                i2 = 0;
            } else {
                i2 = java.lang.Integer.parseInt(split[6]);
            }
            com.android.server.audio.AdiDeviceState adiDeviceState = new com.android.server.audio.AdiDeviceState(parseInt, i, split[1]);
            adiDeviceState.setSAEnabled(java.lang.Integer.parseInt(split[2]) == 1);
            adiDeviceState.setHasHeadTracker(java.lang.Integer.parseInt(split[3]) == 1);
            adiDeviceState.setHeadTrackerEnabled(java.lang.Integer.parseInt(split[4]) == 1);
            adiDeviceState.setAudioDeviceCategory(i2);
            adiDeviceState.updateAudioDeviceCategory();
            return adiDeviceState;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "unable to parse setting for AdiDeviceState: " + str, e);
            return null;
        }
    }

    public synchronized android.media.AudioDeviceAttributes getAudioDeviceAttributes() {
        return new android.media.AudioDeviceAttributes(2, this.mDeviceType, this.mDeviceAddress);
    }
}
