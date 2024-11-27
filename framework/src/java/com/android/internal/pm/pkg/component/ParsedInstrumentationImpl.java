package com.android.internal.pm.pkg.component;

/* loaded from: classes5.dex */
public class ParsedInstrumentationImpl extends com.android.internal.pm.pkg.component.ParsedComponentImpl implements com.android.internal.pm.pkg.component.ParsedInstrumentation, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedInstrumentationImpl> CREATOR = new android.os.Parcelable.Creator<com.android.internal.pm.pkg.component.ParsedInstrumentationImpl>() { // from class: com.android.internal.pm.pkg.component.ParsedInstrumentationImpl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.pm.pkg.component.ParsedInstrumentationImpl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl[] newArray(int i) {
            return new com.android.internal.pm.pkg.component.ParsedInstrumentationImpl[i];
        }
    };
    private boolean functionalTest;
    private boolean handleProfiling;
    private java.lang.String targetPackage;
    private java.lang.String targetProcesses;

    public ParsedInstrumentationImpl() {
    }

    public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl setTargetPackage(java.lang.String str) {
        this.targetPackage = android.text.TextUtils.safeIntern(str);
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl setTargetProcesses(java.lang.String str) {
        this.targetProcesses = android.text.TextUtils.safeIntern(str);
        return this;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("Instrumentation{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        android.content.ComponentName.appendShortString(sb, getPackageName(), getName());
        sb.append('}');
        return sb.toString();
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.targetPackage, parcel, i);
        com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.parcel(this.targetProcesses, parcel, i);
        parcel.writeBoolean(this.handleProfiling);
        parcel.writeBoolean(this.functionalTest);
    }

    protected ParsedInstrumentationImpl(android.os.Parcel parcel) {
        super(parcel);
        this.targetPackage = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.targetProcesses = com.android.internal.pm.parsing.pkg.PackageImpl.sForInternedString.unparcel(parcel);
        this.handleProfiling = parcel.readByte() != 0;
        this.functionalTest = parcel.readByte() != 0;
    }

    public ParsedInstrumentationImpl(java.lang.String str, java.lang.String str2, boolean z, boolean z2) {
        this.targetPackage = str;
        this.targetProcesses = str2;
        this.handleProfiling = z;
        this.functionalTest = z2;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public java.lang.String getTargetPackage() {
        return this.targetPackage;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public java.lang.String getTargetProcesses() {
        return this.targetProcesses;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public boolean isHandleProfiling() {
        return this.handleProfiling;
    }

    @Override // com.android.internal.pm.pkg.component.ParsedInstrumentation
    public boolean isFunctionalTest() {
        return this.functionalTest;
    }

    public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl setHandleProfiling(boolean z) {
        this.handleProfiling = z;
        return this;
    }

    public com.android.internal.pm.pkg.component.ParsedInstrumentationImpl setFunctionalTest(boolean z) {
        this.functionalTest = z;
        return this;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
