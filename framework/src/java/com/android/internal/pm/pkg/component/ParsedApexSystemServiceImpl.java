package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedApexSystemServiceImpl implements com.android.internal.pm.pkg.component.ParsedApexSystemService, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl> CREATOR;
    static com.android.internal.util.Parcelling<java.lang.String> sParcellingForJarPath;
    static com.android.internal.util.Parcelling<java.lang.String> sParcellingForMaxSdkVersion;
    static com.android.internal.util.Parcelling<java.lang.String> sParcellingForMinSdkVersion;
    static com.android.internal.util.Parcelling<java.lang.String> sParcellingForName;
    private int initOrder;
    private java.lang.String jarPath;
    private java.lang.String maxSdkVersion;
    private java.lang.String minSdkVersion;
    private java.lang.String name;

    public ParsedApexSystemServiceImpl() {
    }

    public ParsedApexSystemServiceImpl(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.jarPath = str2;
        this.minSdkVersion = str3;
        this.maxSdkVersion = str4;
        this.initOrder = i;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public java.lang.String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public java.lang.String getJarPath() {
        return this.jarPath;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public java.lang.String getMinSdkVersion() {
        return this.minSdkVersion;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public java.lang.String getMaxSdkVersion() {
        return this.maxSdkVersion;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedApexSystemService
    public int getInitOrder() {
        return this.initOrder;
    }

    public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl setName(java.lang.String str) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl setJarPath(java.lang.String str) {
        this.jarPath = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl setMinSdkVersion(java.lang.String str) {
        this.minSdkVersion = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl setMaxSdkVersion(java.lang.String str) {
        this.maxSdkVersion = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl setInitOrder(int i) {
        this.initOrder = i;
        return this;
    }

    static {
        sParcellingForName = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForName == null) {
            sParcellingForName = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForJarPath = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForJarPath == null) {
            sParcellingForJarPath = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForMinSdkVersion = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForMinSdkVersion == null) {
            sParcellingForMinSdkVersion = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedString());
        }
        sParcellingForMaxSdkVersion = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForMaxSdkVersion == null) {
            sParcellingForMaxSdkVersion = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl[] newArray(int i) {
                return new com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.pm.pkg.component.ParsedApexSystemServiceImpl(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.jarPath != null ? (byte) 2 : (byte) 0;
        if (this.minSdkVersion != null) {
            b = (byte) (b | 4);
        }
        if (this.maxSdkVersion != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        sParcellingForName.parcel(this.name, parcel, i);
        sParcellingForJarPath.parcel(this.jarPath, parcel, i);
        sParcellingForMinSdkVersion.parcel(this.minSdkVersion, parcel, i);
        sParcellingForMaxSdkVersion.parcel(this.maxSdkVersion, parcel, i);
        parcel.writeInt(this.initOrder);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedApexSystemServiceImpl(android.os.Parcel parcel) {
        parcel.readByte();
        java.lang.String unparcel = sParcellingForName.unparcel(parcel);
        java.lang.String unparcel2 = sParcellingForJarPath.unparcel(parcel);
        java.lang.String unparcel3 = sParcellingForMinSdkVersion.unparcel(parcel);
        java.lang.String unparcel4 = sParcellingForMaxSdkVersion.unparcel(parcel);
        int readInt = parcel.readInt();
        this.name = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        this.jarPath = unparcel2;
        this.minSdkVersion = unparcel3;
        this.maxSdkVersion = unparcel4;
        this.initOrder = readInt;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
