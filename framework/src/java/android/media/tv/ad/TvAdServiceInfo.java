package android.media.tv.ad;

/* loaded from: classes2.dex */
public final class TvAdServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.ad.TvAdServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.ad.TvAdServiceInfo>() { // from class: android.media.tv.ad.TvAdServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.ad.TvAdServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.ad.TvAdServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.ad.TvAdServiceInfo[] newArray(int i) {
            return new android.media.tv.ad.TvAdServiceInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "TvAdServiceInfo";
    private static final java.lang.String XML_START_TAG_NAME = "tv-ad-service";
    private final java.lang.String mId;
    private final android.content.pm.ResolveInfo mService;
    private final java.util.List<java.lang.String> mTypes;

    public TvAdServiceInfo(android.content.Context context, android.content.ComponentName componentName) {
        this.mTypes = new java.util.ArrayList();
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context cannot be null.");
        }
        android.content.pm.ResolveInfo resolveService = context.getPackageManager().resolveService(new android.content.Intent(android.media.tv.ad.TvAdService.SERVICE_INTERFACE).setComponent(componentName), 132);
        if (resolveService == null) {
            throw new java.lang.IllegalArgumentException("Invalid component. Can't find the service.");
        }
        java.lang.String generateAdServiceId = generateAdServiceId(new android.content.ComponentName(resolveService.serviceInfo.packageName, resolveService.serviceInfo.name));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parseServiceMetadata(resolveService, context, arrayList);
        this.mService = resolveService;
        this.mId = generateAdServiceId;
        this.mTypes.addAll(arrayList);
    }

    private TvAdServiceInfo(android.content.pm.ResolveInfo resolveInfo, java.lang.String str, java.util.List<java.lang.String> list) {
        this.mTypes = new java.util.ArrayList();
        this.mService = resolveInfo;
        this.mId = str;
        this.mTypes.addAll(list);
    }

    private TvAdServiceInfo(android.os.Parcel parcel) {
        this.mTypes = new java.util.ArrayList();
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        parcel.readStringList(this.mTypes);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public java.util.List<java.lang.String> getSupportedTypes() {
        return this.mTypes;
    }

    private static java.lang.String generateAdServiceId(android.content.ComponentName componentName) {
        return componentName.flattenToShortString();
    }

    private static void parseServiceMetadata(android.content.pm.ResolveInfo resolveInfo, android.content.Context context, java.util.List<java.lang.String> list) {
        int next;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.media.tv.ad.TvAdService.SERVICE_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new java.lang.IllegalStateException("No android.media.tv.ad.service meta-data found for " + serviceInfo.name);
                    }
                    android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!XML_START_TAG_NAME.equals(loadXmlMetaData.getName())) {
                        throw new java.lang.IllegalStateException("Meta-data does not start with tv-ad-service tag for " + serviceInfo.name);
                    }
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.TvAdService);
                    for (java.lang.CharSequence charSequence : obtainAttributes.getTextArray(0)) {
                        list.add(charSequence.toString().toLowerCase());
                    }
                    obtainAttributes.recycle();
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
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
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                throw new java.lang.IllegalStateException("Failed reading meta-data for " + serviceInfo.packageName, e);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            throw new java.lang.IllegalStateException("No resources found for " + serviceInfo.packageName, e2);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeStringList(this.mTypes);
    }
}
