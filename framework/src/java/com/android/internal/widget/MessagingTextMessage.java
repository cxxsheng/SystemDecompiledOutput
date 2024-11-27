package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingTextMessage extends com.android.internal.widget.ImageFloatingTextView implements com.android.internal.widget.MessagingMessage {
    private static final java.lang.String TAG = "MessagingTextMessage";
    private static final com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingTextMessage> sInstancePool = new com.android.internal.widget.MessagingPool<>(20);
    private android.text.PrecomputedText mPrecomputedText;
    private final com.android.internal.widget.MessagingMessageState mState;

    public MessagingTextMessage(android.content.Context context) {
        super(context);
        this.mState = new com.android.internal.widget.MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = new com.android.internal.widget.MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mState = new com.android.internal.widget.MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mState = new com.android.internal.widget.MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public com.android.internal.widget.MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(android.app.Notification.MessagingStyle.Message message, boolean z) {
        super.setMessage(message, z);
        if (z) {
            this.mPrecomputedText = android.text.PrecomputedText.create(message.getText(), getTextMetricsParams());
            return true;
        }
        lambda$setTextAsync$0(message.getText());
        this.mPrecomputedText = null;
        return true;
    }

    static com.android.internal.widget.MessagingMessage createMessage(com.android.internal.widget.IMessagingLayout iMessagingLayout, android.app.Notification.MessagingStyle.Message message, boolean z) {
        com.android.internal.widget.MessagingLinearLayout messagingLinearLayout = iMessagingLayout.getMessagingLinearLayout();
        com.android.internal.widget.MessagingTextMessage acquire = sInstancePool.acquire();
        if (acquire == null) {
            acquire = (com.android.internal.widget.MessagingTextMessage) android.view.LayoutInflater.from(iMessagingLayout.getContext()).inflate(com.android.internal.R.layout.notification_template_messaging_text_message, (android.view.ViewGroup) messagingLinearLayout, false);
            acquire.addOnLayoutChangeListener(com.android.internal.widget.MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        acquire.setMessage(message, z);
        return acquire;
    }

    @Override // com.android.internal.widget.MessagingMessage, com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        super.recycle();
        sInstancePool.release((com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingTextMessage>) this);
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        android.text.Layout layout;
        if ((!(getMeasuredHeight() < (getLayoutHeight() + getPaddingTop()) + getPaddingBottom()) || getLineCount() > 1) && (layout = getLayout()) != null) {
            return layout.getEllipsisCount(layout.getLineCount() - 1) > 0 ? 1 : 0;
        }
        return 2;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
        setMaxLines(i);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        return getLineCount();
    }

    public int getLayoutHeight() {
        android.text.Layout layout = getLayout();
        if (layout == null) {
            return 0;
        }
        return layout.getHeight();
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void setColor(int i) {
        setTextColor(i);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        try {
            lambda$setTextAsync$0(this.mPrecomputedText != null ? this.mPrecomputedText : getState().getMessage().getText());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.wtf(TAG, "PrecomputedText setText failed for TextView:" + this, e);
            this.mPrecomputedText = null;
            lambda$setTextAsync$0(getState().getMessage().getText());
        }
    }
}
