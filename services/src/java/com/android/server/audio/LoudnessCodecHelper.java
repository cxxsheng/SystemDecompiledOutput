package com.android.server.audio;

/* loaded from: classes.dex */
public class LoudnessCodecHelper {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final int SPL_RANGE_LARGE = 3;

    @com.android.internal.annotations.VisibleForTesting
    static final int SPL_RANGE_MEDIUM = 2;

    @com.android.internal.annotations.VisibleForTesting
    static final int SPL_RANGE_SMALL = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final int SPL_RANGE_UNKNOWN = 0;
    private static final java.lang.String SYSTEM_PROPERTY_SPEAKER_SPL_RANGE_SIZE = "audio.loudness.builtin-speaker-spl-range-size";
    private static final java.lang.String TAG = "AS.LoudnessCodecHelper";
    private static final com.android.server.utils.EventLogger sLogger = new com.android.server.utils.EventLogger(30, "Loudness updates");
    private final com.android.server.audio.AudioService mAudioService;
    private final com.android.server.audio.LoudnessCodecHelper.LoudnessRemoteCallbackList mLoudnessUpdateDispatchers = new com.android.server.audio.LoudnessCodecHelper.LoudnessRemoteCallbackList(this);
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId, java.util.Set<java.lang.Integer>> mStartedConfigPiids = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId, java.util.Set<android.media.LoudnessCodecInfo>> mStartedConfigInfo = new java.util.HashMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mPiidToDeviceIdCache = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseIntArray mPiidToPidCache = new android.util.SparseIntArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.HashMap<com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties, android.os.PersistableBundle> mCachedProperties = new java.util.HashMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceSplRange {
    }

    private static final class LoudnessRemoteCallbackList extends android.os.RemoteCallbackList<android.media.ILoudnessCodecUpdatesDispatcher> {
        private final com.android.server.audio.LoudnessCodecHelper mLoudnessCodecHelper;

        LoudnessRemoteCallbackList(com.android.server.audio.LoudnessCodecHelper loudnessCodecHelper) {
            this.mLoudnessCodecHelper = loudnessCodecHelper;
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher, java.lang.Object obj) {
            java.lang.Integer num;
            if (!(obj instanceof java.lang.Integer)) {
                num = null;
            } else {
                num = (java.lang.Integer) obj;
            }
            if (num != null) {
                com.android.server.audio.LoudnessCodecHelper.sLogger.enqueue(com.android.server.audio.AudioServiceEvents.LoudnessEvent.getClientDied(num.intValue()));
                this.mLoudnessCodecHelper.onClientPidDied(num.intValue());
            }
            super.onCallbackDied((com.android.server.audio.LoudnessCodecHelper.LoudnessRemoteCallbackList) iLoudnessCodecUpdatesDispatcher, obj);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class LoudnessCodecInputProperties {
        private final int mDeviceSplRange;
        private final boolean mIsDownmixing;
        private final int mMetadataType;

        static final class Builder {
            private int mDeviceSplRange;
            private boolean mIsDownmixing;
            private int mMetadataType;

            Builder() {
            }

            com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties.Builder setMetadataType(int i) {
                this.mMetadataType = i;
                return this;
            }

            com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties.Builder setIsDownmixing(boolean z) {
                this.mIsDownmixing = z;
                return this;
            }

            com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties.Builder setDeviceSplRange(int i) {
                this.mDeviceSplRange = i;
                return this;
            }

            com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties build() {
                return new com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties(this.mMetadataType, this.mIsDownmixing, this.mDeviceSplRange);
            }
        }

        private LoudnessCodecInputProperties(int i, boolean z, int i2) {
            this.mMetadataType = i;
            this.mIsDownmixing = z;
            this.mDeviceSplRange = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties.class != obj.getClass()) {
                return false;
            }
            com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties loudnessCodecInputProperties = (com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties) obj;
            if (this.mMetadataType == loudnessCodecInputProperties.mMetadataType && this.mIsDownmixing == loudnessCodecInputProperties.mIsDownmixing && this.mDeviceSplRange == loudnessCodecInputProperties.mDeviceSplRange) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mMetadataType), java.lang.Boolean.valueOf(this.mIsDownmixing), java.lang.Integer.valueOf(this.mDeviceSplRange));
        }

        public java.lang.String toString() {
            return "Loudness properties: device SPL range: " + com.android.server.audio.LoudnessCodecHelper.splRangeToString(this.mDeviceSplRange) + " down-mixing: " + this.mIsDownmixing + " metadata type: " + this.mMetadataType;
        }

        android.os.PersistableBundle createLoudnessParameters() {
            android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
            switch (this.mDeviceSplRange) {
                case 1:
                    persistableBundle.putInt("aac-target-ref-level", 64);
                    if (this.mMetadataType == 1) {
                        persistableBundle.putInt("aac-drc-heavy-compression", 1);
                    } else if (this.mMetadataType == 2) {
                        persistableBundle.putInt("aac-drc-effect-type", 3);
                    }
                    return persistableBundle;
                case 2:
                    persistableBundle.putInt("aac-target-ref-level", 96);
                    if (this.mMetadataType == 1) {
                        persistableBundle.putInt("aac-drc-heavy-compression", this.mIsDownmixing ? 1 : 0);
                    } else if (this.mMetadataType == 2) {
                        persistableBundle.putInt("aac-drc-effect-type", 6);
                    }
                    return persistableBundle;
                case 3:
                    persistableBundle.putInt("aac-target-ref-level", 124);
                    if (this.mMetadataType == 1) {
                        persistableBundle.putInt("aac-drc-heavy-compression", 0);
                    } else if (this.mMetadataType == 2) {
                        persistableBundle.putInt("aac-drc-effect-type", 6);
                    }
                    return persistableBundle;
                default:
                    persistableBundle.putInt("aac-target-ref-level", 96);
                    if (this.mMetadataType == 1) {
                        persistableBundle.putInt("aac-drc-heavy-compression", this.mIsDownmixing ? 1 : 0);
                    } else if (this.mMetadataType == 2) {
                        persistableBundle.putInt("aac-drc-effect-type", 6);
                    }
                    return persistableBundle;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class LoudnessTrackId {
        private final int mPid;
        private final int mSessionId;

        private LoudnessTrackId(int i, int i2) {
            this.mSessionId = i;
            this.mPid = i2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId.class != obj.getClass()) {
                return false;
            }
            com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = (com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId) obj;
            if (this.mSessionId == loudnessTrackId.mSessionId && this.mPid == loudnessTrackId.mPid) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSessionId), java.lang.Integer.valueOf(this.mPid));
        }

        public java.lang.String toString() {
            return "Loudness track id: session ID: " + this.mSessionId + " pid: " + this.mPid;
        }
    }

    LoudnessCodecHelper(@android.annotation.NonNull com.android.server.audio.AudioService audioService) {
        java.util.Objects.requireNonNull(audioService);
        this.mAudioService = audioService;
    }

    void registerLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        this.mLoudnessUpdateDispatchers.register(iLoudnessCodecUpdatesDispatcher, java.lang.Integer.valueOf(android.os.Binder.getCallingPid()));
    }

    void unregisterLoudnessCodecUpdatesDispatcher(android.media.ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher) {
        this.mLoudnessUpdateDispatchers.unregister(iLoudnessCodecUpdatesDispatcher);
    }

    void startLoudnessCodecUpdates(final int i) {
        final int callingPid = android.os.Binder.getCallingPid();
        com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(i, callingPid);
        synchronized (this.mLock) {
            try {
                if (this.mStartedConfigInfo.containsKey(loudnessTrackId)) {
                    android.util.Log.w(TAG, "Already started loudness updates for config: " + loudnessTrackId);
                    return;
                }
                this.mStartedConfigInfo.put(loudnessTrackId, new java.util.HashSet());
                final java.util.HashSet hashSet = new java.util.HashSet();
                this.mStartedConfigPiids.put(loudnessTrackId, hashSet);
                android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
                try {
                    this.mAudioService.getActivePlaybackConfigurations().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$startLoudnessCodecUpdates$0;
                            lambda$startLoudnessCodecUpdates$0 = com.android.server.audio.LoudnessCodecHelper.lambda$startLoudnessCodecUpdates$0(i, callingPid, (android.media.AudioPlaybackConfiguration) obj);
                            return lambda$startLoudnessCodecUpdates$0;
                        }
                    }).forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.audio.LoudnessCodecHelper.this.lambda$startLoudnessCodecUpdates$1(hashSet, callingPid, (android.media.AudioPlaybackConfiguration) obj);
                        }
                    });
                    if (create != null) {
                        create.close();
                    }
                } catch (java.lang.Throwable th) {
                    if (create != null) {
                        try {
                            create.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th3) {
                throw th3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$startLoudnessCodecUpdates$0(int i, int i2, android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        return audioPlaybackConfiguration.getSessionId() == i && audioPlaybackConfiguration.getClientPid() == i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startLoudnessCodecUpdates$1(java.util.HashSet hashSet, int i, android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
        synchronized (this.mLock) {
            hashSet.add(java.lang.Integer.valueOf(playerInterfaceId));
            this.mPiidToPidCache.put(playerInterfaceId, i);
            sLogger.enqueue(com.android.server.audio.AudioServiceEvents.LoudnessEvent.getStartPiid(playerInterfaceId, i));
        }
    }

    void stopLoudnessCodecUpdates(int i) {
        com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(i, android.os.Binder.getCallingPid());
        synchronized (this.mLock) {
            try {
                if (!this.mStartedConfigInfo.containsKey(loudnessTrackId)) {
                    android.util.Log.w(TAG, "Loudness updates are already stopped config: " + loudnessTrackId);
                    return;
                }
                java.util.Set<java.lang.Integer> set = this.mStartedConfigPiids.get(loudnessTrackId);
                if (set == null) {
                    android.util.Log.e(TAG, "Loudness updates are already stopped config: " + loudnessTrackId);
                    return;
                }
                for (java.lang.Integer num : set) {
                    sLogger.enqueue(com.android.server.audio.AudioServiceEvents.LoudnessEvent.getStopPiid(num.intValue(), this.mPiidToPidCache.get(num.intValue(), -1)));
                    this.mPiidToDeviceIdCache.delete(num.intValue());
                    this.mPiidToPidCache.delete(num.intValue());
                }
                this.mStartedConfigPiids.remove(loudnessTrackId);
                this.mStartedConfigInfo.remove(loudnessTrackId);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addLoudnessCodecInfo(final int i, int i2, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        final int callingPid = android.os.Binder.getCallingPid();
        com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(i, callingPid);
        synchronized (this.mLock) {
            if (!this.mStartedConfigInfo.containsKey(loudnessTrackId) || !this.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                android.util.Log.w(TAG, "Cannot add new loudness info for stopped config " + loudnessTrackId);
                return;
            }
            java.util.Set<java.lang.Integer> set = this.mStartedConfigPiids.get(loudnessTrackId);
            this.mStartedConfigInfo.get(loudnessTrackId).add(loudnessCodecInfo);
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
                java.util.Optional<android.media.AudioPlaybackConfiguration> findFirst = this.mAudioService.getActivePlaybackConfigurations().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$addLoudnessCodecInfo$2;
                        lambda$addLoudnessCodecInfo$2 = com.android.server.audio.LoudnessCodecHelper.lambda$addLoudnessCodecInfo$2(i, callingPid, (android.media.AudioPlaybackConfiguration) obj);
                        return lambda$addLoudnessCodecInfo$2;
                    }
                }).findFirst();
                if (findFirst.isEmpty()) {
                    persistableBundle.putPersistableBundle(java.lang.Integer.toString(i2), getLoudnessParams(loudnessCodecInfo));
                } else {
                    android.media.AudioDeviceInfo audioDeviceInfo = findFirst.get().getAudioDeviceInfo();
                    if (audioDeviceInfo != null) {
                        synchronized (this.mLock) {
                            set.add(java.lang.Integer.valueOf(findFirst.get().getPlayerInterfaceId()));
                            persistableBundle.putPersistableBundle(java.lang.Integer.toString(i2), getCodecBundle_l(audioDeviceInfo.getInternalType(), audioDeviceInfo.getAddress(), loudnessCodecInfo));
                        }
                    }
                }
                if (!persistableBundle.isDefinitelyEmpty()) {
                    dispatchNewLoudnessParameters(i, persistableBundle);
                }
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$addLoudnessCodecInfo$2(int i, int i2, android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        return audioPlaybackConfiguration.getSessionId() == i && audioPlaybackConfiguration.getClientPid() == i2;
    }

    void removeLoudnessCodecInfo(int i, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(i, android.os.Binder.getCallingPid());
        synchronized (this.mLock) {
            try {
                if (!this.mStartedConfigInfo.containsKey(loudnessTrackId) || !this.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                    android.util.Log.w(TAG, "Cannot remove loudness info for stopped config " + loudnessTrackId);
                    return;
                }
                if (!this.mStartedConfigInfo.get(loudnessTrackId).remove(loudnessCodecInfo)) {
                    android.util.Log.w(TAG, "Could not find to remove codecInfo " + loudnessCodecInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.os.PersistableBundle getLoudnessParams(android.media.LoudnessCodecInfo loudnessCodecInfo) {
        android.os.PersistableBundle codecBundle_l;
        java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributesInt = this.mAudioService.getDevicesForAttributesInt(new android.media.AudioAttributes.Builder().setUsage(1).setContentType(2).build(), false);
        if (!devicesForAttributesInt.isEmpty()) {
            android.media.AudioDeviceAttributes audioDeviceAttributes = devicesForAttributesInt.get(0);
            synchronized (this.mLock) {
                codecBundle_l = getCodecBundle_l(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress(), loudnessCodecInfo);
            }
            return codecBundle_l;
        }
        return new android.os.PersistableBundle();
    }

    void updateCodecParameters(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                    int playerInterfaceId = audioPlaybackConfiguration.getPlayerInterfaceId();
                    int i = this.mPiidToDeviceIdCache.get(playerInterfaceId, 0);
                    android.media.AudioDeviceInfo audioDeviceInfo = audioPlaybackConfiguration.getAudioDeviceInfo();
                    if (audioDeviceInfo == null) {
                        if (i != 0) {
                            this.mPiidToDeviceIdCache.delete(playerInterfaceId);
                        }
                    } else if (i != audioDeviceInfo.getId()) {
                        this.mPiidToDeviceIdCache.put(playerInterfaceId, audioDeviceInfo.getId());
                        com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId loudnessTrackId = new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(audioPlaybackConfiguration.getSessionId(), audioPlaybackConfiguration.getClientPid());
                        if (this.mStartedConfigInfo.containsKey(loudnessTrackId) && this.mStartedConfigPiids.containsKey(loudnessTrackId)) {
                            arrayList.add(audioPlaybackConfiguration);
                            this.mStartedConfigPiids.get(loudnessTrackId).add(java.lang.Integer.valueOf(playerInterfaceId));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        arrayList.forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.LoudnessCodecHelper.this.updateCodecParametersForConfiguration((android.media.AudioPlaybackConfiguration) obj);
            }
        });
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("\nRegistered clients:\n");
        synchronized (this.mLock) {
            try {
                for (java.util.Map.Entry<com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId, java.util.Set<java.lang.Integer>> entry : this.mStartedConfigPiids.entrySet()) {
                    for (java.lang.Integer num : entry.getValue()) {
                        int i = this.mPiidToPidCache.get(num.intValue(), -1);
                        java.util.Set<android.media.LoudnessCodecInfo> set = this.mStartedConfigInfo.get(entry.getKey());
                        if (set != null) {
                            printWriter.println(java.lang.String.format("Player piid %d pid %d active codec types %s\n", num, java.lang.Integer.valueOf(i), set.stream().map(new java.util.function.Function() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda0
                                @Override // java.util.function.Function
                                public final java.lang.Object apply(java.lang.Object obj) {
                                    return ((android.media.LoudnessCodecInfo) obj).toString();
                                }
                            }).collect(java.util.stream.Collectors.joining(", "))));
                        }
                    }
                }
                printWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        sLogger.dump(printWriter);
        printWriter.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClientPidDied(final int i) {
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mPiidToPidCache.size(); i2++) {
                try {
                    int keyAt = this.mPiidToPidCache.keyAt(i2);
                    if (this.mPiidToPidCache.get(keyAt) == i) {
                        this.mPiidToDeviceIdCache.delete(keyAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mStartedConfigPiids.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onClientPidDied$3;
                    lambda$onClientPidDied$3 = com.android.server.audio.LoudnessCodecHelper.lambda$onClientPidDied$3(i, (java.util.Map.Entry) obj);
                    return lambda$onClientPidDied$3;
                }
            });
            this.mStartedConfigInfo.entrySet().removeIf(new java.util.function.Predicate() { // from class: com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$onClientPidDied$4;
                    lambda$onClientPidDied$4 = com.android.server.audio.LoudnessCodecHelper.lambda$onClientPidDied$4(i, (java.util.Map.Entry) obj);
                    return lambda$onClientPidDied$4;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onClientPidDied$3(int i, java.util.Map.Entry entry) {
        return ((com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId) entry.getKey()).mPid == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onClientPidDied$4(int i, java.util.Map.Entry entry) {
        return ((com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId) entry.getKey()).mPid == i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCodecParametersForConfiguration(android.media.AudioPlaybackConfiguration audioPlaybackConfiguration) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle();
        synchronized (this.mLock) {
            try {
                java.util.Set<android.media.LoudnessCodecInfo> set = this.mStartedConfigInfo.get(new com.android.server.audio.LoudnessCodecHelper.LoudnessTrackId(audioPlaybackConfiguration.getSessionId(), audioPlaybackConfiguration.getClientPid()));
                android.media.AudioDeviceInfo audioDeviceInfo = audioPlaybackConfiguration.getAudioDeviceInfo();
                if (set != null && audioDeviceInfo != null) {
                    for (android.media.LoudnessCodecInfo loudnessCodecInfo : set) {
                        if (loudnessCodecInfo != null) {
                            persistableBundle.putPersistableBundle(java.lang.Integer.toString(loudnessCodecInfo.hashCode()), getCodecBundle_l(audioDeviceInfo.getInternalType(), audioDeviceInfo.getAddress(), loudnessCodecInfo));
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!persistableBundle.isDefinitelyEmpty()) {
            dispatchNewLoudnessParameters(audioPlaybackConfiguration.getSessionId(), persistableBundle);
        }
    }

    private void dispatchNewLoudnessParameters(int i, android.os.PersistableBundle persistableBundle) {
        int beginBroadcast = this.mLoudnessUpdateDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mLoudnessUpdateDispatchers.getBroadcastItem(i2).dispatchLoudnessCodecParameterChange(i, persistableBundle);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error dispatching for sessionId " + i + " bundle: " + persistableBundle, e);
            }
        }
        this.mLoudnessUpdateDispatchers.finishBroadcast();
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.os.PersistableBundle getCodecBundle_l(int i, java.lang.String str, android.media.LoudnessCodecInfo loudnessCodecInfo) {
        com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties build = new com.android.server.audio.LoudnessCodecHelper.LoudnessCodecInputProperties.Builder().setDeviceSplRange(getDeviceSplRange(i, str)).setIsDownmixing(loudnessCodecInfo.isDownmixing).setMetadataType(loudnessCodecInfo.metadataType).build();
        if (this.mCachedProperties.containsKey(build)) {
            return this.mCachedProperties.get(build);
        }
        android.os.PersistableBundle createLoudnessParameters = build.createLoudnessParameters();
        this.mCachedProperties.put(build, createLoudnessParameters);
        return createLoudnessParameters;
    }

    private int getDeviceSplRange(int i, java.lang.String str) {
        int bluetoothAudioDeviceCategory_legacy;
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            if (android.media.audio.Flags.automaticBtDeviceType()) {
                bluetoothAudioDeviceCategory_legacy = this.mAudioService.getBluetoothAudioDeviceCategory(str);
            } else {
                bluetoothAudioDeviceCategory_legacy = this.mAudioService.getBluetoothAudioDeviceCategory_legacy(str, android.media.AudioSystem.isBluetoothLeDevice(i));
            }
            if (create != null) {
                create.close();
            }
            if (i == 2) {
                java.lang.String str2 = android.os.SystemProperties.get(SYSTEM_PROPERTY_SPEAKER_SPL_RANGE_SIZE, "unknown");
                if (str2.equals("unknown")) {
                    return (this.mAudioService.isPlatformAutomotive() || this.mAudioService.isPlatformTelevision()) ? 2 : 1;
                }
                return stringToSplRange(str2);
            }
            if (i == 67108864 || i == 8 || i == 4 || (android.media.AudioSystem.isBluetoothDevice(i) && bluetoothAudioDeviceCategory_legacy == 3)) {
                return 3;
            }
            if (android.media.AudioSystem.isBluetoothDevice(i)) {
                if (bluetoothAudioDeviceCategory_legacy == 4) {
                    return 2;
                }
                if (bluetoothAudioDeviceCategory_legacy == 5 || bluetoothAudioDeviceCategory_legacy == 6) {
                    return 1;
                }
                return 0;
            }
            return 0;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String splRangeToString(int i) {
        switch (i) {
            case 1:
                return "small";
            case 2:
                return "medium";
            case 3:
                return "large";
            default:
                return "unknown";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int stringToSplRange(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1078030475:
                if (str.equals("medium")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 102742843:
                if (str.equals("large")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 109548807:
                if (str.equals("small")) {
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
                return 3;
            case 1:
                return 2;
            case 2:
                return 1;
            default:
                return 0;
        }
    }
}
