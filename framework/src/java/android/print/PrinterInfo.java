package android.print;

/* loaded from: classes3.dex */
public final class PrinterInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.print.PrinterInfo> CREATOR = new android.os.Parcelable.Creator<android.print.PrinterInfo>() { // from class: android.print.PrinterInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterInfo createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrinterInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrinterInfo[] newArray(int i) {
            return new android.print.PrinterInfo[i];
        }
    };
    public static final int STATUS_BUSY = 2;
    public static final int STATUS_IDLE = 1;
    public static final int STATUS_UNAVAILABLE = 3;
    private final android.print.PrinterCapabilitiesInfo mCapabilities;
    private final int mCustomPrinterIconGen;
    private final java.lang.String mDescription;
    private final boolean mHasCustomPrinterIcon;
    private final int mIconResourceId;
    private final android.print.PrinterId mId;
    private final android.app.PendingIntent mInfoIntent;
    private final java.lang.String mName;
    private final int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private PrinterInfo(android.print.PrinterId printerId, java.lang.String str, int i, int i2, boolean z, java.lang.String str2, android.app.PendingIntent pendingIntent, android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo, int i3) {
        this.mId = printerId;
        this.mName = str;
        this.mStatus = i;
        this.mIconResourceId = i2;
        this.mHasCustomPrinterIcon = z;
        this.mDescription = str2;
        this.mInfoIntent = pendingIntent;
        this.mCapabilities = printerCapabilitiesInfo;
        this.mCustomPrinterIconGen = i3;
    }

    public android.print.PrinterId getId() {
        return this.mId;
    }

    public android.graphics.drawable.Drawable loadIcon(android.content.Context context) {
        android.graphics.drawable.Drawable drawable;
        android.graphics.drawable.Icon customPrinterIcon;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        if (this.mHasCustomPrinterIcon && (customPrinterIcon = ((android.print.PrintManager) context.getSystemService(android.content.Context.PRINT_SERVICE)).getCustomPrinterIcon(this.mId)) != null) {
            drawable = customPrinterIcon.loadDrawable(context);
        } else {
            drawable = null;
        }
        if (drawable == null) {
            try {
                java.lang.String packageName = this.mId.getServiceName().getPackageName();
                android.content.pm.ApplicationInfo applicationInfo = packageManager.getPackageInfo(packageName, 0).applicationInfo;
                if (this.mIconResourceId != 0) {
                    drawable = packageManager.getDrawable(packageName, this.mIconResourceId, applicationInfo);
                }
                if (drawable == null) {
                    return applicationInfo.loadIcon(packageManager);
                }
                return drawable;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return drawable;
            }
        }
        return drawable;
    }

    public boolean getHasCustomPrinterIcon() {
        return this.mHasCustomPrinterIcon;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public android.app.PendingIntent getInfoIntent() {
        return this.mInfoIntent;
    }

    public android.print.PrinterCapabilitiesInfo getCapabilities() {
        return this.mCapabilities;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.print.PrinterId checkPrinterId(android.print.PrinterId printerId) {
        return (android.print.PrinterId) com.android.internal.util.Preconditions.checkNotNull(printerId, "printerId cannot be null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int checkStatus(int i) {
        if (i != 1 && i != 2 && i != 3) {
            throw new java.lang.IllegalArgumentException("status is invalid.");
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String checkName(java.lang.String str) {
        return (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "name cannot be empty.");
    }

    private PrinterInfo(android.os.Parcel parcel) {
        this.mId = checkPrinterId((android.print.PrinterId) parcel.readParcelable(null, android.print.PrinterId.class));
        this.mName = checkName(parcel.readString());
        this.mStatus = checkStatus(parcel.readInt());
        this.mDescription = parcel.readString();
        this.mCapabilities = (android.print.PrinterCapabilitiesInfo) parcel.readParcelable(null, android.print.PrinterCapabilitiesInfo.class);
        this.mIconResourceId = parcel.readInt();
        this.mHasCustomPrinterIcon = parcel.readByte() != 0;
        this.mCustomPrinterIconGen = parcel.readInt();
        this.mInfoIntent = (android.app.PendingIntent) parcel.readParcelable(null, android.app.PendingIntent.class);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mStatus);
        parcel.writeString(this.mDescription);
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeInt(this.mIconResourceId);
        parcel.writeByte(this.mHasCustomPrinterIcon ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mCustomPrinterIconGen);
        parcel.writeParcelable(this.mInfoIntent, i);
    }

    public int hashCode() {
        return ((((((((((((((((this.mId.hashCode() + 31) * 31) + this.mName.hashCode()) * 31) + this.mStatus) * 31) + (this.mDescription != null ? this.mDescription.hashCode() : 0)) * 31) + (this.mCapabilities != null ? this.mCapabilities.hashCode() : 0)) * 31) + this.mIconResourceId) * 31) + (this.mHasCustomPrinterIcon ? 1 : 0)) * 31) + this.mCustomPrinterIconGen) * 31) + (this.mInfoIntent != null ? this.mInfoIntent.hashCode() : 0);
    }

    public boolean equalsIgnoringStatus(android.print.PrinterInfo printerInfo) {
        if (!this.mId.equals(printerInfo.mId) || !this.mName.equals(printerInfo.mName) || !android.text.TextUtils.equals(this.mDescription, printerInfo.mDescription)) {
            return false;
        }
        if (this.mCapabilities == null) {
            if (printerInfo.mCapabilities != null) {
                return false;
            }
        } else if (!this.mCapabilities.equals(printerInfo.mCapabilities)) {
            return false;
        }
        if (this.mIconResourceId == printerInfo.mIconResourceId && this.mHasCustomPrinterIcon == printerInfo.mHasCustomPrinterIcon && this.mCustomPrinterIconGen == printerInfo.mCustomPrinterIconGen) {
            return this.mInfoIntent == null ? printerInfo.mInfoIntent == null : this.mInfoIntent.equals(printerInfo.mInfoIntent);
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.print.PrinterInfo printerInfo = (android.print.PrinterInfo) obj;
        if (equalsIgnoringStatus(printerInfo) && this.mStatus == printerInfo.mStatus) {
            return true;
        }
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrinterInfo{");
        sb.append("id=").append(this.mId);
        sb.append(", name=").append(this.mName);
        sb.append(", status=").append(this.mStatus);
        sb.append(", description=").append(this.mDescription);
        sb.append(", capabilities=").append(this.mCapabilities);
        sb.append(", iconResId=").append(this.mIconResourceId);
        sb.append(", hasCustomPrinterIcon=").append(this.mHasCustomPrinterIcon);
        sb.append(", customPrinterIconGen=").append(this.mCustomPrinterIconGen);
        sb.append(", infoIntent=").append(this.mInfoIntent);
        sb.append("\"}");
        return sb.toString();
    }

    public static final class Builder {
        private android.print.PrinterCapabilitiesInfo mCapabilities;
        private int mCustomPrinterIconGen;
        private java.lang.String mDescription;
        private boolean mHasCustomPrinterIcon;
        private int mIconResourceId;
        private android.app.PendingIntent mInfoIntent;
        private java.lang.String mName;
        private android.print.PrinterId mPrinterId;
        private int mStatus;

        public Builder(android.print.PrinterId printerId, java.lang.String str, int i) {
            this.mPrinterId = android.print.PrinterInfo.checkPrinterId(printerId);
            this.mName = android.print.PrinterInfo.checkName(str);
            this.mStatus = android.print.PrinterInfo.checkStatus(i);
        }

        public Builder(android.print.PrinterInfo printerInfo) {
            this.mPrinterId = printerInfo.mId;
            this.mName = printerInfo.mName;
            this.mStatus = printerInfo.mStatus;
            this.mIconResourceId = printerInfo.mIconResourceId;
            this.mHasCustomPrinterIcon = printerInfo.mHasCustomPrinterIcon;
            this.mDescription = printerInfo.mDescription;
            this.mInfoIntent = printerInfo.mInfoIntent;
            this.mCapabilities = printerInfo.mCapabilities;
            this.mCustomPrinterIconGen = printerInfo.mCustomPrinterIconGen;
        }

        public android.print.PrinterInfo.Builder setStatus(int i) {
            this.mStatus = android.print.PrinterInfo.checkStatus(i);
            return this;
        }

        public android.print.PrinterInfo.Builder setIconResourceId(int i) {
            this.mIconResourceId = com.android.internal.util.Preconditions.checkArgumentNonnegative(i, "iconResourceId can't be negative");
            return this;
        }

        public android.print.PrinterInfo.Builder setHasCustomPrinterIcon(boolean z) {
            this.mHasCustomPrinterIcon = z;
            return this;
        }

        public android.print.PrinterInfo.Builder setName(java.lang.String str) {
            this.mName = android.print.PrinterInfo.checkName(str);
            return this;
        }

        public android.print.PrinterInfo.Builder setDescription(java.lang.String str) {
            this.mDescription = str;
            return this;
        }

        public android.print.PrinterInfo.Builder setInfoIntent(android.app.PendingIntent pendingIntent) {
            this.mInfoIntent = pendingIntent;
            return this;
        }

        public android.print.PrinterInfo.Builder setCapabilities(android.print.PrinterCapabilitiesInfo printerCapabilitiesInfo) {
            this.mCapabilities = printerCapabilitiesInfo;
            return this;
        }

        public android.print.PrinterInfo build() {
            return new android.print.PrinterInfo(this.mPrinterId, this.mName, this.mStatus, this.mIconResourceId, this.mHasCustomPrinterIcon, this.mDescription, this.mInfoIntent, this.mCapabilities, this.mCustomPrinterIconGen);
        }

        public android.print.PrinterInfo.Builder incCustomPrinterIconGen() {
            this.mCustomPrinterIconGen++;
            return this;
        }
    }
}
