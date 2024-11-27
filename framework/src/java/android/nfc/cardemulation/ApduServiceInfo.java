package android.nfc.cardemulation;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ApduServiceInfo implements android.os.Parcelable {
    private static final java.lang.String TAG = "ApduServiceInfo";
    private final java.util.Map<java.lang.String, java.lang.Boolean> mAutoTransact;
    private final int mBannerResourceId;
    private boolean mCategoryOtherServiceEnabled;
    private final java.lang.String mDescription;
    private final java.util.HashMap<java.lang.String, android.nfc.cardemulation.AidGroup> mDynamicAidGroups;
    private java.lang.String mOffHostName;
    private final boolean mOnHost;
    private final boolean mRequiresDeviceScreenOn;
    private final boolean mRequiresDeviceUnlock;
    private final android.content.pm.ResolveInfo mService;
    private final java.lang.String mSettingsActivityName;
    private boolean mShouldDefaultToObserveMode;
    private final java.util.HashMap<java.lang.String, android.nfc.cardemulation.AidGroup> mStaticAidGroups;
    private final java.lang.String mStaticOffHostName;
    private final int mUid;
    public static final android.os.Parcelable.Creator<android.nfc.cardemulation.ApduServiceInfo> CREATOR = new android.os.Parcelable.Creator<android.nfc.cardemulation.ApduServiceInfo>() { // from class: android.nfc.cardemulation.ApduServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.ApduServiceInfo createFromParcel(android.os.Parcel parcel) {
            android.content.pm.ResolveInfo createFromParcel = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
            java.lang.String readString = parcel.readString();
            boolean z = parcel.readInt() != 0;
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList, android.nfc.cardemulation.AidGroup.CREATOR);
            }
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            if (parcel.readInt() > 0) {
                parcel.readTypedList(arrayList2, android.nfc.cardemulation.AidGroup.CREATOR);
            }
            boolean z2 = parcel.readInt() != 0;
            boolean z3 = parcel.readInt() != 0;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString4 = parcel.readString();
            boolean z4 = parcel.readInt() != 0;
            java.util.HashMap hashMap = new java.util.HashMap(parcel.readInt());
            parcel.readMap(hashMap, getClass().getClassLoader(), java.lang.String.class, java.lang.Boolean.class);
            return new android.nfc.cardemulation.ApduServiceInfo(createFromParcel, z, readString, arrayList, arrayList2, z2, z3, readInt, readInt2, readString4, readString2, readString3, z4, hashMap);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.nfc.cardemulation.ApduServiceInfo[] newArray(int i) {
            return new android.nfc.cardemulation.ApduServiceInfo[i];
        }
    };
    private static final java.util.regex.Pattern AID_PATTERN = java.util.regex.Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");

    public ApduServiceInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.ArrayList<android.nfc.cardemulation.AidGroup> arrayList, java.util.ArrayList<android.nfc.cardemulation.AidGroup> arrayList2, boolean z2, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        this(resolveInfo, z, str, arrayList, arrayList2, z2, i, i2, str2, str3, str4, false);
    }

    public ApduServiceInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.ArrayList<android.nfc.cardemulation.AidGroup> arrayList, java.util.ArrayList<android.nfc.cardemulation.AidGroup> arrayList2, boolean z2, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z3) {
        this(resolveInfo, z, str, arrayList, arrayList2, z2, z, i, i2, str2, str3, str4, z3);
    }

    public ApduServiceInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.List<android.nfc.cardemulation.AidGroup> list, java.util.List<android.nfc.cardemulation.AidGroup> list2, boolean z2, boolean z3, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z4) {
        this(resolveInfo, z, str, list, list2, z2, z3, i, i2, str2, str3, str4, z4, new java.util.HashMap());
    }

    public ApduServiceInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.List<android.nfc.cardemulation.AidGroup> list, java.util.List<android.nfc.cardemulation.AidGroup> list2, boolean z2, boolean z3, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4, boolean z4, java.util.HashMap<java.lang.String, java.lang.Boolean> hashMap) {
        this.mService = resolveInfo;
        this.mDescription = str;
        this.mStaticAidGroups = new java.util.HashMap<>();
        this.mDynamicAidGroups = new java.util.HashMap<>();
        this.mAutoTransact = hashMap;
        this.mOffHostName = str3;
        this.mStaticOffHostName = str4;
        this.mOnHost = z;
        this.mRequiresDeviceUnlock = z2;
        this.mRequiresDeviceScreenOn = z3;
        for (android.nfc.cardemulation.AidGroup aidGroup : list) {
            this.mStaticAidGroups.put(aidGroup.getCategory(), aidGroup);
        }
        for (android.nfc.cardemulation.AidGroup aidGroup2 : list2) {
            this.mDynamicAidGroups.put(aidGroup2.getCategory(), aidGroup2);
        }
        this.mBannerResourceId = i;
        this.mUid = i2;
        this.mSettingsActivityName = str2;
        this.mCategoryOtherServiceEnabled = z4;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x02fb: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:155:0x02fb */
    /* JADX WARN: Not initialized variable reg: 6, insn: 0x02fe: MOVE (r5 I:??[OBJECT, ARRAY]) = (r6 I:??[OBJECT, ARRAY]), block:B:153:0x02fe */
    public ApduServiceInfo(android.content.pm.PackageManager r18, android.content.pm.ResolveInfo r19, boolean r20) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 814
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.nfc.cardemulation.ApduServiceInfo.<init>(android.content.pm.PackageManager, android.content.pm.ResolveInfo, boolean):void");
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public java.lang.String getOffHostSecureElement() {
        return this.mOffHostName;
    }

    public java.util.List<java.lang.String> getAids() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.nfc.cardemulation.AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getAids());
        }
        return arrayList;
    }

    public java.util.List<java.lang.String> getPollingLoopFilters() {
        return new java.util.ArrayList(this.mAutoTransact.keySet());
    }

    public boolean getShouldAutoTransact(java.lang.String str) {
        return this.mAutoTransact.getOrDefault(str.toUpperCase(java.util.Locale.ROOT), false).booleanValue();
    }

    public java.util.List<java.lang.String> getPrefixAids() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.nfc.cardemulation.AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            for (java.lang.String str : it.next().getAids()) {
                if (str.endsWith("*")) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public java.util.List<java.lang.String> getSubsetAids() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<android.nfc.cardemulation.AidGroup> it = getAidGroups().iterator();
        while (it.hasNext()) {
            for (java.lang.String str : it.next().getAids()) {
                if (str.endsWith("#")) {
                    arrayList.add(str);
                }
            }
        }
        return arrayList;
    }

    public android.nfc.cardemulation.AidGroup getDynamicAidGroupForCategory(java.lang.String str) {
        return this.mDynamicAidGroups.get(str);
    }

    public boolean removeDynamicAidGroupForCategory(java.lang.String str) {
        return this.mDynamicAidGroups.remove(str) != null;
    }

    public java.util.List<android.nfc.cardemulation.AidGroup> getAidGroups() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<java.util.Map.Entry<java.lang.String, android.nfc.cardemulation.AidGroup>> it = this.mDynamicAidGroups.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        for (java.util.Map.Entry<java.lang.String, android.nfc.cardemulation.AidGroup> entry : this.mStaticAidGroups.entrySet()) {
            if (!this.mDynamicAidGroups.containsKey(entry.getKey())) {
                arrayList.add(entry.getValue());
            }
        }
        return arrayList;
    }

    public java.lang.String getCategoryForAid(java.lang.String str) {
        for (android.nfc.cardemulation.AidGroup aidGroup : getAidGroups()) {
            if (aidGroup.getAids().contains(str.toUpperCase())) {
                return aidGroup.getCategory();
            }
        }
        return null;
    }

    public boolean hasCategory(java.lang.String str) {
        return this.mStaticAidGroups.containsKey(str) || this.mDynamicAidGroups.containsKey(str);
    }

    public boolean isOnHost() {
        return this.mOnHost;
    }

    public boolean requiresUnlock() {
        return this.mRequiresDeviceUnlock;
    }

    public boolean requiresScreenOn() {
        return this.mRequiresDeviceScreenOn;
    }

    public boolean shouldDefaultToObserveMode() {
        return this.mShouldDefaultToObserveMode;
    }

    public void setShouldDefaultToObserveMode(boolean z) {
        this.mShouldDefaultToObserveMode = z;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public int getUid() {
        return this.mUid;
    }

    public void setDynamicAidGroup(android.nfc.cardemulation.AidGroup aidGroup) {
        this.mDynamicAidGroups.put(aidGroup.getCategory(), aidGroup);
    }

    public void addPollingLoopFilter(java.lang.String str, boolean z) {
        this.mAutoTransact.put(str, java.lang.Boolean.valueOf(z));
    }

    public void removePollingLoopFilter(java.lang.String str) {
        this.mAutoTransact.remove(str.toUpperCase(java.util.Locale.ROOT));
    }

    public void setOffHostSecureElement(java.lang.String str) {
        this.mOffHostName = str;
    }

    public void resetOffHostSecureElement() {
        this.mOffHostName = this.mStaticOffHostName;
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public java.lang.CharSequence loadAppLabel(android.content.pm.PackageManager packageManager) {
        try {
            return packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.mService.resolvePackageName, 128));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public android.graphics.drawable.Drawable loadBanner(android.content.pm.PackageManager packageManager) {
        try {
            return packageManager.getResourcesForApplication(this.mService.serviceInfo.packageName).getDrawable(this.mBannerResourceId);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Log.e(TAG, "Could not load banner.");
            return null;
        } catch (android.content.res.Resources.NotFoundException e2) {
            android.util.Log.e(TAG, "Could not load banner.");
            return null;
        }
    }

    public java.lang.String getSettingsActivityName() {
        return this.mSettingsActivityName;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ApduService: ");
        sb.append(getComponent());
        sb.append(", UID: " + this.mUid);
        sb.append(", description: " + this.mDescription);
        sb.append(", Static AID Groups: ");
        java.util.Iterator<android.nfc.cardemulation.AidGroup> it = this.mStaticAidGroups.values().iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        sb.append(", Dynamic AID Groups: ");
        java.util.Iterator<android.nfc.cardemulation.AidGroup> it2 = this.mDynamicAidGroups.values().iterator();
        while (it2.hasNext()) {
            sb.append(it2.next().toString());
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.nfc.cardemulation.ApduServiceInfo)) {
            return false;
        }
        android.nfc.cardemulation.ApduServiceInfo apduServiceInfo = (android.nfc.cardemulation.ApduServiceInfo) obj;
        return apduServiceInfo.getComponent().equals(getComponent()) && apduServiceInfo.getUid() == getUid();
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
        parcel.writeInt(this.mOnHost ? 1 : 0);
        parcel.writeString(this.mOffHostName);
        parcel.writeString(this.mStaticOffHostName);
        parcel.writeInt(this.mStaticAidGroups.size());
        if (this.mStaticAidGroups.size() > 0) {
            parcel.writeTypedList(new java.util.ArrayList(this.mStaticAidGroups.values()));
        }
        parcel.writeInt(this.mDynamicAidGroups.size());
        if (this.mDynamicAidGroups.size() > 0) {
            parcel.writeTypedList(new java.util.ArrayList(this.mDynamicAidGroups.values()));
        }
        parcel.writeInt(this.mRequiresDeviceUnlock ? 1 : 0);
        parcel.writeInt(this.mRequiresDeviceScreenOn ? 1 : 0);
        parcel.writeInt(this.mBannerResourceId);
        parcel.writeInt(this.mUid);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeInt(this.mCategoryOtherServiceEnabled ? 1 : 0);
        parcel.writeInt(this.mAutoTransact.size());
        parcel.writeMap(this.mAutoTransact);
    }

    public void dump(android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        printWriter.println("    " + getComponent() + " (Description: " + getDescription() + ") (UID: " + getUid() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        if (this.mOnHost) {
            printWriter.println("    On Host Service");
        } else {
            printWriter.println("    Off-host Service");
            printWriter.println("        Current off-host SE:" + this.mOffHostName + " static off-host SE:" + this.mStaticOffHostName);
        }
        printWriter.println("    Static AID groups:");
        for (android.nfc.cardemulation.AidGroup aidGroup : this.mStaticAidGroups.values()) {
            printWriter.println("        Category: " + aidGroup.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            java.util.Iterator<java.lang.String> it = aidGroup.getAids().iterator();
            while (it.hasNext()) {
                printWriter.println("            AID: " + it.next());
            }
        }
        printWriter.println("    Dynamic AID groups:");
        for (android.nfc.cardemulation.AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            printWriter.println("        Category: " + aidGroup2.getCategory() + "(enabled: " + this.mCategoryOtherServiceEnabled + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            java.util.Iterator<java.lang.String> it2 = aidGroup2.getAids().iterator();
            while (it2.hasNext()) {
                printWriter.println("            AID: " + it2.next());
            }
        }
        printWriter.println("    Settings Activity: " + this.mSettingsActivityName);
        printWriter.println("    Requires Device Unlock: " + this.mRequiresDeviceUnlock);
        printWriter.println("    Requires Device ScreenOn: " + this.mRequiresDeviceScreenOn);
    }

    public void setCategoryOtherServiceEnabled(boolean z) {
        this.mCategoryOtherServiceEnabled = z;
    }

    public boolean isCategoryOtherServiceEnabled() {
        return this.mCategoryOtherServiceEnabled;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream) {
        getComponent().dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1138166333442L, getDescription());
        protoOutputStream.write(1133871366147L, this.mOnHost);
        if (!this.mOnHost) {
            protoOutputStream.write(1138166333444L, this.mOffHostName);
            protoOutputStream.write(1138166333445L, this.mStaticOffHostName);
        }
        for (android.nfc.cardemulation.AidGroup aidGroup : this.mStaticAidGroups.values()) {
            long start = protoOutputStream.start(2246267895814L);
            aidGroup.dump(protoOutputStream);
            protoOutputStream.end(start);
        }
        for (android.nfc.cardemulation.AidGroup aidGroup2 : this.mDynamicAidGroups.values()) {
            long start2 = protoOutputStream.start(2246267895814L);
            aidGroup2.dump(protoOutputStream);
            protoOutputStream.end(start2);
        }
        protoOutputStream.write(1138166333448L, this.mSettingsActivityName);
    }

    private static boolean isValidAid(java.lang.String str) {
        if (str == null) {
            return false;
        }
        if ((str.endsWith("*") || str.endsWith("#")) && str.length() % 2 == 0) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!str.endsWith("*") && !str.endsWith("#") && str.length() % 2 != 0) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        if (!AID_PATTERN.matcher(str).matches()) {
            android.util.Log.e(TAG, "AID " + str + " is not a valid AID.");
            return false;
        }
        return true;
    }
}
