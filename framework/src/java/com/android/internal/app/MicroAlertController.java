package com.android.internal.app;

/* loaded from: classes4.dex */
public class MicroAlertController extends com.android.internal.app.AlertController {
    public MicroAlertController(android.content.Context context, android.content.DialogInterface dialogInterface, android.view.Window window) {
        super(context, dialogInterface, window);
    }

    @Override // com.android.internal.app.AlertController
    protected void setupContent(android.view.ViewGroup viewGroup) {
        this.mScrollView = (android.widget.ScrollView) this.mWindow.findViewById(com.android.internal.R.id.scrollView);
        this.mMessageView = (android.widget.TextView) viewGroup.findViewById(16908299);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.lambda$setTextAsync$0(this.mMessage);
            return;
        }
        this.mMessageView.setVisibility(8);
        viewGroup.removeView(this.mMessageView);
        if (this.mListView != null) {
            android.view.View findViewById = this.mScrollView.findViewById(com.android.internal.R.id.topPanel);
            ((android.view.ViewGroup) findViewById.getParent()).removeView(findViewById);
            android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(findViewById.getLayoutParams());
            layoutParams.gravity = 48;
            findViewById.setLayoutParams(layoutParams);
            android.view.View findViewById2 = this.mScrollView.findViewById(com.android.internal.R.id.buttonPanel);
            ((android.view.ViewGroup) findViewById2.getParent()).removeView(findViewById2);
            android.widget.FrameLayout.LayoutParams layoutParams2 = new android.widget.FrameLayout.LayoutParams(findViewById2.getLayoutParams());
            layoutParams2.gravity = 80;
            findViewById2.setLayoutParams(layoutParams2);
            android.view.ViewGroup viewGroup2 = (android.view.ViewGroup) this.mScrollView.getParent();
            viewGroup2.removeViewAt(viewGroup2.indexOfChild(this.mScrollView));
            viewGroup2.addView(this.mListView, new android.view.ViewGroup.LayoutParams(-1, -1));
            viewGroup2.addView(findViewById);
            viewGroup2.addView(findViewById2);
            return;
        }
        viewGroup.setVisibility(8);
    }

    @Override // com.android.internal.app.AlertController
    protected void setupTitle(android.view.ViewGroup viewGroup) {
        super.setupTitle(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }

    @Override // com.android.internal.app.AlertController
    protected void setupButtons(android.view.ViewGroup viewGroup) {
        super.setupButtons(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }
}
