package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingLayout extends android.widget.FrameLayout implements com.android.internal.widget.ImageMessageConsumer, com.android.internal.widget.IMessagingLayout {
    private static final float COLOR_SHIFT_AMOUNT = 60.0f;
    private java.util.ArrayList<com.android.internal.widget.MessagingGroup> mAddedGroups;
    private android.graphics.drawable.Icon mAvatarReplacement;
    private java.lang.CharSequence mConversationTitle;
    private java.util.ArrayList<com.android.internal.widget.MessagingGroup> mGroups;
    private java.util.List<com.android.internal.widget.MessagingMessage> mHistoricMessages;
    private com.android.internal.widget.MessagingLinearLayout mImageMessageContainer;
    private com.android.internal.widget.ImageResolver mImageResolver;
    private boolean mIsCollapsed;
    private boolean mIsOneToOne;
    private int mLayoutColor;
    private int mMessageTextColor;
    private java.util.List<com.android.internal.widget.MessagingMessage> mMessages;
    private android.graphics.Rect mMessagingClipRect;
    private com.android.internal.widget.MessagingLinearLayout mMessagingLinearLayout;
    private java.lang.CharSequence mNameReplacement;
    private final com.android.internal.widget.PeopleHelper mPeopleHelper;
    private boolean mPrecomputedTextEnabled;
    private android.widget.ImageView mRightIconView;
    private int mSenderTextColor;
    private boolean mShowHistoricMessages;
    private java.util.ArrayList<com.android.internal.widget.MessagingLinearLayout.MessagingChild> mToRecycle;
    private android.app.Person mUser;
    public static final android.view.animation.Interpolator LINEAR_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
    public static final android.view.animation.Interpolator FAST_OUT_LINEAR_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final android.view.animation.Interpolator FAST_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final android.view.View.OnLayoutChangeListener MESSAGING_PROPERTY_ANIMATOR = new com.android.internal.widget.MessagingPropertyAnimator();

    public MessagingLayout(android.content.Context context) {
        super(context);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    public MessagingLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
        this.mMessages = new java.util.ArrayList();
        this.mHistoricMessages = new java.util.ArrayList();
        this.mGroups = new java.util.ArrayList<>();
        this.mAddedGroups = new java.util.ArrayList<>();
        this.mToRecycle = new java.util.ArrayList<>();
        this.mPrecomputedTextEnabled = false;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mMessagingLinearLayout = (com.android.internal.widget.MessagingLinearLayout) findViewById(com.android.internal.R.id.notification_messaging);
        this.mImageMessageContainer = (com.android.internal.widget.MessagingLinearLayout) findViewById(com.android.internal.R.id.conversation_image_message_container);
        this.mRightIconView = (android.widget.ImageView) findViewById(com.android.internal.R.id.right_icon);
        android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int max = java.lang.Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.mMessagingClipRect = new android.graphics.Rect(0, 0, max, max);
        setMessagingClippingDisabled(false);
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
    public void setIsCollapsed(boolean z) {
        this.mIsCollapsed = z;
    }

    @android.view.RemotableViewMethod
    public void setLargeIcon(android.graphics.drawable.Icon icon) {
    }

    @android.view.RemotableViewMethod
    public void setConversationTitle(java.lang.CharSequence charSequence) {
        this.mConversationTitle = charSequence;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$0(android.os.Bundle bundle) {
        bind(parseMessagingData(bundle, false));
    }

    private com.android.internal.widget.MessagingData parseMessagingData(android.os.Bundle bundle, boolean z) {
        java.util.List<android.app.Notification.MessagingStyle.Message> messagesFromBundleArray = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(android.app.Notification.EXTRA_MESSAGES));
        java.util.List<android.app.Notification.MessagingStyle.Message> messagesFromBundleArray2 = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(bundle.getParcelableArray(android.app.Notification.EXTRA_HISTORIC_MESSAGES));
        setUser((android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_MESSAGING_PERSON, android.app.Person.class));
        addRemoteInputHistoryToMessages(messagesFromBundleArray, (android.app.RemoteInputHistoryItem[]) bundle.getParcelableArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, android.app.RemoteInputHistoryItem.class));
        android.app.Person person = (android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_MESSAGING_PERSON, android.app.Person.class);
        boolean z2 = bundle.getBoolean(android.app.Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, false);
        java.util.List<com.android.internal.widget.MessagingMessage> createMessages = createMessages(messagesFromBundleArray2, true, z);
        java.util.List<com.android.internal.widget.MessagingMessage> createMessages2 = createMessages(messagesFromBundleArray, false, z);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        findGroups(createMessages, createMessages2, arrayList, arrayList2);
        return new com.android.internal.widget.MessagingData(person, z2, createMessages, createMessages2, arrayList, arrayList2);
    }

    public java.lang.Runnable setDataAsync(final android.os.Bundle bundle) {
        if (!this.mPrecomputedTextEnabled) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.MessagingLayout.this.lambda$setDataAsync$0(bundle);
                }
            };
        }
        final com.android.internal.widget.MessagingData parseMessagingData = parseMessagingData(bundle, true);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.MessagingLayout.this.lambda$setDataAsync$1(parseMessagingData);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDataAsync$1(com.android.internal.widget.MessagingData messagingData) {
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
        this.mPeopleHelper.maybeHideFirstSenderName(this.mGroups, this.mIsOneToOne, this.mConversationTitle);
        updateImageMessages();
        java.util.Iterator<com.android.internal.widget.MessagingLinearLayout.MessagingChild> it3 = this.mToRecycle.iterator();
        while (it3.hasNext()) {
            it3.next().recycle();
        }
        this.mToRecycle.clear();
    }

    private void updateImageMessages() {
        android.view.View view;
        com.android.internal.widget.MessagingImageMessage isolatedMessage;
        if (this.mImageMessageContainer == null) {
            return;
        }
        if (this.mIsCollapsed && !this.mGroups.isEmpty() && (isolatedMessage = this.mGroups.get(this.mGroups.size() - 1).getIsolatedMessage()) != null) {
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
        if (view != null && this.mRightIconView != null && this.mRightIconView.getDrawable() != null) {
            this.mRightIconView.lambda$setImageURIAsync$2(null);
            this.mRightIconView.setVisibility(8);
        }
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
                    messagingGroup.removeGroupAnimated(new java.lang.Runnable() { // from class: com.android.internal.widget.MessagingLayout$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.internal.widget.MessagingLayout.this.lambda$removeGroups$2(messagingGroup);
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
    public /* synthetic */ void lambda$removeGroups$2(com.android.internal.widget.MessagingGroup messagingGroup) {
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
                        icon = createAvatarSymbol(senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                        arrayMap.put(senderName2, icon);
                    }
                    messagingGroup2.setCreatedAvatar(icon, senderName2, mapUniqueNamesToPrefix.get(senderName2), this.mLayoutColor);
                }
            }
        }
    }

    public android.graphics.drawable.Icon createAvatarSymbol(java.lang.CharSequence charSequence, java.lang.String str, int i) {
        return this.mPeopleHelper.createAvatarSymbol(charSequence, str, i);
    }

    private int findColor(java.lang.CharSequence charSequence, int i) {
        return com.android.internal.util.ContrastColorUtil.getShiftedColor(i, (int) (((float) (((float) ((((java.lang.Math.abs(charSequence.hashCode()) % 5) / 4.0f) - 0.5f) + java.lang.Math.max(0.30000001192092896d - r0, 0.0d))) - java.lang.Math.max(0.30000001192092896d - (1.0d - com.android.internal.util.ContrastColorUtil.calculateLuminance(i)), 0.0d))) * 60.0f));
    }

    private java.lang.String findNameSplit(java.lang.String str) {
        java.lang.String[] split = str.split(" ");
        if (split.length > 1) {
            return java.lang.Character.toString(split[0].charAt(0)) + java.lang.Character.toString(split[1].charAt(0));
        }
        return str.substring(0, 1);
    }

    @android.view.RemotableViewMethod
    public void setLayoutColor(int i) {
        this.mLayoutColor = i;
    }

    @android.view.RemotableViewMethod
    public void setIsOneToOne(boolean z) {
        this.mIsOneToOne = z;
    }

    @android.view.RemotableViewMethod
    public void setSenderTextColor(int i) {
        this.mSenderTextColor = i;
    }

    @android.view.RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
    }

    @android.view.RemotableViewMethod
    public void setMessageTextColor(int i) {
        this.mMessageTextColor = i;
    }

    public void setUser(android.app.Person person) {
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
            messagingGroup.setIsInConversation(false);
            messagingGroup.setLayoutColor(this.mLayoutColor);
            messagingGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            android.app.Person person = list2.get(i2);
            if (person != this.mUser && this.mNameReplacement != null) {
                charSequence = this.mNameReplacement;
            }
            messagingGroup.setSingleLine(this.mIsCollapsed);
            messagingGroup.setShowingAvatar(!this.mIsCollapsed);
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

    private void findGroups(java.util.List<com.android.internal.widget.MessagingMessage> list, java.util.List<com.android.internal.widget.MessagingMessage> list2, java.util.List<java.util.List<com.android.internal.widget.MessagingMessage>> list3, java.util.List<android.app.Person> list4) {
        java.lang.CharSequence name;
        int size = list.size();
        java.util.ArrayList arrayList = null;
        java.lang.CharSequence charSequence = null;
        int i = 0;
        while (i < list2.size() + size) {
            com.android.internal.widget.MessagingMessage messagingMessage = i < size ? list.get(i) : list2.get(i - size);
            boolean z = arrayList == null;
            android.app.Person senderPerson = messagingMessage.getMessage() == null ? null : messagingMessage.getMessage().getSenderPerson();
            if (senderPerson == null) {
                name = null;
            } else {
                name = senderPerson.getKey() == null ? senderPerson.getName() : senderPerson.getKey();
            }
            if ((true ^ android.text.TextUtils.equals(name, charSequence)) | z) {
                arrayList = new java.util.ArrayList();
                list3.add(arrayList);
                if (senderPerson == null) {
                    senderPerson = this.mUser;
                }
                list4.add(senderPerson);
                charSequence = name;
            }
            arrayList.add(messagingMessage);
            i++;
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
            getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.widget.MessagingLayout.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    java.util.Iterator it = com.android.internal.widget.MessagingLayout.this.mAddedGroups.iterator();
                    while (it.hasNext()) {
                        com.android.internal.widget.MessagingGroup messagingGroup = (com.android.internal.widget.MessagingGroup) it.next();
                        if (messagingGroup.isShown()) {
                            com.android.internal.widget.MessagingPropertyAnimator.fadeIn(messagingGroup.getAvatar());
                            com.android.internal.widget.MessagingPropertyAnimator.fadeIn(messagingGroup.getSenderView());
                            com.android.internal.widget.MessagingPropertyAnimator.startLocalTranslationFrom(messagingGroup, messagingGroup.getHeight(), com.android.internal.widget.MessagingLayout.LINEAR_OUT_SLOW_IN);
                        }
                    }
                    com.android.internal.widget.MessagingLayout.this.mAddedGroups.clear();
                    com.android.internal.widget.MessagingLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
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

    @Override // com.android.internal.widget.IMessagingLayout
    public void setMessagingClippingDisabled(boolean z) {
        this.mMessagingLinearLayout.setClipBounds(z ? null : this.mMessagingClipRect);
    }
}
