package com.android.server.audio;

/* loaded from: classes.dex */
public final class FadeConfigurations {
    private static final boolean DEBUG = false;
    private static final long DEFAULT_DELAY_FADE_IN_OFFENDERS_MS = 2000;
    private static final long DEFAULT_FADE_OUT_DURATION_MS = 2000;
    private static final int INVALID_UID = -1;
    public static final java.lang.String TAG = "AS.FadeConfigurations";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration mActiveFadeManagerConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration mDefaultFadeManagerConfig;
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration mTransientFadeManagerConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration mUpdatedFadeManagerConfig;
    private static final java.util.List<java.lang.Integer> DEFAULT_UNFADEABLE_PLAYER_TYPES = java.util.List.of(13, 3);
    private static final java.util.List<java.lang.Integer> DEFAULT_UNFADEABLE_CONTENT_TYPES = java.util.List.of(1);
    private static final java.util.List<java.lang.Integer> DEFAULT_FADEABLE_USAGES = java.util.List.of(14, 1);
    private static final android.media.VolumeShaper.Configuration DEFAULT_FADEOUT_VSHAPE = new android.media.VolumeShaper.Configuration.Builder().setId(2).setCurve(new float[]{com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0.25f, 1.0f}, new float[]{1.0f, 0.65f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE}).setOptionFlags(2).setDuration(2000).build();

    public int setFadeManagerConfiguration(@android.annotation.NonNull android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return -1;
        }
        synchronized (this.mLock) {
            java.util.Objects.requireNonNull(fadeManagerConfiguration, "Fade manager configuration cannot be null");
            android.media.FadeManagerConfiguration fadeManagerConfiguration2 = fadeManagerConfiguration;
            this.mUpdatedFadeManagerConfig = fadeManagerConfiguration;
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return 0;
    }

    public int clearFadeManagerConfiguration() {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return -1;
        }
        synchronized (this.mLock) {
            this.mUpdatedFadeManagerConfig = null;
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return 0;
    }

    @android.annotation.Nullable
    public android.media.FadeManagerConfiguration getFadeManagerConfiguration() {
        android.media.FadeManagerConfiguration fadeManagerConfiguration;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return null;
        }
        synchronized (this.mLock) {
            fadeManagerConfiguration = this.mActiveFadeManagerConfig;
        }
        return fadeManagerConfiguration;
    }

    public int setTransientFadeManagerConfiguration(@android.annotation.NonNull android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return -1;
        }
        synchronized (this.mLock) {
            java.util.Objects.requireNonNull(fadeManagerConfiguration, "Transient FadeManagerConfiguration cannot be null");
            android.media.FadeManagerConfiguration fadeManagerConfiguration2 = fadeManagerConfiguration;
            this.mTransientFadeManagerConfig = fadeManagerConfiguration;
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return 0;
    }

    public int clearTransientFadeManagerConfiguration() {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return -1;
        }
        synchronized (this.mLock) {
            this.mTransientFadeManagerConfig = null;
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return 0;
    }

    public boolean isFadeEnabled() {
        boolean isFadeEnabled;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return true;
        }
        synchronized (this.mLock) {
            isFadeEnabled = getUpdatedFadeManagerConfigLocked().isFadeEnabled();
        }
        return isFadeEnabled;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getFadeableUsages() {
        java.util.List<java.lang.Integer> fadeableUsages;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_FADEABLE_USAGES;
        }
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                fadeableUsages = updatedFadeManagerConfigLocked.isFadeEnabled() ? updatedFadeManagerConfigLocked.getFadeableUsages() : java.util.Collections.EMPTY_LIST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return fadeableUsages;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getUnfadeableContentTypes() {
        java.util.List<java.lang.Integer> unfadeableContentTypes;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_UNFADEABLE_CONTENT_TYPES;
        }
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                unfadeableContentTypes = updatedFadeManagerConfigLocked.isFadeEnabled() ? updatedFadeManagerConfigLocked.getUnfadeableContentTypes() : java.util.Collections.EMPTY_LIST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return unfadeableContentTypes;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getUnfadeablePlayerTypes() {
        java.util.List<java.lang.Integer> unfadeablePlayerTypes;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_UNFADEABLE_PLAYER_TYPES;
        }
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                unfadeablePlayerTypes = updatedFadeManagerConfigLocked.isFadeEnabled() ? updatedFadeManagerConfigLocked.getUnfadeablePlayerTypes() : java.util.Collections.EMPTY_LIST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return unfadeablePlayerTypes;
    }

    @android.annotation.NonNull
    public android.media.VolumeShaper.Configuration getFadeOutVolumeShaperConfig(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_FADEOUT_VSHAPE;
        }
        return getOptimalFadeOutVolShaperConfig(audioAttributes);
    }

    @android.annotation.Nullable
    public android.media.VolumeShaper.Configuration getFadeInVolumeShaperConfig(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return null;
        }
        return getOptimalFadeInVolShaperConfig(audioAttributes);
    }

    public long getFadeOutDuration(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        if (!isFadeable(audioAttributes, -1, -1)) {
            return 0L;
        }
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return 2000L;
        }
        return getOptimalFadeOutDuration(audioAttributes);
    }

    public long getDelayFadeInOffenders(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        long fadeInDelayForOffenders;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return 2000L;
        }
        synchronized (this.mLock) {
            fadeInDelayForOffenders = getUpdatedFadeManagerConfigLocked().getFadeInDelayForOffenders();
        }
        return fadeInDelayForOffenders;
    }

    @android.annotation.NonNull
    public java.util.List<android.media.AudioAttributes> getUnfadeableAudioAttributes() {
        java.util.List<android.media.AudioAttributes> unfadeableAudioAttributes;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return java.util.Collections.EMPTY_LIST;
        }
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                unfadeableAudioAttributes = updatedFadeManagerConfigLocked.isFadeEnabled() ? updatedFadeManagerConfigLocked.getUnfadeableAudioAttributes() : java.util.Collections.EMPTY_LIST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return unfadeableAudioAttributes;
    }

    @android.annotation.NonNull
    public java.util.List<java.lang.Integer> getUnfadeableUids() {
        java.util.List<java.lang.Integer> unfadeableUids;
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return java.util.Collections.EMPTY_LIST;
        }
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                unfadeableUids = updatedFadeManagerConfigLocked.isFadeEnabled() ? updatedFadeManagerConfigLocked.getUnfadeableUids() : java.util.Collections.EMPTY_LIST;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return unfadeableUids;
    }

    public boolean isFadeable(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (isPlayerTypeUnfadeableLocked(i2)) {
                    return false;
                }
                if (isContentTypeUnfadeableLocked(audioAttributes.getContentType())) {
                    return false;
                }
                if (isUsageFadeableLocked(audioAttributes.getSystemUsage())) {
                    return !isUnfadeableForFadeMgrConfigLocked(audioAttributes, i);
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.media.VolumeShaper.Configuration getOptimalFadeOutVolShaperConfig(android.media.AudioAttributes audioAttributes) {
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                android.media.VolumeShaper.Configuration fadeOutVolumeShaperConfigForAudioAttributes = updatedFadeManagerConfigLocked.getFadeOutVolumeShaperConfigForAudioAttributes(audioAttributes);
                if (fadeOutVolumeShaperConfigForAudioAttributes != null) {
                    return fadeOutVolumeShaperConfigForAudioAttributes;
                }
                return updatedFadeManagerConfigLocked.getFadeOutVolumeShaperConfigForUsage(audioAttributes.getSystemUsage());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private android.media.VolumeShaper.Configuration getOptimalFadeInVolShaperConfig(android.media.AudioAttributes audioAttributes) {
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                android.media.VolumeShaper.Configuration fadeInVolumeShaperConfigForAudioAttributes = updatedFadeManagerConfigLocked.getFadeInVolumeShaperConfigForAudioAttributes(audioAttributes);
                if (fadeInVolumeShaperConfigForAudioAttributes != null) {
                    return fadeInVolumeShaperConfigForAudioAttributes;
                }
                return updatedFadeManagerConfigLocked.getFadeInVolumeShaperConfigForUsage(audioAttributes.getSystemUsage());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private long getOptimalFadeOutDuration(android.media.AudioAttributes audioAttributes) {
        synchronized (this.mLock) {
            try {
                android.media.FadeManagerConfiguration updatedFadeManagerConfigLocked = getUpdatedFadeManagerConfigLocked();
                long fadeOutDurationForAudioAttributes = updatedFadeManagerConfigLocked.getFadeOutDurationForAudioAttributes(audioAttributes);
                if (fadeOutDurationForAudioAttributes != 0) {
                    return fadeOutDurationForAudioAttributes;
                }
                return updatedFadeManagerConfigLocked.getFadeOutDurationForUsage(audioAttributes.getSystemUsage());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUnfadeableForFadeMgrConfigLocked(android.media.AudioAttributes audioAttributes, int i) {
        return isAudioAttributesUnfadeableLocked(audioAttributes) || isUidUnfadeableLocked(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUsageFadeableLocked(int i) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_FADEABLE_USAGES.contains(java.lang.Integer.valueOf(i));
        }
        return getUpdatedFadeManagerConfigLocked().isUsageFadeable(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isContentTypeUnfadeableLocked(int i) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_UNFADEABLE_CONTENT_TYPES.contains(java.lang.Integer.valueOf(i));
        }
        return getUpdatedFadeManagerConfigLocked().isContentTypeUnfadeable(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isPlayerTypeUnfadeableLocked(int i) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return DEFAULT_UNFADEABLE_PLAYER_TYPES.contains(java.lang.Integer.valueOf(i));
        }
        return getUpdatedFadeManagerConfigLocked().isPlayerTypeUnfadeable(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isAudioAttributesUnfadeableLocked(android.media.AudioAttributes audioAttributes) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return false;
        }
        return getUpdatedFadeManagerConfigLocked().isAudioAttributesUnfadeable(audioAttributes);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean isUidUnfadeableLocked(int i) {
        if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
            return false;
        }
        return getUpdatedFadeManagerConfigLocked().isUidUnfadeable(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration getUpdatedFadeManagerConfigLocked() {
        if (this.mActiveFadeManagerConfig == null) {
            this.mActiveFadeManagerConfig = getActiveFadeMgrConfigLocked();
        }
        return this.mActiveFadeManagerConfig;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration getActiveFadeMgrConfigLocked() {
        if (this.mTransientFadeManagerConfig != null) {
            return this.mTransientFadeManagerConfig;
        }
        if (this.mUpdatedFadeManagerConfig != null) {
            return this.mUpdatedFadeManagerConfig;
        }
        return getDefaultFadeManagerConfigLocked();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.media.FadeManagerConfiguration getDefaultFadeManagerConfigLocked() {
        if (this.mDefaultFadeManagerConfig == null) {
            this.mDefaultFadeManagerConfig = new android.media.FadeManagerConfiguration.Builder().build();
        }
        return this.mDefaultFadeManagerConfig;
    }
}
