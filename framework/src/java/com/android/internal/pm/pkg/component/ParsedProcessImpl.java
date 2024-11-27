package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedProcessImpl implements com.android.internal.pm.pkg.component.ParsedProcess, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedProcessImpl> CREATOR;
    static com.android.internal.util.Parcelling<java.util.Set<java.lang.String>> sParcellingForDeniedPermissions;
    private android.util.ArrayMap<java.lang.String, java.lang.String> appClassNamesByPackage;
    private java.util.Set<java.lang.String> deniedPermissions;
    private int gwpAsanMode;
    private int memtagMode;
    private java.lang.String name;
    private int nativeHeapZeroInitialized;
    private boolean useEmbeddedDex;

    public ParsedProcessImpl() {
        this.appClassNamesByPackage = android.util.ArrayMap.EMPTY;
        this.deniedPermissions = java.util.Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
    }

    public ParsedProcessImpl(com.android.internal.pm.pkg.component.ParsedProcess parsedProcess) {
        this.appClassNamesByPackage = android.util.ArrayMap.EMPTY;
        this.deniedPermissions = java.util.Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.name = parsedProcess.getName();
        this.appClassNamesByPackage = parsedProcess.getAppClassNamesByPackage().size() == 0 ? android.util.ArrayMap.EMPTY : new android.util.ArrayMap<>(parsedProcess.getAppClassNamesByPackage());
        this.deniedPermissions = new android.util.ArraySet(parsedProcess.getDeniedPermissions());
        this.gwpAsanMode = parsedProcess.getGwpAsanMode();
        this.memtagMode = parsedProcess.getMemtagMode();
        this.nativeHeapZeroInitialized = parsedProcess.getNativeHeapZeroInitialized();
        this.useEmbeddedDex = parsedProcess.isUseEmbeddedDex();
    }

    public void addStateFrom(com.android.internal.pm.pkg.component.ParsedProcess parsedProcess) {
        this.deniedPermissions = com.android.internal.util.CollectionUtils.addAll(this.deniedPermissions, parsedProcess.getDeniedPermissions());
        this.gwpAsanMode = parsedProcess.getGwpAsanMode();
        this.memtagMode = parsedProcess.getMemtagMode();
        this.nativeHeapZeroInitialized = parsedProcess.getNativeHeapZeroInitialized();
        this.useEmbeddedDex = parsedProcess.isUseEmbeddedDex();
        android.util.ArrayMap<java.lang.String, java.lang.String> appClassNamesByPackage = parsedProcess.getAppClassNamesByPackage();
        for (int i = 0; i < appClassNamesByPackage.size(); i++) {
            this.appClassNamesByPackage.put(appClassNamesByPackage.keyAt(i), appClassNamesByPackage.valueAt(i));
        }
    }

    public void putAppClassNameForPackage(java.lang.String str, java.lang.String str2) {
        if (this.appClassNamesByPackage.size() == 0) {
            this.appClassNamesByPackage = new android.util.ArrayMap<>(4);
        }
        this.appClassNamesByPackage.put(str, str2);
    }

    public ParsedProcessImpl(java.lang.String str, android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap, java.util.Set<java.lang.String> set, int i, int i2, int i3, boolean z) {
        this.appClassNamesByPackage = android.util.ArrayMap.EMPTY;
        this.deniedPermissions = java.util.Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.appClassNamesByPackage = arrayMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) arrayMap);
        this.deniedPermissions = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) set);
        this.gwpAsanMode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.GwpAsanMode.class, (java.lang.annotation.Annotation) null, i);
        this.memtagMode = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.MemtagMode.class, (java.lang.annotation.Annotation) null, i2);
        this.nativeHeapZeroInitialized = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.NativeHeapZeroInitialized.class, (java.lang.annotation.Annotation) null, i3);
        this.useEmbeddedDex = z;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public java.lang.String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public android.util.ArrayMap<java.lang.String, java.lang.String> getAppClassNamesByPackage() {
        return this.appClassNamesByPackage;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public java.util.Set<java.lang.String> getDeniedPermissions() {
        return this.deniedPermissions;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getGwpAsanMode() {
        return this.gwpAsanMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getMemtagMode() {
        return this.memtagMode;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public int getNativeHeapZeroInitialized() {
        return this.nativeHeapZeroInitialized;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedProcess
    public boolean isUseEmbeddedDex() {
        return this.useEmbeddedDex;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setName(java.lang.String str) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setAppClassNamesByPackage(android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap) {
        this.appClassNamesByPackage = arrayMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.appClassNamesByPackage);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setDeniedPermissions(java.util.Set<java.lang.String> set) {
        this.deniedPermissions = set;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.deniedPermissions);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setGwpAsanMode(int i) {
        this.gwpAsanMode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.GwpAsanMode.class, (java.lang.annotation.Annotation) null, this.gwpAsanMode);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setMemtagMode(int i) {
        this.memtagMode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.MemtagMode.class, (java.lang.annotation.Annotation) null, this.memtagMode);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setNativeHeapZeroInitialized(int i) {
        this.nativeHeapZeroInitialized = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.NativeHeapZeroInitialized.class, (java.lang.annotation.Annotation) null, this.nativeHeapZeroInitialized);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedProcessImpl setUseEmbeddedDex(boolean z) {
        this.useEmbeddedDex = z;
        return this;
    }

    static {
        sParcellingForDeniedPermissions = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringSet.class);
        if (sParcellingForDeniedPermissions == null) {
            sParcellingForDeniedPermissions = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedStringSet());
        }
        CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedProcessImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedProcessImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedProcessImpl[] newArray(int i) {
                return new com.android.internal.pm.pkg.component.ParsedProcessImpl[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedProcessImpl createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.pm.pkg.component.ParsedProcessImpl(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByte(this.useEmbeddedDex ? (byte) 64 : (byte) 0);
        parcel.writeString(this.name);
        parcel.writeMap(this.appClassNamesByPackage);
        sParcellingForDeniedPermissions.parcel(this.deniedPermissions, parcel, i);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedProcessImpl(android.os.Parcel parcel) {
        this.appClassNamesByPackage = android.util.ArrayMap.EMPTY;
        this.deniedPermissions = java.util.Collections.emptySet();
        this.gwpAsanMode = -1;
        this.memtagMode = -1;
        this.nativeHeapZeroInitialized = -1;
        boolean z = (parcel.readByte() & 64) != 0;
        java.lang.String readString = parcel.readString();
        android.util.ArrayMap<java.lang.String, java.lang.String> arrayMap = new android.util.ArrayMap<>();
        parcel.readMap(arrayMap, java.lang.String.class.getClassLoader());
        java.util.Set<java.lang.String> unparcel = sParcellingForDeniedPermissions.unparcel(parcel);
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        this.name = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        this.appClassNamesByPackage = arrayMap;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.appClassNamesByPackage);
        this.deniedPermissions = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.deniedPermissions);
        this.gwpAsanMode = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.GwpAsanMode.class, (java.lang.annotation.Annotation) null, this.gwpAsanMode);
        this.memtagMode = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.MemtagMode.class, (java.lang.annotation.Annotation) null, this.memtagMode);
        this.nativeHeapZeroInitialized = readInt3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.NativeHeapZeroInitialized.class, (java.lang.annotation.Annotation) null, this.nativeHeapZeroInitialized);
        this.useEmbeddedDex = z;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
