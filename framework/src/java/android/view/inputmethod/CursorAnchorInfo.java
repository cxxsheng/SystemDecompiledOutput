package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class CursorAnchorInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.CursorAnchorInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.CursorAnchorInfo>() { // from class: android.view.inputmethod.CursorAnchorInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CursorAnchorInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.CursorAnchorInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.CursorAnchorInfo[] newArray(int i) {
            return new android.view.inputmethod.CursorAnchorInfo[i];
        }
    };
    public static final int FLAG_HAS_INVISIBLE_REGION = 2;
    public static final int FLAG_HAS_VISIBLE_REGION = 1;
    public static final int FLAG_IS_RTL = 4;
    private final android.view.inputmethod.SparseRectFArray mCharacterBoundsArray;
    private final java.lang.CharSequence mComposingText;
    private final int mComposingTextStart;
    private final android.view.inputmethod.EditorBoundsInfo mEditorBoundsInfo;
    private final int mHashCode;
    private final float mInsertionMarkerBaseline;
    private final float mInsertionMarkerBottom;
    private final int mInsertionMarkerFlags;
    private final float mInsertionMarkerHorizontal;
    private final float mInsertionMarkerTop;
    private final float[] mMatrixValues;
    private final int mSelectionEnd;
    private final int mSelectionStart;
    private final android.view.inputmethod.TextAppearanceInfo mTextAppearanceInfo;
    private final float[] mVisibleLineBounds;

    public CursorAnchorInfo(android.os.Parcel parcel) {
        this.mHashCode = parcel.readInt();
        this.mSelectionStart = parcel.readInt();
        this.mSelectionEnd = parcel.readInt();
        this.mComposingTextStart = parcel.readInt();
        this.mComposingText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mInsertionMarkerFlags = parcel.readInt();
        this.mInsertionMarkerHorizontal = parcel.readFloat();
        this.mInsertionMarkerTop = parcel.readFloat();
        this.mInsertionMarkerBaseline = parcel.readFloat();
        this.mInsertionMarkerBottom = parcel.readFloat();
        this.mCharacterBoundsArray = (android.view.inputmethod.SparseRectFArray) parcel.readTypedObject(android.view.inputmethod.SparseRectFArray.CREATOR);
        this.mEditorBoundsInfo = (android.view.inputmethod.EditorBoundsInfo) parcel.readTypedObject(android.view.inputmethod.EditorBoundsInfo.CREATOR);
        this.mMatrixValues = parcel.createFloatArray();
        this.mVisibleLineBounds = parcel.createFloatArray();
        this.mTextAppearanceInfo = (android.view.inputmethod.TextAppearanceInfo) parcel.readTypedObject(android.view.inputmethod.TextAppearanceInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHashCode);
        parcel.writeInt(this.mSelectionStart);
        parcel.writeInt(this.mSelectionEnd);
        parcel.writeInt(this.mComposingTextStart);
        android.text.TextUtils.writeToParcel(this.mComposingText, parcel, i);
        parcel.writeInt(this.mInsertionMarkerFlags);
        parcel.writeFloat(this.mInsertionMarkerHorizontal);
        parcel.writeFloat(this.mInsertionMarkerTop);
        parcel.writeFloat(this.mInsertionMarkerBaseline);
        parcel.writeFloat(this.mInsertionMarkerBottom);
        parcel.writeTypedObject(this.mCharacterBoundsArray, i);
        parcel.writeTypedObject(this.mEditorBoundsInfo, i);
        parcel.writeFloatArray(this.mMatrixValues);
        parcel.writeFloatArray(this.mVisibleLineBounds);
        parcel.writeTypedObject(this.mTextAppearanceInfo, i);
    }

    public int hashCode() {
        return this.mHashCode;
    }

    private static boolean areSameFloatImpl(float f, float f2) {
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || f == f2;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.view.inputmethod.CursorAnchorInfo)) {
            return false;
        }
        android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo = (android.view.inputmethod.CursorAnchorInfo) obj;
        if (hashCode() != cursorAnchorInfo.hashCode() || this.mSelectionStart != cursorAnchorInfo.mSelectionStart || this.mSelectionEnd != cursorAnchorInfo.mSelectionEnd || this.mInsertionMarkerFlags != cursorAnchorInfo.mInsertionMarkerFlags || !areSameFloatImpl(this.mInsertionMarkerHorizontal, cursorAnchorInfo.mInsertionMarkerHorizontal) || !areSameFloatImpl(this.mInsertionMarkerTop, cursorAnchorInfo.mInsertionMarkerTop) || !areSameFloatImpl(this.mInsertionMarkerBaseline, cursorAnchorInfo.mInsertionMarkerBaseline) || !areSameFloatImpl(this.mInsertionMarkerBottom, cursorAnchorInfo.mInsertionMarkerBottom) || !java.util.Objects.equals(this.mCharacterBoundsArray, cursorAnchorInfo.mCharacterBoundsArray) || !java.util.Objects.equals(this.mEditorBoundsInfo, cursorAnchorInfo.mEditorBoundsInfo) || !java.util.Arrays.equals(this.mVisibleLineBounds, cursorAnchorInfo.mVisibleLineBounds) || this.mComposingTextStart != cursorAnchorInfo.mComposingTextStart || !java.util.Objects.equals(this.mComposingText, cursorAnchorInfo.mComposingText) || this.mMatrixValues.length != cursorAnchorInfo.mMatrixValues.length) {
            return false;
        }
        for (int i = 0; i < this.mMatrixValues.length; i++) {
            if (this.mMatrixValues[i] != cursorAnchorInfo.mMatrixValues[i]) {
                return false;
            }
        }
        if (!java.util.Objects.equals(this.mTextAppearanceInfo, cursorAnchorInfo.mTextAppearanceInfo)) {
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return "CursorAnchorInfo{mHashCode=" + this.mHashCode + " mSelection=" + this.mSelectionStart + "," + this.mSelectionEnd + " mComposingTextStart=" + this.mComposingTextStart + " mComposingText=" + ((java.lang.Object) this.mComposingText) + " mInsertionMarkerFlags=" + this.mInsertionMarkerFlags + " mInsertionMarkerHorizontal=" + this.mInsertionMarkerHorizontal + " mInsertionMarkerTop=" + this.mInsertionMarkerTop + " mInsertionMarkerBaseline=" + this.mInsertionMarkerBaseline + " mInsertionMarkerBottom=" + this.mInsertionMarkerBottom + " mCharacterBoundsArray=" + this.mCharacterBoundsArray + " mEditorBoundsInfo=" + this.mEditorBoundsInfo + " mVisibleLineBounds=" + getVisibleLineBounds() + " mMatrix=" + java.util.Arrays.toString(this.mMatrixValues) + " mTextAppearanceInfo=" + this.mTextAppearanceInfo + "}";
    }

    public static final class Builder {
        private static final int LINE_BOUNDS_INITIAL_SIZE = 4;
        private int mSelectionStart = -1;
        private int mSelectionEnd = -1;
        private int mComposingTextStart = -1;
        private java.lang.CharSequence mComposingText = null;
        private float mInsertionMarkerHorizontal = Float.NaN;
        private float mInsertionMarkerTop = Float.NaN;
        private float mInsertionMarkerBaseline = Float.NaN;
        private float mInsertionMarkerBottom = Float.NaN;
        private int mInsertionMarkerFlags = 0;
        private android.view.inputmethod.SparseRectFArray.SparseRectFArrayBuilder mCharacterBoundsArrayBuilder = null;
        private android.view.inputmethod.EditorBoundsInfo mEditorBoundsInfo = null;
        private float[] mMatrixValues = null;
        private boolean mMatrixInitialized = false;
        private float[] mVisibleLineBounds = new float[16];
        private int mVisibleLineBoundsCount = 0;
        private android.view.inputmethod.TextAppearanceInfo mTextAppearanceInfo = null;

        public android.view.inputmethod.CursorAnchorInfo.Builder setSelectionRange(int i, int i2) {
            this.mSelectionStart = i;
            this.mSelectionEnd = i2;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder setComposingText(int i, java.lang.CharSequence charSequence) {
            this.mComposingTextStart = i;
            if (charSequence == null) {
                this.mComposingText = null;
            } else {
                this.mComposingText = new android.text.SpannedString(charSequence);
            }
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder setInsertionMarkerLocation(float f, float f2, float f3, float f4, int i) {
            this.mInsertionMarkerHorizontal = f;
            this.mInsertionMarkerTop = f2;
            this.mInsertionMarkerBaseline = f3;
            this.mInsertionMarkerBottom = f4;
            this.mInsertionMarkerFlags = i;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder addCharacterBounds(int i, float f, float f2, float f3, float f4, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("index must not be a negative integer.");
            }
            if (this.mCharacterBoundsArrayBuilder == null) {
                this.mCharacterBoundsArrayBuilder = new android.view.inputmethod.SparseRectFArray.SparseRectFArrayBuilder();
            }
            this.mCharacterBoundsArrayBuilder.append(i, f, f2, f3, f4, i2);
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder setEditorBoundsInfo(android.view.inputmethod.EditorBoundsInfo editorBoundsInfo) {
            this.mEditorBoundsInfo = editorBoundsInfo;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder setMatrix(android.graphics.Matrix matrix) {
            if (this.mMatrixValues == null) {
                this.mMatrixValues = new float[9];
            }
            if (matrix == null) {
                matrix = android.graphics.Matrix.IDENTITY_MATRIX;
            }
            matrix.getValues(this.mMatrixValues);
            this.mMatrixInitialized = true;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder setTextAppearanceInfo(android.view.inputmethod.TextAppearanceInfo textAppearanceInfo) {
            this.mTextAppearanceInfo = textAppearanceInfo;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder addVisibleLineBounds(float f, float f2, float f3, float f4) {
            if (this.mVisibleLineBounds.length <= this.mVisibleLineBoundsCount + 4) {
                this.mVisibleLineBounds = java.util.Arrays.copyOf(this.mVisibleLineBounds, (this.mVisibleLineBoundsCount + 4) * 2);
            }
            float[] fArr = this.mVisibleLineBounds;
            int i = this.mVisibleLineBoundsCount;
            this.mVisibleLineBoundsCount = i + 1;
            fArr[i] = f;
            float[] fArr2 = this.mVisibleLineBounds;
            int i2 = this.mVisibleLineBoundsCount;
            this.mVisibleLineBoundsCount = i2 + 1;
            fArr2[i2] = f2;
            float[] fArr3 = this.mVisibleLineBounds;
            int i3 = this.mVisibleLineBoundsCount;
            this.mVisibleLineBoundsCount = i3 + 1;
            fArr3[i3] = f3;
            float[] fArr4 = this.mVisibleLineBounds;
            int i4 = this.mVisibleLineBoundsCount;
            this.mVisibleLineBoundsCount = i4 + 1;
            fArr4[i4] = f4;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo.Builder clearVisibleLineBounds() {
            this.mVisibleLineBoundsCount = 0;
            return this;
        }

        public android.view.inputmethod.CursorAnchorInfo build() {
            if (!this.mMatrixInitialized) {
                boolean z = (this.mCharacterBoundsArrayBuilder == null || this.mCharacterBoundsArrayBuilder.isEmpty()) ? false : true;
                boolean z2 = this.mVisibleLineBounds != null && this.mVisibleLineBoundsCount > 0;
                if (z || z2 || !java.lang.Float.isNaN(this.mInsertionMarkerHorizontal) || !java.lang.Float.isNaN(this.mInsertionMarkerTop) || !java.lang.Float.isNaN(this.mInsertionMarkerBaseline) || !java.lang.Float.isNaN(this.mInsertionMarkerBottom)) {
                    throw new java.lang.IllegalArgumentException("Coordinate transformation matrix is required when positional parameters are specified.");
                }
            }
            return android.view.inputmethod.CursorAnchorInfo.create(this);
        }

        public void reset() {
            this.mSelectionStart = -1;
            this.mSelectionEnd = -1;
            this.mComposingTextStart = -1;
            this.mComposingText = null;
            this.mInsertionMarkerFlags = 0;
            this.mInsertionMarkerHorizontal = Float.NaN;
            this.mInsertionMarkerTop = Float.NaN;
            this.mInsertionMarkerBaseline = Float.NaN;
            this.mInsertionMarkerBottom = Float.NaN;
            this.mMatrixInitialized = false;
            if (this.mCharacterBoundsArrayBuilder != null) {
                this.mCharacterBoundsArrayBuilder.reset();
            }
            this.mEditorBoundsInfo = null;
            clearVisibleLineBounds();
            this.mTextAppearanceInfo = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.inputmethod.CursorAnchorInfo create(android.view.inputmethod.CursorAnchorInfo.Builder builder) {
        android.view.inputmethod.SparseRectFArray sparseRectFArray;
        if (builder.mCharacterBoundsArrayBuilder != null) {
            sparseRectFArray = builder.mCharacterBoundsArrayBuilder.build();
        } else {
            sparseRectFArray = null;
        }
        float[] fArr = new float[9];
        if (builder.mMatrixInitialized) {
            java.lang.System.arraycopy(builder.mMatrixValues, 0, fArr, 0, 9);
        } else {
            android.graphics.Matrix.IDENTITY_MATRIX.getValues(fArr);
        }
        return new android.view.inputmethod.CursorAnchorInfo(builder.mSelectionStart, builder.mSelectionEnd, builder.mComposingTextStart, builder.mComposingText, builder.mInsertionMarkerFlags, builder.mInsertionMarkerHorizontal, builder.mInsertionMarkerTop, builder.mInsertionMarkerBaseline, builder.mInsertionMarkerBottom, sparseRectFArray, builder.mEditorBoundsInfo, fArr, java.util.Arrays.copyOf(builder.mVisibleLineBounds, builder.mVisibleLineBoundsCount), builder.mTextAppearanceInfo);
    }

    private CursorAnchorInfo(int i, int i2, int i3, java.lang.CharSequence charSequence, int i4, float f, float f2, float f3, float f4, android.view.inputmethod.SparseRectFArray sparseRectFArray, android.view.inputmethod.EditorBoundsInfo editorBoundsInfo, float[] fArr, float[] fArr2, android.view.inputmethod.TextAppearanceInfo textAppearanceInfo) {
        this.mSelectionStart = i;
        this.mSelectionEnd = i2;
        this.mComposingTextStart = i3;
        this.mComposingText = charSequence;
        this.mInsertionMarkerFlags = i4;
        this.mInsertionMarkerHorizontal = f;
        this.mInsertionMarkerTop = f2;
        this.mInsertionMarkerBaseline = f3;
        this.mInsertionMarkerBottom = f4;
        this.mCharacterBoundsArray = sparseRectFArray;
        this.mEditorBoundsInfo = editorBoundsInfo;
        this.mMatrixValues = fArr;
        this.mVisibleLineBounds = fArr2;
        this.mTextAppearanceInfo = textAppearanceInfo;
        this.mHashCode = (java.util.Objects.hashCode(this.mComposingText) * 31) + java.util.Arrays.hashCode(this.mMatrixValues);
    }

    public static android.view.inputmethod.CursorAnchorInfo createForAdditionalParentMatrix(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, android.graphics.Matrix matrix) {
        return new android.view.inputmethod.CursorAnchorInfo(cursorAnchorInfo.mSelectionStart, cursorAnchorInfo.mSelectionEnd, cursorAnchorInfo.mComposingTextStart, cursorAnchorInfo.mComposingText, cursorAnchorInfo.mInsertionMarkerFlags, cursorAnchorInfo.mInsertionMarkerHorizontal, cursorAnchorInfo.mInsertionMarkerTop, cursorAnchorInfo.mInsertionMarkerBaseline, cursorAnchorInfo.mInsertionMarkerBottom, cursorAnchorInfo.mCharacterBoundsArray, cursorAnchorInfo.mEditorBoundsInfo, computeMatrixValues(matrix, cursorAnchorInfo), cursorAnchorInfo.mVisibleLineBounds, cursorAnchorInfo.mTextAppearanceInfo);
    }

    private static float[] computeMatrixValues(android.graphics.Matrix matrix, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        if (matrix.isIdentity()) {
            return cursorAnchorInfo.mMatrixValues;
        }
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        matrix2.setValues(cursorAnchorInfo.mMatrixValues);
        matrix2.postConcat(matrix);
        float[] fArr = new float[9];
        matrix2.getValues(fArr);
        return fArr;
    }

    public int getSelectionStart() {
        return this.mSelectionStart;
    }

    public int getSelectionEnd() {
        return this.mSelectionEnd;
    }

    public int getComposingTextStart() {
        return this.mComposingTextStart;
    }

    public java.lang.CharSequence getComposingText() {
        return this.mComposingText;
    }

    public int getInsertionMarkerFlags() {
        return this.mInsertionMarkerFlags;
    }

    public float getInsertionMarkerHorizontal() {
        return this.mInsertionMarkerHorizontal;
    }

    public float getInsertionMarkerTop() {
        return this.mInsertionMarkerTop;
    }

    public float getInsertionMarkerBaseline() {
        return this.mInsertionMarkerBaseline;
    }

    public float getInsertionMarkerBottom() {
        return this.mInsertionMarkerBottom;
    }

    public android.graphics.RectF getCharacterBounds(int i) {
        if (this.mCharacterBoundsArray == null) {
            return null;
        }
        return this.mCharacterBoundsArray.get(i);
    }

    public int getCharacterBoundsFlags(int i) {
        if (this.mCharacterBoundsArray == null) {
            return 0;
        }
        return this.mCharacterBoundsArray.getFlags(i, 0);
    }

    public java.util.List<android.graphics.RectF> getVisibleLineBounds() {
        if (this.mVisibleLineBounds == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mVisibleLineBounds.length / 4);
        int i = 0;
        while (i < this.mVisibleLineBounds.length) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            int i4 = i3 + 1;
            arrayList.add(new android.graphics.RectF(this.mVisibleLineBounds[i], this.mVisibleLineBounds[i2], this.mVisibleLineBounds[i3], this.mVisibleLineBounds[i4]));
            i = i4 + 1;
        }
        return arrayList;
    }

    public android.view.inputmethod.EditorBoundsInfo getEditorBoundsInfo() {
        return this.mEditorBoundsInfo;
    }

    public android.view.inputmethod.TextAppearanceInfo getTextAppearanceInfo() {
        return this.mTextAppearanceInfo;
    }

    public android.graphics.Matrix getMatrix() {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setValues(this.mMatrixValues);
        return matrix;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
