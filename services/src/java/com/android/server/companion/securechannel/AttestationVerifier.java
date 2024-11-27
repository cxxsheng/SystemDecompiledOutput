package com.android.server.companion.securechannel;

/* loaded from: classes.dex */
public class AttestationVerifier {
    private static final long ATTESTATION_VERIFICATION_TIMEOUT_SECONDS = 10;
    private static final java.lang.String PARAM_OWNED_BY_SYSTEM = "android.key_owned_by_system";
    private final android.content.Context mContext;

    AttestationVerifier(android.content.Context context) {
        this.mContext = context;
    }

    @android.annotation.NonNull
    public int verifyAttestation(@android.annotation.NonNull byte[] bArr, @android.annotation.NonNull byte[] bArr2) throws com.android.server.companion.securechannel.SecureChannelException {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putByteArray("localbinding.challenge", bArr2);
        bundle.putBoolean(PARAM_OWNED_BY_SYSTEM, true);
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger(0);
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        ((android.security.attestationverification.AttestationVerificationManager) this.mContext.getSystemService(android.security.attestationverification.AttestationVerificationManager.class)).verifyAttestation(new android.security.attestationverification.AttestationProfile(3), 3, bundle, bArr, new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new java.util.function.BiConsumer() { // from class: com.android.server.companion.securechannel.AttestationVerifier$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.companion.securechannel.AttestationVerifier.lambda$verifyAttestation$0(atomicInteger, countDownLatch, (java.lang.Integer) obj, (android.security.attestationverification.VerificationToken) obj2);
            }
        });
        try {
            if (!countDownLatch.await(ATTESTATION_VERIFICATION_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)) {
                throw new com.android.server.companion.securechannel.SecureChannelException("Attestation verification timed out.");
            }
            return atomicInteger.get();
        } catch (java.lang.InterruptedException e) {
            throw new com.android.server.companion.securechannel.SecureChannelException("Attestation verification was interrupted", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$verifyAttestation$0(java.util.concurrent.atomic.AtomicInteger atomicInteger, java.util.concurrent.CountDownLatch countDownLatch, java.lang.Integer num, android.security.attestationverification.VerificationToken verificationToken) {
        atomicInteger.set(num.intValue());
        countDownLatch.countDown();
    }
}
