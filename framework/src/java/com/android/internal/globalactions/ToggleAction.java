package com.android.internal.globalactions;

/* loaded from: classes4.dex */
public abstract class ToggleAction implements com.android.internal.globalactions.Action {
    private static final java.lang.String TAG = "ToggleAction";
    protected int mDisabledIconResid;
    protected int mDisabledStatusMessageResId;
    protected int mEnabledIconResId;
    protected int mEnabledStatusMessageResId;
    protected int mMessageResId;
    protected com.android.internal.globalactions.ToggleAction.State mState = com.android.internal.globalactions.ToggleAction.State.Off;

    public abstract void onToggle(boolean z);

    public enum State {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);

        private final boolean inTransition;

        State(boolean z) {
            this.inTransition = z;
        }

        public boolean inTransition() {
            return this.inTransition;
        }
    }

    public ToggleAction(int i, int i2, int i3, int i4, int i5) {
        this.mEnabledIconResId = i;
        this.mDisabledIconResid = i2;
        this.mMessageResId = i3;
        this.mEnabledStatusMessageResId = i4;
        this.mDisabledStatusMessageResId = i5;
    }

    void willCreate() {
    }

    @Override // com.android.internal.globalactions.Action
    public java.lang.CharSequence getLabelForAccessibility(android.content.Context context) {
        return context.getString(this.mMessageResId);
    }

    @Override // com.android.internal.globalactions.Action
    public android.view.View create(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.view.LayoutInflater layoutInflater) {
        willCreate();
        android.view.View inflate = layoutInflater.inflate(com.android.internal.R.layout.global_actions_item, viewGroup, false);
        android.widget.ImageView imageView = (android.widget.ImageView) inflate.findViewById(16908294);
        android.widget.TextView textView = (android.widget.TextView) inflate.findViewById(16908299);
        android.widget.TextView textView2 = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.status);
        boolean isEnabled = isEnabled();
        if (textView != null) {
            textView.setText(this.mMessageResId);
            textView.setEnabled(isEnabled);
        }
        boolean z = this.mState == com.android.internal.globalactions.ToggleAction.State.On || this.mState == com.android.internal.globalactions.ToggleAction.State.TurningOn;
        if (imageView != null) {
            imageView.lambda$setImageURIAsync$2(context.getDrawable(z ? this.mEnabledIconResId : this.mDisabledIconResid));
            imageView.setEnabled(isEnabled);
        }
        if (textView2 != null) {
            textView2.setText(z ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId);
            textView2.setVisibility(0);
            textView2.setEnabled(isEnabled);
        }
        inflate.setEnabled(isEnabled);
        return inflate;
    }

    @Override // com.android.internal.globalactions.Action
    public final void onPress() {
        if (this.mState.inTransition()) {
            android.util.Log.w(TAG, "shouldn't be able to toggle when in transition");
            return;
        }
        boolean z = this.mState != com.android.internal.globalactions.ToggleAction.State.On;
        onToggle(z);
        changeStateFromPress(z);
    }

    @Override // com.android.internal.globalactions.Action
    public boolean isEnabled() {
        return !this.mState.inTransition();
    }

    protected void changeStateFromPress(boolean z) {
        this.mState = z ? com.android.internal.globalactions.ToggleAction.State.On : com.android.internal.globalactions.ToggleAction.State.Off;
    }

    public void updateState(com.android.internal.globalactions.ToggleAction.State state) {
        this.mState = state;
    }
}
