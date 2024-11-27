package com.android.server.security;

/* loaded from: classes2.dex */
public class AttestationVerificationManagerService extends com.android.server.SystemService {
    private static final java.lang.String TAG = "AVF";
    private final com.android.server.security.AttestationVerificationPeerDeviceVerifier mPeerDeviceVerifier;
    private final android.os.IBinder mService;

    public AttestationVerificationManagerService(android.content.Context context) throws java.lang.Exception {
        super(context);
        this.mService = new android.security.attestationverification.IAttestationVerificationManagerService.Stub() { // from class: com.android.server.security.AttestationVerificationManagerService.1
            public void verifyAttestation(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                enforceUsePermission();
                try {
                    android.util.Slog.d(com.android.server.security.AttestationVerificationManagerService.TAG, "verifyAttestation");
                    com.android.server.security.AttestationVerificationManagerService.this.verifyAttestationForAllVerifiers(attestationProfile, i, bundle, bArr, androidFuture);
                } catch (java.lang.Throwable th) {
                    android.util.Slog.e(com.android.server.security.AttestationVerificationManagerService.TAG, "failed to verify attestation", th);
                    throw android.util.ExceptionUtils.propagate(th, android.os.RemoteException.class);
                }
            }

            public void verifyToken(android.security.attestationverification.VerificationToken verificationToken, android.os.ParcelDuration parcelDuration, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                enforceUsePermission();
                androidFuture.complete(0);
            }

            private void enforceUsePermission() {
                com.android.server.security.AttestationVerificationManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.USE_ATTESTATION_VERIFICATION_SERVICE", null);
            }
        };
        this.mPeerDeviceVerifier = new com.android.server.security.AttestationVerificationPeerDeviceVerifier(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyAttestationForAllVerifiers(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, com.android.internal.infra.AndroidFuture<android.security.attestationverification.IVerificationResult> androidFuture) {
        android.security.attestationverification.IVerificationResult iVerificationResult = new android.security.attestationverification.IVerificationResult();
        iVerificationResult.token = null;
        switch (attestationProfile.getAttestationProfileId()) {
            case 2:
                android.util.Slog.d(TAG, "Verifying Self Trusted profile.");
                try {
                    iVerificationResult.resultCode = com.android.server.security.AttestationVerificationSelfTrustedVerifierForTesting.getInstance().verifyAttestation(i, bundle, bArr);
                    break;
                } catch (java.lang.Throwable th) {
                    iVerificationResult.resultCode = 2;
                    break;
                }
            case 3:
                android.util.Slog.d(TAG, "Verifying Peer Device profile.");
                iVerificationResult.resultCode = this.mPeerDeviceVerifier.verifyAttestation(i, bundle, bArr);
                break;
            default:
                android.util.Slog.d(TAG, "No profile found, defaulting.");
                iVerificationResult.resultCode = 0;
                break;
        }
        androidFuture.complete(iVerificationResult);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.util.Slog.d(TAG, "Started");
        publishBinderService("attestation_verification", this.mService);
    }
}
