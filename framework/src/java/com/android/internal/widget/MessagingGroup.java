package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingGroup extends com.android.internal.widget.NotificationOptimizedLinearLayout implements com.android.internal.widget.MessagingLinearLayout.MessagingChild {
    public static final int IMAGE_DISPLAY_LOCATION_AT_END = 1;
    public static final int IMAGE_DISPLAY_LOCATION_EXTERNAL = 2;
    public static final int IMAGE_DISPLAY_LOCATION_INLINE = 0;
    private static final com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingGroup> sInstancePool = new com.android.internal.widget.MessagingPool<>(10);
    private java.util.ArrayList<com.android.internal.widget.MessagingMessage> mAddedMessages;
    private android.view.View mAvatarContainer;
    private android.graphics.drawable.Icon mAvatarIcon;
    private java.lang.CharSequence mAvatarName;
    private java.lang.String mAvatarSymbol;
    private android.widget.ImageView mAvatarView;
    private boolean mCanHideSenderIfFirst;
    private boolean mClippingDisabled;
    private android.widget.LinearLayout mContentContainer;
    private int mConversationAvatarSize;
    private int mConversationContentStart;
    private android.graphics.Point mDisplaySize;
    private boolean mFirstLayout;
    private android.view.ViewGroup mImageContainer;
    private int mImageDisplayLocation;
    private boolean mIsFirstGroupInLayout;
    private boolean mIsHidingAnimated;
    private boolean mIsInConversation;
    private com.android.internal.widget.MessagingImageMessage mIsolatedMessage;
    private int mLayoutColor;
    private com.android.internal.widget.MessagingLinearLayout mMessageContainer;
    private java.util.List<com.android.internal.widget.MessagingMessage> mMessages;
    private android.view.ViewGroup mMessagingIconContainer;
    private boolean mNeedsGeneratedAvatar;
    private int mNonConversationAvatarSize;
    private int mNonConversationContentStart;
    private int mNonConversationPaddingStart;
    private int mNotificationTextMarginTop;
    private int mRequestedMaxDisplayedLines;
    private android.app.Person mSender;
    private java.lang.CharSequence mSenderName;
    private int mSenderTextPaddingSingleLine;
    com.android.internal.widget.ImageFloatingTextView mSenderView;
    private android.widget.ProgressBar mSendingSpinner;
    private android.view.View mSendingSpinnerContainer;
    private int mSendingTextColor;
    private boolean mShowingAvatar;
    private boolean mSingleLine;
    private int mTextColor;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ImageDisplayLocation {
    }

    public MessagingGroup(android.content.Context context) {
        super(context);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new java.util.ArrayList<>();
        this.mDisplaySize = new android.graphics.Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new java.util.ArrayList<>();
        this.mDisplaySize = new android.graphics.Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new java.util.ArrayList<>();
        this.mDisplaySize = new android.graphics.Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    public MessagingGroup(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
        this.mAddedMessages = new java.util.ArrayList<>();
        this.mDisplaySize = new android.graphics.Point();
        this.mShowingAvatar = true;
        this.mSingleLine = false;
        this.mRequestedMaxDisplayedLines = Integer.MAX_VALUE;
        this.mIsFirstGroupInLayout = true;
        this.mIsInConversation = true;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageContainer = (com.android.internal.widget.MessagingLinearLayout) findViewById(com.android.internal.R.id.group_message_container);
        this.mSenderView = (com.android.internal.widget.ImageFloatingTextView) findViewById(com.android.internal.R.id.message_name);
        this.mAvatarView = (android.widget.ImageView) findViewById(com.android.internal.R.id.message_icon);
        this.mImageContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.messaging_group_icon_container);
        this.mSendingSpinner = (android.widget.ProgressBar) findViewById(com.android.internal.R.id.messaging_group_sending_progress);
        this.mMessagingIconContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.message_icon_container);
        this.mContentContainer = (android.widget.LinearLayout) findViewById(com.android.internal.R.id.messaging_group_content_container);
        this.mSendingSpinnerContainer = findViewById(com.android.internal.R.id.messaging_group_sending_progress_container);
        android.content.res.Resources resources = getResources();
        android.util.DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mDisplaySize.x = displayMetrics.widthPixels;
        this.mDisplaySize.y = displayMetrics.heightPixels;
        this.mSenderTextPaddingSingleLine = resources.getDimensionPixelSize(com.android.internal.R.dimen.messaging_group_singleline_sender_padding_end);
        this.mConversationContentStart = resources.getDimensionPixelSize(com.android.internal.R.dimen.conversation_content_start);
        this.mNonConversationContentStart = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_content_margin_start);
        this.mNonConversationPaddingStart = resources.getDimensionPixelSize(com.android.internal.R.dimen.messaging_layout_icon_padding_start);
        this.mConversationAvatarSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.messaging_avatar_size);
        this.mNonConversationAvatarSize = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_icon_circle_size);
        this.mNotificationTextMarginTop = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_text_margin_top);
    }

    public void updateClipRect() {
        android.graphics.Rect rect;
        int distanceFromParent;
        if (this.mSenderView.getVisibility() != 8 && !this.mClippingDisabled) {
            if (this.mSingleLine) {
                distanceFromParent = 0;
            } else {
                distanceFromParent = (getDistanceFromParent(this.mSenderView, this.mContentContainer) - getDistanceFromParent(this.mMessageContainer, this.mContentContainer)) + this.mSenderView.getHeight();
            }
            int max = java.lang.Math.max(this.mDisplaySize.x, this.mDisplaySize.y);
            rect = new android.graphics.Rect(-max, distanceFromParent, max, max);
        } else {
            rect = null;
        }
        this.mMessageContainer.setClipBounds(rect);
    }

    private int getDistanceFromParent(android.view.View view, android.view.ViewGroup viewGroup) {
        int i = 0;
        while (view != viewGroup) {
            i = (int) (i + view.getTop() + view.getTranslationY());
            view = (android.view.View) view.getParent();
        }
        return i;
    }

    public void setSender(android.app.Person person, java.lang.CharSequence charSequence) {
        this.mSender = person;
        if (charSequence == null) {
            charSequence = person.getName();
        }
        this.mSenderName = charSequence;
        if (this.mSingleLine && !android.text.TextUtils.isEmpty(charSequence)) {
            charSequence = this.mContext.getResources().getString(com.android.internal.R.string.conversation_single_line_name_display, charSequence);
        }
        this.mSenderView.lambda$setTextAsync$0(charSequence);
        this.mNeedsGeneratedAvatar = person.getIcon() == null;
        if (!this.mNeedsGeneratedAvatar) {
            setAvatar(person.getIcon());
        }
        updateSenderVisibility();
    }

    public void setShowingAvatar(boolean z) {
        this.mAvatarView.setVisibility(z ? 0 : 8);
        this.mShowingAvatar = z;
    }

    public void setSending(boolean z) {
        int i = z ? 0 : 8;
        if (this.mSendingSpinnerContainer.getVisibility() != i) {
            this.mSendingSpinnerContainer.setVisibility(i);
            updateMessageColor();
        }
    }

    private int calculateSendingTextColor() {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        this.mContext.getResources().getValue(com.android.internal.R.dimen.notification_secondary_text_disabled_alpha, typedValue, true);
        return android.graphics.Color.valueOf(android.graphics.Color.red(this.mTextColor), android.graphics.Color.green(this.mTextColor), android.graphics.Color.blue(this.mTextColor), typedValue.getFloat()).toArgb();
    }

    public void setAvatar(android.graphics.drawable.Icon icon) {
        this.mAvatarIcon = icon;
        if (this.mShowingAvatar || icon == null) {
            this.mAvatarView.setImageIcon(icon);
        }
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
    }

    static com.android.internal.widget.MessagingGroup createGroup(com.android.internal.widget.MessagingLinearLayout messagingLinearLayout) {
        com.android.internal.widget.MessagingGroup acquire = sInstancePool.acquire();
        if (acquire == null) {
            acquire = (com.android.internal.widget.MessagingGroup) android.view.LayoutInflater.from(messagingLinearLayout.getContext()).inflate(com.android.internal.R.layout.notification_template_messaging_group, (android.view.ViewGroup) messagingLinearLayout, false);
            acquire.addOnLayoutChangeListener(com.android.internal.widget.MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingLinearLayout.addView(acquire);
        return acquire;
    }

    public void removeMessage(final com.android.internal.widget.MessagingMessage messagingMessage, java.util.ArrayList<com.android.internal.widget.MessagingLinearLayout.MessagingChild> arrayList) {
        final android.view.View view = messagingMessage.getView();
        boolean isShown = view.isShown();
        final android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeView(view);
        if (isShown && !com.android.internal.widget.MessagingLinearLayout.isGone(view)) {
            viewGroup.addTransientView(view, 0);
            performRemoveAnimation(view, new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.MessagingGroup.lambda$removeMessage$0(android.view.ViewGroup.this, view, messagingMessage);
                }
            });
        } else {
            arrayList.add(messagingMessage);
        }
    }

    static /* synthetic */ void lambda$removeMessage$0(android.view.ViewGroup viewGroup, android.view.View view, com.android.internal.widget.MessagingMessage messagingMessage) {
        viewGroup.removeTransientView(view);
        messagingMessage.recycle();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        if (this.mIsolatedMessage != null) {
            this.mImageContainer.removeView(this.mIsolatedMessage);
        }
        for (int i = 0; i < this.mMessages.size(); i++) {
            com.android.internal.widget.MessagingMessage messagingMessage = this.mMessages.get(i);
            this.mMessageContainer.removeView(messagingMessage.getView());
            messagingMessage.recycle();
        }
        setAvatar(null);
        this.mAvatarView.setAlpha(1.0f);
        this.mAvatarView.setTranslationY(0.0f);
        this.mSenderView.setAlpha(1.0f);
        this.mSenderView.setTranslationY(0.0f);
        setAlpha(1.0f);
        this.mIsolatedMessage = null;
        this.mMessages = null;
        this.mSenderName = null;
        this.mAddedMessages.clear();
        this.mFirstLayout = true;
        setCanHideSenderIfFirst(false);
        setIsFirstInLayout(true);
        setMaxDisplayedLines(Integer.MAX_VALUE);
        setSingleLine(false);
        setShowingAvatar(true);
        com.android.internal.widget.MessagingPropertyAnimator.recycle(this);
        sInstancePool.release((com.android.internal.widget.MessagingPool<com.android.internal.widget.MessagingGroup>) this);
    }

    public void removeGroupAnimated(final java.lang.Runnable runnable) {
        performRemoveAnimation(this, new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.MessagingGroup.this.lambda$removeGroupAnimated$1(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroupAnimated$1(java.lang.Runnable runnable) {
        setAlpha(1.0f);
        com.android.internal.widget.MessagingPropertyAnimator.setToLaidOutPosition(this);
        if (runnable != null) {
            runnable.run();
        }
    }

    public void performRemoveAnimation(android.view.View view, java.lang.Runnable runnable) {
        performRemoveAnimation(view, -view.getHeight(), runnable);
    }

    private void performRemoveAnimation(android.view.View view, int i, java.lang.Runnable runnable) {
        com.android.internal.widget.MessagingPropertyAnimator.startLocalTranslationTo(view, i, com.android.internal.widget.MessagingLayout.FAST_OUT_LINEAR_IN);
        com.android.internal.widget.MessagingPropertyAnimator.fadeOut(view, runnable);
    }

    public java.lang.CharSequence getSenderName() {
        return this.mSenderName;
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        if (this.mIsolatedMessage != null) {
            return 1;
        }
        boolean z = false;
        for (int childCount = this.mMessageContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = this.mMessageContainer.getChildAt(childCount);
            if (childAt.getVisibility() != 8 && (childAt instanceof com.android.internal.widget.MessagingLinearLayout.MessagingChild)) {
                int measuredType = ((com.android.internal.widget.MessagingLinearLayout.MessagingChild) childAt).getMeasuredType();
                if (((com.android.internal.widget.MessagingLinearLayout.LayoutParams) childAt.getLayoutParams()).hide || (measuredType == 2)) {
                    return z ? 1 : 2;
                }
                if (measuredType == 1) {
                    return 1;
                }
                z = true;
            }
        }
        return 0;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        int i = 0;
        for (int i2 = 0; i2 < this.mMessageContainer.getChildCount(); i2++) {
            android.view.KeyEvent.Callback childAt = this.mMessageContainer.getChildAt(i2);
            if (childAt instanceof com.android.internal.widget.MessagingLinearLayout.MessagingChild) {
                i += ((com.android.internal.widget.MessagingLinearLayout.MessagingChild) childAt).getConsumedLines();
            }
        }
        if (this.mIsolatedMessage != null) {
            i = java.lang.Math.max(i, 1);
        }
        return i + 1;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
        this.mRequestedMaxDisplayedLines = i;
        updateMaxDisplayedLines();
    }

    private void updateMaxDisplayedLines() {
        this.mMessageContainer.setMaxDisplayedLines(this.mSingleLine ? 1 : this.mRequestedMaxDisplayedLines);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void hideAnimated() {
        setIsHidingAnimated(true);
        removeGroupAnimated(new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingGroup$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.MessagingGroup.this.lambda$hideAnimated$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideAnimated$2() {
        setIsHidingAnimated(false);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setIsFirstInLayout(boolean z) {
        if (z != this.mIsFirstGroupInLayout) {
            this.mIsFirstGroupInLayout = z;
            updateSenderVisibility();
        }
    }

    public void setCanHideSenderIfFirst(boolean z) {
        if (this.mCanHideSenderIfFirst != z) {
            this.mCanHideSenderIfFirst = z;
            updateSenderVisibility();
        }
    }

    private void updateSenderVisibility() {
        this.mSenderView.setVisibility(((this.mIsFirstGroupInLayout || this.mSingleLine) && this.mCanHideSenderIfFirst) || android.text.TextUtils.isEmpty(this.mSenderName) ? 8 : 0);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public boolean hasDifferentHeightWhenFirst() {
        return (!this.mCanHideSenderIfFirst || this.mSingleLine || android.text.TextUtils.isEmpty(this.mSenderName)) ? false : true;
    }

    private void setIsHidingAnimated(boolean z) {
        android.view.ViewParent parent = getParent();
        this.mIsHidingAnimated = z;
        invalidate();
        if (parent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) parent).invalidate();
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public android.graphics.drawable.Icon getAvatarSymbolIfMatching(java.lang.CharSequence charSequence, java.lang.String str, int i) {
        if (this.mAvatarName.equals(charSequence) && this.mAvatarSymbol.equals(str) && i == this.mLayoutColor) {
            return this.mAvatarIcon;
        }
        return null;
    }

    public void setCreatedAvatar(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, java.lang.String str, int i) {
        if (!this.mAvatarName.equals(charSequence) || !this.mAvatarSymbol.equals(str) || i != this.mLayoutColor) {
            setAvatar(icon);
            this.mAvatarSymbol = str;
            setLayoutColor(i);
            this.mAvatarName = charSequence;
        }
    }

    public void setTextColors(int i, int i2) {
        this.mTextColor = i2;
        this.mSendingTextColor = calculateSendingTextColor();
        updateMessageColor();
        this.mSenderView.setTextColor(i);
    }

    public void setLayoutColor(int i) {
        if (i != this.mLayoutColor) {
            this.mLayoutColor = i;
            this.mSendingSpinner.setIndeterminateTintList(android.content.res.ColorStateList.valueOf(this.mLayoutColor));
        }
    }

    private void updateMessageColor() {
        if (this.mMessages != null) {
            int i = this.mSendingSpinnerContainer.getVisibility() == 0 ? this.mSendingTextColor : this.mTextColor;
            for (com.android.internal.widget.MessagingMessage messagingMessage : this.mMessages) {
                messagingMessage.setColor(messagingMessage.getMessage() != null && messagingMessage.getMessage().isRemoteInputHistory() ? i : this.mTextColor);
            }
        }
    }

    public void setMessages(java.util.List<com.android.internal.widget.MessagingMessage> list) {
        com.android.internal.widget.MessagingImageMessage messagingImageMessage = null;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.internal.widget.MessagingMessage messagingMessage = list.get(i2);
            if (messagingMessage.getGroup() != this) {
                messagingMessage.setMessagingGroup(this);
                this.mAddedMessages.add(messagingMessage);
            }
            boolean z = messagingMessage instanceof com.android.internal.widget.MessagingImageMessage;
            if (this.mImageDisplayLocation != 0 && z) {
                messagingImageMessage = (com.android.internal.widget.MessagingImageMessage) messagingMessage;
            } else {
                if (removeFromParentIfDifferent(messagingMessage, this.mMessageContainer)) {
                    android.view.ViewGroup.LayoutParams layoutParams = messagingMessage.getView().getLayoutParams();
                    if (layoutParams != null && !(layoutParams instanceof com.android.internal.widget.MessagingLinearLayout.LayoutParams)) {
                        messagingMessage.getView().setLayoutParams(this.mMessageContainer.generateDefaultLayoutParams());
                    }
                    this.mMessageContainer.addView(messagingMessage.getView(), i);
                }
                if (z) {
                    ((com.android.internal.widget.MessagingImageMessage) messagingMessage).setIsolated(false);
                }
                if (i != this.mMessageContainer.indexOfChild(messagingMessage.getView())) {
                    this.mMessageContainer.removeView(messagingMessage.getView());
                    this.mMessageContainer.addView(messagingMessage.getView(), i);
                }
                i++;
            }
        }
        if (messagingImageMessage != null) {
            if (this.mImageDisplayLocation == 1 && removeFromParentIfDifferent(messagingImageMessage, this.mImageContainer)) {
                this.mImageContainer.removeAllViews();
                this.mImageContainer.addView(messagingImageMessage.getView());
            } else if (this.mImageDisplayLocation == 2) {
                this.mImageContainer.removeAllViews();
            }
            messagingImageMessage.setIsolated(true);
        } else if (this.mIsolatedMessage != null) {
            this.mImageContainer.removeAllViews();
        }
        this.mIsolatedMessage = messagingImageMessage;
        updateImageContainerVisibility();
        this.mMessages = list;
        updateMessageColor();
    }

    private void updateImageContainerVisibility() {
        this.mImageContainer.setVisibility((this.mIsolatedMessage == null || this.mImageDisplayLocation != 1) ? 8 : 0);
    }

    private boolean removeFromParentIfDifferent(com.android.internal.widget.MessagingMessage messagingMessage, android.view.ViewGroup viewGroup) {
        android.view.ViewParent parent = messagingMessage.getView().getParent();
        if (parent != viewGroup) {
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).removeView(messagingMessage.getView());
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // com.android.internal.widget.NotificationOptimizedLinearLayout, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!this.mAddedMessages.isEmpty()) {
            final boolean z2 = this.mFirstLayout;
            getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.MessagingGroup.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    java.util.Iterator it = com.android.internal.widget.MessagingGroup.this.mAddedMessages.iterator();
                    while (it.hasNext()) {
                        com.android.internal.widget.MessagingMessage messagingMessage = (com.android.internal.widget.MessagingMessage) it.next();
                        if (messagingMessage.getView().isShown()) {
                            com.android.internal.widget.MessagingPropertyAnimator.fadeIn(messagingMessage.getView());
                            if (!z2) {
                                com.android.internal.widget.MessagingPropertyAnimator.startLocalTranslationFrom(messagingMessage.getView(), messagingMessage.getView().getHeight(), com.android.internal.widget.MessagingLayout.LINEAR_OUT_SLOW_IN);
                            }
                        }
                    }
                    com.android.internal.widget.MessagingGroup.this.mAddedMessages.clear();
                    com.android.internal.widget.MessagingGroup.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mFirstLayout = false;
        updateClipRect();
    }

    public int calculateGroupCompatibility(com.android.internal.widget.MessagingGroup messagingGroup) {
        if (!android.text.TextUtils.equals(getSenderName(), messagingGroup.getSenderName())) {
            return 0;
        }
        int i = 1;
        for (int i2 = 0; i2 < this.mMessages.size() && i2 < messagingGroup.mMessages.size(); i2++) {
            if (!this.mMessages.get((this.mMessages.size() - 1) - i2).sameAs(messagingGroup.mMessages.get((messagingGroup.mMessages.size() - 1) - i2))) {
                return i;
            }
            i++;
        }
        return i;
    }

    public android.widget.TextView getSenderView() {
        return this.mSenderView;
    }

    public android.view.View getAvatar() {
        return this.mAvatarView;
    }

    public android.graphics.drawable.Icon getAvatarIcon() {
        return this.mAvatarIcon;
    }

    public com.android.internal.widget.MessagingLinearLayout getMessageContainer() {
        return this.mMessageContainer;
    }

    public com.android.internal.widget.MessagingImageMessage getIsolatedMessage() {
        return this.mIsolatedMessage;
    }

    public boolean needsGeneratedAvatar() {
        return this.mNeedsGeneratedAvatar;
    }

    public android.app.Person getSender() {
        return this.mSender;
    }

    public void setClippingDisabled(boolean z) {
        this.mClippingDisabled = z;
    }

    public void setImageDisplayLocation(int i) {
        if (this.mImageDisplayLocation != i) {
            this.mImageDisplayLocation = i;
            updateImageContainerVisibility();
        }
    }

    public java.util.List<com.android.internal.widget.MessagingMessage> getMessages() {
        return this.mMessages;
    }

    public void setSingleLine(boolean z) {
        if (z != this.mSingleLine) {
            this.mSingleLine = z;
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mMessageContainer.getLayoutParams();
            marginLayoutParams.topMargin = z ? 0 : this.mNotificationTextMarginTop;
            this.mMessageContainer.setLayoutParams(marginLayoutParams);
            this.mContentContainer.setOrientation(!z ? 1 : 0);
            ((android.view.ViewGroup.MarginLayoutParams) this.mSenderView.getLayoutParams()).setMarginEnd(z ? this.mSenderTextPaddingSingleLine : 0);
            this.mSenderView.setSingleLine(z);
            updateMaxDisplayedLines();
            updateClipRect();
            updateSenderVisibility();
        }
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    public void setIsInConversation(boolean z) {
        int i;
        if (this.mIsInConversation != z) {
            this.mIsInConversation = z;
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mMessagingIconContainer.getLayoutParams();
            if (this.mIsInConversation) {
                i = this.mConversationContentStart;
            } else {
                i = this.mNonConversationContentStart;
            }
            marginLayoutParams.width = i;
            this.mMessagingIconContainer.setLayoutParams(marginLayoutParams);
            this.mMessagingIconContainer.setPaddingRelative(z ? 0 : this.mNonConversationPaddingStart, 0, 0, 0);
            android.view.ViewGroup.LayoutParams layoutParams = this.mAvatarView.getLayoutParams();
            int i2 = this.mIsInConversation ? this.mConversationAvatarSize : this.mNonConversationAvatarSize;
            layoutParams.height = i2;
            layoutParams.width = i2;
            this.mAvatarView.setLayoutParams(layoutParams);
        }
    }
}
