package com.android.internal.widget.remotecompose.player;

/* loaded from: classes5.dex */
public class RemoteComposePlayer extends android.widget.FrameLayout {
    private static final int MAX_SUPPORTED_MAJOR_VERSION = 0;
    private static final int MAX_SUPPORTED_MINOR_VERSION = 1;
    private com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas mInner;

    public interface ClickCallbacks {
        void click(int i, java.lang.String str);
    }

    public RemoteComposePlayer(android.content.Context context) {
        super(context);
        init(context, null, 0);
    }

    public RemoteComposePlayer(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet, 0);
    }

    public RemoteComposePlayer(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    public void setDebug(int i) {
        if (i == 1) {
            this.mInner.setDebug(true);
        } else {
            this.mInner.setDebug(false);
        }
    }

    public void setDocument(com.android.internal.widget.remotecompose.player.RemoteComposeDocument remoteComposeDocument) {
        if (remoteComposeDocument != null) {
            if (remoteComposeDocument.canBeDisplayed(0, 1, 0L)) {
                this.mInner.setDocument(remoteComposeDocument);
                applyContentBehavior(remoteComposeDocument.getDocument().getContentScroll());
                return;
            } else {
                android.util.Log.e("RemoteComposePlayer", "Unsupported document ");
                return;
            }
        }
        this.mInner.setDocument(null);
    }

    private void applyContentBehavior(int i) {
        switch (i) {
            case 1:
                if (!(this.mInner.getParent() instanceof android.widget.HorizontalScrollView)) {
                    ((android.view.ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(-2, -1);
                    android.widget.HorizontalScrollView horizontalScrollView = new android.widget.HorizontalScrollView(getContext());
                    horizontalScrollView.setFillViewport(true);
                    horizontalScrollView.addView(this.mInner, layoutParams);
                    addView(horizontalScrollView, new android.widget.FrameLayout.LayoutParams(-1, -1));
                    break;
                }
                break;
            case 2:
                if (!(this.mInner.getParent() instanceof android.widget.ScrollView)) {
                    ((android.view.ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    android.widget.FrameLayout.LayoutParams layoutParams2 = new android.widget.FrameLayout.LayoutParams(-1, -2);
                    android.widget.ScrollView scrollView = new android.widget.ScrollView(getContext());
                    scrollView.setFillViewport(true);
                    scrollView.addView(this.mInner, layoutParams2);
                    addView(scrollView, new android.widget.FrameLayout.LayoutParams(-1, -1));
                    break;
                }
                break;
            default:
                if (this.mInner.getParent() != this) {
                    ((android.view.ViewGroup) this.mInner.getParent()).removeView(this.mInner);
                    removeAllViews();
                    addView(this.mInner, new android.widget.FrameLayout.LayoutParams(-1, -1));
                    break;
                }
                break;
        }
    }

    private void init(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(-1, -1);
        this.mInner = new com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas(context, attributeSet, i);
        addView(this.mInner, layoutParams);
    }

    public void addClickListener(final com.android.internal.widget.remotecompose.player.RemoteComposePlayer.ClickCallbacks clickCallbacks) {
        this.mInner.addClickListener(new com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks() { // from class: com.android.internal.widget.remotecompose.player.RemoteComposePlayer$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.ClickCallbacks
            public final void click(int i, java.lang.String str) {
                com.android.internal.widget.remotecompose.player.RemoteComposePlayer.ClickCallbacks.this.click(i, str);
            }
        });
    }

    public void setTheme(int i) {
        if (this.mInner.getTheme() != i) {
            this.mInner.setTheme(i);
            this.mInner.invalidate();
        }
    }
}
