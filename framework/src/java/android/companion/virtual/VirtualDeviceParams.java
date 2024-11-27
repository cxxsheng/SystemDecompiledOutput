package android.companion.virtual;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class VirtualDeviceParams implements android.os.Parcelable {

    @java.lang.Deprecated
    public static final int ACTIVITY_POLICY_DEFAULT_ALLOWED = 0;

    @java.lang.Deprecated
    public static final int ACTIVITY_POLICY_DEFAULT_BLOCKED = 1;
    public static final android.os.Parcelable.Creator<android.companion.virtual.VirtualDeviceParams> CREATOR = new android.os.Parcelable.Creator<android.companion.virtual.VirtualDeviceParams>() { // from class: android.companion.virtual.VirtualDeviceParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.VirtualDeviceParams createFromParcel(android.os.Parcel parcel) {
            return new android.companion.virtual.VirtualDeviceParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.virtual.VirtualDeviceParams[] newArray(int i) {
            return new android.companion.virtual.VirtualDeviceParams[i];
        }
    };
    public static final int DEVICE_POLICY_CUSTOM = 1;
    public static final int DEVICE_POLICY_DEFAULT = 0;
    public static final int LOCK_STATE_ALWAYS_UNLOCKED = 1;
    public static final int LOCK_STATE_DEFAULT = 0;

    @java.lang.Deprecated
    public static final int NAVIGATION_POLICY_DEFAULT_ALLOWED = 0;

    @java.lang.Deprecated
    public static final int NAVIGATION_POLICY_DEFAULT_BLOCKED = 1;
    public static final int POLICY_TYPE_ACTIVITY = 3;
    public static final int POLICY_TYPE_AUDIO = 1;
    public static final int POLICY_TYPE_CAMERA = 5;
    public static final int POLICY_TYPE_CLIPBOARD = 4;
    public static final int POLICY_TYPE_RECENTS = 2;
    public static final int POLICY_TYPE_SENSORS = 0;
    private final android.util.ArraySet<android.content.ComponentName> mActivityPolicyExemptions;
    private final int mAudioPlaybackSessionId;
    private final int mAudioRecordingSessionId;
    private final android.util.ArraySet<android.content.ComponentName> mCrossTaskNavigationExemptions;
    private final int mDefaultActivityPolicy;
    private final int mDefaultNavigationPolicy;
    private final android.util.SparseIntArray mDevicePolicies;
    private final android.content.ComponentName mHomeComponent;
    private final android.content.ComponentName mInputMethodComponent;
    private final int mLockState;
    private final java.lang.String mName;
    private final android.util.ArraySet<android.os.UserHandle> mUsersWithMatchingAccounts;
    private final android.companion.virtual.sensor.IVirtualSensorCallback mVirtualSensorCallback;
    private final java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> mVirtualSensorConfigs;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActivityPolicy {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DevicePolicy {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DynamicPolicyType {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LockState {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NavigationPolicy {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PolicyType {
    }

    private VirtualDeviceParams(int i, java.util.Set<android.os.UserHandle> set, int i2, java.util.Set<android.content.ComponentName> set2, int i3, java.util.Set<android.content.ComponentName> set3, java.lang.String str, android.util.SparseIntArray sparseIntArray, android.content.ComponentName componentName, android.content.ComponentName componentName2, java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> list, android.companion.virtual.sensor.IVirtualSensorCallback iVirtualSensorCallback, int i4, int i5) {
        this.mLockState = i;
        this.mUsersWithMatchingAccounts = new android.util.ArraySet<>((java.util.Collection) java.util.Objects.requireNonNull(set));
        this.mDefaultNavigationPolicy = i2;
        this.mCrossTaskNavigationExemptions = new android.util.ArraySet<>((java.util.Collection) java.util.Objects.requireNonNull(set2));
        this.mDefaultActivityPolicy = i3;
        this.mActivityPolicyExemptions = new android.util.ArraySet<>((java.util.Collection) java.util.Objects.requireNonNull(set3));
        this.mName = str;
        this.mDevicePolicies = (android.util.SparseIntArray) java.util.Objects.requireNonNull(sparseIntArray);
        this.mHomeComponent = componentName;
        this.mInputMethodComponent = componentName2;
        this.mVirtualSensorConfigs = (java.util.List) java.util.Objects.requireNonNull(list);
        this.mVirtualSensorCallback = iVirtualSensorCallback;
        this.mAudioPlaybackSessionId = i4;
        this.mAudioRecordingSessionId = i5;
    }

    private VirtualDeviceParams(android.os.Parcel parcel) {
        this.mLockState = parcel.readInt();
        this.mUsersWithMatchingAccounts = parcel.readArraySet(null);
        this.mDefaultNavigationPolicy = parcel.readInt();
        this.mCrossTaskNavigationExemptions = parcel.readArraySet(null);
        this.mDefaultActivityPolicy = parcel.readInt();
        this.mActivityPolicyExemptions = parcel.readArraySet(null);
        this.mName = parcel.readString8();
        this.mDevicePolicies = parcel.readSparseIntArray();
        this.mVirtualSensorConfigs = new java.util.ArrayList();
        parcel.readTypedList(this.mVirtualSensorConfigs, android.companion.virtual.sensor.VirtualSensorConfig.CREATOR);
        this.mVirtualSensorCallback = android.companion.virtual.sensor.IVirtualSensorCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mAudioPlaybackSessionId = parcel.readInt();
        this.mAudioRecordingSessionId = parcel.readInt();
        this.mHomeComponent = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        this.mInputMethodComponent = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
    }

    public int getLockState() {
        return this.mLockState;
    }

    public android.content.ComponentName getHomeComponent() {
        return this.mHomeComponent;
    }

    public android.content.ComponentName getInputMethodComponent() {
        return this.mInputMethodComponent;
    }

    public java.util.Set<android.os.UserHandle> getUsersWithMatchingAccounts() {
        return java.util.Collections.unmodifiableSet(this.mUsersWithMatchingAccounts);
    }

    @java.lang.Deprecated
    public java.util.Set<android.content.ComponentName> getAllowedCrossTaskNavigations() {
        if (this.mDefaultNavigationPolicy == 0) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(this.mCrossTaskNavigationExemptions);
    }

    @java.lang.Deprecated
    public java.util.Set<android.content.ComponentName> getBlockedCrossTaskNavigations() {
        if (this.mDefaultNavigationPolicy == 1) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(this.mCrossTaskNavigationExemptions);
    }

    @java.lang.Deprecated
    public int getDefaultNavigationPolicy() {
        return this.mDefaultNavigationPolicy;
    }

    @java.lang.Deprecated
    public java.util.Set<android.content.ComponentName> getAllowedActivities() {
        if (this.mDefaultActivityPolicy == 0) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(this.mActivityPolicyExemptions);
    }

    @java.lang.Deprecated
    public java.util.Set<android.content.ComponentName> getBlockedActivities() {
        if (this.mDefaultActivityPolicy == 1) {
            return java.util.Collections.emptySet();
        }
        return java.util.Collections.unmodifiableSet(this.mActivityPolicyExemptions);
    }

    @java.lang.Deprecated
    public int getDefaultActivityPolicy() {
        return this.mDefaultActivityPolicy;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getDevicePolicy(int i) {
        return this.mDevicePolicies.get(i, 0);
    }

    public android.util.SparseIntArray getDevicePolicies() {
        return this.mDevicePolicies;
    }

    public java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> getVirtualSensorConfigs() {
        return this.mVirtualSensorConfigs;
    }

    public android.companion.virtual.sensor.IVirtualSensorCallback getVirtualSensorCallback() {
        return this.mVirtualSensorCallback;
    }

    public int getAudioPlaybackSessionId() {
        return this.mAudioPlaybackSessionId;
    }

    public int getAudioRecordingSessionId() {
        return this.mAudioRecordingSessionId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLockState);
        parcel.writeArraySet(this.mUsersWithMatchingAccounts);
        parcel.writeInt(this.mDefaultNavigationPolicy);
        parcel.writeArraySet(this.mCrossTaskNavigationExemptions);
        parcel.writeInt(this.mDefaultActivityPolicy);
        parcel.writeArraySet(this.mActivityPolicyExemptions);
        parcel.writeString8(this.mName);
        parcel.writeSparseIntArray(this.mDevicePolicies);
        parcel.writeTypedList(this.mVirtualSensorConfigs);
        parcel.writeStrongBinder(this.mVirtualSensorCallback != null ? this.mVirtualSensorCallback.asBinder() : null);
        parcel.writeInt(this.mAudioPlaybackSessionId);
        parcel.writeInt(this.mAudioRecordingSessionId);
        parcel.writeTypedObject(this.mHomeComponent, i);
        parcel.writeTypedObject(this.mInputMethodComponent, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.companion.virtual.VirtualDeviceParams)) {
            return false;
        }
        android.companion.virtual.VirtualDeviceParams virtualDeviceParams = (android.companion.virtual.VirtualDeviceParams) obj;
        int size = this.mDevicePolicies.size();
        if (size != virtualDeviceParams.mDevicePolicies.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.mDevicePolicies.keyAt(i) != virtualDeviceParams.mDevicePolicies.keyAt(i) || this.mDevicePolicies.valueAt(i) != virtualDeviceParams.mDevicePolicies.valueAt(i)) {
                return false;
            }
        }
        return this.mLockState == virtualDeviceParams.mLockState && this.mUsersWithMatchingAccounts.equals(virtualDeviceParams.mUsersWithMatchingAccounts) && java.util.Objects.equals(this.mCrossTaskNavigationExemptions, virtualDeviceParams.mCrossTaskNavigationExemptions) && this.mDefaultNavigationPolicy == virtualDeviceParams.mDefaultNavigationPolicy && java.util.Objects.equals(this.mActivityPolicyExemptions, virtualDeviceParams.mActivityPolicyExemptions) && this.mDefaultActivityPolicy == virtualDeviceParams.mDefaultActivityPolicy && java.util.Objects.equals(this.mName, virtualDeviceParams.mName) && java.util.Objects.equals(this.mHomeComponent, virtualDeviceParams.mHomeComponent) && java.util.Objects.equals(this.mInputMethodComponent, virtualDeviceParams.mInputMethodComponent) && this.mAudioPlaybackSessionId == virtualDeviceParams.mAudioPlaybackSessionId && this.mAudioRecordingSessionId == virtualDeviceParams.mAudioRecordingSessionId;
    }

    public int hashCode() {
        int hash = java.util.Objects.hash(java.lang.Integer.valueOf(this.mLockState), this.mUsersWithMatchingAccounts, this.mCrossTaskNavigationExemptions, java.lang.Integer.valueOf(this.mDefaultNavigationPolicy), this.mActivityPolicyExemptions, java.lang.Integer.valueOf(this.mDefaultActivityPolicy), this.mName, this.mDevicePolicies, this.mHomeComponent, this.mInputMethodComponent, java.lang.Integer.valueOf(this.mAudioPlaybackSessionId), java.lang.Integer.valueOf(this.mAudioRecordingSessionId));
        for (int i = 0; i < this.mDevicePolicies.size(); i++) {
            hash = (((hash * 31) + this.mDevicePolicies.keyAt(i)) * 31) + this.mDevicePolicies.valueAt(i);
        }
        return hash;
    }

    public java.lang.String toString() {
        return "VirtualDeviceParams( mLockState=" + this.mLockState + " mUsersWithMatchingAccounts=" + this.mUsersWithMatchingAccounts + " mDefaultNavigationPolicy=" + this.mDefaultNavigationPolicy + " mCrossTaskNavigationExemptions=" + this.mCrossTaskNavigationExemptions + " mDefaultActivityPolicy=" + this.mDefaultActivityPolicy + " mActivityPolicyExemptions=" + this.mActivityPolicyExemptions + " mName=" + this.mName + " mDevicePolicies=" + this.mDevicePolicies + " mHomeComponent=" + this.mHomeComponent + " mInputMethodComponent=" + this.mInputMethodComponent + " mAudioPlaybackSessionId=" + this.mAudioPlaybackSessionId + " mAudioRecordingSessionId=" + this.mAudioRecordingSessionId + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "mName=" + this.mName);
        printWriter.println(str + "mLockState=" + this.mLockState);
        printWriter.println(str + "mUsersWithMatchingAccounts=" + this.mUsersWithMatchingAccounts);
        printWriter.println(str + "mDefaultNavigationPolicy=" + this.mDefaultNavigationPolicy);
        printWriter.println(str + "mCrossTaskNavigationExemptions=" + this.mCrossTaskNavigationExemptions);
        printWriter.println(str + "mDefaultActivityPolicy=" + this.mDefaultActivityPolicy);
        printWriter.println(str + "mActivityPolicyExemptions=" + this.mActivityPolicyExemptions);
        printWriter.println(str + "mDevicePolicies=" + this.mDevicePolicies);
        printWriter.println(str + "mVirtualSensorConfigs=" + this.mVirtualSensorConfigs);
        printWriter.println(str + "mHomeComponent=" + this.mHomeComponent);
        printWriter.println(str + "mInputMethodComponent=" + this.mInputMethodComponent);
        printWriter.println(str + "mAudioPlaybackSessionId=" + this.mAudioPlaybackSessionId);
        printWriter.println(str + "mAudioRecordingSessionId=" + this.mAudioRecordingSessionId);
    }

    public static final class Builder {
        private android.content.ComponentName mHomeComponent;
        private android.content.ComponentName mInputMethodComponent;
        private java.lang.String mName;
        private android.companion.virtual.sensor.VirtualSensorCallback mVirtualSensorCallback;
        private java.util.concurrent.Executor mVirtualSensorCallbackExecutor;
        private android.companion.virtual.sensor.VirtualSensorDirectChannelCallback mVirtualSensorDirectChannelCallback;
        private java.util.concurrent.Executor mVirtualSensorDirectChannelCallbackExecutor;
        private int mLockState = 0;
        private java.util.Set<android.os.UserHandle> mUsersWithMatchingAccounts = java.util.Collections.emptySet();
        private java.util.Set<android.content.ComponentName> mCrossTaskNavigationExemptions = java.util.Collections.emptySet();
        private int mDefaultNavigationPolicy = 0;
        private boolean mDefaultNavigationPolicyConfigured = false;
        private java.util.Set<android.content.ComponentName> mActivityPolicyExemptions = java.util.Collections.emptySet();
        private int mDefaultActivityPolicy = 0;
        private boolean mDefaultActivityPolicyConfigured = false;
        private final android.util.SparseIntArray mDevicePolicies = new android.util.SparseIntArray();
        private int mAudioPlaybackSessionId = 0;
        private int mAudioRecordingSessionId = 0;
        private final java.util.List<android.companion.virtual.sensor.VirtualSensorConfig> mVirtualSensorConfigs = new java.util.ArrayList();

        /* JADX INFO: Access modifiers changed from: private */
        static class VirtualSensorCallbackDelegate extends android.companion.virtual.sensor.IVirtualSensorCallback.Stub {
            private final android.companion.virtual.sensor.VirtualSensorCallback mCallback;
            private final android.companion.virtual.sensor.VirtualSensorDirectChannelCallback mDirectChannelCallback;
            private final java.util.concurrent.Executor mDirectChannelExecutor;
            private final java.util.concurrent.Executor mExecutor;

            VirtualSensorCallbackDelegate(java.util.concurrent.Executor executor, android.companion.virtual.sensor.VirtualSensorCallback virtualSensorCallback, java.util.concurrent.Executor executor2, android.companion.virtual.sensor.VirtualSensorDirectChannelCallback virtualSensorDirectChannelCallback) {
                this.mExecutor = executor;
                this.mCallback = virtualSensorCallback;
                this.mDirectChannelExecutor = executor2;
                this.mDirectChannelCallback = virtualSensorDirectChannelCallback;
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onConfigurationChanged(final android.companion.virtual.sensor.VirtualSensor virtualSensor, final boolean z, int i, int i2) {
                final java.time.Duration ofNanos = java.time.Duration.ofNanos(java.util.concurrent.TimeUnit.MICROSECONDS.toNanos(i));
                final java.time.Duration ofNanos2 = java.time.Duration.ofNanos(java.util.concurrent.TimeUnit.MICROSECONDS.toNanos(i2));
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onConfigurationChanged$0(virtualSensor, z, ofNanos, ofNanos2);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onConfigurationChanged$0(android.companion.virtual.sensor.VirtualSensor virtualSensor, boolean z, java.time.Duration duration, java.time.Duration duration2) {
                this.mCallback.onConfigurationChanged(virtualSensor, z, duration, duration2);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelCreated(final int i, final android.os.SharedMemory sharedMemory) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelCreated$1(i, sharedMemory);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelCreated$1(int i, android.os.SharedMemory sharedMemory) {
                this.mDirectChannelCallback.onDirectChannelCreated(i, sharedMemory);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelDestroyed(final int i) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelDestroyed$2(i);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelDestroyed$2(int i) {
                this.mDirectChannelCallback.onDirectChannelDestroyed(i);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelConfigured(final int i, final android.companion.virtual.sensor.VirtualSensor virtualSensor, final int i2, final int i3) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelConfigured$3(i, virtualSensor, i2, i3);
                        }
                    });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelConfigured$3(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3) {
                this.mDirectChannelCallback.onDirectChannelConfigured(i, virtualSensor, i2, i3);
            }
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setLockState(int i) {
            this.mLockState = i;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setHomeComponent(android.content.ComponentName componentName) {
            this.mHomeComponent = componentName;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setInputMethodComponent(android.content.ComponentName componentName) {
            this.mInputMethodComponent = componentName;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setUsersWithMatchingAccounts(java.util.Set<android.os.UserHandle> set) {
            this.mUsersWithMatchingAccounts = (java.util.Set) java.util.Objects.requireNonNull(set);
            return this;
        }

        @java.lang.Deprecated
        public android.companion.virtual.VirtualDeviceParams.Builder setAllowedCrossTaskNavigations(java.util.Set<android.content.ComponentName> set) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 1) {
                throw new java.lang.IllegalArgumentException("Allowed cross task navigations and blocked cross task navigations cannot  both be set.");
            }
            this.mDefaultNavigationPolicy = 1;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions = (java.util.Set) java.util.Objects.requireNonNull(set);
            return this;
        }

        @java.lang.Deprecated
        public android.companion.virtual.VirtualDeviceParams.Builder setBlockedCrossTaskNavigations(java.util.Set<android.content.ComponentName> set) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 0) {
                throw new java.lang.IllegalArgumentException("Allowed cross task navigation and blocked task navigation cannot  be set.");
            }
            this.mDefaultNavigationPolicy = 0;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions = (java.util.Set) java.util.Objects.requireNonNull(set);
            return this;
        }

        @java.lang.Deprecated
        public android.companion.virtual.VirtualDeviceParams.Builder setAllowedActivities(java.util.Set<android.content.ComponentName> set) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 1) {
                throw new java.lang.IllegalArgumentException("Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 1;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (java.util.Set) java.util.Objects.requireNonNull(set);
            return this;
        }

        @java.lang.Deprecated
        public android.companion.virtual.VirtualDeviceParams.Builder setBlockedActivities(java.util.Set<android.content.ComponentName> set) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 0) {
                throw new java.lang.IllegalArgumentException("Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 0;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (java.util.Set) java.util.Objects.requireNonNull(set);
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setDevicePolicy(int i, int i2) {
            this.mDevicePolicies.put(i, i2);
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder addVirtualSensorConfig(android.companion.virtual.sensor.VirtualSensorConfig virtualSensorConfig) {
            this.mVirtualSensorConfigs.add((android.companion.virtual.sensor.VirtualSensorConfig) java.util.Objects.requireNonNull(virtualSensorConfig));
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setVirtualSensorCallback(java.util.concurrent.Executor executor, android.companion.virtual.sensor.VirtualSensorCallback virtualSensorCallback) {
            this.mVirtualSensorCallbackExecutor = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor);
            this.mVirtualSensorCallback = (android.companion.virtual.sensor.VirtualSensorCallback) java.util.Objects.requireNonNull(virtualSensorCallback);
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setVirtualSensorDirectChannelCallback(java.util.concurrent.Executor executor, android.companion.virtual.sensor.VirtualSensorDirectChannelCallback virtualSensorDirectChannelCallback) {
            this.mVirtualSensorDirectChannelCallbackExecutor = (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor);
            this.mVirtualSensorDirectChannelCallback = (android.companion.virtual.sensor.VirtualSensorDirectChannelCallback) java.util.Objects.requireNonNull(virtualSensorDirectChannelCallback);
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setAudioPlaybackSessionId(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Invalid playback audio session id");
            }
            this.mAudioPlaybackSessionId = i;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams.Builder setAudioRecordingSessionId(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Invalid recording audio session id");
            }
            this.mAudioRecordingSessionId = i;
            return this;
        }

        public android.companion.virtual.VirtualDeviceParams build() {
            android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate virtualSensorCallbackDelegate;
            if (this.mVirtualSensorConfigs.isEmpty()) {
                virtualSensorCallbackDelegate = null;
            } else {
                if (this.mDevicePolicies.get(0, 0) != 1) {
                    throw new java.lang.IllegalArgumentException("DEVICE_POLICY_CUSTOM for POLICY_TYPE_SENSORS is required for creating virtual sensors.");
                }
                if (this.mVirtualSensorCallback == null) {
                    throw new java.lang.IllegalArgumentException("VirtualSensorCallback is required for creating virtual sensors.");
                }
                int i = 0;
                while (true) {
                    if (i >= this.mVirtualSensorConfigs.size()) {
                        break;
                    }
                    if (this.mVirtualSensorConfigs.get(i).getDirectChannelTypesSupported() <= 0) {
                        i++;
                    } else if (this.mVirtualSensorDirectChannelCallback == null) {
                        throw new java.lang.IllegalArgumentException("VirtualSensorDirectChannelCallback is required for creating virtual sensors that support direct channel.");
                    }
                }
                virtualSensorCallbackDelegate = new android.companion.virtual.VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate(this.mVirtualSensorCallbackExecutor, this.mVirtualSensorCallback, this.mVirtualSensorDirectChannelCallbackExecutor, this.mVirtualSensorDirectChannelCallback);
            }
            if (android.companion.virtual.flags.Flags.dynamicPolicy()) {
                switch (this.mDevicePolicies.get(3, -1)) {
                    case 0:
                        if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 1) {
                            throw new java.lang.IllegalArgumentException("DEVICE_POLICY_DEFAULT is explicitly configured for POLICY_TYPE_ACTIVITY, which is exclusive with setAllowedActivities.");
                        }
                        break;
                    case 1:
                        if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 0) {
                            throw new java.lang.IllegalArgumentException("DEVICE_POLICY_CUSTOM is explicitly configured for POLICY_TYPE_ACTIVITY, which is exclusive with setBlockedActivities.");
                        }
                        break;
                    default:
                        if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 1) {
                            this.mDevicePolicies.put(3, 1);
                            break;
                        }
                        break;
                }
            }
            if (!android.companion.virtual.flags.Flags.crossDeviceClipboard()) {
                this.mDevicePolicies.delete(4);
            }
            if (!android.companion.virtual.flags.Flags.virtualCamera()) {
                this.mDevicePolicies.delete(5);
            }
            if ((this.mAudioPlaybackSessionId != 0 || this.mAudioRecordingSessionId != 0) && this.mDevicePolicies.get(1, 0) != 1) {
                throw new java.lang.IllegalArgumentException("DEVICE_POLICY_CUSTOM for POLICY_TYPE_AUDIO is required for configuration of device-specific audio session ids.");
            }
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            for (int i2 = 0; i2 < this.mVirtualSensorConfigs.size(); i2++) {
                android.companion.virtual.sensor.VirtualSensorConfig virtualSensorConfig = this.mVirtualSensorConfigs.get(i2);
                java.util.Set set = (java.util.Set) sparseArray.get(virtualSensorConfig.getType(), new android.util.ArraySet());
                if (!set.add(virtualSensorConfig.getName())) {
                    throw new java.lang.IllegalArgumentException("Sensor names must be unique for a particular sensor type.");
                }
                sparseArray.put(virtualSensorConfig.getType(), set);
            }
            return new android.companion.virtual.VirtualDeviceParams(this.mLockState, this.mUsersWithMatchingAccounts, this.mDefaultNavigationPolicy, this.mCrossTaskNavigationExemptions, this.mDefaultActivityPolicy, this.mActivityPolicyExemptions, this.mName, this.mDevicePolicies, this.mHomeComponent, this.mInputMethodComponent, this.mVirtualSensorConfigs, virtualSensorCallbackDelegate, this.mAudioPlaybackSessionId, this.mAudioRecordingSessionId);
        }
    }
}
