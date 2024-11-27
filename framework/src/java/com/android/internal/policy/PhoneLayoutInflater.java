package com.android.internal.policy;

/* loaded from: classes5.dex */
public class PhoneLayoutInflater extends android.view.LayoutInflater {
    private static final java.lang.String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

    public PhoneLayoutInflater(android.content.Context context) {
        super(context);
    }

    protected PhoneLayoutInflater(android.view.LayoutInflater layoutInflater, android.content.Context context) {
        super(layoutInflater, context);
    }

    @Override // android.view.LayoutInflater
    protected android.view.View onCreateView(java.lang.String str, android.util.AttributeSet attributeSet) throws java.lang.ClassNotFoundException {
        android.view.View createView;
        for (java.lang.String str2 : sClassPrefixList) {
            try {
                createView = createView(str, str2, attributeSet);
            } catch (java.lang.ClassNotFoundException e) {
            }
            if (createView != null) {
                return createView;
            }
        }
        return super.onCreateView(str, attributeSet);
    }

    @Override // android.view.LayoutInflater
    public android.view.LayoutInflater cloneInContext(android.content.Context context) {
        return new com.android.internal.policy.PhoneLayoutInflater(this, context);
    }
}
