package android.security.attestationverification;

/* loaded from: classes3.dex */
public class AttestationVerificationManager {
    private static final java.time.Duration MAX_TOKEN_AGE = java.time.Duration.ofHours(1);
    public static final java.lang.String PARAM_CHALLENGE = "localbinding.challenge";
    public static final java.lang.String PARAM_ID = "localbinding.id";
    public static final java.lang.String PARAM_PUBLIC_KEY = "localbinding.public_key";
    public static final int PROFILE_APP_DEFINED = 1;
    public static final int PROFILE_PEER_DEVICE = 3;
    public static final int PROFILE_SELF_TRUSTED = 2;
    public static final int PROFILE_UNKNOWN = 0;
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_UNKNOWN = 0;
    private static final java.lang.String TAG = "AVF";
    public static final int TYPE_APP_DEFINED = 1;
    public static final int TYPE_CHALLENGE = 3;
    public static final int TYPE_PUBLIC_KEY = 2;
    public static final int TYPE_UNKNOWN = 0;
    private final android.content.Context mContext;
    private final android.security.attestationverification.IAttestationVerificationManagerService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttestationProfileId {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LocalBindingType {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerificationResult {
    }

    public void verifyAttestation(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, byte[] bArr, final java.util.concurrent.Executor executor, final java.util.function.BiConsumer<java.lang.Integer, android.security.attestationverification.VerificationToken> biConsumer) {
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            androidFuture.thenAccept(new java.util.function.Consumer() { // from class: android.security.attestationverification.AttestationVerificationManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.security.attestationverification.AttestationVerificationManager.lambda$verifyAttestation$1(executor, biConsumer, (android.security.attestationverification.IVerificationResult) obj);
                }
            });
            this.mService.verifyAttestation(attestationProfile, i, bundle, bArr, androidFuture);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$verifyAttestation$1(java.util.concurrent.Executor executor, final java.util.function.BiConsumer biConsumer, final android.security.attestationverification.IVerificationResult iVerificationResult) {
        android.util.Log.d(TAG, "verifyAttestation result: " + iVerificationResult.resultCode + " / " + iVerificationResult.token);
        executor.execute(new java.lang.Runnable() { // from class: android.security.attestationverification.AttestationVerificationManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                biConsumer.accept(java.lang.Integer.valueOf(r1.resultCode), iVerificationResult.token);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int verifyToken(android.security.attestationverification.AttestationProfile attestationProfile, int i, android.os.Bundle bundle, android.security.attestationverification.VerificationToken verificationToken, java.time.Duration duration) {
        if (duration == null) {
            duration = MAX_TOKEN_AGE;
        } else if (duration.compareTo(MAX_TOKEN_AGE) > 0) {
            throw new java.lang.IllegalArgumentException("maximumAge cannot be greater than " + MAX_TOKEN_AGE + "; was " + duration);
        }
        try {
            com.android.internal.infra.AndroidFuture androidFuture = new com.android.internal.infra.AndroidFuture();
            androidFuture.orTimeout(5L, java.util.concurrent.TimeUnit.SECONDS);
            this.mService.verifyToken(verificationToken, new android.os.ParcelDuration(duration), androidFuture);
            return ((java.lang.Integer) androidFuture.get()).intValue();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (java.lang.Throwable th) {
            throw new java.lang.RuntimeException("Error verifying token.", th);
        }
    }

    public AttestationVerificationManager(android.content.Context context, android.security.attestationverification.IAttestationVerificationManagerService iAttestationVerificationManagerService) {
        this.mContext = context;
        this.mService = iAttestationVerificationManagerService;
    }

    public static java.lang.String localBindingTypeToString(int i) {
        java.lang.String str;
        switch (i) {
            case 0:
                str = "UNKNOWN";
                break;
            case 1:
                str = "APP_DEFINED";
                break;
            case 2:
                str = "PUBLIC_KEY";
                break;
            case 3:
                str = "CHALLENGE";
                break;
            default:
                return java.lang.Integer.toString(i);
        }
        return str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static java.lang.String verificationResultCodeToString(int i) {
        java.lang.String str;
        switch (i) {
            case 0:
                str = "UNKNOWN";
                break;
            case 1:
                str = android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
                break;
            case 2:
                str = "FAILURE";
                break;
            default:
                return java.lang.Integer.toString(i);
        }
        return str + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }
}
