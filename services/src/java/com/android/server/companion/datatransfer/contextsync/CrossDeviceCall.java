package com.android.server.companion.datatransfer.contextsync;

/* loaded from: classes.dex */
public class CrossDeviceCall {
    private static final java.lang.String SEPARATOR = "::";
    private static final java.lang.String TAG = "CrossDeviceCall";
    private final android.telecom.Call mCall;
    private java.lang.String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private byte[] mCallingAppIcon;
    private java.lang.String mCallingAppName;
    private final java.lang.String mCallingAppPackageName;
    private java.lang.String mContactDisplayName;
    private final java.util.Set<java.lang.Integer> mControls;
    private int mDirection;
    private android.net.Uri mHandle;
    private int mHandlePresentation;
    private final java.lang.String mId;
    private final boolean mIsCallPlacedByContextSync;

    @com.android.internal.annotations.VisibleForTesting
    boolean mIsEnterprise;
    private boolean mIsMuted;
    private final java.lang.String mSerializedPhoneAccountHandle;
    private int mStatus;
    private final int mUserId;

    public CrossDeviceCall(android.content.Context context, @android.annotation.NonNull android.telecom.Call call, android.telecom.CallAudioState callAudioState) {
        this(context, call, call.getDetails(), callAudioState);
    }

    CrossDeviceCall(android.content.Context context, android.telecom.Call.Details details, android.telecom.CallAudioState callAudioState) {
        this(context, null, details, callAudioState);
    }

    private CrossDeviceCall(android.content.Context context, @android.annotation.Nullable android.telecom.Call call, android.telecom.Call.Details details, android.telecom.CallAudioState callAudioState) {
        java.lang.String str;
        boolean z;
        java.lang.String str2;
        boolean z2;
        boolean z3 = false;
        this.mStatus = 0;
        this.mControls = new java.util.HashSet();
        this.mCall = call;
        if (details.getIntentExtras() != null) {
            str = details.getIntentExtras().getString(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID);
        } else {
            str = null;
        }
        java.lang.String obj = java.util.UUID.randomUUID().toString();
        if (str != null) {
            obj = obj + SEPARATOR + str;
        }
        this.mId = obj;
        if (call != null) {
            call.putExtra(com.android.server.companion.datatransfer.contextsync.CrossDeviceSyncController.EXTRA_CALL_ID, this.mId);
        }
        android.telecom.PhoneAccountHandle accountHandle = details.getAccountHandle();
        this.mUserId = accountHandle != null ? accountHandle.getUserHandle().getIdentifier() : -1;
        if (accountHandle == null || !new android.content.ComponentName(context, (java.lang.Class<?>) com.android.server.companion.datatransfer.contextsync.CallMetadataSyncConnectionService.class).equals(accountHandle.getComponentName())) {
            z = false;
        } else {
            z = true;
        }
        this.mIsCallPlacedByContextSync = z;
        java.lang.String str3 = "";
        if (accountHandle == null) {
            str2 = "";
        } else {
            str2 = details.getAccountHandle().getComponentName().getPackageName();
        }
        this.mCallingAppPackageName = str2;
        if (accountHandle != null) {
            str3 = accountHandle.getId() + SEPARATOR + accountHandle.getComponentName().flattenToString();
        }
        this.mSerializedPhoneAccountHandle = str3;
        if ((details.getCallProperties() & 32) != 32) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mIsEnterprise = z2;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(this.mCallingAppPackageName, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L), this.mUserId);
            this.mCallingAppName = packageManager.getApplicationLabel(applicationInfoAsUser).toString();
            this.mCallingAppIcon = com.android.server.companion.datatransfer.contextsync.BitmapUtils.renderDrawableToByteArray(packageManager.getApplicationIcon(applicationInfoAsUser));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, "Could not get application info for package " + this.mCallingAppPackageName, e);
        }
        if (callAudioState != null && callAudioState.isMuted()) {
            z3 = true;
        }
        this.mIsMuted = z3;
        updateCallDetails(details);
    }

    public void updateMuted(boolean z) {
        this.mIsMuted = z;
        updateCallDetails(this.mCall.getDetails());
    }

    public void updateSilencedIfRinging() {
        if (this.mStatus == 1) {
            this.mStatus = 4;
        }
        this.mControls.remove(3);
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateCallDetails(android.telecom.Call.Details details) {
        this.mCallerDisplayName = details.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = details.getCallerDisplayNamePresentation();
        this.mContactDisplayName = details.getContactDisplayName();
        this.mHandle = details.getHandle();
        this.mHandlePresentation = details.getHandlePresentation();
        int callDirection = details.getCallDirection();
        if (callDirection == 0) {
            this.mDirection = 1;
        } else if (callDirection == 1) {
            this.mDirection = 2;
        } else {
            this.mDirection = 0;
        }
        this.mStatus = convertStateToStatus(details.getState());
        this.mControls.clear();
        if (this.mStatus == 8) {
            this.mControls.add(6);
        }
        if (this.mStatus == 1 || this.mStatus == 4) {
            this.mControls.add(1);
            this.mControls.add(2);
            if (this.mStatus == 1) {
                this.mControls.add(3);
            }
        }
        if (this.mStatus == 2 || this.mStatus == 3) {
            this.mControls.add(6);
            if (details.can(1)) {
                this.mControls.add(java.lang.Integer.valueOf(this.mStatus != 3 ? 7 : 8));
            }
        }
        if (this.mStatus == 2 && details.can(64)) {
            this.mControls.add(java.lang.Integer.valueOf(this.mIsMuted ? 5 : 4));
        }
    }

    public static int convertStateToStatus(int i) {
        switch (i) {
            case 1:
                return 8;
            case 2:
                return 1;
            case 3:
                return 3;
            case 4:
                return 2;
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            default:
                android.util.Slog.e(TAG, "Couldn't resolve state to status: " + i);
                return 0;
            case 7:
                return 7;
            case 12:
                return 5;
            case 13:
                return 6;
        }
    }

    public static int convertStatusToState(int i) {
        switch (i) {
            case 1:
            case 4:
                return 2;
            case 2:
                return 4;
            case 3:
                return 3;
            case 5:
                return 12;
            case 6:
                return 13;
            case 7:
                return 7;
            case 8:
                return 1;
            default:
                return 0;
        }
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.telecom.Call getCall() {
        return this.mCall;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public java.lang.String getCallingAppName() {
        return this.mCallingAppName;
    }

    public byte[] getCallingAppIcon() {
        return this.mCallingAppIcon;
    }

    public java.lang.String getCallingAppPackageName() {
        return this.mCallingAppPackageName;
    }

    public java.lang.String getSerializedPhoneAccountHandle() {
        return this.mSerializedPhoneAccountHandle;
    }

    public java.lang.String getReadableCallerId(boolean z) {
        if (this.mIsEnterprise && z) {
            return getNonContactString();
        }
        return android.text.TextUtils.isEmpty(this.mContactDisplayName) ? getNonContactString() : this.mContactDisplayName;
    }

    private java.lang.String getNonContactString() {
        if (!android.text.TextUtils.isEmpty(this.mCallerDisplayName) && this.mCallerDisplayNamePresentation == 1) {
            return this.mCallerDisplayName;
        }
        if (this.mHandle != null && this.mHandle.getSchemeSpecificPart() != null && this.mHandlePresentation == 1) {
            return this.mHandle.getSchemeSpecificPart();
        }
        return null;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getDirection() {
        return this.mDirection;
    }

    public java.util.Set<java.lang.Integer> getControls() {
        return this.mControls;
    }

    public boolean isCallPlacedByContextSync() {
        return this.mIsCallPlacedByContextSync;
    }

    void doAccept() {
        this.mCall.answer(0);
    }

    void doReject() {
        if (this.mStatus == 1) {
            this.mCall.reject(1);
        }
    }

    void doEnd() {
        this.mCall.disconnect();
    }

    void doPutOnHold() {
        this.mCall.hold();
    }

    void doTakeOffHold() {
        this.mCall.unhold();
    }
}
