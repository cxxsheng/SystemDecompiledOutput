package android.content.pm;

/* loaded from: classes.dex */
public class ProcessInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ProcessInfo> CREATOR;
    static com.android.internal.util.Parcelling<android.util.ArraySet<java.lang.String>> sParcellingForDeniedPermissions;
    public android.util.ArraySet<java.lang.String> deniedPermissions;
    public int gwpAsanMode;
    public int memtagMode;
    public java.lang.String name;
    public int nativeHeapZeroInitialized;
    public boolean useEmbeddedDex;

    @java.lang.Deprecated
    public ProcessInfo(android.content.pm.ProcessInfo processInfo) {
        this.name = processInfo.name;
        this.deniedPermissions = processInfo.deniedPermissions;
        this.gwpAsanMode = processInfo.gwpAsanMode;
        this.memtagMode = processInfo.memtagMode;
        this.nativeHeapZeroInitialized = processInfo.nativeHeapZeroInitialized;
        this.useEmbeddedDex = processInfo.useEmbeddedDex;
    }

    public ProcessInfo(java.lang.String str, android.util.ArraySet<java.lang.String> arraySet, int i, int i2, int i3, boolean z) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.deniedPermissions = arraySet;
        this.gwpAsanMode = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.GwpAsanMode.class, (java.lang.annotation.Annotation) null, i);
        this.memtagMode = i2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.MemtagMode.class, (java.lang.annotation.Annotation) null, i2);
        this.nativeHeapZeroInitialized = i3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.ApplicationInfo.NativeHeapZeroInitialized.class, (java.lang.annotation.Annotation) null, i3);
        this.useEmbeddedDex = z;
    }

    static {
        sParcellingForDeniedPermissions = com.android.internal.util.Parcelling.Cache.get(com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArraySet.class);
        if (sParcellingForDeniedPermissions == null) {
            sParcellingForDeniedPermissions = com.android.internal.util.Parcelling.Cache.put(new com.android.internal.util.Parcelling.BuiltIn.ForInternedStringArraySet());
        }
        CREATOR = new android.os.Parcelable.Creator<android.content.pm.ProcessInfo>() { // from class: android.content.pm.ProcessInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.ProcessInfo[] newArray(int i) {
                return new android.content.pm.ProcessInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.ProcessInfo createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.ProcessInfo(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.useEmbeddedDex ? (byte) 32 : (byte) 0;
        if (this.deniedPermissions != null) {
            b = (byte) (b | 2);
        }
        parcel.writeByte(b);
        parcel.writeString(this.name);
        sParcellingForDeniedPermissions.parcel(this.deniedPermissions, parcel, i);
        parcel.writeInt(this.gwpAsanMode);
        parcel.writeInt(this.memtagMode);
        parcel.writeInt(this.nativeHeapZeroInitialized);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    protected ProcessInfo(android.os.Parcel parcel) {
        boolean z = (parcel.readByte() & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 0;
        java.lang.String readString = parcel.readString();
        android.util.ArraySet<java.lang.String> unparcel = sParcellingForDeniedPermissions.unparcel(parcel);
        int readInt = parcel.readInt();
        int readInt2 = parcel.readInt();
        int readInt3 = parcel.readInt();
        this.name = readString;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.name);
        this.deniedPermissions = unparcel;
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
