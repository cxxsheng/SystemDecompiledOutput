package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayFrames {
    public final android.graphics.Rect mDisplayCutoutSafe;
    public int mHeight;
    public final android.view.InsetsState mInsetsState;
    public int mRotation;
    public final android.graphics.Rect mUnrestricted;
    public int mWidth;
    private static final int ID_DISPLAY_CUTOUT_LEFT = android.view.InsetsSource.createId((java.lang.Object) null, 0, android.view.WindowInsets.Type.displayCutout());
    private static final int ID_DISPLAY_CUTOUT_TOP = android.view.InsetsSource.createId((java.lang.Object) null, 1, android.view.WindowInsets.Type.displayCutout());
    private static final int ID_DISPLAY_CUTOUT_RIGHT = android.view.InsetsSource.createId((java.lang.Object) null, 2, android.view.WindowInsets.Type.displayCutout());
    private static final int ID_DISPLAY_CUTOUT_BOTTOM = android.view.InsetsSource.createId((java.lang.Object) null, 3, android.view.WindowInsets.Type.displayCutout());

    public DisplayFrames(android.view.InsetsState insetsState, android.view.DisplayInfo displayInfo, android.view.DisplayCutout displayCutout, android.view.RoundedCorners roundedCorners, android.view.PrivacyIndicatorBounds privacyIndicatorBounds, android.view.DisplayShape displayShape) {
        this.mUnrestricted = new android.graphics.Rect();
        this.mDisplayCutoutSafe = new android.graphics.Rect();
        this.mInsetsState = insetsState;
        update(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight, displayCutout, roundedCorners, privacyIndicatorBounds, displayShape);
    }

    DisplayFrames() {
        this.mUnrestricted = new android.graphics.Rect();
        this.mDisplayCutoutSafe = new android.graphics.Rect();
        this.mInsetsState = new android.view.InsetsState();
    }

    public boolean update(int i, int i2, int i3, @android.annotation.NonNull android.view.DisplayCutout displayCutout, @android.annotation.NonNull android.view.RoundedCorners roundedCorners, @android.annotation.NonNull android.view.PrivacyIndicatorBounds privacyIndicatorBounds, @android.annotation.NonNull android.view.DisplayShape displayShape) {
        android.view.InsetsState insetsState = this.mInsetsState;
        android.graphics.Rect rect = this.mDisplayCutoutSafe;
        if (this.mRotation == i && this.mWidth == i2 && this.mHeight == i3 && this.mInsetsState.getDisplayCutout().equals(displayCutout) && insetsState.getRoundedCorners().equals(roundedCorners) && insetsState.getPrivacyIndicatorBounds().equals(privacyIndicatorBounds)) {
            return false;
        }
        this.mRotation = i;
        this.mWidth = i2;
        this.mHeight = i3;
        android.graphics.Rect rect2 = this.mUnrestricted;
        rect2.set(0, 0, i2, i3);
        insetsState.setDisplayFrame(rect2);
        insetsState.setDisplayCutout(displayCutout);
        insetsState.setRoundedCorners(roundedCorners);
        insetsState.setPrivacyIndicatorBounds(privacyIndicatorBounds);
        insetsState.setDisplayShape(displayShape);
        insetsState.getDisplayCutoutSafe(rect);
        if (rect.left > rect2.left) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_LEFT, android.view.WindowInsets.Type.displayCutout()).setFrame(rect2.left, rect2.top, rect.left, rect2.bottom).updateSideHint(rect2);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_LEFT);
        }
        if (rect.top > rect2.top) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_TOP, android.view.WindowInsets.Type.displayCutout()).setFrame(rect2.left, rect2.top, rect2.right, rect.top).updateSideHint(rect2);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_TOP);
        }
        if (rect.right < rect2.right) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_RIGHT, android.view.WindowInsets.Type.displayCutout()).setFrame(rect.right, rect2.top, rect2.right, rect2.bottom).updateSideHint(rect2);
        } else {
            insetsState.removeSource(ID_DISPLAY_CUTOUT_RIGHT);
        }
        if (rect.bottom < rect2.bottom) {
            insetsState.getOrCreateSource(ID_DISPLAY_CUTOUT_BOTTOM, android.view.WindowInsets.Type.displayCutout()).setFrame(rect2.left, rect.bottom, rect2.right, rect2.bottom).updateSideHint(rect2);
            return true;
        }
        insetsState.removeSource(ID_DISPLAY_CUTOUT_BOTTOM);
        return true;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        protoOutputStream.end(protoOutputStream.start(j));
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "DisplayFrames w=" + this.mWidth + " h=" + this.mHeight + " r=" + this.mRotation);
    }
}
