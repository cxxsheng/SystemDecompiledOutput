package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedPermissionImpl extends com.android.internal.pm.pkg.component.ParsedComponentImpl implements com.android.internal.pm.pkg.component.ParsedPermission, android.os.Parcelable {
    private java.lang.String backgroundPermission;
    private java.lang.String group;
    private java.util.Set<java.lang.String> knownCerts;
    private com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup;
    private int protectionLevel;
    private int requestRes;
    private boolean tree;
    private static final com.android.internal.util.Parcelling.BuiltIn.ForStringSet sForStringSet = (com.android.internal.util.Parcelling.BuiltIn.ForStringSet) com.android.internal.util.Parcelling.Cache.getOrCreate(com.android.internal.util.Parcelling.BuiltIn.ForStringSet.class);
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedPermissionImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedPermissionImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedPermissionImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedPermissionImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedPermissionImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedPermissionImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedPermissionImpl[i];
        }
    };

    public ParsedPermissionImpl() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public com.android.internal.pm.pkg.component.ParsedPermissionGroup getParsedPermissionGroup() {
        return this.parsedPermissionGroup;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setGroup(java.lang.String str) {
        this.group = android.text.TextUtils.safeIntern(str);
        return this;
    }

    protected void setKnownCert(java.lang.String str) {
        this.knownCerts = java.util.Set.of(str.toUpperCase(java.util.Locale.US));
    }

    protected void setKnownCerts(java.lang.String[] strArr) {
        this.knownCerts = new android.util.ArraySet();
        for (java.lang.String str : strArr) {
            this.knownCerts.add(str.toUpperCase(java.util.Locale.US));
        }
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public java.util.Set<java.lang.String> getKnownCerts() {
        return this.knownCerts == null ? java.util.Collections.emptySet() : this.knownCerts;
    }

    public java.lang.String toString() {
        return "Permission{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + getName() + "}";
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.backgroundPermission);
        parcel.writeString(this.group);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.protectionLevel);
        parcel.writeBoolean(this.tree);
        parcel.writeParcelable((com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl) this.parsedPermissionGroup, i);
        sForStringSet.parcel(this.knownCerts, parcel, i);
    }

    protected ParsedPermissionImpl(android.os.Parcel parcel) {
        super(parcel);
        this.backgroundPermission = parcel.readString();
        this.group = android.text.TextUtils.safeIntern(parcel.readString());
        this.requestRes = parcel.readInt();
        this.protectionLevel = parcel.readInt();
        this.tree = parcel.readBoolean();
        this.parsedPermissionGroup = (com.android.internal.pm.pkg.component.ParsedPermissionGroup) parcel.readParcelable(com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl.class.getClassLoader(), com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl.class);
        this.knownCerts = sForStringSet.unparcel(parcel);
    }

    public ParsedPermissionImpl(java.lang.String str, java.lang.String str2, int i, int i2, boolean z, com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup, java.util.Set<java.lang.String> set) {
        this.backgroundPermission = str;
        this.group = str2;
        this.requestRes = i;
        this.protectionLevel = i2;
        this.tree = z;
        this.parsedPermissionGroup = parsedPermissionGroup;
        this.knownCerts = set;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public java.lang.String getBackgroundPermission() {
        return this.backgroundPermission;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public java.lang.String getGroup() {
        return this.group;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public int getRequestRes() {
        return this.requestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public int getProtectionLevel() {
        return this.protectionLevel;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermission
    public boolean isTree() {
        return this.tree;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setBackgroundPermission(java.lang.String str) {
        this.backgroundPermission = str;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setRequestRes(int i) {
        this.requestRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setProtectionLevel(int i) {
        this.protectionLevel = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setTree(boolean z) {
        this.tree = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setParsedPermissionGroup(com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup) {
        this.parsedPermissionGroup = parsedPermissionGroup;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionImpl setKnownCerts(java.util.Set<java.lang.String> set) {
        this.knownCerts = set;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
