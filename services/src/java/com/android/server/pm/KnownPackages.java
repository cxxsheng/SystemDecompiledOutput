package com.android.server.pm;

/* loaded from: classes2.dex */
public final class KnownPackages {
    public static final int LAST_KNOWN_PACKAGE = 19;
    public static final int PACKAGE_AMBIENT_CONTEXT_DETECTION = 18;
    public static final int PACKAGE_APP_PREDICTOR = 12;
    public static final int PACKAGE_BROWSER = 5;
    public static final int PACKAGE_COMPANION = 15;
    public static final int PACKAGE_CONFIGURATOR = 10;
    public static final int PACKAGE_DOCUMENTER = 9;
    public static final int PACKAGE_INCIDENT_REPORT_APPROVER = 11;
    public static final int PACKAGE_INSTALLER = 2;
    public static final int PACKAGE_OVERLAY_CONFIG_SIGNATURE = 13;
    public static final int PACKAGE_PERMISSION_CONTROLLER = 7;
    public static final int PACKAGE_RECENTS = 17;
    public static final int PACKAGE_RETAIL_DEMO = 16;
    public static final int PACKAGE_SETUP_WIZARD = 1;
    public static final int PACKAGE_SYSTEM = 0;
    public static final int PACKAGE_SYSTEM_TEXT_CLASSIFIER = 6;
    public static final int PACKAGE_UNINSTALLER = 3;
    public static final int PACKAGE_VERIFIER = 4;
    public static final int PACKAGE_WEARABLE_SENSING = 19;
    public static final int PACKAGE_WELLBEING = 8;
    public static final int PACKAGE_WIFI = 14;
    static final java.lang.String SYSTEM_PACKAGE_NAME = "android";
    private final java.lang.String mAmbientContextDetectionPackage;
    private final java.lang.String mAppPredictionServicePackage;
    private final java.lang.String mCompanionPackage;
    private final java.lang.String mConfiguratorPackage;
    private final com.android.server.pm.DefaultAppProvider mDefaultAppProvider;
    private final java.lang.String mDefaultTextClassifierPackage;
    private final java.lang.String mIncidentReportApproverPackage;
    private final java.lang.String mOverlayConfigSignaturePackage;
    private final java.lang.String mRecentsPackage;
    private final java.lang.String mRequiredInstallerPackage;
    private final java.lang.String mRequiredPermissionControllerPackage;
    private final java.lang.String mRequiredUninstallerPackage;
    private final java.lang.String[] mRequiredVerifierPackages;
    private final java.lang.String mRetailDemoPackage;
    private final java.lang.String mSetupWizardPackage;
    private final java.lang.String mSystemTextClassifierPackageName;
    private final java.lang.String mWearableSensingPackage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KnownPackage {
    }

    KnownPackages(com.android.server.pm.DefaultAppProvider defaultAppProvider, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String[] strArr, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.lang.String str9, java.lang.String str10, java.lang.String str11, java.lang.String str12, java.lang.String str13, java.lang.String str14, java.lang.String str15) {
        this.mDefaultAppProvider = defaultAppProvider;
        this.mRequiredInstallerPackage = str;
        this.mRequiredUninstallerPackage = str2;
        this.mSetupWizardPackage = str3;
        this.mRequiredVerifierPackages = strArr;
        this.mDefaultTextClassifierPackage = str4;
        this.mSystemTextClassifierPackageName = str5;
        this.mRequiredPermissionControllerPackage = str6;
        this.mConfiguratorPackage = str7;
        this.mIncidentReportApproverPackage = str8;
        this.mAmbientContextDetectionPackage = str9;
        this.mWearableSensingPackage = str10;
        this.mAppPredictionServicePackage = str11;
        this.mCompanionPackage = str12;
        this.mRetailDemoPackage = str13;
        this.mOverlayConfigSignaturePackage = str14;
        this.mRecentsPackage = str15;
    }

    @android.annotation.NonNull
    static java.lang.String knownPackageToString(int i) {
        switch (i) {
            case 0:
                return "System";
            case 1:
                return "Setup Wizard";
            case 2:
                return "Installer";
            case 3:
                return "Uninstaller";
            case 4:
                return "Verifier";
            case 5:
                return "Browser";
            case 6:
                return "System Text Classifier";
            case 7:
                return "Permission Controller";
            case 8:
                return "Wellbeing";
            case 9:
                return "Documenter";
            case 10:
                return "Configurator";
            case 11:
                return "Incident Report Approver";
            case 12:
                return "App Predictor";
            case 13:
                return "Overlay Config Signature";
            case 14:
                return "Wi-Fi";
            case 15:
                return "Companion";
            case 16:
                return "Retail Demo";
            case 17:
                return "Recents";
            case 18:
                return "Ambient Context Detection";
            case 19:
                return "Wearable sensing";
            default:
                return "Unknown";
        }
    }

    java.lang.String[] getKnownPackageNames(@android.annotation.NonNull com.android.server.pm.Computer computer, int i, int i2) {
        switch (i) {
            case 0:
                return new java.lang.String[]{"android"};
            case 1:
                return computer.filterOnlySystemPackages(this.mSetupWizardPackage);
            case 2:
                return computer.filterOnlySystemPackages(this.mRequiredInstallerPackage);
            case 3:
                return computer.filterOnlySystemPackages(this.mRequiredUninstallerPackage);
            case 4:
                return computer.filterOnlySystemPackages(this.mRequiredVerifierPackages);
            case 5:
                return new java.lang.String[]{this.mDefaultAppProvider.getDefaultBrowser(i2)};
            case 6:
                return computer.filterOnlySystemPackages(this.mDefaultTextClassifierPackage, this.mSystemTextClassifierPackageName);
            case 7:
                return computer.filterOnlySystemPackages(this.mRequiredPermissionControllerPackage);
            case 8:
            case 9:
            case 14:
            default:
                return (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
            case 10:
                return computer.filterOnlySystemPackages(this.mConfiguratorPackage);
            case 11:
                return computer.filterOnlySystemPackages(this.mIncidentReportApproverPackage);
            case 12:
                return computer.filterOnlySystemPackages(this.mAppPredictionServicePackage);
            case 13:
                return computer.filterOnlySystemPackages(this.mOverlayConfigSignaturePackage);
            case 15:
                return computer.filterOnlySystemPackages(this.mCompanionPackage);
            case 16:
                if (android.text.TextUtils.isEmpty(this.mRetailDemoPackage)) {
                    return (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
                }
                return new java.lang.String[]{this.mRetailDemoPackage};
            case 17:
                return computer.filterOnlySystemPackages(this.mRecentsPackage);
            case 18:
                return computer.filterOnlySystemPackages(this.mAmbientContextDetectionPackage);
            case 19:
                return computer.filterOnlySystemPackages(this.mWearableSensingPackage);
        }
    }
}
