package com.android.server.media.metrics;

/* loaded from: classes2.dex */
public final class MediaMetricsManagerService extends com.android.server.SystemService {
    private static final java.lang.String AUDIO_MIME_TYPE_PREFIX = "audio/";
    private static final int DURATION_BUCKETS_BELOW_ONE_MINUTE = 8;
    private static final int DURATION_BUCKETS_COUNT = 13;
    private static final java.lang.String FAILED_TO_GET = "failed_to_get";
    private static final int LOGGING_LEVEL_BLOCKED = 99999;
    private static final int LOGGING_LEVEL_EVERYTHING = 0;
    private static final int LOGGING_LEVEL_NO_UID = 1000;
    private static final java.lang.String MEDIA_METRICS_MODE = "media_metrics_mode";
    private static final int MEDIA_METRICS_MODE_ALLOWLIST = 3;
    private static final int MEDIA_METRICS_MODE_BLOCKLIST = 2;
    private static final int MEDIA_METRICS_MODE_OFF = 0;
    private static final int MEDIA_METRICS_MODE_ON = 1;
    private static final java.lang.String PLAYER_METRICS_APP_ALLOWLIST = "player_metrics_app_allowlist";
    private static final java.lang.String PLAYER_METRICS_APP_BLOCKLIST = "player_metrics_app_blocklist";
    private static final java.lang.String PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST = "player_metrics_per_app_attribution_allowlist";
    private static final java.lang.String PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST = "player_metrics_per_app_attribution_blocklist";
    private static final java.lang.String TAG = "MediaMetricsManagerService";
    private static final java.lang.String VIDEO_MIME_TYPE_PREFIX = "video/";
    private static final java.lang.String mMetricsId = "metrics.manager";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.String> mAllowlist;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.String> mBlockList;
    private final android.content.Context mContext;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.Integer mMode;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.String> mNoUidAllowlist;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.util.List<java.lang.String> mNoUidBlocklist;
    private final java.security.SecureRandom mSecureRandom;
    private static final android.media.metrics.MediaItemInfo EMPTY_MEDIA_ITEM_INFO = new android.media.metrics.MediaItemInfo.Builder().build();
    private static final java.util.regex.Pattern PATTERN_KNOWN_EDITING_LIBRARY_NAMES = java.util.regex.Pattern.compile("androidx\\.media3:media3-(transformer|muxer):[\\d.]+(-(alpha|beta|rc)\\d\\d)?");

    public MediaMetricsManagerService(android.content.Context context) {
        super(context);
        this.mMode = null;
        this.mAllowlist = null;
        this.mNoUidAllowlist = null;
        this.mBlockList = null;
        this.mNoUidBlocklist = null;
        this.mLock = new java.lang.Object();
        this.mContext = context;
        this.mSecureRandom = new java.security.SecureRandom();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("media_metrics", new com.android.server.media.metrics.MediaMetricsManagerService.BinderService());
        android.provider.DeviceConfig.addOnPropertiesChangedListener("media", this.mContext.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.media.metrics.MediaMetricsManagerService$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.media.metrics.MediaMetricsManagerService.this.updateConfigs(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConfigs(android.provider.DeviceConfig.Properties properties) {
        synchronized (this.mLock) {
            try {
                this.mMode = java.lang.Integer.valueOf(properties.getInt(MEDIA_METRICS_MODE, 2));
                java.util.List<java.lang.String> listLocked = getListLocked(PLAYER_METRICS_APP_ALLOWLIST);
                if (listLocked != null || this.mMode.intValue() != 3) {
                    this.mAllowlist = listLocked;
                }
                java.util.List<java.lang.String> listLocked2 = getListLocked(PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST);
                if (listLocked2 != null || this.mMode.intValue() != 3) {
                    this.mNoUidAllowlist = listLocked2;
                }
                java.util.List<java.lang.String> listLocked3 = getListLocked(PLAYER_METRICS_APP_BLOCKLIST);
                if (listLocked3 != null || this.mMode.intValue() != 2) {
                    this.mBlockList = listLocked3;
                }
                java.util.List<java.lang.String> listLocked4 = getListLocked(PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST);
                if (listLocked4 != null || this.mMode.intValue() != 2) {
                    this.mNoUidBlocklist = listLocked4;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public java.util.List<java.lang.String> getListLocked(java.lang.String str) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.lang.String string = android.provider.DeviceConfig.getString("media", str, FAILED_TO_GET);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            if (string.equals(FAILED_TO_GET)) {
                android.util.Slog.d(TAG, "failed to get " + str + " from DeviceConfig");
                return null;
            }
            return java.util.Arrays.asList(string.split(","));
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private final class BinderService extends android.media.metrics.IMediaMetricsManager.Stub {
        private BinderService() {
        }

        public void reportPlaybackMetrics(java.lang.String str, android.media.metrics.PlaybackMetrics playbackMetrics, int i) {
            int loggingLevel = loggingLevel();
            if (loggingLevel == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(320).writeInt(loggingLevel == 0 ? android.os.Binder.getCallingUid() : 0).writeString(str).writeLong(playbackMetrics.getMediaDurationMillis()).writeInt(playbackMetrics.getStreamSource()).writeInt(playbackMetrics.getStreamType()).writeInt(playbackMetrics.getPlaybackType()).writeInt(playbackMetrics.getDrmType()).writeInt(playbackMetrics.getContentType()).writeString(playbackMetrics.getPlayerName()).writeString(playbackMetrics.getPlayerVersion()).writeByteArray(new byte[0]).writeInt(playbackMetrics.getVideoFramesPlayed()).writeInt(playbackMetrics.getVideoFramesDropped()).writeInt(playbackMetrics.getAudioUnderrunCount()).writeLong(playbackMetrics.getNetworkBytesRead()).writeLong(playbackMetrics.getLocalBytesRead()).writeLong(playbackMetrics.getNetworkTransferDurationMillis()).writeString(android.util.Base64.encodeToString(playbackMetrics.getDrmSessionId(), 0)).usePooledBuffer().build());
        }

        public void reportBundleMetrics(java.lang.String str, android.os.PersistableBundle persistableBundle, int i) {
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
            }
            switch (persistableBundle.getInt("bundlesession-statsd-atom")) {
                case 322:
                    java.lang.String string = persistableBundle.getString("playbackstateevent-sessionid");
                    int i2 = persistableBundle.getInt("playbackstateevent-state", -1);
                    long j = persistableBundle.getLong("playbackstateevent-lifetime", -1L);
                    if (string == null || i2 < 0 || j < 0) {
                        android.util.Slog.d(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "dropping incomplete data for atom 322: _sessionId: " + string + " _state: " + i2 + " _lifetime: " + j);
                        break;
                    } else {
                        android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(322).writeString(string).writeInt(i2).writeLong(j).usePooledBuffer().build());
                        break;
                    }
            }
        }

        public void reportPlaybackStateEvent(java.lang.String str, android.media.metrics.PlaybackStateEvent playbackStateEvent, int i) {
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(322).writeString(str).writeInt(playbackStateEvent.getState()).writeLong(playbackStateEvent.getTimeSinceCreatedMillis()).usePooledBuffer().build());
        }

        private java.lang.String getSessionIdInternal(int i) {
            byte[] bArr = new byte[12];
            com.android.server.media.metrics.MediaMetricsManagerService.this.mSecureRandom.nextBytes(bArr);
            java.lang.String encodeToString = android.util.Base64.encodeToString(bArr, 11);
            new android.media.MediaMetrics.Item(com.android.server.media.metrics.MediaMetricsManagerService.mMetricsId).set(android.media.MediaMetrics.Property.EVENT, "create").set(android.media.MediaMetrics.Property.LOG_SESSION_ID, encodeToString).record();
            return encodeToString;
        }

        public void releaseSessionId(java.lang.String str, int i) {
            android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Releasing sessionId " + str + " for userId " + i + " [NOP]");
        }

        public java.lang.String getPlaybackSessionId(int i) {
            return getSessionIdInternal(i);
        }

        public java.lang.String getRecordingSessionId(int i) {
            return getSessionIdInternal(i);
        }

        public java.lang.String getTranscodingSessionId(int i) {
            return getSessionIdInternal(i);
        }

        public java.lang.String getEditingSessionId(int i) {
            return getSessionIdInternal(i);
        }

        public java.lang.String getBundleSessionId(int i) {
            return getSessionIdInternal(i);
        }

        public void reportPlaybackErrorEvent(java.lang.String str, android.media.metrics.PlaybackErrorEvent playbackErrorEvent, int i) {
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(323).writeString(str).writeString(playbackErrorEvent.getExceptionStack()).writeInt(playbackErrorEvent.getErrorCode()).writeInt(playbackErrorEvent.getSubErrorCode()).writeLong(playbackErrorEvent.getTimeSinceCreatedMillis()).usePooledBuffer().build());
        }

        public void reportNetworkEvent(java.lang.String str, android.media.metrics.NetworkEvent networkEvent, int i) {
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(321).writeString(str).writeInt(networkEvent.getNetworkType()).writeLong(networkEvent.getTimeSinceCreatedMillis()).usePooledBuffer().build());
        }

        public void reportTrackChangeEvent(java.lang.String str, android.media.metrics.TrackChangeEvent trackChangeEvent, int i) {
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN).writeString(str).writeInt(trackChangeEvent.getTrackState()).writeInt(trackChangeEvent.getTrackChangeReason()).writeString(trackChangeEvent.getContainerMimeType()).writeString(trackChangeEvent.getSampleMimeType()).writeString(trackChangeEvent.getCodecName()).writeInt(trackChangeEvent.getBitrate()).writeLong(trackChangeEvent.getTimeSinceCreatedMillis()).writeInt(trackChangeEvent.getTrackType()).writeString(trackChangeEvent.getLanguage()).writeString(trackChangeEvent.getLanguageRegion()).writeInt(trackChangeEvent.getChannelCount()).writeInt(trackChangeEvent.getAudioSampleRate()).writeInt(trackChangeEvent.getWidth()).writeInt(trackChangeEvent.getHeight()).writeFloat(trackChangeEvent.getVideoFrameRate()).usePooledBuffer().build());
        }

        public void reportEditingEndedEvent(java.lang.String str, android.media.metrics.EditingEndedEvent editingEndedEvent, int i) {
            android.media.metrics.MediaItemInfo mediaItemInfo;
            android.media.metrics.MediaItemInfo outputMediaItemInfo;
            if (loggingLevel() == com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED) {
                return;
            }
            if (editingEndedEvent.getInputMediaItemInfos().isEmpty()) {
                mediaItemInfo = com.android.server.media.metrics.MediaMetricsManagerService.EMPTY_MEDIA_ITEM_INFO;
            } else {
                mediaItemInfo = (android.media.metrics.MediaItemInfo) editingEndedEvent.getInputMediaItemInfos().get(0);
            }
            java.lang.String filteredFirstMimeType = com.android.server.media.metrics.MediaMetricsManagerService.getFilteredFirstMimeType(mediaItemInfo.getSampleMimeTypes(), com.android.server.media.metrics.MediaMetricsManagerService.AUDIO_MIME_TYPE_PREFIX);
            java.lang.String filteredFirstMimeType2 = com.android.server.media.metrics.MediaMetricsManagerService.getFilteredFirstMimeType(mediaItemInfo.getSampleMimeTypes(), com.android.server.media.metrics.MediaMetricsManagerService.VIDEO_MIME_TYPE_PREFIX);
            android.util.Size videoSize = mediaItemInfo.getVideoSize();
            int videoResolutionEnum = com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionEnum(videoSize);
            if (videoResolutionEnum == 0) {
                videoResolutionEnum = com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionEnum(new android.util.Size(videoSize.getHeight(), videoSize.getWidth()));
            }
            java.util.List codecNames = mediaItemInfo.getCodecNames();
            java.lang.String str2 = !codecNames.isEmpty() ? (java.lang.String) codecNames.get(0) : "";
            java.lang.String str3 = codecNames.size() > 1 ? (java.lang.String) codecNames.get(1) : "";
            if (editingEndedEvent.getOutputMediaItemInfo() == null) {
                outputMediaItemInfo = com.android.server.media.metrics.MediaMetricsManagerService.EMPTY_MEDIA_ITEM_INFO;
            } else {
                outputMediaItemInfo = editingEndedEvent.getOutputMediaItemInfo();
            }
            java.lang.String filteredFirstMimeType3 = com.android.server.media.metrics.MediaMetricsManagerService.getFilteredFirstMimeType(outputMediaItemInfo.getSampleMimeTypes(), com.android.server.media.metrics.MediaMetricsManagerService.AUDIO_MIME_TYPE_PREFIX);
            java.lang.String filteredFirstMimeType4 = com.android.server.media.metrics.MediaMetricsManagerService.getFilteredFirstMimeType(outputMediaItemInfo.getSampleMimeTypes(), com.android.server.media.metrics.MediaMetricsManagerService.VIDEO_MIME_TYPE_PREFIX);
            android.util.Size videoSize2 = outputMediaItemInfo.getVideoSize();
            int videoResolutionEnum2 = com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionEnum(videoSize2);
            if (videoResolutionEnum2 == 0) {
                videoResolutionEnum2 = com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionEnum(new android.util.Size(videoSize2.getHeight(), videoSize2.getWidth()));
            }
            java.util.List codecNames2 = outputMediaItemInfo.getCodecNames();
            android.util.StatsLog.write(android.util.StatsEvent.newBuilder().setAtomId(798).writeString(str).writeInt(editingEndedEvent.getFinalState()).writeFloat(editingEndedEvent.getFinalProgressPercent()).writeInt(editingEndedEvent.getErrorCode()).writeLong(editingEndedEvent.getTimeSinceCreatedMillis()).writeString(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredLibraryName(editingEndedEvent.getExporterName())).writeString(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredLibraryName(editingEndedEvent.getMuxerName())).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getThroughputFps(editingEndedEvent)).writeInt(editingEndedEvent.getInputMediaItemInfos().size()).writeInt(mediaItemInfo.getSourceType()).writeLong(com.android.server.media.metrics.MediaMetricsManagerService.getBucketedDurationMillis(mediaItemInfo.getDurationMillis())).writeLong(com.android.server.media.metrics.MediaMetricsManagerService.getBucketedDurationMillis(mediaItemInfo.getClipDurationMillis())).writeString(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredMimeType(mediaItemInfo.getContainerMimeType())).writeString(filteredFirstMimeType).writeString(filteredFirstMimeType2).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getCodecEnum(filteredFirstMimeType2)).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredAudioSampleRateHz(mediaItemInfo.getAudioSampleRateHz())).writeInt(mediaItemInfo.getAudioChannelCount()).writeInt(videoSize.getWidth()).writeInt(videoSize.getHeight()).writeInt(videoResolutionEnum).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionAspectRatioEnum(videoSize)).writeInt(mediaItemInfo.getVideoDataSpace()).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoHdrFormatEnum(mediaItemInfo.getVideoDataSpace(), filteredFirstMimeType2)).writeInt(java.lang.Math.round(mediaItemInfo.getVideoFrameRate())).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoFrameRateEnum(mediaItemInfo.getVideoFrameRate())).writeString(str2).writeString(str3).writeLong(com.android.server.media.metrics.MediaMetricsManagerService.getBucketedDurationMillis(outputMediaItemInfo.getDurationMillis())).writeLong(com.android.server.media.metrics.MediaMetricsManagerService.getBucketedDurationMillis(outputMediaItemInfo.getClipDurationMillis())).writeString(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredMimeType(outputMediaItemInfo.getContainerMimeType())).writeString(filteredFirstMimeType3).writeString(filteredFirstMimeType4).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getCodecEnum(filteredFirstMimeType4)).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getFilteredAudioSampleRateHz(outputMediaItemInfo.getAudioSampleRateHz())).writeInt(outputMediaItemInfo.getAudioChannelCount()).writeInt(videoSize2.getWidth()).writeInt(videoSize2.getHeight()).writeInt(videoResolutionEnum2).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoResolutionAspectRatioEnum(videoSize2)).writeInt(outputMediaItemInfo.getVideoDataSpace()).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoHdrFormatEnum(outputMediaItemInfo.getVideoDataSpace(), filteredFirstMimeType4)).writeInt(java.lang.Math.round(outputMediaItemInfo.getVideoFrameRate())).writeInt(com.android.server.media.metrics.MediaMetricsManagerService.getVideoFrameRateEnum(outputMediaItemInfo.getVideoFrameRate())).writeString(!codecNames2.isEmpty() ? (java.lang.String) codecNames2.get(0) : "").writeString(codecNames2.size() > 1 ? (java.lang.String) codecNames2.get(1) : "").usePooledBuffer().build());
        }

        private int loggingLevel() {
            synchronized (com.android.server.media.metrics.MediaMetricsManagerService.this.mLock) {
                try {
                    int callingUid = android.os.Binder.getCallingUid();
                    if (com.android.server.media.metrics.MediaMetricsManagerService.this.mMode == null) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            com.android.server.media.metrics.MediaMetricsManagerService.this.mMode = java.lang.Integer.valueOf(android.provider.DeviceConfig.getInt("media", com.android.server.media.metrics.MediaMetricsManagerService.MEDIA_METRICS_MODE, 2));
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        } catch (java.lang.Throwable th) {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    }
                    if (com.android.server.media.metrics.MediaMetricsManagerService.this.mMode.intValue() == 1) {
                        return 0;
                    }
                    int intValue = com.android.server.media.metrics.MediaMetricsManagerService.this.mMode.intValue();
                    int i = com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                    if (intValue == 0) {
                        android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: MEDIA_METRICS_MODE_OFF");
                        return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                    }
                    java.lang.String[] packagesForUid = com.android.server.media.metrics.MediaMetricsManagerService.this.getContext().getPackageManager().getPackagesForUid(callingUid);
                    if (packagesForUid == null || packagesForUid.length == 0) {
                        android.util.Slog.d(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "empty package from uid " + callingUid);
                        if (com.android.server.media.metrics.MediaMetricsManagerService.this.mMode.intValue() == 2) {
                            i = 1000;
                        }
                        return i;
                    }
                    if (com.android.server.media.metrics.MediaMetricsManagerService.this.mMode.intValue() == 2) {
                        if (com.android.server.media.metrics.MediaMetricsManagerService.this.mBlockList == null) {
                            com.android.server.media.metrics.MediaMetricsManagerService.this.mBlockList = com.android.server.media.metrics.MediaMetricsManagerService.this.getListLocked(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_BLOCKLIST);
                            if (com.android.server.media.metrics.MediaMetricsManagerService.this.mBlockList == null) {
                                android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Failed to get PLAYER_METRICS_APP_BLOCKLIST.");
                                return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                            }
                        }
                        java.lang.Integer loggingLevelInternal = loggingLevelInternal(packagesForUid, com.android.server.media.metrics.MediaMetricsManagerService.this.mBlockList, com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_BLOCKLIST);
                        if (loggingLevelInternal != null) {
                            return loggingLevelInternal.intValue();
                        }
                        if (com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidBlocklist == null) {
                            com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidBlocklist = com.android.server.media.metrics.MediaMetricsManagerService.this.getListLocked(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST);
                            if (com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidBlocklist == null) {
                                android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Failed to get PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST.");
                                return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                            }
                        }
                        java.lang.Integer loggingLevelInternal2 = loggingLevelInternal(packagesForUid, com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidBlocklist, com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST);
                        if (loggingLevelInternal2 == null) {
                            return 0;
                        }
                        return loggingLevelInternal2.intValue();
                    }
                    if (com.android.server.media.metrics.MediaMetricsManagerService.this.mMode.intValue() == 3) {
                        if (com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidAllowlist == null) {
                            com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidAllowlist = com.android.server.media.metrics.MediaMetricsManagerService.this.getListLocked(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST);
                            if (com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidAllowlist == null) {
                                android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Failed to get PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST.");
                                return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                            }
                        }
                        java.lang.Integer loggingLevelInternal3 = loggingLevelInternal(packagesForUid, com.android.server.media.metrics.MediaMetricsManagerService.this.mNoUidAllowlist, com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST);
                        if (loggingLevelInternal3 != null) {
                            return loggingLevelInternal3.intValue();
                        }
                        if (com.android.server.media.metrics.MediaMetricsManagerService.this.mAllowlist == null) {
                            com.android.server.media.metrics.MediaMetricsManagerService.this.mAllowlist = com.android.server.media.metrics.MediaMetricsManagerService.this.getListLocked(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_ALLOWLIST);
                            if (com.android.server.media.metrics.MediaMetricsManagerService.this.mAllowlist == null) {
                                android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Failed to get PLAYER_METRICS_APP_ALLOWLIST.");
                                return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                            }
                        }
                        java.lang.Integer loggingLevelInternal4 = loggingLevelInternal(packagesForUid, com.android.server.media.metrics.MediaMetricsManagerService.this.mAllowlist, com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_ALLOWLIST);
                        if (loggingLevelInternal4 != null) {
                            return loggingLevelInternal4.intValue();
                        }
                        android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Not detected in any allowlist.");
                        return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                    }
                    android.util.Slog.v(com.android.server.media.metrics.MediaMetricsManagerService.TAG, "Logging level blocked: Blocked by default.");
                    return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                } finally {
                }
            }
        }

        private java.lang.Integer loggingLevelInternal(java.lang.String[] strArr, java.util.List<java.lang.String> list, java.lang.String str) {
            if (inList(strArr, list)) {
                return java.lang.Integer.valueOf(listNameToLoggingLevel(str));
            }
            return null;
        }

        private boolean inList(java.lang.String[] strArr, java.util.List<java.lang.String> list) {
            for (java.lang.String str : strArr) {
                java.util.Iterator<java.lang.String> it = list.iterator();
                while (it.hasNext()) {
                    if (str.equals(it.next())) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int listNameToLoggingLevel(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -1894232751:
                    if (str.equals(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_BLOCKLIST)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1289480849:
                    if (str.equals(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_ALLOWLIST)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -789056333:
                    if (str.equals(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_APP_BLOCKLIST)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1900310029:
                    if (str.equals(com.android.server.media.metrics.MediaMetricsManagerService.PLAYER_METRICS_PER_APP_ATTRIBUTION_ALLOWLIST)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
                case 1:
                    return 0;
                case 2:
                case 3:
                    return 1000;
                default:
                    return com.android.server.media.metrics.MediaMetricsManagerService.LOGGING_LEVEL_BLOCKED;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getFilteredLibraryName(java.lang.String str) {
        return (!android.text.TextUtils.isEmpty(str) && PATTERN_KNOWN_EDITING_LIBRARY_NAMES.matcher(str).matches()) ? str : "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getThroughputFps(android.media.metrics.EditingEndedEvent editingEndedEvent) {
        android.media.metrics.MediaItemInfo outputMediaItemInfo = editingEndedEvent.getOutputMediaItemInfo();
        if (outputMediaItemInfo == null) {
            return -1;
        }
        long videoSampleCount = outputMediaItemInfo.getVideoSampleCount();
        if (videoSampleCount == -1) {
            return -1;
        }
        long timeSinceCreatedMillis = editingEndedEvent.getTimeSinceCreatedMillis();
        if (timeSinceCreatedMillis == -1) {
            return -1;
        }
        return (int) java.lang.Math.min(2147483647L, java.lang.Math.round((videoSampleCount * 1000.0d) / timeSinceCreatedMillis));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getBucketedDurationMillis(long j) {
        if (j == -1 || j <= 0) {
            return -1L;
        }
        return (long) java.lang.Math.ceil(java.lang.Math.pow(2.0d, java.lang.Math.min(13, java.lang.Math.max(0, (int) java.lang.Math.floor((java.lang.Math.log((j + 1) / 60000.0d) / java.lang.Math.log(2.0d)) + 8.0d))) - 8) * 60000.0d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getFilteredFirstMimeType(java.util.List<java.lang.String> list, java.lang.String str) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            java.lang.String str2 = list.get(i);
            if (str2.startsWith(str)) {
                return getFilteredMimeType(str2);
            }
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static java.lang.String getFilteredMimeType(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            return "";
        }
        switch (str.hashCode()) {
            case -2123537834:
                if (str.equals("audio/eac3-joc")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -1851077871:
                if (str.equals("video/dolby-vision")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -1664118616:
                if (str.equals("video/3gpp")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1662735862:
                if (str.equals("video/av01")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1662541442:
                if (str.equals("video/hevc")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1662384007:
                if (str.equals("video/mp2t")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1662382439:
                if (str.equals("video/mpeg")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1662095187:
                if (str.equals("video/webm")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1248337486:
                if (str.equals("application/mp4")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case -1095064472:
                if (str.equals("audio/vnd.dts")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1003765268:
                if (str.equals("audio/vorbis")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -979127466:
                if (str.equals("application/x-mpegURL")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -432837260:
                if (str.equals("audio/mpeg-L1")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -432837259:
                if (str.equals("audio/mpeg-L2")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -387023398:
                if (str.equals("audio/x-matroska")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -275430368:
                if (str.equals("application/x-matroska")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -156749520:
                if (str.equals("application/vnd.ms-sstr+xml")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -53558318:
                if (str.equals("audio/mp4a-latm")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -43467528:
                if (str.equals("application/webm")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 13915911:
                if (str.equals("video/x-flv")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 64194685:
                if (str.equals("application/dash+xml")) {
                    c = '\'';
                    break;
                }
                c = 65535;
                break;
            case 187078296:
                if (str.equals("audio/ac3")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 187078886:
                if (str.equals("audio/av4")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 187090232:
                if (str.equals("audio/mp4")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 187091926:
                if (str.equals("audio/ogg")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 187094639:
                if (str.equals("audio/raw")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 187099443:
                if (str.equals("audio/wav")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1187890754:
                if (str.equals("video/mp4v-es")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 1331836730:
                if (str.equals("video/avc")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1331848029:
                if (str.equals("video/mp4")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1331852436:
                if (str.equals("video/raw")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1504578661:
                if (str.equals("audio/eac3")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1504619009:
                if (str.equals("audio/flac")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 1504824762:
                if (str.equals("audio/midi")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1504831518:
                if (str.equals("audio/mpeg")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1504891608:
                if (str.equals("audio/opus")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1505118770:
                if (str.equals("audio/webm")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 1505942594:
                if (str.equals("audio/vnd.dts.hd")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1556697186:
                if (str.equals("audio/true-hd")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1599127256:
                if (str.equals("video/x-vnd.on2.vp8")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1599127257:
                if (str.equals("video/x-vnd.on2.vp9")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2039520277:
                if (str.equals("video/x-matroska")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getCodecEnum(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        switch (str.hashCode()) {
            case -1662735862:
                if (str.equals("video/av01")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1662541442:
                if (str.equals("video/hevc")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1331836730:
                if (str.equals("video/avc")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1599127256:
                if (str.equals("video/x-vnd.on2.vp8")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1599127257:
                if (str.equals("video/x-vnd.on2.vp9")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            default:
                return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getFilteredAudioSampleRateHz(int i) {
        switch (i) {
            case com.android.server.EventLogTags.JOB_DEFERRED_EXECUTION /* 8000 */:
            case 11025:
            case 16000:
            case 22050:
            case 44100:
            case 48000:
            case 96000:
            case 192000:
                return i;
            default:
                return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getVideoResolutionEnum(android.util.Size size) {
        int width = size.getWidth();
        int height = size.getHeight();
        if (width == 352 && height == 640) {
            return 228;
        }
        if (width == 360 && height == 640) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_360X640;
        }
        if (width == 480 && height == 640) {
            return 311;
        }
        if (width == 480 && height == 854) {
            return 414;
        }
        if (width == 540 && height == 960) {
            return 524;
        }
        if (width == 576 && height == 1024) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_576X1024;
        }
        if (width == 1280 && height == 720) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_720P_HD;
        }
        if (width == 1920 && height == 1080) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1080P_FHD;
        }
        if (width == 1440 && height == 2560) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_1440X2560;
        }
        if (width == 3840 && height == 2160) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_4K_UHD;
        }
        if (width == 7680 && height == 4320) {
            return com.android.internal.util.FrameworkStatsLog.MEDIA_CODEC_RENDERED__RESOLUTION__RESOLUTION_8K_UHD;
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getVideoResolutionAspectRatioEnum(android.util.Size size) {
        int width = size.getWidth();
        int height = size.getHeight();
        if (width <= 0 || height <= 0) {
            return 0;
        }
        if (width < height) {
            return 3;
        }
        if (height < width) {
            return 2;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getVideoHdrFormatEnum(int i, java.lang.String str) {
        if (i == 0) {
            return 0;
        }
        if (str.equals("video/dolby-vision")) {
            return 5;
        }
        int standard = android.hardware.DataSpace.getStandard(i);
        int transfer = android.hardware.DataSpace.getTransfer(i);
        if (standard == 393216 && transfer == 33554432) {
            return 2;
        }
        if (standard == 393216 && transfer == 29360128) {
            return 3;
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getVideoFrameRateEnum(float f) {
        switch (java.lang.Math.round(f)) {
            case 24:
                return 2400;
            case 25:
                return 2500;
            case 30:
                return 3000;
            case 50:
                return 5000;
            case 60:
                return 6000;
            case 120:
                return 12000;
            case com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED /* 240 */:
                return 24000;
            case com.android.server.SystemService.PHASE_LOCK_SETTINGS_READY /* 480 */:
                return 48000;
            case 960:
                return 96000;
            default:
                return 0;
        }
    }
}
