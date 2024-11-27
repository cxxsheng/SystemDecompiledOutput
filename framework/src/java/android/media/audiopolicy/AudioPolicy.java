package android.media.audiopolicy;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AudioPolicy {
    private static final boolean DEBUG = false;
    public static final int FOCUS_POLICY_DUCKING_DEFAULT = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_APP = 0;
    public static final int FOCUS_POLICY_DUCKING_IN_POLICY = 1;
    private static final int MSG_FOCUS_ABANDON = 5;
    private static final int MSG_FOCUS_GRANT = 1;
    private static final int MSG_FOCUS_LOSS = 2;
    private static final int MSG_FOCUS_REQUEST = 4;
    private static final int MSG_MIX_STATE_UPDATE = 3;
    private static final int MSG_POLICY_STATUS_CHANGE = 0;
    private static final int MSG_VOL_ADJUST = 6;
    public static final int POLICY_STATUS_REGISTERED = 2;
    public static final int POLICY_STATUS_UNREGISTERED = 1;
    private static final java.lang.String TAG = "AudioPolicy";
    private static android.media.IAudioService sService;
    private java.util.ArrayList<java.lang.ref.WeakReference<android.media.AudioRecord>> mCaptors;
    private android.media.audiopolicy.AudioPolicyConfig mConfig;
    private android.content.Context mContext;
    private final android.media.audiopolicy.AudioPolicy.EventHandler mEventHandler;
    private android.media.audiopolicy.AudioPolicy.AudioPolicyFocusListener mFocusListener;
    private java.util.ArrayList<java.lang.ref.WeakReference<android.media.AudioTrack>> mInjectors;
    private final boolean mIsFocusPolicy;
    private final boolean mIsTestFocusPolicy;
    private final java.lang.Object mLock;
    private final android.media.audiopolicy.IAudioPolicyCallback mPolicyCb;
    private final android.media.projection.MediaProjection mProjection;
    private java.lang.String mRegistrationId;
    private int mStatus;
    private final android.media.audiopolicy.AudioPolicy.AudioPolicyStatusListener mStatusListener;
    private final android.media.audiopolicy.AudioPolicy.AudioPolicyVolumeCallback mVolCb;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PolicyStatus {
    }

    public android.media.audiopolicy.AudioPolicyConfig getConfig() {
        return this.mConfig;
    }

    public boolean hasFocusListener() {
        return this.mFocusListener != null;
    }

    public boolean isFocusPolicy() {
        return this.mIsFocusPolicy;
    }

    public boolean isTestFocusPolicy() {
        return this.mIsTestFocusPolicy;
    }

    public boolean isVolumeController() {
        return this.mVolCb != null;
    }

    public android.media.projection.MediaProjection getMediaProjection() {
        return this.mProjection;
    }

    private AudioPolicy(android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig, android.content.Context context, android.os.Looper looper, android.media.audiopolicy.AudioPolicy.AudioPolicyFocusListener audioPolicyFocusListener, android.media.audiopolicy.AudioPolicy.AudioPolicyStatusListener audioPolicyStatusListener, boolean z, boolean z2, android.media.audiopolicy.AudioPolicy.AudioPolicyVolumeCallback audioPolicyVolumeCallback, android.media.projection.MediaProjection mediaProjection) {
        this.mLock = new java.lang.Object();
        this.mPolicyCb = new android.media.audiopolicy.IAudioPolicyCallback.Stub() { // from class: android.media.audiopolicy.AudioPolicy.1
            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusGrant(android.media.AudioFocusInfo audioFocusInfo, int i) {
                android.media.audiopolicy.AudioPolicy.this.sendMsg(1, audioFocusInfo, i);
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusLoss(android.media.AudioFocusInfo audioFocusInfo, boolean z3) {
                android.media.audiopolicy.AudioPolicy.this.sendMsg(2, audioFocusInfo, z3 ? 1 : 0);
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusRequest(android.media.AudioFocusInfo audioFocusInfo, int i) {
                android.media.audiopolicy.AudioPolicy.this.sendMsg(4, audioFocusInfo, i);
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyAudioFocusAbandon(android.media.AudioFocusInfo audioFocusInfo) {
                android.media.audiopolicy.AudioPolicy.this.sendMsg(5, audioFocusInfo, 0);
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyMixStateUpdate(java.lang.String str, int i) {
                java.util.Iterator<android.media.audiopolicy.AudioMix> it = android.media.audiopolicy.AudioPolicy.this.mConfig.getMixes().iterator();
                while (it.hasNext()) {
                    android.media.audiopolicy.AudioMix next = it.next();
                    if (next.getRegistration().equals(str)) {
                        next.mMixState = i;
                        android.media.audiopolicy.AudioPolicy.this.sendMsg(3, next, 0);
                    }
                }
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyVolumeAdjust(int i) {
                android.media.audiopolicy.AudioPolicy.this.sendMsg(6, null, i);
            }

            @Override // android.media.audiopolicy.IAudioPolicyCallback
            public void notifyUnregistration() {
                android.media.audiopolicy.AudioPolicy.this.setRegistration(null);
            }
        };
        this.mConfig = audioPolicyConfig;
        this.mStatus = 1;
        this.mContext = context;
        looper = looper == null ? android.os.Looper.getMainLooper() : looper;
        if (looper != null) {
            this.mEventHandler = new android.media.audiopolicy.AudioPolicy.EventHandler(this, looper);
        } else {
            this.mEventHandler = null;
            android.util.Log.e(TAG, "No event handler due to looper without a thread");
        }
        this.mFocusListener = audioPolicyFocusListener;
        this.mStatusListener = audioPolicyStatusListener;
        this.mIsFocusPolicy = z;
        this.mIsTestFocusPolicy = z2;
        this.mVolCb = audioPolicyVolumeCallback;
        this.mProjection = mediaProjection;
    }

    public static class Builder {
        private android.content.Context mContext;
        private android.media.audiopolicy.AudioPolicy.AudioPolicyFocusListener mFocusListener;
        private android.os.Looper mLooper;
        private android.media.projection.MediaProjection mProjection;
        private android.media.audiopolicy.AudioPolicy.AudioPolicyStatusListener mStatusListener;
        private android.media.audiopolicy.AudioPolicy.AudioPolicyVolumeCallback mVolCb;
        private boolean mIsFocusPolicy = false;
        private boolean mIsTestFocusPolicy = false;
        private java.util.ArrayList<android.media.audiopolicy.AudioMix> mMixes = new java.util.ArrayList<>();

        public Builder(android.content.Context context) {
            this.mContext = context;
        }

        public android.media.audiopolicy.AudioPolicy.Builder addMix(android.media.audiopolicy.AudioMix audioMix) throws java.lang.IllegalArgumentException {
            if (audioMix == null) {
                throw new java.lang.IllegalArgumentException("Illegal null AudioMix argument");
            }
            this.mMixes.add(audioMix);
            return this;
        }

        public android.media.audiopolicy.AudioPolicy.Builder setLooper(android.os.Looper looper) throws java.lang.IllegalArgumentException {
            if (looper == null) {
                throw new java.lang.IllegalArgumentException("Illegal null Looper argument");
            }
            this.mLooper = looper;
            return this;
        }

        public void setAudioPolicyFocusListener(android.media.audiopolicy.AudioPolicy.AudioPolicyFocusListener audioPolicyFocusListener) {
            this.mFocusListener = audioPolicyFocusListener;
        }

        public android.media.audiopolicy.AudioPolicy.Builder setIsAudioFocusPolicy(boolean z) {
            this.mIsFocusPolicy = z;
            return this;
        }

        public android.media.audiopolicy.AudioPolicy.Builder setIsTestFocusPolicy(boolean z) {
            this.mIsTestFocusPolicy = z;
            return this;
        }

        public void setAudioPolicyStatusListener(android.media.audiopolicy.AudioPolicy.AudioPolicyStatusListener audioPolicyStatusListener) {
            this.mStatusListener = audioPolicyStatusListener;
        }

        public android.media.audiopolicy.AudioPolicy.Builder setAudioPolicyVolumeCallback(android.media.audiopolicy.AudioPolicy.AudioPolicyVolumeCallback audioPolicyVolumeCallback) {
            if (audioPolicyVolumeCallback == null) {
                throw new java.lang.IllegalArgumentException("Invalid null volume callback");
            }
            this.mVolCb = audioPolicyVolumeCallback;
            return this;
        }

        public android.media.audiopolicy.AudioPolicy.Builder setMediaProjection(android.media.projection.MediaProjection mediaProjection) {
            if (mediaProjection == null) {
                throw new java.lang.IllegalArgumentException("Invalid null volume callback");
            }
            this.mProjection = mediaProjection;
            return this;
        }

        public android.media.audiopolicy.AudioPolicy build() {
            if (this.mStatusListener != null) {
                java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mMixes.iterator();
                while (it.hasNext()) {
                    it.next().mCallbackFlags |= 1;
                }
            }
            if (this.mIsFocusPolicy && this.mFocusListener == null) {
                throw new java.lang.IllegalStateException("Cannot be a focus policy without an AudioPolicyFocusListener");
            }
            return new android.media.audiopolicy.AudioPolicy(new android.media.audiopolicy.AudioPolicyConfig(this.mMixes), this.mContext, this.mLooper, this.mFocusListener, this.mStatusListener, this.mIsFocusPolicy, this.mIsTestFocusPolicy, this.mVolCb, this.mProjection);
        }
    }

    public int attachMixes(java.util.List<android.media.audiopolicy.AudioMix> list) {
        int addMixForPolicy;
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Illegal null list of AudioMix");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot alter unregistered AudioPolicy");
            }
            java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList = new java.util.ArrayList<>(list.size());
            for (android.media.audiopolicy.AudioMix audioMix : list) {
                if (audioMix == null) {
                    throw new java.lang.IllegalArgumentException("Illegal null AudioMix in attachMixes");
                }
                arrayList.add(audioMix);
            }
            android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig = new android.media.audiopolicy.AudioPolicyConfig(arrayList);
            try {
                addMixForPolicy = getService().addMixForPolicy(audioPolicyConfig, cb());
                if (addMixForPolicy == 0) {
                    this.mConfig.add(arrayList);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in attachMixes", e);
                return -1;
            }
        }
        return addMixForPolicy;
    }

    public int detachMixes(java.util.List<android.media.audiopolicy.AudioMix> list) {
        int removeMixForPolicy;
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Illegal null list of AudioMix");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot alter unregistered AudioPolicy");
            }
            java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList = new java.util.ArrayList<>(list.size());
            for (android.media.audiopolicy.AudioMix audioMix : list) {
                if (audioMix == null) {
                    throw new java.lang.IllegalArgumentException("Illegal null AudioMix in detachMixes");
                }
                arrayList.add(audioMix);
            }
            android.media.audiopolicy.AudioPolicyConfig audioPolicyConfig = new android.media.audiopolicy.AudioPolicyConfig(arrayList);
            try {
                removeMixForPolicy = getService().removeMixForPolicy(audioPolicyConfig, cb());
                if (removeMixForPolicy == 0) {
                    this.mConfig.remove(arrayList);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in detachMixes", e);
                return -1;
            }
        }
        return removeMixForPolicy;
    }

    public int updateMixingRules(java.util.List<android.util.Pair<android.media.audiopolicy.AudioMix, android.media.audiopolicy.AudioMixingRule>> list) {
        int updateMixingRulesForPolicy;
        java.util.Objects.requireNonNull(list);
        android.media.IAudioService service = getService();
        try {
            synchronized (this.mLock) {
                updateMixingRulesForPolicy = service.updateMixingRulesForPolicy((android.media.audiopolicy.AudioMix[]) list.stream().map(new java.util.function.Function() { // from class: android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.audiopolicy.AudioPolicy.lambda$updateMixingRules$0((android.util.Pair) obj);
                    }
                }).toArray(new java.util.function.IntFunction() { // from class: android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda2
                    @Override // java.util.function.IntFunction
                    public final java.lang.Object apply(int i) {
                        return android.media.audiopolicy.AudioPolicy.lambda$updateMixingRules$1(i);
                    }
                }), (android.media.audiopolicy.AudioMixingRule[]) list.stream().map(new java.util.function.Function() { // from class: android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final java.lang.Object apply(java.lang.Object obj) {
                        return android.media.audiopolicy.AudioPolicy.lambda$updateMixingRules$2((android.util.Pair) obj);
                    }
                }).toArray(new java.util.function.IntFunction() { // from class: android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda4
                    @Override // java.util.function.IntFunction
                    public final java.lang.Object apply(int i) {
                        return android.media.audiopolicy.AudioPolicy.lambda$updateMixingRules$3(i);
                    }
                }), cb());
                if (updateMixingRulesForPolicy == 0) {
                    this.mConfig.updateMixingRules(list);
                }
            }
            return updateMixingRulesForPolicy;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Received remote exeception in updateMixingRules call: ", e);
            return -1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ android.media.audiopolicy.AudioMix lambda$updateMixingRules$0(android.util.Pair pair) {
        return (android.media.audiopolicy.AudioMix) pair.first;
    }

    static /* synthetic */ android.media.audiopolicy.AudioMix[] lambda$updateMixingRules$1(int i) {
        return new android.media.audiopolicy.AudioMix[i];
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ android.media.audiopolicy.AudioMixingRule lambda$updateMixingRules$2(android.util.Pair pair) {
        return (android.media.audiopolicy.AudioMixingRule) pair.second;
    }

    static /* synthetic */ android.media.audiopolicy.AudioMixingRule[] lambda$updateMixingRules$3(int i) {
        return new android.media.audiopolicy.AudioMixingRule[i];
    }

    @android.annotation.SystemApi
    public boolean setUidDeviceAffinity(int i, java.util.List<android.media.AudioDeviceInfo> list) {
        boolean z;
        if (list == null) {
            throw new java.lang.IllegalArgumentException("Illegal null list of audio devices");
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            int[] iArr = new int[list.size()];
            java.lang.String[] strArr = new java.lang.String[list.size()];
            int i2 = 0;
            for (android.media.AudioDeviceInfo audioDeviceInfo : list) {
                if (audioDeviceInfo == null) {
                    throw new java.lang.IllegalArgumentException("Illegal null AudioDeviceInfo in setUidDeviceAffinity");
                }
                iArr[i2] = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
                strArr[i2] = audioDeviceInfo.getAddress();
                i2++;
            }
            try {
                z = getService().setUidDeviceAffinity(cb(), i, iArr, strArr) == 0;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in setUidDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @android.annotation.SystemApi
    public boolean removeUidDeviceAffinity(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            try {
                z = getService().removeUidDeviceAffinity(cb(), i) == 0;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in removeUidDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @android.annotation.SystemApi
    public boolean removeUserIdDeviceAffinity(int i) {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            try {
                z = getService().removeUserIdDeviceAffinity(cb(), i) == 0;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in removeUserIdDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    @android.annotation.SystemApi
    public boolean setUserIdDeviceAffinity(int i, java.util.List<android.media.AudioDeviceInfo> list) {
        boolean z;
        java.util.Objects.requireNonNull(list, "Illegal null list of audio devices");
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot use unregistered AudioPolicy");
            }
            int[] iArr = new int[list.size()];
            java.lang.String[] strArr = new java.lang.String[list.size()];
            int i2 = 0;
            for (android.media.AudioDeviceInfo audioDeviceInfo : list) {
                if (audioDeviceInfo == null) {
                    throw new java.lang.IllegalArgumentException("Illegal null AudioDeviceInfo in setUserIdDeviceAffinity");
                }
                iArr[i2] = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(audioDeviceInfo.getType());
                strArr[i2] = audioDeviceInfo.getAddress();
                i2++;
            }
            try {
                z = getService().setUserIdDeviceAffinity(cb(), i, iArr, strArr) == 0;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in setUserIdDeviceAffinity", e);
                return false;
            }
        }
        return z;
    }

    public void reset() {
        setRegistration(null);
    }

    public java.util.List<android.media.audiopolicy.AudioMix> getMixes() {
        java.util.List<android.media.audiopolicy.AudioMix> copyOf;
        if (!android.media.audiopolicy.Flags.audioMixTestApi()) {
            return java.util.Collections.emptyList();
        }
        synchronized (this.mLock) {
            copyOf = java.util.List.copyOf(this.mConfig.getMixes());
        }
        return copyOf;
    }

    public void setRegistration(java.lang.String str) {
        synchronized (this.mLock) {
            this.mRegistrationId = str;
            this.mConfig.setRegistration(str);
            if (str != null) {
                this.mStatus = 2;
            } else {
                this.mStatus = 1;
                this.mConfig.reset();
            }
        }
        sendMsg(0);
    }

    public java.lang.String getRegistration() {
        return this.mRegistrationId;
    }

    @android.annotation.SystemApi
    public int setFadeManagerConfigurationForFocusLoss(android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        int fadeManagerConfigurationForFocusLoss;
        java.util.Objects.requireNonNull(fadeManagerConfiguration, "FadeManagerConfiguration for focus loss cannot be null");
        android.media.IAudioService service = getService();
        synchronized (this.mLock) {
            com.android.internal.util.Preconditions.checkState(isAudioPolicyRegisteredLocked(), "Cannot set FadeManagerConfiguration with unregistered AudioPolicy");
            try {
                fadeManagerConfigurationForFocusLoss = service.setFadeManagerConfigurationForFocusLoss(fadeManagerConfiguration);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Received remote exception for setFadeManagerConfigurationForFocusLoss:", e);
                throw e.rethrowFromSystemServer();
            }
        }
        return fadeManagerConfigurationForFocusLoss;
    }

    @android.annotation.SystemApi
    public int clearFadeManagerConfigurationForFocusLoss() {
        int clearFadeManagerConfigurationForFocusLoss;
        android.media.IAudioService service = getService();
        synchronized (this.mLock) {
            com.android.internal.util.Preconditions.checkState(isAudioPolicyRegisteredLocked(), "Cannot clear FadeManagerConfiguration from unregistered AudioPolicy");
            try {
                clearFadeManagerConfigurationForFocusLoss = service.clearFadeManagerConfigurationForFocusLoss();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Received remote exception for clearFadeManagerConfigurationForFocusLoss:", e);
                throw e.rethrowFromSystemServer();
            }
        }
        return clearFadeManagerConfigurationForFocusLoss;
    }

    @android.annotation.SystemApi
    public android.media.FadeManagerConfiguration getFadeManagerConfigurationForFocusLoss() {
        android.media.FadeManagerConfiguration fadeManagerConfigurationForFocusLoss;
        android.media.IAudioService service = getService();
        synchronized (this.mLock) {
            com.android.internal.util.Preconditions.checkState(isAudioPolicyRegisteredLocked(), "Cannot get FadeManagerConfiguration from unregistered AudioPolicy");
            try {
                fadeManagerConfigurationForFocusLoss = service.getFadeManagerConfigurationForFocusLoss();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Received remote exception for getFadeManagerConfigurationForFocusLoss:", e);
                throw e.rethrowFromSystemServer();
            }
        }
        return fadeManagerConfigurationForFocusLoss;
    }

    private boolean isAudioPolicyRegisteredLocked() {
        return this.mStatus == 2;
    }

    private boolean policyReadyToUse() {
        boolean z;
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                android.util.Log.e(TAG, "Cannot use unregistered AudioPolicy");
                return false;
            }
            if (this.mRegistrationId == null) {
                android.util.Log.e(TAG, "Cannot use unregistered AudioPolicy");
                return false;
            }
            boolean z2 = checkCallingOrSelfPermission(android.Manifest.permission.MODIFY_AUDIO_ROUTING) == 0;
            boolean z3 = checkCallingOrSelfPermission(android.Manifest.permission.CALL_AUDIO_INTERCEPTION) == 0;
            try {
                if (this.mProjection != null) {
                    if (this.mProjection.getProjection().canProjectAudio()) {
                        z = true;
                        if ((!isLoopbackRenderPolicy() && z) || ((isCallRedirectionPolicy() && z3) || z2)) {
                            return true;
                        }
                        android.util.Slog.w(TAG, "Cannot use AudioPolicy for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project audio.");
                        return false;
                    }
                }
                z = false;
                if (!isLoopbackRenderPolicy()) {
                }
                android.util.Slog.w(TAG, "Cannot use AudioPolicy for pid " + android.os.Binder.getCallingPid() + " / uid " + android.os.Binder.getCallingUid() + ", needs MODIFY_AUDIO_ROUTING or MediaProjection that can project audio.");
                return false;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Failed to check if MediaProjection#canProjectAudio");
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private boolean isLoopbackRenderPolicy() {
        boolean allMatch;
        synchronized (this.mLock) {
            allMatch = this.mConfig.mMixes.stream().allMatch(new java.util.function.Predicate() { // from class: android.media.audiopolicy.AudioPolicy$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.media.audiopolicy.AudioPolicy.lambda$isLoopbackRenderPolicy$4((android.media.audiopolicy.AudioMix) obj);
                }
            });
        }
        return allMatch;
    }

    static /* synthetic */ boolean lambda$isLoopbackRenderPolicy$4(android.media.audiopolicy.AudioMix audioMix) {
        return audioMix.getRouteFlags() == 3;
    }

    private boolean isCallRedirectionPolicy() {
        synchronized (this.mLock) {
            java.util.Iterator<android.media.audiopolicy.AudioMix> it = this.mConfig.mMixes.iterator();
            while (it.hasNext()) {
                if (it.next().isForCallRedirection()) {
                    return true;
                }
            }
            return false;
        }
    }

    private int checkCallingOrSelfPermission(java.lang.String str) {
        if (this.mContext != null) {
            return this.mContext.checkCallingOrSelfPermission(str);
        }
        android.util.Slog.v(TAG, "Null context, checking permission via ActivityManager");
        try {
            return android.app.ActivityManager.getService().checkPermission(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void checkMixReadyToUse(android.media.audiopolicy.AudioMix audioMix, boolean z) throws java.lang.IllegalArgumentException {
        if (audioMix == null) {
            throw new java.lang.IllegalArgumentException(z ? "Invalid null AudioMix for AudioTrack creation" : "Invalid null AudioMix for AudioRecord creation");
        }
        if (!this.mConfig.mMixes.contains(audioMix)) {
            throw new java.lang.IllegalArgumentException("Invalid mix: not part of this policy");
        }
        if ((audioMix.getRouteFlags() & 2) != 2) {
            throw new java.lang.IllegalArgumentException("Invalid AudioMix: not defined for loop back");
        }
        if (z && audioMix.getMixType() != 1) {
            throw new java.lang.IllegalArgumentException("Invalid AudioMix: not defined for being a recording source");
        }
        if (!z && audioMix.getMixType() != 0) {
            throw new java.lang.IllegalArgumentException("Invalid AudioMix: not defined for capturing playback");
        }
    }

    public int getFocusDuckingBehavior() {
        return this.mConfig.mDuckingPolicy;
    }

    public int setFocusDuckingBehavior(int i) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        int focusPropertiesForPolicy;
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid ducking behavior " + i);
        }
        synchronized (this.mLock) {
            if (this.mStatus != 2) {
                throw new java.lang.IllegalStateException("Cannot change ducking behavior for unregistered policy");
            }
            if (i == 1 && this.mFocusListener == null) {
                throw new java.lang.IllegalStateException("Cannot handle ducking without an audio focus listener");
            }
            try {
                focusPropertiesForPolicy = getService().setFocusPropertiesForPolicy(i, cb());
                if (focusPropertiesForPolicy == 0) {
                    this.mConfig.mDuckingPolicy = i;
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Dead object in setFocusPropertiesForPolicy for behavior", e);
                return -1;
            }
        }
        return focusPropertiesForPolicy;
    }

    public java.util.List<android.media.AudioFocusInfo> getFocusStack() {
        try {
            return getService().getFocusStack();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean sendFocusLoss(android.media.AudioFocusInfo audioFocusInfo) throws java.lang.IllegalStateException {
        java.util.Objects.requireNonNull(audioFocusInfo);
        try {
            return getService().sendFocusLoss(audioFocusInfo, cb());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.AudioRecord createAudioRecordSink(android.media.audiopolicy.AudioMix audioMix) throws java.lang.IllegalArgumentException {
        if (!policyReadyToUse()) {
            android.util.Log.e(TAG, "Cannot create AudioRecord sink for AudioMix");
            return null;
        }
        checkMixReadyToUse(audioMix, false);
        android.media.AudioFormat build = new android.media.AudioFormat.Builder(audioMix.getFormat()).setChannelMask(android.media.AudioFormat.inChannelMaskFromOutChannelMask(audioMix.getFormat().getChannelMask())).build();
        android.media.AudioAttributes.Builder addTag = new android.media.AudioAttributes.Builder().setInternalCapturePreset(8).addTag(addressForTag(audioMix)).addTag(android.media.AudioRecord.SUBMIX_FIXED_VOLUME);
        if (audioMix.isForCallRedirection()) {
            addTag.setForCallRedirection();
        }
        android.media.AudioRecord audioRecord = new android.media.AudioRecord(addTag.build(), build, android.media.AudioRecord.getMinBufferSize(audioMix.getFormat().getSampleRate(), 12, audioMix.getFormat().getEncoding()), 0);
        synchronized (this.mLock) {
            if (this.mCaptors == null) {
                this.mCaptors = new java.util.ArrayList<>(1);
            }
            this.mCaptors.add(new java.lang.ref.WeakReference<>(audioRecord));
        }
        return audioRecord;
    }

    public android.media.AudioTrack createAudioTrackSource(android.media.audiopolicy.AudioMix audioMix) throws java.lang.IllegalArgumentException {
        if (!policyReadyToUse()) {
            android.util.Log.e(TAG, "Cannot create AudioTrack source for AudioMix");
            return null;
        }
        checkMixReadyToUse(audioMix, true);
        android.media.AudioAttributes.Builder addTag = new android.media.AudioAttributes.Builder().setUsage(15).addTag(addressForTag(audioMix));
        if (audioMix.isForCallRedirection()) {
            addTag.setForCallRedirection();
        }
        android.media.AudioTrack audioTrack = new android.media.AudioTrack(addTag.build(), audioMix.getFormat(), android.media.AudioTrack.getMinBufferSize(audioMix.getFormat().getSampleRate(), audioMix.getFormat().getChannelMask(), audioMix.getFormat().getEncoding()), 1, 0);
        synchronized (this.mLock) {
            if (this.mInjectors == null) {
                this.mInjectors = new java.util.ArrayList<>(1);
            }
            this.mInjectors.add(new java.lang.ref.WeakReference<>(audioTrack));
        }
        return audioTrack;
    }

    public void invalidateCaptorsAndInjectors() {
        if (!policyReadyToUse()) {
            return;
        }
        synchronized (this.mLock) {
            if (this.mInjectors != null) {
                java.util.Iterator<java.lang.ref.WeakReference<android.media.AudioTrack>> it = this.mInjectors.iterator();
                while (it.hasNext()) {
                    android.media.AudioTrack audioTrack = it.next().get();
                    if (audioTrack != null) {
                        try {
                            audioTrack.stop();
                            audioTrack.flush();
                        } catch (java.lang.IllegalStateException e) {
                        }
                    }
                }
                this.mInjectors.clear();
            }
            if (this.mCaptors != null) {
                java.util.Iterator<java.lang.ref.WeakReference<android.media.AudioRecord>> it2 = this.mCaptors.iterator();
                while (it2.hasNext()) {
                    android.media.AudioRecord audioRecord = it2.next().get();
                    if (audioRecord != null) {
                        try {
                            audioRecord.stop();
                        } catch (java.lang.IllegalStateException e2) {
                        }
                    }
                }
                this.mCaptors.clear();
            }
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public static abstract class AudioPolicyStatusListener {
        public void onStatusChange() {
        }

        public void onMixStateUpdate(android.media.audiopolicy.AudioMix audioMix) {
        }
    }

    public static abstract class AudioPolicyFocusListener {
        public void onAudioFocusGrant(android.media.AudioFocusInfo audioFocusInfo, int i) {
        }

        public void onAudioFocusLoss(android.media.AudioFocusInfo audioFocusInfo, boolean z) {
        }

        public void onAudioFocusRequest(android.media.AudioFocusInfo audioFocusInfo, int i) {
        }

        public void onAudioFocusAbandon(android.media.AudioFocusInfo audioFocusInfo) {
        }
    }

    public static abstract class AudioPolicyVolumeCallback {
        public void onVolumeAdjustment(int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPolicyStatusChange() {
        if (this.mStatusListener != null) {
            this.mStatusListener.onStatusChange();
        }
    }

    public android.media.audiopolicy.IAudioPolicyCallback cb() {
        return this.mPolicyCb;
    }

    private class EventHandler extends android.os.Handler {
        public EventHandler(android.media.audiopolicy.AudioPolicy audioPolicy, android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    android.media.audiopolicy.AudioPolicy.this.onPolicyStatusChange();
                    break;
                case 1:
                    if (android.media.audiopolicy.AudioPolicy.this.mFocusListener != null) {
                        android.media.audiopolicy.AudioPolicy.this.mFocusListener.onAudioFocusGrant((android.media.AudioFocusInfo) message.obj, message.arg1);
                        break;
                    }
                    break;
                case 2:
                    if (android.media.audiopolicy.AudioPolicy.this.mFocusListener != null) {
                        android.media.audiopolicy.AudioPolicy.this.mFocusListener.onAudioFocusLoss((android.media.AudioFocusInfo) message.obj, message.arg1 != 0);
                        break;
                    }
                    break;
                case 3:
                    if (android.media.audiopolicy.AudioPolicy.this.mStatusListener != null) {
                        android.media.audiopolicy.AudioPolicy.this.mStatusListener.onMixStateUpdate((android.media.audiopolicy.AudioMix) message.obj);
                        break;
                    }
                    break;
                case 4:
                    if (android.media.audiopolicy.AudioPolicy.this.mFocusListener != null) {
                        android.media.audiopolicy.AudioPolicy.this.mFocusListener.onAudioFocusRequest((android.media.AudioFocusInfo) message.obj, message.arg1);
                        break;
                    } else {
                        android.util.Log.e(android.media.audiopolicy.AudioPolicy.TAG, "Invalid null focus listener for focus request event");
                        break;
                    }
                case 5:
                    if (android.media.audiopolicy.AudioPolicy.this.mFocusListener != null) {
                        android.media.audiopolicy.AudioPolicy.this.mFocusListener.onAudioFocusAbandon((android.media.AudioFocusInfo) message.obj);
                        break;
                    } else {
                        android.util.Log.e(android.media.audiopolicy.AudioPolicy.TAG, "Invalid null focus listener for focus abandon event");
                        break;
                    }
                case 6:
                    if (android.media.audiopolicy.AudioPolicy.this.mVolCb != null) {
                        android.media.audiopolicy.AudioPolicy.this.mVolCb.onVolumeAdjustment(message.arg1);
                        break;
                    } else {
                        android.util.Log.e(android.media.audiopolicy.AudioPolicy.TAG, "Invalid null volume event");
                        break;
                    }
                default:
                    android.util.Log.e(android.media.audiopolicy.AudioPolicy.TAG, "Unknown event " + message.what);
                    break;
            }
        }
    }

    private static java.lang.String addressForTag(android.media.audiopolicy.AudioMix audioMix) {
        return "addr=" + audioMix.getRegistration();
    }

    private void sendMsg(int i) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendEmptyMessage(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMsg(int i, java.lang.Object obj, int i2) {
        if (this.mEventHandler != null) {
            this.mEventHandler.sendMessage(this.mEventHandler.obtainMessage(i, i2, 0, obj));
        }
    }

    private static android.media.IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        return sService;
    }

    public java.lang.String toLogFriendlyString() {
        return new java.lang.String("android.media.audiopolicy.AudioPolicy:\n") + "config=" + this.mConfig.toLogFriendlyString();
    }
}
