package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedServiceImpl extends com.android.internal.pm.pkg.component.ParsedMainComponentImpl implements com.android.internal.pm.pkg.component.ParsedService, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedServiceImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedServiceImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedServiceImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedServiceImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedServiceImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedServiceImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedServiceImpl[i];
        }
    };
    private int foregroundServiceType;
    private java.lang.String permission;

    public ParsedServiceImpl(com.android.internal.pm.pkg.component.ParsedServiceImpl parsedServiceImpl) {
        super(parsedServiceImpl);
        this.foregroundServiceType = parsedServiceImpl.foregroundServiceType;
        this.permission = parsedServiceImpl.permission;
    }

    public com.android.internal.pm.pkg.component.ParsedMainComponent setPermission(java.lang.String str) {
        this.permission = android.text.TextUtils.isEmpty(str) ? null : str.intern();
        return this;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Service{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.content.ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedMainComponentImpl, com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.foregroundServiceType);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.permission, parcel, i);
    }

    public ParsedServiceImpl() {
    }

    protected ParsedServiceImpl(android.os.Parcel parcel) {
        super(parcel);
        this.foregroundServiceType = parcel.readInt();
        this.permission = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
    }

    public ParsedServiceImpl(int i, java.lang.String str) {
        this.foregroundServiceType = i;
        this.permission = str;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedService
    public int getForegroundServiceType() {
        return this.foregroundServiceType;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedService
    public java.lang.String getPermission() {
        return this.permission;
    }

    public com.android.internal.pm.pkg.component.ParsedServiceImpl setForegroundServiceType(int i) {
        this.foregroundServiceType = i;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
