package android.view;

/* loaded from: classes4.dex */
public abstract class ActionProvider {
    private static final java.lang.String TAG = "ActionProvider";
    private android.view.ActionProvider.SubUiVisibilityListener mSubUiVisibilityListener;
    private android.view.ActionProvider.VisibilityListener mVisibilityListener;

    public interface SubUiVisibilityListener {
        void onSubUiVisibilityChanged(boolean z);
    }

    public interface VisibilityListener {
        void onActionProviderVisibilityChanged(boolean z);
    }

    @java.lang.Deprecated
    public abstract android.view.View onCreateActionView();

    public ActionProvider(android.content.Context context) {
    }

    public android.view.View onCreateActionView(android.view.MenuItem menuItem) {
        return onCreateActionView();
    }

    public boolean overridesItemVisibility() {
        return false;
    }

    public boolean isVisible() {
        return true;
    }

    public void refreshVisibility() {
        if (this.mVisibilityListener != null && overridesItemVisibility()) {
            this.mVisibilityListener.onActionProviderVisibilityChanged(isVisible());
        }
    }

    public boolean onPerformDefaultAction() {
        return false;
    }

    public boolean hasSubMenu() {
        return false;
    }

    public void onPrepareSubMenu(android.view.SubMenu subMenu) {
    }

    public void subUiVisibilityChanged(boolean z) {
        if (this.mSubUiVisibilityListener != null) {
            this.mSubUiVisibilityListener.onSubUiVisibilityChanged(z);
        }
    }

    public void setSubUiVisibilityListener(android.view.ActionProvider.SubUiVisibilityListener subUiVisibilityListener) {
        this.mSubUiVisibilityListener = subUiVisibilityListener;
    }

    public void setVisibilityListener(android.view.ActionProvider.VisibilityListener visibilityListener) {
        if (this.mVisibilityListener != null) {
            android.util.Log.w(TAG, "setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this " + getClass().getSimpleName() + " instance while it is still in use somewhere else?");
        }
        this.mVisibilityListener = visibilityListener;
    }

    public void reset() {
        this.mVisibilityListener = null;
        this.mSubUiVisibilityListener = null;
    }
}
