package android.view.textservice;

/* loaded from: classes4.dex */
public final class SpellCheckerInfo implements android.os.Parcelable {
    private final java.lang.String mId;
    private final int mLabel;
    private final android.content.pm.ResolveInfo mService;
    private final java.lang.String mSettingsActivityName;
    private final java.util.ArrayList<android.view.textservice.SpellCheckerSubtype> mSubtypes = new java.util.ArrayList<>();
    private static final java.lang.String TAG = android.view.textservice.SpellCheckerInfo.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.view.textservice.SpellCheckerInfo> CREATOR = new android.os.Parcelable.Creator<android.view.textservice.SpellCheckerInfo>() { // from class: android.view.textservice.SpellCheckerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SpellCheckerInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.textservice.SpellCheckerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.textservice.SpellCheckerInfo[] newArray(int i) {
            return new android.view.textservice.SpellCheckerInfo[i];
        }
    };

    public SpellCheckerInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        int i;
        this.mService = resolveInfo;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mId = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, android.view.textservice.SpellCheckerSession.SERVICE_META_DATA);
                if (loadXmlMetaData == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("No android.view.textservice.scs meta-data");
                }
                android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    i = 2;
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"spell-checker".equals(loadXmlMetaData.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with spell-checker tag");
                }
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.SpellChecker);
                int resourceId = obtainAttributes.getResourceId(0, 0);
                java.lang.String string = obtainAttributes.getString(1);
                obtainAttributes.recycle();
                int depth = loadXmlMetaData.getDepth();
                while (true) {
                    int next2 = loadXmlMetaData.next();
                    if ((next2 != 3 || loadXmlMetaData.getDepth() > depth) && next2 != 1) {
                        if (next2 != i) {
                            i = 2;
                        } else {
                            if (!"subtype".equals(loadXmlMetaData.getName())) {
                                throw new org.xmlpull.v1.XmlPullParserException("Meta-data in spell-checker does not start with subtype tag");
                            }
                            android.content.res.TypedArray obtainAttributes2 = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.SpellChecker_Subtype);
                            android.view.textservice.SpellCheckerSubtype spellCheckerSubtype = new android.view.textservice.SpellCheckerSubtype(obtainAttributes2.getResourceId(0, 0), obtainAttributes2.getString(1), obtainAttributes2.getString(4), obtainAttributes2.getString(i), obtainAttributes2.getInt(3, 0));
                            obtainAttributes2.recycle();
                            this.mSubtypes.add(spellCheckerSubtype);
                            i = 2;
                        }
                    }
                }
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                this.mLabel = resourceId;
                this.mSettingsActivityName = string;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Caught exception: " + e);
                throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
            }
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public SpellCheckerInfo(android.os.Parcel parcel) {
        this.mLabel = parcel.readInt();
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
        parcel.readTypedList(this.mSubtypes, android.view.textservice.SpellCheckerSubtype.CREATOR);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public java.lang.String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLabel);
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        this.mService.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSubtypes);
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        if (this.mLabel == 0 || packageManager == null) {
            return "";
        }
        return packageManager.getText(getPackageName(), this.mLabel, this.mService.serviceInfo.applicationInfo);
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public int getSubtypeCount() {
        return this.mSubtypes.size();
    }

    public android.view.textservice.SpellCheckerSubtype getSubtypeAt(int i) {
        return this.mSubtypes.get(i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "mId=" + this.mId);
        printWriter.println(str + "mSettingsActivityName=" + this.mSettingsActivityName);
        printWriter.println(str + "Service:");
        this.mService.dump(new android.util.PrintWriterPrinter(printWriter), str + "  ");
        int subtypeCount = getSubtypeCount();
        for (int i = 0; i < subtypeCount; i++) {
            android.view.textservice.SpellCheckerSubtype subtypeAt = getSubtypeAt(i);
            printWriter.println(str + "  Subtype #" + i + ":");
            printWriter.println(str + "    locale=" + subtypeAt.getLocale() + " languageTag=" + subtypeAt.getLanguageTag());
            printWriter.println(str + "    extraValue=" + subtypeAt.getExtraValue());
        }
    }
}
