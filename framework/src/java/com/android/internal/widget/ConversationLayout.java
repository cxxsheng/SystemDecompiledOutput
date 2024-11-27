package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ConversationLayout extends android.widget.FrameLayout implements com.android.internal.widget.ImageMessageConsumer, com.android.internal.widget.IMessagingLayout {
    public static final int IMPORTANCE_ANIM_GROW_DURATION = 250;
    public static final int IMPORTANCE_ANIM_SHRINK_DELAY = 25;
    public static final int IMPORTANCE_ANIM_SHRINK_DURATION = 200;
    private com.android.internal.widget.NotificationActionListLayout mActions;
    private java.util.ArrayList<com.android.internal.widget.MessagingGroup> mAddedGroups;
    private com.android.internal.widget.ObservableTextView mAppName;
    private android.view.View mAppNameDivider;
    private boolean mAppNameGone;
    private android.graphics.drawable.Icon mAvatarReplacement;
    private int mBadgeProtrusion;
    private android.view.View mContentContainer;
    private int mContentMarginEnd;
    private int mConversationAvatarSize;
    private int mConversationAvatarSizeExpanded;
    private android.view.View mConversationFacePile;
    private android.view.View mConversationHeader;
    private android.graphics.drawable.Icon mConversationIcon;
    private android.view.View mConversationIconBadge;
    private com.android.internal.widget.CachingIconView mConversationIconBadgeBg;
    private android.view.View mConversationIconContainer;
    private int mConversationIconTopPadding;
    private int mConversationIconTopPaddingExpandedGroup;
    private com.android.internal.widget.CachingIconView mConversationIconView;
    private android.widget.TextView mConversationText;
    private java.lang.CharSequence mConversationTitle;
    private com.android.internal.widget.NotificationExpandButton mExpandButton;
    private android.view.ViewGroup mExpandButtonAndContentContainer;
    private android.view.View mExpandButtonContainer;
    private android.view.ViewGroup mExpandButtonContainerA11yContainer;
    private boolean mExpandable;
    private int mExpandedGroupBadgeProtrusion;
    private int mExpandedGroupBadgeProtrusionFacePile;
    private int mExpandedGroupMessagePadding;
    private int mFacePileAvatarSize;
    private int mFacePileAvatarSizeExpandedGroup;
    private int mFacePileProtectionWidth;
    private int mFacePileProtectionWidthExpanded;
    private java.lang.CharSequence mFallbackChatName;
    private java.lang.CharSequence mFallbackGroupChatName;
    private android.view.View mFeedbackIcon;
    private java.util.ArrayList<com.android.internal.widget.MessagingGroup> mGroups;
    private java.util.List<com.android.internal.widget.MessagingMessage> mHistoricMessages;
    private com.android.internal.widget.CachingIconView mIcon;
    private com.android.internal.widget.MessagingLinearLayout mImageMessageContainer;
    private com.android.internal.widget.ImageResolver mImageResolver;
    private com.android.internal.widget.CachingIconView mImportanceRingView;
    private boolean mImportantConversation;
    private boolean mIsCollapsed;
    private boolean mIsOneToOne;
    private android.graphics.drawable.Icon mLargeIcon;
    private int mLayoutColor;
    private int mMessageSpacingGroup;
    private int mMessageSpacingStandard;
    private int mMessageTextColor;
    private java.util.List<com.android.internal.widget.MessagingMessage> mMessages;
    private android.graphics.Rect mMessagingClipRect;
    private com.android.internal.widget.MessagingLinearLayout mMessagingLinearLayout;
    private float mMinTouchSize;
    private java.lang.CharSequence mNameReplacement;
    private int mNotificationBackgroundColor;
    private int mNotificationHeaderExpandedPadding;
    private final com.android.internal.widget.PeopleHelper mPeopleHelper;
    private boolean mPrecomputedTextEnabled;
    private int mSenderTextColor;
    private android.graphics.drawable.Icon mShortcutIcon;
    private boolean mShowHistoricMessages;
    private java.util.ArrayList<com.android.internal.widget.MessagingLinearLayout.MessagingChild> mToRecycle;
    private com.android.internal.widget.ConversationLayout.TouchDelegateComposite mTouchDelegate;
    private android.app.Person mUser;
    public static final android.view.animation.Interpolator LINEAR_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    public static final android.view.animation.Interpolator FAST_OUT_LINEAR_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final android.view.animation.Interpolator FAST_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final android.view.animation.Interpolator OVERSHOOT = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
    public static final android.view.View.OnLayoutChangeListener MESSAGING_PROPERTY_ANIMATOR = new com.android.internal.widget.MessagingPropertyAnimator();

    public ConversationLayout(android.content.Context context) {
        super(context);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mExpandable = true;
        this.mTouchDelegate = new com.android.internal.widget.ConversationLayout.TouchDelegateComposite(this);
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mExpandable = true;
        this.mTouchDelegate = new com.android.internal.widget.ConversationLayout.TouchDelegateComposite(this);
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mExpandable = true;
        this.mTouchDelegate = new com.android.internal.widget.ConversationLayout.TouchDelegateComposite(this);
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public ConversationLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mExpandable = true;
        this.mTouchDelegate = new com.android.internal.widget.ConversationLayout.TouchDelegateComposite(this);
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mMessagingLinearLayout = (com.android.internal.widget.MessagingLinearLayout) findViewById(com.android.internal.R.id.notification_messaging);
        this.mActions = (com.android.internal.widget.NotificationActionListLayout) findViewById(com.android.internal.R.id.actions);
        this.mImageMessageContainer = (com.android.internal.widget.MessagingLinearLayout) findViewById(com.android.internal.R.id.conversation_image_message_container);
        android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int max = java.lang.Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMessagingClipRect = new android.graphics.Rect(0, 0, max, max);
        setMessagingClippingDisabled(false);
        this.mConversationIconView = (com.android.internal.widget.CachingIconView) findViewById(com.android.internal.R.id.conversation_icon);
        this.mConversationIconContainer = findViewById(com.android.internal.R.id.conversation_icon_container);
        this.mIcon = (com.android.internal.widget.CachingIconView) findViewById(16908294);
        this.mFeedbackIcon = findViewById(com.android.internal.R.id.feedback);
        this.mMinTouchSize = getResources().getDisplayMetrics().density * 48.0f;
        this.mImportanceRingView = (com.android.internal.widget.CachingIconView) findViewById(com.android.internal.R.id.conversation_icon_badge_ring);
        this.mConversationIconBadge = findViewById(com.android.internal.R.id.conversation_icon_badge);
        this.mConversationIconBadgeBg = (com.android.internal.widget.CachingIconView) findViewById(com.android.internal.R.id.conversation_icon_badge_bg);
        this.mIcon.setOnVisibilityChangedListener(new java.util.function.Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.widget.ConversationLayout.this.lambda$onFinishInflate$0((java.lang.Integer) obj);
            }
        });
        this.mIcon.setOnForceHiddenChangedListener(new java.util.function.Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.widget.ConversationLayout.this.lambda$onFinishInflate$1((java.lang.Boolean) obj);
            }
        });
        this.mConversationIconView.setOnForceHiddenChangedListener(new java.util.function.Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.widget.ConversationLayout.this.lambda$onFinishInflate$2((java.lang.Boolean) obj);
            }
        });
        this.mConversationText = (android.widget.TextView) findViewById(com.android.internal.R.id.conversation_text);
        this.mExpandButtonContainer = findViewById(com.android.internal.R.id.expand_button_container);
        this.mExpandButtonContainerA11yContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.expand_button_a11y_container);
        this.mConversationHeader = findViewById(com.android.internal.R.id.conversation_header);
        this.mContentContainer = findViewById(com.android.internal.R.id.notification_action_list_margin_target);
        this.mExpandButtonAndContentContainer = (android.view.ViewGroup) findViewById(com.android.internal.R.id.expand_button_and_content_container);
        this.mExpandButton = (com.android.internal.widget.NotificationExpandButton) findViewById(com.android.internal.R.id.expand_button);
        this.mMessageSpacingStandard = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_messaging_spacing);
        this.mMessageSpacingGroup = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_messaging_spacing_conversation_group);
        this.mNotificationHeaderExpandedPadding = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_header_expanded_padding_end);
        this.mContentMarginEnd = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_content_margin_end);
        this.mBadgeProtrusion = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_badge_protrusion);
        this.mConversationAvatarSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_avatar_size);
        this.mConversationAvatarSizeExpanded = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_avatar_size_group_expanded);
        this.mConversationIconTopPaddingExpandedGroup = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_icon_container_top_padding_small_avatar);
        this.mConversationIconTopPadding = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_icon_container_top_padding);
        this.mExpandedGroupMessagePadding = getResources().getDimensionPixelSize(com.android.internal.R.dimen.expanded_group_conversation_message_padding);
        this.mExpandedGroupBadgeProtrusion = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_badge_protrusion_group_expanded);
        this.mExpandedGroupBadgeProtrusionFacePile = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        this.mConversationFacePile = findViewById(com.android.internal.R.id.conversation_face_pile);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_face_pile_avatar_size);
        this.mFacePileAvatarSizeExpandedGroup = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_face_pile_avatar_size_group_expanded);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_face_pile_protection_width);
        this.mFacePileProtectionWidthExpanded = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_face_pile_protection_width_expanded);
        this.mFallbackChatName = getResources().getString(com.android.internal.R.string.conversation_title_fallback_one_to_one);
        this.mFallbackGroupChatName = getResources().getString(com.android.internal.R.string.conversation_title_fallback_group_chat);
        this.mAppName = (com.android.internal.widget.ObservableTextView) findViewById(com.android.internal.R.id.app_name_text);
        this.mAppNameDivider = findViewById(com.android.internal.R.id.app_name_divider);
        this.mAppNameGone = this.mAppName.getVisibility() == 8;
        this.mAppName.setOnVisibilityChangedListener(new java.util.function.Consumer() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.widget.ConversationLayout.this.lambda$onFinishInflate$3((java.lang.Integer) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(java.lang.Integer num) {
        boolean z = num.intValue() == 8;
        if ((this.mConversationIconBadgeBg.getVisibility() == 8) != z) {
            this.mConversationIconBadgeBg.animate().cancel();
            this.mConversationIconBadgeBg.setVisibility(num.intValue());
        }
        boolean z2 = this.mImportanceRingView.getVisibility() == 8;
        java.lang.Integer valueOf = java.lang.Integer.valueOf(!this.mImportantConversation ? 8 : num.intValue());
        if (z2 != (valueOf.intValue() == 8)) {
            this.mImportanceRingView.animate().cancel();
            this.mImportanceRingView.setVisibility(valueOf.intValue());
        }
        if ((this.mConversationIconBadge.getVisibility() == 8) != z) {
            this.mConversationIconBadge.animate().cancel();
            this.mConversationIconBadge.setVisibility(valueOf.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$1(java.lang.Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mImportanceRingView, bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$2(java.lang.Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mImportanceRingView, bool.booleanValue());
        this.mPeopleHelper.animateViewForceHidden(this.mIcon, bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$3(java.lang.Integer num) {
        onAppNameVisibilityChanged();
    }

    @android.view.RemotableViewMethod
    public void setAvatarReplacement(android.graphics.drawable.Icon icon) {
        this.mAvatarReplacement = icon;
    }

    @android.view.RemotableViewMethod
    public void setNameReplacement(java.lang.CharSequence charSequence) {
        this.mNameReplacement = charSequence;
    }

    @android.view.RemotableViewMethod
    public void setIsImportantConversation(boolean z) {
        setIsImportantConversation(z, false);
    }

    public void setIsImportantConversation(boolean z, boolean z2) {
        this.mImportantConversation = z;
        com.android.internal.widget.CachingIconView cachingIconView = this.mImportanceRingView;
        int i = 8;
        if (z && this.mIcon.getVisibility() != 8) {
            i = 0;
        }
        cachingIconView.setVisibility(i);
        if (z2 && z) {
            final android.graphics.drawable.GradientDrawable gradientDrawable = (android.graphics.drawable.GradientDrawable) this.mImportanceRingView.getDrawable();
            gradientDrawable.mutate();
            final android.graphics.drawable.GradientDrawable gradientDrawable2 = (android.graphics.drawable.GradientDrawable) this.mConversationIconBadgeBg.getDrawable();
            gradientDrawable2.mutate();
            final int color = getResources().getColor(com.android.internal.R.color.conversation_important_highlight);
            int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.importance_ring_stroke_width);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(com.android.internal.R.dimen.importance_ring_anim_max_stroke_width);
            final int dimensionPixelSize3 = getResources().getDimensionPixelSize(com.android.internal.R.dimen.importance_ring_size) - (dimensionPixelSize * 2);
            final int dimensionPixelSize4 = getResources().getDimensionPixelSize(com.android.internal.R.dimen.conversation_icon_size_badged);
            android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    com.android.internal.widget.ConversationLayout.this.lambda$setIsImportantConversation$4(gradientDrawable, color, dimensionPixelSize3, valueAnimator);
                }
            };
            float f = dimensionPixelSize2;
            android.animation.ValueAnimator ofFloat = android.animation.ValueAnimator.ofFloat(0.0f, f);
            ofFloat.setInterpolator(LINEAR_OUT_SLOW_IN);
            ofFloat.setDuration(250L);
            ofFloat.addUpdateListener(animatorUpdateListener);
            android.animation.ValueAnimator ofFloat2 = android.animation.ValueAnimator.ofFloat(f, dimensionPixelSize);
            ofFloat2.setDuration(200L);
            ofFloat2.setStartDelay(25L);
            ofFloat2.setInterpolator(OVERSHOOT);
            ofFloat2.addUpdateListener(animatorUpdateListener);
            ofFloat2.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.widget.ConversationLayout.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator) {
                    gradientDrawable2.setSize(dimensionPixelSize3, dimensionPixelSize3);
                    com.android.internal.widget.ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    gradientDrawable2.setSize(dimensionPixelSize4, dimensionPixelSize4);
                    com.android.internal.widget.ConversationLayout.this.mConversationIconBadgeBg.invalidate();
                }
            });
            android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
            animatorSet.playSequentially(ofFloat, ofFloat2);
            animatorSet.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsImportantConversation$4(android.graphics.drawable.GradientDrawable gradientDrawable, int i, int i2, android.animation.ValueAnimator valueAnimator) {
        int round = java.lang.Math.round(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
        gradientDrawable.setStroke(round, i);
        int i3 = i2 + (round * 2);
        gradientDrawable.setSize(i3, i3);
        this.mImportanceRingView.invalidate();
    }

    public boolean isImportantConversation() {
        return this.mImportantConversation;
    }

    @android.view.RemotableViewMethod
    public void setIsCollapsed(boolean z) {
        this.mIsCollapsed = z;
        this.mMessagingLinearLayout.setMaxDisplayedLines(z ? 1 : Integer.MAX_VALUE);
        updateExpandButton();
        updateContentEndPaddings();
    }

    @android.view.RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$5(android.os.Bundle bundle) {
        bind(parseMessagingData(bundle, false, false));
    }

    private com.android.internal.widget.MessagingData parseMessagingData(android.os.Bundle bundle, boolean z, boolean z2) {
        com.android.internal.widget.ConversationHeaderData conversationHeaderData;
        java.util.List<android.app.Notification.MessagingStyle.Message> messagesFromBundleArray = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(android.app.Notification.EXTRA_MESSAGES));
        java.util.List<android.app.Notification.MessagingStyle.Message> messagesFromBundleArray2 = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(android.app.Notification.EXTRA_HISTORIC_MESSAGES));
        android.app.Person person = (android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_MESSAGING_PERSON, android.app.Person.class);
        addRemoteInputHistoryToMessages(messagesFromBundleArray, (android.app.RemoteInputHistoryItem[]) bundle.getParcelableArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, android.app.RemoteInputHistoryItem.class));
        boolean z3 = bundle.getBoolean(android.app.Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, false);
        int i = bundle.getInt(android.app.Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
        java.util.List<com.android.internal.widget.MessagingMessage> createMessages = createMessages(messagesFromBundleArray, false, z);
        java.util.List<com.android.internal.widget.MessagingMessage> createMessages2 = createMessages(messagesFromBundleArray2, true, z);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        findGroups(createMessages2, createMessages, person, arrayList, arrayList2);
        if (z2 && android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            conversationHeaderData = loadConversationHeaderData(this.mIsOneToOne, this.mConversationTitle, this.mShortcutIcon, this.mLargeIcon, createMessages, person, arrayList, this.mLayoutColor);
        } else {
            conversationHeaderData = null;
        }
        return new com.android.internal.widget.MessagingData(person, z3, i, createMessages2, createMessages, arrayList, arrayList2, conversationHeaderData);
    }

    public java.lang.Runnable setDataAsync(final android.os.Bundle bundle) {
        if (!this.mPrecomputedTextEnabled) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setDataAsync$5(bundle);
                }
            };
        }
        final com.android.internal.widget.MessagingData parseMessagingData = parseMessagingData(bundle, true, true);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.ConversationLayout.this.lambda$setDataAsync$6(parseMessagingData);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAsync$6(com.android.internal.widget.MessagingData messagingData) {
        finalizeInflate(messagingData.getHistoricMessagingMessages());
        finalizeInflate(messagingData.getNewMessagingMessages());
        bind(messagingData);
    }

    public void setPrecomputedTextEnabled(boolean z) {
        this.mPrecomputedTextEnabled = z;
    }

    private void finalizeInflate(java.util.List<com.android.internal.widget.MessagingMessage> list) {
        java.util.Iterator<com.android.internal.widget.MessagingMessage> it = list.iterator();
        while (it.hasNext()) {
            it.next().finalizeInflate();
        }
    }

    @Override // com.android.internal.widget.ImageMessageConsumer
    public void setImageResolver(com.android.internal.widget.ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    public void setUnreadCount(int i) {
        this.mExpandButton.setNumber(i);
    }

    private void addRemoteInputHistoryToMessages(java.util.List<android.app.Notification.MessagingStyle.Message> list, android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr) {
        if (remoteInputHistoryItemArr == null || remoteInputHistoryItemArr.length == 0) {
            return;
        }
        for (int length = remoteInputHistoryItemArr.length - 1; length >= 0; length--) {
            android.app.RemoteInputHistoryItem remoteInputHistoryItem = remoteInputHistoryItemArr[length];
            android.app.Notification.MessagingStyle.Message message = new android.app.Notification.MessagingStyle.Message(remoteInputHistoryItem.getText(), 0L, null, true);
            if (remoteInputHistoryItem.getUri() != null) {
                message.setData(remoteInputHistoryItem.getMimeType(), remoteInputHistoryItem.getUri());
            }
            list.add(message);
        }
    }

    private void bind(com.android.internal.widget.MessagingData messagingData) {
        setUser(messagingData.getUser());
        setUnreadCount(messagingData.getUnreadCount());
        java.util.ArrayList<com.android.internal.widget.MessagingGroup> arrayList = new java.util.ArrayList<>(this.mGroups);
        createGroupViews(messagingData.getGroups(), messagingData.getSenders(), messagingData.getShowSpinner());
        removeGroups(arrayList);
        java.util.Iterator<com.android.internal.widget.MessagingMessage> it = this.mMessages.iterator();
        while (it.hasNext()) {
            it.next().removeMessage(this.mToRecycle);
        }
        java.util.Iterator<com.android.internal.widget.MessagingMessage> it2 = this.mHistoricMessages.iterator();
        while (it2.hasNext()) {
            it2.next().removeMessage(this.mToRecycle);
        }
        this.mMessages = messagingData.getNewMessagingMessages();
        this.mHistoricMessages = messagingData.getHistoricMessagingMessages();
        updateHistoricMessageVisibility();
        updateTitleAndNamesDisplay();
        updateConversationLayout(messagingData);
        java.util.Iterator<com.android.internal.widget.MessagingLinearLayout.MessagingChild> it3 = this.mToRecycle.iterator();
        while (it3.hasNext()) {
            it3.next().recycle();
        }
        this.mToRecycle.clear();
    }

    private void updateConversationLayout(com.android.internal.widget.MessagingData messagingData) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            computeAndSetConversationAvatarAndName();
        } else {
            com.android.internal.widget.ConversationHeaderData conversationHeaderData = messagingData.getConversationHeaderData();
            if (conversationHeaderData == null) {
                conversationHeaderData = loadConversationHeaderData(this.mIsOneToOne, this.mConversationTitle, this.mShortcutIcon, this.mLargeIcon, this.mMessages, this.mUser, messagingData.getGroups(), this.mLayoutColor);
            }
            setConversationAvatarAndNameFromData(conversationHeaderData);
        }
        updateAppName();
        updateIconPositionAndSize();
        updateImageMessages();
        updatePaddingsBasedOnContentAvailability();
        updateActionListPadding();
        updateAppNameDividerVisibility();
    }

    @java.lang.Deprecated
    private void computeAndSetConversationAvatarAndName() {
        java.lang.CharSequence charSequence = this.mConversationTitle;
        this.mConversationIcon = this.mShortcutIcon;
        if (this.mIsOneToOne) {
            java.lang.CharSequence key = getKey(this.mUser);
            for (int size = this.mGroups.size() - 1; size >= 0; size--) {
                com.android.internal.widget.MessagingGroup messagingGroup = this.mGroups.get(size);
                android.app.Person sender = messagingGroup.getSender();
                if ((sender != null && !android.text.TextUtils.equals(key, getKey(sender))) || size == 0) {
                    if (android.text.TextUtils.isEmpty(charSequence)) {
                        charSequence = messagingGroup.getSenderName();
                    }
                    if (this.mConversationIcon == null) {
                        android.graphics.drawable.Icon avatarIcon = messagingGroup.getAvatarIcon();
                        if (avatarIcon == null) {
                            avatarIcon = this.mPeopleHelper.createAvatarSymbol(charSequence, "", this.mLayoutColor);
                        }
                        this.mConversationIcon = avatarIcon;
                    }
                }
            }
        }
        if (this.mConversationIcon == null) {
            this.mConversationIcon = this.mLargeIcon;
        }
        if (this.mIsOneToOne || this.mConversationIcon != null) {
            this.mConversationIconView.setVisibility(0);
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.setImageIcon(this.mConversationIcon);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            this.mConversationFacePile = findViewById(com.android.internal.R.id.conversation_face_pile);
            bindFacePile();
        }
        if (android.text.TextUtils.isEmpty(charSequence)) {
            charSequence = this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(charSequence);
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, charSequence);
    }

    private void setConversationAvatarAndNameFromData(com.android.internal.widget.ConversationHeaderData conversationHeaderData) {
        com.android.internal.widget.ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData;
        com.android.internal.widget.ConversationAvatarData conversationAvatar = conversationHeaderData.getConversationAvatar();
        com.android.internal.widget.ConversationAvatarData.OneToOneConversationAvatarData oneToOneConversationAvatarData = null;
        if (conversationAvatar instanceof com.android.internal.widget.ConversationAvatarData.OneToOneConversationAvatarData) {
            oneToOneConversationAvatarData = (com.android.internal.widget.ConversationAvatarData.OneToOneConversationAvatarData) conversationAvatar;
            groupConversationAvatarData = null;
        } else {
            groupConversationAvatarData = (com.android.internal.widget.ConversationAvatarData.GroupConversationAvatarData) conversationAvatar;
        }
        if (oneToOneConversationAvatarData != null) {
            this.mConversationIconView.setVisibility(0);
            this.mConversationFacePile.setVisibility(8);
            this.mConversationIconView.lambda$setImageURIAsync$2(oneToOneConversationAvatarData.mDrawable);
        } else {
            this.mConversationIconView.setVisibility(8);
            this.mConversationFacePile.setVisibility(0);
            this.mConversationFacePile = findViewById(com.android.internal.R.id.conversation_face_pile);
            bindFacePile(groupConversationAvatarData);
        }
        java.lang.CharSequence conversationText = conversationHeaderData.getConversationText();
        if (android.text.TextUtils.isEmpty(conversationText)) {
            conversationText = this.mIsOneToOne ? this.mFallbackChatName : this.mFallbackGroupChatName;
        }
        this.mConversationText.lambda$setTextAsync$0(conversationText);
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, conversationText);
    }

    private void updateActionListPadding() {
        if (this.mActions != null) {
            this.mActions.setCollapsibleIndentDimen(com.android.internal.R.dimen.call_notification_collapsible_indent);
        }
    }

    private void updateImageMessages() {
        android.view.View view;
        com.android.internal.widget.MessagingImageMessage isolatedMessage;
        if (this.mIsCollapsed && this.mGroups.size() > 0 && (isolatedMessage = this.mGroups.get(this.mGroups.size() - 1).getIsolatedMessage()) != null) {
            view = isolatedMessage.getView();
        } else {
            view = null;
        }
        android.view.View childAt = this.mImageMessageContainer.getChildAt(0);
        if (childAt != view) {
            this.mImageMessageContainer.removeView(childAt);
            if (view != null) {
                this.mImageMessageContainer.addView(view);
            }
        }
        this.mImageMessageContainer.setVisibility(view == null ? 8 : 0);
    }

    public void bindFacePile(android.widget.ImageView imageView, android.widget.ImageView imageView2, android.widget.ImageView imageView3) {
        applyNotificationBackgroundColor(imageView);
        java.lang.CharSequence key = getKey(this.mUser);
        int size = this.mGroups.size() - 1;
        android.graphics.drawable.Icon icon = null;
        java.lang.CharSequence charSequence = null;
        android.graphics.drawable.Icon icon2 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            com.android.internal.widget.MessagingGroup messagingGroup = this.mGroups.get(size);
            android.app.Person sender = messagingGroup.getSender();
            boolean z = false;
            boolean z2 = (sender == null || android.text.TextUtils.equals(key, getKey(sender))) ? false : true;
            if (sender != null && !android.text.TextUtils.equals(charSequence, getKey(sender))) {
                z = true;
            }
            if ((z2 && z) || (size == 0 && charSequence == null)) {
                if (icon2 == null) {
                    icon2 = messagingGroup.getAvatarIcon();
                    charSequence = getKey(sender);
                } else {
                    icon = messagingGroup.getAvatarIcon();
                    break;
                }
            }
            size--;
        }
        if (icon2 == null) {
            icon2 = this.mPeopleHelper.createAvatarSymbol(" ", "", this.mLayoutColor);
        }
        imageView2.setImageIcon(icon2);
        if (icon == null) {
            icon = this.mPeopleHelper.createAvatarSymbol("", "", this.mLayoutColor);
        }
        imageView3.setImageIcon(icon);
    }

    @java.lang.Deprecated
    private void bindFacePile() {
        bindFacePile(null);
    }

    private void bindFacePile(com.android.internal.widget.ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        int i;
        int i2;
        int i3;
        android.widget.ImageView imageView = (android.widget.ImageView) this.mConversationFacePile.findViewById(com.android.internal.R.id.conversation_face_pile_bottom_background);
        android.widget.ImageView imageView2 = (android.widget.ImageView) this.mConversationFacePile.findViewById(com.android.internal.R.id.conversation_face_pile_bottom);
        android.widget.ImageView imageView3 = (android.widget.ImageView) this.mConversationFacePile.findViewById(com.android.internal.R.id.conversation_face_pile_top);
        if (groupConversationAvatarData == null) {
            bindFacePile(imageView, imageView2, imageView3);
        } else {
            bindFacePileWithDrawable(imageView, imageView2, imageView3, groupConversationAvatarData);
        }
        if (this.mIsCollapsed) {
            i = this.mConversationAvatarSize;
            i2 = this.mFacePileAvatarSize;
            i3 = (this.mFacePileProtectionWidth * 2) + i2;
        } else {
            i = this.mConversationAvatarSizeExpanded;
            i2 = this.mFacePileAvatarSizeExpandedGroup;
            i3 = (this.mFacePileProtectionWidthExpanded * 2) + i2;
        }
        android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.mConversationFacePile.setLayoutParams(layoutParams);
        android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) imageView2.getLayoutParams();
        layoutParams2.width = i2;
        layoutParams2.height = i2;
        imageView2.setLayoutParams(layoutParams2);
        android.widget.FrameLayout.LayoutParams layoutParams3 = (android.widget.FrameLayout.LayoutParams) imageView3.getLayoutParams();
        layoutParams3.width = i2;
        layoutParams3.height = i2;
        imageView3.setLayoutParams(layoutParams3);
        android.widget.FrameLayout.LayoutParams layoutParams4 = (android.widget.FrameLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams4.width = i3;
        layoutParams4.height = i3;
        imageView.setLayoutParams(layoutParams4);
    }

    private void bindFacePileWithDrawable(android.widget.ImageView imageView, android.widget.ImageView imageView2, android.widget.ImageView imageView3, com.android.internal.widget.ConversationAvatarData.GroupConversationAvatarData groupConversationAvatarData) {
        applyNotificationBackgroundColor(imageView);
        imageView2.lambda$setImageURIAsync$2(groupConversationAvatarData.mLastIcon);
        imageView3.lambda$setImageURIAsync$2(groupConversationAvatarData.mSecondLastIcon);
    }

    private void updateAppName() {
        this.mAppName.setVisibility(this.mIsCollapsed ? 8 : 0);
    }

    public boolean shouldHideAppName() {
        return this.mIsCollapsed;
    }

    private void updateIconPositionAndSize() {
        int i;
        int i2;
        if (this.mIsOneToOne || this.mIsCollapsed) {
            i = this.mBadgeProtrusion;
            i2 = this.mConversationAvatarSize;
        } else {
            if (this.mConversationFacePile.getVisibility() == 0) {
                i = this.mExpandedGroupBadgeProtrusionFacePile;
            } else {
                i = this.mExpandedGroupBadgeProtrusion;
            }
            i2 = this.mConversationAvatarSizeExpanded;
        }
        if (this.mConversationIconView.getVisibility() == 0) {
            android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) this.mConversationIconView.getLayoutParams();
            layoutParams.width = i2;
            layoutParams.height = i2;
            layoutParams.leftMargin = i;
            layoutParams.rightMargin = i;
            layoutParams.bottomMargin = i;
            this.mConversationIconView.setLayoutParams(layoutParams);
        }
        if (this.mConversationFacePile.getVisibility() == 0) {
            android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) this.mConversationFacePile.getLayoutParams();
            layoutParams2.leftMargin = i;
            layoutParams2.rightMargin = i;
            layoutParams2.bottomMargin = i;
            this.mConversationFacePile.setLayoutParams(layoutParams2);
        }
    }

    private void updatePaddingsBasedOnContentAvailability() {
        int i;
        int i2;
        this.mMessagingLinearLayout.setSpacing(this.mIsOneToOne ? this.mMessageSpacingStandard : this.mMessageSpacingGroup);
        if (this.mIsOneToOne || this.mIsCollapsed) {
            i = 0;
        } else {
            i = this.mExpandedGroupMessagePadding;
        }
        if (this.mIsOneToOne || this.mIsCollapsed) {
            i2 = this.mConversationIconTopPadding;
        } else {
            i2 = this.mConversationIconTopPaddingExpandedGroup;
        }
        this.mConversationIconContainer.setPaddingRelative(this.mConversationIconContainer.getPaddingStart(), i2, this.mConversationIconContainer.getPaddingEnd(), this.mConversationIconContainer.getPaddingBottom());
        this.mMessagingLinearLayout.setPaddingRelative(this.mMessagingLinearLayout.getPaddingStart(), i, this.mMessagingLinearLayout.getPaddingEnd(), this.mMessagingLinearLayout.getPaddingBottom());
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setLargeIconAsync(final android.graphics.drawable.Icon icon) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setLargeIconAsync$7(icon);
                }
            };
        }
        this.mLargeIcon = icon;
        return com.android.internal.widget.NotificationRunnables.NOOP;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$7(android.graphics.drawable.Icon icon) {
        this.mLargeIcon = icon;
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setShortcutIconAsync(final android.graphics.drawable.Icon icon) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setShortcutIconAsync$8(icon);
                }
            };
        }
        this.mShortcutIcon = icon;
        return com.android.internal.widget.NotificationRunnables.NOOP;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setShortcutIconAsync")
    /* renamed from: setShortcutIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setShortcutIconAsync$8(android.graphics.drawable.Icon icon) {
        this.mShortcutIcon = icon;
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setConversationTitleAsync(final java.lang.CharSequence charSequence) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setConversationTitleAsync$9(charSequence);
                }
            };
        }
        this.mConversationTitle = charSequence != null ? charSequence.toString() : null;
        return com.android.internal.widget.NotificationRunnables.NOOP;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setConversationTitleAsync")
    /* renamed from: setConversationTitle, reason: merged with bridge method [inline-methods] */
    public void lambda$setConversationTitleAsync$9(java.lang.CharSequence charSequence) {
        this.mConversationTitle = charSequence != null ? charSequence.toString() : null;
    }

    public java.lang.CharSequence getConversationTitle() {
        return this.mConversationText.getText();
    }

    private void removeGroups(java.util.ArrayList<com.android.internal.widget.MessagingGroup> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            final com.android.internal.widget.MessagingGroup messagingGroup = arrayList.get(i);
            if (!this.mGroups.contains(messagingGroup)) {
                java.util.List<com.android.internal.widget.MessagingMessage> messages = messagingGroup.getMessages();
                boolean isShown = messagingGroup.isShown();
                this.mMessagingLinearLayout.removeView(messagingGroup);
                if (isShown && !com.android.internal.widget.MessagingLinearLayout.isGone(messagingGroup)) {
                    this.mMessagingLinearLayout.addTransientView(messagingGroup, 0);
                    messagingGroup.removeGroupAnimated(new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda11
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.internal.widget.ConversationLayout.this.lambda$removeGroups$10(messagingGroup);
                        }
                    });
                } else {
                    this.mToRecycle.add(messagingGroup);
                }
                this.mMessages.removeAll(messages);
                this.mHistoricMessages.removeAll(messages);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeGroups$10(com.android.internal.widget.MessagingGroup messagingGroup) {
        this.mMessagingLinearLayout.removeTransientView(messagingGroup);
        messagingGroup.recycle();
    }

    private void updateTitleAndNamesDisplay() {
        android.graphics.drawable.Icon avatarSymbolIfMatching;
        java.util.Map<java.lang.CharSequence, java.lang.String> mapUniqueNamesToPrefix = this.mPeopleHelper.mapUniqueNamesToPrefix(this.mGroups);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < this.mGroups.size(); i++) {
            com.android.internal.widget.MessagingGroup messagingGroup = this.mGroups.get(i);
            boolean z = messagingGroup.getSender() == this.mUser;
            java.lang.CharSequence senderName = messagingGroup.getSenderName();
            if (messagingGroup.needsGeneratedAvatar() && !android.text.TextUtils.isEmpty(senderName) && ((!this.mIsOneToOne || this.mAvatarReplacement == null || z) && (avatarSymbolIfMatching = messagingGroup.getAvatarSymbolIfMatching(senderName, mapUniqueNamesToPrefix.get(senderName), this.mLayoutColor)) != null)) {
                arrayMap.put(senderName, avatarSymbolIfMatching);
            }
        }
        for (int i2 = 0; i2 < this.mGroups.size(); i2++) {
            com.android.internal.widget.MessagingGroup messagingGroup2 = this.mGroups.get(i2);
            java.lang.CharSequence senderName2 = messagingGroup2.getSenderName();
            if (messagingGroup2.needsGeneratedAvatar() && !android.text.TextUtils.isEmpty(senderName2)) {
                if (this.mIsOneToOne && this.mAvatarReplacement != null && messagingGroup2.getSender() != this.mUser) {
                    messagingGroup2.setAvatar(this.mAvatarReplacement);
                } else {
                    android.graphics.drawable.Icon icon = (android.graphics.drawable.Icon) arrayMap.get(senderName2);
                    if (icon == null) {
                        icon = this.mPeopleHelper.createAvatarSymbol(senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                        arrayMap.put(senderName2, icon);
                    }
                    messagingGroup2.setCreatedAvatar(icon, senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                }
            }
        }
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setLayoutColorAsync(final int i) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setLayoutColorAsync$11(i);
                }
            };
        }
        this.mLayoutColor = i;
        return com.android.internal.widget.NotificationRunnables.NOOP;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$11(int i) {
        this.mLayoutColor = i;
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setIsOneToOneAsync(final boolean z) {
        if (!android.widget.flags.Flags.conversationStyleSetAvatarAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.ConversationLayout$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.ConversationLayout.this.lambda$setIsOneToOneAsync$12(z);
                }
            };
        }
        this.mIsOneToOne = z;
        return com.android.internal.widget.NotificationRunnables.NOOP;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setIsOneToOneAsync")
    /* renamed from: setIsOneToOne, reason: merged with bridge method [inline-methods] */
    public void lambda$setIsOneToOneAsync$12(boolean z) {
        this.mIsOneToOne = z;
    }

    @android.view.RemotableViewMethod
    public void setSenderTextColor(int i) {
        this.mSenderTextColor = i;
        this.mConversationText.setTextColor(i);
    }

    @android.view.RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
        this.mNotificationBackgroundColor = i;
        applyNotificationBackgroundColor(this.mConversationIconBadgeBg);
    }

    private void applyNotificationBackgroundColor(android.widget.ImageView imageView) {
        imageView.setImageTintList(android.content.res.ColorStateList.valueOf(this.mNotificationBackgroundColor));
    }

    @android.view.RemotableViewMethod
    public void setMessageTextColor(int i) {
        this.mMessageTextColor = i;
    }

    private void setUser(android.app.Person person) {
        this.mUser = person;
        if (this.mUser.getIcon() == null) {
            android.graphics.drawable.Icon createWithResource = android.graphics.drawable.Icon.createWithResource(getContext(), com.android.internal.R.drawable.messaging_user);
            createWithResource.setTint(this.mLayoutColor);
            this.mUser = this.mUser.toBuilder().setIcon(createWithResource).build();
        }
    }

    private void createGroupViews(java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list, java.util.List<android.app.Person> list2, boolean z) {
        int i;
        this.mGroups.clear();
        int i2 = 0;
        while (i2 < list.size()) {
            java.util.List<com.android.internal.widget.MessagingMessage> list3 = list.get(i2);
            java.lang.CharSequence charSequence = null;
            com.android.internal.widget.MessagingGroup messagingGroup = null;
            for (int size = list3.size() - 1; size >= 0; size--) {
                messagingGroup = list3.get(size).getGroup();
                if (messagingGroup != null) {
                    break;
                }
            }
            if (messagingGroup == null) {
                messagingGroup = com.android.internal.widget.MessagingGroup.createGroup(this.mMessagingLinearLayout);
                this.mAddedGroups.add(messagingGroup);
            } else if (messagingGroup.getParent() != this.mMessagingLinearLayout) {
                throw new java.lang.IllegalStateException("group parent was " + messagingGroup.getParent() + " but expected " + this.mMessagingLinearLayout);
            }
            if (this.mIsCollapsed) {
                i = 2;
            } else {
                i = 0;
            }
            messagingGroup.setImageDisplayLocation(i);
            messagingGroup.setIsInConversation(true);
            messagingGroup.setLayoutColor(this.mLayoutColor);
            messagingGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            android.app.Person person = list2.get(i2);
            if (person != this.mUser && this.mNameReplacement != null) {
                charSequence = this.mNameReplacement;
            }
            messagingGroup.setShowingAvatar((this.mIsOneToOne || this.mIsCollapsed) ? false : true);
            messagingGroup.setSingleLine(this.mIsCollapsed);
            messagingGroup.setSender(person, charSequence);
            messagingGroup.setSending(i2 == list.size() - 1 && z);
            this.mGroups.add(messagingGroup);
            if (this.mMessagingLinearLayout.indexOfChild(messagingGroup) != i2) {
                this.mMessagingLinearLayout.removeView(messagingGroup);
                this.mMessagingLinearLayout.addView(messagingGroup, i2);
            }
            messagingGroup.setMessages(list3);
            i2++;
        }
    }

    private void findGroups(java.util.List<com.android.internal.widget.MessagingMessage> list, java.util.List<com.android.internal.widget.MessagingMessage> list2, android.app.Person person, java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list3, java.util.List<android.app.Person> list4) {
        android.app.Person build;
        int size = list.size();
        java.util.ArrayList arrayList = null;
        java.lang.CharSequence charSequence = null;
        int i = 0;
        while (i < list2.size() + size) {
            com.android.internal.widget.MessagingMessage messagingMessage = i < size ? list.get(i) : list2.get(i - size);
            boolean z = arrayList == null;
            android.app.Person senderPerson = messagingMessage.getMessage() == null ? null : messagingMessage.getMessage().getSenderPerson();
            java.lang.CharSequence key = getKey(senderPerson);
            if ((true ^ android.text.TextUtils.equals(key, charSequence)) | z) {
                arrayList = new java.util.ArrayList();
                list3.add(arrayList);
                if (senderPerson == null) {
                    build = person;
                } else {
                    build = senderPerson.toBuilder().setName(java.util.Objects.toString(senderPerson.getName())).build();
                }
                list4.add(build);
                charSequence = key;
            }
            arrayList.add(messagingMessage);
            i++;
        }
    }

    private java.lang.CharSequence getKey(android.app.Person person) {
        if (person == null) {
            return null;
        }
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.internal.widget.ConversationHeaderData loadConversationHeaderData(boolean z, java.lang.CharSequence charSequence, android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2, java.util.List<com.android.internal.widget.MessagingMessage> list, android.app.Person person, java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list2, int i) {
        java.lang.String str;
        android.graphics.drawable.Icon icon3;
        boolean z2;
        boolean z3;
        java.lang.CharSequence key = getKey(person);
        if (z) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.app.Person senderPerson = list.get(size).getMessage().getSenderPerson();
                java.lang.CharSequence key2 = getKey(senderPerson);
                if ((senderPerson != null && key2 != key) || size == 0) {
                    if (charSequence == null || charSequence.length() == 0) {
                        str = senderPerson != null ? senderPerson.getName() : "";
                    } else {
                        str = charSequence;
                    }
                    if (icon != null) {
                        icon3 = icon;
                    } else {
                        icon3 = senderPerson != null ? senderPerson.getIcon() : this.mPeopleHelper.createAvatarSymbol(str, "", i);
                    }
                    if (icon3 == null) {
                        icon3 = icon2;
                    }
                    if (!z || icon3 != null) {
                        return new com.android.internal.widget.ConversationHeaderData(str, new com.android.internal.widget.ConversationAvatarData.OneToOneConversationAvatarData(resolveAvatarImage(icon3)));
                    }
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    for (int i2 = 0; i2 < list2.size(); i2++) {
                        java.util.ArrayList arrayList2 = new java.util.ArrayList();
                        for (int i3 = 0; i3 < list2.get(i2).size(); i3++) {
                            arrayList2.add(list2.get(i2).get(i3).getMessage());
                        }
                        arrayList.add(arrayList2);
                    }
                    com.android.internal.widget.PeopleHelper.NameToPrefixMap mapUniqueNamesToPrefixWithGroupList = this.mPeopleHelper.mapUniqueNamesToPrefixWithGroupList(arrayList);
                    int size2 = list2.size() - 1;
                    android.graphics.drawable.Icon icon4 = null;
                    java.lang.CharSequence charSequence2 = null;
                    android.graphics.drawable.Icon icon5 = null;
                    while (true) {
                        if (size2 < 0) {
                            break;
                        }
                        android.app.Notification.MessagingStyle.Message message = list2.get(size2).get(0).getMessage();
                        android.app.Person senderPerson2 = message.getSenderPerson() != null ? message.getSenderPerson() : person;
                        java.lang.CharSequence key3 = getKey(senderPerson2);
                        if (key3 != key) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (key3 != charSequence2) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if ((z2 && z3) || (size2 == 0 && charSequence2 == null)) {
                            if (icon5 == null) {
                                if (senderPerson2.getIcon() != null) {
                                    icon5 = senderPerson2.getIcon();
                                } else {
                                    java.lang.String name = senderPerson2.getName() != null ? senderPerson2.getName() : "";
                                    icon5 = this.mPeopleHelper.createAvatarSymbol(name, mapUniqueNamesToPrefixWithGroupList.getPrefix(name), i);
                                }
                                charSequence2 = key3;
                            } else if (senderPerson2.getIcon() != null) {
                                icon4 = senderPerson2.getIcon();
                            } else {
                                java.lang.String name2 = senderPerson2.getName() != null ? senderPerson2.getName() : "";
                                icon4 = this.mPeopleHelper.createAvatarSymbol(name2, mapUniqueNamesToPrefixWithGroupList.getPrefix(name2), i);
                            }
                        }
                        size2--;
                    }
                    if (icon5 == null) {
                        icon5 = this.mPeopleHelper.createAvatarSymbol("", "", i);
                    }
                    if (icon4 == null) {
                        icon4 = this.mPeopleHelper.createAvatarSymbol("", "", i);
                    }
                    return new com.android.internal.widget.ConversationHeaderData(str, new com.android.internal.widget.ConversationAvatarData.GroupConversationAvatarData(resolveAvatarImage(icon5), resolveAvatarImage(icon4)));
                }
            }
        }
        str = charSequence;
        icon3 = icon;
        if (icon3 == null) {
        }
        if (!z) {
        }
        return new com.android.internal.widget.ConversationHeaderData(str, new com.android.internal.widget.ConversationAvatarData.OneToOneConversationAvatarData(resolveAvatarImage(icon3)));
    }

    private android.graphics.drawable.Drawable resolveAvatarImage(android.graphics.drawable.Icon icon) {
        try {
            return com.android.internal.widget.LocalImageResolver.resolveImage(icon, getContext());
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    private java.util.List<com.android.internal.widget.MessagingMessage> createMessages(java.util.List<android.app.Notification.MessagingStyle.Message> list, boolean z, boolean z2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            android.app.Notification.MessagingStyle.Message message = list.get(i);
            com.android.internal.widget.MessagingMessage findAndRemoveMatchingMessage = findAndRemoveMatchingMessage(message);
            if (findAndRemoveMatchingMessage == null) {
                findAndRemoveMatchingMessage = com.android.internal.widget.MessagingMessage.createMessage(this, message, this.mImageResolver, z2);
            }
            findAndRemoveMatchingMessage.setIsHistoric(z);
            arrayList.add(findAndRemoveMatchingMessage);
        }
        return arrayList;
    }

    private com.android.internal.widget.MessagingMessage findAndRemoveMatchingMessage(android.app.Notification.MessagingStyle.Message message) {
        for (int i = 0; i < this.mMessages.size(); i++) {
            com.android.internal.widget.MessagingMessage messagingMessage = this.mMessages.get(i);
            if (messagingMessage.sameAs(message)) {
                this.mMessages.remove(i);
                return messagingMessage;
            }
        }
        for (int i2 = 0; i2 < this.mHistoricMessages.size(); i2++) {
            com.android.internal.widget.MessagingMessage messagingMessage2 = this.mHistoricMessages.get(i2);
            if (messagingMessage2.sameAs(message)) {
                this.mHistoricMessages.remove(i2);
                return messagingMessage2;
            }
        }
        return null;
    }

    public void showHistoricMessages(boolean z) {
        this.mShowHistoricMessages = z;
        updateHistoricMessageVisibility();
    }

    private void updateHistoricMessageVisibility() {
        int size = this.mHistoricMessages.size();
        int i = 0;
        while (true) {
            int i2 = 8;
            if (i >= size) {
                break;
            }
            com.android.internal.widget.MessagingMessage messagingMessage = this.mHistoricMessages.get(i);
            if (this.mShowHistoricMessages) {
                i2 = 0;
            }
            messagingMessage.setVisibility(i2);
            i++;
        }
        int size2 = this.mGroups.size();
        for (int i3 = 0; i3 < size2; i3++) {
            com.android.internal.widget.MessagingGroup messagingGroup = this.mGroups.get(i3);
            java.util.List<com.android.internal.widget.MessagingMessage> messages = messagingGroup.getMessages();
            int size3 = messages.size();
            int i4 = 0;
            for (int i5 = 0; i5 < size3; i5++) {
                if (messages.get(i5).getVisibility() != 8) {
                    i4++;
                }
            }
            if (i4 > 0 && messagingGroup.getVisibility() == 8) {
                messagingGroup.setVisibility(0);
            } else if (i4 == 0 && messagingGroup.getVisibility() != 8) {
                messagingGroup.setVisibility(8);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!this.mAddedGroups.isEmpty()) {
            getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.ConversationLayout.2
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    java.util.Iterator it = com.android.internal.widget.ConversationLayout.this.mAddedGroups.iterator();
                    while (it.hasNext()) {
                        com.android.internal.widget.MessagingGroup messagingGroup = (com.android.internal.widget.MessagingGroup) it.next();
                        if (messagingGroup.isShown()) {
                            com.android.internal.widget.MessagingPropertyAnimator.fadeIn(messagingGroup.getAvatar());
                            com.android.internal.widget.MessagingPropertyAnimator.fadeIn(messagingGroup.getSenderView());
                            com.android.internal.widget.MessagingPropertyAnimator.startLocalTranslationFrom(messagingGroup, messagingGroup.getHeight(), com.android.internal.widget.ConversationLayout.LINEAR_OUT_SLOW_IN);
                        }
                    }
                    com.android.internal.widget.ConversationLayout.this.mAddedGroups.clear();
                    com.android.internal.widget.ConversationLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mTouchDelegate.clear();
        if (this.mFeedbackIcon.getVisibility() == 0) {
            float max = java.lang.Math.max(this.mMinTouchSize, this.mFeedbackIcon.getWidth());
            float max2 = java.lang.Math.max(this.mMinTouchSize, this.mFeedbackIcon.getHeight());
            android.graphics.Rect rect = new android.graphics.Rect();
            rect.left = (int) (((this.mFeedbackIcon.getLeft() + this.mFeedbackIcon.getRight()) / 2.0f) - (max / 2.0f));
            rect.top = (int) (((this.mFeedbackIcon.getTop() + this.mFeedbackIcon.getBottom()) / 2.0f) - (max2 / 2.0f));
            rect.bottom = (int) (rect.top + max2);
            rect.right = (int) (rect.left + max);
            getRelativeTouchRect(rect, this.mFeedbackIcon);
            this.mTouchDelegate.add(new android.view.TouchDelegate(rect, this.mFeedbackIcon));
        }
        setTouchDelegate(this.mTouchDelegate);
    }

    private void getRelativeTouchRect(android.graphics.Rect rect, android.view.View view) {
        for (android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent(); viewGroup != this; viewGroup = (android.view.ViewGroup) viewGroup.getParent()) {
            rect.offset(viewGroup.getLeft(), viewGroup.getTop());
        }
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public com.android.internal.widget.MessagingLinearLayout getMessagingLinearLayout() {
        return this.mMessagingLinearLayout;
    }

    public android.view.ViewGroup getImageMessageContainer() {
        return this.mImageMessageContainer;
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public java.util.ArrayList<com.android.internal.widget.MessagingGroup> getMessagingGroups() {
        return this.mGroups;
    }

    private void updateExpandButton() {
        android.view.ViewGroup viewGroup;
        int i;
        if (this.mIsCollapsed) {
            viewGroup = this.mExpandButtonAndContentContainer;
            i = 17;
        } else {
            viewGroup = this.mExpandButtonContainerA11yContainer;
            i = 49;
        }
        this.mExpandButton.setExpanded(!this.mIsCollapsed);
        if (viewGroup != this.mExpandButtonContainer.getParent()) {
            ((android.view.ViewGroup) this.mExpandButtonContainer.getParent()).removeView(this.mExpandButtonContainer);
            viewGroup.addView(this.mExpandButtonContainer);
        }
        android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) this.mExpandButton.getLayoutParams();
        layoutParams.gravity = i;
        this.mExpandButton.setLayoutParams(layoutParams);
    }

    private void updateContentEndPaddings() {
        int i;
        int i2 = 0;
        if (!this.mExpandable) {
            i = this.mContentMarginEnd;
        } else if (this.mIsCollapsed) {
            i = 0;
        } else {
            i2 = this.mNotificationHeaderExpandedPadding;
            i = this.mContentMarginEnd;
        }
        this.mConversationHeader.setPaddingRelative(this.mConversationHeader.getPaddingStart(), this.mConversationHeader.getPaddingTop(), i2, this.mConversationHeader.getPaddingBottom());
        this.mContentContainer.setPaddingRelative(this.mContentContainer.getPaddingStart(), this.mContentContainer.getPaddingTop(), i, this.mContentContainer.getPaddingBottom());
    }

    private void onAppNameVisibilityChanged() {
        boolean z = this.mAppName.getVisibility() == 8;
        if (z != this.mAppNameGone) {
            this.mAppNameGone = z;
            updateAppNameDividerVisibility();
        }
    }

    private void updateAppNameDividerVisibility() {
        this.mAppNameDivider.setVisibility(this.mAppNameGone ? 8 : 0);
    }

    public void updateExpandability(boolean z, android.view.View.OnClickListener onClickListener) {
        this.mExpandable = z;
        if (z) {
            this.mExpandButtonContainer.setVisibility(0);
            this.mExpandButton.setOnClickListener(onClickListener);
            this.mConversationIconContainer.setOnClickListener(onClickListener);
        } else {
            this.mExpandButtonContainer.setVisibility(8);
            this.mConversationIconContainer.setOnClickListener(null);
        }
        this.mExpandButton.setVisibility(0);
        updateContentEndPaddings();
    }

    @Override // com.android.internal.widget.IMessagingLayout
    public void setMessagingClippingDisabled(boolean z) {
        this.mMessagingLinearLayout.setClipBounds(z ? null : this.mMessagingClipRect);
    }

    public java.lang.CharSequence getConversationSenderName() {
        if (this.mGroups.isEmpty()) {
            return null;
        }
        return getResources().getString(com.android.internal.R.string.conversation_single_line_name_display, this.mGroups.get(this.mGroups.size() - 1).getSenderName());
    }

    public boolean isOneToOne() {
        return this.mIsOneToOne;
    }

    public java.lang.CharSequence getConversationText() {
        if (this.mMessages.isEmpty()) {
            return null;
        }
        com.android.internal.widget.MessagingMessage messagingMessage = this.mMessages.get(this.mMessages.size() - 1);
        java.lang.CharSequence text = messagingMessage.getMessage() != null ? messagingMessage.getMessage().getText() : null;
        if (text == null && (messagingMessage instanceof com.android.internal.widget.MessagingImageMessage)) {
            android.text.SpannableString spannableString = new android.text.SpannableString(getResources().getString(com.android.internal.R.string.conversation_single_line_image_placeholder));
            spannableString.setSpan(new android.text.style.StyleSpan(2), 0, spannableString.length(), 17);
            return spannableString;
        }
        return text;
    }

    public android.graphics.drawable.Icon getConversationIcon() {
        return this.mConversationIcon;
    }

    private static class TouchDelegateComposite extends android.view.TouchDelegate {
        private final java.util.ArrayList<android.view.TouchDelegate> mDelegates;

        private TouchDelegateComposite(android.view.View view) {
            super(new android.graphics.Rect(), view);
            this.mDelegates = new java.util.ArrayList<>();
        }

        public void add(android.view.TouchDelegate touchDelegate) {
            this.mDelegates.add(touchDelegate);
        }

        public void clear() {
            this.mDelegates.clear();
        }

        @Override // android.view.TouchDelegate
        public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            java.util.Iterator<android.view.TouchDelegate> it = this.mDelegates.iterator();
            while (it.hasNext()) {
                android.view.TouchDelegate next = it.next();
                motionEvent.setLocation(x, y);
                if (next.onTouchEvent(motionEvent)) {
                    return true;
                }
            }
            return false;
        }
    }
}
