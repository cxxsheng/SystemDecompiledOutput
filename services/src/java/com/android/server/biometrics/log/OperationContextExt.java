package com.android.server.biometrics.log;

/* loaded from: classes.dex */
public class OperationContextExt {

    @android.annotation.NonNull
    private final android.hardware.biometrics.common.OperationContext mAidlContext;
    private int mDockState;
    private int mFoldState;
    private final boolean mIsBP;
    private boolean mIsDisplayOn;
    private int mOrientation;

    @android.annotation.Nullable
    private com.android.server.biometrics.log.BiometricContextSessionInfo mSessionInfo;

    public OperationContextExt(boolean z) {
        this(new android.hardware.biometrics.common.OperationContext(), z, 0);
    }

    public OperationContextExt(boolean z, int i) {
        this(new android.hardware.biometrics.common.OperationContext(), z, i);
    }

    public OperationContextExt(@android.annotation.NonNull android.hardware.biometrics.common.OperationContext operationContext, boolean z, int i) {
        this.mIsDisplayOn = false;
        this.mDockState = 0;
        this.mOrientation = 0;
        this.mFoldState = 0;
        this.mAidlContext = operationContext;
        this.mIsBP = z;
        if (i == 2) {
            this.mAidlContext.operationState = android.hardware.biometrics.common.OperationState.fingerprintOperationState(new android.hardware.biometrics.common.OperationState.FingerprintOperationState());
        } else if (i == 8) {
            this.mAidlContext.operationState = android.hardware.biometrics.common.OperationState.faceOperationState(new android.hardware.biometrics.common.OperationState.FaceOperationState());
        }
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.common.OperationContext toAidlContext() {
        return this.mAidlContext;
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.common.OperationContext toAidlContext(@android.annotation.NonNull android.hardware.biometrics.AuthenticateOptions authenticateOptions) {
        if (authenticateOptions instanceof android.hardware.face.FaceAuthenticateOptions) {
            return toAidlContext((android.hardware.face.FaceAuthenticateOptions) authenticateOptions);
        }
        if (authenticateOptions instanceof android.hardware.fingerprint.FingerprintAuthenticateOptions) {
            return toAidlContext((android.hardware.fingerprint.FingerprintAuthenticateOptions) authenticateOptions);
        }
        throw new java.lang.IllegalStateException("Authenticate options are invalid.");
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.common.OperationContext toAidlContext(@android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
        this.mAidlContext.authenticateReason = android.hardware.biometrics.common.AuthenticateReason.faceAuthenticateReason(getAuthReason(faceAuthenticateOptions));
        this.mAidlContext.wakeReason = getWakeReason(faceAuthenticateOptions);
        return this.mAidlContext;
    }

    @android.annotation.NonNull
    public android.hardware.biometrics.common.OperationContext toAidlContext(@android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        if (fingerprintAuthenticateOptions.getVendorReason() != null) {
            this.mAidlContext.authenticateReason = android.hardware.biometrics.common.AuthenticateReason.vendorAuthenticateReason(fingerprintAuthenticateOptions.getVendorReason());
        } else {
            this.mAidlContext.authenticateReason = android.hardware.biometrics.common.AuthenticateReason.fingerprintAuthenticateReason(getAuthReason(fingerprintAuthenticateOptions));
        }
        this.mAidlContext.wakeReason = getWakeReason(fingerprintAuthenticateOptions);
        return this.mAidlContext;
    }

    @android.hardware.biometrics.common.AuthenticateReason.Face
    private int getAuthReason(@android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
        switch (faceAuthenticateOptions.getAuthenticateReason()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
            case 9:
                return 9;
            case 10:
                return 10;
            default:
                return 0;
        }
    }

    @android.hardware.biometrics.common.WakeReason
    private int getWakeReason(@android.annotation.NonNull android.hardware.face.FaceAuthenticateOptions faceAuthenticateOptions) {
        switch (faceAuthenticateOptions.getWakeReason()) {
            case 1:
                return 1;
            case 4:
                return 2;
            case 6:
                return 3;
            case 7:
                return 4;
            case 10:
                return 6;
            case 15:
                return 7;
            case 16:
                return 8;
            case 17:
                return 9;
            default:
                return 0;
        }
    }

    @android.hardware.biometrics.common.AuthenticateReason.Fingerprint
    private int getAuthReason(@android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        return 0;
    }

    @android.hardware.biometrics.common.WakeReason
    private int getWakeReason(@android.annotation.NonNull android.hardware.fingerprint.FingerprintAuthenticateOptions fingerprintAuthenticateOptions) {
        return 0;
    }

    public int getId() {
        return this.mAidlContext.id;
    }

    public int getOrderAndIncrement() {
        com.android.server.biometrics.log.BiometricContextSessionInfo biometricContextSessionInfo = this.mSessionInfo;
        if (biometricContextSessionInfo != null) {
            return biometricContextSessionInfo.getOrderAndIncrement();
        }
        return -1;
    }

    @android.hardware.biometrics.common.OperationReason
    public byte getReason() {
        return this.mAidlContext.reason;
    }

    @android.hardware.biometrics.common.WakeReason
    public int getWakeReason() {
        return this.mAidlContext.wakeReason;
    }

    public boolean isDisplayOn() {
        return this.mIsDisplayOn;
    }

    public boolean isAod() {
        return this.mAidlContext.isAod;
    }

    @android.hardware.biometrics.common.DisplayState
    public int getDisplayState() {
        return this.mAidlContext.displayState;
    }

    public boolean isCrypto() {
        return this.mAidlContext.isCrypto;
    }

    public int getDockState() {
        return this.mDockState;
    }

    public int getFoldState() {
        return this.mFoldState;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public android.hardware.biometrics.common.OperationState getOperationState() {
        return this.mAidlContext.operationState;
    }

    com.android.server.biometrics.log.OperationContextExt update(@android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext, boolean z) {
        this.mAidlContext.isAod = biometricContext.isAod();
        this.mAidlContext.displayState = toAidlDisplayState(biometricContext.getDisplayState());
        this.mAidlContext.foldState = toAidlFoldState(biometricContext.getFoldState());
        this.mAidlContext.isCrypto = z;
        if (this.mAidlContext.operationState != null && this.mAidlContext.operationState.getTag() == 0) {
            this.mAidlContext.operationState.getFingerprintOperationState().isHardwareIgnoringTouches = biometricContext.isHardwareIgnoringTouches();
        }
        setFirstSessionId(biometricContext);
        this.mIsDisplayOn = biometricContext.isDisplayOn();
        this.mDockState = biometricContext.getDockedState();
        this.mFoldState = biometricContext.getFoldState();
        this.mOrientation = biometricContext.getCurrentRotation();
        return this;
    }

    @android.hardware.biometrics.common.DisplayState
    private static int toAidlDisplayState(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return 0;
        }
    }

    @android.hardware.biometrics.common.FoldState
    private static int toAidlFoldState(@android.hardware.biometrics.IBiometricContextListener.FoldState int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    private void setFirstSessionId(@android.annotation.NonNull com.android.server.biometrics.log.BiometricContext biometricContext) {
        if (this.mIsBP) {
            this.mSessionInfo = biometricContext.getBiometricPromptSessionInfo();
            if (this.mSessionInfo != null) {
                this.mAidlContext.id = this.mSessionInfo.getId();
                this.mAidlContext.reason = (byte) 1;
                return;
            }
        } else {
            this.mSessionInfo = biometricContext.getKeyguardEntrySessionInfo();
            if (this.mSessionInfo != null) {
                this.mAidlContext.id = this.mSessionInfo.getId();
                this.mAidlContext.reason = (byte) 2;
                return;
            }
        }
        this.mAidlContext.id = 0;
        this.mAidlContext.reason = (byte) 0;
    }
}
