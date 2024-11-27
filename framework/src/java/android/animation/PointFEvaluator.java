package android.animation;

/* loaded from: classes.dex */
public class PointFEvaluator implements android.animation.TypeEvaluator<android.graphics.PointF> {
    private android.graphics.PointF mPoint;

    public PointFEvaluator() {
    }

    public PointFEvaluator(android.graphics.PointF pointF) {
        this.mPoint = pointF;
    }

    @Override // android.animation.TypeEvaluator
    public android.graphics.PointF evaluate(float f, android.graphics.PointF pointF, android.graphics.PointF pointF2) {
        float f2 = pointF.x + ((pointF2.x - pointF.x) * f);
        float f3 = pointF.y + (f * (pointF2.y - pointF.y));
        if (this.mPoint != null) {
            this.mPoint.set(f2, f3);
            return this.mPoint;
        }
        return new android.graphics.PointF(f2, f3);
    }
}
