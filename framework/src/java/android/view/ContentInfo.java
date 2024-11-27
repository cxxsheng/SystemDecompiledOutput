package android.view;

/* loaded from: classes4.dex */
public final class ContentInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.ContentInfo> CREATOR = new android.os.Parcelable.Creator<android.view.ContentInfo>() { // from class: android.view.ContentInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ContentInfo createFromParcel(android.os.Parcel parcel) {
            android.view.inputmethod.InputContentInfo inputContentInfo;
            android.content.ClipData createFromParcel = android.content.ClipData.CREATOR.createFromParcel(parcel);
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.net.Uri createFromParcel2 = android.net.Uri.CREATOR.createFromParcel(parcel);
            android.os.Bundle readBundle = parcel.readBundle();
            if (parcel.readInt() == 0) {
                inputContentInfo = null;
            } else {
                inputContentInfo = android.view.inputmethod.InputContentInfo.CREATOR.createFromParcel(parcel);
            }
            return new android.view.ContentInfo.Builder(createFromParcel, readInt).setFlags(readInt2).setLinkUri(createFromParcel2).setExtras(readBundle).setInputContentInfo(inputContentInfo).setDragAndDropPermissions(parcel.readInt() != 0 ? android.view.DragAndDropPermissions.CREATOR.createFromParcel(parcel) : null).build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.ContentInfo[] newArray(int i) {
            return new android.view.ContentInfo[i];
        }
    };
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_AUTOFILL = 4;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    public static final int SOURCE_PROCESS_TEXT = 5;
    private final android.content.ClipData mClip;
    private final android.view.DragAndDropPermissions mDragAndDropPermissions;
    private final android.os.Bundle mExtras;
    private final int mFlags;
    private final android.view.inputmethod.InputContentInfo mInputContentInfo;
    private final android.net.Uri mLinkUri;
    private final int mSource;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    static java.lang.String sourceToString(int i) {
        switch (i) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            case 4:
                return "SOURCE_AUTOFILL";
            case 5:
                return "SOURCE_PROCESS_TEXT";
            default:
                return java.lang.String.valueOf(i);
        }
    }

    static java.lang.String flagsToString(int i) {
        if ((i & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return java.lang.String.valueOf(i);
    }

    private ContentInfo(android.view.ContentInfo.Builder builder) {
        this.mClip = (android.content.ClipData) java.util.Objects.requireNonNull(builder.mClip);
        this.mSource = com.android.internal.util.Preconditions.checkArgumentInRange(builder.mSource, 0, 5, android.app.slice.Slice.SUBTYPE_SOURCE);
        this.mFlags = com.android.internal.util.Preconditions.checkFlagsArgument(builder.mFlags, 1);
        this.mLinkUri = builder.mLinkUri;
        this.mExtras = builder.mExtras;
        this.mInputContentInfo = builder.mInputContentInfo;
        this.mDragAndDropPermissions = builder.mDragAndDropPermissions;
    }

    public void releasePermissions() {
        if (this.mInputContentInfo != null) {
            this.mInputContentInfo.releasePermission();
        }
        if (this.mDragAndDropPermissions != null) {
            this.mDragAndDropPermissions.release();
        }
    }

    public java.lang.String toString() {
        return "ContentInfo{clip=" + this.mClip + ", source=" + sourceToString(this.mSource) + ", flags=" + flagsToString(this.mFlags) + ", linkUri=" + this.mLinkUri + ", extras=" + this.mExtras + "}";
    }

    public android.content.ClipData getClip() {
        return this.mClip;
    }

    public int getSource() {
        return this.mSource;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public android.net.Uri getLinkUri() {
        return this.mLinkUri;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public android.util.Pair<android.view.ContentInfo, android.view.ContentInfo> partition(java.util.function.Predicate<android.content.ClipData.Item> predicate) {
        if (this.mClip.getItemCount() == 1) {
            boolean test = predicate.test(this.mClip.getItemAt(0));
            return android.util.Pair.create(test ? this : null, test ? null : this);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i = 0; i < this.mClip.getItemCount(); i++) {
            android.content.ClipData.Item itemAt = this.mClip.getItemAt(i);
            if (predicate.test(itemAt)) {
                arrayList.add(itemAt);
            } else {
                arrayList2.add(itemAt);
            }
        }
        if (arrayList.isEmpty()) {
            return android.util.Pair.create(null, this);
        }
        if (arrayList2.isEmpty()) {
            return android.util.Pair.create(this, null);
        }
        return android.util.Pair.create(new android.view.ContentInfo.Builder(this).setClip(new android.content.ClipData(new android.content.ClipDescription(this.mClip.getDescription()), (java.util.ArrayList<android.content.ClipData.Item>) arrayList)).build(), new android.view.ContentInfo.Builder(this).setClip(new android.content.ClipData(new android.content.ClipDescription(this.mClip.getDescription()), (java.util.ArrayList<android.content.ClipData.Item>) arrayList2)).build());
    }

    public static final class Builder {
        private android.content.ClipData mClip;
        private android.view.DragAndDropPermissions mDragAndDropPermissions;
        private android.os.Bundle mExtras;
        private int mFlags;
        private android.view.inputmethod.InputContentInfo mInputContentInfo;
        private android.net.Uri mLinkUri;
        private int mSource;

        public Builder(android.view.ContentInfo contentInfo) {
            this.mClip = contentInfo.mClip;
            this.mSource = contentInfo.mSource;
            this.mFlags = contentInfo.mFlags;
            this.mLinkUri = contentInfo.mLinkUri;
            this.mExtras = contentInfo.mExtras;
            this.mInputContentInfo = contentInfo.mInputContentInfo;
            this.mDragAndDropPermissions = contentInfo.mDragAndDropPermissions;
        }

        public Builder(android.content.ClipData clipData, int i) {
            this.mClip = clipData;
            this.mSource = i;
        }

        public android.view.ContentInfo.Builder setClip(android.content.ClipData clipData) {
            this.mClip = clipData;
            return this;
        }

        public android.view.ContentInfo.Builder setSource(int i) {
            this.mSource = i;
            return this;
        }

        public android.view.ContentInfo.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.view.ContentInfo.Builder setLinkUri(android.net.Uri uri) {
            this.mLinkUri = uri;
            return this;
        }

        public android.view.ContentInfo.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.view.ContentInfo.Builder setInputContentInfo(android.view.inputmethod.InputContentInfo inputContentInfo) {
            this.mInputContentInfo = inputContentInfo;
            return this;
        }

        public android.view.ContentInfo.Builder setDragAndDropPermissions(android.view.DragAndDropPermissions dragAndDropPermissions) {
            this.mDragAndDropPermissions = dragAndDropPermissions;
            return this;
        }

        public android.view.ContentInfo build() {
            return new android.view.ContentInfo(this);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mClip.writeToParcel(parcel, i);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        android.net.Uri.writeToParcel(parcel, this.mLinkUri);
        parcel.writeBundle(this.mExtras);
        if (this.mInputContentInfo == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mInputContentInfo.writeToParcel(parcel, i);
        }
        if (this.mDragAndDropPermissions == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mDragAndDropPermissions.writeToParcel(parcel, i);
        }
    }
}
