package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class CallLayout extends android.widget.FrameLayout {
    private com.android.internal.widget.CachingIconView mConversationIconBadgeBg;
    private com.android.internal.widget.CachingIconView mConversationIconView;
    private android.widget.TextView mConversationText;
    private com.android.internal.widget.CachingIconView mIcon;
    private android.graphics.drawable.Icon mLargeIcon;
    private int mLayoutColor;
    private final com.android.internal.widget.PeopleHelper mPeopleHelper;
    private android.app.Person mUser;

    public CallLayout(android.content.Context context) {
        super(context);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
    }

    public CallLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
    }

    public CallLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
    }

    public CallLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPeopleHelper = new com.android.internal.widget.PeopleHelper();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mConversationText = (android.widget.TextView) findViewById(com.android.internal.R.id.conversation_text);
        this.mConversationIconView = (com.android.internal.widget.CachingIconView) findViewById(com.android.internal.R.id.conversation_icon);
        this.mIcon = (com.android.internal.widget.CachingIconView) findViewById(16908294);
        this.mConversationIconBadgeBg = (com.android.internal.widget.CachingIconView) findViewById(com.android.internal.R.id.conversation_icon_badge_bg);
        this.mIcon.setOnForceHiddenChangedListener(new java.util.function.Consumer() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.internal.widget.CallLayout.this.lambda$onFinishInflate$0((java.lang.Boolean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFinishInflate$0(java.lang.Boolean bool) {
        this.mPeopleHelper.animateViewForceHidden(this.mConversationIconBadgeBg, bool.booleanValue());
    }

    private android.graphics.drawable.Icon getConversationIcon() {
        android.graphics.drawable.Icon icon;
        java.lang.String str;
        java.lang.CharSequence charSequence = "";
        if (this.mUser == null) {
            icon = null;
            str = "";
        } else {
            icon = this.mUser.getIcon();
            java.lang.CharSequence name = this.mUser.getName();
            str = this.mPeopleHelper.findNamePrefix(name, "");
            charSequence = name;
        }
        if (icon == null) {
            icon = this.mLargeIcon;
        }
        if (icon == null) {
            return this.mPeopleHelper.createAvatarSymbol(charSequence, str, this.mLayoutColor);
        }
        return icon;
    }

    public java.lang.Runnable setLayoutColorAsync(final int i) {
        if (!android.widget.flags.Flags.callStyleSetDataAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CallLayout.this.lambda$setLayoutColorAsync$1(i);
                }
            };
        }
        this.mLayoutColor = i;
        return new java.lang.Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.CallLayout.lambda$setLayoutColorAsync$2();
            }
        };
    }

    static /* synthetic */ void lambda$setLayoutColorAsync$2() {
    }

    @android.view.RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$1(int i) {
        this.mLayoutColor = i;
    }

    @android.view.RemotableViewMethod
    public void setNotificationBackgroundColor(int i) {
        this.mConversationIconBadgeBg.setImageTintList(android.content.res.ColorStateList.valueOf(i));
    }

    public java.lang.Runnable setLargeIconAsync(final android.graphics.drawable.Icon icon) {
        if (!android.widget.flags.Flags.callStyleSetDataAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CallLayout.this.lambda$setLargeIconAsync$3(icon);
                }
            };
        }
        this.mLargeIcon = icon;
        return new java.lang.Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.CallLayout.lambda$setLargeIconAsync$4();
            }
        };
    }

    static /* synthetic */ void lambda$setLargeIconAsync$4() {
    }

    @android.view.RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$3(android.graphics.drawable.Icon icon) {
        this.mLargeIcon = icon;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$5(android.os.Bundle bundle) {
        setUser(getPerson(bundle));
        this.mConversationIconView.setImageIcon(getConversationIcon());
    }

    public java.lang.Runnable setDataAsync(final android.os.Bundle bundle) {
        if (!android.widget.flags.Flags.callStyleSetDataAsync()) {
            return new java.lang.Runnable() { // from class: com.android.internal.widget.CallLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.internal.widget.CallLayout.this.lambda$setDataAsync$5(bundle);
                }
            };
        }
        setUser(getPerson(bundle));
        return this.mConversationIconView.setImageIconAsync(getConversationIcon());
    }

    private android.app.Person getPerson(android.os.Bundle bundle) {
        return (android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_CALL_PERSON, android.app.Person.class);
    }

    private void setUser(android.app.Person person) {
        this.mUser = person;
    }
}
