package android.service.chooser;

/* loaded from: classes3.dex */
public final class ChooserResult implements android.os.Parcelable {
    public static final int CHOOSER_RESULT_COPY = 1;
    public static final int CHOOSER_RESULT_EDIT = 2;
    public static final int CHOOSER_RESULT_SELECTED_COMPONENT = 0;
    public static final int CHOOSER_RESULT_UNKNOWN = -1;
    public static final android.os.Parcelable.Creator<android.service.chooser.ChooserResult> CREATOR = new android.os.Parcelable.Creator<android.service.chooser.ChooserResult>() { // from class: android.service.chooser.ChooserResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.chooser.ChooserResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.chooser.ChooserResult[] newArray(int i) {
            return new android.service.chooser.ChooserResult[0];
        }
    };
    public static final long SEND_CHOOSER_RESULT = 263474465;
    private final boolean mIsShortcut;
    private final android.content.ComponentName mSelectedComponent;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultType {
    }

    private ChooserResult(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mSelectedComponent = android.content.ComponentName.readFromParcel(parcel);
        this.mIsShortcut = parcel.readBoolean();
    }

    public ChooserResult(int i, android.content.ComponentName componentName, boolean z) {
        this.mType = i;
        this.mSelectedComponent = componentName;
        this.mIsShortcut = z;
    }

    public int getType() {
        return this.mType;
    }

    public android.content.ComponentName getSelectedComponent() {
        return this.mSelectedComponent;
    }

    public boolean isShortcut() {
        return this.mIsShortcut;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        android.content.ComponentName.writeToParcel(this.mSelectedComponent, parcel);
        parcel.writeBoolean(this.mIsShortcut);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.chooser.ChooserResult chooserResult = (android.service.chooser.ChooserResult) obj;
        if (this.mType == chooserResult.mType && this.mIsShortcut == chooserResult.mIsShortcut && java.util.Objects.equals(this.mSelectedComponent, chooserResult.mSelectedComponent)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), this.mSelectedComponent, java.lang.Boolean.valueOf(this.mIsShortcut));
    }
}
