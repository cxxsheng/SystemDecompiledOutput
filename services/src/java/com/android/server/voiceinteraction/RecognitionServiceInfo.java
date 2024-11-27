package com.android.server.voiceinteraction;

/* loaded from: classes2.dex */
class RecognitionServiceInfo {
    private static final java.lang.String TAG = "RecognitionServiceInfo";
    private final java.lang.String mParseError;
    private final boolean mSelectableAsDefault;
    private final android.content.pm.ServiceInfo mServiceInfo;

    static java.util.List<com.android.server.voiceinteraction.RecognitionServiceInfo> getAvailableServices(@android.annotation.NonNull android.content.Context context, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = context.getPackageManager().queryIntentServicesAsUser(new android.content.Intent("android.speech.RecognitionService"), 786432, i).iterator();
        while (it.hasNext()) {
            com.android.server.voiceinteraction.RecognitionServiceInfo parseInfo = parseInfo(context.getPackageManager(), ((android.content.pm.ResolveInfo) it.next()).serviceInfo);
            if (!android.text.TextUtils.isEmpty(parseInfo.mParseError)) {
                android.util.Log.w(TAG, "Parse error in getAvailableServices: " + parseInfo.mParseError);
            }
            arrayList.add(parseInfo);
        }
        return arrayList;
    }

    static com.android.server.voiceinteraction.RecognitionServiceInfo parseInfo(@android.annotation.NonNull android.content.pm.PackageManager packageManager, @android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo) {
        java.lang.String str;
        android.content.res.XmlResourceParser loadXmlMetaData;
        boolean z = true;
        try {
            loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, "android.speech");
            try {
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
            str = "Error parsing recognition service meta-data: " + e;
        }
        if (loadXmlMetaData == null) {
            com.android.server.voiceinteraction.RecognitionServiceInfo recognitionServiceInfo = new com.android.server.voiceinteraction.RecognitionServiceInfo(serviceInfo, true, "No android.speech meta-data for " + serviceInfo.packageName);
            if (loadXmlMetaData != null) {
                loadXmlMetaData.close();
            }
            return recognitionServiceInfo;
        }
        android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
        for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
        }
        if (!"recognition-service".equals(loadXmlMetaData.getName())) {
            throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with recognition-service tag");
        }
        android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.RecognitionService);
        z = obtainAttributes.getBoolean(1, true);
        obtainAttributes.recycle();
        loadXmlMetaData.close();
        str = "";
        return new com.android.server.voiceinteraction.RecognitionServiceInfo(serviceInfo, z, str);
    }

    private RecognitionServiceInfo(@android.annotation.NonNull android.content.pm.ServiceInfo serviceInfo, boolean z, @android.annotation.NonNull java.lang.String str) {
        this.mServiceInfo = serviceInfo;
        this.mSelectableAsDefault = z;
        this.mParseError = str;
    }

    @android.annotation.NonNull
    public java.lang.String getParseError() {
        return this.mParseError;
    }

    @android.annotation.NonNull
    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mServiceInfo;
    }

    public boolean isSelectableAsDefault() {
        return this.mSelectableAsDefault;
    }
}
