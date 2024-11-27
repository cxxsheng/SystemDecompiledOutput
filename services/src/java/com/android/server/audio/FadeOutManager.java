package com.android.server.audio;

/* loaded from: classes.dex */
public final class FadeOutManager {
    private static final boolean DEBUG = false;
    public static final java.lang.String TAG = "AS.FadeOutManager";
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.audio.FadeOutManager.FadedOutApp> mUidToFadedAppsMap = new android.util.SparseArray<>();
    private final com.android.server.audio.FadeConfigurations mFadeConfigurations = new com.android.server.audio.FadeConfigurations();

    int setFadeManagerConfiguration(android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        int fadeManagerConfiguration2;
        synchronized (this.mLock) {
            fadeManagerConfiguration2 = this.mFadeConfigurations.setFadeManagerConfiguration(fadeManagerConfiguration);
        }
        return fadeManagerConfiguration2;
    }

    int clearFadeManagerConfiguration() {
        int clearFadeManagerConfiguration;
        synchronized (this.mLock) {
            clearFadeManagerConfiguration = this.mFadeConfigurations.clearFadeManagerConfiguration();
        }
        return clearFadeManagerConfiguration;
    }

    android.media.FadeManagerConfiguration getFadeManagerConfiguration() {
        return this.mFadeConfigurations.getFadeManagerConfiguration();
    }

    int setTransientFadeManagerConfiguration(android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        int transientFadeManagerConfiguration;
        synchronized (this.mLock) {
            transientFadeManagerConfiguration = this.mFadeConfigurations.setTransientFadeManagerConfiguration(fadeManagerConfiguration);
        }
        return transientFadeManagerConfiguration;
    }

    int clearTransientFadeManagerConfiguration() {
        int clearTransientFadeManagerConfiguration;
        synchronized (this.mLock) {
            clearTransientFadeManagerConfiguration = this.mFadeConfigurations.clearTransientFadeManagerConfiguration();
        }
        return clearTransientFadeManagerConfiguration;
    }

    boolean isFadeEnabled() {
        return this.mFadeConfigurations.isFadeEnabled();
    }

    boolean canCauseFadeOut(@android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester2) {
        return focusRequester.getAudioAttributes().getContentType() != 1 && (focusRequester2.getGrantFlags() & 2) == 0;
    }

    boolean canBeFadedOut(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        boolean isFadeable;
        synchronized (this.mLock) {
            isFadeable = this.mFadeConfigurations.isFadeable(audioPlaybackConfiguration.getAudioAttributes(), audioPlaybackConfiguration.getClientUid(), audioPlaybackConfiguration.getPlayerType());
        }
        return isFadeable;
    }

    long getFadeOutDurationOnFocusLossMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        long fadeOutDuration;
        synchronized (this.mLock) {
            fadeOutDuration = this.mFadeConfigurations.getFadeOutDuration(audioAttributes);
        }
        return fadeOutDuration;
    }

    long getFadeInDelayForOffendersMillis(@android.annotation.NonNull android.media.AudioAttributes audioAttributes) {
        long delayFadeInOffenders;
        synchronized (this.mLock) {
            delayFadeInOffenders = this.mFadeConfigurations.getDelayFadeInOffenders(audioAttributes);
        }
        return delayFadeInOffenders;
    }

    void fadeOutUid(int i, java.util.List<android.media.AudioPlaybackConfiguration> list) {
        android.util.Slog.i(TAG, "fadeOutUid() uid:" + i);
        synchronized (this.mLock) {
            try {
                if (!this.mUidToFadedAppsMap.contains(i)) {
                    this.mUidToFadedAppsMap.put(i, new com.android.server.audio.FadeOutManager.FadedOutApp(i));
                }
                com.android.server.audio.FadeOutManager.FadedOutApp fadedOutApp = this.mUidToFadedAppsMap.get(i);
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                    fadedOutApp.addFade(audioPlaybackConfiguration, false, this.mFadeConfigurations.getFadeOutVolumeShaperConfig(audioPlaybackConfiguration.getAudioAttributes()));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void unfadeOutUid(int i, java.util.Map<java.lang.Integer, android.media.AudioPlaybackConfiguration> map) {
        android.util.Slog.i(TAG, "unfadeOutUid() uid:" + i);
        synchronized (this.mLock) {
            try {
                com.android.server.audio.FadeOutManager.FadedOutApp fadedOutApp = this.mUidToFadedAppsMap.get(i);
                if (fadedOutApp == null) {
                    return;
                }
                this.mUidToFadedAppsMap.remove(i);
                if (!android.media.audiopolicy.Flags.enableFadeManagerConfiguration()) {
                    fadedOutApp.removeUnfadeAll(map);
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(map.values());
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = (android.media.AudioPlaybackConfiguration) arrayList.get(i2);
                    fadedOutApp.fadeInPlayer(audioPlaybackConfiguration, this.mFadeConfigurations.getFadeInVolumeShaperConfig(audioPlaybackConfiguration.getAudioAttributes()));
                }
                fadedOutApp.clear();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void checkFade(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        synchronized (this.mLock) {
            try {
                android.media.VolumeShaper.Configuration fadeOutVolumeShaperConfig = this.mFadeConfigurations.getFadeOutVolumeShaperConfig(audioPlaybackConfiguration.getAudioAttributes());
                com.android.server.audio.FadeOutManager.FadedOutApp fadedOutApp = this.mUidToFadedAppsMap.get(audioPlaybackConfiguration.getClientUid());
                if (fadedOutApp == null) {
                    return;
                }
                fadedOutApp.addFade(audioPlaybackConfiguration, true, fadeOutVolumeShaperConfig);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void removeReleased(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        int clientUid = audioPlaybackConfiguration.getClientUid();
        synchronized (this.mLock) {
            try {
                com.android.server.audio.FadeOutManager.FadedOutApp fadedOutApp = this.mUidToFadedAppsMap.get(clientUid);
                if (fadedOutApp == null) {
                    return;
                }
                fadedOutApp.removeReleased(audioPlaybackConfiguration);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean isUidFadedOut(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mUidToFadedAppsMap.contains(i);
        }
        return contains;
    }

    void dump(java.io.PrintWriter printWriter) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mUidToFadedAppsMap.size(); i++) {
                try {
                    this.mUidToFadedAppsMap.valueAt(i).dump(printWriter);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private static final class FadedOutApp {
        private static final android.media.VolumeShaper.Operation PLAY_CREATE_IF_NEEDED = new android.media.VolumeShaper.Operation.Builder(android.media.VolumeShaper.Operation.PLAY).createIfNeeded().build();
        private static final android.media.VolumeShaper.Operation PLAY_SKIP_RAMP = new android.media.VolumeShaper.Operation.Builder(PLAY_CREATE_IF_NEEDED).setXOffset(1.0f).build();
        private final android.util.SparseArray<android.media.VolumeShaper.Configuration> mFadedPlayers = new android.util.SparseArray<>();
        private final int mUid;

        FadedOutApp(int i) {
            this.mUid = i;
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.print("\t uid:" + this.mUid + " piids:");
            for (int i = 0; i < this.mFadedPlayers.size(); i++) {
                printWriter.print("piid: " + this.mFadedPlayers.keyAt(i) + " Volume shaper: " + this.mFadedPlayers.valueAt(i));
            }
            printWriter.println("");
        }

        void addFade(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, boolean z, @android.annotation.NonNull android.media.VolumeShaper.Configuration configuration) {
            int intValue = java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()).intValue();
            if (this.mFadedPlayers.indexOfKey(intValue) < 0 && audioPlaybackConfiguration.getPlayerProxy() != null) {
                applyVolumeShaperInternal(audioPlaybackConfiguration, intValue, configuration, z ? PLAY_SKIP_RAMP : PLAY_CREATE_IF_NEEDED, z, "fading out");
                this.mFadedPlayers.put(intValue, configuration);
            }
        }

        void removeUnfadeAll(java.util.Map<java.lang.Integer, android.media.AudioPlaybackConfiguration> map) {
            for (int i = 0; i < this.mFadedPlayers.size(); i++) {
                int keyAt = this.mFadedPlayers.keyAt(i);
                android.media.AudioPlaybackConfiguration audioPlaybackConfiguration = map.get(java.lang.Integer.valueOf(keyAt));
                if (audioPlaybackConfiguration != null && audioPlaybackConfiguration.getPlayerProxy() != null) {
                    applyVolumeShaperInternal(audioPlaybackConfiguration, keyAt, null, android.media.VolumeShaper.Operation.REVERSE, false, "fading in");
                }
            }
            this.mFadedPlayers.clear();
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void fadeInPlayer(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, @android.annotation.Nullable android.media.VolumeShaper.Configuration configuration) {
            android.media.VolumeShaper.Operation operation;
            int intValue = java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()).intValue();
            if (!this.mFadedPlayers.contains(intValue)) {
                return;
            }
            android.media.VolumeShaper.Operation operation2 = android.media.VolumeShaper.Operation.REVERSE;
            if (configuration == null) {
                operation = operation2;
            } else {
                operation = new android.media.VolumeShaper.Operation.Builder().replace(this.mFadedPlayers.get(intValue).getId(), true).build();
            }
            this.mFadedPlayers.remove(intValue);
            if (audioPlaybackConfiguration.getPlayerProxy() != null) {
                applyVolumeShaperInternal(audioPlaybackConfiguration, intValue, configuration, operation, false, "fading in");
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        void clear() {
            this.mFadedPlayers.size();
            this.mFadedPlayers.clear();
        }

        void removeReleased(@android.annotation.NonNull android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
            this.mFadedPlayers.delete(java.lang.Integer.valueOf(audioPlaybackConfiguration.getPlayerInterfaceId()).intValue());
        }

        private void applyVolumeShaperInternal(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation, boolean z, java.lang.String str) {
            android.media.VolumeShaper.Configuration configuration2;
            if (!operation.equals(android.media.VolumeShaper.Operation.REVERSE)) {
                configuration2 = configuration;
            } else {
                configuration2 = this.mFadedPlayers.get(i);
            }
            try {
                logFadeEvent(audioPlaybackConfiguration, i, configuration, operation, z, str);
                audioPlaybackConfiguration.getPlayerProxy().applyVolumeShaper(configuration2, operation);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.audio.FadeOutManager.TAG, "Error " + str + " piid:" + i + " uid:" + this.mUid, e);
            }
        }

        private void logFadeEvent(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration, int i, android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation, boolean z, java.lang.String str) {
            if (str.equals("fading out")) {
                com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.FadeOutEvent(audioPlaybackConfiguration, z, configuration, operation).printLog(com.android.server.audio.FadeOutManager.TAG));
                return;
            }
            if (str.equals("fading in")) {
                com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.audio.PlaybackActivityMonitor.FadeInEvent(audioPlaybackConfiguration, z, configuration, operation).printLog(com.android.server.audio.FadeOutManager.TAG));
                return;
            }
            com.android.server.audio.PlaybackActivityMonitor.sEventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str + " piid:" + i).printLog(com.android.server.audio.FadeOutManager.TAG));
        }
    }
}
