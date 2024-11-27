package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public class StatusBarIcon implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.internal.statusbar.StatusBarIcon> CREATOR = new android.os.Parcelable.Creator<com.android.internal.statusbar.StatusBarIcon>() { // from class: com.android.internal.statusbar.StatusBarIcon.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.StatusBarIcon createFromParcel(android.os.Parcel parcel) {
            return new com.android.internal.statusbar.StatusBarIcon(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.internal.statusbar.StatusBarIcon[] newArray(int i) {
            return new com.android.internal.statusbar.StatusBarIcon[i];
        }
    };
    public java.lang.CharSequence contentDescription;
    public android.graphics.drawable.Icon icon;
    public int iconLevel;
    public int number;
    public java.lang.String pkg;
    public android.os.UserHandle user;
    public boolean visible;

    public StatusBarIcon(android.os.UserHandle userHandle, java.lang.String str, android.graphics.drawable.Icon icon, int i, int i2, java.lang.CharSequence charSequence) {
        this.visible = true;
        if (icon.getType() == 2 && android.text.TextUtils.isEmpty(icon.getResPackage())) {
            icon = android.graphics.drawable.Icon.createWithResource(str, icon.getResId());
        }
        this.pkg = str;
        this.user = userHandle;
        this.icon = icon;
        this.iconLevel = i;
        this.number = i2;
        this.contentDescription = charSequence;
    }

    public StatusBarIcon(java.lang.String str, android.os.UserHandle userHandle, int i, int i2, int i3, java.lang.CharSequence charSequence) {
        this(userHandle, str, android.graphics.drawable.Icon.createWithResource(str, i), i2, i3, charSequence);
    }

    public java.lang.String toString() {
        return "StatusBarIcon(icon=" + this.icon + (this.iconLevel != 0 ? " level=" + this.iconLevel : "") + (this.visible ? " visible" : "") + " user=" + this.user.getIdentifier() + (this.number != 0 ? " num=" + this.number : "") + " )";
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public com.android.internal.statusbar.StatusBarIcon m7031clone() {
        com.android.internal.statusbar.StatusBarIcon statusBarIcon = new com.android.internal.statusbar.StatusBarIcon(this.user, this.pkg, this.icon, this.iconLevel, this.number, this.contentDescription);
        statusBarIcon.visible = this.visible;
        return statusBarIcon;
    }

    public StatusBarIcon(android.os.Parcel parcel) {
        this.visible = true;
        readFromParcel(parcel);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.icon = (android.graphics.drawable.Icon) parcel.readParcelable(null, android.graphics.drawable.Icon.class);
        this.pkg = parcel.readString();
        this.user = (android.os.UserHandle) parcel.readParcelable(null, android.os.UserHandle.class);
        this.iconLevel = parcel.readInt();
        this.visible = parcel.readInt() != 0;
        this.number = parcel.readInt();
        this.contentDescription = parcel.readCharSequence();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.icon, 0);
        parcel.writeString(this.pkg);
        parcel.writeParcelable(this.user, 0);
        parcel.writeInt(this.iconLevel);
        parcel.writeInt(this.visible ? 1 : 0);
        parcel.writeInt(this.number);
        parcel.writeCharSequence(this.contentDescription);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
