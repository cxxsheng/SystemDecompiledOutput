package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedUsesPermissionImpl implements com.android.internal.pm.pkg.component.ParsedUsesPermission, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl> CREATOR;
    static com.android.internal.util.Parcelling<java.lang.String> sParcellingForName;
    private java.lang.String name;
    private int usesPermissionFlags;

    public ParsedUsesPermissionImpl(java.lang.String str, int i) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.usesPermissionFlags = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) com.android.internal.pm.pkg.component.ParsedUsesPermission.UsesPermissionFlags.class, (java.lang.annotation.Annotation) null, i);
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public java.lang.String getName() {
        return this.name;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedUsesPermission
    public int getUsesPermissionFlags() {
        return this.usesPermissionFlags;
    }

    public com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl setName(java.lang.String str) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl setUsesPermissionFlags(int i) {
        this.usesPermissionFlags = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) com.android.internal.pm.pkg.component.ParsedUsesPermission.UsesPermissionFlags.class, (java.lang.annotation.Annotation) null, this.usesPermissionFlags);
        return this;
    }

    static {
        sParcellingForName = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedString.class);
        if (sParcellingForName == null) {
            sParcellingForName = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedString());
        }
        CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl[] newArray(int i) {
                return new com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl createFromParcel(android.os.Parcel parcel) {
                return new com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        sParcellingForName.parcel(this.name, parcel, i);
        parcel.writeInt(this.usesPermissionFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ParsedUsesPermissionImpl(android.os.Parcel parcel) {
        java.lang.String unparcel = sParcellingForName.unparcel(parcel);
        int readInt = parcel.readInt();
        this.name = unparcel;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        this.usesPermissionFlags = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) com.android.internal.pm.pkg.component.ParsedUsesPermission.UsesPermissionFlags.class, (java.lang.annotation.Annotation) null, this.usesPermissionFlags);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
