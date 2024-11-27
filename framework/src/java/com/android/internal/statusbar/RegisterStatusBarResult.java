package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public final class RegisterStatusBarResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.statusbar.RegisterStatusBarResult> CREATOR = new android.os.Parcelable.Creator<com.android.internal.statusbar.RegisterStatusBarResult>() { // from class: com.android.internal.statusbar.RegisterStatusBarResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.RegisterStatusBarResult createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.statusbar.RegisterStatusBarResult(parcel.createTypedArrayMap(com.android.internal.statusbar.StatusBarIcon.CREATOR), parcel.readInt(), parcel.readInt(), (com.android.internal.view.AppearanceRegion[]) parcel.readParcelableArray(null, com.android.internal.view.AppearanceRegion.class), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readStrongBinder(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), (com.android.internal.statusbar.LetterboxDetails[]) parcel.readParcelableArray(null, com.android.internal.statusbar.LetterboxDetails.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.RegisterStatusBarResult[] newArray(int i) {
            return new com.android.internal.statusbar.RegisterStatusBarResult[i];
        }
    };
    public final int mAppearance;
    public final com.android.internal.view.AppearanceRegion[] mAppearanceRegions;
    public final int mBehavior;
    public final int mDisabledFlags1;
    public final int mDisabledFlags2;
    public final android.util.ArrayMap<java.lang.String, com.android.internal.statusbar.StatusBarIcon> mIcons;
    public final int mImeBackDisposition;
    public final android.os.IBinder mImeToken;
    public final int mImeWindowVis;
    public final com.android.internal.statusbar.LetterboxDetails[] mLetterboxDetails;
    public final boolean mNavbarColorManagedByIme;
    public final java.lang.String mPackageName;
    public final int mRequestedVisibleTypes;
    public final boolean mShowImeSwitcher;
    public final int mTransientBarTypes;

    public RegisterStatusBarResult(android.util.ArrayMap<java.lang.String, com.android.internal.statusbar.StatusBarIcon> arrayMap, int i, int i2, com.android.internal.view.AppearanceRegion[] appearanceRegionArr, int i3, int i4, boolean z, int i5, android.os.IBinder iBinder, boolean z2, int i6, int i7, java.lang.String str, int i8, com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr) {
        this.mIcons = new android.util.ArrayMap<>(arrayMap);
        this.mDisabledFlags1 = i;
        this.mAppearance = i2;
        this.mAppearanceRegions = appearanceRegionArr;
        this.mImeWindowVis = i3;
        this.mImeBackDisposition = i4;
        this.mShowImeSwitcher = z;
        this.mDisabledFlags2 = i5;
        this.mImeToken = iBinder;
        this.mNavbarColorManagedByIme = z2;
        this.mBehavior = i6;
        this.mRequestedVisibleTypes = i7;
        this.mPackageName = str;
        this.mTransientBarTypes = i8;
        this.mLetterboxDetails = letterboxDetailsArr;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedArrayMap(this.mIcons, i);
        parcel.writeInt(this.mDisabledFlags1);
        parcel.writeInt(this.mAppearance);
        parcel.writeParcelableArray(this.mAppearanceRegions, 0);
        parcel.writeInt(this.mImeWindowVis);
        parcel.writeInt(this.mImeBackDisposition);
        parcel.writeBoolean(this.mShowImeSwitcher);
        parcel.writeInt(this.mDisabledFlags2);
        parcel.writeStrongBinder(this.mImeToken);
        parcel.writeBoolean(this.mNavbarColorManagedByIme);
        parcel.writeInt(this.mBehavior);
        parcel.writeInt(this.mRequestedVisibleTypes);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mTransientBarTypes);
        parcel.writeParcelableArray(this.mLetterboxDetails, i);
    }
}
