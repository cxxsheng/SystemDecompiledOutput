package android.view;

/* loaded from: classes4.dex */
public class FocusFinderHelper {
    private android.view.FocusFinder mFocusFinder;

    public FocusFinderHelper(android.view.FocusFinder focusFinder) {
        this.mFocusFinder = focusFinder;
    }

    public boolean isBetterCandidate(int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        return this.mFocusFinder.isBetterCandidate(i, rect, rect2, rect3);
    }

    public boolean beamBeats(int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        return this.mFocusFinder.beamBeats(i, rect, rect2, rect3);
    }

    public boolean isCandidate(android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
        return this.mFocusFinder.isCandidate(rect, rect2, i);
    }

    public boolean beamsOverlap(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return this.mFocusFinder.beamsOverlap(i, rect, rect2);
    }

    public static int majorAxisDistance(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return android.view.FocusFinder.majorAxisDistance(i, rect, rect2);
    }

    public static int majorAxisDistanceToFarEdge(int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
        return android.view.FocusFinder.majorAxisDistanceToFarEdge(i, rect, rect2);
    }
}
