package com.android.internal.globalactions;

/* loaded from: classes4.dex */
public interface Action {
    android.view.View create(android.content.Context context, android.view.View view, android.view.ViewGroup viewGroup, android.view.LayoutInflater layoutInflater);

    java.lang.CharSequence getLabelForAccessibility(android.content.Context context);

    boolean isEnabled();

    void onPress();

    boolean showBeforeProvisioning();

    boolean showDuringKeyguard();
}
