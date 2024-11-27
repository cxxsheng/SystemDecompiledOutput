package android.view;

/* loaded from: classes4.dex */
public interface ViewManager {
    void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams);

    void removeView(android.view.View view);

    void updateViewLayout(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams);
}
