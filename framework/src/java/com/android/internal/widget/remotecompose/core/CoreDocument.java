package com.android.internal.widget.remotecompose.core;

/* loaded from: classes5.dex */
public class CoreDocument {
    java.lang.String mContentDescription;
    java.util.ArrayList<com.android.internal.widget.remotecompose.core.Operation> mOperations;
    com.android.internal.widget.remotecompose.core.RemoteComposeState mRemoteComposeState = new com.android.internal.widget.remotecompose.core.RemoteComposeState();
    com.android.internal.widget.remotecompose.core.CoreDocument.Version mVersion = new com.android.internal.widget.remotecompose.core.CoreDocument.Version(0, 1, 0);
    long mRequiredCapabilities = 0;
    int mWidth = 0;
    int mHeight = 0;
    int mContentScroll = 0;
    int mContentSizing = 0;
    int mContentMode = 0;
    int mContentAlignment = 34;
    com.android.internal.widget.remotecompose.core.RemoteComposeBuffer mBuffer = new com.android.internal.widget.remotecompose.core.RemoteComposeBuffer(this.mRemoteComposeState);
    java.util.HashSet<com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks> mClickListeners = new java.util.HashSet<>();
    java.util.HashSet<com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation> mClickAreas = new java.util.HashSet<>();
    private final float[] mScaleOutput = new float[2];
    private final float[] mTranslateOutput = new float[2];

    public interface ClickCallbacks {
        void click(int i, java.lang.String str);
    }

    public java.lang.String getContentDescription() {
        return this.mContentDescription;
    }

    public void setContentDescription(java.lang.String str) {
        this.mContentDescription = str;
    }

    public long getRequiredCapabilities() {
        return this.mRequiredCapabilities;
    }

    public void setRequiredCapabilities(long j) {
        this.mRequiredCapabilities = j;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public com.android.internal.widget.remotecompose.core.RemoteComposeBuffer getBuffer() {
        return this.mBuffer;
    }

    public void setBuffer(com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer) {
        this.mBuffer = remoteComposeBuffer;
    }

    public com.android.internal.widget.remotecompose.core.RemoteComposeState getRemoteComposeState() {
        return this.mRemoteComposeState;
    }

    public void setRemoteComposeState(com.android.internal.widget.remotecompose.core.RemoteComposeState remoteComposeState) {
        this.mRemoteComposeState = remoteComposeState;
    }

    public int getContentScroll() {
        return this.mContentScroll;
    }

    public int getContentSizing() {
        return this.mContentSizing;
    }

    public int getContentMode() {
        return this.mContentMode;
    }

    public void setRootContentBehavior(int i, int i2, int i3, int i4) {
        this.mContentScroll = i;
        this.mContentAlignment = i2;
        this.mContentSizing = i3;
        this.mContentMode = i4;
    }

    public void computeScale(float f, float f2, float[] fArr) {
        float f3;
        float f4 = 1.0f;
        if (this.mContentSizing == 2) {
            switch (this.mContentMode) {
                case 1:
                    f4 = java.lang.Math.min(1.0f, java.lang.Math.min(f / this.mWidth, f2 / this.mHeight));
                    f3 = f4;
                    break;
                case 2:
                    f4 = f / this.mWidth;
                    f3 = f4;
                    break;
                case 3:
                    f4 = f2 / this.mHeight;
                    f3 = f4;
                    break;
                case 4:
                    f4 = java.lang.Math.min(f / this.mWidth, f2 / this.mHeight);
                    f3 = f4;
                    break;
                case 5:
                    f4 = java.lang.Math.max(f / this.mWidth, f2 / this.mHeight);
                    f3 = f4;
                    break;
                case 6:
                    f4 = f / this.mWidth;
                    f3 = f2 / this.mHeight;
                    break;
            }
            fArr[0] = f4;
            fArr[1] = f3;
        }
        f3 = 1.0f;
        fArr[0] = f4;
        fArr[1] = f3;
    }

    private void computeTranslate(float f, float f2, float f3, float f4, float[] fArr) {
        float f5;
        int i = this.mContentAlignment & 240;
        int i2 = this.mContentAlignment & 15;
        float f6 = this.mWidth * f3;
        float f7 = this.mHeight * f4;
        float f8 = 0.0f;
        switch (i) {
            case 16:
            default:
                f5 = 0.0f;
                break;
            case 32:
                f5 = (f - f6) / 2.0f;
                break;
            case 64:
                f5 = f - f6;
                break;
        }
        switch (i2) {
            case 2:
                f8 = (f2 - f7) / 2.0f;
                break;
            case 4:
                f8 = f2 - f7;
                break;
        }
        fArr[0] = f5;
        fArr[1] = f8;
    }

    public java.util.Set<com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation> getClickAreas() {
        return this.mClickAreas;
    }

    static class Version {
        public final int major;
        public final int minor;
        public final int patchLevel;

        Version(int i, int i2, int i3) {
            this.major = i;
            this.minor = i2;
            this.patchLevel = i3;
        }
    }

    public static class ClickAreaRepresentation {
        float mBottom;
        java.lang.String mContentDescription;
        int mId;
        float mLeft;
        java.lang.String mMetadata;
        float mRight;
        float mTop;

        public ClickAreaRepresentation(int i, java.lang.String str, float f, float f2, float f3, float f4, java.lang.String str2) {
            this.mId = i;
            this.mContentDescription = str;
            this.mLeft = f;
            this.mTop = f2;
            this.mRight = f3;
            this.mBottom = f4;
            this.mMetadata = str2;
        }

        public boolean contains(float f, float f2) {
            return f >= this.mLeft && f < this.mRight && f2 >= this.mTop && f2 < this.mBottom;
        }

        public float getLeft() {
            return this.mLeft;
        }

        public float getTop() {
            return this.mTop;
        }

        public float width() {
            return java.lang.Math.max(0.0f, this.mRight - this.mLeft);
        }

        public float height() {
            return java.lang.Math.max(0.0f, this.mBottom - this.mTop);
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String getContentDescription() {
            return this.mContentDescription;
        }

        public java.lang.String getMetadata() {
            return this.mMetadata;
        }
    }

    public void initFromBuffer(com.android.internal.widget.remotecompose.core.RemoteComposeBuffer remoteComposeBuffer) {
        this.mOperations = new java.util.ArrayList<>();
        remoteComposeBuffer.inflateFromBuffer(this.mOperations);
    }

    public void initializeContext(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext) {
        this.mRemoteComposeState.reset();
        this.mClickAreas.clear();
        remoteContext.mDocument = this;
        remoteContext.mRemoteComposeState = this.mRemoteComposeState;
        remoteContext.mMode = com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.DATA;
        java.util.Iterator<com.android.internal.widget.remotecompose.core.Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            it.next().apply(remoteContext);
        }
        remoteContext.mMode = com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.UNSET;
    }

    public boolean canBeDisplayed(int i, int i2, long j) {
        return this.mVersion.major <= i && this.mVersion.minor <= i2;
    }

    void setVersion(int i, int i2, int i3) {
        this.mVersion = new com.android.internal.widget.remotecompose.core.CoreDocument.Version(i, i2, i3);
    }

    public void addClickArea(int i, java.lang.String str, float f, float f2, float f3, float f4, java.lang.String str2) {
        this.mClickAreas.add(new com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation(i, str, f, f2, f3, f4, str2));
    }

    public void addClickListener(com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks clickCallbacks) {
        this.mClickListeners.add(clickCallbacks);
    }

    public void onClick(float f, float f2) {
        java.util.Iterator<com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation next = it.next();
            if (next.contains(f, f2)) {
                warnClickListeners(next);
            }
        }
    }

    public void performClick(int i) {
        java.util.Iterator<com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation> it = this.mClickAreas.iterator();
        while (it.hasNext()) {
            com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation next = it.next();
            if (next.mId == i) {
                warnClickListeners(next);
            }
        }
    }

    private void warnClickListeners(com.android.internal.widget.remotecompose.core.CoreDocument.ClickAreaRepresentation clickAreaRepresentation) {
        java.util.Iterator<com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks> it = this.mClickListeners.iterator();
        while (it.hasNext()) {
            it.next().click(clickAreaRepresentation.mId, clickAreaRepresentation.mMetadata);
        }
    }

    public void paint(com.android.internal.widget.remotecompose.core.RemoteContext remoteContext, int i) {
        boolean z;
        remoteContext.mMode = com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.PAINT;
        remoteContext.setTheme(-1);
        remoteContext.mRemoteComposeState = this.mRemoteComposeState;
        if (this.mContentSizing == 2) {
            computeScale(remoteContext.mWidth, remoteContext.mHeight, this.mScaleOutput);
            computeTranslate(remoteContext.mWidth, remoteContext.mHeight, this.mScaleOutput[0], this.mScaleOutput[1], this.mTranslateOutput);
            remoteContext.mPaintContext.translate(this.mTranslateOutput[0], this.mTranslateOutput[1]);
            remoteContext.mPaintContext.scale(this.mScaleOutput[0], this.mScaleOutput[1]);
        }
        java.util.Iterator<com.android.internal.widget.remotecompose.core.Operation> it = this.mOperations.iterator();
        while (it.hasNext()) {
            com.android.internal.widget.remotecompose.core.Operation next = it.next();
            if (i == -1) {
                z = true;
            } else {
                z = (next instanceof com.android.internal.widget.remotecompose.core.operations.Theme) || remoteContext.getTheme() == i || remoteContext.getTheme() == -1;
            }
            if (z) {
                next.apply(remoteContext);
            }
        }
        remoteContext.mMode = com.android.internal.widget.remotecompose.core.RemoteContext.ContextMode.UNSET;
    }
}
