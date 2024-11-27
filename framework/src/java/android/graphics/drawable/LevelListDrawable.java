package android.graphics.drawable;

/* loaded from: classes.dex */
public class LevelListDrawable extends android.graphics.drawable.DrawableContainer {
    private android.graphics.drawable.LevelListDrawable.LevelListState mLevelListState;
    private boolean mMutated;

    public LevelListDrawable() {
        this(null, null);
    }

    public void addLevel(int i, int i2, android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            this.mLevelListState.addLevel(i, i2, drawable);
            onLevelChange(getLevel());
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        if (selectDrawable(this.mLevelListState.indexOfLevel(i))) {
            return true;
        }
        return super.onLevelChange(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateDensity(resources);
        inflateChildElements(resources, xmlPullParser, attributeSet, theme);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x009d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0096, code lost:
    
        onLevelChange(getLevel());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void inflateChildElements(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth;
        int next;
        android.graphics.drawable.Drawable createFromXmlInner;
        int depth2 = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1 || ((depth = xmlPullParser.getDepth()) < depth2 && next2 == 3)) {
                break;
            }
            if (next2 == 2 && depth <= depth2 && xmlPullParser.getName().equals(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM)) {
                android.content.res.TypedArray obtainAttributes = obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.LevelListDrawableItem);
                int i = obtainAttributes.getInt(1, 0);
                int i2 = obtainAttributes.getInt(2, 0);
                int resourceId = obtainAttributes.getResourceId(0, 0);
                obtainAttributes.recycle();
                if (i2 < 0) {
                    throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'maxLevel' attribute");
                }
                if (resourceId != 0) {
                    createFromXmlInner = resources.getDrawable(resourceId, theme);
                } else {
                    do {
                        next = xmlPullParser.next();
                    } while (next == 4);
                    if (next != 2) {
                        throw new org.xmlpull.v1.XmlPullParserException(xmlPullParser.getPositionDescription() + ": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    }
                    createFromXmlInner = android.graphics.drawable.Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                }
                this.mLevelListState.addLevel(i, i2, createFromXmlInner);
            }
        }
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public android.graphics.drawable.Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLevelListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.DrawableContainer
    public android.graphics.drawable.LevelListDrawable.LevelListState cloneConstantState() {
        return new android.graphics.drawable.LevelListDrawable.LevelListState(this.mLevelListState, this, null);
    }

    @Override // android.graphics.drawable.DrawableContainer, android.graphics.drawable.Drawable
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    private static final class LevelListState extends android.graphics.drawable.DrawableContainer.DrawableContainerState {
        private int[] mHighs;
        private int[] mLows;

        LevelListState(android.graphics.drawable.LevelListDrawable.LevelListState levelListState, android.graphics.drawable.LevelListDrawable levelListDrawable, android.content.res.Resources resources) {
            super(levelListState, levelListDrawable, resources);
            if (levelListState != null) {
                this.mLows = levelListState.mLows;
                this.mHighs = levelListState.mHighs;
            } else {
                this.mLows = new int[getCapacity()];
                this.mHighs = new int[getCapacity()];
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mutate() {
            this.mLows = (int[]) this.mLows.clone();
            this.mHighs = (int[]) this.mHighs.clone();
        }

        public void addLevel(int i, int i2, android.graphics.drawable.Drawable drawable) {
            int addChild = addChild(drawable);
            this.mLows[addChild] = i;
            this.mHighs[addChild] = i2;
        }

        public int indexOfLevel(int i) {
            int[] iArr = this.mLows;
            int[] iArr2 = this.mHighs;
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (i >= iArr[i2] && i <= iArr2[i2]) {
                    return i2;
                }
            }
            return -1;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable() {
            return new android.graphics.drawable.LevelListDrawable(this, null);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public android.graphics.drawable.Drawable newDrawable(android.content.res.Resources resources) {
            return new android.graphics.drawable.LevelListDrawable(this, resources);
        }

        @Override // android.graphics.drawable.DrawableContainer.DrawableContainerState
        public void growArray(int i, int i2) {
            super.growArray(i, i2);
            int[] iArr = new int[i2];
            java.lang.System.arraycopy(this.mLows, 0, iArr, 0, i);
            this.mLows = iArr;
            int[] iArr2 = new int[i2];
            java.lang.System.arraycopy(this.mHighs, 0, iArr2, 0, i);
            this.mHighs = iArr2;
        }
    }

    @Override // android.graphics.drawable.DrawableContainer
    protected void setConstantState(android.graphics.drawable.DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof android.graphics.drawable.LevelListDrawable.LevelListState) {
            this.mLevelListState = (android.graphics.drawable.LevelListDrawable.LevelListState) drawableContainerState;
        }
    }

    private LevelListDrawable(android.graphics.drawable.LevelListDrawable.LevelListState levelListState, android.content.res.Resources resources) {
        setConstantState(new android.graphics.drawable.LevelListDrawable.LevelListState(levelListState, this, resources));
        onLevelChange(getLevel());
    }
}
