package android.view;

/* loaded from: classes4.dex */
public abstract class ActionMode {
    public static final int DEFAULT_HIDE_DURATION = -1;
    public static final int TYPE_FLOATING = 1;
    public static final int TYPE_PRIMARY = 0;
    private java.lang.Object mTag;
    private boolean mTitleOptionalHint;
    private int mType = 0;

    public interface Callback {
        boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem);

        boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu);

        void onDestroyActionMode(android.view.ActionMode actionMode);

        boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu);
    }

    public abstract void finish();

    public abstract android.view.View getCustomView();

    public abstract android.view.Menu getMenu();

    public abstract android.view.MenuInflater getMenuInflater();

    public abstract java.lang.CharSequence getSubtitle();

    public abstract java.lang.CharSequence getTitle();

    public abstract void invalidate();

    public abstract void setCustomView(android.view.View view);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(java.lang.CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(java.lang.CharSequence charSequence);

    public void setTag(java.lang.Object obj) {
        this.mTag = obj;
    }

    public java.lang.Object getTag() {
        return this.mTag;
    }

    public void setTitleOptionalHint(boolean z) {
        this.mTitleOptionalHint = z;
    }

    public boolean getTitleOptionalHint() {
        return this.mTitleOptionalHint;
    }

    public boolean isTitleOptional() {
        return false;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getType() {
        return this.mType;
    }

    public void invalidateContentRect() {
    }

    public void hide(long j) {
    }

    public void onWindowFocusChanged(boolean z) {
    }

    public boolean isUiFocusable() {
        return true;
    }

    public static abstract class Callback2 implements android.view.ActionMode.Callback {
        public void onGetContentRect(android.view.ActionMode actionMode, android.view.View view, android.graphics.Rect rect) {
            if (view != null) {
                rect.set(0, 0, view.getWidth(), view.getHeight());
            } else {
                rect.set(0, 0, 0, 0);
            }
        }
    }
}
