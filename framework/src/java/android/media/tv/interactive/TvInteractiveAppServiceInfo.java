package android.media.tv.interactive;

/* loaded from: classes2.dex */
public final class TvInteractiveAppServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.media.tv.interactive.TvInteractiveAppServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.media.tv.interactive.TvInteractiveAppServiceInfo>() { // from class: android.media.tv.interactive.TvInteractiveAppServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.interactive.TvInteractiveAppServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.media.tv.interactive.TvInteractiveAppServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.media.tv.interactive.TvInteractiveAppServiceInfo[] newArray(int i) {
            return new android.media.tv.interactive.TvInteractiveAppServiceInfo[i];
        }
    };
    private static final boolean DEBUG = false;
    public static final int INTERACTIVE_APP_TYPE_ATSC = 2;
    public static final int INTERACTIVE_APP_TYPE_GINGA = 4;
    public static final int INTERACTIVE_APP_TYPE_HBBTV = 1;
    public static final int INTERACTIVE_APP_TYPE_OTHER = Integer.MIN_VALUE;
    public static final int INTERACTIVE_APP_TYPE_TARGETED_AD = 8;
    private static final java.lang.String TAG = "TvInteractiveAppServiceInfo";
    private static final java.lang.String XML_START_TAG_NAME = "tv-interactive-app";
    private final java.util.List<java.lang.String> mExtraTypes;
    private final java.lang.String mId;
    private final android.content.pm.ResolveInfo mService;
    private int mTypes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface InteractiveAppType {
    }

    public TvInteractiveAppServiceInfo(android.content.Context context, android.content.ComponentName componentName) {
        this.mExtraTypes = new java.util.ArrayList();
        if (context == null) {
            throw new java.lang.IllegalArgumentException("context cannot be null.");
        }
        android.content.pm.ResolveInfo resolveService = context.getPackageManager().resolveService(new android.content.Intent(android.media.tv.interactive.TvInteractiveAppService.SERVICE_INTERFACE).setComponent(componentName), 132);
        if (resolveService == null) {
            throw new java.lang.IllegalArgumentException("Invalid component. Can't find the service.");
        }
        java.lang.String generateInteractiveAppServiceId = generateInteractiveAppServiceId(new android.content.ComponentName(resolveService.serviceInfo.packageName, resolveService.serviceInfo.name));
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parseServiceMetadata(resolveService, context, arrayList);
        this.mService = resolveService;
        this.mId = generateInteractiveAppServiceId;
        toTypesFlag(arrayList);
    }

    private TvInteractiveAppServiceInfo(android.content.pm.ResolveInfo resolveInfo, java.lang.String str, int i, java.util.List<java.lang.String> list) {
        this.mExtraTypes = new java.util.ArrayList();
        this.mService = resolveInfo;
        this.mId = str;
        this.mTypes = i;
        this.mExtraTypes.addAll(list);
    }

    private TvInteractiveAppServiceInfo(android.os.Parcel parcel) {
        this.mExtraTypes = new java.util.ArrayList();
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mId = parcel.readString();
        this.mTypes = parcel.readInt();
        parcel.readStringList(this.mExtraTypes);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mId);
        parcel.writeInt(this.mTypes);
        parcel.writeStringList(this.mExtraTypes);
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

    public int getSupportedTypes() {
        return this.mTypes;
    }

    public java.util.List<java.lang.String> getCustomSupportedTypes() {
        return this.mExtraTypes;
    }

    private static java.lang.String generateInteractiveAppServiceId(android.content.ComponentName componentName) {
        return componentName.flattenToShortString();
    }

    private static void parseServiceMetadata(android.content.pm.ResolveInfo resolveInfo, android.content.Context context, java.util.List<java.lang.String> list) {
        int next;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.media.tv.interactive.TvInteractiveAppService.SERVICE_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new java.lang.IllegalStateException("No android.media.tv.interactive.app meta-data found for " + serviceInfo.name);
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
                        throw new java.lang.IllegalStateException("Meta-data does not start with tv-interactive-app tag for " + serviceInfo.name);
                    }
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.TvInteractiveAppService);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void toTypesFlag(java.util.List<java.lang.String> list) {
        char c;
        this.mTypes = 0;
        this.mExtraTypes.clear();
        for (java.lang.String str : list) {
            switch (str.hashCode()) {
                case -2079519534:
                    if (str.equals("targeted_ad")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 3004867:
                    if (str.equals("atsc")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 98359718:
                    if (str.equals("ginga")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 99063594:
                    if (str.equals("hbbtv")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mTypes |= 1;
                    continue;
                case 1:
                    this.mTypes |= 2;
                    continue;
                case 2:
                    this.mTypes |= 4;
                    continue;
                case 3:
                    this.mTypes |= 8;
                    break;
            }
            this.mTypes |= Integer.MIN_VALUE;
            this.mExtraTypes.add(str);
        }
    }
}
