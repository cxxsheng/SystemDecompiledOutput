package android.view;

/* loaded from: classes4.dex */
public abstract class ViewOutlineProvider {
    public static final android.view.ViewOutlineProvider BACKGROUND = new android.view.ViewOutlineProvider() { // from class: android.view.ViewOutlineProvider.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(android.view.View view, android.graphics.Outline outline) {
            android.graphics.drawable.Drawable background = view.getBackground();
            if (background != null) {
                background.getOutline(outline);
            } else {
                outline.setRect(0, 0, view.getWidth(), view.getHeight());
                outline.setAlpha(0.0f);
            }
        }
    };
    public static final android.view.ViewOutlineProvider BOUNDS = new android.view.ViewOutlineProvider() { // from class: android.view.ViewOutlineProvider.2
        @Override // android.view.ViewOutlineProvider
        public void getOutline(android.view.View view, android.graphics.Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
        }
    };
    public static final android.view.ViewOutlineProvider PADDED_BOUNDS = new android.view.ViewOutlineProvider() { // from class: android.view.ViewOutlineProvider.3
        @Override // android.view.ViewOutlineProvider
        public void getOutline(android.view.View view, android.graphics.Outline outline) {
            outline.setRect(view.getPaddingLeft(), view.getPaddingTop(), view.getWidth() - view.getPaddingRight(), view.getHeight() - view.getPaddingBottom());
        }
    };

    public abstract void getOutline(android.view.View view, android.graphics.Outline outline);
}
