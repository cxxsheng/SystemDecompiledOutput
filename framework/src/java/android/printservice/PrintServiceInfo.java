package android.printservice;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class PrintServiceInfo implements android.os.Parcelable {
    private static final java.lang.String TAG_PRINT_SERVICE = "print-service";
    private final java.lang.String mAddPrintersActivityName;
    private final java.lang.String mAdvancedPrintOptionsActivityName;
    private final java.lang.String mId;
    private boolean mIsEnabled;
    private final android.content.pm.ResolveInfo mResolveInfo;
    private final java.lang.String mSettingsActivityName;
    private static final java.lang.String LOG_TAG = android.printservice.PrintServiceInfo.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.printservice.PrintServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.printservice.PrintServiceInfo>() { // from class: android.printservice.PrintServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.printservice.PrintServiceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.printservice.PrintServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.printservice.PrintServiceInfo[] newArray(int i) {
            return new android.printservice.PrintServiceInfo[i];
        }
    };

    public PrintServiceInfo(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mIsEnabled = parcel.readByte() != 0;
        this.mResolveInfo = (android.content.pm.ResolveInfo) parcel.readParcelable(null, android.content.pm.ResolveInfo.class);
        this.mSettingsActivityName = parcel.readString();
        this.mAddPrintersActivityName = parcel.readString();
        this.mAdvancedPrintOptionsActivityName = parcel.readString();
    }

    public PrintServiceInfo(android.content.pm.ResolveInfo resolveInfo, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        this.mId = new android.content.ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name).flattenToString();
        this.mResolveInfo = resolveInfo;
        this.mSettingsActivityName = str;
        this.mAddPrintersActivityName = str2;
        this.mAdvancedPrintOptionsActivityName = str3;
    }

    public android.content.ComponentName getComponentName() {
        return new android.content.ComponentName(this.mResolveInfo.serviceInfo.packageName, this.mResolveInfo.serviceInfo.name);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00da, code lost:
    
        if (r0 != null) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.printservice.PrintServiceInfo create(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.res.XmlResourceParser loadXmlMetaData = resolveInfo.serviceInfo.loadXmlMetaData(packageManager, android.printservice.PrintService.SERVICE_META_DATA);
        java.lang.String str4 = null;
        if (loadXmlMetaData != null) {
            for (int i = 0; i != 1 && i != 2; i = loadXmlMetaData.next()) {
                try {
                    try {
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        str = null;
                        str2 = null;
                        android.util.Log.e(LOG_TAG, "Unable to load resources for: " + resolveInfo.serviceInfo.packageName);
                        if (loadXmlMetaData != null) {
                        }
                        return new android.printservice.PrintServiceInfo(resolveInfo, str4, str2, str);
                    } catch (java.io.IOException e2) {
                        e = e2;
                        str3 = null;
                        str2 = null;
                        android.util.Log.w(LOG_TAG, "Error reading meta-data:" + e);
                    } catch (org.xmlpull.v1.XmlPullParserException e3) {
                        e = e3;
                        str3 = null;
                        str2 = null;
                        android.util.Log.w(LOG_TAG, "Error reading meta-data:" + e);
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        str = str3;
                        return new android.printservice.PrintServiceInfo(resolveInfo, str4, str2, str);
                    }
                } finally {
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                }
            }
            if (TAG_PRINT_SERVICE.equals(loadXmlMetaData.getName())) {
                android.content.res.TypedArray obtainAttributes = packageManager.getResourcesForApplication(resolveInfo.serviceInfo.applicationInfo).obtainAttributes(android.util.Xml.asAttributeSet(loadXmlMetaData), com.android.internal.R.styleable.PrintService);
                java.lang.String string = obtainAttributes.getString(0);
                try {
                    str2 = obtainAttributes.getString(1);
                } catch (android.content.pm.PackageManager.NameNotFoundException e4) {
                    str = null;
                    str2 = null;
                } catch (java.io.IOException e5) {
                    e = e5;
                    str2 = null;
                    str4 = string;
                    str3 = null;
                } catch (org.xmlpull.v1.XmlPullParserException e6) {
                    e = e6;
                    str2 = null;
                    str4 = string;
                    str3 = null;
                }
                try {
                    str4 = obtainAttributes.getString(3);
                    obtainAttributes.recycle();
                    str = str4;
                    str4 = string;
                } catch (android.content.pm.PackageManager.NameNotFoundException e7) {
                    str = str4;
                    str4 = string;
                    android.util.Log.e(LOG_TAG, "Unable to load resources for: " + resolveInfo.serviceInfo.packageName);
                    if (loadXmlMetaData != null) {
                    }
                    return new android.printservice.PrintServiceInfo(resolveInfo, str4, str2, str);
                } catch (java.io.IOException e8) {
                    e = e8;
                    str3 = str4;
                    str4 = string;
                    android.util.Log.w(LOG_TAG, "Error reading meta-data:" + e);
                } catch (org.xmlpull.v1.XmlPullParserException e9) {
                    e = e9;
                    str3 = str4;
                    str4 = string;
                    android.util.Log.w(LOG_TAG, "Error reading meta-data:" + e);
                    if (loadXmlMetaData != null) {
                    }
                    str = str3;
                    return new android.printservice.PrintServiceInfo(resolveInfo, str4, str2, str);
                }
            } else {
                android.util.Log.e(LOG_TAG, "Ignoring meta-data that does not start with print-service tag");
                str = null;
                str2 = null;
            }
        } else {
            str = null;
            str2 = null;
        }
        return new android.printservice.PrintServiceInfo(resolveInfo, str4, str2, str);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setIsEnabled(boolean z) {
        this.mIsEnabled = z;
    }

    public android.content.pm.ResolveInfo getResolveInfo() {
        return this.mResolveInfo;
    }

    public java.lang.String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public java.lang.String getAddPrintersActivityName() {
        return this.mAddPrintersActivityName;
    }

    public java.lang.String getAdvancedOptionsActivityName() {
        return this.mAdvancedPrintOptionsActivityName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
        parcel.writeParcelable(this.mResolveInfo, 0);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString(this.mAddPrintersActivityName);
        parcel.writeString(this.mAdvancedPrintOptionsActivityName);
    }

    public int hashCode() {
        return (this.mId == null ? 0 : this.mId.hashCode()) + 31;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.printservice.PrintServiceInfo printServiceInfo = (android.printservice.PrintServiceInfo) obj;
        if (this.mId == null) {
            if (printServiceInfo.mId != null) {
                return false;
            }
        } else if (!this.mId.equals(printServiceInfo.mId)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrintServiceInfo{");
        sb.append("id=").append(this.mId);
        sb.append("isEnabled=").append(this.mIsEnabled);
        sb.append(", resolveInfo=").append(this.mResolveInfo);
        sb.append(", settingsActivityName=").append(this.mSettingsActivityName);
        sb.append(", addPrintersActivityName=").append(this.mAddPrintersActivityName);
        sb.append(", advancedPrintOptionsActivityName=").append(this.mAdvancedPrintOptionsActivityName);
        sb.append("}");
        return sb.toString();
    }
}
