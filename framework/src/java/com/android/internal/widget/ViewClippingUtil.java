package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ViewClippingUtil {
    private static final int CLIP_CHILDREN_TAG = 16908892;
    private static final int CLIP_CLIPPING_SET = 16908891;
    private static final int CLIP_TO_PADDING = 16908894;

    public static void setClippingDeactivated(android.view.View view, boolean z, com.android.internal.widget.ViewClippingUtil.ClippingParameters clippingParameters) {
        if ((!z && !clippingParameters.isClippingEnablingAllowed(view)) || !(view.getParent() instanceof android.view.ViewGroup)) {
            return;
        }
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) view.getParent();
        while (true) {
            if (!z && !clippingParameters.isClippingEnablingAllowed(view)) {
                return;
            }
            android.util.ArraySet arraySet = (android.util.ArraySet) viewGroup.getTag(16908891);
            if (arraySet == null) {
                arraySet = new android.util.ArraySet();
                viewGroup.setTagInternal(16908891, arraySet);
            }
            java.lang.Boolean bool = (java.lang.Boolean) viewGroup.getTag(16908892);
            if (bool == null) {
                bool = java.lang.Boolean.valueOf(viewGroup.getClipChildren());
                viewGroup.setTagInternal(16908892, bool);
            }
            java.lang.Boolean bool2 = (java.lang.Boolean) viewGroup.getTag(16908894);
            if (bool2 == null) {
                bool2 = java.lang.Boolean.valueOf(viewGroup.getClipToPadding());
                viewGroup.setTagInternal(16908894, bool2);
            }
            if (!z) {
                arraySet.remove(view);
                if (arraySet.isEmpty()) {
                    viewGroup.setClipChildren(bool.booleanValue());
                    viewGroup.setClipToPadding(bool2.booleanValue());
                    viewGroup.setTagInternal(16908891, null);
                    clippingParameters.onClippingStateChanged(viewGroup, true);
                }
            } else {
                arraySet.add(view);
                viewGroup.setClipChildren(false);
                viewGroup.setClipToPadding(false);
                clippingParameters.onClippingStateChanged(viewGroup, false);
            }
            if (clippingParameters.shouldFinish(viewGroup)) {
                return;
            }
            android.view.ViewParent parent = viewGroup.getParent();
            if (parent instanceof android.view.ViewGroup) {
                viewGroup = (android.view.ViewGroup) parent;
            } else {
                return;
            }
        }
    }

    public interface ClippingParameters {
        boolean shouldFinish(android.view.View view);

        default boolean isClippingEnablingAllowed(android.view.View view) {
            return !com.android.internal.widget.MessagingPropertyAnimator.isAnimatingTranslation(view);
        }

        default void onClippingStateChanged(android.view.View view, boolean z) {
        }
    }
}
