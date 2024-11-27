package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class InputMethodInfo implements android.os.Parcelable {
    public static final java.lang.String ACTION_IME_LANGUAGE_SETTINGS = "android.view.inputmethod.action.IME_LANGUAGE_SETTINGS";
    public static final java.lang.String ACTION_STYLUS_HANDWRITING_SETTINGS = "android.view.inputmethod.action.STYLUS_HANDWRITING_SETTINGS";
    public static final int COMPONENT_NAME_MAX_LENGTH = 1000;
    public static final android.os.Parcelable.Creator<android.view.inputmethod.InputMethodInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.InputMethodInfo>() { // from class: android.view.inputmethod.InputMethodInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputMethodInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.InputMethodInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.InputMethodInfo[] newArray(int i) {
            return new android.view.inputmethod.InputMethodInfo[i];
        }
    };
    public static final int MAX_IMES_PER_PACKAGE = 20;
    static final java.lang.String TAG = "InputMethodInfo";
    private final boolean mForceDefault;
    private final int mHandledConfigChanges;
    final java.lang.String mId;
    private final boolean mInlineSuggestionsEnabled;
    private final boolean mIsAuxIme;
    final int mIsDefaultResId;
    final boolean mIsVirtualDeviceOnly;
    final boolean mIsVrOnly;
    private final java.lang.String mLanguageSettingsActivityName;
    final android.content.pm.ResolveInfo mService;
    final java.lang.String mSettingsActivityName;
    private final boolean mShowInInputMethodPicker;
    private final java.lang.String mStylusHandwritingSettingsActivityAttr;
    private final android.view.inputmethod.InputMethodSubtypeArray mSubtypes;
    private final boolean mSupportsConnectionlessStylusHandwriting;
    private final boolean mSupportsInlineSuggestionsWithTouchExploration;
    private final boolean mSupportsStylusHandwriting;
    private final boolean mSupportsSwitchingToNextInputMethod;
    private final boolean mSuppressesSpellChecker;

    public static java.lang.String computeId(android.content.pm.ResolveInfo resolveInfo) {
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        return new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
    }

    public InputMethodInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this(context, resolveInfo, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x02b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public InputMethodInfo(android.content.Context context, android.content.pm.ResolveInfo resolveInfo, java.util.List<android.view.inputmethod.InputMethodSubtype> list) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.XmlResourceParser xmlResourceParser;
        android.content.pm.ServiceInfo serviceInfo;
        android.content.res.XmlResourceParser loadXmlMetaData;
        int next;
        boolean z;
        boolean z2;
        int i;
        this.mService = resolveInfo;
        android.content.pm.ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
        this.mId = computeId(resolveInfo);
        this.mForceDefault = false;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        try {
            loadXmlMetaData = serviceInfo2.loadXmlMetaData(packageManager, android.view.inputmethod.InputMethod.SERVICE_META_DATA);
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e) {
            serviceInfo = serviceInfo2;
            xmlResourceParser = null;
        } catch (java.lang.Throwable th) {
            th = th;
            xmlResourceParser = null;
        }
        try {
            try {
                if (loadXmlMetaData == null) {
                    throw new org.xmlpull.v1.XmlPullParserException("No android.view.im meta-data");
                }
                try {
                    android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo2.applicationInfo);
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"input-method".equals(loadXmlMetaData.getName())) {
                        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with input-method tag");
                    }
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.InputMethod);
                    java.lang.String string = obtainAttributes.getString(2);
                    java.lang.String string2 = android.view.inputmethod.Flags.imeSwitcherRevamp() ? obtainAttributes.getString(13) : null;
                    if ((serviceInfo2.name != null && serviceInfo2.name.length() > 1000) || ((string != null && string.length() > 1000) || (string2 != null && string2.length() > 1000))) {
                        throw new org.xmlpull.v1.XmlPullParserException("Activity name exceeds maximum of 1000 characters");
                    }
                    boolean z3 = obtainAttributes.getBoolean(4, false);
                    boolean z4 = obtainAttributes.getBoolean(12, false);
                    int resourceId = obtainAttributes.getResourceId(1, 0);
                    boolean z5 = obtainAttributes.getBoolean(3, false);
                    boolean z6 = obtainAttributes.getBoolean(5, false);
                    boolean z7 = obtainAttributes.getBoolean(9, false);
                    boolean z8 = obtainAttributes.getBoolean(6, false);
                    boolean z9 = obtainAttributes.getBoolean(7, true);
                    this.mHandledConfigChanges = obtainAttributes.getInt(0, 0);
                    this.mSupportsStylusHandwriting = obtainAttributes.getBoolean(8, false);
                    this.mSupportsConnectionlessStylusHandwriting = obtainAttributes.getBoolean(14, false);
                    java.lang.String string3 = obtainAttributes.getString(10);
                    obtainAttributes.recycle();
                    int depth = loadXmlMetaData.getDepth();
                    boolean z10 = true;
                    while (true) {
                        int next2 = loadXmlMetaData.next();
                        if (next2 == 3 && loadXmlMetaData.getDepth() <= depth) {
                            z = z6;
                            z2 = z7;
                            i = 0;
                            break;
                        }
                        if (next2 == 1) {
                            z = z6;
                            z2 = z7;
                            i = 0;
                            break;
                        }
                        if (next2 != 2) {
                            resourcesForApplication = resourcesForApplication;
                            asAttributeSet = asAttributeSet;
                            depth = depth;
                        } else {
                            if (!"subtype".equals(loadXmlMetaData.getName())) {
                                throw new org.xmlpull.v1.XmlPullParserException("Meta-data in input-method does not start with subtype tag");
                            }
                            android.content.res.TypedArray obtainAttributes2 = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.InputMethod_Subtype);
                            android.content.res.Resources resources = resourcesForApplication;
                            java.lang.String string4 = obtainAttributes2.getString(10);
                            java.lang.String string5 = obtainAttributes2.getString(11);
                            android.util.AttributeSet attributeSet = asAttributeSet;
                            boolean z11 = z7;
                            int i2 = depth;
                            boolean z12 = z6;
                            android.view.inputmethod.InputMethodSubtype build = new android.view.inputmethod.InputMethodSubtype.InputMethodSubtypeBuilder().setSubtypeNameResId(obtainAttributes2.getResourceId(0, 0)).setSubtypeIconResId(obtainAttributes2.getResourceId(1, 0)).setPhysicalKeyboardHint(string4 == null ? null : new android.icu.util.ULocale(string4), string5 == null ? "" : string5).setLanguageTag(obtainAttributes2.getString(9)).setSubtypeLocale(obtainAttributes2.getString(2)).setSubtypeMode(obtainAttributes2.getString(3)).setSubtypeExtraValue(obtainAttributes2.getString(4)).setIsAuxiliary(obtainAttributes2.getBoolean(5, false)).setOverridesImplicitlyEnabledSubtype(obtainAttributes2.getBoolean(6, false)).setSubtypeId(obtainAttributes2.getInt(7, 0)).setIsAsciiCapable(obtainAttributes2.getBoolean(8, false)).build();
                            obtainAttributes2.recycle();
                            z10 = build.isAuxiliary() ? z10 : false;
                            arrayList.add(build);
                            z6 = z12;
                            resourcesForApplication = resources;
                            asAttributeSet = attributeSet;
                            depth = i2;
                            z7 = z11;
                        }
                    }
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    boolean z13 = arrayList.size() == 0 ? i : z10;
                    if (list != null) {
                        int size = list.size();
                        while (i < size) {
                            android.view.inputmethod.InputMethodSubtype inputMethodSubtype = list.get(i);
                            if (arrayList.contains(inputMethodSubtype)) {
                                android.util.Slog.w(TAG, "Duplicated subtype definition found: " + inputMethodSubtype.getLocale() + ", " + inputMethodSubtype.getMode());
                            } else {
                                arrayList.add(inputMethodSubtype);
                            }
                            i++;
                        }
                    }
                    this.mSubtypes = new android.view.inputmethod.InputMethodSubtypeArray(arrayList);
                    this.mSettingsActivityName = string;
                    this.mLanguageSettingsActivityName = string2;
                    this.mStylusHandwritingSettingsActivityAttr = string3;
                    this.mIsDefaultResId = resourceId;
                    this.mIsAuxIme = z13;
                    this.mSupportsSwitchingToNextInputMethod = z5;
                    this.mInlineSuggestionsEnabled = z;
                    this.mSupportsInlineSuggestionsWithTouchExploration = z2;
                    this.mSuppressesSpellChecker = z8;
                    this.mShowInInputMethodPicker = z9;
                    this.mIsVrOnly = z3;
                    this.mIsVirtualDeviceOnly = z4;
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e2) {
                    serviceInfo = serviceInfo2;
                    xmlResourceParser = loadXmlMetaData;
                    try {
                        throw new org.xmlpull.v1.XmlPullParserException("Unable to create context for: " + serviceInfo.packageName);
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        if (xmlResourceParser != null) {
                        }
                        throw th;
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException | java.lang.IndexOutOfBoundsException | java.lang.NumberFormatException e3) {
            }
        } catch (java.lang.Throwable th3) {
            th = th3;
            xmlResourceParser = loadXmlMetaData;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public InputMethodInfo(android.view.inputmethod.InputMethodInfo inputMethodInfo) {
        this.mId = inputMethodInfo.mId;
        this.mSettingsActivityName = inputMethodInfo.mSettingsActivityName;
        this.mLanguageSettingsActivityName = inputMethodInfo.mLanguageSettingsActivityName;
        this.mIsDefaultResId = inputMethodInfo.mIsDefaultResId;
        this.mIsAuxIme = inputMethodInfo.mIsAuxIme;
        this.mSupportsSwitchingToNextInputMethod = inputMethodInfo.mSupportsSwitchingToNextInputMethod;
        this.mInlineSuggestionsEnabled = inputMethodInfo.mInlineSuggestionsEnabled;
        this.mSupportsInlineSuggestionsWithTouchExploration = inputMethodInfo.mSupportsInlineSuggestionsWithTouchExploration;
        this.mSuppressesSpellChecker = inputMethodInfo.mSuppressesSpellChecker;
        this.mShowInInputMethodPicker = inputMethodInfo.mShowInInputMethodPicker;
        this.mIsVrOnly = inputMethodInfo.mIsVrOnly;
        this.mIsVirtualDeviceOnly = inputMethodInfo.mIsVirtualDeviceOnly;
        this.mService = inputMethodInfo.mService;
        this.mSubtypes = inputMethodInfo.mSubtypes;
        this.mHandledConfigChanges = inputMethodInfo.mHandledConfigChanges;
        this.mSupportsStylusHandwriting = inputMethodInfo.mSupportsStylusHandwriting;
        this.mSupportsConnectionlessStylusHandwriting = inputMethodInfo.mSupportsConnectionlessStylusHandwriting;
        this.mForceDefault = inputMethodInfo.mForceDefault;
        this.mStylusHandwritingSettingsActivityAttr = inputMethodInfo.mStylusHandwritingSettingsActivityAttr;
    }

    InputMethodInfo(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mSettingsActivityName = parcel.readString();
        this.mLanguageSettingsActivityName = parcel.readString8();
        this.mIsDefaultResId = parcel.readInt();
        this.mIsAuxIme = parcel.readInt() == 1;
        this.mSupportsSwitchingToNextInputMethod = parcel.readInt() == 1;
        this.mInlineSuggestionsEnabled = parcel.readInt() == 1;
        this.mSupportsInlineSuggestionsWithTouchExploration = parcel.readInt() == 1;
        this.mSuppressesSpellChecker = parcel.readBoolean();
        this.mShowInInputMethodPicker = parcel.readBoolean();
        this.mIsVrOnly = parcel.readBoolean();
        this.mIsVirtualDeviceOnly = parcel.readBoolean();
        this.mService = android.content.pm.ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mSubtypes = new android.view.inputmethod.InputMethodSubtypeArray(parcel);
        this.mHandledConfigChanges = parcel.readInt();
        this.mSupportsStylusHandwriting = parcel.readBoolean();
        this.mSupportsConnectionlessStylusHandwriting = parcel.readBoolean();
        this.mStylusHandwritingSettingsActivityAttr = parcel.readString8();
        this.mForceDefault = false;
    }

    public InputMethodInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, 0, false, false, null, false);
    }

    public InputMethodInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3, boolean z, java.lang.String str4) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, 0, z, false, str4, false);
    }

    public InputMethodInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3, java.lang.String str4, boolean z, java.lang.String str5) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, str4, null, 0, false, true, false, false, false, 0, z, false, str5, false);
    }

    public InputMethodInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3, java.lang.String str4, boolean z, boolean z2, java.lang.String str5) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, str4, null, 0, false, true, false, false, false, 0, z, z2, str5, false);
    }

    public InputMethodInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String str3, int i) {
        this(buildFakeResolveInfo(str, str2, charSequence), false, str3, null, null, 0, false, true, false, false, false, i, false, false, null, false);
    }

    public InputMethodInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.List<android.view.inputmethod.InputMethodSubtype> list, int i, boolean z2) {
        this(resolveInfo, z, str, null, list, i, z2, true, false, false, false, 0, false, false, null, false);
    }

    public InputMethodInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.util.List<android.view.inputmethod.InputMethodSubtype> list, int i, boolean z2, boolean z3, boolean z4) {
        this(resolveInfo, z, str, null, list, i, z2, z3, false, z4, false, 0, false, false, null, false);
    }

    public InputMethodInfo(android.content.pm.ResolveInfo resolveInfo, boolean z, java.lang.String str, java.lang.String str2, java.util.List<android.view.inputmethod.InputMethodSubtype> list, int i, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2, boolean z7, boolean z8, java.lang.String str3, boolean z9) {
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        this.mService = resolveInfo;
        this.mId = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString();
        this.mSettingsActivityName = str;
        this.mLanguageSettingsActivityName = str2;
        this.mIsDefaultResId = i;
        this.mIsAuxIme = z;
        this.mSubtypes = new android.view.inputmethod.InputMethodSubtypeArray(list);
        this.mForceDefault = z2;
        this.mSupportsSwitchingToNextInputMethod = z3;
        this.mInlineSuggestionsEnabled = z4;
        this.mSupportsInlineSuggestionsWithTouchExploration = z9;
        this.mSuppressesSpellChecker = false;
        this.mShowInInputMethodPicker = true;
        this.mIsVrOnly = z5;
        this.mIsVirtualDeviceOnly = z6;
        this.mHandledConfigChanges = i2;
        this.mSupportsStylusHandwriting = z7;
        this.mSupportsConnectionlessStylusHandwriting = z8;
        this.mStylusHandwritingSettingsActivityAttr = str3;
    }

    private static android.content.pm.ResolveInfo buildFakeResolveInfo(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence) {
        android.content.pm.ResolveInfo resolveInfo = new android.content.pm.ResolveInfo();
        android.content.pm.ServiceInfo serviceInfo = new android.content.pm.ServiceInfo();
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.enabled = true;
        serviceInfo.applicationInfo = applicationInfo;
        serviceInfo.enabled = true;
        serviceInfo.packageName = str;
        serviceInfo.name = str2;
        serviceInfo.exported = true;
        serviceInfo.nonLocalizedLabel = charSequence;
        resolveInfo.serviceInfo = serviceInfo;
        return resolveInfo;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.String getPackageName() {
        return this.mService.serviceInfo.packageName;
    }

    public java.lang.String getServiceName() {
        return this.mService.serviceInfo.name;
    }

    public android.content.pm.ServiceInfo getServiceInfo() {
        return this.mService.serviceInfo;
    }

    public android.content.ComponentName getComponent() {
        return new android.content.ComponentName(this.mService.serviceInfo.packageName, this.mService.serviceInfo.name);
    }

    public java.lang.CharSequence loadLabel(android.content.pm.PackageManager packageManager) {
        return this.mService.loadLabel(packageManager);
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.pm.PackageManager packageManager) {
        return this.mService.loadIcon(packageManager);
    }

    public java.lang.String getSettingsActivity() {
        return this.mSettingsActivityName;
    }

    public boolean isVrOnly() {
        return this.mIsVrOnly;
    }

    @android.annotation.SystemApi
    public boolean isVirtualDeviceOnly() {
        return this.mIsVirtualDeviceOnly;
    }

    public int getSubtypeCount() {
        return this.mSubtypes.getCount();
    }

    public android.view.inputmethod.InputMethodSubtype getSubtypeAt(int i) {
        return this.mSubtypes.get(i);
    }

    public int getIsDefaultResourceId() {
        return this.mIsDefaultResId;
    }

    public boolean isDefault(android.content.Context context) {
        if (this.mForceDefault) {
            return true;
        }
        try {
            if (getIsDefaultResourceId() == 0) {
                return false;
            }
            return context.createPackageContext(getPackageName(), 0).getResources().getBoolean(getIsDefaultResourceId());
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
            return false;
        }
    }

    public int getConfigChanges() {
        return this.mHandledConfigChanges;
    }

    public boolean supportsStylusHandwriting() {
        return this.mSupportsStylusHandwriting;
    }

    public boolean supportsConnectionlessStylusHandwriting() {
        return this.mSupportsConnectionlessStylusHandwriting;
    }

    public android.content.Intent createStylusHandwritingSettingsActivityIntent() {
        if (android.text.TextUtils.isEmpty(this.mStylusHandwritingSettingsActivityAttr) || !this.mSupportsStylusHandwriting) {
            return null;
        }
        return new android.content.Intent(ACTION_STYLUS_HANDWRITING_SETTINGS).setComponent(new android.content.ComponentName(getServiceInfo().packageName, this.mStylusHandwritingSettingsActivityAttr));
    }

    public android.content.Intent createImeLanguageSettingsActivityIntent() {
        if (android.text.TextUtils.isEmpty(this.mLanguageSettingsActivityName)) {
            return null;
        }
        return new android.content.Intent(ACTION_IME_LANGUAGE_SETTINGS).setComponent(new android.content.ComponentName(getServiceInfo().packageName, this.mLanguageSettingsActivityName));
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        printer.println(str + "mId=" + this.mId + " mSettingsActivityName=" + this.mSettingsActivityName + " mLanguageSettingsActivityName=" + this.mLanguageSettingsActivityName + " mIsVrOnly=" + this.mIsVrOnly + " mIsVirtualDeviceOnly=" + this.mIsVirtualDeviceOnly + " mSupportsSwitchingToNextInputMethod=" + this.mSupportsSwitchingToNextInputMethod + " mInlineSuggestionsEnabled=" + this.mInlineSuggestionsEnabled + " mSupportsInlineSuggestionsWithTouchExploration=" + this.mSupportsInlineSuggestionsWithTouchExploration + " mSuppressesSpellChecker=" + this.mSuppressesSpellChecker + " mShowInInputMethodPicker=" + this.mShowInInputMethodPicker + " mSupportsStylusHandwriting=" + this.mSupportsStylusHandwriting + " mSupportsConnectionlessStylusHandwriting=" + this.mSupportsConnectionlessStylusHandwriting + " mStylusHandwritingSettingsActivityAttr=" + this.mStylusHandwritingSettingsActivityAttr);
        printer.println(str + "mIsDefaultResId=0x" + java.lang.Integer.toHexString(this.mIsDefaultResId));
        printer.println(str + "Service:");
        this.mService.dump(printer, str + "  ");
    }

    public java.lang.String toString() {
        return "InputMethodInfo{" + this.mId + ", settings: " + this.mSettingsActivityName + ", languageSettings: " + this.mLanguageSettingsActivityName + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof android.view.inputmethod.InputMethodInfo)) {
            return false;
        }
        return this.mId.equals(((android.view.inputmethod.InputMethodInfo) obj).mId);
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    public boolean isSystem() {
        return (this.mService.serviceInfo.applicationInfo.flags & 1) != 0;
    }

    public boolean isAuxiliaryIme() {
        return this.mIsAuxIme;
    }

    public boolean supportsSwitchingToNextInputMethod() {
        return this.mSupportsSwitchingToNextInputMethod;
    }

    public boolean isInlineSuggestionsEnabled() {
        return this.mInlineSuggestionsEnabled;
    }

    public boolean supportsInlineSuggestionsWithTouchExploration() {
        return this.mSupportsInlineSuggestionsWithTouchExploration;
    }

    public boolean suppressesSpellChecker() {
        return this.mSuppressesSpellChecker;
    }

    public boolean shouldShowInInputMethodPicker() {
        return this.mShowInInputMethodPicker;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeString(this.mSettingsActivityName);
        parcel.writeString8(this.mLanguageSettingsActivityName);
        parcel.writeInt(this.mIsDefaultResId);
        parcel.writeInt(this.mIsAuxIme ? 1 : 0);
        parcel.writeInt(this.mSupportsSwitchingToNextInputMethod ? 1 : 0);
        parcel.writeInt(this.mInlineSuggestionsEnabled ? 1 : 0);
        parcel.writeInt(this.mSupportsInlineSuggestionsWithTouchExploration ? 1 : 0);
        parcel.writeBoolean(this.mSuppressesSpellChecker);
        parcel.writeBoolean(this.mShowInInputMethodPicker);
        parcel.writeBoolean(this.mIsVrOnly);
        parcel.writeBoolean(this.mIsVirtualDeviceOnly);
        this.mService.writeToParcel(parcel, i);
        this.mSubtypes.writeToParcel(parcel);
        parcel.writeInt(this.mHandledConfigChanges);
        parcel.writeBoolean(this.mSupportsStylusHandwriting);
        parcel.writeBoolean(this.mSupportsConnectionlessStylusHandwriting);
        parcel.writeString8(this.mStylusHandwritingSettingsActivityAttr);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
