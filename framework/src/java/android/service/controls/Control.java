package android.service.controls;

/* loaded from: classes3.dex */
public final class Control implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.controls.Control> CREATOR = new android.os.Parcelable.Creator<android.service.controls.Control>() { // from class: android.service.controls.Control.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.Control createFromParcel(android.os.Parcel parcel) {
            return new android.service.controls.Control(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.Control[] newArray(int i) {
            return new android.service.controls.Control[i];
        }
    };
    private static final int NUM_STATUS = 5;
    public static final int STATUS_DISABLED = 4;
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_NOT_FOUND = 2;
    public static final int STATUS_OK = 1;
    public static final int STATUS_UNKNOWN = 0;
    private static final java.lang.String TAG = "Control";
    private final android.app.PendingIntent mAppIntent;
    private final boolean mAuthRequired;
    private final java.lang.String mControlId;
    private final android.service.controls.templates.ControlTemplate mControlTemplate;
    private final android.content.res.ColorStateList mCustomColor;
    private final android.graphics.drawable.Icon mCustomIcon;
    private final int mDeviceType;
    private final int mStatus;
    private final java.lang.CharSequence mStatusText;
    private final java.lang.CharSequence mStructure;
    private final java.lang.CharSequence mSubtitle;
    private final java.lang.CharSequence mTitle;
    private final java.lang.CharSequence mZone;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    Control(java.lang.String str, int i, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, java.lang.CharSequence charSequence4, android.app.PendingIntent pendingIntent, android.graphics.drawable.Icon icon, android.content.res.ColorStateList colorStateList, int i2, android.service.controls.templates.ControlTemplate controlTemplate, java.lang.CharSequence charSequence5, boolean z) {
        com.android.internal.util.Preconditions.checkNotNull(str);
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        com.android.internal.util.Preconditions.checkNotNull(charSequence2);
        com.android.internal.util.Preconditions.checkNotNull(pendingIntent);
        com.android.internal.util.Preconditions.checkNotNull(controlTemplate);
        com.android.internal.util.Preconditions.checkNotNull(charSequence5);
        this.mControlId = str;
        if (!android.service.controls.DeviceTypes.validDeviceType(i)) {
            android.util.Log.e(TAG, "Invalid device type:" + i);
            this.mDeviceType = 0;
        } else {
            this.mDeviceType = i;
        }
        this.mTitle = charSequence;
        this.mSubtitle = charSequence2;
        this.mStructure = charSequence3;
        this.mZone = charSequence4;
        this.mAppIntent = pendingIntent;
        this.mCustomColor = colorStateList;
        this.mCustomIcon = icon;
        if (i2 < 0 || i2 >= 5) {
            this.mStatus = 0;
            android.util.Log.e(TAG, "Status unknown:" + i2);
        } else {
            this.mStatus = i2;
        }
        this.mControlTemplate = controlTemplate;
        this.mStatusText = charSequence5;
        this.mAuthRequired = z;
    }

    Control(android.os.Parcel parcel) {
        this.mControlId = parcel.readString();
        this.mDeviceType = parcel.readInt();
        this.mTitle = parcel.readCharSequence();
        this.mSubtitle = parcel.readCharSequence();
        if (parcel.readByte() == 1) {
            this.mStructure = parcel.readCharSequence();
        } else {
            this.mStructure = null;
        }
        if (parcel.readByte() == 1) {
            this.mZone = parcel.readCharSequence();
        } else {
            this.mZone = null;
        }
        this.mAppIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        if (parcel.readByte() == 1) {
            this.mCustomIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mCustomIcon = null;
        }
        if (parcel.readByte() == 1) {
            this.mCustomColor = android.content.res.ColorStateList.CREATOR.createFromParcel(parcel);
        } else {
            this.mCustomColor = null;
        }
        this.mStatus = parcel.readInt();
        this.mControlTemplate = android.service.controls.templates.ControlTemplateWrapper.CREATOR.createFromParcel(parcel).getWrappedTemplate();
        this.mStatusText = parcel.readCharSequence();
        this.mAuthRequired = parcel.readBoolean();
    }

    public java.lang.String getControlId() {
        return this.mControlId;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public java.lang.CharSequence getTitle() {
        return this.mTitle;
    }

    public java.lang.CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public java.lang.CharSequence getStructure() {
        return this.mStructure;
    }

    public java.lang.CharSequence getZone() {
        return this.mZone;
    }

    public android.app.PendingIntent getAppIntent() {
        return this.mAppIntent;
    }

    public android.graphics.drawable.Icon getCustomIcon() {
        return this.mCustomIcon;
    }

    public android.content.res.ColorStateList getCustomColor() {
        return this.mCustomColor;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public android.service.controls.templates.ControlTemplate getControlTemplate() {
        return this.mControlTemplate;
    }

    public java.lang.CharSequence getStatusText() {
        return this.mStatusText;
    }

    public boolean isAuthRequired() {
        return this.mAuthRequired;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mControlId);
        parcel.writeInt(this.mDeviceType);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeCharSequence(this.mSubtitle);
        if (this.mStructure != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mStructure);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mZone != null) {
            parcel.writeByte((byte) 1);
            parcel.writeCharSequence(this.mZone);
        } else {
            parcel.writeByte((byte) 0);
        }
        this.mAppIntent.writeToParcel(parcel, i);
        if (this.mCustomIcon != null) {
            parcel.writeByte((byte) 1);
            this.mCustomIcon.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mCustomColor != null) {
            parcel.writeByte((byte) 1);
            this.mCustomColor.writeToParcel(parcel, i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mStatus);
        new android.service.controls.templates.ControlTemplateWrapper(this.mControlTemplate).writeToParcel(parcel, i);
        parcel.writeCharSequence(this.mStatusText);
        parcel.writeBoolean(this.mAuthRequired);
    }

    public static final class StatelessBuilder {
        private static final java.lang.String TAG = "StatelessBuilder";
        private android.app.PendingIntent mAppIntent;
        private java.lang.String mControlId;
        private android.content.res.ColorStateList mCustomColor;
        private android.graphics.drawable.Icon mCustomIcon;
        private int mDeviceType;
        private java.lang.CharSequence mStructure;
        private java.lang.CharSequence mSubtitle;
        private java.lang.CharSequence mTitle;
        private java.lang.CharSequence mZone;

        public StatelessBuilder(java.lang.String str, android.app.PendingIntent pendingIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            com.android.internal.util.Preconditions.checkNotNull(str);
            com.android.internal.util.Preconditions.checkNotNull(pendingIntent);
            this.mControlId = str;
            this.mAppIntent = pendingIntent;
        }

        public StatelessBuilder(android.service.controls.Control control) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            com.android.internal.util.Preconditions.checkNotNull(control);
            this.mControlId = control.mControlId;
            this.mDeviceType = control.mDeviceType;
            this.mTitle = control.mTitle;
            this.mSubtitle = control.mSubtitle;
            this.mStructure = control.mStructure;
            this.mZone = control.mZone;
            this.mAppIntent = control.mAppIntent;
            this.mCustomIcon = control.mCustomIcon;
            this.mCustomColor = control.mCustomColor;
        }

        public android.service.controls.Control.StatelessBuilder setControlId(java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mControlId = str;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setDeviceType(int i) {
            if (!android.service.controls.DeviceTypes.validDeviceType(i)) {
                android.util.Log.e(TAG, "Invalid device type:" + i);
                this.mDeviceType = 0;
            } else {
                this.mDeviceType = i;
            }
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setTitle(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mTitle = charSequence;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setSubtitle(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mSubtitle = charSequence;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setStructure(java.lang.CharSequence charSequence) {
            this.mStructure = charSequence;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setZone(java.lang.CharSequence charSequence) {
            this.mZone = charSequence;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setAppIntent(android.app.PendingIntent pendingIntent) {
            com.android.internal.util.Preconditions.checkNotNull(pendingIntent);
            this.mAppIntent = pendingIntent;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setCustomIcon(android.graphics.drawable.Icon icon) {
            this.mCustomIcon = icon;
            return this;
        }

        public android.service.controls.Control.StatelessBuilder setCustomColor(android.content.res.ColorStateList colorStateList) {
            this.mCustomColor = colorStateList;
            return this;
        }

        public android.service.controls.Control build() {
            return new android.service.controls.Control(this.mControlId, this.mDeviceType, this.mTitle, this.mSubtitle, this.mStructure, this.mZone, this.mAppIntent, this.mCustomIcon, this.mCustomColor, 0, android.service.controls.templates.ControlTemplate.NO_TEMPLATE, "", true);
        }
    }

    public static final class StatefulBuilder {
        private static final java.lang.String TAG = "StatefulBuilder";
        private android.app.PendingIntent mAppIntent;
        private boolean mAuthRequired;
        private java.lang.String mControlId;
        private android.service.controls.templates.ControlTemplate mControlTemplate;
        private android.content.res.ColorStateList mCustomColor;
        private android.graphics.drawable.Icon mCustomIcon;
        private int mDeviceType;
        private int mStatus;
        private java.lang.CharSequence mStatusText;
        private java.lang.CharSequence mStructure;
        private java.lang.CharSequence mSubtitle;
        private java.lang.CharSequence mTitle;
        private java.lang.CharSequence mZone;

        public StatefulBuilder(java.lang.String str, android.app.PendingIntent pendingIntent) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            this.mStatus = 0;
            this.mControlTemplate = android.service.controls.templates.ControlTemplate.NO_TEMPLATE;
            this.mStatusText = "";
            this.mAuthRequired = true;
            com.android.internal.util.Preconditions.checkNotNull(str);
            com.android.internal.util.Preconditions.checkNotNull(pendingIntent);
            this.mControlId = str;
            this.mAppIntent = pendingIntent;
        }

        public StatefulBuilder(android.service.controls.Control control) {
            this.mDeviceType = 0;
            this.mTitle = "";
            this.mSubtitle = "";
            this.mStatus = 0;
            this.mControlTemplate = android.service.controls.templates.ControlTemplate.NO_TEMPLATE;
            this.mStatusText = "";
            this.mAuthRequired = true;
            com.android.internal.util.Preconditions.checkNotNull(control);
            this.mControlId = control.mControlId;
            this.mDeviceType = control.mDeviceType;
            this.mTitle = control.mTitle;
            this.mSubtitle = control.mSubtitle;
            this.mStructure = control.mStructure;
            this.mZone = control.mZone;
            this.mAppIntent = control.mAppIntent;
            this.mCustomIcon = control.mCustomIcon;
            this.mCustomColor = control.mCustomColor;
            this.mStatus = control.mStatus;
            this.mControlTemplate = control.mControlTemplate;
            this.mStatusText = control.mStatusText;
            this.mAuthRequired = control.mAuthRequired;
        }

        public android.service.controls.Control.StatefulBuilder setControlId(java.lang.String str) {
            com.android.internal.util.Preconditions.checkNotNull(str);
            this.mControlId = str;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setDeviceType(int i) {
            if (!android.service.controls.DeviceTypes.validDeviceType(i)) {
                android.util.Log.e(TAG, "Invalid device type:" + i);
                this.mDeviceType = 0;
            } else {
                this.mDeviceType = i;
            }
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setTitle(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mTitle = charSequence;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setSubtitle(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mSubtitle = charSequence;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setStructure(java.lang.CharSequence charSequence) {
            this.mStructure = charSequence;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setZone(java.lang.CharSequence charSequence) {
            this.mZone = charSequence;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setAppIntent(android.app.PendingIntent pendingIntent) {
            com.android.internal.util.Preconditions.checkNotNull(pendingIntent);
            this.mAppIntent = pendingIntent;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setCustomIcon(android.graphics.drawable.Icon icon) {
            this.mCustomIcon = icon;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setCustomColor(android.content.res.ColorStateList colorStateList) {
            this.mCustomColor = colorStateList;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setStatus(int i) {
            if (i < 0 || i >= 5) {
                this.mStatus = 0;
                android.util.Log.e(TAG, "Status unknown:" + i);
            } else {
                this.mStatus = i;
            }
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setControlTemplate(android.service.controls.templates.ControlTemplate controlTemplate) {
            com.android.internal.util.Preconditions.checkNotNull(controlTemplate);
            this.mControlTemplate = controlTemplate;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setStatusText(java.lang.CharSequence charSequence) {
            com.android.internal.util.Preconditions.checkNotNull(charSequence);
            this.mStatusText = charSequence;
            return this;
        }

        public android.service.controls.Control.StatefulBuilder setAuthRequired(boolean z) {
            this.mAuthRequired = z;
            return this;
        }

        public android.service.controls.Control build() {
            return new android.service.controls.Control(this.mControlId, this.mDeviceType, this.mTitle, this.mSubtitle, this.mStructure, this.mZone, this.mAppIntent, this.mCustomIcon, this.mCustomColor, this.mStatus, this.mControlTemplate, this.mStatusText, this.mAuthRequired);
        }
    }
}
