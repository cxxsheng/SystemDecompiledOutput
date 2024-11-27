package com.android.server.audio;

/* loaded from: classes.dex */
public class FocusRequester {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "FocusRequester";

    @android.annotation.NonNull
    private final android.media.AudioAttributes mAttributes;
    private final int mCallingUid;

    @android.annotation.NonNull
    private final java.lang.String mClientId;
    private com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler mDeathHandler;
    private final com.android.server.audio.MediaFocusControl mFocusController;
    private android.media.IAudioFocusDispatcher mFocusDispatcher;
    private final int mFocusGainRequest;
    private final int mGrantFlags;

    @android.annotation.NonNull
    private final java.lang.String mPackageName;
    private final int mSdkTarget;
    private final android.os.IBinder mSourceRef;
    private int mFocusLossReceived = 0;
    private boolean mFocusLossWasNotified = true;
    boolean mFocusLossFadeLimbo = false;

    FocusRequester(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, int i, int i2, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler, @android.annotation.NonNull java.lang.String str2, int i3, @android.annotation.NonNull com.android.server.audio.MediaFocusControl mediaFocusControl, int i4) {
        this.mAttributes = audioAttributes;
        this.mFocusDispatcher = iAudioFocusDispatcher;
        this.mSourceRef = iBinder;
        this.mClientId = str;
        this.mDeathHandler = audioFocusDeathHandler;
        this.mPackageName = str2;
        this.mCallingUid = i3;
        this.mFocusGainRequest = i;
        this.mGrantFlags = i2;
        this.mFocusController = mediaFocusControl;
        this.mSdkTarget = i4;
    }

    FocusRequester(android.media.AudioFocusInfo audioFocusInfo, android.media.IAudioFocusDispatcher iAudioFocusDispatcher, android.os.IBinder iBinder, com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler, @android.annotation.NonNull com.android.server.audio.MediaFocusControl mediaFocusControl) {
        this.mAttributes = audioFocusInfo.getAttributes();
        this.mClientId = audioFocusInfo.getClientId();
        this.mPackageName = audioFocusInfo.getPackageName();
        this.mCallingUid = audioFocusInfo.getClientUid();
        this.mFocusGainRequest = audioFocusInfo.getGainRequest();
        this.mGrantFlags = audioFocusInfo.getFlags();
        this.mSdkTarget = audioFocusInfo.getSdkTarget();
        this.mFocusDispatcher = iAudioFocusDispatcher;
        this.mSourceRef = iBinder;
        this.mDeathHandler = audioFocusDeathHandler;
        this.mFocusController = mediaFocusControl;
    }

    boolean hasSameClient(java.lang.String str) {
        return this.mClientId.compareTo(str) == 0;
    }

    boolean isLockedFocusOwner() {
        return (this.mGrantFlags & 4) != 0;
    }

    boolean isInFocusLossLimbo() {
        return this.mFocusLossFadeLimbo;
    }

    boolean hasSameBinder(android.os.IBinder iBinder) {
        return this.mSourceRef != null && this.mSourceRef.equals(iBinder);
    }

    boolean hasSameDispatcher(android.media.IAudioFocusDispatcher iAudioFocusDispatcher) {
        return this.mFocusDispatcher != null && this.mFocusDispatcher.equals(iAudioFocusDispatcher);
    }

    @android.annotation.NonNull
    java.lang.String getPackageName() {
        return this.mPackageName;
    }

    boolean hasSamePackage(@android.annotation.NonNull java.lang.String str) {
        return this.mPackageName.compareTo(str) == 0;
    }

    boolean hasSameUid(int i) {
        return this.mCallingUid == i;
    }

    boolean isAlwaysVisibleUser() {
        android.content.pm.UserProperties userProperties = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserProperties(android.os.UserHandle.getUserId(this.mCallingUid));
        return userProperties != null && userProperties.getAlwaysVisible();
    }

    int getClientUid() {
        return this.mCallingUid;
    }

    java.lang.String getClientId() {
        return this.mClientId;
    }

    int getGainRequest() {
        return this.mFocusGainRequest;
    }

    int getGrantFlags() {
        return this.mGrantFlags;
    }

    @android.annotation.NonNull
    android.media.AudioAttributes getAudioAttributes() {
        return this.mAttributes;
    }

    int getSdkTarget() {
        return this.mSdkTarget;
    }

    private static java.lang.String focusChangeToString(int i) {
        switch (i) {
            case -3:
                return "LOSS_TRANSIENT_CAN_DUCK";
            case -2:
                return "LOSS_TRANSIENT";
            case -1:
                return "LOSS";
            case 0:
                return "none";
            case 1:
                return "GAIN";
            case 2:
                return "GAIN_TRANSIENT";
            case 3:
                return "GAIN_TRANSIENT_MAY_DUCK";
            case 4:
                return "GAIN_TRANSIENT_EXCLUSIVE";
            default:
                return "[invalid focus change" + i + "]";
        }
    }

    private java.lang.String focusGainToString() {
        return focusChangeToString(this.mFocusGainRequest);
    }

    private java.lang.String focusLossToString() {
        return focusChangeToString(this.mFocusLossReceived);
    }

    private static java.lang.String flagsToString(int i) {
        java.lang.String str = new java.lang.String();
        if ((i & 1) != 0) {
            str = str + "DELAY_OK";
        }
        if ((i & 4) != 0) {
            if (!str.isEmpty()) {
                str = str + "|";
            }
            str = str + "LOCK";
        }
        if ((i & 2) != 0) {
            if (!str.isEmpty()) {
                str = str + "|";
            }
            return str + "PAUSES_ON_DUCKABLE_LOSS";
        }
        return str;
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  source:" + this.mSourceRef + " -- pack: " + this.mPackageName + " -- client: " + this.mClientId + " -- gain: " + focusGainToString() + " -- flags: " + flagsToString(this.mGrantFlags) + " -- loss: " + focusLossToString() + " -- notified: " + this.mFocusLossWasNotified + " -- limbo" + this.mFocusLossFadeLimbo + " -- uid: " + this.mCallingUid + " -- attr: " + this.mAttributes + " -- sdk:" + this.mSdkTarget);
    }

    void maybeRelease() {
        if (!this.mFocusLossFadeLimbo) {
            release();
        }
    }

    void release() {
        android.os.IBinder iBinder = this.mSourceRef;
        com.android.server.audio.MediaFocusControl.AudioFocusDeathHandler audioFocusDeathHandler = this.mDeathHandler;
        if (iBinder != null && audioFocusDeathHandler != null) {
            try {
                iBinder.unlinkToDeath(audioFocusDeathHandler, 0);
            } catch (java.util.NoSuchElementException e) {
            }
        }
        this.mDeathHandler = null;
        this.mFocusDispatcher = null;
    }

    protected void finalize() throws java.lang.Throwable {
        release();
        super.finalize();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    private int focusLossForGainRequest(int r4) {
        /*
            r3 = this;
            r0 = -2
            r1 = -1
            switch(r4) {
                case 1: goto L6;
                case 2: goto Lb;
                case 3: goto L10;
                case 4: goto Lb;
                default: goto L5;
            }
        L5:
            goto L15
        L6:
            int r2 = r3.mFocusLossReceived
            switch(r2) {
                case -3: goto L33;
                case -2: goto L33;
                case -1: goto L33;
                case 0: goto L33;
                default: goto Lb;
            }
        Lb:
            int r2 = r3.mFocusLossReceived
            switch(r2) {
                case -3: goto L32;
                case -2: goto L32;
                case -1: goto L31;
                case 0: goto L32;
                default: goto L10;
            }
        L10:
            int r2 = r3.mFocusLossReceived
            switch(r2) {
                case -3: goto L2f;
                case -2: goto L2e;
                case -1: goto L2d;
                case 0: goto L2f;
                default: goto L15;
            }
        L15:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "focusLossForGainRequest() for invalid focus request "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            java.lang.String r0 = "FocusRequester"
            android.util.Log.e(r0, r4)
            r4 = 0
            return r4
        L2d:
            return r1
        L2e:
            return r0
        L2f:
            r4 = -3
            return r4
        L31:
            return r1
        L32:
            return r0
        L33:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.FocusRequester.focusLossForGainRequest(int):int");
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    boolean handleFocusLossFromGain(int i, com.android.server.audio.FocusRequester focusRequester, boolean z) {
        int focusLossForGainRequest = focusLossForGainRequest(i);
        handleFocusLoss(focusLossForGainRequest, focusRequester, z);
        return focusLossForGainRequest == -1;
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    void handleFocusGain(int i) {
        try {
            this.mFocusLossReceived = 0;
            this.mFocusLossFadeLimbo = false;
            this.mFocusController.notifyExtPolicyFocusGrant_syncAf(toAudioFocusInfo(), 1);
            android.media.IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
            if (iAudioFocusDispatcher != null && this.mFocusLossWasNotified) {
                iAudioFocusDispatcher.dispatchAudioFocusChange(i, this.mClientId);
            }
            this.mFocusController.restoreVShapedPlayers(this);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failure to signal gain of audio focus due to: ", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    void handleFocusGainFromRequest(int i) {
        if (i == 1) {
            this.mFocusController.restoreVShapedPlayers(this);
        }
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    void handleFocusLoss(int i, @android.annotation.Nullable com.android.server.audio.FocusRequester focusRequester, boolean z) {
        boolean z2;
        try {
            if (i != this.mFocusLossReceived) {
                this.mFocusLossReceived = i;
                this.mFocusLossWasNotified = false;
                if (!this.mFocusController.mustNotifyFocusOwnerOnDuck() && this.mFocusLossReceived == -3 && (this.mGrantFlags & 2) == 0) {
                    this.mFocusController.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), false);
                    return;
                }
                if (focusRequester == null) {
                    z2 = false;
                } else {
                    z2 = frameworkHandleFocusLoss(i, focusRequester, z);
                }
                if (z2) {
                    this.mFocusController.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), false);
                    return;
                }
                android.media.IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
                if (iAudioFocusDispatcher != null) {
                    this.mFocusController.notifyExtPolicyFocusLoss_syncAf(toAudioFocusInfo(), true);
                    this.mFocusLossWasNotified = true;
                    iAudioFocusDispatcher.dispatchAudioFocusChange(this.mFocusLossReceived, this.mClientId);
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failure to signal loss of audio focus due to:", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    private boolean frameworkHandleFocusLoss(int i, @android.annotation.NonNull com.android.server.audio.FocusRequester focusRequester, boolean z) {
        if (focusRequester.mCallingUid == this.mCallingUid) {
            return false;
        }
        if (i != -3) {
            if (i != -1 || !this.mFocusController.fadeOutPlayers(focusRequester, this)) {
                return false;
            }
            this.mFocusLossFadeLimbo = true;
            this.mFocusController.postDelayedLossAfterFade(this, this.mFocusController.getFadeOutDurationOnFocusLossMillis(getAudioAttributes()));
            return true;
        }
        if (!z && (this.mGrantFlags & 2) != 0) {
            android.util.Log.v(TAG, "not ducking uid " + this.mCallingUid + " - flags");
            return false;
        }
        if (!z && getSdkTarget() <= 25) {
            android.util.Log.v(TAG, "not ducking uid " + this.mCallingUid + " - old SDK");
            return false;
        }
        return this.mFocusController.duckPlayers(focusRequester, this, z);
    }

    int dispatchFocusChange(int i) {
        android.media.IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
        if (iAudioFocusDispatcher == null || i == 0) {
            return 0;
        }
        if ((i == 3 || i == 4 || i == 2 || i == 1) && this.mFocusGainRequest != i) {
            android.util.Log.w(TAG, "focus gain was requested with " + this.mFocusGainRequest + ", dispatching " + i);
        } else if (i == -3 || i == -2 || i == -1) {
            this.mFocusLossReceived = i;
        }
        try {
            iAudioFocusDispatcher.dispatchAudioFocusChange(i, this.mClientId);
            return 1;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "dispatchFocusChange: error talking to focus listener " + this.mClientId, e);
            return 0;
        }
    }

    @com.android.internal.annotations.GuardedBy({"MediaFocusControl.mAudioFocusLock"})
    int dispatchFocusChangeWithFadeLocked(int i, java.util.List<com.android.server.audio.FocusRequester> list) {
        if (i == 3 || i == 4 || i == 2 || i == 1) {
            this.mFocusLossFadeLimbo = false;
            this.mFocusController.restoreVShapedPlayers(this);
        } else if (i == -1 && this.mFocusController.shouldEnforceFade()) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (this.mFocusController.fadeOutPlayers(list.get(i2), this)) {
                    this.mFocusLossFadeLimbo = true;
                    this.mFocusController.postDelayedLossAfterFade(this, this.mFocusController.getFadeOutDurationOnFocusLossMillis(getAudioAttributes()));
                    return 2;
                }
            }
        }
        return dispatchFocusChange(i);
    }

    void dispatchFocusResultFromExtPolicy(int i) {
        android.media.IAudioFocusDispatcher iAudioFocusDispatcher = this.mFocusDispatcher;
        if (iAudioFocusDispatcher == null) {
            return;
        }
        try {
            iAudioFocusDispatcher.dispatchFocusResultFromExtPolicy(i, this.mClientId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "dispatchFocusResultFromExtPolicy: error talking to focus listener" + this.mClientId, e);
        }
    }

    android.media.AudioFocusInfo toAudioFocusInfo() {
        return new android.media.AudioFocusInfo(this.mAttributes, this.mCallingUid, this.mClientId, this.mPackageName, this.mFocusGainRequest, this.mFocusLossReceived, this.mGrantFlags, this.mSdkTarget);
    }
}
