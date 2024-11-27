package com.android.server.wm.animation;

/* loaded from: classes3.dex */
public class CurvedTranslateAnimation extends android.view.animation.Animation {
    private final android.animation.PathKeyframes mKeyframes;

    public CurvedTranslateAnimation(android.graphics.Path path) {
        this.mKeyframes = android.animation.KeyframeSet.ofPath(path);
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        android.graphics.PointF pointF = (android.graphics.PointF) this.mKeyframes.getValue(f);
        transformation.getMatrix().setTranslate(pointF.x, pointF.y);
    }
}
