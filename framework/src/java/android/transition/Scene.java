package android.transition;

/* loaded from: classes3.dex */
public final class Scene {
    private android.content.Context mContext;
    java.lang.Runnable mEnterAction;
    java.lang.Runnable mExitAction;
    private android.view.View mLayout;
    private int mLayoutId;
    private android.view.ViewGroup mSceneRoot;

    public static android.transition.Scene getSceneForLayout(android.view.ViewGroup viewGroup, int i, android.content.Context context) {
        android.util.SparseArray sparseArray = (android.util.SparseArray) viewGroup.getTag(com.android.internal.R.id.scene_layoutid_cache);
        if (sparseArray == null) {
            sparseArray = new android.util.SparseArray();
            viewGroup.setTagInternal(com.android.internal.R.id.scene_layoutid_cache, sparseArray);
        }
        android.transition.Scene scene = (android.transition.Scene) sparseArray.get(i);
        if (scene != null) {
            return scene;
        }
        android.transition.Scene scene2 = new android.transition.Scene(viewGroup, i, context);
        sparseArray.put(i, scene2);
        return scene2;
    }

    public Scene(android.view.ViewGroup viewGroup) {
        this.mLayoutId = -1;
        this.mSceneRoot = viewGroup;
    }

    private Scene(android.view.ViewGroup viewGroup, int i, android.content.Context context) {
        this.mLayoutId = -1;
        this.mContext = context;
        this.mSceneRoot = viewGroup;
        this.mLayoutId = i;
    }

    public Scene(android.view.ViewGroup viewGroup, android.view.View view) {
        this.mLayoutId = -1;
        this.mSceneRoot = viewGroup;
        this.mLayout = view;
    }

    @java.lang.Deprecated
    public Scene(android.view.ViewGroup viewGroup, android.view.ViewGroup viewGroup2) {
        this.mLayoutId = -1;
        this.mSceneRoot = viewGroup;
        this.mLayout = viewGroup2;
    }

    public android.view.ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        if (getCurrentScene(this.mSceneRoot) == this && this.mExitAction != null) {
            this.mExitAction.run();
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                android.view.LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        if (this.mEnterAction != null) {
            this.mEnterAction.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    static void setCurrentScene(android.view.ViewGroup viewGroup, android.transition.Scene scene) {
        viewGroup.setTagInternal(com.android.internal.R.id.current_scene, scene);
    }

    public static android.transition.Scene getCurrentScene(android.view.ViewGroup viewGroup) {
        return (android.transition.Scene) viewGroup.getTag(com.android.internal.R.id.current_scene);
    }

    public void setEnterAction(java.lang.Runnable runnable) {
        this.mEnterAction = runnable;
    }

    public void setExitAction(java.lang.Runnable runnable) {
        this.mExitAction = runnable;
    }

    boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
