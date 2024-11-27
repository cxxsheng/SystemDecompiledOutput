package android.nfc.cardemulation;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NfcFServiceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.nfc.cardemulation.NfcFServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.nfc.cardemulation.NfcFServiceInfo>() { // from class: android.nfc.cardemulation.NfcFServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.NfcFServiceInfo createFromParcel(android.os.Parcel parcel) {
            java.lang.String str;
            java.lang.String str2;
            android.content.pm.ResolveInfo createFromParcel = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
            java.lang.String readString = parcel.readString();
            java.lang.String readString2 = parcel.readString();
            if (parcel.readInt() == 0) {
                str = null;
            } else {
                str = parcel.readString();
            }
            java.lang.String readString3 = parcel.readString();
            if (parcel.readInt() == 0) {
                str2 = null;
            } else {
                str2 = parcel.readString();
            }
            return new android.nfc.cardemulation.NfcFServiceInfo(createFromParcel, readString, readString2, str, readString3, str2, parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.NfcFServiceInfo[] newArray(int i) {
            return new android.nfc.cardemulation.NfcFServiceInfo[i];
        }
    };
    private static final java.lang.String DEFAULT_T3T_PMM = "FFFFFFFFFFFFFFFF";
    static final java.lang.String TAG = "NfcFServiceInfo";
    private final java.lang.String mDescription;
    private java.lang.String mDynamicNfcid2;
    private java.lang.String mDynamicSystemCode;
    private final java.lang.String mNfcid2;
    private final android.content.pm.ResolveInfo mService;
    private final java.lang.String mSystemCode;
    private final java.lang.String mT3tPmm;
    private final int mUid;

    public NfcFServiceInfo(android.content.pm.ResolveInfo resolveInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i, java.lang.String str6) {
        this.mService = resolveInfo;
        this.mDescription = str;
        this.mSystemCode = str2;
        this.mDynamicSystemCode = str3;
        this.mNfcid2 = str4;
        this.mDynamicNfcid2 = str5;
        this.mUid = i;
        this.mT3tPmm = str6;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bc, code lost:
    
        if ("nfcid2-filter".equals(r14) == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00be, code lost:
    
        if (r11 != null) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00c0, code lost:
    
        r7 = r1.obtainAttributes(r6, com.android.internal.R.styleable.Nfcid2Filter);
        r11 = r7.getString(0).toUpperCase();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00d4, code lost:
    
        if (r11.equalsIgnoreCase("RANDOM") != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00da, code lost:
    
        if (r11.equalsIgnoreCase("NULL") != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00e0, code lost:
    
        if (isValidNfcid2(r11) != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00e2, code lost:
    
        android.util.Log.e(android.nfc.cardemulation.NfcFServiceInfo.TAG, "Invalid NFCID2: " + r11);
        r11 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00f9, code lost:
    
        r7.recycle();
        r7 = 1;
     */
    /* JADX WARN: Removed duplicated region for block: B:98:0x017a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NfcFServiceInfo(android.content.pm.PackageManager packageManager, android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.XmlResourceParser xmlResourceParser;
        int i;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        try {
            android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, "android.nfc.cardemulation.host_nfcf_service");
            try {
                if (loadXmlMetaData == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("No android.nfc.cardemulation.host_nfcf_service meta-data");
                }
                int eventType = loadXmlMetaData.getEventType();
                while (true) {
                    i = 1;
                    if (eventType == 2 || eventType == 1) {
                        break;
                    } else {
                        eventType = loadXmlMetaData.next();
                    }
                }
                if (!"host-nfcf-service".equals(loadXmlMetaData.getName())) {
                    throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with <host-nfcf-service> tag");
                }
                android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.HostNfcFService);
                this.mService = resolveInfo;
                this.mDescription = obtainAttributes.getString(0);
                this.mDynamicSystemCode = null;
                this.mDynamicNfcid2 = null;
                obtainAttributes.recycle();
                int depth = loadXmlMetaData.getDepth();
                java.lang.String str = null;
                java.lang.String str2 = null;
                java.lang.String str3 = null;
                while (true) {
                    int next = loadXmlMetaData.next();
                    if ((next != 3 || loadXmlMetaData.getDepth() > depth) && next != i) {
                        java.lang.String name = loadXmlMetaData.getName();
                        if (next == 2 && "system-code-filter".equals(name) && str == null) {
                            android.content.res.TypedArray obtainAttributes2 = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.SystemCodeFilter);
                            str = obtainAttributes2.getString(0).toUpperCase();
                            if (!isValidSystemCode(str) && !str.equalsIgnoreCase("NULL")) {
                                android.util.Log.e(TAG, "Invalid System Code: " + str);
                                str = null;
                            }
                            obtainAttributes2.recycle();
                            i = 1;
                        } else if (next == 2 && name.equals("t3tPmm-filter") && str3 == null) {
                            android.content.res.TypedArray obtainAttributes3 = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.T3tPmmFilter);
                            str3 = obtainAttributes3.getString(0).toUpperCase();
                            obtainAttributes3.recycle();
                            i = 1;
                        } else {
                            i = 1;
                        }
                    }
                }
                this.mSystemCode = str == null ? "NULL" : str;
                this.mNfcid2 = str2 == null ? "NULL" : str2;
                this.mT3tPmm = str3 == null ? DEFAULT_T3T_PMM : str3;
                if (loadXmlMetaData != null) {
                    loadXmlMetaData.close();
                }
                this.mUid = serviceInfo.applicationInfo.uid;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                xmlResourceParser = loadXmlMetaData;
                try {
                    throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
                } catch (java.lang.Throwable th) {
                    th = th;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                xmlResourceParser = loadXmlMetaData;
                if (xmlResourceParser != null) {
                }
                throw th;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            xmlResourceParser = null;
        } catch (java.lang.Throwable th3) {
            th = th3;
            xmlResourceParser = null;
        }
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public java.lang.String getSystemCode() {
        return this.mDynamicSystemCode == null ? this.mSystemCode : this.mDynamicSystemCode;
    }

    public void setDynamicSystemCode(java.lang.String str) {
        this.mDynamicSystemCode = str;
    }

    public java.lang.String getNfcid2() {
        return this.mDynamicNfcid2 == null ? this.mNfcid2 : this.mDynamicNfcid2;
    }

    public void setDynamicNfcid2(java.lang.String str) {
        this.mDynamicNfcid2 = str;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public java.lang.String getT3tPmm() {
        return this.mT3tPmm;
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("NfcFService: ");
        sb.append(getComponent());
        sb.append(", UID: " + this.mUid);
        sb.append(", description: " + this.mDescription);
        sb.append(", System Code: " + this.mSystemCode);
        if (this.mDynamicSystemCode != null) {
            sb.append(", dynamic System Code: " + this.mDynamicSystemCode);
        }
        sb.append(", NFCID2: " + this.mNfcid2);
        if (this.mDynamicNfcid2 != null) {
            sb.append(", dynamic NFCID2: " + this.mDynamicNfcid2);
        }
        sb.append(", T3T PMM:" + this.mT3tPmm);
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.nfc.cardemulation.NfcFServiceInfo)) {
            return false;
        }
        android.nfc.cardemulation.NfcFServiceInfo nfcFServiceInfo = (android.nfc.cardemulation.NfcFServiceInfo) obj;
        return nfcFServiceInfo.getComponent().equals(getComponent()) && nfcFServiceInfo.getUid() == getUid() && nfcFServiceInfo.mSystemCode.equalsIgnoreCase(this.mSystemCode) && nfcFServiceInfo.mNfcid2.equalsIgnoreCase(this.mNfcid2) && nfcFServiceInfo.mT3tPmm.equalsIgnoreCase(this.mT3tPmm);
    }

    public int hashCode() {
        return getComponent().hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mService.writeToParcel(parcel, i);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mSystemCode);
        parcel.writeInt(this.mDynamicSystemCode != null ? 1 : 0);
        if (this.mDynamicSystemCode != null) {
            parcel.writeString(this.mDynamicSystemCode);
        }
        parcel.writeString(this.mNfcid2);
        parcel.writeInt(this.mDynamicNfcid2 == null ? 0 : 1);
        if (this.mDynamicNfcid2 != null) {
            parcel.writeString(this.mDynamicNfcid2);
        }
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mT3tPmm);
    }

    public void dump(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println("    System Code: " + getSystemCode());
        printWriter.println("    NFCID2: " + getNfcid2());
        printWriter.println("    T3tPmm: " + getT3tPmm());
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        getComponent().dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1138166333442L, getDescription());
        protoOutputStream.write(1138166333443L, getSystemCode());
        protoOutputStream.write(1138166333444L, getNfcid2());
        protoOutputStream.write(1138166333445L, getT3tPmm());
    }

    private static boolean isValidSystemCode(java.lang.String str) {
        if (str == null) {
            return false;
        }
        if (str.length() != 4) {
            android.util.Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
        if (!str.startsWith("4") || str.toUpperCase().endsWith("FF")) {
            android.util.Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
        try {
            java.lang.Integer.parseInt(str, 16);
            return true;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "System Code " + str + " is not a valid System Code.");
            return false;
        }
    }

    private static boolean isValidNfcid2(java.lang.String str) {
        if (str == null) {
            return false;
        }
        if (str.length() != 16) {
            android.util.Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
        if (!str.toUpperCase().startsWith("02FE")) {
            android.util.Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
        try {
            java.lang.Long.parseLong(str, 16);
            return true;
        } catch (java.lang.NumberFormatException e) {
            android.util.Log.e(TAG, "NFCID2 " + str + " is not a valid NFCID2.");
            return false;
        }
    }
}
