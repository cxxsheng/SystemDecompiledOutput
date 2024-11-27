package android.service.translation;

/* loaded from: classes3.dex */
public final class TranslationServiceInfo {
    private static final java.lang.String TAG = "TranslationServiceInfo";
    private static final java.lang.String XML_TAG_SERVICE = "translation-service";
    private final android.content.pm.ServiceInfo mServiceInfo;
    private final java.lang.String mSettingsActivity;

    private static android.content.pm.ServiceInfo getServiceInfoOrThrow(android.content.ComponentName componentName, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        int i2;
        android.content.pm.ServiceInfo serviceInfo;
        if (z) {
            i2 = 128;
        } else {
            i2 = 1048704;
        }
        try {
            serviceInfo = android.app.AppGlobals.getPackageManager().getServiceInfo(componentName, i2, i);
        } catch (android.os.RemoteException e) {
            serviceInfo = null;
        }
        if (serviceInfo == null) {
            throw new android.content.pm.PackageManager.NameNotFoundException("Could not get serviceInfo for " + (z ? " (temp)" : "(default system)") + " " + componentName.flattenToShortString());
        }
        return serviceInfo;
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivity;
    }

    public TranslationServiceInfo(android.content.Context context, android.content.ComponentName componentName, boolean z, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        this(context, getServiceInfoOrThrow(componentName, z, i));
    }

    private TranslationServiceInfo(android.content.Context context, android.content.pm.ServiceInfo serviceInfo) {
        android.content.res.TypedArray typedArray;
        if (!android.Manifest.permission.BIND_TRANSLATION_SERVICE.equals(serviceInfo.permission)) {
            android.util.Slog.w(TAG, "TranslationServiceInfo from '" + serviceInfo.packageName + "' does not require permission " + android.Manifest.permission.BIND_TRANSLATION_SERVICE);
            throw new java.lang.SecurityException("Service does not require permission android.permission.BIND_TRANSLATION_SERVICE");
        }
        this.mServiceInfo = serviceInfo;
        android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(context.getPackageManager(), android.service.translation.TranslationService.SERVICE_META_DATA);
        java.lang.String str = null;
        if (loadXmlMetaData == null) {
            this.mSettingsActivity = null;
            return;
        }
        try {
            android.content.res.Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(serviceInfo.applicationInfo);
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
            }
            if (XML_TAG_SERVICE.equals(loadXmlMetaData.getName())) {
                try {
                    typedArray = resourcesForApplication.obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.TranslationService);
                } catch (java.lang.Throwable th) {
                    th = th;
                    typedArray = null;
                }
                try {
                    str = typedArray.getString(0);
                    if (typedArray != null) {
                        typedArray.recycle();
                    }
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    if (typedArray != null) {
                        typedArray.recycle();
                    }
                    throw th;
                }
            } else {
                android.util.Log.e(TAG, "Meta-data does not start with translation-service tag");
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.e(TAG, "Error parsing auto fill service meta-data", e);
        }
        this.mSettingsActivity = str;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START).append(this.mServiceInfo);
        sb.append(", settings:").append(this.mSettingsActivity);
        return sb.toString();
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("Component: ");
        printWriter.println(getServiceInfo().getComponentName());
        printWriter.print(str);
        printWriter.print("Settings: ");
        printWriter.println(this.mSettingsActivity);
    }
}
