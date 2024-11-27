package android.view;

/* loaded from: classes4.dex */
public class ViewAnimationHostBridge implements android.graphics.RenderNode.AnimationHost {
    private final android.view.View mView;

    public ViewAnimationHostBridge(android.view.View view) {
        this.mView = view;
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public void registerAnimatingRenderNode(android.graphics.RenderNode renderNode) {
        this.mView.mAttachInfo.mViewRootImpl.registerAnimatingRenderNode(renderNode);
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public void registerVectorDrawableAnimator(android.view.NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        this.mView.mAttachInfo.mViewRootImpl.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
    }

    @Override // android.graphics.RenderNode.AnimationHost
    public boolean isAttached() {
        return this.mView.mAttachInfo != null;
    }
}
