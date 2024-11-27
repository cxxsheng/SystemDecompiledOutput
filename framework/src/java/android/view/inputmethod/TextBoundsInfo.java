package android.view.inputmethod;

/* loaded from: classes4.dex */
public final class TextBoundsInfo implements android.os.Parcelable {
    private static final int BIDI_LEVEL_MASK = 66584576;
    private static final int BIDI_LEVEL_SHIFT = 19;
    public static final android.os.Parcelable.Creator<android.view.inputmethod.TextBoundsInfo> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.TextBoundsInfo>() { // from class: android.view.inputmethod.TextBoundsInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextBoundsInfo createFromParcel(android.os.Parcel parcel) {
            return new android.view.inputmethod.TextBoundsInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.TextBoundsInfo[] newArray(int i) {
            return new android.view.inputmethod.TextBoundsInfo[i];
        }
    };
    public static final int FLAG_CHARACTER_LINEFEED = 2;
    public static final int FLAG_CHARACTER_PUNCTUATION = 4;
    public static final int FLAG_CHARACTER_WHITESPACE = 1;
    private static final int FLAG_GRAPHEME_SEGMENT_END = 67108864;
    private static final int FLAG_GRAPHEME_SEGMENT_START = 134217728;
    public static final int FLAG_LINE_IS_RTL = 8;
    private static final int FLAG_LINE_SEGMENT_END = 1073741824;
    private static final int FLAG_LINE_SEGMENT_START = Integer.MIN_VALUE;
    private static final int FLAG_WORD_SEGMENT_END = 268435456;
    private static final int FLAG_WORD_SEGMENT_START = 536870912;
    private static final int KNOWN_CHARACTER_FLAGS = 15;
    private static final java.lang.String TEXT_BOUNDS_INFO_KEY = "android.view.inputmethod.TextBoundsInfo";
    private final float[] mCharacterBounds;
    private final int mEnd;
    private final android.text.SegmentFinder mGraphemeSegmentFinder;
    private final int[] mInternalCharacterFlags;
    private final android.text.SegmentFinder mLineSegmentFinder;
    private final float[] mMatrixValues;
    private final int mStart;
    private final android.text.SegmentFinder mWordSegmentFinder;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CharacterFlags {
    }

    public void getMatrix(android.graphics.Matrix matrix) {
        java.util.Objects.requireNonNull(matrix);
        matrix.setValues(this.mMatrixValues);
    }

    public int getStartIndex() {
        return this.mStart;
    }

    public int getEndIndex() {
        return this.mEnd;
    }

    public void getCharacterBounds(int i, android.graphics.RectF rectF) {
        if (i < this.mStart || i >= this.mEnd) {
            throw new java.lang.IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        int i2 = (i - this.mStart) * 4;
        rectF.set(this.mCharacterBounds[i2], this.mCharacterBounds[i2 + 1], this.mCharacterBounds[i2 + 2], this.mCharacterBounds[i2 + 3]);
    }

    public int getCharacterFlags(int i) {
        if (i < this.mStart || i >= this.mEnd) {
            throw new java.lang.IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        return this.mInternalCharacterFlags[i - this.mStart] & 15;
    }

    public int getCharacterBidiLevel(int i) {
        if (i < this.mStart || i >= this.mEnd) {
            throw new java.lang.IndexOutOfBoundsException("Index is out of the bounds of [" + this.mStart + ", " + this.mEnd + ").");
        }
        return (this.mInternalCharacterFlags[i - this.mStart] & BIDI_LEVEL_MASK) >> 19;
    }

    public android.text.SegmentFinder getWordSegmentFinder() {
        return this.mWordSegmentFinder;
    }

    public android.text.SegmentFinder getGraphemeSegmentFinder() {
        return this.mGraphemeSegmentFinder;
    }

    public android.text.SegmentFinder getLineSegmentFinder() {
        return this.mLineSegmentFinder;
    }

    public int getOffsetForPosition(float f, float f2) {
        int i;
        int[] iArr = new int[2];
        android.graphics.RectF rectF = new android.graphics.RectF();
        getLineInfo(f2, iArr, rectF);
        if (iArr[0] == -1 || iArr[1] == -1) {
            return -1;
        }
        int i2 = iArr[0];
        int i3 = iArr[1];
        if ((2 & getCharacterFlags(i3 + (-1))) != 0) {
            i = i3;
        } else {
            i = i3 + 1;
        }
        int nextEndBoundary = this.mGraphemeSegmentFinder.nextEndBoundary(i2);
        if (nextEndBoundary == -1) {
            return -1;
        }
        int previousStartBoundary = this.mGraphemeSegmentFinder.previousStartBoundary(nextEndBoundary);
        float f3 = Float.MAX_VALUE;
        int i4 = -1;
        while (previousStartBoundary != -1 && previousStartBoundary < i) {
            if (previousStartBoundary >= i2) {
                float abs = java.lang.Math.abs(getCursorHorizontalPosition(previousStartBoundary, i2, i3, rectF.left, rectF.right) - f);
                if (abs < f3) {
                    i4 = previousStartBoundary;
                    f3 = abs;
                }
            }
            previousStartBoundary = this.mGraphemeSegmentFinder.nextStartBoundary(previousStartBoundary);
        }
        return i4;
    }

    private boolean primaryIsTrailingPrevious(int i, int i2, int i3) {
        int i4;
        int i5;
        if (i < i3) {
            i4 = getCharacterBidiLevel(i);
        } else {
            i4 = (getCharacterFlags(i + (-1)) & 8) == 8 ? 1 : 0;
        }
        if (i > i2) {
            i5 = getCharacterBidiLevel(i - 1);
        } else {
            i5 = (getCharacterFlags(i) & 8) == 8 ? 1 : 0;
        }
        return i5 < i4;
    }

    private float getCursorHorizontalPosition(int i, int i2, int i3, float f, float f2) {
        boolean z;
        com.android.internal.util.Preconditions.checkArgumentInRange(i, i2, i3, android.graphics.FontListParser.ATTR_INDEX);
        boolean z2 = (getCharacterFlags(i2) & 8) != 0;
        if (primaryIsTrailingPrevious(i, i2, i3)) {
            if (i <= i2) {
                return z2 ? f2 : f;
            }
            i--;
            z = false;
        } else {
            if (i >= i3) {
                return z2 ? f : f2;
            }
            z = true;
        }
        boolean z3 = (getCharacterBidiLevel(i) & 1) != 0;
        int i4 = i - this.mStart;
        return z3 != z ? this.mCharacterBounds[i4 * 4] : this.mCharacterBounds[(i4 * 4) + 2];
    }

    private void getBoundsForRange(int i, int i2, android.graphics.RectF rectF) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, this.mStart, this.mEnd - 1, "start");
        com.android.internal.util.Preconditions.checkArgumentInRange(i2, i, this.mEnd, "end");
        if (i2 <= i) {
            rectF.setEmpty();
            return;
        }
        rectF.left = Float.MAX_VALUE;
        rectF.top = Float.MAX_VALUE;
        rectF.right = Float.MIN_VALUE;
        rectF.bottom = Float.MIN_VALUE;
        while (i < i2) {
            int i3 = (i - this.mStart) * 4;
            rectF.left = java.lang.Math.min(rectF.left, this.mCharacterBounds[i3]);
            rectF.top = java.lang.Math.min(rectF.top, this.mCharacterBounds[i3 + 1]);
            rectF.right = java.lang.Math.max(rectF.right, this.mCharacterBounds[i3 + 2]);
            rectF.bottom = java.lang.Math.max(rectF.bottom, this.mCharacterBounds[i3 + 3]);
            i++;
        }
    }

    private void getLineInfo(float f, int[] iArr, android.graphics.RectF rectF) {
        iArr[0] = -1;
        iArr[1] = -1;
        int nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (nextEndBoundary == -1) {
            return;
        }
        int previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(nextEndBoundary);
        android.graphics.RectF rectF2 = new android.graphics.RectF();
        float f2 = Float.MAX_VALUE;
        float f3 = Float.MIN_VALUE;
        float f4 = Float.MAX_VALUE;
        while (previousStartBoundary != -1 && previousStartBoundary < this.mEnd) {
            getBoundsForRange(java.lang.Math.max(this.mStart, previousStartBoundary), java.lang.Math.min(this.mEnd, nextEndBoundary), rectF2);
            f2 = java.lang.Math.min(rectF2.top, f2);
            f3 = java.lang.Math.max(rectF2.bottom, f3);
            float verticalDistance = verticalDistance(rectF2, f);
            if (verticalDistance == 0.0f) {
                iArr[0] = previousStartBoundary;
                iArr[1] = nextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                    return;
                }
                return;
            }
            if (verticalDistance < f4) {
                iArr[0] = previousStartBoundary;
                iArr[1] = nextEndBoundary;
                if (rectF != null) {
                    rectF.set(rectF2);
                }
                f4 = verticalDistance;
            }
            if (f < rectF.top) {
                break;
            }
            previousStartBoundary = this.mLineSegmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(nextEndBoundary);
        }
        if (f < f2 || f > f3) {
            iArr[0] = -1;
            iArr[1] = -1;
            if (rectF != null) {
                rectF.setEmpty();
            }
        }
    }

    public int[] getRangeForRect(android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int previousStartBoundary;
        int nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(this.mStart);
        if (nextEndBoundary == -1) {
            return null;
        }
        int previousStartBoundary2 = this.mLineSegmentFinder.previousStartBoundary(nextEndBoundary);
        int i = -1;
        while (previousStartBoundary2 != -1 && i == -1) {
            i = getStartForRectWithinLine(previousStartBoundary2, nextEndBoundary, rectF, segmentFinder, textInclusionStrategy);
            previousStartBoundary2 = this.mLineSegmentFinder.nextStartBoundary(previousStartBoundary2);
            nextEndBoundary = this.mLineSegmentFinder.nextEndBoundary(nextEndBoundary);
        }
        if (i == -1 || (previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(this.mEnd)) == -1) {
            return null;
        }
        int nextEndBoundary2 = this.mLineSegmentFinder.nextEndBoundary(previousStartBoundary);
        int i2 = -1;
        while (nextEndBoundary2 > i && i2 == -1) {
            i2 = getEndForRectWithinLine(previousStartBoundary, nextEndBoundary2, rectF, segmentFinder, textInclusionStrategy);
            previousStartBoundary = this.mLineSegmentFinder.previousStartBoundary(previousStartBoundary);
            nextEndBoundary2 = this.mLineSegmentFinder.previousEndBoundary(nextEndBoundary2);
        }
        return new int[]{segmentFinder.previousStartBoundary(i + 1), segmentFinder.nextEndBoundary(i2 - 1)};
    }

    private int getStartForRectWithinLine(int i, int i2, android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        if (i >= i2) {
            return -1;
        }
        int i3 = i;
        int i4 = -1;
        while (i < i2) {
            int characterBidiLevel = getCharacterBidiLevel(i);
            if (characterBidiLevel != i4) {
                int startForRectWithinRun = getStartForRectWithinRun(i3, i, rectF, segmentFinder, textInclusionStrategy);
                if (startForRectWithinRun != -1) {
                    return startForRectWithinRun;
                }
                i3 = i;
                i4 = characterBidiLevel;
            }
            i++;
        }
        return getStartForRectWithinRun(i3, i2, rectF, segmentFinder, textInclusionStrategy);
    }

    private int getStartForRectWithinRun(int i, int i2, android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int nextEndBoundary;
        if (i >= i2 || (nextEndBoundary = segmentFinder.nextEndBoundary(i)) == -1) {
            return -1;
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary);
        android.graphics.RectF rectF2 = new android.graphics.RectF();
        while (previousStartBoundary != -1 && previousStartBoundary < i2) {
            int max = java.lang.Math.max(i, previousStartBoundary);
            getBoundsForRange(max, java.lang.Math.min(i2, nextEndBoundary), rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return max;
            }
            previousStartBoundary = segmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.nextEndBoundary(nextEndBoundary);
        }
        return -1;
    }

    private int getEndForRectWithinLine(int i, int i2, android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        if (i >= i2) {
            return -1;
        }
        int max = java.lang.Math.max(i, this.mStart);
        int min = java.lang.Math.min(i2, this.mEnd);
        int i3 = min;
        int i4 = -1;
        for (int i5 = min - 1; i5 >= max; i5--) {
            int characterBidiLevel = getCharacterBidiLevel(i5);
            if (characterBidiLevel != i4) {
                int i6 = i5 + 1;
                int endForRectWithinRun = getEndForRectWithinRun(i6, i3, rectF, segmentFinder, textInclusionStrategy);
                if (endForRectWithinRun != -1) {
                    return endForRectWithinRun;
                }
                i3 = i6;
                i4 = characterBidiLevel;
            }
        }
        return getEndForRectWithinRun(max, i3, rectF, segmentFinder, textInclusionStrategy);
    }

    private int getEndForRectWithinRun(int i, int i2, android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int previousStartBoundary;
        if (i >= i2 || (previousStartBoundary = segmentFinder.previousStartBoundary(i2)) == -1) {
            return -1;
        }
        int nextEndBoundary = segmentFinder.nextEndBoundary(previousStartBoundary);
        android.graphics.RectF rectF2 = new android.graphics.RectF();
        while (nextEndBoundary != -1 && nextEndBoundary > i) {
            int max = java.lang.Math.max(i, previousStartBoundary);
            int min = java.lang.Math.min(i2, nextEndBoundary);
            getBoundsForRange(max, min, rectF2);
            if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                return min;
            }
            previousStartBoundary = segmentFinder.previousStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.previousEndBoundary(nextEndBoundary);
        }
        return -1;
    }

    private static float verticalDistance(android.graphics.RectF rectF, float f) {
        if (rectF.top <= f && f < rectF.bottom) {
            return 0.0f;
        }
        if (f < rectF.top) {
            return rectF.top - f;
        }
        return f - rectF.bottom;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStart);
        parcel.writeInt(this.mEnd);
        parcel.writeFloatArray(this.mMatrixValues);
        parcel.writeFloatArray(this.mCharacterBounds);
        int[] copyOf = java.util.Arrays.copyOf(this.mInternalCharacterFlags, (this.mEnd - this.mStart) + 1);
        encodeSegmentFinder(copyOf, 134217728, 67108864, this.mStart, this.mEnd, this.mGraphemeSegmentFinder);
        encodeSegmentFinder(copyOf, 536870912, 268435456, this.mStart, this.mEnd, this.mWordSegmentFinder);
        encodeSegmentFinder(copyOf, Integer.MIN_VALUE, 1073741824, this.mStart, this.mEnd, this.mLineSegmentFinder);
        parcel.writeIntArray(copyOf);
    }

    private TextBoundsInfo(android.os.Parcel parcel) {
        this.mStart = parcel.readInt();
        this.mEnd = parcel.readInt();
        this.mMatrixValues = (float[]) java.util.Objects.requireNonNull(parcel.createFloatArray());
        this.mCharacterBounds = (float[]) java.util.Objects.requireNonNull(parcel.createFloatArray());
        int[] iArr = (int[]) java.util.Objects.requireNonNull(parcel.createIntArray());
        this.mGraphemeSegmentFinder = decodeSegmentFinder(iArr, 134217728, 67108864, this.mStart, this.mEnd);
        this.mWordSegmentFinder = decodeSegmentFinder(iArr, 536870912, 268435456, this.mStart, this.mEnd);
        this.mLineSegmentFinder = decodeSegmentFinder(iArr, Integer.MIN_VALUE, 1073741824, this.mStart, this.mEnd);
        int i = this.mEnd - this.mStart;
        this.mInternalCharacterFlags = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mInternalCharacterFlags[i2] = iArr[i2] & 66584591;
        }
    }

    private TextBoundsInfo(android.view.inputmethod.TextBoundsInfo.Builder builder) {
        this.mStart = builder.mStart;
        this.mEnd = builder.mEnd;
        this.mMatrixValues = java.util.Arrays.copyOf(builder.mMatrixValues, 9);
        int i = this.mEnd - this.mStart;
        this.mCharacterBounds = java.util.Arrays.copyOf(builder.mCharacterBounds, i * 4);
        this.mInternalCharacterFlags = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mInternalCharacterFlags[i2] = builder.mCharacterFlags[i2] | (builder.mCharacterBidiLevels[i2] << 19);
        }
        this.mGraphemeSegmentFinder = builder.mGraphemeSegmentFinder;
        this.mWordSegmentFinder = builder.mWordSegmentFinder;
        this.mLineSegmentFinder = builder.mLineSegmentFinder;
    }

    public android.os.Bundle toBundle() {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(TEXT_BOUNDS_INFO_KEY, this);
        return bundle;
    }

    public static android.view.inputmethod.TextBoundsInfo createFromBundle(android.os.Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        return (android.view.inputmethod.TextBoundsInfo) bundle.getParcelable(TEXT_BOUNDS_INFO_KEY, android.view.inputmethod.TextBoundsInfo.class);
    }

    public static final class Builder {
        private int[] mCharacterBidiLevels;
        private float[] mCharacterBounds;
        private int[] mCharacterFlags;
        private android.text.SegmentFinder mGraphemeSegmentFinder;
        private android.text.SegmentFinder mLineSegmentFinder;
        private boolean mMatrixInitialized;
        private android.text.SegmentFinder mWordSegmentFinder;
        private final float[] mMatrixValues = new float[9];
        private int mStart = -1;
        private int mEnd = -1;

        public Builder(int i, int i2) {
            setStartAndEnd(i, i2);
        }

        public android.view.inputmethod.TextBoundsInfo.Builder clear() {
            this.mMatrixInitialized = false;
            this.mStart = -1;
            this.mEnd = -1;
            this.mCharacterBounds = null;
            this.mCharacterFlags = null;
            this.mCharacterBidiLevels = null;
            this.mLineSegmentFinder = null;
            this.mWordSegmentFinder = null;
            this.mGraphemeSegmentFinder = null;
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setMatrix(android.graphics.Matrix matrix) {
            ((android.graphics.Matrix) java.util.Objects.requireNonNull(matrix)).getValues(this.mMatrixValues);
            this.mMatrixInitialized = true;
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setStartAndEnd(int i, int i2) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0);
            com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, i2, "start");
            this.mStart = i;
            this.mEnd = i2;
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setCharacterBounds(float[] fArr) {
            this.mCharacterBounds = (float[]) java.util.Objects.requireNonNull(fArr);
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setCharacterFlags(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            for (int i : iArr) {
                if ((i & (-16)) != 0) {
                    throw new java.lang.IllegalArgumentException("characterFlags contains invalid flags.");
                }
            }
            this.mCharacterFlags = iArr;
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setCharacterBidiLevel(int[] iArr) {
            java.util.Objects.requireNonNull(iArr);
            for (int i = 0; i < iArr.length; i++) {
                com.android.internal.util.Preconditions.checkArgumentInRange(iArr[i], 0, 125, "bidiLevels[" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
            this.mCharacterBidiLevels = iArr;
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setGraphemeSegmentFinder(android.text.SegmentFinder segmentFinder) {
            this.mGraphemeSegmentFinder = (android.text.SegmentFinder) java.util.Objects.requireNonNull(segmentFinder);
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setWordSegmentFinder(android.text.SegmentFinder segmentFinder) {
            this.mWordSegmentFinder = (android.text.SegmentFinder) java.util.Objects.requireNonNull(segmentFinder);
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo.Builder setLineSegmentFinder(android.text.SegmentFinder segmentFinder) {
            this.mLineSegmentFinder = (android.text.SegmentFinder) java.util.Objects.requireNonNull(segmentFinder);
            return this;
        }

        public android.view.inputmethod.TextBoundsInfo build() {
            if (this.mStart < 0 || this.mEnd < 0) {
                throw new java.lang.IllegalStateException("Start and end must be set.");
            }
            if (!this.mMatrixInitialized) {
                throw new java.lang.IllegalStateException("Matrix must be set.");
            }
            if (this.mCharacterBounds == null) {
                throw new java.lang.IllegalStateException("CharacterBounds must be set.");
            }
            if (this.mCharacterFlags == null) {
                throw new java.lang.IllegalStateException("CharacterFlags must be set.");
            }
            if (this.mCharacterBidiLevels == null) {
                throw new java.lang.IllegalStateException("CharacterBidiLevel must be set.");
            }
            if (this.mCharacterBounds.length != (this.mEnd - this.mStart) * 4) {
                throw new java.lang.IllegalStateException("The length of characterBounds doesn't match the length of the given start and end. Expected length: " + ((this.mEnd - this.mStart) * 4) + " characterBounds length: " + this.mCharacterBounds.length);
            }
            if (this.mCharacterFlags.length != this.mEnd - this.mStart) {
                throw new java.lang.IllegalStateException("The length of characterFlags doesn't match the length of the given start and end. Expected length: " + (this.mEnd - this.mStart) + " characterFlags length: " + this.mCharacterFlags.length);
            }
            if (this.mCharacterBidiLevels.length != this.mEnd - this.mStart) {
                throw new java.lang.IllegalStateException("The length of characterBidiLevels doesn't match the length of the given start and end. Expected length: " + (this.mEnd - this.mStart) + " characterFlags length: " + this.mCharacterBidiLevels.length);
            }
            if (this.mGraphemeSegmentFinder == null) {
                throw new java.lang.IllegalStateException("GraphemeSegmentFinder must be set.");
            }
            if (this.mWordSegmentFinder == null) {
                throw new java.lang.IllegalStateException("WordSegmentFinder must be set.");
            }
            if (this.mLineSegmentFinder == null) {
                throw new java.lang.IllegalStateException("LineSegmentFinder must be set.");
            }
            if (!android.view.inputmethod.TextBoundsInfo.isLineDirectionFlagConsistent(this.mCharacterFlags, this.mLineSegmentFinder, this.mStart, this.mEnd)) {
                throw new java.lang.IllegalStateException("characters in the same line must have the same FLAG_LINE_IS_RTL flag value.");
            }
            return new android.view.inputmethod.TextBoundsInfo(this);
        }
    }

    private static void encodeSegmentFinder(int[] iArr, int i, int i2, int i3, int i4, android.text.SegmentFinder segmentFinder) {
        if ((i4 - i3) + 1 != iArr.length) {
            throw new java.lang.IllegalStateException("The given flags array must have the same length as the given range. flags length: " + iArr.length + " range: [" + i3 + ", " + i4 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        int nextEndBoundary = segmentFinder.nextEndBoundary(i3);
        if (nextEndBoundary == -1) {
            return;
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary);
        while (nextEndBoundary != -1 && nextEndBoundary <= i4) {
            if (previousStartBoundary >= i3) {
                int i5 = previousStartBoundary - i3;
                iArr[i5] = iArr[i5] | i;
                int i6 = nextEndBoundary - i3;
                iArr[i6] = iArr[i6] | i2;
            }
            previousStartBoundary = segmentFinder.nextStartBoundary(previousStartBoundary);
            nextEndBoundary = segmentFinder.nextEndBoundary(nextEndBoundary);
        }
    }

    private static android.text.SegmentFinder decodeSegmentFinder(int[] iArr, int i, int i2, int i3, int i4) {
        if ((i4 - i3) + 1 != iArr.length) {
            throw new java.lang.IllegalStateException("The given flags array must have the same length as the given range. flags length: " + iArr.length + " range: [" + i3 + ", " + i4 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(10);
        int i5 = 0;
        for (int i6 = 0; i6 < iArr.length; i6++) {
            if ((iArr[i6] & i) == i) {
                newUnpaddedIntArray = com.android.internal.util.GrowingArrayUtils.append(newUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
            if ((iArr[i6] & i2) == i2) {
                newUnpaddedIntArray = com.android.internal.util.GrowingArrayUtils.append(newUnpaddedIntArray, i5, i3 + i6);
                i5++;
            }
        }
        return new android.text.SegmentFinder.PrescribedSegmentFinder(java.util.Arrays.copyOf(newUnpaddedIntArray, i5));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0037, code lost:
    
        r3 = r10.nextStartBoundary(r3);
        r0 = r10.nextEndBoundary(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isLineDirectionFlagConsistent(int[] iArr, android.text.SegmentFinder segmentFinder, int i, int i2) {
        int nextEndBoundary = segmentFinder.nextEndBoundary(i);
        if (nextEndBoundary == -1) {
            return true;
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary);
        while (previousStartBoundary != -1 && previousStartBoundary < i2) {
            int max = java.lang.Math.max(previousStartBoundary, i);
            int min = java.lang.Math.min(nextEndBoundary, i2);
            boolean z = (iArr[max - i] & 8) != 0;
            do {
                max++;
                if (max < min) {
                }
            } while (((iArr[max - i] & 8) != 0) == z);
            return false;
        }
        return true;
    }
}
