package com.android.server.audio;

/* loaded from: classes.dex */
public class AudioSystemAdapter implements android.media.AudioSystem.RoutingUpdateCallback, android.media.AudioSystem.VolumeRangeInitRequestCallback {
    private static final boolean DEBUG_CACHE = false;
    private static final boolean ENABLE_GETDEVICES_STATS = false;
    private static final int METHOD_GETDEVICESFORATTRIBUTES = 0;
    private static final int NB_MEASUREMENTS = 1;
    private static final java.lang.String TAG = "AudioSystemAdapter";
    private static final boolean USE_CACHE_FOR_GETDEVICES = true;

    @com.android.internal.annotations.GuardedBy({"sRoutingListenerLock"})
    @android.annotation.Nullable
    private static com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener sRoutingListener;
    private static com.android.server.audio.AudioSystemAdapter sSingletonDefaultAdapter;

    @com.android.internal.annotations.GuardedBy({"sVolRangeInitReqListenerLock"})
    @android.annotation.Nullable
    private static com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener sVolRangeInitReqListener;

    @com.android.internal.annotations.GuardedBy({"sDeviceCacheLock"})
    private java.util.concurrent.ConcurrentHashMap<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>, java.util.ArrayList<android.media.AudioDeviceAttributes>> mDevicesForAttrCache;
    private int[] mMethodCacheHit;
    private int[] mMethodCallCounter;
    private long[] mMethodTimeNs;
    private static final java.lang.Object sDeviceCacheLock = new java.lang.Object();
    private static final java.lang.Object sRoutingListenerLock = new java.lang.Object();
    private static final java.lang.Object sVolRangeInitReqListenerLock = new java.lang.Object();
    private java.lang.String[] mMethodNames = {"getDevicesForAttributes"};
    private java.util.concurrent.ConcurrentHashMap<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>, java.util.ArrayList<android.media.AudioDeviceAttributes>> mLastDevicesForAttr = new java.util.concurrent.ConcurrentHashMap<>();

    @com.android.internal.annotations.GuardedBy({"sDeviceCacheLock"})
    private long mDevicesForAttributesCacheClearTimeMs = java.lang.System.currentTimeMillis();
    private final android.util.ArrayMap<android.os.IBinder, java.util.List<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>>> mRegisteredAttributesMap = new android.util.ArrayMap<>();
    private final android.os.RemoteCallbackList<android.media.IDevicesForAttributesCallback> mDevicesForAttributesCallbacks = new android.os.RemoteCallbackList<>();

    interface OnRoutingUpdatedListener {
        void onRoutingUpdatedFromNative();
    }

    interface OnVolRangeInitRequestListener {
        void onVolumeRangeInitRequestFromNative();
    }

    public void onRoutingUpdated() {
        com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener onRoutingUpdatedListener;
        invalidateRoutingCache();
        synchronized (sRoutingListenerLock) {
            onRoutingUpdatedListener = sRoutingListener;
        }
        if (onRoutingUpdatedListener != null) {
            onRoutingUpdatedListener.onRoutingUpdatedFromNative();
        }
        synchronized (this.mRegisteredAttributesMap) {
            int beginBroadcast = this.mDevicesForAttributesCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                android.media.IDevicesForAttributesCallback broadcastItem = this.mDevicesForAttributesCallbacks.getBroadcastItem(i);
                java.util.List<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>> list = this.mRegisteredAttributesMap.get(broadcastItem.asBinder());
                if (list == null) {
                    throw new java.lang.IllegalStateException("Attribute list must not be null");
                }
                for (android.util.Pair<android.media.AudioAttributes, java.lang.Boolean> pair : list) {
                    java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributes = getDevicesForAttributes((android.media.AudioAttributes) pair.first, ((java.lang.Boolean) pair.second).booleanValue());
                    if (!this.mLastDevicesForAttr.containsKey(pair) || !sameDeviceList(devicesForAttributes, this.mLastDevicesForAttr.get(pair))) {
                        try {
                            broadcastItem.onDevicesForAttributesChanged((android.media.AudioAttributes) pair.first, ((java.lang.Boolean) pair.second).booleanValue(), devicesForAttributes);
                        } catch (android.os.RemoteException e) {
                        }
                    }
                }
            }
            this.mDevicesForAttributesCallbacks.finishBroadcast();
        }
    }

    static void setRoutingListener(@android.annotation.Nullable com.android.server.audio.AudioSystemAdapter.OnRoutingUpdatedListener onRoutingUpdatedListener) {
        synchronized (sRoutingListenerLock) {
            sRoutingListener = onRoutingUpdatedListener;
        }
    }

    public void clearRoutingCache() {
        invalidateRoutingCache();
    }

    public void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, boolean z, @android.annotation.NonNull android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        android.util.Pair<android.media.AudioAttributes, java.lang.Boolean> pair = new android.util.Pair<>(audioAttributes, java.lang.Boolean.valueOf(z));
        synchronized (this.mRegisteredAttributesMap) {
            try {
                java.util.List<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>> list = this.mRegisteredAttributesMap.get(iDevicesForAttributesCallback.asBinder());
                if (list == null) {
                    list = new java.util.ArrayList<>();
                    this.mRegisteredAttributesMap.put(iDevicesForAttributesCallback.asBinder(), list);
                    this.mDevicesForAttributesCallbacks.register(iDevicesForAttributesCallback);
                }
                if (!list.contains(pair)) {
                    list.add(pair);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        getDevicesForAttributes(audioAttributes, z);
    }

    public void removeOnDevicesForAttributesChangedListener(@android.annotation.NonNull android.media.IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        synchronized (this.mRegisteredAttributesMap) {
            try {
                if (!this.mRegisteredAttributesMap.containsKey(iDevicesForAttributesCallback.asBinder())) {
                    android.util.Log.w(TAG, "listener to be removed is not found.");
                } else {
                    this.mRegisteredAttributesMap.remove(iDevicesForAttributesCallback.asBinder());
                    this.mDevicesForAttributesCallbacks.unregister(iDevicesForAttributesCallback);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean sameDeviceList(@android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list2) {
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            if (!list2.contains(it.next())) {
                return false;
            }
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!list.contains(it2.next())) {
                return false;
            }
        }
        return true;
    }

    public void onVolumeRangeInitializationRequested() {
        com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener onVolRangeInitRequestListener;
        synchronized (sVolRangeInitReqListenerLock) {
            onVolRangeInitRequestListener = sVolRangeInitReqListener;
        }
        if (onVolRangeInitRequestListener != null) {
            onVolRangeInitRequestListener.onVolumeRangeInitRequestFromNative();
        }
    }

    static void setVolRangeInitReqListener(@android.annotation.Nullable com.android.server.audio.AudioSystemAdapter.OnVolRangeInitRequestListener onVolRangeInitRequestListener) {
        synchronized (sVolRangeInitReqListenerLock) {
            sVolRangeInitReqListener = onVolRangeInitRequestListener;
        }
    }

    @android.annotation.NonNull
    static final synchronized com.android.server.audio.AudioSystemAdapter getDefaultAdapter() {
        com.android.server.audio.AudioSystemAdapter audioSystemAdapter;
        synchronized (com.android.server.audio.AudioSystemAdapter.class) {
            try {
                if (sSingletonDefaultAdapter == null) {
                    sSingletonDefaultAdapter = new com.android.server.audio.AudioSystemAdapter();
                    android.media.AudioSystem.setRoutingCallback(sSingletonDefaultAdapter);
                    android.media.AudioSystem.setVolumeRangeInitRequestCallback(sSingletonDefaultAdapter);
                    synchronized (sDeviceCacheLock) {
                        sSingletonDefaultAdapter.mDevicesForAttrCache = new java.util.concurrent.ConcurrentHashMap<>(android.media.AudioSystem.getNumStreamTypes());
                        sSingletonDefaultAdapter.mMethodCacheHit = new int[1];
                    }
                }
                audioSystemAdapter = sSingletonDefaultAdapter;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return audioSystemAdapter;
    }

    private void invalidateRoutingCache() {
        synchronized (sDeviceCacheLock) {
            try {
                if (this.mDevicesForAttrCache != null) {
                    this.mDevicesForAttributesCacheClearTimeMs = java.lang.System.currentTimeMillis();
                    this.mLastDevicesForAttr.putAll(this.mDevicesForAttrCache);
                    this.mDevicesForAttrCache.clear();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.NonNull
    public java.util.ArrayList<android.media.AudioDeviceAttributes> getDevicesForAttributes(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, boolean z) {
        return getDevicesForAttributesImpl(audioAttributes, z);
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.media.AudioDeviceAttributes> getDevicesForAttributesImpl(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, boolean z) {
        android.util.Pair<android.media.AudioAttributes, java.lang.Boolean> pair = new android.util.Pair<>(audioAttributes, java.lang.Boolean.valueOf(z));
        synchronized (sDeviceCacheLock) {
            try {
                java.util.ArrayList<android.media.AudioDeviceAttributes> arrayList = this.mDevicesForAttrCache.get(pair);
                if (arrayList == null) {
                    java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributes = android.media.AudioSystem.getDevicesForAttributes(audioAttributes, z);
                    this.mDevicesForAttrCache.put(pair, devicesForAttributes);
                    return devicesForAttributes;
                }
                int[] iArr = this.mMethodCacheHit;
                iArr[0] = iArr[0] + 1;
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static java.lang.String attrDeviceToDebugString(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return " attrUsage=" + audioAttributes.getSystemUsage() + " " + android.media.AudioSystem.deviceSetToString(android.media.AudioSystem.generateAudioDeviceTypesSet(list));
    }

    public int setDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setDeviceConnectionState(audioDeviceAttributes, i, i2);
    }

    public int getDeviceConnectionState(int i, java.lang.String str) {
        return android.media.AudioSystem.getDeviceConnectionState(i, str);
    }

    public int handleDeviceConfigChange(int i, java.lang.String str, java.lang.String str2, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.handleDeviceConfigChange(i, str, str2, i2);
    }

    public int setDevicesRoleForStrategy(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    public int removeDevicesRoleForStrategy(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        invalidateRoutingCache();
        return android.media.AudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    public int clearDevicesRoleForStrategy(int i, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    public int setDevicesRoleForCapturePreset(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setDevicesRoleForCapturePreset(i, i2, list);
    }

    public int removeDevicesRoleForCapturePreset(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        invalidateRoutingCache();
        return android.media.AudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    public int addDevicesRoleForCapturePreset(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        invalidateRoutingCache();
        return android.media.AudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    public int clearDevicesRoleForCapturePreset(int i, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    public int setParameters(java.lang.String str) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setParameters(str);
    }

    public boolean isMicrophoneMuted() {
        return android.media.AudioSystem.isMicrophoneMuted();
    }

    public int muteMicrophone(boolean z) {
        return android.media.AudioSystem.muteMicrophone(z);
    }

    public int setCurrentImeUid(int i) {
        return android.media.AudioSystem.setCurrentImeUid(i);
    }

    public boolean isStreamActive(int i, int i2) {
        return android.media.AudioSystem.isStreamActive(i, i2);
    }

    public boolean isStreamActiveRemotely(int i, int i2) {
        return android.media.AudioSystem.isStreamActiveRemotely(i, i2);
    }

    public int setStreamVolumeIndexAS(int i, int i2, int i3) {
        return android.media.AudioSystem.setStreamVolumeIndexAS(i, i2, i3);
    }

    public int setVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes, int i, int i2) {
        return android.media.AudioSystem.setVolumeIndexForAttributes(audioAttributes, i, i2);
    }

    public int setPhoneState(int i, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setPhoneState(i, i2);
    }

    public int setAllowedCapturePolicy(int i, int i2) {
        return android.media.AudioSystem.setAllowedCapturePolicy(i, i2);
    }

    public int setForceUse(int i, int i2) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setForceUse(i, i2);
    }

    public int getForceUse(int i) {
        return android.media.AudioSystem.getForceUse(i);
    }

    public int registerPolicyMixes(java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList, boolean z) {
        invalidateRoutingCache();
        return android.media.AudioSystem.registerPolicyMixes(arrayList, z);
    }

    public java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() {
        if (!android.media.audiopolicy.Flags.audioMixTestApi()) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int registeredPolicyMixes = android.media.AudioSystem.getRegisteredPolicyMixes(arrayList);
        if (registeredPolicyMixes != 0) {
            throw new java.lang.IllegalStateException("Cannot fetch registered policy mixes. Result: " + registeredPolicyMixes);
        }
        return java.util.Collections.unmodifiableList(arrayList);
    }

    public int updateMixingRules(@android.annotation.NonNull android.media.audiopolicy.AudioMix[] audioMixArr, @android.annotation.NonNull android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr) {
        invalidateRoutingCache();
        return android.media.AudioSystem.updatePolicyMixes(audioMixArr, audioMixingRuleArr);
    }

    public int setUidDeviceAffinities(int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setUidDeviceAffinities(i, iArr, strArr);
    }

    public int removeUidDeviceAffinities(int i) {
        invalidateRoutingCache();
        return android.media.AudioSystem.removeUidDeviceAffinities(i);
    }

    public int setUserIdDeviceAffinities(int i, @android.annotation.NonNull int[] iArr, @android.annotation.NonNull java.lang.String[] strArr) {
        invalidateRoutingCache();
        return android.media.AudioSystem.setUserIdDeviceAffinities(i, iArr, strArr);
    }

    public int removeUserIdDeviceAffinities(int i) {
        invalidateRoutingCache();
        return android.media.AudioSystem.removeUserIdDeviceAffinities(i);
    }

    public android.media.ISoundDose getSoundDoseInterface(android.media.ISoundDoseCallback iSoundDoseCallback) {
        return android.media.AudioSystem.getSoundDoseInterface(iSoundDoseCallback);
    }

    public int setPreferredMixerAttributes(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i, int i2, @android.annotation.NonNull android.media.AudioMixerAttributes audioMixerAttributes) {
        return android.media.AudioSystem.setPreferredMixerAttributes(audioAttributes, i, i2, audioMixerAttributes);
    }

    public int clearPreferredMixerAttributes(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i, int i2) {
        return android.media.AudioSystem.clearPreferredMixerAttributes(audioAttributes, i, i2);
    }

    public int setMasterMute(boolean z) {
        return android.media.AudioSystem.setMasterMute(z);
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("\nAudioSystemAdapter:");
        java.time.format.DateTimeFormatter withZone = java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SSS").withLocale(java.util.Locale.US).withZone(java.time.ZoneId.systemDefault());
        synchronized (sDeviceCacheLock) {
            printWriter.println(" last cache clear time: " + withZone.format(java.time.Instant.ofEpochMilli(this.mDevicesForAttributesCacheClearTimeMs)));
            printWriter.println(" mDevicesForAttrCache:");
            if (this.mDevicesForAttrCache != null) {
                for (java.util.Map.Entry<android.util.Pair<android.media.AudioAttributes, java.lang.Boolean>, java.util.ArrayList<android.media.AudioDeviceAttributes>> entry : this.mDevicesForAttrCache.entrySet()) {
                    android.media.AudioAttributes audioAttributes = (android.media.AudioAttributes) entry.getKey().first;
                    try {
                        int volumeControlStream = audioAttributes.getVolumeControlStream();
                        printWriter.println("\t" + audioAttributes + " forVolume: " + entry.getKey().second + " stream: " + android.media.AudioSystem.STREAM_NAMES[volumeControlStream] + "(" + volumeControlStream + ")");
                        java.util.Iterator<android.media.AudioDeviceAttributes> it = entry.getValue().iterator();
                        while (it.hasNext()) {
                            printWriter.println("\t\t" + it.next());
                        }
                    } catch (java.lang.IllegalArgumentException e) {
                        printWriter.println("\t dump failed for attributes: " + audioAttributes);
                        android.util.Log.e(TAG, "dump failed", e);
                    }
                }
            }
        }
    }
}
