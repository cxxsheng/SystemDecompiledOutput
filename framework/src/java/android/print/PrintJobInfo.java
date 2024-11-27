package android.print;

/* loaded from: classes3.dex */
public final class PrintJobInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.print.PrintJobInfo> CREATOR = new android.os.Parcelable.Creator<android.print.PrintJobInfo>() { // from class: android.print.PrintJobInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintJobInfo createFromParcel(android.os.Parcel parcel) {
            return new android.print.PrintJobInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.print.PrintJobInfo[] newArray(int i) {
            return new android.print.PrintJobInfo[i];
        }
    };
    public static final int STATE_ANY = -1;
    public static final int STATE_ANY_ACTIVE = -3;
    public static final int STATE_ANY_SCHEDULED = -4;
    public static final int STATE_ANY_VISIBLE_TO_CLIENTS = -2;
    public static final int STATE_BLOCKED = 4;
    public static final int STATE_CANCELED = 7;
    public static final int STATE_COMPLETED = 5;
    public static final int STATE_CREATED = 1;
    public static final int STATE_FAILED = 6;
    public static final int STATE_QUEUED = 2;
    public static final int STATE_STARTED = 3;
    private android.os.Bundle mAdvancedOptions;
    private int mAppId;
    private android.print.PrintAttributes mAttributes;
    private boolean mCanceling;
    private int mCopies;
    private long mCreationTime;
    private android.print.PrintDocumentInfo mDocumentInfo;
    private android.print.PrintJobId mId;
    private java.lang.String mLabel;
    private android.print.PageRange[] mPageRanges;
    private android.print.PrinterId mPrinterId;
    private java.lang.String mPrinterName;
    private float mProgress;
    private int mState;
    private java.lang.CharSequence mStatus;
    private int mStatusRes;
    private java.lang.CharSequence mStatusResAppPackageName;
    private java.lang.String mTag;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    public PrintJobInfo() {
        this.mProgress = -1.0f;
    }

    public PrintJobInfo(android.print.PrintJobInfo printJobInfo) {
        this.mId = printJobInfo.mId;
        this.mLabel = printJobInfo.mLabel;
        this.mPrinterId = printJobInfo.mPrinterId;
        this.mPrinterName = printJobInfo.mPrinterName;
        this.mState = printJobInfo.mState;
        this.mAppId = printJobInfo.mAppId;
        this.mTag = printJobInfo.mTag;
        this.mCreationTime = printJobInfo.mCreationTime;
        this.mCopies = printJobInfo.mCopies;
        this.mPageRanges = printJobInfo.mPageRanges;
        this.mAttributes = printJobInfo.mAttributes;
        this.mDocumentInfo = printJobInfo.mDocumentInfo;
        this.mProgress = printJobInfo.mProgress;
        this.mStatus = printJobInfo.mStatus;
        this.mStatusRes = printJobInfo.mStatusRes;
        this.mStatusResAppPackageName = printJobInfo.mStatusResAppPackageName;
        this.mCanceling = printJobInfo.mCanceling;
        this.mAdvancedOptions = printJobInfo.mAdvancedOptions;
    }

    private PrintJobInfo(android.os.Parcel parcel) {
        this.mId = (android.print.PrintJobId) parcel.readParcelable(null, android.print.PrintJobId.class);
        this.mLabel = parcel.readString();
        this.mPrinterId = (android.print.PrinterId) parcel.readParcelable(null, android.print.PrinterId.class);
        this.mPrinterName = parcel.readString();
        this.mState = parcel.readInt();
        this.mAppId = parcel.readInt();
        this.mTag = parcel.readString();
        this.mCreationTime = parcel.readLong();
        this.mCopies = parcel.readInt();
        android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(null, android.print.PageRange.class);
        if (parcelableArr != null) {
            this.mPageRanges = new android.print.PageRange[parcelableArr.length];
            for (int i = 0; i < parcelableArr.length; i++) {
                this.mPageRanges[i] = (android.print.PageRange) parcelableArr[i];
            }
        }
        this.mAttributes = (android.print.PrintAttributes) parcel.readParcelable(null, android.print.PrintAttributes.class);
        this.mDocumentInfo = (android.print.PrintDocumentInfo) parcel.readParcelable(null, android.print.PrintDocumentInfo.class);
        this.mProgress = parcel.readFloat();
        this.mStatus = parcel.readCharSequence();
        this.mStatusRes = parcel.readInt();
        this.mStatusResAppPackageName = parcel.readCharSequence();
        this.mCanceling = parcel.readInt() == 1;
        this.mAdvancedOptions = parcel.readBundle();
        if (this.mAdvancedOptions != null) {
            com.android.internal.util.Preconditions.checkArgument(!this.mAdvancedOptions.containsKey(null));
        }
    }

    public android.print.PrintJobId getId() {
        return this.mId;
    }

    public void setId(android.print.PrintJobId printJobId) {
        this.mId = printJobId;
    }

    public java.lang.String getLabel() {
        return this.mLabel;
    }

    public void setLabel(java.lang.String str) {
        this.mLabel = str;
    }

    public android.print.PrinterId getPrinterId() {
        return this.mPrinterId;
    }

    public void setPrinterId(android.print.PrinterId printerId) {
        this.mPrinterId = printerId;
    }

    public java.lang.String getPrinterName() {
        return this.mPrinterName;
    }

    public void setPrinterName(java.lang.String str) {
        this.mPrinterName = str;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setProgress(float f) {
        com.android.internal.util.Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, android.app.Notification.CATEGORY_PROGRESS);
        this.mProgress = f;
    }

    public void setStatus(java.lang.CharSequence charSequence) {
        this.mStatusRes = 0;
        this.mStatusResAppPackageName = null;
        this.mStatus = charSequence;
    }

    public void setStatus(int i, java.lang.CharSequence charSequence) {
        this.mStatus = null;
        this.mStatusRes = i;
        this.mStatusResAppPackageName = charSequence;
    }

    public int getAppId() {
        return this.mAppId;
    }

    public void setAppId(int i) {
        this.mAppId = i;
    }

    public java.lang.String getTag() {
        return this.mTag;
    }

    public void setTag(java.lang.String str) {
        this.mTag = str;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    public void setCreationTime(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("creationTime must be non-negative.");
        }
        this.mCreationTime = j;
    }

    public int getCopies() {
        return this.mCopies;
    }

    public void setCopies(int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("Copies must be more than one.");
        }
        this.mCopies = i;
    }

    public android.print.PageRange[] getPages() {
        return this.mPageRanges;
    }

    public void setPages(android.print.PageRange[] pageRangeArr) {
        this.mPageRanges = pageRangeArr;
    }

    public android.print.PrintAttributes getAttributes() {
        return this.mAttributes;
    }

    public void setAttributes(android.print.PrintAttributes printAttributes) {
        this.mAttributes = printAttributes;
    }

    public android.print.PrintDocumentInfo getDocumentInfo() {
        return this.mDocumentInfo;
    }

    public void setDocumentInfo(android.print.PrintDocumentInfo printDocumentInfo) {
        this.mDocumentInfo = printDocumentInfo;
    }

    public boolean isCancelling() {
        return this.mCanceling;
    }

    public void setCancelling(boolean z) {
        this.mCanceling = z;
    }

    public boolean shouldStayAwake() {
        return this.mCanceling || this.mState == 3 || this.mState == 2;
    }

    public boolean hasAdvancedOption(java.lang.String str) {
        return this.mAdvancedOptions != null && this.mAdvancedOptions.containsKey(str);
    }

    public java.lang.String getAdvancedStringOption(java.lang.String str) {
        if (this.mAdvancedOptions != null) {
            return this.mAdvancedOptions.getString(str);
        }
        return null;
    }

    public int getAdvancedIntOption(java.lang.String str) {
        if (this.mAdvancedOptions != null) {
            return this.mAdvancedOptions.getInt(str);
        }
        return 0;
    }

    public android.os.Bundle getAdvancedOptions() {
        return this.mAdvancedOptions;
    }

    public void setAdvancedOptions(android.os.Bundle bundle) {
        this.mAdvancedOptions = bundle;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mId, i);
        parcel.writeString(this.mLabel);
        parcel.writeParcelable(this.mPrinterId, i);
        parcel.writeString(this.mPrinterName);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mAppId);
        parcel.writeString(this.mTag);
        parcel.writeLong(this.mCreationTime);
        parcel.writeInt(this.mCopies);
        parcel.writeParcelableArray(this.mPageRanges, i);
        parcel.writeParcelable(this.mAttributes, i);
        parcel.writeParcelable(this.mDocumentInfo, 0);
        parcel.writeFloat(this.mProgress);
        parcel.writeCharSequence(this.mStatus);
        parcel.writeInt(this.mStatusRes);
        parcel.writeCharSequence(this.mStatusResAppPackageName);
        parcel.writeInt(this.mCanceling ? 1 : 0);
        parcel.writeBundle(this.mAdvancedOptions);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("PrintJobInfo{");
        sb.append("label: ").append(this.mLabel);
        sb.append(", id: ").append(this.mId);
        sb.append(", state: ").append(stateToString(this.mState));
        sb.append(", printer: " + this.mPrinterId);
        sb.append(", tag: ").append(this.mTag);
        sb.append(", creationTime: " + this.mCreationTime);
        sb.append(", copies: ").append(this.mCopies);
        sb.append(", attributes: " + (this.mAttributes != null ? this.mAttributes.toString() : null));
        sb.append(", documentInfo: " + (this.mDocumentInfo != null ? this.mDocumentInfo.toString() : null));
        sb.append(", cancelling: " + this.mCanceling);
        sb.append(", pages: " + (this.mPageRanges != null ? java.util.Arrays.toString(this.mPageRanges) : null));
        sb.append(", hasAdvancedOptions: " + (this.mAdvancedOptions != null));
        sb.append(", progress: " + this.mProgress);
        sb.append(", status: " + (this.mStatus != null ? this.mStatus.toString() : null));
        sb.append(", statusRes: " + this.mStatusRes);
        sb.append(", statusResAppPackageName: " + (this.mStatusResAppPackageName != null ? this.mStatusResAppPackageName.toString() : null));
        sb.append("}");
        return sb.toString();
    }

    public static java.lang.String stateToString(int i) {
        switch (i) {
            case 1:
                return "STATE_CREATED";
            case 2:
                return "STATE_QUEUED";
            case 3:
                return "STATE_STARTED";
            case 4:
                return "STATE_BLOCKED";
            case 5:
                return "STATE_COMPLETED";
            case 6:
                return "STATE_FAILED";
            case 7:
                return "STATE_CANCELED";
            default:
                return "STATE_UNKNOWN";
        }
    }

    public float getProgress() {
        return this.mProgress;
    }

    public java.lang.CharSequence getStatus(android.content.pm.PackageManager packageManager) {
        if (this.mStatusRes == 0) {
            return this.mStatus;
        }
        try {
            return packageManager.getResourcesForApplication(this.mStatusResAppPackageName.toString()).getString(this.mStatusRes);
        } catch (android.content.pm.PackageManager.NameNotFoundException | android.content.res.Resources.NotFoundException e) {
            return null;
        }
    }

    public static final class Builder {
        private final android.print.PrintJobInfo mPrototype;

        public Builder(android.print.PrintJobInfo printJobInfo) {
            android.print.PrintJobInfo printJobInfo2;
            if (printJobInfo != null) {
                printJobInfo2 = new android.print.PrintJobInfo(printJobInfo);
            } else {
                printJobInfo2 = new android.print.PrintJobInfo();
            }
            this.mPrototype = printJobInfo2;
        }

        public void setCopies(int i) {
            this.mPrototype.mCopies = i;
        }

        public void setAttributes(android.print.PrintAttributes printAttributes) {
            this.mPrototype.mAttributes = printAttributes;
        }

        public void setPages(android.print.PageRange[] pageRangeArr) {
            this.mPrototype.mPageRanges = pageRangeArr;
        }

        public void setProgress(float f) {
            com.android.internal.util.Preconditions.checkArgumentInRange(f, 0.0f, 1.0f, android.app.Notification.CATEGORY_PROGRESS);
            this.mPrototype.mProgress = f;
        }

        public void setStatus(java.lang.CharSequence charSequence) {
            this.mPrototype.mStatus = charSequence;
        }

        public void putAdvancedOption(java.lang.String str, java.lang.String str2) {
            com.android.internal.util.Preconditions.checkNotNull(str, "key cannot be null");
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new android.os.Bundle();
            }
            this.mPrototype.mAdvancedOptions.putString(str, str2);
        }

        public void putAdvancedOption(java.lang.String str, int i) {
            if (this.mPrototype.mAdvancedOptions == null) {
                this.mPrototype.mAdvancedOptions = new android.os.Bundle();
            }
            this.mPrototype.mAdvancedOptions.putInt(str, i);
        }

        public android.print.PrintJobInfo build() {
            return this.mPrototype;
        }
    }
}
