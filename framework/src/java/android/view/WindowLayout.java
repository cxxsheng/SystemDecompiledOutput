package android.view;

/* loaded from: classes4.dex */
public class WindowLayout {
    private static final boolean DEBUG = false;
    static final int MAX_X = 100000;
    static final int MAX_Y = 100000;
    static final int MIN_X = -100000;
    static final int MIN_Y = -100000;
    private static final java.lang.String TAG = android.view.WindowLayout.class.getSimpleName();
    public static final int UNSPECIFIED_LENGTH = -1;
    private final android.graphics.Rect mTempDisplayCutoutSafeExceptMaybeBarsRect = new android.graphics.Rect();
    private final android.graphics.Rect mTempRect = new android.graphics.Rect();

    public void computeFrames(android.view.WindowManager.LayoutParams layoutParams, android.view.InsetsState insetsState, android.graphics.Rect rect, android.graphics.Rect rect2, int i, int i2, int i3, int i4, float f, android.window.ClientWindowFrames clientWindowFrames) {
        android.graphics.Rect rect3;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        float f2;
        float f3;
        boolean z;
        android.graphics.Rect rect4;
        int i10;
        android.view.InsetsSource peekSource;
        int i11 = layoutParams.type;
        int i12 = layoutParams.flags;
        int i13 = layoutParams.privateFlags;
        boolean z2 = (i12 & 256) == 256;
        android.graphics.Rect rect5 = clientWindowFrames.attachedFrame;
        android.graphics.Rect rect6 = clientWindowFrames.displayFrame;
        android.graphics.Rect rect7 = clientWindowFrames.parentFrame;
        android.graphics.Rect rect8 = clientWindowFrames.frame;
        android.graphics.Insets calculateInsets = insetsState.calculateInsets(rect2, layoutParams.getFitInsetsTypes(), layoutParams.isFitInsetsIgnoringVisibility());
        int fitInsetsSides = layoutParams.getFitInsetsSides();
        int i14 = (fitInsetsSides & 1) != 0 ? calculateInsets.left : 0;
        if ((fitInsetsSides & 2) != 0) {
            rect3 = rect8;
            i5 = calculateInsets.top;
        } else {
            rect3 = rect8;
            i5 = 0;
        }
        if ((fitInsetsSides & 4) != 0) {
            i6 = i11;
            i7 = calculateInsets.right;
        } else {
            i6 = i11;
            i7 = 0;
        }
        rect6.set(rect2.left + i14, rect2.top + i5, rect2.right - i7, rect2.bottom - ((fitInsetsSides & 8) != 0 ? calculateInsets.bottom : 0));
        if (rect5 == null) {
            rect7.set(rect6);
            if ((1073741824 & i13) != 0 && (peekSource = insetsState.peekSource(android.view.InsetsSource.ID_IME)) != null) {
                rect7.inset(peekSource.calculateInsets(rect7, false));
            }
        } else {
            rect7.set(!z2 ? rect5 : rect6);
        }
        int i15 = layoutParams.layoutInDisplayCutoutMode;
        android.view.DisplayCutout displayCutout = insetsState.getDisplayCutout();
        android.graphics.Rect rect9 = this.mTempDisplayCutoutSafeExceptMaybeBarsRect;
        rect9.set(rect);
        clientWindowFrames.isParentFrameClippedByDisplayCutout = false;
        if (i15 == 3 || displayCutout.isEmpty()) {
            i8 = i6;
        } else {
            android.graphics.Rect displayFrame = insetsState.getDisplayFrame();
            if (i15 == 1) {
                if (displayFrame.width() < displayFrame.height()) {
                    rect9.top = -100000;
                    rect9.bottom = 100000;
                } else {
                    rect9.left = -100000;
                    rect9.right = 100000;
                }
            }
            boolean z3 = (layoutParams.flags & 65536) != 0;
            if (z2 && z3 && (i15 == 0 || i15 == 1)) {
                android.graphics.Insets calculateInsets2 = insetsState.calculateInsets(displayFrame, android.view.WindowInsets.Type.systemBars(), i4);
                if (calculateInsets2.left >= displayCutout.getSafeInsetLeft()) {
                    rect9.left = -100000;
                }
                if (calculateInsets2.top >= displayCutout.getSafeInsetTop()) {
                    rect9.top = -100000;
                }
                if (calculateInsets2.right < displayCutout.getSafeInsetRight()) {
                    i10 = 100000;
                } else {
                    i10 = 100000;
                    rect9.right = 100000;
                }
                if (calculateInsets2.bottom >= displayCutout.getSafeInsetBottom()) {
                    rect9.bottom = i10;
                }
            }
            i8 = i6;
            if (i8 == 2011 && rect9.bottom != 100000 && insetsState.calculateInsets(displayFrame, android.view.WindowInsets.Type.navigationBars(), true).bottom > 0) {
                rect9.bottom = 100000;
            }
            boolean z4 = (rect5 == null || z2) ? false : true;
            boolean z5 = (layoutParams.isFullscreen() || !z2 || i8 == 1) ? false : true;
            if (!z4 && !z5) {
                this.mTempRect.set(rect7);
                rect7.intersectUnchecked(rect9);
                clientWindowFrames.isParentFrameClippedByDisplayCutout = !this.mTempRect.equals(rect7);
            }
            rect6.intersectUnchecked(rect9);
        }
        boolean z6 = (layoutParams.flags & 512) != 0;
        boolean inMultiWindowMode = android.app.WindowConfiguration.inMultiWindowMode(i);
        if (z6 && i8 != 2010 && !inMultiWindowMode) {
            rect6.left = -100000;
            rect6.top = -100000;
            rect6.right = 100000;
            rect6.bottom = 100000;
        }
        boolean z7 = f != 1.0f;
        int width = rect7.width();
        int height = rect7.height();
        boolean z8 = (layoutParams.privateFlags & 4096) != 0;
        int i16 = i2;
        if (i16 == -1 || z8) {
            i16 = layoutParams.width >= 0 ? layoutParams.width : width;
        }
        int i17 = i3;
        if (i17 == -1 || z8) {
            i17 = layoutParams.height >= 0 ? layoutParams.height : height;
        }
        if ((layoutParams.flags & 16384) == 0) {
            if (layoutParams.width == -1) {
                i16 = width;
            } else if (z7) {
                i16 = (int) ((i16 * f) + 0.5f);
            }
            if (layoutParams.height == -1) {
                i17 = height;
                i9 = i16;
            } else if (z7) {
                i17 = (int) ((i17 * f) + 0.5f);
                i9 = i16;
            } else {
                i9 = i16;
            }
        } else {
            if (layoutParams.width < 0) {
                i9 = width;
            } else if (z7) {
                i9 = (int) ((layoutParams.width * f) + 0.5f);
            } else {
                i9 = layoutParams.width;
            }
            if (layoutParams.height < 0) {
                i17 = height;
            } else if (z7) {
                i17 = (int) ((layoutParams.height * f) + 0.5f);
            } else {
                i17 = layoutParams.height;
            }
        }
        if (z7) {
            f2 = layoutParams.x * f;
            f3 = layoutParams.y * f;
        } else {
            f2 = layoutParams.x;
            f3 = layoutParams.y;
        }
        if (inMultiWindowMode && (layoutParams.privateFlags & 16384) == 0) {
            i9 = java.lang.Math.min(i9, width);
            i17 = java.lang.Math.min(i17, height);
        }
        if (inMultiWindowMode) {
            z = true;
            if (layoutParams.type == 1 || z6) {
                z = false;
            }
        } else {
            z = true;
        }
        android.view.Gravity.apply(layoutParams.gravity, i9, i17, rect7, (int) (f2 + (layoutParams.horizontalMargin * width)), (int) (f3 + (layoutParams.verticalMargin * height)), rect3);
        if (!z) {
            rect4 = rect3;
        } else {
            rect4 = rect3;
            android.view.Gravity.applyDisplay(layoutParams.gravity, rect6, rect4);
        }
        if (z8) {
            extendFrameByCutout(rect, rect6, rect4, this.mTempRect);
        }
    }

    public static void extendFrameByCutout(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        if (rect.contains(rect3)) {
            return;
        }
        rect4.set(rect3);
        android.view.Gravity.applyDisplay(0, rect, rect4);
        if (rect4.intersect(rect2)) {
            rect3.union(rect4);
        }
    }

    public static void computeSurfaceSize(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, int i, int i2, android.graphics.Rect rect2, boolean z, android.graphics.Point point) {
        if ((layoutParams.flags & 16384) == 0) {
            if (z) {
                i = rect.width();
                i2 = rect.height();
            } else {
                i = rect2.width();
                i2 = rect2.height();
            }
        }
        if (i < 1) {
            i = 1;
        }
        if (i2 < 1) {
            i2 = 1;
        }
        android.graphics.Rect rect3 = layoutParams.surfaceInsets;
        point.set(i + rect3.left + rect3.right, i2 + rect3.top + rect3.bottom);
    }
}
