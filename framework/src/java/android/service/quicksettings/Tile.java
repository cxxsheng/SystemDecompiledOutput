package android.service.quicksettings;

/* loaded from: classes3.dex */
public final class Tile implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quicksettings.Tile> CREATOR = new android.os.Parcelable.Creator<android.service.quicksettings.Tile>() { // from class: android.service.quicksettings.Tile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quicksettings.Tile createFromParcel(android.os.Parcel parcel) {
            return new android.service.quicksettings.Tile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quicksettings.Tile[] newArray(int i) {
            return new android.service.quicksettings.Tile[i];
        }
    };
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_UNAVAILABLE = 0;
    private static final java.lang.String TAG = "Tile";
    private java.lang.CharSequence mContentDescription;
    private java.lang.CharSequence mDefaultLabel;
    private android.graphics.drawable.Icon mIcon;
    private java.lang.CharSequence mLabel;
    private android.app.PendingIntent mPendingIntent;
    private android.service.quicksettings.IQSService mService;
    private int mState = 1;
    private java.lang.CharSequence mStateDescription;
    private java.lang.CharSequence mSubtitle;
    private android.os.IBinder mToken;

    public Tile(android.os.Parcel parcel) {
        readFromParcel(parcel);
    }

    public Tile() {
    }

    public void setService(android.service.quicksettings.IQSService iQSService, android.os.IBinder iBinder) {
        this.mService = iQSService;
        this.mToken = iBinder;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public void setIcon(android.graphics.drawable.Icon icon) {
        this.mIcon = icon;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel != null ? this.mLabel : this.mDefaultLabel;
    }

    public java.lang.CharSequence getCustomLabel() {
        return this.mLabel;
    }

    public void setDefaultLabel(java.lang.CharSequence charSequence) {
        this.mDefaultLabel = charSequence;
    }

    public void setLabel(java.lang.CharSequence charSequence) {
        this.mLabel = charSequence;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setSubtitle(java.lang.CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public java.lang.CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public void setContentDescription(java.lang.CharSequence charSequence) {
        this.mContentDescription = charSequence;
    }

    public void setStateDescription(java.lang.CharSequence charSequence) {
        this.mStateDescription = charSequence;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void updateTile() {
        try {
            this.mService.updateQsTile(this, this.mToken);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't update tile");
        }
    }

    public android.app.PendingIntent getActivityLaunchForClick() {
        return this.mPendingIntent;
    }

    public void setActivityLaunchForClick(android.app.PendingIntent pendingIntent) {
        if (pendingIntent != null && !pendingIntent.isActivity()) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mPendingIntent = pendingIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mIcon != null) {
            parcel.writeByte((byte) 1);
            this.mIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mPendingIntent != null) {
            parcel.writeByte((byte) 1);
            this.mPendingIntent.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mState);
        android.text.TextUtils.writeToParcel(this.mLabel, parcel, i);
        android.text.TextUtils.writeToParcel(this.mDefaultLabel, parcel, i);
        android.text.TextUtils.writeToParcel(this.mSubtitle, parcel, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        android.text.TextUtils.writeToParcel(this.mStateDescription, parcel, i);
    }

    private void readFromParcel(android.os.Parcel parcel) {
        if (parcel.readByte() != 0) {
            this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        if (parcel.readByte() != 0) {
            this.mPendingIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        } else {
            this.mPendingIntent = null;
        }
        this.mState = parcel.readInt();
        this.mLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mDefaultLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mStateDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }
}
