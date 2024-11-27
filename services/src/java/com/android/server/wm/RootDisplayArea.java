package com.android.server.wm;

/* loaded from: classes3.dex */
class RootDisplayArea extends com.android.server.wm.DisplayArea.Dimmable {
    private com.android.server.wm.DisplayArea.Tokens[] mAreaForLayer;
    java.util.Map<com.android.server.wm.DisplayAreaPolicyBuilder.Feature, java.util.List<com.android.server.wm.DisplayArea<com.android.server.wm.WindowContainer>>> mFeatureToDisplayAreas;
    java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> mFeatures;
    private boolean mHasBuiltHierarchy;

    RootDisplayArea(com.android.server.wm.WindowManagerService windowManagerService, java.lang.String str, int i) {
        super(windowManagerService, com.android.server.wm.DisplayArea.Type.ANY, str, i);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.RootDisplayArea getRootDisplayArea() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.RootDisplayArea asRootDisplayArea() {
        return this;
    }

    boolean isOrientationDifferentFromDisplay() {
        return false;
    }

    boolean placeImeContainer(com.android.server.wm.DisplayArea.Tokens tokens) {
        com.android.server.wm.RootDisplayArea rootDisplayArea = tokens.getRootDisplayArea();
        java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> list = this.mFeatures;
        for (int i = 0; i < list.size(); i++) {
            com.android.server.wm.DisplayAreaPolicyBuilder.Feature feature = list.get(i);
            if (feature.getId() == 7) {
                java.util.List<com.android.server.wm.DisplayArea<com.android.server.wm.WindowContainer>> list2 = this.mFeatureToDisplayAreas.get(feature);
                if (list2.size() != 1) {
                    throw new java.lang.IllegalStateException("There must be exactly one DisplayArea for the FEATURE_IME_PLACEHOLDER");
                }
                rootDisplayArea.updateImeContainerForLayers(null);
                tokens.reparent(list2.get(0), Integer.MAX_VALUE);
                updateImeContainerForLayers(tokens);
                return true;
            }
        }
        if (!isDescendantOf(rootDisplayArea)) {
            android.util.Slog.w("WindowManager", "The IME target is not in the same root as the IME container, but there is no DisplayArea of FEATURE_IME_PLACEHOLDER in the target RootDisplayArea");
        }
        return false;
    }

    @android.annotation.Nullable
    com.android.server.wm.DisplayArea.Tokens findAreaForTokenInLayer(com.android.server.wm.WindowToken windowToken) {
        return findAreaForWindowTypeInLayer(windowToken.windowType, windowToken.mOwnerCanManageAppTokens, windowToken.mRoundedCornerOverlay);
    }

    @android.annotation.Nullable
    com.android.server.wm.DisplayArea.Tokens findAreaForWindowTypeInLayer(int i, boolean z, boolean z2) {
        int windowLayerFromTypeLw = this.mWmService.mPolicy.getWindowLayerFromTypeLw(i, z, z2);
        if (windowLayerFromTypeLw == 2) {
            throw new java.lang.IllegalArgumentException("There shouldn't be WindowToken on APPLICATION_LAYER");
        }
        return this.mAreaForLayer[windowLayerFromTypeLw];
    }

    void onHierarchyBuilt(java.util.ArrayList<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> arrayList, com.android.server.wm.DisplayArea.Tokens[] tokensArr, java.util.Map<com.android.server.wm.DisplayAreaPolicyBuilder.Feature, java.util.List<com.android.server.wm.DisplayArea<com.android.server.wm.WindowContainer>>> map) {
        if (this.mHasBuiltHierarchy) {
            throw new java.lang.IllegalStateException("Root should only build the hierarchy once");
        }
        this.mHasBuiltHierarchy = true;
        this.mFeatures = java.util.Collections.unmodifiableList(arrayList);
        this.mAreaForLayer = tokensArr;
        this.mFeatureToDisplayAreas = map;
    }

    private void updateImeContainerForLayers(@android.annotation.Nullable com.android.server.wm.DisplayArea.Tokens tokens) {
        com.android.server.policy.WindowManagerPolicy windowManagerPolicy = this.mWmService.mPolicy;
        this.mAreaForLayer[windowManagerPolicy.getWindowLayerFromTypeLw(2011)] = tokens;
        this.mAreaForLayer[windowManagerPolicy.getWindowLayerFromTypeLw(2012)] = tokens;
    }
}
