package android.view.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class ViewNode extends android.app.assist.AssistStructure.ViewNode {
    private static final long FLAGS_ACCESSIBILITY_FOCUSED = 131072;
    private static final long FLAGS_ACTIVATED = 2097152;
    private static final long FLAGS_ASSIST_BLOCKED = 1024;
    private static final long FLAGS_CHECKABLE = 262144;
    private static final long FLAGS_CHECKED = 524288;
    private static final long FLAGS_CLICKABLE = 4096;
    private static final long FLAGS_CONTEXT_CLICKABLE = 16384;
    private static final long FLAGS_DISABLED = 2048;
    private static final long FLAGS_FOCUSABLE = 32768;
    private static final long FLAGS_FOCUSED = 65536;
    private static final long FLAGS_HAS_AUTOFILL_HINTS = 8589934592L;
    private static final long FLAGS_HAS_AUTOFILL_ID = 32;
    private static final long FLAGS_HAS_AUTOFILL_OPTIONS = 17179869184L;
    private static final long FLAGS_HAS_AUTOFILL_PARENT_ID = 64;
    private static final long FLAGS_HAS_AUTOFILL_TYPE = 2147483648L;
    private static final long FLAGS_HAS_AUTOFILL_VALUE = 4294967296L;
    private static final long FLAGS_HAS_CLASSNAME = 16;
    private static final long FLAGS_HAS_COMPLEX_TEXT = 2;
    private static final long FLAGS_HAS_CONTENT_DESCRIPTION = 8388608;
    private static final long FLAGS_HAS_EXTRAS = 16777216;
    private static final long FLAGS_HAS_HINT_ID_ENTRY = 34359738368L;
    private static final long FLAGS_HAS_ID = 128;
    private static final long FLAGS_HAS_INPUT_TYPE = 67108864;
    private static final long FLAGS_HAS_LARGE_COORDS = 256;
    private static final long FLAGS_HAS_LOCALE_LIST = 33554432;
    private static final long FLAGS_HAS_MAX_TEXT_EMS = 268435456;
    private static final long FLAGS_HAS_MAX_TEXT_LENGTH = 536870912;
    private static final long FLAGS_HAS_MIME_TYPES = 68719476736L;
    private static final long FLAGS_HAS_MIN_TEXT_EMS = 134217728;
    private static final long FLAGS_HAS_SCROLL = 512;
    private static final long FLAGS_HAS_TEXT = 1;
    private static final long FLAGS_HAS_TEXT_ID_ENTRY = 1073741824;
    private static final long FLAGS_LONG_CLICKABLE = 8192;
    private static final long FLAGS_OPAQUE = 4194304;
    private static final long FLAGS_SELECTED = 1048576;
    private static final long FLAGS_VISIBILITY_MASK = 12;
    private static final java.lang.String TAG = android.view.contentcapture.ViewNode.class.getSimpleName();
    private java.lang.String[] mAutofillHints;
    private android.view.autofill.AutofillId mAutofillId;
    private java.lang.CharSequence[] mAutofillOptions;
    private int mAutofillType;
    private android.view.autofill.AutofillValue mAutofillValue;
    private java.lang.String mClassName;
    private java.lang.CharSequence mContentDescription;
    private android.os.Bundle mExtras;
    private long mFlags;
    private int mHeight;
    private java.lang.String mHintIdEntry;
    private int mId;
    private java.lang.String mIdEntry;
    private java.lang.String mIdPackage;
    private java.lang.String mIdType;
    private int mInputType;
    private android.os.LocaleList mLocaleList;
    private int mMaxEms;
    private int mMaxLength;
    private int mMinEms;
    private android.view.autofill.AutofillId mParentAutofillId;
    private java.lang.String[] mReceiveContentMimeTypes;
    private int mScrollX;
    private int mScrollY;
    private android.view.contentcapture.ViewNode.ViewNodeText mText;
    private java.lang.String mTextIdEntry;
    private int mWidth;
    private int mX;
    private int mY;

    public ViewNode() {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        this.mAutofillType = 0;
    }

    private ViewNode(long j, android.os.Parcel parcel) {
        this.mId = -1;
        this.mMinEms = -1;
        this.mMaxEms = -1;
        this.mMaxLength = -1;
        this.mAutofillType = 0;
        this.mFlags = j;
        if ((32 & j) != 0) {
            this.mAutofillId = (android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class);
        }
        if ((64 & j) != 0) {
            this.mParentAutofillId = (android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class);
        }
        if ((1 & j) != 0) {
            this.mText = new android.view.contentcapture.ViewNode.ViewNodeText(parcel, (2 & j) == 0);
        }
        if ((16 & j) != 0) {
            this.mClassName = parcel.readString();
        }
        if ((128 & j) != 0) {
            this.mId = parcel.readInt();
            if (this.mId != -1) {
                this.mIdEntry = parcel.readString();
                if (this.mIdEntry != null) {
                    this.mIdType = parcel.readString();
                    this.mIdPackage = parcel.readString();
                }
            }
        }
        if ((256 & j) != 0) {
            this.mX = parcel.readInt();
            this.mY = parcel.readInt();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
        } else {
            int readInt = parcel.readInt();
            this.mX = readInt & 32767;
            this.mY = (readInt >> 16) & 32767;
            int readInt2 = parcel.readInt();
            this.mWidth = readInt2 & 32767;
            this.mHeight = (readInt2 >> 16) & 32767;
        }
        if ((512 & j) != 0) {
            this.mScrollX = parcel.readInt();
            this.mScrollY = parcel.readInt();
        }
        if ((8388608 & j) != 0) {
            this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if ((16777216 & j) != 0) {
            this.mExtras = parcel.readBundle();
        }
        if ((33554432 & j) != 0) {
            this.mLocaleList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
        }
        if ((68719476736L & j) != 0) {
            this.mReceiveContentMimeTypes = parcel.readStringArray();
        }
        if ((67108864 & j) != 0) {
            this.mInputType = parcel.readInt();
        }
        if ((134217728 & j) != 0) {
            this.mMinEms = parcel.readInt();
        }
        if ((268435456 & j) != 0) {
            this.mMaxEms = parcel.readInt();
        }
        if ((536870912 & j) != 0) {
            this.mMaxLength = parcel.readInt();
        }
        if ((1073741824 & j) != 0) {
            this.mTextIdEntry = parcel.readString();
        }
        if ((2147483648L & j) != 0) {
            this.mAutofillType = parcel.readInt();
        }
        if ((8589934592L & j) != 0) {
            this.mAutofillHints = parcel.readStringArray();
        }
        if ((4294967296L & j) != 0) {
            this.mAutofillValue = (android.view.autofill.AutofillValue) parcel.readParcelable(null, android.view.autofill.AutofillValue.class);
        }
        if ((17179869184L & j) != 0) {
            this.mAutofillOptions = parcel.readCharSequenceArray();
        }
        if ((j & 34359738368L) != 0) {
            this.mHintIdEntry = parcel.readString();
        }
    }

    public android.view.autofill.AutofillId getParentAutofillId() {
        return this.mParentAutofillId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public android.view.autofill.AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.CharSequence getText() {
        if (this.mText != null) {
            return this.mText.mText;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getClassName() {
        return this.mClassName;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getId() {
        return this.mId;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getIdPackage() {
        return this.mIdPackage;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getIdType() {
        return this.mIdType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getIdEntry() {
        return this.mIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getLeft() {
        return this.mX;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTop() {
        return this.mY;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getScrollX() {
        return this.mScrollX;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getScrollY() {
        return this.mScrollY;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getWidth() {
        return this.mWidth;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getHeight() {
        return this.mHeight;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isAssistBlocked() {
        return (this.mFlags & 1024) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isEnabled() {
        return (this.mFlags & 2048) == 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isClickable() {
        return (this.mFlags & 4096) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isLongClickable() {
        return (this.mFlags & 8192) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isContextClickable() {
        return (this.mFlags & 16384) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isFocusable() {
        return (this.mFlags & 32768) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isFocused() {
        return (this.mFlags & 65536) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isAccessibilityFocused() {
        return (this.mFlags & 131072) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isCheckable() {
        return (this.mFlags & 262144) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isChecked() {
        return (this.mFlags & 524288) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isSelected() {
        return (this.mFlags & 1048576) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isActivated() {
        return (this.mFlags & 2097152) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public boolean isOpaque() {
        return (this.mFlags & 4194304) != 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getHint() {
        if (this.mText != null) {
            return this.mText.mHint;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getHintIdEntry() {
        return this.mHintIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextSelectionStart() {
        if (this.mText != null) {
            return this.mText.mTextSelectionStart;
        }
        return -1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextSelectionEnd() {
        if (this.mText != null) {
            return this.mText.mTextSelectionEnd;
        }
        return -1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextColor() {
        if (this.mText != null) {
            return this.mText.mTextColor;
        }
        return 1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextBackgroundColor() {
        if (this.mText != null) {
            return this.mText.mTextBackgroundColor;
        }
        return 1;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public float getTextSize() {
        if (this.mText != null) {
            return this.mText.mTextSize;
        }
        return 0.0f;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getTextStyle() {
        if (this.mText != null) {
            return this.mText.mTextStyle;
        }
        return 0;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int[] getTextLineCharOffsets() {
        if (this.mText != null) {
            return this.mText.mLineCharOffsets;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int[] getTextLineBaselines() {
        if (this.mText != null) {
            return this.mText.mLineBaselines;
        }
        return null;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getVisibility() {
        return (int) (this.mFlags & 12);
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getInputType() {
        return this.mInputType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMinTextEms() {
        return this.mMinEms;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMaxTextEms() {
        return this.mMaxEms;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getMaxTextLength() {
        return this.mMaxLength;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String getTextIdEntry() {
        return this.mTextIdEntry;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public int getAutofillType() {
        return this.mAutofillType;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public android.view.autofill.AutofillValue getAutofillValue() {
        return this.mAutofillValue;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public java.lang.String[] getReceiveContentMimeTypes() {
        return this.mReceiveContentMimeTypes;
    }

    @Override // android.app.assist.AssistStructure.ViewNode
    public android.os.LocaleList getLocaleList() {
        return this.mLocaleList;
    }

    public void setTextIdEntry(java.lang.String str) {
        this.mTextIdEntry = str;
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0059, code lost:
    
        if ((((r42.mWidth & (-32768)) != 0) | ((r42.mHeight & (-32768)) != 0)) != false) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void writeSelfToParcel(android.os.Parcel parcel, int i) {
        long j = this.mFlags;
        if (this.mAutofillId != null) {
            j |= 32;
        }
        if (this.mParentAutofillId != null) {
            j |= 64;
        }
        if (this.mText != null) {
            j |= 1;
            if (!this.mText.isSimple()) {
                j |= 2;
            }
        }
        if (this.mClassName != null) {
            j |= 16;
        }
        if (this.mId != -1) {
            j |= 128;
        }
        if ((this.mX & (-32768)) == 0 && (this.mY & (-32768)) == 0) {
        }
        j |= 256;
        if (this.mScrollX != 0 || this.mScrollY != 0) {
            j |= 512;
        }
        if (this.mContentDescription != null) {
            j |= 8388608;
        }
        if (this.mExtras != null) {
            j |= 16777216;
        }
        if (this.mLocaleList != null) {
            j |= 33554432;
        }
        if (this.mReceiveContentMimeTypes != null) {
            j |= 68719476736L;
        }
        if (this.mInputType != 0) {
            j |= 67108864;
        }
        if (this.mMinEms > -1) {
            j |= 134217728;
        }
        if (this.mMaxEms > -1) {
            j |= 268435456;
        }
        if (this.mMaxLength > -1) {
            j |= 536870912;
        }
        if (this.mTextIdEntry != null) {
            j |= 1073741824;
        }
        if (this.mAutofillValue != null) {
            j |= 4294967296L;
        }
        if (this.mAutofillType != 0) {
            j |= 2147483648L;
        }
        if (this.mAutofillHints != null) {
            j |= 8589934592L;
        }
        if (this.mAutofillOptions != null) {
            j |= 17179869184L;
        }
        if (this.mHintIdEntry != null) {
            j |= 34359738368L;
        }
        parcel.writeLong(j);
        if ((j & 32) != 0) {
            parcel.writeParcelable(this.mAutofillId, i);
        }
        if ((j & 64) != 0) {
            parcel.writeParcelable(this.mParentAutofillId, i);
        }
        if ((j & 1) != 0) {
            this.mText.writeToParcel(parcel, (j & 2) == 0);
        }
        if ((16 & j) != 0) {
            parcel.writeString(this.mClassName);
        }
        if ((j & 128) != 0) {
            parcel.writeInt(this.mId);
            if (this.mId != -1) {
                parcel.writeString(this.mIdEntry);
                if (this.mIdEntry != null) {
                    parcel.writeString(this.mIdType);
                    parcel.writeString(this.mIdPackage);
                }
            }
        }
        if ((j & 256) == 0) {
            parcel.writeInt((this.mY << 16) | this.mX);
            parcel.writeInt((this.mHeight << 16) | this.mWidth);
        } else {
            parcel.writeInt(this.mX);
            parcel.writeInt(this.mY);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
        }
        if ((j & 512) != 0) {
            parcel.writeInt(this.mScrollX);
            parcel.writeInt(this.mScrollY);
        }
        if ((j & 8388608) != 0) {
            android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
        }
        if ((j & 16777216) != 0) {
            parcel.writeBundle(this.mExtras);
        }
        if ((j & 33554432) != 0) {
            parcel.writeParcelable(this.mLocaleList, 0);
        }
        if ((j & 68719476736L) != 0) {
            parcel.writeStringArray(this.mReceiveContentMimeTypes);
        }
        if ((j & 67108864) != 0) {
            parcel.writeInt(this.mInputType);
        }
        if ((j & 134217728) != 0) {
            parcel.writeInt(this.mMinEms);
        }
        if ((j & 268435456) != 0) {
            parcel.writeInt(this.mMaxEms);
        }
        if ((j & 536870912) != 0) {
            parcel.writeInt(this.mMaxLength);
        }
        if ((j & 1073741824) != 0) {
            parcel.writeString(this.mTextIdEntry);
        }
        if ((2147483648L & j) != 0) {
            parcel.writeInt(this.mAutofillType);
        }
        if ((8589934592L & j) != 0) {
            parcel.writeStringArray(this.mAutofillHints);
        }
        if ((4294967296L & j) != 0) {
            parcel.writeParcelable(this.mAutofillValue, 0);
        }
        if ((17179869184L & j) != 0) {
            parcel.writeCharSequenceArray(this.mAutofillOptions);
        }
        if ((j & 34359738368L) != 0) {
            parcel.writeString(this.mHintIdEntry);
        }
    }

    public static void writeToParcel(android.os.Parcel parcel, android.view.contentcapture.ViewNode viewNode, int i) {
        if (viewNode == null) {
            parcel.writeLong(0L);
        } else {
            viewNode.writeSelfToParcel(parcel, i);
        }
    }

    public static android.view.contentcapture.ViewNode readFromParcel(android.os.Parcel parcel) {
        long readLong = parcel.readLong();
        if (readLong == 0) {
            return null;
        }
        return new android.view.contentcapture.ViewNode(readLong, parcel);
    }

    public static final class ViewStructureImpl extends android.view.ViewStructure {
        final android.view.contentcapture.ViewNode mNode = new android.view.contentcapture.ViewNode();

        public ViewStructureImpl(android.view.View view) {
            this.mNode.mAutofillId = ((android.view.View) java.util.Objects.requireNonNull(view)).getAutofillId();
            java.lang.Object parent = view.getParent();
            if (parent instanceof android.view.View) {
                this.mNode.mParentAutofillId = ((android.view.View) parent).getAutofillId();
            }
        }

        public ViewStructureImpl(android.view.autofill.AutofillId autofillId, long j, int i) {
            this.mNode.mParentAutofillId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
            this.mNode.mAutofillId = new android.view.autofill.AutofillId(autofillId, j, i);
        }

        public android.view.contentcapture.ViewNode getNode() {
            return this.mNode;
        }

        @Override // android.view.ViewStructure
        public void setId(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mNode.mId = i;
            this.mNode.mIdPackage = str;
            this.mNode.mIdType = str2;
            this.mNode.mIdEntry = str3;
        }

        @Override // android.view.ViewStructure
        public void setDimens(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mNode.mX = i;
            this.mNode.mY = i2;
            this.mNode.mScrollX = i3;
            this.mNode.mScrollY = i4;
            this.mNode.mWidth = i5;
            this.mNode.mHeight = i6;
        }

        @Override // android.view.ViewStructure
        public void setTransformation(android.graphics.Matrix matrix) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setTransformation() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setElevation(float f) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setElevation() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setAlpha(float f) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setAlpha() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setVisibility(int i) {
            this.mNode.mFlags = (this.mNode.mFlags & (-13)) | (i & 12);
        }

        @Override // android.view.ViewStructure
        public void setAssistBlocked(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-1025)) | (z ? 1024L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setEnabled(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-2049)) | (z ? 0L : 2048L);
        }

        @Override // android.view.ViewStructure
        public void setClickable(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-4097)) | (z ? 4096L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setLongClickable(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-8193)) | (z ? 8192L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setContextClickable(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-16385)) | (z ? 16384L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setFocusable(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-32769)) | (z ? 32768L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setFocused(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-65537)) | (z ? 65536L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setAccessibilityFocused(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-131073)) | (z ? 131072L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setCheckable(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-262145)) | (z ? 262144L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setChecked(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-524289)) | (z ? 524288L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setSelected(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-1048577)) | (z ? 1048576L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setActivated(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-2097153)) | (z ? 2097152L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setOpaque(boolean z) {
            this.mNode.mFlags = (this.mNode.mFlags & (-4194305)) | (z ? 4194304L : 0L);
        }

        @Override // android.view.ViewStructure
        public void setClassName(java.lang.String str) {
            this.mNode.mClassName = str;
        }

        @Override // android.view.ViewStructure
        public void setContentDescription(java.lang.CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        @Override // android.view.ViewStructure
        public void setText(java.lang.CharSequence charSequence) {
            android.view.contentcapture.ViewNode.ViewNodeText nodeText = getNodeText();
            nodeText.mText = android.text.TextUtils.trimNoCopySpans(charSequence);
            nodeText.mTextSelectionEnd = -1;
            nodeText.mTextSelectionStart = -1;
        }

        @Override // android.view.ViewStructure
        public void setText(java.lang.CharSequence charSequence, int i, int i2) {
            android.view.contentcapture.ViewNode.ViewNodeText nodeText = getNodeText();
            nodeText.mText = android.text.TextUtils.trimNoCopySpans(charSequence);
            nodeText.mTextSelectionStart = i;
            nodeText.mTextSelectionEnd = i2;
        }

        @Override // android.view.ViewStructure
        public void setTextStyle(float f, int i, int i2, int i3) {
            android.view.contentcapture.ViewNode.ViewNodeText nodeText = getNodeText();
            nodeText.mTextColor = i;
            nodeText.mTextBackgroundColor = i2;
            nodeText.mTextSize = f;
            nodeText.mTextStyle = i3;
        }

        @Override // android.view.ViewStructure
        public void setTextLines(int[] iArr, int[] iArr2) {
            android.view.contentcapture.ViewNode.ViewNodeText nodeText = getNodeText();
            nodeText.mLineCharOffsets = iArr;
            nodeText.mLineBaselines = iArr2;
        }

        @Override // android.view.ViewStructure
        public void setTextIdEntry(java.lang.String str) {
            this.mNode.mTextIdEntry = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public void setHint(java.lang.CharSequence charSequence) {
            getNodeText().mHint = charSequence != null ? charSequence.toString() : null;
        }

        @Override // android.view.ViewStructure
        public void setHintIdEntry(java.lang.String str) {
            this.mNode.mHintIdEntry = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public java.lang.CharSequence getText() {
            return this.mNode.getText();
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionStart() {
            return this.mNode.getTextSelectionStart();
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionEnd() {
            return this.mNode.getTextSelectionEnd();
        }

        @Override // android.view.ViewStructure
        public java.lang.CharSequence getHint() {
            return this.mNode.getHint();
        }

        @Override // android.view.ViewStructure
        public android.os.Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new android.os.Bundle();
            return this.mNode.mExtras;
        }

        @Override // android.view.ViewStructure
        public boolean hasExtras() {
            return this.mNode.mExtras != null;
        }

        @Override // android.view.ViewStructure
        public void setChildCount(int i) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setChildCount() is not supported");
        }

        @Override // android.view.ViewStructure
        public int addChildCount(int i) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "addChildCount() is not supported");
            return 0;
        }

        @Override // android.view.ViewStructure
        public int getChildCount() {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "getChildCount() is not supported");
            return 0;
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure newChild(int i) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "newChild() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure asyncNewChild(int i) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "asyncNewChild() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public android.view.autofill.AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(android.view.autofill.AutofillId autofillId) {
            this.mNode.mAutofillId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(android.view.autofill.AutofillId autofillId, int i) {
            this.mNode.mParentAutofillId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
            this.mNode.mAutofillId = new android.view.autofill.AutofillId(autofillId, i);
        }

        @Override // android.view.ViewStructure
        public void setAutofillType(int i) {
            this.mNode.mAutofillType = i;
        }

        @Override // android.view.ViewStructure
        public void setReceiveContentMimeTypes(java.lang.String[] strArr) {
            this.mNode.mReceiveContentMimeTypes = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillHints(java.lang.String[] strArr) {
            this.mNode.mAutofillHints = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillValue(android.view.autofill.AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override // android.view.ViewStructure
        public void setAutofillOptions(java.lang.CharSequence[] charSequenceArr) {
            this.mNode.mAutofillOptions = charSequenceArr;
        }

        @Override // android.view.ViewStructure
        public void setInputType(int i) {
            this.mNode.mInputType = i;
        }

        @Override // android.view.ViewStructure
        public void setMinTextEms(int i) {
            this.mNode.mMinEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextEms(int i) {
            this.mNode.mMaxEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextLength(int i) {
            this.mNode.mMaxLength = i;
        }

        @Override // android.view.ViewStructure
        public void setDataIsSensitive(boolean z) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setDataIsSensitive() is not supported");
        }

        @Override // android.view.ViewStructure
        public void asyncCommit() {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "asyncCommit() is not supported");
        }

        @Override // android.view.ViewStructure
        public android.graphics.Rect getTempRect() {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "getTempRect() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public void setWebDomain(java.lang.String str) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setWebDomain() is not supported");
        }

        @Override // android.view.ViewStructure
        public void setLocaleList(android.os.LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(java.lang.String str) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "newHtmlInfoBuilder() is not supported");
            return null;
        }

        @Override // android.view.ViewStructure
        public void setHtmlInfo(android.view.ViewStructure.HtmlInfo htmlInfo) {
            android.util.Log.w(android.view.contentcapture.ViewNode.TAG, "setHtmlInfo() is not supported");
        }

        private android.view.contentcapture.ViewNode.ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new android.view.contentcapture.ViewNode.ViewNodeText();
            return this.mNode.mText;
        }
    }

    static final class ViewNodeText {
        java.lang.String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        java.lang.CharSequence mText;
        int mTextBackgroundColor;
        int mTextColor;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
        }

        boolean isSimple() {
            return this.mTextBackgroundColor == 1 && this.mTextSelectionStart == 0 && this.mTextSelectionEnd == 0 && this.mLineCharOffsets == null && this.mLineBaselines == null && this.mHint == null;
        }

        ViewNodeText(android.os.Parcel parcel, boolean z) {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
            this.mText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (!z) {
                this.mTextBackgroundColor = parcel.readInt();
                this.mTextSelectionStart = parcel.readInt();
                this.mTextSelectionEnd = parcel.readInt();
                this.mLineCharOffsets = parcel.createIntArray();
                this.mLineBaselines = parcel.createIntArray();
                this.mHint = parcel.readString();
            }
        }

        void writeToParcel(android.os.Parcel parcel, boolean z) {
            int i;
            int i2;
            java.lang.CharSequence trimToParcelableSize = android.text.TextUtils.trimToParcelableSize(this.mText);
            android.text.TextUtils.writeToParcel(trimToParcelableSize, parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (!z) {
                if (trimToParcelableSize != null) {
                    i = java.lang.Math.min(this.mTextSelectionStart, trimToParcelableSize.length());
                } else {
                    i = this.mTextSelectionStart;
                }
                if (trimToParcelableSize != null) {
                    i2 = java.lang.Math.min(this.mTextSelectionEnd, trimToParcelableSize.length());
                } else {
                    i2 = this.mTextSelectionEnd;
                }
                parcel.writeInt(this.mTextBackgroundColor);
                parcel.writeInt(i);
                parcel.writeInt(i2);
                parcel.writeIntArray(this.mLineCharOffsets);
                parcel.writeIntArray(this.mLineBaselines);
                parcel.writeString(this.mHint);
            }
        }
    }
}
