package android.app;

/* loaded from: classes.dex */
public class BroadcastOptions extends android.app.ComponentOptions {
    public static final long CHANGE_ALWAYS_DISABLED = 210856463;
    public static final long CHANGE_ALWAYS_ENABLED = 209888056;
    public static final long CHANGE_INVALID = Long.MIN_VALUE;
    public static final int DEFERRAL_POLICY_DEFAULT = 0;
    public static final int DEFERRAL_POLICY_NONE = 1;
    public static final int DEFERRAL_POLICY_UNTIL_ACTIVE = 2;
    public static final int DELIVERY_GROUP_POLICY_ALL = 0;
    public static final int DELIVERY_GROUP_POLICY_MERGED = 2;
    public static final int DELIVERY_GROUP_POLICY_MOST_RECENT = 1;
    private static final int FLAG_ALLOW_BACKGROUND_ACTIVITY_STARTS = 2;
    private static final int FLAG_DONT_SEND_TO_RESTRICTED_APPS = 1;
    private static final int FLAG_INTERACTIVE = 32;
    private static final int FLAG_IS_ALARM_BROADCAST = 8;
    private static final int FLAG_REQUIRE_COMPAT_CHANGE_ENABLED = 4;
    private static final int FLAG_SHARE_IDENTITY = 16;
    private static final java.lang.String KEY_DEFERRAL_POLICY = "android:broadcast.deferralPolicy";
    private static final java.lang.String KEY_DELIVERY_GROUP_EXTRAS_MERGER = "android:broadcast.deliveryGroupExtrasMerger";
    private static final java.lang.String KEY_DELIVERY_GROUP_KEY = "android:broadcast.deliveryGroupMatchingKey";
    private static final java.lang.String KEY_DELIVERY_GROUP_MATCHING_FILTER = "android:broadcast.deliveryGroupMatchingFilter";
    private static final java.lang.String KEY_DELIVERY_GROUP_NAMESPACE = "android:broadcast.deliveryGroupMatchingNamespace";
    private static final java.lang.String KEY_DELIVERY_GROUP_POLICY = "android:broadcast.deliveryGroupPolicy";
    private static final java.lang.String KEY_EVENT_TRIGGER_TIMESTAMP = "android:broadcast.eventTriggerTimestamp";
    private static final java.lang.String KEY_FLAGS = "android:broadcast.flags";
    private static final java.lang.String KEY_ID_FOR_RESPONSE_EVENT = "android:broadcast.idForResponseEvent";
    private static final java.lang.String KEY_MAX_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.maxManifestReceiverApiLevel";
    private static final java.lang.String KEY_MIN_MANIFEST_RECEIVER_API_LEVEL = "android:broadcast.minManifestReceiverApiLevel";
    private static final java.lang.String KEY_REMOTE_EVENT_TRIGGER_TIMESTAMP = "android:broadcast.remoteEventTriggerTimestamp";
    public static final java.lang.String KEY_REQUIRE_ALL_OF_PERMISSIONS = "android:broadcast.requireAllOfPermissions";
    private static final java.lang.String KEY_REQUIRE_COMPAT_CHANGE_ID = "android:broadcast.requireCompatChangeId";
    public static final java.lang.String KEY_REQUIRE_NONE_OF_PERMISSIONS = "android:broadcast.requireNoneOfPermissions";
    private static final java.lang.String KEY_TEMPORARY_APP_ALLOWLIST_DURATION = "android:broadcast.temporaryAppAllowlistDuration";
    private static final java.lang.String KEY_TEMPORARY_APP_ALLOWLIST_REASON = "android:broadcast.temporaryAppAllowlistReason";
    private static final java.lang.String KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE = "android:broadcast.temporaryAppAllowlistReasonCode";
    private static final java.lang.String KEY_TEMPORARY_APP_ALLOWLIST_TYPE = "android:broadcast.temporaryAppAllowlistType";

    @java.lang.Deprecated
    public static final int TEMPORARY_WHITELIST_TYPE_FOREGROUND_SERVICE_ALLOWED = 0;

    @java.lang.Deprecated
    public static final int TEMPORARY_WHITELIST_TYPE_FOREGROUND_SERVICE_NOT_ALLOWED = 1;
    private int mDeferralPolicy;
    private android.os.BundleMerger mDeliveryGroupExtrasMerger;
    private android.content.IntentFilter mDeliveryGroupMatchingFilter;
    private java.lang.String mDeliveryGroupMatchingKeyFragment;
    private java.lang.String mDeliveryGroupMatchingNamespaceFragment;
    private int mDeliveryGroupPolicy;
    private long mEventTriggerTimestampMillis;
    private int mFlags;
    private long mIdForResponseEvent;
    private int mMaxManifestReceiverApiLevel;
    private int mMinManifestReceiverApiLevel;
    private long mRemoteEventTriggerTimestampMillis;
    private java.lang.String[] mRequireAllOfPermissions;
    private long mRequireCompatChangeId;
    private java.lang.String[] mRequireNoneOfPermissions;
    private long mTemporaryAppAllowlistDuration;
    private java.lang.String mTemporaryAppAllowlistReason;
    private int mTemporaryAppAllowlistReasonCode;
    private int mTemporaryAppAllowlistType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeferralPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeliveryGroupPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static android.app.BroadcastOptions makeBasic() {
        return new android.app.BroadcastOptions();
    }

    public BroadcastOptions() {
        this.mMinManifestReceiverApiLevel = 0;
        this.mMaxManifestReceiverApiLevel = 10000;
        this.mRequireCompatChangeId = Long.MIN_VALUE;
        resetTemporaryAppAllowlist();
    }

    public BroadcastOptions(android.os.Bundle bundle) {
        super(bundle);
        this.mMinManifestReceiverApiLevel = 0;
        this.mMaxManifestReceiverApiLevel = 10000;
        this.mRequireCompatChangeId = Long.MIN_VALUE;
        this.mFlags = bundle.getInt(KEY_FLAGS, 0);
        if (bundle.containsKey(KEY_TEMPORARY_APP_ALLOWLIST_DURATION)) {
            this.mTemporaryAppAllowlistDuration = bundle.getLong(KEY_TEMPORARY_APP_ALLOWLIST_DURATION);
            this.mTemporaryAppAllowlistType = bundle.getInt(KEY_TEMPORARY_APP_ALLOWLIST_TYPE);
            this.mTemporaryAppAllowlistReasonCode = bundle.getInt(KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE, 0);
            this.mTemporaryAppAllowlistReason = bundle.getString(KEY_TEMPORARY_APP_ALLOWLIST_REASON);
        } else {
            resetTemporaryAppAllowlist();
        }
        this.mMinManifestReceiverApiLevel = bundle.getInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, 0);
        this.mMaxManifestReceiverApiLevel = bundle.getInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, 10000);
        this.mRequireAllOfPermissions = bundle.getStringArray(KEY_REQUIRE_ALL_OF_PERMISSIONS);
        this.mRequireNoneOfPermissions = bundle.getStringArray(KEY_REQUIRE_NONE_OF_PERMISSIONS);
        this.mRequireCompatChangeId = bundle.getLong(KEY_REQUIRE_COMPAT_CHANGE_ID, Long.MIN_VALUE);
        this.mIdForResponseEvent = bundle.getLong(KEY_ID_FOR_RESPONSE_EVENT);
        this.mEventTriggerTimestampMillis = bundle.getLong(KEY_EVENT_TRIGGER_TIMESTAMP);
        this.mRemoteEventTriggerTimestampMillis = bundle.getLong(KEY_REMOTE_EVENT_TRIGGER_TIMESTAMP);
        this.mDeliveryGroupPolicy = bundle.getInt(KEY_DELIVERY_GROUP_POLICY, 0);
        this.mDeliveryGroupMatchingNamespaceFragment = bundle.getString(KEY_DELIVERY_GROUP_NAMESPACE);
        this.mDeliveryGroupMatchingKeyFragment = bundle.getString(KEY_DELIVERY_GROUP_KEY);
        this.mDeliveryGroupExtrasMerger = (android.os.BundleMerger) bundle.getParcelable(KEY_DELIVERY_GROUP_EXTRAS_MERGER, android.os.BundleMerger.class);
        this.mDeliveryGroupMatchingFilter = (android.content.IntentFilter) bundle.getParcelable(KEY_DELIVERY_GROUP_MATCHING_FILTER, android.content.IntentFilter.class);
        this.mDeferralPolicy = bundle.getInt(KEY_DEFERRAL_POLICY, 0);
    }

    public static android.app.BroadcastOptions makeWithDeferUntilActive(boolean z) {
        android.app.BroadcastOptions makeBasic = makeBasic();
        if (z) {
            makeBasic.setDeferralPolicy(2);
        }
        return makeBasic;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setTemporaryAppWhitelistDuration(long j) {
        setTemporaryAppAllowlist(j, 0, 0, null);
    }

    @android.annotation.SystemApi
    public void setTemporaryAppAllowlist(long j, int i, int i2, java.lang.String str) {
        this.mTemporaryAppAllowlistDuration = j;
        this.mTemporaryAppAllowlistType = i;
        this.mTemporaryAppAllowlistReasonCode = i2;
        this.mTemporaryAppAllowlistReason = str;
        if (!isTemporaryAppAllowlistSet()) {
            resetTemporaryAppAllowlist();
        }
    }

    private boolean isTemporaryAppAllowlistSet() {
        return this.mTemporaryAppAllowlistDuration > 0 && this.mTemporaryAppAllowlistType != -1;
    }

    private void resetTemporaryAppAllowlist() {
        this.mTemporaryAppAllowlistDuration = 0L;
        this.mTemporaryAppAllowlistType = -1;
        this.mTemporaryAppAllowlistReasonCode = 0;
        this.mTemporaryAppAllowlistReason = null;
    }

    public long getTemporaryAppAllowlistDuration() {
        return this.mTemporaryAppAllowlistDuration;
    }

    public int getTemporaryAppAllowlistType() {
        return this.mTemporaryAppAllowlistType;
    }

    public int getTemporaryAppAllowlistReasonCode() {
        return this.mTemporaryAppAllowlistReasonCode;
    }

    public java.lang.String getTemporaryAppAllowlistReason() {
        return this.mTemporaryAppAllowlistReason;
    }

    @java.lang.Deprecated
    public void setMinManifestReceiverApiLevel(int i) {
        this.mMinManifestReceiverApiLevel = i;
    }

    @java.lang.Deprecated
    public int getMinManifestReceiverApiLevel() {
        return this.mMinManifestReceiverApiLevel;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    @java.lang.Deprecated
    public void setMaxManifestReceiverApiLevel(int i) {
        this.mMaxManifestReceiverApiLevel = i;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    @java.lang.Deprecated
    public int getMaxManifestReceiverApiLevel() {
        return this.mMaxManifestReceiverApiLevel;
    }

    @android.annotation.SystemApi
    public void setDontSendToRestrictedApps(boolean z) {
        if (z) {
            this.mFlags |= 1;
        } else {
            this.mFlags &= -2;
        }
    }

    public boolean isDontSendToRestrictedApps() {
        return (this.mFlags & 1) != 0;
    }

    @android.annotation.SystemApi
    public void setBackgroundActivityStartsAllowed(boolean z) {
        if (z) {
            this.mFlags |= 2;
        } else {
            this.mFlags &= -3;
        }
    }

    @java.lang.Deprecated
    public boolean allowsBackgroundActivityStarts() {
        return (this.mFlags & 2) != 0;
    }

    @android.annotation.SystemApi
    public void setRequireAllOfPermissions(java.lang.String[] strArr) {
        this.mRequireAllOfPermissions = strArr;
    }

    @android.annotation.SystemApi
    public void setRequireNoneOfPermissions(java.lang.String[] strArr) {
        this.mRequireNoneOfPermissions = strArr;
    }

    @android.annotation.SystemApi
    public void setRequireCompatChange(long j, boolean z) {
        this.mRequireCompatChangeId = j;
        if (z) {
            this.mFlags |= 4;
        } else {
            this.mFlags &= -5;
        }
    }

    @android.annotation.SystemApi
    public void clearRequireCompatChange() {
        setRequireCompatChange(Long.MIN_VALUE, true);
    }

    public void setAlarmBroadcast(boolean z) {
        if (z) {
            this.mFlags |= 8;
        } else {
            this.mFlags &= -9;
        }
    }

    public boolean isAlarmBroadcast() {
        return (this.mFlags & 8) != 0;
    }

    public android.app.BroadcastOptions setShareIdentityEnabled(boolean z) {
        if (z) {
            this.mFlags |= 16;
        } else {
            this.mFlags &= -17;
        }
        return this;
    }

    public boolean isShareIdentityEnabled() {
        return (this.mFlags & 16) != 0;
    }

    public boolean isPushMessagingBroadcast() {
        return this.mTemporaryAppAllowlistReasonCode == 101;
    }

    public boolean isPushMessagingOverQuotaBroadcast() {
        return this.mTemporaryAppAllowlistReasonCode == 102;
    }

    public long getRequireCompatChangeId() {
        return this.mRequireCompatChangeId;
    }

    public boolean testRequireCompatChange(int i) {
        if (this.mRequireCompatChangeId != Long.MIN_VALUE) {
            return android.app.compat.CompatChanges.isChangeEnabled(this.mRequireCompatChangeId, i) == ((this.mFlags & 4) != 0);
        }
        return true;
    }

    @android.annotation.SystemApi
    public void recordResponseEventWhileInBackground(long j) {
        this.mIdForResponseEvent = j;
    }

    public long getIdForResponseEvent() {
        return this.mIdForResponseEvent;
    }

    public void setEventTriggerTimestampMillis(long j) {
        this.mEventTriggerTimestampMillis = j;
    }

    public long getEventTriggerTimestampMillis() {
        return this.mEventTriggerTimestampMillis;
    }

    public void setRemoteEventTriggerTimestampMillis(long j) {
        this.mRemoteEventTriggerTimestampMillis = j;
    }

    public long getRemoteEventTriggerTimestampMillis() {
        return this.mRemoteEventTriggerTimestampMillis;
    }

    public android.app.BroadcastOptions setDeferralPolicy(int i) {
        this.mDeferralPolicy = i;
        return this;
    }

    public int getDeferralPolicy() {
        return this.mDeferralPolicy;
    }

    public void clearDeferralPolicy() {
        this.mDeferralPolicy = 0;
    }

    public android.app.BroadcastOptions setDeliveryGroupPolicy(int i) {
        this.mDeliveryGroupPolicy = i;
        return this;
    }

    public int getDeliveryGroupPolicy() {
        return this.mDeliveryGroupPolicy;
    }

    public void clearDeliveryGroupPolicy() {
        this.mDeliveryGroupPolicy = 0;
    }

    public android.app.BroadcastOptions setDeliveryGroupMatchingKey(java.lang.String str, java.lang.String str2) {
        this.mDeliveryGroupMatchingNamespaceFragment = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mDeliveryGroupMatchingKeyFragment = (java.lang.String) java.util.Objects.requireNonNull(str2);
        return this;
    }

    public java.lang.String getDeliveryGroupMatchingKey() {
        if (this.mDeliveryGroupMatchingNamespaceFragment == null || this.mDeliveryGroupMatchingKeyFragment == null) {
            return null;
        }
        return java.lang.String.join(":", this.mDeliveryGroupMatchingNamespaceFragment, this.mDeliveryGroupMatchingKeyFragment);
    }

    public java.lang.String getDeliveryGroupMatchingNamespaceFragment() {
        return this.mDeliveryGroupMatchingNamespaceFragment;
    }

    public java.lang.String getDeliveryGroupMatchingKeyFragment() {
        return this.mDeliveryGroupMatchingKeyFragment;
    }

    public void clearDeliveryGroupMatchingKey() {
        this.mDeliveryGroupMatchingNamespaceFragment = null;
        this.mDeliveryGroupMatchingKeyFragment = null;
    }

    public android.app.BroadcastOptions setDeliveryGroupMatchingFilter(android.content.IntentFilter intentFilter) {
        this.mDeliveryGroupMatchingFilter = (android.content.IntentFilter) java.util.Objects.requireNonNull(intentFilter);
        return this;
    }

    public android.content.IntentFilter getDeliveryGroupMatchingFilter() {
        return this.mDeliveryGroupMatchingFilter;
    }

    public void clearDeliveryGroupMatchingFilter() {
        this.mDeliveryGroupMatchingFilter = null;
    }

    public android.app.BroadcastOptions setDeliveryGroupExtrasMerger(android.os.BundleMerger bundleMerger) {
        this.mDeliveryGroupExtrasMerger = (android.os.BundleMerger) java.util.Objects.requireNonNull(bundleMerger);
        return this;
    }

    public android.os.BundleMerger getDeliveryGroupExtrasMerger() {
        return this.mDeliveryGroupExtrasMerger;
    }

    public void clearDeliveryGroupExtrasMerger() {
        this.mDeliveryGroupExtrasMerger = null;
    }

    public android.app.BroadcastOptions setInteractive(boolean z) {
        if (z) {
            this.mFlags |= 32;
        } else {
            this.mFlags &= -33;
        }
        return this;
    }

    public boolean isInteractive() {
        return (this.mFlags & 32) != 0;
    }

    @Override // android.app.ComponentOptions
    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setPendingIntentBackgroundActivityLaunchAllowed(boolean z) {
        super.setPendingIntentBackgroundActivityLaunchAllowed(z);
    }

    @Override // android.app.ComponentOptions
    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isPendingIntentBackgroundActivityLaunchAllowed() {
        return super.isPendingIntentBackgroundActivityLaunchAllowed();
    }

    @Override // android.app.ComponentOptions
    @android.annotation.SystemApi
    public android.app.BroadcastOptions setPendingIntentBackgroundActivityStartMode(int i) {
        super.setPendingIntentBackgroundActivityStartMode(i);
        return this;
    }

    @Override // android.app.ComponentOptions
    @android.annotation.SystemApi
    public int getPendingIntentBackgroundActivityStartMode() {
        return super.getPendingIntentBackgroundActivityStartMode();
    }

    @Override // android.app.ComponentOptions
    public android.os.Bundle toBundle() {
        android.os.Bundle bundle = super.toBundle();
        if (this.mFlags != 0) {
            bundle.putInt(KEY_FLAGS, this.mFlags);
        }
        if (isTemporaryAppAllowlistSet()) {
            bundle.putLong(KEY_TEMPORARY_APP_ALLOWLIST_DURATION, this.mTemporaryAppAllowlistDuration);
            bundle.putInt(KEY_TEMPORARY_APP_ALLOWLIST_TYPE, this.mTemporaryAppAllowlistType);
            bundle.putInt(KEY_TEMPORARY_APP_ALLOWLIST_REASON_CODE, this.mTemporaryAppAllowlistReasonCode);
            bundle.putString(KEY_TEMPORARY_APP_ALLOWLIST_REASON, this.mTemporaryAppAllowlistReason);
        }
        if (this.mMinManifestReceiverApiLevel != 0) {
            bundle.putInt(KEY_MIN_MANIFEST_RECEIVER_API_LEVEL, this.mMinManifestReceiverApiLevel);
        }
        if (this.mMaxManifestReceiverApiLevel != 10000) {
            bundle.putInt(KEY_MAX_MANIFEST_RECEIVER_API_LEVEL, this.mMaxManifestReceiverApiLevel);
        }
        if (this.mRequireAllOfPermissions != null) {
            bundle.putStringArray(KEY_REQUIRE_ALL_OF_PERMISSIONS, this.mRequireAllOfPermissions);
        }
        if (this.mRequireNoneOfPermissions != null) {
            bundle.putStringArray(KEY_REQUIRE_NONE_OF_PERMISSIONS, this.mRequireNoneOfPermissions);
        }
        if (this.mRequireCompatChangeId != Long.MIN_VALUE) {
            bundle.putLong(KEY_REQUIRE_COMPAT_CHANGE_ID, this.mRequireCompatChangeId);
        }
        if (this.mIdForResponseEvent != 0) {
            bundle.putLong(KEY_ID_FOR_RESPONSE_EVENT, this.mIdForResponseEvent);
        }
        if (this.mEventTriggerTimestampMillis > 0) {
            bundle.putLong(KEY_EVENT_TRIGGER_TIMESTAMP, this.mEventTriggerTimestampMillis);
        }
        if (this.mRemoteEventTriggerTimestampMillis > 0) {
            bundle.putLong(KEY_REMOTE_EVENT_TRIGGER_TIMESTAMP, this.mRemoteEventTriggerTimestampMillis);
        }
        if (this.mDeliveryGroupPolicy != 0) {
            bundle.putInt(KEY_DELIVERY_GROUP_POLICY, this.mDeliveryGroupPolicy);
        }
        if (this.mDeliveryGroupMatchingNamespaceFragment != null) {
            bundle.putString(KEY_DELIVERY_GROUP_NAMESPACE, this.mDeliveryGroupMatchingNamespaceFragment);
        }
        if (this.mDeliveryGroupMatchingKeyFragment != null) {
            bundle.putString(KEY_DELIVERY_GROUP_KEY, this.mDeliveryGroupMatchingKeyFragment);
        }
        if (this.mDeliveryGroupPolicy == 2) {
            if (this.mDeliveryGroupExtrasMerger != null) {
                bundle.putParcelable(KEY_DELIVERY_GROUP_EXTRAS_MERGER, this.mDeliveryGroupExtrasMerger);
            } else {
                throw new java.lang.IllegalStateException("Extras merger cannot be empty when delivery group policy is 'MERGED'");
            }
        }
        if (this.mDeliveryGroupMatchingFilter != null) {
            bundle.putParcelable(KEY_DELIVERY_GROUP_MATCHING_FILTER, this.mDeliveryGroupMatchingFilter);
        }
        if (this.mDeferralPolicy != 0) {
            bundle.putInt(KEY_DEFERRAL_POLICY, this.mDeferralPolicy);
        }
        return bundle;
    }

    public static android.app.BroadcastOptions fromBundle(android.os.Bundle bundle) {
        return new android.app.BroadcastOptions(bundle);
    }

    public static android.app.BroadcastOptions fromBundleNullable(android.os.Bundle bundle) {
        if (bundle != null) {
            return new android.app.BroadcastOptions(bundle);
        }
        return null;
    }
}
