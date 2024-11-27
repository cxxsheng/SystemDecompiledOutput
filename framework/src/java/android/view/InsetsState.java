package android.view;

/* loaded from: classes4.dex */
public class InsetsState implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.InsetsState> CREATOR = new android.os.Parcelable.Creator<android.view.InsetsState>() { // from class: android.view.InsetsState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsState createFromParcel(android.os.Parcel parcel) {
            return new android.view.InsetsState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.InsetsState[] newArray(int i) {
            return new android.view.InsetsState[i];
        }
    };
    private final android.view.DisplayCutout.ParcelableWrapper mDisplayCutout;
    private final android.graphics.Rect mDisplayFrame;
    private android.view.DisplayShape mDisplayShape;
    private android.view.PrivacyIndicatorBounds mPrivacyIndicatorBounds;
    private final android.graphics.Rect mRoundedCornerFrame;
    private android.view.RoundedCorners mRoundedCorners;
    private final android.util.SparseArray<android.view.InsetsSource> mSources;

    public InsetsState() {
        this.mDisplayFrame = new android.graphics.Rect();
        this.mDisplayCutout = new android.view.DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new android.graphics.Rect();
        this.mRoundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
        this.mDisplayShape = android.view.DisplayShape.NONE;
        this.mSources = new android.util.SparseArray<>();
    }

    public InsetsState(android.view.InsetsState insetsState) {
        this(insetsState, false);
    }

    public InsetsState(android.view.InsetsState insetsState, boolean z) {
        this.mDisplayFrame = new android.graphics.Rect();
        this.mDisplayCutout = new android.view.DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new android.graphics.Rect();
        this.mRoundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
        this.mDisplayShape = android.view.DisplayShape.NONE;
        this.mSources = new android.util.SparseArray<>(insetsState.mSources.size());
        set(insetsState, z);
    }

    public android.view.WindowInsets calculateInsets(android.graphics.Rect rect, android.view.InsetsState insetsState, boolean z, int i, int i2, int i3, int i4, int i5, android.util.SparseIntArray sparseIntArray) {
        int i6;
        int i7;
        int i8;
        android.view.InsetsSource insetsSource;
        android.view.InsetsState insetsState2 = this;
        android.graphics.Insets[] insetsArr = new android.graphics.Insets[10];
        android.graphics.Insets[] insetsArr2 = new android.graphics.Insets[10];
        boolean[] zArr = new boolean[10];
        android.graphics.Rect rect2 = new android.graphics.Rect(rect);
        android.graphics.Rect rect3 = new android.graphics.Rect(rect);
        android.graphics.Rect[][] rectArr = new android.graphics.Rect[10][];
        android.graphics.Rect[][] rectArr2 = new android.graphics.Rect[10][];
        int size = insetsState2.mSources.size() - 1;
        int i9 = 0;
        int i10 = 0;
        while (size >= 0) {
            android.view.InsetsSource valueAt = insetsState2.mSources.valueAt(size);
            int type = valueAt.getType();
            if ((valueAt.getFlags() & 4) == 0) {
                i7 = i9;
            } else {
                i7 = i9 | type;
            }
            if ((valueAt.getFlags() & 1) == 0) {
                i8 = i10;
            } else {
                i8 = i10 | type;
            }
            int i11 = size;
            android.graphics.Rect[][] rectArr3 = rectArr2;
            android.graphics.Rect[][] rectArr4 = rectArr;
            android.graphics.Rect rect4 = rect3;
            processSource(valueAt, rect2, false, insetsArr, sparseIntArray, zArr, rectArr4);
            if (type != android.view.WindowInsets.Type.ime()) {
                if (insetsState != null) {
                    insetsSource = insetsState.peekSource(valueAt.getId());
                } else {
                    insetsSource = valueAt;
                }
                if (insetsSource != null) {
                    processSource(insetsSource, rect4, true, insetsArr2, null, null, rectArr3);
                }
            }
            size = i11 - 1;
            insetsState2 = this;
            i9 = i7;
            i10 = i8;
            rect3 = rect4;
            rectArr = rectArr4;
            rectArr2 = rectArr3;
        }
        android.graphics.Rect[][] rectArr5 = rectArr2;
        android.graphics.Rect[][] rectArr6 = rectArr;
        int i12 = i & 240;
        int systemBars = android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.displayCutout();
        if (i12 == 16) {
            systemBars |= android.view.WindowInsets.Type.ime();
        }
        if ((i2 & 1024) != 0) {
            systemBars &= ~android.view.WindowInsets.Type.statusBars();
        }
        if (!clearsCompatInsets(i4, i2, i5, i9)) {
            i6 = systemBars;
        } else {
            i6 = 0;
        }
        return new android.view.WindowInsets(insetsArr, insetsArr2, zArr, z, i9, i10, calculateRelativeCutout(rect), calculateRelativeRoundedCorners(rect), calculateRelativePrivacyIndicatorBounds(rect), calculateRelativeDisplayShape(rect), i6, (i3 & 256) != 0, rectArr6, rectArr5, rect.width(), rect.height());
    }

    private android.view.DisplayCutout calculateRelativeCutout(android.graphics.Rect rect) {
        android.view.DisplayCutout displayCutout = this.mDisplayCutout.get();
        if (this.mDisplayFrame.equals(rect)) {
            return displayCutout;
        }
        if (rect == null) {
            return android.view.DisplayCutout.NO_CUTOUT;
        }
        int i = rect.left - this.mDisplayFrame.left;
        int i2 = rect.top - this.mDisplayFrame.top;
        int i3 = this.mDisplayFrame.right - rect.right;
        int i4 = this.mDisplayFrame.bottom - rect.bottom;
        if (i >= displayCutout.getSafeInsetLeft() && i2 >= displayCutout.getSafeInsetTop() && i3 >= displayCutout.getSafeInsetRight() && i4 >= displayCutout.getSafeInsetBottom()) {
            return android.view.DisplayCutout.NO_CUTOUT;
        }
        return displayCutout.inset(i, i2, i3, i4);
    }

    private android.view.RoundedCorners calculateRelativeRoundedCorners(android.graphics.Rect rect) {
        if (rect == null) {
            return android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        }
        android.graphics.Rect rect2 = new android.graphics.Rect(this.mRoundedCornerFrame);
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            if (valueAt.hasFlags(2)) {
                rect2.inset(valueAt.calculateInsets(rect2, false));
            }
        }
        if (!rect2.isEmpty() && !rect2.equals(this.mDisplayFrame)) {
            return this.mRoundedCorners.insetWithFrame(rect, rect2);
        }
        if (this.mDisplayFrame.equals(rect)) {
            return this.mRoundedCorners;
        }
        return this.mRoundedCorners.inset(rect.left - this.mDisplayFrame.left, rect.top - this.mDisplayFrame.top, this.mDisplayFrame.right - rect.right, this.mDisplayFrame.bottom - rect.bottom);
    }

    private android.view.PrivacyIndicatorBounds calculateRelativePrivacyIndicatorBounds(android.graphics.Rect rect) {
        if (this.mDisplayFrame.equals(rect)) {
            return this.mPrivacyIndicatorBounds;
        }
        if (rect == null) {
            return null;
        }
        return this.mPrivacyIndicatorBounds.inset(rect.left - this.mDisplayFrame.left, rect.top - this.mDisplayFrame.top, this.mDisplayFrame.right - rect.right, this.mDisplayFrame.bottom - rect.bottom);
    }

    private android.view.DisplayShape calculateRelativeDisplayShape(android.graphics.Rect rect) {
        if (this.mDisplayFrame.equals(rect)) {
            return this.mDisplayShape;
        }
        if (rect == null) {
            return android.view.DisplayShape.NONE;
        }
        return this.mDisplayShape.setOffset(-rect.left, -rect.top);
    }

    public android.graphics.Insets calculateInsets(android.graphics.Rect rect, int i, boolean z) {
        android.graphics.Insets insets = android.graphics.Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & i) != 0) {
                insets = android.graphics.Insets.max(valueAt.calculateInsets(rect, z), insets);
            }
        }
        return insets;
    }

    public android.graphics.Insets calculateInsets(android.graphics.Rect rect, int i, int i2) {
        android.graphics.Insets insets = android.graphics.Insets.NONE;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & i & i2) != 0) {
                insets = android.graphics.Insets.max(valueAt.calculateInsets(rect, true), insets);
            }
        }
        return insets;
    }

    public android.graphics.Insets calculateVisibleInsets(android.graphics.Rect rect, int i, int i2, int i3, int i4) {
        int systemBars;
        if ((i3 & 240) != 48) {
            systemBars = android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.ime();
        } else {
            systemBars = android.view.WindowInsets.Type.systemBars();
        }
        android.graphics.Insets insets = android.graphics.Insets.NONE;
        int i5 = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            if ((valueAt.getType() & systemBars) != 0) {
                if (valueAt.hasFlags(4)) {
                    i5 |= valueAt.getType();
                }
                insets = android.graphics.Insets.max(valueAt.calculateVisibleInsets(rect), insets);
            }
        }
        if (!clearsCompatInsets(i, i4, i2, i5)) {
            return insets;
        }
        return android.graphics.Insets.NONE;
    }

    public int calculateUncontrollableInsetsFromFrame(android.graphics.Rect rect) {
        int i = 0;
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            if (!canControlSource(rect, valueAt)) {
                i |= valueAt.getType();
            }
        }
        return i;
    }

    private static boolean canControlSource(android.graphics.Rect rect, android.view.InsetsSource insetsSource) {
        android.graphics.Insets calculateInsets = insetsSource.calculateInsets(rect, true);
        android.graphics.Rect frame = insetsSource.getFrame();
        int width = frame.width();
        int height = frame.height();
        return calculateInsets.left == width || calculateInsets.right == width || calculateInsets.top == height || calculateInsets.bottom == height;
    }

    private void processSource(android.view.InsetsSource insetsSource, android.graphics.Rect rect, boolean z, android.graphics.Insets[] insetsArr, android.util.SparseIntArray sparseIntArray, boolean[] zArr, android.graphics.Rect[][] rectArr) {
        android.graphics.Insets calculateInsets = insetsSource.calculateInsets(rect, z);
        android.graphics.Rect[] calculateBoundingRects = insetsSource.calculateBoundingRects(rect, z);
        int type = insetsSource.getType();
        processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, calculateInsets, calculateBoundingRects, type);
        if (type == 32) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, calculateInsets, calculateBoundingRects, 16);
        }
        if (type == 4) {
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, calculateInsets, calculateBoundingRects, 16);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, calculateInsets, calculateBoundingRects, 32);
            processSourceAsPublicType(insetsSource, insetsArr, sparseIntArray, zArr, rectArr, calculateInsets, calculateBoundingRects, 64);
        }
    }

    private void processSourceAsPublicType(android.view.InsetsSource insetsSource, android.graphics.Insets[] insetsArr, android.util.SparseIntArray sparseIntArray, boolean[] zArr, android.graphics.Rect[][] rectArr, android.graphics.Insets insets, android.graphics.Rect[] rectArr2, int i) {
        int insetSide;
        int indexOf = android.view.WindowInsets.Type.indexOf(i);
        if (!android.graphics.Insets.NONE.equals(insets)) {
            android.graphics.Insets insets2 = insetsArr[indexOf];
            if (insets2 == null) {
                insetsArr[indexOf] = insets;
            } else {
                insetsArr[indexOf] = android.graphics.Insets.max(insets2, insets);
            }
        }
        if (zArr != null) {
            zArr[indexOf] = insetsSource.isVisible();
        }
        if (sparseIntArray != null && (insetSide = android.view.InsetsSource.getInsetSide(insets)) != 5) {
            sparseIntArray.put(insetsSource.getId(), insetSide);
        }
        if (rectArr != null && rectArr2.length > 0) {
            android.graphics.Rect[] rectArr3 = rectArr[indexOf];
            if (rectArr3 == null) {
                rectArr[indexOf] = rectArr2;
            } else {
                rectArr[indexOf] = concatenate(rectArr3, rectArr2);
            }
        }
    }

    private static android.graphics.Rect[] concatenate(android.graphics.Rect[] rectArr, android.graphics.Rect[] rectArr2) {
        android.graphics.Rect[] rectArr3 = new android.graphics.Rect[rectArr.length + rectArr2.length];
        java.lang.System.arraycopy(rectArr, 0, rectArr3, 0, rectArr.length);
        java.lang.System.arraycopy(rectArr2, 0, rectArr3, rectArr.length, rectArr2.length);
        return rectArr3;
    }

    public android.view.InsetsSource getOrCreateSource(int i, int i2) {
        android.view.InsetsSource insetsSource = this.mSources.get(i);
        if (insetsSource != null) {
            return insetsSource;
        }
        android.view.InsetsSource insetsSource2 = new android.view.InsetsSource(i, i2);
        this.mSources.put(i, insetsSource2);
        return insetsSource2;
    }

    public android.view.InsetsSource peekSource(int i) {
        return this.mSources.get(i);
    }

    public int sourceIdAt(int i) {
        return this.mSources.keyAt(i);
    }

    public android.view.InsetsSource sourceAt(int i) {
        return this.mSources.valueAt(i);
    }

    public int sourceSize() {
        return this.mSources.size();
    }

    public boolean isSourceOrDefaultVisible(int i, int i2) {
        android.view.InsetsSource insetsSource = this.mSources.get(i);
        return insetsSource != null ? insetsSource.isVisible() : (android.view.WindowInsets.Type.defaultVisible() & i2) != 0;
    }

    public void setDisplayFrame(android.graphics.Rect rect) {
        this.mDisplayFrame.set(rect);
    }

    public android.graphics.Rect getDisplayFrame() {
        return this.mDisplayFrame;
    }

    public void setDisplayCutout(android.view.DisplayCutout displayCutout) {
        this.mDisplayCutout.set(displayCutout);
    }

    public android.view.DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout.get();
    }

    public void getDisplayCutoutSafe(android.graphics.Rect rect) {
        rect.set(-100000, -100000, 100000, 100000);
        android.view.DisplayCutout displayCutout = this.mDisplayCutout.get();
        android.graphics.Rect rect2 = this.mDisplayFrame;
        if (!displayCutout.isEmpty()) {
            if (displayCutout.getSafeInsetLeft() > 0) {
                rect.left = rect2.left + displayCutout.getSafeInsetLeft();
            }
            if (displayCutout.getSafeInsetTop() > 0) {
                rect.top = rect2.top + displayCutout.getSafeInsetTop();
            }
            if (displayCutout.getSafeInsetRight() > 0) {
                rect.right = rect2.right - displayCutout.getSafeInsetRight();
            }
            if (displayCutout.getSafeInsetBottom() > 0) {
                rect.bottom = rect2.bottom - displayCutout.getSafeInsetBottom();
            }
        }
    }

    public void setRoundedCorners(android.view.RoundedCorners roundedCorners) {
        this.mRoundedCorners = roundedCorners;
    }

    public android.view.RoundedCorners getRoundedCorners() {
        return this.mRoundedCorners;
    }

    public void setRoundedCornerFrame(android.graphics.Rect rect) {
        this.mRoundedCornerFrame.set(rect);
    }

    public void setPrivacyIndicatorBounds(android.view.PrivacyIndicatorBounds privacyIndicatorBounds) {
        this.mPrivacyIndicatorBounds = privacyIndicatorBounds;
    }

    public android.view.PrivacyIndicatorBounds getPrivacyIndicatorBounds() {
        return this.mPrivacyIndicatorBounds;
    }

    public void setDisplayShape(android.view.DisplayShape displayShape) {
        this.mDisplayShape = displayShape;
    }

    public android.view.DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    public void removeSource(int i) {
        this.mSources.delete(i);
    }

    public void removeSourceAt(int i) {
        this.mSources.removeAt(i);
    }

    public void setSourceVisible(int i, boolean z) {
        android.view.InsetsSource insetsSource = this.mSources.get(i);
        if (insetsSource != null) {
            insetsSource.setVisible(z);
        }
    }

    public void scale(float f) {
        this.mDisplayFrame.scale(f);
        this.mDisplayCutout.scale(f);
        this.mRoundedCorners = this.mRoundedCorners.scale(f);
        this.mRoundedCornerFrame.scale(f);
        this.mPrivacyIndicatorBounds = this.mPrivacyIndicatorBounds.scale(f);
        this.mDisplayShape = this.mDisplayShape.setScale(f);
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            android.view.InsetsSource valueAt = this.mSources.valueAt(size);
            valueAt.getFrame().scale(f);
            android.graphics.Rect visibleFrame = valueAt.getVisibleFrame();
            if (visibleFrame != null) {
                visibleFrame.scale(f);
            }
        }
    }

    public void set(android.view.InsetsState insetsState) {
        set(insetsState, false);
    }

    public void set(android.view.InsetsState insetsState, boolean z) {
        this.mDisplayFrame.set(insetsState.mDisplayFrame);
        this.mDisplayCutout.set(insetsState.mDisplayCutout);
        this.mRoundedCorners = insetsState.getRoundedCorners();
        this.mRoundedCornerFrame.set(insetsState.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = insetsState.getPrivacyIndicatorBounds();
        this.mDisplayShape = insetsState.getDisplayShape();
        this.mSources.clear();
        int size = insetsState.mSources.size();
        for (int i = 0; i < size; i++) {
            android.view.InsetsSource valueAt = insetsState.mSources.valueAt(i);
            android.util.SparseArray<android.view.InsetsSource> sparseArray = this.mSources;
            int id = valueAt.getId();
            if (z) {
                valueAt = new android.view.InsetsSource(valueAt);
            }
            sparseArray.append(id, valueAt);
        }
    }

    public void set(android.view.InsetsState insetsState, int i) {
        this.mDisplayFrame.set(insetsState.mDisplayFrame);
        this.mDisplayCutout.set(insetsState.mDisplayCutout);
        this.mRoundedCorners = insetsState.getRoundedCorners();
        this.mRoundedCornerFrame.set(insetsState.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = insetsState.getPrivacyIndicatorBounds();
        this.mDisplayShape = insetsState.getDisplayShape();
        if (i == 0) {
            return;
        }
        for (int size = this.mSources.size() - 1; size >= 0; size--) {
            if ((this.mSources.valueAt(size).getType() & i) != 0) {
                this.mSources.removeAt(size);
            }
        }
        for (int size2 = insetsState.mSources.size() - 1; size2 >= 0; size2--) {
            android.view.InsetsSource valueAt = insetsState.mSources.valueAt(size2);
            if ((valueAt.getType() & i) != 0) {
                this.mSources.put(valueAt.getId(), valueAt);
            }
        }
    }

    public void addSource(android.view.InsetsSource insetsSource) {
        this.mSources.put(insetsSource.getId(), insetsSource);
    }

    public static boolean clearsCompatInsets(int i, int i2, int i3, int i4) {
        return ((i2 & 512) == 0 || i == 2013 || i == 2010 || (i4 != 0 && i3 == 1)) ? false : true;
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        java.lang.String str2 = str + "  ";
        printWriter.println(str + "InsetsState");
        printWriter.println(str2 + "mDisplayFrame=" + this.mDisplayFrame);
        printWriter.println(str2 + "mDisplayCutout=" + this.mDisplayCutout.get());
        printWriter.println(str2 + "mRoundedCorners=" + this.mRoundedCorners);
        printWriter.println(str2 + "mRoundedCornerFrame=" + this.mRoundedCornerFrame);
        printWriter.println(str2 + "mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds);
        printWriter.println(str2 + "mDisplayShape=" + this.mDisplayShape);
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            this.mSources.valueAt(i).dump(str2 + "  ", printWriter);
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        android.view.InsetsSource insetsSource = this.mSources.get(android.view.InsetsSource.ID_IME);
        if (insetsSource != null) {
            insetsSource.dumpDebug(protoOutputStream, 2246267895809L);
        }
        this.mDisplayFrame.dumpDebug(protoOutputStream, 1146756268034L);
        this.mDisplayCutout.get().dumpDebug(protoOutputStream, 1146756268035L);
        protoOutputStream.end(start);
    }

    public boolean equals(java.lang.Object obj) {
        return equals(obj, false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ac, code lost:
    
        r9 = r13.valueAt(r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean equals(java.lang.Object obj, boolean z, boolean z2) {
        android.view.InsetsSource insetsSource;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.view.InsetsState insetsState = (android.view.InsetsState) obj;
        if (!this.mDisplayFrame.equals(insetsState.mDisplayFrame) || !this.mDisplayCutout.equals(insetsState.mDisplayCutout) || !this.mRoundedCorners.equals(insetsState.mRoundedCorners) || !this.mRoundedCornerFrame.equals(insetsState.mRoundedCornerFrame) || !this.mPrivacyIndicatorBounds.equals(insetsState.mPrivacyIndicatorBounds) || !this.mDisplayShape.equals(insetsState.mDisplayShape)) {
            return false;
        }
        android.util.SparseArray<android.view.InsetsSource> sparseArray = this.mSources;
        android.util.SparseArray<android.view.InsetsSource> sparseArray2 = insetsState.mSources;
        if (!z && !z2) {
            return sparseArray.contentEquals(sparseArray2);
        }
        int size = sparseArray.size();
        int size2 = sparseArray2.size();
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i >= size && i2 >= size2) {
                return true;
            }
            if (i < size) {
                insetsSource = sparseArray.valueAt(i);
            } else {
                insetsSource = null;
            }
            while (insetsSource != null && ((z && insetsSource.getType() == android.view.WindowInsets.Type.captionBar()) || (z2 && insetsSource.getType() == android.view.WindowInsets.Type.ime() && !insetsSource.isVisible()))) {
                i++;
                insetsSource = i < size ? sparseArray.valueAt(i) : null;
            }
            android.view.InsetsSource insetsSource2 = null;
            while (insetsSource2 != null && ((z && insetsSource2.getType() == android.view.WindowInsets.Type.captionBar()) || (z2 && insetsSource2.getType() == android.view.WindowInsets.Type.ime() && !insetsSource2.isVisible()))) {
                i2++;
                insetsSource2 = i2 < size2 ? sparseArray2.valueAt(i2) : null;
            }
            if (!java.util.Objects.equals(insetsSource, insetsSource2)) {
                return false;
            }
            i++;
            i2++;
        }
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDisplayFrame, this.mDisplayCutout, java.lang.Integer.valueOf(this.mSources.contentHashCode()), this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mRoundedCornerFrame, this.mDisplayShape);
    }

    public InsetsState(android.os.Parcel parcel) {
        this.mDisplayFrame = new android.graphics.Rect();
        this.mDisplayCutout = new android.view.DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new android.graphics.Rect();
        this.mRoundedCorners = android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
        this.mDisplayShape = android.view.DisplayShape.NONE;
        this.mSources = readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        this.mDisplayFrame.writeToParcel(parcel, i);
        this.mDisplayCutout.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mRoundedCorners, i);
        this.mRoundedCornerFrame.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mPrivacyIndicatorBounds, i);
        parcel.writeTypedObject(this.mDisplayShape, i);
        int size = this.mSources.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeTypedObject(this.mSources.valueAt(i2), i);
        }
    }

    public android.util.SparseArray<android.view.InsetsSource> readFromParcel(android.os.Parcel parcel) {
        android.util.SparseArray<android.view.InsetsSource> sparseArray;
        this.mDisplayFrame.readFromParcel(parcel);
        this.mDisplayCutout.readFromParcel(parcel);
        this.mRoundedCorners = (android.view.RoundedCorners) parcel.readTypedObject(android.view.RoundedCorners.CREATOR);
        this.mRoundedCornerFrame.readFromParcel(parcel);
        this.mPrivacyIndicatorBounds = (android.view.PrivacyIndicatorBounds) parcel.readTypedObject(android.view.PrivacyIndicatorBounds.CREATOR);
        this.mDisplayShape = (android.view.DisplayShape) parcel.readTypedObject(android.view.DisplayShape.CREATOR);
        int readInt = parcel.readInt();
        if (this.mSources == null) {
            sparseArray = new android.util.SparseArray<>(readInt);
        } else {
            sparseArray = this.mSources;
            sparseArray.clear();
        }
        for (int i = 0; i < readInt; i++) {
            android.view.InsetsSource insetsSource = (android.view.InsetsSource) parcel.readTypedObject(android.view.InsetsSource.CREATOR);
            sparseArray.append(insetsSource.getId(), insetsSource);
        }
        return sparseArray;
    }

    public java.lang.String toString() {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ");
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            stringJoiner.add(this.mSources.valueAt(i).toString());
        }
        return "InsetsState: {mDisplayFrame=" + this.mDisplayFrame + ", mDisplayCutout=" + this.mDisplayCutout + ", mRoundedCorners=" + this.mRoundedCorners + "  mRoundedCornerFrame=" + this.mRoundedCornerFrame + ", mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds + ", mDisplayShape=" + this.mDisplayShape + ", mSources= { " + stringJoiner + " }";
    }

    public static void traverse(android.view.InsetsState insetsState, android.view.InsetsState insetsState2, android.view.InsetsState.OnTraverseCallbacks onTraverseCallbacks) {
        onTraverseCallbacks.onStart(insetsState, insetsState2);
        int sourceSize = insetsState.sourceSize();
        int sourceSize2 = insetsState2.sourceSize();
        int i = 0;
        int i2 = 0;
        while (i < sourceSize && i2 < sourceSize2) {
            int sourceIdAt = insetsState.sourceIdAt(i);
            int sourceIdAt2 = insetsState2.sourceIdAt(i2);
            while (sourceIdAt != sourceIdAt2) {
                if (sourceIdAt < sourceIdAt2) {
                    onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
                    i++;
                    if (i >= sourceSize) {
                        break;
                    } else {
                        sourceIdAt = insetsState.sourceIdAt(i);
                    }
                } else {
                    onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
                    i2++;
                    if (i2 >= sourceSize2) {
                        break;
                    } else {
                        sourceIdAt2 = insetsState2.sourceIdAt(i2);
                    }
                }
            }
            if (i >= sourceSize || i2 >= sourceSize2) {
                break;
            }
            onTraverseCallbacks.onIdMatch(insetsState.sourceAt(i), insetsState2.sourceAt(i2));
            i++;
            i2++;
        }
        while (i2 < sourceSize2) {
            onTraverseCallbacks.onIdNotFoundInState1(i2, insetsState2.sourceAt(i2));
            i2++;
        }
        while (i < sourceSize) {
            onTraverseCallbacks.onIdNotFoundInState2(i, insetsState.sourceAt(i));
            i++;
        }
        onTraverseCallbacks.onFinish(insetsState, insetsState2);
    }

    public interface OnTraverseCallbacks {
        default void onStart(android.view.InsetsState insetsState, android.view.InsetsState insetsState2) {
        }

        default void onIdMatch(android.view.InsetsSource insetsSource, android.view.InsetsSource insetsSource2) {
        }

        default void onIdNotFoundInState1(int i, android.view.InsetsSource insetsSource) {
        }

        default void onIdNotFoundInState2(int i, android.view.InsetsSource insetsSource) {
        }

        default void onFinish(android.view.InsetsState insetsState, android.view.InsetsState insetsState2) {
        }
    }
}
