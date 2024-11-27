package android.view;

/* loaded from: classes4.dex */
public class ViewGroupOverlay extends android.view.ViewOverlay {
    ViewGroupOverlay(android.content.Context context, android.view.View view) {
        super(context, view);
    }

    public void add(android.view.View view) {
        this.mOverlayViewGroup.add(view);
    }

    public void remove(android.view.View view) {
        this.mOverlayViewGroup.remove(view);
    }
}
