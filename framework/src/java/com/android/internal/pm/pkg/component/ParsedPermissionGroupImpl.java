package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedPermissionGroupImpl extends com.android.internal.pm.pkg.component.ParsedComponentImpl implements com.android.internal.pm.pkg.component.ParsedPermissionGroup, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl(parcel);
        }
    };
    private int backgroundRequestDetailRes;
    private int backgroundRequestRes;
    private int priority;
    private int requestDetailRes;
    private int requestRes;

    public java.lang.String toString() {
        return "PermissionGroup{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + getName() + "}";
    }

    public ParsedPermissionGroupImpl() {
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.requestDetailRes);
        parcel.writeInt(this.backgroundRequestRes);
        parcel.writeInt(this.backgroundRequestDetailRes);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.priority);
    }

    protected ParsedPermissionGroupImpl(android.os.Parcel parcel) {
        super(parcel);
        this.requestDetailRes = parcel.readInt();
        this.backgroundRequestRes = parcel.readInt();
        this.backgroundRequestDetailRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.priority = parcel.readInt();
    }

    public ParsedPermissionGroupImpl(int i, int i2, int i3, int i4, int i5) {
        this.requestDetailRes = i;
        this.backgroundRequestRes = i2;
        this.backgroundRequestDetailRes = i3;
        this.requestRes = i4;
        this.priority = i5;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getRequestDetailRes() {
        return this.requestDetailRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getBackgroundRequestRes() {
        return this.backgroundRequestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getBackgroundRequestDetailRes() {
        return this.backgroundRequestDetailRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getRequestRes() {
        return this.requestRes;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedPermissionGroup
    public int getPriority() {
        return this.priority;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl setRequestDetailRes(int i) {
        this.requestDetailRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl setBackgroundRequestRes(int i) {
        this.backgroundRequestRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl setBackgroundRequestDetailRes(int i) {
        this.backgroundRequestDetailRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl setRequestRes(int i) {
        this.requestRes = i;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedPermissionGroupImpl setPriority(int i) {
        this.priority = i;
        return this;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
