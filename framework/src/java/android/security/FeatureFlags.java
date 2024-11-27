package android.security;

/* loaded from: classes3.dex */
public interface FeatureFlags {
    boolean asmRestrictionsEnabled();

    boolean asmToastsEnabled();

    boolean binaryTransparencySepolicyHash();

    boolean blockNullActionIntents();

    boolean certificateTransparencyConfiguration();

    boolean contentUriPermissionApis();

    boolean deprecateFsvSig();

    boolean enforceIntentFilterMatch();

    boolean extendEcmToAllSettings();

    boolean extendVbChainToUpdatedApk();

    boolean fixUnlockedDeviceRequiredKeysV2();

    boolean frpEnforcement();

    boolean fsverityApi();

    boolean keyinfoUnlockedDeviceRequired();

    boolean mgf1DigestSetterV2();

    boolean reportPrimaryAuthAttempts();
}
