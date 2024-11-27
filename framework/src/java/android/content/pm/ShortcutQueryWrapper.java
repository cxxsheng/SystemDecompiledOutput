package android.content.pm;

/* loaded from: classes.dex */
public final class ShortcutQueryWrapper extends android.content.pm.LauncherApps.ShortcutQuery implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.ShortcutQueryWrapper> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ShortcutQueryWrapper>() { // from class: android.content.pm.ShortcutQueryWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ShortcutQueryWrapper[] newArray(int i) {
            return new android.content.pm.ShortcutQueryWrapper[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.ShortcutQueryWrapper createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.ShortcutQueryWrapper(parcel);
        }
    };

    public ShortcutQueryWrapper(android.content.pm.LauncherApps.ShortcutQuery shortcutQuery) {
        this();
        this.mChangedSince = shortcutQuery.mChangedSince;
        this.mPackage = shortcutQuery.mPackage;
        this.mLocusIds = shortcutQuery.mLocusIds;
        this.mShortcutIds = shortcutQuery.mShortcutIds;
        this.mActivity = shortcutQuery.mActivity;
        this.mQueryFlags = shortcutQuery.mQueryFlags;
    }

    public long getChangedSince() {
        return this.mChangedSince;
    }

    public java.lang.String getPackage() {
        return this.mPackage;
    }

    public java.util.List<android.content.LocusId> getLocusIds() {
        return this.mLocusIds;
    }

    public java.util.List<java.lang.String> getShortcutIds() {
        return this.mShortcutIds;
    }

    public android.content.ComponentName getActivity() {
        return this.mActivity;
    }

    public int getQueryFlags() {
        return this.mQueryFlags;
    }

    public ShortcutQueryWrapper() {
    }

    public java.lang.String toString() {
        return "ShortcutQueryWrapper {  }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        byte b = this.mPackage != null ? (byte) 2 : (byte) 0;
        if (this.mShortcutIds != null) {
            b = (byte) (b | 4);
        }
        if (this.mLocusIds != null) {
            b = (byte) (b | 8);
        }
        if (this.mActivity != null) {
            b = (byte) (b | 16);
        }
        parcel.writeByte(b);
        parcel.writeLong(this.mChangedSince);
        if (this.mPackage != null) {
            parcel.writeString(this.mPackage);
        }
        if (this.mShortcutIds != null) {
            parcel.writeStringList(this.mShortcutIds);
        }
        if (this.mLocusIds != null) {
            parcel.writeParcelableList(this.mLocusIds, i);
        }
        if (this.mActivity != null) {
            parcel.writeTypedObject(this.mActivity, i);
        }
        parcel.writeInt(this.mQueryFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ShortcutQueryWrapper(android.os.Parcel parcel) {
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        byte readByte = parcel.readByte();
        long readLong = parcel.readLong();
        java.lang.String readString = (readByte & 2) == 0 ? null : parcel.readString();
        if ((readByte & 4) == 0) {
            arrayList = null;
        } else {
            arrayList = new java.util.ArrayList();
            parcel.readStringList(arrayList);
        }
        if ((readByte & 8) == 0) {
            arrayList2 = null;
        } else {
            arrayList2 = new java.util.ArrayList();
            parcel.readParcelableList(arrayList2, android.content.LocusId.class.getClassLoader(), android.content.LocusId.class);
        }
        android.content.ComponentName componentName = (readByte & 16) == 0 ? null : (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
        int readInt = parcel.readInt();
        this.mChangedSince = readLong;
        this.mPackage = readString;
        this.mShortcutIds = arrayList;
        this.mLocusIds = arrayList2;
        this.mActivity = componentName;
        this.mQueryFlags = readInt;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.content.pm.LauncherApps.ShortcutQuery.QueryFlags.class, (java.lang.annotation.Annotation) null, this.mQueryFlags);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
