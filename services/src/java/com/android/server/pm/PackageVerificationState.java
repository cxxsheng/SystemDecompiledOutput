package com.android.server.pm;

/* loaded from: classes2.dex */
class PackageVerificationState {
    private boolean mIntegrityVerificationComplete;
    private boolean mSufficientVerificationComplete;
    private boolean mSufficientVerificationPassed;
    private final com.android.server.pm.VerifyingSession mVerifyingSession;
    private final android.util.SparseBooleanArray mSufficientVerifierUids = new android.util.SparseBooleanArray();
    private final android.util.SparseBooleanArray mRequiredVerifierUids = new android.util.SparseBooleanArray();
    private final android.util.SparseBooleanArray mUnrespondedRequiredVerifierUids = new android.util.SparseBooleanArray();
    private final android.util.SparseBooleanArray mExtendedTimeoutUids = new android.util.SparseBooleanArray();
    private boolean mRequiredVerificationComplete = false;
    private boolean mRequiredVerificationPassed = true;

    PackageVerificationState(com.android.server.pm.VerifyingSession verifyingSession) {
        this.mVerifyingSession = verifyingSession;
    }

    com.android.server.pm.VerifyingSession getVerifyingSession() {
        return this.mVerifyingSession;
    }

    void addRequiredVerifierUid(int i) {
        this.mRequiredVerifierUids.put(i, true);
        this.mUnrespondedRequiredVerifierUids.put(i, true);
    }

    boolean checkRequiredVerifierUid(int i) {
        return this.mRequiredVerifierUids.get(i, false);
    }

    void addSufficientVerifier(int i) {
        this.mSufficientVerifierUids.put(i, true);
    }

    boolean checkSufficientVerifierUid(int i) {
        return this.mSufficientVerifierUids.get(i, false);
    }

    void setVerifierResponseOnTimeout(int i, int i2) {
        if (!checkRequiredVerifierUid(i)) {
            return;
        }
        this.mSufficientVerifierUids.clear();
        if (this.mUnrespondedRequiredVerifierUids.get(i, false)) {
            setVerifierResponse(i, i2);
        }
    }

    void setVerifierResponse(int i, int i2) {
        if (this.mRequiredVerifierUids.get(i)) {
            switch (i2) {
                case 1:
                    break;
                case 2:
                    this.mSufficientVerifierUids.clear();
                    break;
                default:
                    this.mRequiredVerificationPassed = false;
                    this.mUnrespondedRequiredVerifierUids.clear();
                    this.mSufficientVerifierUids.clear();
                    this.mExtendedTimeoutUids.clear();
                    break;
            }
            this.mExtendedTimeoutUids.delete(i);
            this.mUnrespondedRequiredVerifierUids.delete(i);
            if (this.mUnrespondedRequiredVerifierUids.size() == 0) {
                this.mRequiredVerificationComplete = true;
                return;
            }
            return;
        }
        if (this.mSufficientVerifierUids.get(i)) {
            if (i2 == 1) {
                this.mSufficientVerificationPassed = true;
                this.mSufficientVerificationComplete = true;
            }
            this.mSufficientVerifierUids.delete(i);
            if (this.mSufficientVerifierUids.size() == 0) {
                this.mSufficientVerificationComplete = true;
            }
        }
    }

    void passRequiredVerification() {
        if (this.mUnrespondedRequiredVerifierUids.size() > 0) {
            throw new java.lang.RuntimeException("Required verifiers still present.");
        }
        this.mRequiredVerificationPassed = true;
        this.mRequiredVerificationComplete = true;
    }

    boolean isVerificationComplete() {
        if (!this.mRequiredVerificationComplete) {
            return false;
        }
        if (this.mSufficientVerifierUids.size() == 0) {
            return true;
        }
        return this.mSufficientVerificationComplete;
    }

    boolean isInstallAllowed() {
        if (!this.mRequiredVerificationComplete || !this.mRequiredVerificationPassed) {
            return false;
        }
        if (this.mSufficientVerificationComplete) {
            return this.mSufficientVerificationPassed;
        }
        return true;
    }

    boolean extendTimeout(int i) {
        if (!checkRequiredVerifierUid(i) || timeoutExtended(i)) {
            return false;
        }
        this.mExtendedTimeoutUids.append(i, true);
        return true;
    }

    boolean timeoutExtended(int i) {
        return this.mExtendedTimeoutUids.get(i, false);
    }

    void setIntegrityVerificationResult(int i) {
        this.mIntegrityVerificationComplete = true;
    }

    boolean isIntegrityVerificationComplete() {
        return this.mIntegrityVerificationComplete;
    }

    boolean areAllVerificationsComplete() {
        return this.mIntegrityVerificationComplete && isVerificationComplete();
    }
}
