package android.widget;

/* loaded from: classes4.dex */
public class ViewSwitcher extends android.widget.ViewAnimator {
    android.widget.ViewSwitcher.ViewFactory mFactory;

    public interface ViewFactory {
        android.view.View makeView();
    }

    public ViewSwitcher(android.content.Context context) {
        super(context);
    }

    public ViewSwitcher(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.ViewAnimator, android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() >= 2) {
            throw new java.lang.IllegalStateException("Can't add more than 2 views to a ViewSwitcher");
        }
        super.addView(view, i, layoutParams);
    }

    @Override // android.widget.ViewAnimator, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ViewSwitcher.class.getName();
    }

    public android.view.View getNextView() {
        return getChildAt(this.mWhichChild == 0 ? 1 : 0);
    }

    private android.view.View obtainView() {
        android.view.View makeView = this.mFactory.makeView();
        android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) makeView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new android.widget.FrameLayout.LayoutParams(-1, -2);
        }
        addView(makeView, layoutParams);
        return makeView;
    }

    public void setFactory(android.widget.ViewSwitcher.ViewFactory viewFactory) {
        this.mFactory = viewFactory;
        obtainView();
        obtainView();
    }

    public void reset() {
        this.mFirstTime = true;
        android.view.View childAt = getChildAt(0);
        if (childAt != null) {
            childAt.setVisibility(8);
        }
        android.view.View childAt2 = getChildAt(1);
        if (childAt2 != null) {
            childAt2.setVisibility(8);
        }
    }
}
