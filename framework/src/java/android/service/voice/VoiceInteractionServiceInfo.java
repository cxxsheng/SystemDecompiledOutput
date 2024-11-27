package android.service.voice;

/* loaded from: classes3.dex */
public class VoiceInteractionServiceInfo {
    static final java.lang.String TAG = "VoiceInteractionServiceInfo";
    private java.lang.String mHotwordDetectionService;
    private java.lang.String mParseError;
    private java.lang.String mRecognitionService;
    private android.content.pm.ServiceInfo mServiceInfo;
    private java.lang.String mSessionService;
    private java.lang.String mSettingsActivity;
    private boolean mSupportsAssist;
    private boolean mSupportsLaunchFromKeyguard;
    private boolean mSupportsLocalInteraction;
    private java.lang.String mVisualQueryDetectionService;

    public VoiceInteractionServiceInfo(android.content.pm.PackageManager packageManager, android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        this(packageManager, getServiceInfoOrThrow(componentName, i));
    }

    private static android.content.pm.ServiceInfo getServiceInfoOrThrow(android.content.ComponentName componentName, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        try {
            android.content.pm.ServiceInfo serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, 786560L, i);
            if (serviceInfo != null) {
                return serviceInfo;
            }
        } catch (android.os.RemoteException e) {
        }
        throw new android.content.pm.PackageManager.NameNotFoundException(componentName.toString());
    }

    public VoiceInteractionServiceInfo(android.content.pm.PackageManager packageManager, android.content.pm.ServiceInfo serviceInfo) {
        int next;
        if (!android.Manifest.permission.BIND_VOICE_INTERACTION.equals(serviceInfo.permission)) {
            this.mParseError = "Service does not require permission android.permission.BIND_VOICE_INTERACTION";
            return;
        }
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.service.voice.VoiceInteractionService.SERVICE_META_DATA);
            try {
                if (loadXmlMetaData == null) {
                    this.mParseError = "No android.voice_interaction meta-data for " + serviceInfo.packageName;
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                        return;
                    }
                    return;
                }
                android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"voice-interaction-service".equals(loadXmlMetaData.getName())) {
                    this.mParseError = "Meta-data does not start with voice-interaction-service tag";
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                        return;
                    }
                    return;
                }
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.VoiceInteractionService);
                this.mSessionService = obtainAttributes.getString(1);
                this.mRecognitionService = obtainAttributes.getString(2);
                this.mSettingsActivity = obtainAttributes.getString(0);
                this.mSupportsAssist = obtainAttributes.getBoolean(3, false);
                this.mSupportsLaunchFromKeyguard = obtainAttributes.getBoolean(4, false);
                this.mSupportsLocalInteraction = obtainAttributes.getBoolean(5, false);
                this.mHotwordDetectionService = obtainAttributes.getString(6);
                this.mVisualQueryDetectionService = obtainAttributes.getString(7);
                obtainAttributes.recycle();
                if (this.mSessionService == null) {
                    this.mParseError = "No sessionService specified";
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                        return;
                    }
                    return;
                }
                if (this.mRecognitionService != null) {
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    this.mServiceInfo = serviceInfo;
                } else {
                    this.mParseError = "No recognitionService specified";
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                }
            } catch (java.lang.Throwable th) {
                if (loadXmlMetaData != null) {
                    try {
                        loadXmlMetaData.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            this.mParseError = "Error parsing voice interation service meta-data: " + e;
            android.util.Log.w(TAG, "error parsing voice interaction service meta-data", e);
        }
    }

    public java.lang.String getParseError() {
        return this.mParseError;
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public java.lang.String getSessionService() {
        return this.mSessionService;
    }

    public java.lang.String getRecognitionService() {
        return this.mRecognitionService;
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public boolean getSupportsAssist() {
        return this.mSupportsAssist;
    }

    public boolean getSupportsLaunchFromKeyguard() {
        return this.mSupportsLaunchFromKeyguard;
    }

    public boolean getSupportsLocalInteraction() {
        return this.mSupportsLocalInteraction;
    }

    public java.lang.String getHotwordDetectionService() {
        return this.mHotwordDetectionService;
    }

    public java.lang.String getVisualQueryDetectionService() {
        return this.mVisualQueryDetectionService;
    }
}
