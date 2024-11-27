package android.view.inputmethod;

/* loaded from: classes4.dex */
public class EditorInfo implements android.text.InputType, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.EditorInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.EditorInfo>() { // from class: android.view.inputmethod.EditorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.EditorInfo createFromParcel(android.os.Parcel parcel) {
            android.view.inputmethod.EditorInfo editorInfo = new android.view.inputmethod.EditorInfo();
            editorInfo.inputType = parcel.readInt();
            editorInfo.imeOptions = parcel.readInt();
            editorInfo.privateImeOptions = parcel.readString();
            editorInfo.internalImeOptions = parcel.readInt();
            editorInfo.actionLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.actionId = parcel.readInt();
            editorInfo.initialSelStart = parcel.readInt();
            editorInfo.initialSelEnd = parcel.readInt();
            editorInfo.initialCapsMode = parcel.readInt();
            editorInfo.mInitialToolType = parcel.readInt();
            editorInfo.hintText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.label = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            editorInfo.packageName = parcel.readString();
            editorInfo.autofillId = (android.view.autofill.AutofillId) parcel.readParcelable(android.view.autofill.AutofillId.class.getClassLoader(), android.view.autofill.AutofillId.class);
            editorInfo.fieldId = parcel.readInt();
            editorInfo.fieldName = parcel.readString();
            editorInfo.extras = parcel.readBundle();
            editorInfo.mSupportedHandwritingGestureTypes = parcel.readInt();
            editorInfo.mSupportedHandwritingGesturePreviewTypes = parcel.readInt();
            if (android.view.inputmethod.Flags.editorinfoHandwritingEnabled()) {
                editorInfo.mIsStylusHandwritingEnabled = parcel.readBoolean();
            }
            if (parcel.readBoolean()) {
                editorInfo.mInitialSurroundingText = android.view.inputmethod.SurroundingText.CREATOR.createFromParcel(parcel);
            }
            android.os.LocaleList createFromParcel = android.os.LocaleList.CREATOR.createFromParcel(parcel);
            if (createFromParcel.isEmpty()) {
                createFromParcel = null;
            }
            editorInfo.hintLocales = createFromParcel;
            editorInfo.contentMimeTypes = parcel.readStringArray();
            editorInfo.targetInputMethodUser = android.os.UserHandle.readFromParcel(parcel);
            return editorInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.EditorInfo[] newArray(int i) {
            return new android.view.inputmethod.EditorInfo[i];
        }
    };
    public static final int IME_ACTION_DONE = 6;
    public static final int IME_ACTION_GO = 2;
    public static final int IME_ACTION_NEXT = 5;
    public static final int IME_ACTION_NONE = 1;
    public static final int IME_ACTION_PREVIOUS = 7;
    public static final int IME_ACTION_SEARCH = 3;
    public static final int IME_ACTION_SEND = 4;
    public static final int IME_ACTION_UNSPECIFIED = 0;
    public static final int IME_FLAG_FORCE_ASCII = Integer.MIN_VALUE;
    public static final int IME_FLAG_NAVIGATE_NEXT = 134217728;
    public static final int IME_FLAG_NAVIGATE_PREVIOUS = 67108864;
    public static final int IME_FLAG_NO_ACCESSORY_ACTION = 536870912;
    public static final int IME_FLAG_NO_ENTER_ACTION = 1073741824;
    public static final int IME_FLAG_NO_EXTRACT_UI = 268435456;
    public static final int IME_FLAG_NO_FULLSCREEN = 33554432;
    public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
    public static final int IME_INTERNAL_FLAG_APP_WINDOW_PORTRAIT = 1;
    public static final int IME_MASK_ACTION = 255;
    public static final int IME_NULL = 0;
    static final int MAX_INITIAL_SELECTION_LENGTH = 1024;
    static final int MEMORY_EFFICIENT_TEXT_LENGTH = 2048;
    public android.view.autofill.AutofillId autofillId;
    public android.os.Bundle extras;
    public int fieldId;
    public java.lang.String fieldName;
    public java.lang.CharSequence hintText;
    public java.lang.CharSequence label;
    private boolean mIsStylusHandwritingEnabled;
    private int mSupportedHandwritingGesturePreviewTypes;
    private int mSupportedHandwritingGestureTypes;
    public java.lang.String packageName;
    public int inputType = 0;
    public int imeOptions = 0;
    public java.lang.String privateImeOptions = null;
    public int internalImeOptions = 0;
    public java.lang.CharSequence actionLabel = null;
    public int actionId = 0;
    public int initialSelStart = -1;
    public int initialSelEnd = -1;
    public int initialCapsMode = 0;
    public android.os.LocaleList hintLocales = null;
    public java.lang.String[] contentMimeTypes = null;
    public android.os.UserHandle targetInputMethodUser = null;
    private android.view.inputmethod.SurroundingText mInitialSurroundingText = null;
    private int mInitialToolType = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface TrimPolicy {
        public static final int HEAD = 0;
        public static final int TAIL = 1;
    }

    public void setSupportedHandwritingGestures(java.util.List<java.lang.Class<? extends android.view.inputmethod.HandwritingGesture>> list) {
        java.util.Objects.requireNonNull(list);
        int i = 0;
        if (list.isEmpty()) {
            this.mSupportedHandwritingGestureTypes = 0;
            return;
        }
        for (java.lang.Class<? extends android.view.inputmethod.HandwritingGesture> cls : list) {
            java.util.Objects.requireNonNull(cls);
            if (cls.equals(android.view.inputmethod.SelectGesture.class)) {
                i |= 1;
            } else if (cls.equals(android.view.inputmethod.SelectRangeGesture.class)) {
                i |= 32;
            } else if (cls.equals(android.view.inputmethod.InsertGesture.class)) {
                i |= 2;
            } else if (cls.equals(android.view.inputmethod.InsertModeGesture.class)) {
                i |= 128;
            } else if (cls.equals(android.view.inputmethod.DeleteGesture.class)) {
                i |= 4;
            } else if (cls.equals(android.view.inputmethod.DeleteRangeGesture.class)) {
                i |= 64;
            } else if (cls.equals(android.view.inputmethod.RemoveSpaceGesture.class)) {
                i |= 8;
            } else if (cls.equals(android.view.inputmethod.JoinOrSplitGesture.class)) {
                i |= 16;
            } else {
                throw new java.lang.IllegalArgumentException("Unknown gesture type: " + cls);
            }
        }
        this.mSupportedHandwritingGestureTypes = i;
    }

    public java.util.List<java.lang.Class<? extends android.view.inputmethod.HandwritingGesture>> getSupportedHandwritingGestures() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (this.mSupportedHandwritingGestureTypes == 0) {
            return arrayList;
        }
        if ((this.mSupportedHandwritingGestureTypes & 1) == 1) {
            arrayList.add(android.view.inputmethod.SelectGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 32) == 32) {
            arrayList.add(android.view.inputmethod.SelectRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 2) == 2) {
            arrayList.add(android.view.inputmethod.InsertGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 128) == 128) {
            arrayList.add(android.view.inputmethod.InsertModeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 4) == 4) {
            arrayList.add(android.view.inputmethod.DeleteGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 64) == 64) {
            arrayList.add(android.view.inputmethod.DeleteRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 8) == 8) {
            arrayList.add(android.view.inputmethod.RemoveSpaceGesture.class);
        }
        if ((this.mSupportedHandwritingGestureTypes & 16) == 16) {
            arrayList.add(android.view.inputmethod.JoinOrSplitGesture.class);
        }
        return arrayList;
    }

    public void setSupportedHandwritingGesturePreviews(java.util.Set<java.lang.Class<? extends android.view.inputmethod.PreviewableHandwritingGesture>> set) {
        java.util.Objects.requireNonNull(set);
        int i = 0;
        if (set.isEmpty()) {
            this.mSupportedHandwritingGesturePreviewTypes = 0;
            return;
        }
        for (java.lang.Class<? extends android.view.inputmethod.PreviewableHandwritingGesture> cls : set) {
            java.util.Objects.requireNonNull(cls);
            if (cls.equals(android.view.inputmethod.SelectGesture.class)) {
                i |= 1;
            } else if (cls.equals(android.view.inputmethod.SelectRangeGesture.class)) {
                i |= 32;
            } else if (cls.equals(android.view.inputmethod.DeleteGesture.class)) {
                i |= 4;
            } else if (cls.equals(android.view.inputmethod.DeleteRangeGesture.class)) {
                i |= 64;
            } else {
                throw new java.lang.IllegalArgumentException("Unsupported gesture type for preview: " + cls);
            }
        }
        this.mSupportedHandwritingGesturePreviewTypes = i;
    }

    public java.util.Set<java.lang.Class<? extends android.view.inputmethod.PreviewableHandwritingGesture>> getSupportedHandwritingGesturePreviews() {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (this.mSupportedHandwritingGesturePreviewTypes == 0) {
            return hashSet;
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 1) == 1) {
            hashSet.add(android.view.inputmethod.SelectGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 32) == 32) {
            hashSet.add(android.view.inputmethod.SelectRangeGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 4) == 4) {
            hashSet.add(android.view.inputmethod.DeleteGesture.class);
        }
        if ((this.mSupportedHandwritingGesturePreviewTypes & 64) == 64) {
            hashSet.add(android.view.inputmethod.DeleteRangeGesture.class);
        }
        return hashSet;
    }

    public void setStylusHandwritingEnabled(boolean z) {
        this.mIsStylusHandwritingEnabled = z;
    }

    public boolean isStylusHandwritingEnabled() {
        return this.mIsStylusHandwritingEnabled;
    }

    public void setInitialSurroundingText(java.lang.CharSequence charSequence) {
        setInitialSurroundingSubText(charSequence, 0);
    }

    public final void setInitialSurroundingTextInternal(android.view.inputmethod.SurroundingText surroundingText) {
        this.mInitialSurroundingText = surroundingText;
    }

    public void setInitialSurroundingSubText(java.lang.CharSequence charSequence, int i) {
        java.util.Objects.requireNonNull(charSequence);
        if (isPasswordInputType(this.inputType)) {
            this.mInitialSurroundingText = null;
            return;
        }
        int i2 = (this.initialSelStart > this.initialSelEnd ? this.initialSelEnd : this.initialSelStart) - i;
        int i3 = (this.initialSelStart > this.initialSelEnd ? this.initialSelStart : this.initialSelEnd) - i;
        int length = charSequence.length();
        if (i < 0 || i2 < 0 || i3 > length) {
            this.mInitialSurroundingText = null;
        } else if (length <= 2048) {
            this.mInitialSurroundingText = new android.view.inputmethod.SurroundingText(charSequence, i2, i3, i);
        } else {
            trimLongSurroundingText(charSequence, i2, i3, i);
        }
    }

    private void trimLongSurroundingText(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        java.lang.CharSequence subSequence;
        int i4 = i2 - i;
        int i5 = i4 > 1024 ? 0 : i4;
        int i6 = 2048 - i5;
        int min = java.lang.Math.min(charSequence.length() - i2, i6 - java.lang.Math.min(i, (int) (i6 * 0.8d)));
        int min2 = java.lang.Math.min(i, i6 - min);
        int i7 = i - min2;
        if (isCutOnSurrogate(charSequence, i7, 0)) {
            i7++;
            min2--;
        }
        if (isCutOnSurrogate(charSequence, (i2 + min) - 1, 1)) {
            min--;
        }
        int i8 = min2 + i5 + min;
        if (i5 != i4) {
            subSequence = android.text.TextUtils.concat(charSequence.subSequence(i7, i7 + min2), charSequence.subSequence(i2, min + i2));
        } else {
            subSequence = charSequence.subSequence(i7, i8 + i7);
        }
        int i9 = min2 + 0;
        this.mInitialSurroundingText = new android.view.inputmethod.SurroundingText(subSequence, i9, i5 + i9, (i3 + i) - i9);
    }

    public java.lang.CharSequence getInitialTextBeforeCursor(int i, int i2) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int min = java.lang.Math.min(this.mInitialSurroundingText.getSelectionStart(), this.mInitialSurroundingText.getSelectionEnd());
        int min2 = java.lang.Math.min(i, min);
        if ((i2 & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(min - min2, min);
        }
        return android.text.TextUtils.substring(this.mInitialSurroundingText.getText(), min - min2, min);
    }

    public java.lang.CharSequence getInitialSelectedText(int i) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int i2 = (this.initialSelStart > this.initialSelEnd ? this.initialSelStart : this.initialSelEnd) - (this.initialSelStart > this.initialSelEnd ? this.initialSelEnd : this.initialSelStart);
        int selectionStart = this.mInitialSurroundingText.getSelectionStart();
        int selectionEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selectionStart <= selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int i3 = selectionStart - selectionEnd;
        if (this.initialSelStart < 0 || this.initialSelEnd < 0 || i3 != i2) {
            return null;
        }
        if ((i & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(selectionEnd, selectionStart);
        }
        return android.text.TextUtils.substring(this.mInitialSurroundingText.getText(), selectionEnd, selectionStart);
    }

    public java.lang.CharSequence getInitialTextAfterCursor(int i, int i2) {
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int length = this.mInitialSurroundingText.getText().length();
        int max = java.lang.Math.max(this.mInitialSurroundingText.getSelectionStart(), this.mInitialSurroundingText.getSelectionEnd());
        int min = java.lang.Math.min(i, length - max);
        if ((i2 & 1) != 0) {
            return this.mInitialSurroundingText.getText().subSequence(max, min + max);
        }
        return android.text.TextUtils.substring(this.mInitialSurroundingText.getText(), max, min + max);
    }

    public android.view.inputmethod.SurroundingText getInitialSurroundingText(int i, int i2, int i3) {
        java.lang.CharSequence substring;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        if (this.mInitialSurroundingText == null) {
            return null;
        }
        int length = this.mInitialSurroundingText.getText().length();
        int selectionStart = this.mInitialSurroundingText.getSelectionStart();
        int selectionEnd = this.mInitialSurroundingText.getSelectionEnd();
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int min = java.lang.Math.min(i, selectionStart);
        int min2 = java.lang.Math.min(i2 + selectionEnd, length);
        int i4 = selectionStart - min;
        if ((i3 & 1) != 0) {
            substring = this.mInitialSurroundingText.getText().subSequence(i4, min2);
        } else {
            substring = android.text.TextUtils.substring(this.mInitialSurroundingText.getText(), i4, min2);
        }
        return new android.view.inputmethod.SurroundingText(substring, min, java.lang.Math.min(selectionEnd - i4, length), this.mInitialSurroundingText.getOffset() + i4);
    }

    private static boolean isCutOnSurrogate(java.lang.CharSequence charSequence, int i, int i2) {
        switch (i2) {
            case 0:
                return java.lang.Character.isLowSurrogate(charSequence.charAt(i));
            case 1:
                return java.lang.Character.isHighSurrogate(charSequence.charAt(i));
            default:
                return false;
        }
    }

    private static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    public final void makeCompatible(int i) {
        if (i < 11) {
            switch (this.inputType & 4095) {
                case 2:
                case 18:
                    this.inputType = (this.inputType & android.text.InputType.TYPE_MASK_FLAGS) | 2;
                    break;
                case 209:
                    this.inputType = (this.inputType & android.text.InputType.TYPE_MASK_FLAGS) | 33;
                    break;
                case 225:
                    this.inputType = (this.inputType & android.text.InputType.TYPE_MASK_FLAGS) | 129;
                    break;
            }
        }
    }

    public int getInitialToolType() {
        return this.mInitialToolType;
    }

    public void setInitialToolType(int i) {
        this.mInitialToolType = i;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.inputType);
        protoOutputStream.write(1120986464258L, this.imeOptions);
        protoOutputStream.write(1138166333443L, this.privateImeOptions);
        protoOutputStream.write(1138166333444L, this.packageName);
        protoOutputStream.write(1120986464261L, this.fieldId);
        if (this.targetInputMethodUser != null) {
            protoOutputStream.write(1120986464262L, this.targetInputMethodUser.getIdentifier());
        }
        protoOutputStream.end(start);
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        dump(printer, str, true);
    }

    public void dump(android.util.Printer printer, java.lang.String str, boolean z) {
        printer.println(str + "inputType=0x" + java.lang.Integer.toHexString(this.inputType) + " imeOptions=0x" + java.lang.Integer.toHexString(this.imeOptions) + " privateImeOptions=" + this.privateImeOptions);
        printer.println(str + "actionLabel=" + ((java.lang.Object) this.actionLabel) + " actionId=" + this.actionId);
        printer.println(str + "initialSelStart=" + this.initialSelStart + " initialSelEnd=" + this.initialSelEnd + " initialToolType=" + this.mInitialToolType + " initialCapsMode=0x" + java.lang.Integer.toHexString(this.initialCapsMode));
        printer.println(str + "hintText=" + ((java.lang.Object) this.hintText) + " label=" + ((java.lang.Object) this.label));
        printer.println(str + "packageName=" + this.packageName + " autofillId=" + this.autofillId + " fieldId=" + this.fieldId + " fieldName=" + this.fieldName);
        if (z) {
            printer.println(str + "extras=" + this.extras);
        }
        printer.println(str + "hintLocales=" + this.hintLocales);
        printer.println(str + "supportedHandwritingGestureTypes=" + com.android.internal.inputmethod.InputMethodDebug.handwritingGestureTypeFlagsToString(this.mSupportedHandwritingGestureTypes));
        printer.println(str + "supportedHandwritingGesturePreviewTypes=" + com.android.internal.inputmethod.InputMethodDebug.handwritingGestureTypeFlagsToString(this.mSupportedHandwritingGesturePreviewTypes));
        printer.println(str + "isStylusHandwritingEnabled=" + this.mIsStylusHandwritingEnabled);
        printer.println(str + "contentMimeTypes=" + java.util.Arrays.toString(this.contentMimeTypes));
        if (this.targetInputMethodUser != null) {
            printer.println(str + "targetInputMethodUserId=" + this.targetInputMethodUser.getIdentifier());
        }
    }

    public final android.view.inputmethod.EditorInfo createCopyInternal() {
        android.view.inputmethod.EditorInfo editorInfo = new android.view.inputmethod.EditorInfo();
        editorInfo.inputType = this.inputType;
        editorInfo.imeOptions = this.imeOptions;
        editorInfo.privateImeOptions = this.privateImeOptions;
        editorInfo.internalImeOptions = this.internalImeOptions;
        editorInfo.actionLabel = android.text.TextUtils.stringOrSpannedString(this.actionLabel);
        editorInfo.actionId = this.actionId;
        editorInfo.initialSelStart = this.initialSelStart;
        editorInfo.initialSelEnd = this.initialSelEnd;
        editorInfo.initialCapsMode = this.initialCapsMode;
        editorInfo.mInitialToolType = this.mInitialToolType;
        editorInfo.hintText = android.text.TextUtils.stringOrSpannedString(this.hintText);
        editorInfo.label = android.text.TextUtils.stringOrSpannedString(this.label);
        editorInfo.packageName = this.packageName;
        editorInfo.autofillId = this.autofillId;
        editorInfo.fieldId = this.fieldId;
        editorInfo.fieldName = this.fieldName;
        editorInfo.extras = this.extras != null ? this.extras.deepCopy() : null;
        editorInfo.mInitialSurroundingText = this.mInitialSurroundingText;
        editorInfo.hintLocales = this.hintLocales;
        editorInfo.contentMimeTypes = (java.lang.String[]) com.android.internal.util.ArrayUtils.cloneOrNull(this.contentMimeTypes);
        editorInfo.targetInputMethodUser = this.targetInputMethodUser;
        editorInfo.mSupportedHandwritingGestureTypes = this.mSupportedHandwritingGestureTypes;
        editorInfo.mSupportedHandwritingGesturePreviewTypes = this.mSupportedHandwritingGesturePreviewTypes;
        return editorInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.inputType);
        parcel.writeInt(this.imeOptions);
        parcel.writeString(this.privateImeOptions);
        parcel.writeInt(this.internalImeOptions);
        android.text.TextUtils.writeToParcel(this.actionLabel, parcel, i);
        parcel.writeInt(this.actionId);
        parcel.writeInt(this.initialSelStart);
        parcel.writeInt(this.initialSelEnd);
        parcel.writeInt(this.initialCapsMode);
        parcel.writeInt(this.mInitialToolType);
        android.text.TextUtils.writeToParcel(this.hintText, parcel, i);
        android.text.TextUtils.writeToParcel(this.label, parcel, i);
        parcel.writeString(this.packageName);
        parcel.writeParcelable(this.autofillId, i);
        parcel.writeInt(this.fieldId);
        parcel.writeString(this.fieldName);
        parcel.writeBundle(this.extras);
        parcel.writeInt(this.mSupportedHandwritingGestureTypes);
        parcel.writeInt(this.mSupportedHandwritingGesturePreviewTypes);
        if (android.view.inputmethod.Flags.editorinfoHandwritingEnabled()) {
            parcel.writeBoolean(this.mIsStylusHandwritingEnabled);
        }
        parcel.writeBoolean(this.mInitialSurroundingText != null);
        if (this.mInitialSurroundingText != null) {
            this.mInitialSurroundingText.writeToParcel(parcel, i);
        }
        if (this.hintLocales != null) {
            this.hintLocales.writeToParcel(parcel, i);
        } else {
            android.os.LocaleList.getEmptyLocaleList().writeToParcel(parcel, i);
        }
        parcel.writeStringArray(this.contentMimeTypes);
        android.os.UserHandle.writeToParcel(this.targetInputMethodUser, parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean kindofEquals(android.view.inputmethod.EditorInfo editorInfo) {
        if (editorInfo == null) {
            return false;
        }
        if (this == editorInfo) {
            return true;
        }
        if (this.inputType != editorInfo.inputType || this.imeOptions != editorInfo.imeOptions || this.internalImeOptions != editorInfo.internalImeOptions || this.actionId != editorInfo.actionId || this.initialSelStart != editorInfo.initialSelStart || this.initialSelEnd != editorInfo.initialSelEnd || this.initialCapsMode != editorInfo.initialCapsMode || this.fieldId != editorInfo.fieldId || this.mSupportedHandwritingGestureTypes != editorInfo.mSupportedHandwritingGestureTypes || this.mSupportedHandwritingGesturePreviewTypes != editorInfo.mSupportedHandwritingGesturePreviewTypes || !java.util.Objects.equals(this.autofillId, editorInfo.autofillId) || !java.util.Objects.equals(this.privateImeOptions, editorInfo.privateImeOptions) || !java.util.Objects.equals(this.packageName, editorInfo.packageName) || !java.util.Objects.equals(this.fieldName, editorInfo.fieldName) || !java.util.Objects.equals(this.hintLocales, editorInfo.hintLocales) || !java.util.Objects.equals(this.targetInputMethodUser, editorInfo.targetInputMethodUser) || !java.util.Arrays.equals(this.contentMimeTypes, editorInfo.contentMimeTypes) || !android.text.TextUtils.equals(this.actionLabel, editorInfo.actionLabel) || !android.text.TextUtils.equals(this.hintText, editorInfo.hintText) || !android.text.TextUtils.equals(this.label, editorInfo.label)) {
            return false;
        }
        if (this.extras != editorInfo.extras && (this.extras == null || !this.extras.kindofEquals(editorInfo.extras))) {
            return false;
        }
        if (this.mInitialSurroundingText != editorInfo.mInitialSurroundingText && (this.mInitialSurroundingText == null || !this.mInitialSurroundingText.isEqualTo(editorInfo.mInitialSurroundingText))) {
            return false;
        }
        return true;
    }
}
