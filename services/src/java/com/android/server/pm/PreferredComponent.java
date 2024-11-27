package com.android.server.pm;

/* loaded from: classes2.dex */
public class PreferredComponent {
    private static final java.lang.String ATTR_ALWAYS = "always";
    private static final java.lang.String ATTR_MATCH = "match";
    private static final java.lang.String ATTR_NAME = "name";
    private static final java.lang.String ATTR_SET = "set";
    private static final java.lang.String TAG_SET = "set";
    public final boolean mAlways;
    private final com.android.server.pm.PreferredComponent.Callbacks mCallbacks;
    public final android.content.ComponentName mComponent;
    public final int mMatch;
    private java.lang.String mParseError;
    final java.lang.String[] mSetClasses;
    final java.lang.String[] mSetComponents;
    final java.lang.String[] mSetPackages;
    final java.lang.String mShortComponent;

    public interface Callbacks {
        boolean onReadTag(java.lang.String str, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException;
    }

    public PreferredComponent(com.android.server.pm.PreferredComponent.Callbacks callbacks, int i, android.content.ComponentName[] componentNameArr, android.content.ComponentName componentName, boolean z) {
        this.mCallbacks = callbacks;
        this.mMatch = 268369920 & i;
        this.mComponent = componentName;
        this.mAlways = z;
        this.mShortComponent = componentName.flattenToShortString();
        this.mParseError = null;
        if (componentNameArr != null) {
            int length = componentNameArr.length;
            java.lang.String[] strArr = new java.lang.String[length];
            java.lang.String[] strArr2 = new java.lang.String[length];
            java.lang.String[] strArr3 = new java.lang.String[length];
            for (int i2 = 0; i2 < length; i2++) {
                android.content.ComponentName componentName2 = componentNameArr[i2];
                if (componentName2 == null) {
                    this.mSetPackages = null;
                    this.mSetClasses = null;
                    this.mSetComponents = null;
                    return;
                } else {
                    strArr[i2] = componentName2.getPackageName().intern();
                    strArr2[i2] = componentName2.getClassName().intern();
                    strArr3[i2] = componentName2.flattenToShortString();
                }
            }
            this.mSetPackages = strArr;
            this.mSetClasses = strArr2;
            this.mSetComponents = strArr3;
            return;
        }
        this.mSetPackages = null;
        this.mSetClasses = null;
        this.mSetComponents = null;
    }

    public PreferredComponent(com.android.server.pm.PreferredComponent.Callbacks callbacks, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mCallbacks = callbacks;
        this.mShortComponent = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
        this.mComponent = android.content.ComponentName.unflattenFromString(this.mShortComponent);
        if (this.mComponent == null) {
            this.mParseError = "Bad activity name " + this.mShortComponent;
        }
        int i = 0;
        this.mMatch = typedXmlPullParser.getAttributeIntHex((java.lang.String) null, ATTR_MATCH, 0);
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "set", 0);
        this.mAlways = typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_ALWAYS, true);
        java.lang.String[] strArr = attributeInt > 0 ? new java.lang.String[attributeInt] : null;
        java.lang.String[] strArr2 = attributeInt > 0 ? new java.lang.String[attributeInt] : null;
        java.lang.String[] strArr3 = attributeInt > 0 ? new java.lang.String[attributeInt] : null;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals("set")) {
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "name");
                    if (attributeValue == null) {
                        if (this.mParseError == null) {
                            this.mParseError = "No name in set tag in preferred activity " + this.mShortComponent;
                        }
                    } else if (i >= attributeInt) {
                        if (this.mParseError == null) {
                            this.mParseError = "Too many set tags in preferred activity " + this.mShortComponent;
                        }
                    } else {
                        android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(attributeValue);
                        if (unflattenFromString == null) {
                            if (this.mParseError == null) {
                                this.mParseError = "Bad set name " + attributeValue + " in preferred activity " + this.mShortComponent;
                            }
                        } else {
                            strArr[i] = unflattenFromString.getPackageName();
                            strArr2[i] = unflattenFromString.getClassName();
                            strArr3[i] = attributeValue;
                            i++;
                        }
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else if (!this.mCallbacks.onReadTag(name, typedXmlPullParser)) {
                    android.util.Slog.w("PreferredComponent", "Unknown element: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        if (i != attributeInt && this.mParseError == null) {
            this.mParseError = "Not enough set tags (expected " + attributeInt + " but found " + i + ") in " + this.mShortComponent;
        }
        this.mSetPackages = strArr;
        this.mSetClasses = strArr2;
        this.mSetComponents = strArr3;
    }

    public java.lang.String getParseError() {
        return this.mParseError;
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z) throws java.io.IOException {
        int length = this.mSetClasses != null ? this.mSetClasses.length : 0;
        typedXmlSerializer.attribute((java.lang.String) null, "name", this.mShortComponent);
        if (z) {
            if (this.mMatch != 0) {
                typedXmlSerializer.attributeIntHex((java.lang.String) null, ATTR_MATCH, this.mMatch);
            }
            typedXmlSerializer.attributeBoolean((java.lang.String) null, ATTR_ALWAYS, this.mAlways);
            typedXmlSerializer.attributeInt((java.lang.String) null, "set", length);
            for (int i = 0; i < length; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, "set");
                typedXmlSerializer.attribute((java.lang.String) null, "name", this.mSetComponents[i]);
                typedXmlSerializer.endTag((java.lang.String) null, "set");
            }
        }
    }

    public boolean sameSet(java.util.List<android.content.pm.ResolveInfo> list, boolean z, int i) {
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal;
        com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal;
        boolean z2;
        if (this.mSetPackages == null) {
            return list == null;
        }
        if (list == null) {
            return false;
        }
        int size = list.size();
        int length = this.mSetPackages.length;
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        java.lang.String setupWizardPackageName = packageManagerInternal.getSetupWizardPackageName();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            android.content.pm.ActivityInfo activityInfo = list.get(i3).activityInfo;
            if ((!z || !activityInfo.packageName.equals(setupWizardPackageName)) && (packageStateInternal = packageManagerInternal.getPackageStateInternal(activityInfo.packageName)) != null && (packageUserStateInternal = packageStateInternal.getUserStates().get(i)) != null && packageUserStateInternal.getInstallReason() != 3) {
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        z2 = false;
                        break;
                    }
                    if (!this.mSetPackages[i4].equals(activityInfo.packageName) || !this.mSetClasses[i4].equals(activityInfo.name)) {
                        i4++;
                    } else {
                        i2++;
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    return false;
                }
            }
        }
        return i2 == length;
    }

    public boolean sameSet(android.content.ComponentName[] componentNameArr) {
        if (this.mSetPackages == null) {
            return false;
        }
        int length = componentNameArr.length;
        int length2 = this.mSetPackages.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i >= length) {
                return i2 == length2;
            }
            android.content.ComponentName componentName = componentNameArr[i];
            int i3 = 0;
            while (true) {
                if (i3 >= length2) {
                    z = false;
                    break;
                }
                if (!this.mSetPackages[i3].equals(componentName.getPackageName()) || !this.mSetClasses[i3].equals(componentName.getClassName())) {
                    i3++;
                } else {
                    i2++;
                    break;
                }
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    public boolean sameSet(com.android.server.pm.PreferredComponent preferredComponent) {
        if (this.mSetPackages == null || preferredComponent == null || preferredComponent.mSetPackages == null || !sameComponent(preferredComponent.mComponent)) {
            return false;
        }
        int length = preferredComponent.mSetPackages.length;
        int length2 = this.mSetPackages.length;
        if (length != length2) {
            return false;
        }
        for (int i = 0; i < length2; i++) {
            if (!this.mSetPackages[i].equals(preferredComponent.mSetPackages[i]) || !this.mSetClasses[i].equals(preferredComponent.mSetClasses[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean sameComponent(android.content.ComponentName componentName) {
        return this.mComponent != null && componentName != null && this.mComponent.getPackageName().equals(componentName.getPackageName()) && this.mComponent.getClassName().equals(componentName.getClassName());
    }

    public boolean isSuperset(java.util.List<android.content.pm.ResolveInfo> list, boolean z) {
        boolean z2;
        if (this.mSetPackages == null) {
            return list == null;
        }
        if (list == null) {
            return true;
        }
        int size = list.size();
        int length = this.mSetPackages.length;
        if (!z && length < size) {
            return false;
        }
        java.lang.String setupWizardPackageName = ((android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class)).getSetupWizardPackageName();
        for (int i = 0; i < size; i++) {
            android.content.pm.ActivityInfo activityInfo = list.get(i).activityInfo;
            if (!z || !activityInfo.packageName.equals(setupWizardPackageName)) {
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z2 = false;
                        break;
                    }
                    if (!this.mSetPackages[i2].equals(activityInfo.packageName) || !this.mSetClasses[i2].equals(activityInfo.name)) {
                        i2++;
                    } else {
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    return false;
                }
            }
        }
        return true;
    }

    public android.content.ComponentName[] discardObsoleteComponents(java.util.List<android.content.pm.ResolveInfo> list) {
        if (this.mSetPackages == null || list == null) {
            return new android.content.ComponentName[0];
        }
        int size = list.size();
        int length = this.mSetPackages.length;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < size; i++) {
            android.content.pm.ActivityInfo activityInfo = list.get(i).activityInfo;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                if (!this.mSetPackages[i2].equals(activityInfo.packageName) || !this.mSetClasses[i2].equals(activityInfo.name)) {
                    i2++;
                } else {
                    arrayList.add(new android.content.ComponentName(this.mSetPackages[i2], this.mSetClasses[i2]));
                    break;
                }
            }
        }
        return (android.content.ComponentName[]) arrayList.toArray(new android.content.ComponentName[arrayList.size()]);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Object obj) {
        printWriter.print(str);
        printWriter.print(java.lang.Integer.toHexString(java.lang.System.identityHashCode(obj)));
        printWriter.print(' ');
        printWriter.println(this.mShortComponent);
        printWriter.print(str);
        printWriter.print(" mMatch=0x");
        printWriter.print(java.lang.Integer.toHexString(this.mMatch));
        printWriter.print(" mAlways=");
        printWriter.println(this.mAlways);
        if (this.mSetComponents != null) {
            printWriter.print(str);
            printWriter.println("  Selected from:");
            for (int i = 0; i < this.mSetComponents.length; i++) {
                printWriter.print(str);
                printWriter.print("    ");
                printWriter.println(this.mSetComponents[i]);
            }
        }
    }
}
