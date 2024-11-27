package android.graphics.drawable;

/* loaded from: classes.dex */
public class StateListDrawable extends android.graphics.drawable.DrawableContainer {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "StateListDrawable";
    private boolean mMutated;
    private android.graphics.drawable.StateListDrawable.StateListState mStateListState;

    public StateListDrawable() {
        this(null, null);
    }

    public void addState(int[] iArr, android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            this.mStateListState.addStateSet(iArr, drawable);
            onStateChange(getState());
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return this.mStateListState.hasFocusStateSpecified();
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        int indexOfStateSet = this.mStateListState.indexOfStateSet(iArr);
        if (indexOfStateSet < 0) {
            indexOfStateSet = this.mStateListState.indexOfStateSet(android.util.StateSet.WILD_CARD);
        }
        return selectDrawable(indexOfStateSet) || onStateChange;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.StateListDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, obtainAttributes, 1);
        updateStateFromTypedArray(obtainAttributes);
        updateDensity(resources);
        obtainAttributes.recycle();
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        onStateChange(getState());
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray) {
        android.graphics.drawable.StateListDrawable.StateListState stateListState = this.mStateListState;
        stateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        stateListState.mThemeAttrs = typedArray.extractThemeAttrs();
        stateListState.mVariablePadding = typedArray.getBoolean(2, stateListState.mVariablePadding);
        stateListState.mConstantSize = typedArray.getBoolean(3, stateListState.mConstantSize);
        stateListState.mEnterFadeDuration = typedArray.getInt(4, stateListState.mEnterFadeDuration);
        stateListState.mExitFadeDuration = typedArray.getInt(5, stateListState.mExitFadeDuration);
        stateListState.mDither = typedArray.getBoolean(0, stateListState.mDither);
        stateListState.mAutoMirrored = typedArray.getBoolean(6, stateListState.mAutoMirrored);
    }

    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.graphics.drawable.StateListDrawable.StateListState stateListState = this.mStateListState;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 >= depth || next2 != 3) {
                    if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                        android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.StateListDrawableItem);
                        android.graphics.drawable.Drawable drawable = obtainAttributes.getDrawable(0);
                        obtainAttributes.recycle();
                        int[] extractStateSet = extractStateSet(attributeSet);
                        if (drawable == null) {
                            do {
                                next = xmlPullParser.next();
                            } while (next == 4);
                            if (next != 2) {
                                throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                            }
                            drawable = android.graphics.drawable.Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                        }
                        stateListState.addStateSet(extractStateSet, drawable);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    int[] extractStateSet(android.util.AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        int[] iArr = new int[attributeCount];
        int i = 0;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            int attributeNameResource = attributeSet.getAttributeNameResource(i2);
            switch (attributeNameResource) {
                case 0:
                case 16842960:
                case 16843161:
                    break;
                default:
                    int i3 = i + 1;
                    if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                        attributeNameResource = -attributeNameResource;
                    }
                    iArr[i] = attributeNameResource;
                    i = i3;
                    break;
            }
        }
        return android.util.StateSet.trimStateSet(iArr, i);
    }

    android.graphics.drawable.StateListDrawable.StateListState getStateListState() {
        return this.mStateListState;
    }

    public int getStateCount() {
        return this.mStateListState.getChildCount();
    }

    public int[] getStateSet(int i) {
        return this.mStateListState.mStateSets[i];
    }

    public android.graphics.drawable.Drawable getStateDrawable(int i) {
        return this.mStateListState.getChild(i);
    }

    public int findStateDrawableIndex(int[] iArr) {
        return this.mStateListState.indexOfStateSet(iArr);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mStateListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.DrawableContainer
    public android.graphics.drawable.StateListDrawable.StateListState cloneConstantState() {
        return new android.graphics.drawable.StateListDrawable.StateListState(this.mStateListState, this, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    static class StateListState extends android.graphics.drawable.DrawableContainer.DrawableContainerState {
        int[][] mStateSets;
        int[] mThemeAttrs;

        StateListState(android.graphics.drawable.StateListDrawable.StateListState stateListState, android.graphics.drawable.StateListDrawable stateListDrawable, android.content.res.Resources resources) {
            super(stateListState, stateListDrawable, resources);
            if (stateListState != null) {
                this.mThemeAttrs = stateListState.mThemeAttrs;
                this.mStateSets = stateListState.mStateSets;
            } else {
                this.mThemeAttrs = null;
                this.mStateSets = new int[getCapacity()][];
            }
        }

        void mutate() {
            this.mThemeAttrs = this.mThemeAttrs != null ? (int[]) this.mThemeAttrs.clone() : null;
            int[][] iArr = new int[this.mStateSets.length][];
            for (int length = this.mStateSets.length - 1; length >= 0; length--) {
                iArr[length] = this.mStateSets[length] != null ? (int[]) this.mStateSets[length].clone() : null;
            }
            this.mStateSets = iArr;
        }

        int addStateSet(int[] iArr, android.graphics.drawable.Drawable drawable) {
            int addChild = addChild(drawable);
            this.mStateSets[addChild] = iArr;
            return addChild;
        }

        int indexOfStateSet(int[] iArr) {
            int[][] iArr2 = this.mStateSets;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (android.util.StateSet.stateSetMatches(iArr2[i], iArr)) {
                    return i;
                }
            }
            return -1;
        }

        boolean hasFocusStateSpecified() {
            return android.util.StateSet.containsAttribute(this.mStateSets, 16842908);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.StateListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.StateListDrawable(this, resources);
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return this.mThemeAttrs != null || super.canApplyTheme();
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[][] iArr = new int[i2][];
            java.lang.System.arraycopy(this.mStateSets, 0, iArr, 0, i);
            this.mStateSets = iArr;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void applyTheme(android.content.res.Resources.Theme theme) {
        super.applyTheme(theme);
        onStateChange(getState());
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(android.graphics.drawable.DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof android.graphics.drawable.StateListDrawable.StateListState) {
            this.mStateListState = (android.graphics.drawable.StateListDrawable.StateListState) drawableContainerState;
        }
    }

    private StateListDrawable(android.graphics.drawable.StateListDrawable.StateListState stateListState, android.content.res.Resources resources) {
        setConstantState(new android.graphics.drawable.StateListDrawable.StateListState(stateListState, this, resources));
        onStateChange(getState());
    }

    StateListDrawable(android.graphics.drawable.StateListDrawable.StateListState stateListState) {
        if (stateListState != null) {
            setConstantState(stateListState);
        }
    }
}
