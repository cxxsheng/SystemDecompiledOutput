package com.android.server.wm;

@com.android.internal.annotations.Keep
/* loaded from: classes3.dex */
class DisplayAreaPolicyBuilder {
    private final java.util.ArrayList<com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder> mDisplayAreaGroupHierarchyBuilders = new java.util.ArrayList<>();

    @android.annotation.Nullable
    private com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder mRootHierarchyBuilder;

    @android.annotation.Nullable
    private java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, com.android.server.wm.RootDisplayArea> mSelectRootForWindowFunc;

    @android.annotation.Nullable
    private java.util.function.Function<android.os.Bundle, com.android.server.wm.TaskDisplayArea> mSelectTaskDisplayAreaFunc;

    interface NewDisplayAreaSupplier {
        com.android.server.wm.DisplayArea create(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i);
    }

    DisplayAreaPolicyBuilder() {
    }

    com.android.server.wm.DisplayAreaPolicyBuilder setRootHierarchy(com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder) {
        this.mRootHierarchyBuilder = hierarchyBuilder;
        return this;
    }

    com.android.server.wm.DisplayAreaPolicyBuilder addDisplayAreaGroupHierarchy(com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder) {
        this.mDisplayAreaGroupHierarchyBuilders.add(hierarchyBuilder);
        return this;
    }

    com.android.server.wm.DisplayAreaPolicyBuilder setSelectRootForWindowFunc(java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, com.android.server.wm.RootDisplayArea> biFunction) {
        this.mSelectRootForWindowFunc = biFunction;
        return this;
    }

    com.android.server.wm.DisplayAreaPolicyBuilder setSelectTaskDisplayAreaFunc(java.util.function.Function<android.os.Bundle, com.android.server.wm.TaskDisplayArea> function) {
        this.mSelectTaskDisplayAreaFunc = function;
        return this;
    }

    private void validate() {
        if (this.mRootHierarchyBuilder == null) {
            throw new java.lang.IllegalStateException("Root must be set for the display area policy.");
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        validateIds(this.mRootHierarchyBuilder, arraySet, arraySet2);
        boolean z = this.mRootHierarchyBuilder.mImeContainer != null;
        boolean containsDefaultTaskDisplayArea = containsDefaultTaskDisplayArea(this.mRootHierarchyBuilder);
        for (int i = 0; i < this.mDisplayAreaGroupHierarchyBuilders.size(); i++) {
            com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder = this.mDisplayAreaGroupHierarchyBuilders.get(i);
            validateIds(hierarchyBuilder, arraySet, arraySet2);
            if (hierarchyBuilder.mTaskDisplayAreas.isEmpty()) {
                throw new java.lang.IllegalStateException("DisplayAreaGroup must contain at least one TaskDisplayArea.");
            }
            if (z) {
                if (hierarchyBuilder.mImeContainer != null) {
                    throw new java.lang.IllegalStateException("Only one DisplayArea hierarchy can contain the IME container");
                }
            } else {
                z = hierarchyBuilder.mImeContainer != null;
            }
            if (containsDefaultTaskDisplayArea) {
                if (containsDefaultTaskDisplayArea(hierarchyBuilder)) {
                    throw new java.lang.IllegalStateException("Only one TaskDisplayArea can have the feature id of FEATURE_DEFAULT_TASK_CONTAINER");
                }
            } else {
                containsDefaultTaskDisplayArea = containsDefaultTaskDisplayArea(hierarchyBuilder);
            }
        }
        if (!z) {
            throw new java.lang.IllegalStateException("IME container must be set.");
        }
        if (!containsDefaultTaskDisplayArea) {
            throw new java.lang.IllegalStateException("There must be a default TaskDisplayArea with id of FEATURE_DEFAULT_TASK_CONTAINER.");
        }
    }

    private static boolean containsDefaultTaskDisplayArea(com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder) {
        for (int i = 0; i < hierarchyBuilder.mTaskDisplayAreas.size(); i++) {
            if (((com.android.server.wm.TaskDisplayArea) hierarchyBuilder.mTaskDisplayAreas.get(i)).mFeatureId == 1) {
                return true;
            }
        }
        return false;
    }

    private static void validateIds(com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder, java.util.Set<java.lang.Integer> set, java.util.Set<java.lang.Integer> set2) {
        int i = hierarchyBuilder.mRoot.mFeatureId;
        if (!set2.add(java.lang.Integer.valueOf(i)) || !set.add(java.lang.Integer.valueOf(i))) {
            throw new java.lang.IllegalStateException("RootDisplayArea must have unique id, but id=" + i + " is not unique.");
        }
        if (i > 20001) {
            throw new java.lang.IllegalStateException("RootDisplayArea should not have an id greater than FEATURE_VENDOR_LAST.");
        }
        for (int i2 = 0; i2 < hierarchyBuilder.mTaskDisplayAreas.size(); i2++) {
            int i3 = ((com.android.server.wm.TaskDisplayArea) hierarchyBuilder.mTaskDisplayAreas.get(i2)).mFeatureId;
            if (!set2.add(java.lang.Integer.valueOf(i3)) || !set.add(java.lang.Integer.valueOf(i3))) {
                throw new java.lang.IllegalStateException("TaskDisplayArea must have unique id, but id=" + i3 + " is not unique.");
            }
            if (i3 > 20001) {
                throw new java.lang.IllegalStateException("TaskDisplayArea declared in the policy should nothave an id greater than FEATURE_VENDOR_LAST.");
            }
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (int i4 = 0; i4 < hierarchyBuilder.mFeatures.size(); i4++) {
            int id = ((com.android.server.wm.DisplayAreaPolicyBuilder.Feature) hierarchyBuilder.mFeatures.get(i4)).getId();
            if (set.contains(java.lang.Integer.valueOf(id))) {
                throw new java.lang.IllegalStateException("Feature must not have same id with any RootDisplayArea or TaskDisplayArea, but id=" + id + " is used");
            }
            if (!arraySet.add(java.lang.Integer.valueOf(id))) {
                throw new java.lang.IllegalStateException("Feature below the same root must have unique id, but id=" + id + " is not unique.");
            }
            if (id > 20001) {
                throw new java.lang.IllegalStateException("Feature should not have an id greater than FEATURE_VENDOR_LAST.");
            }
        }
        set2.addAll(arraySet);
    }

    com.android.server.wm.DisplayAreaPolicyBuilder.Result build(com.android.server.wm.WindowManagerService windowManagerService) {
        validate();
        this.mRootHierarchyBuilder.build(this.mDisplayAreaGroupHierarchyBuilders);
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mDisplayAreaGroupHierarchyBuilders.size());
        for (int i = 0; i < this.mDisplayAreaGroupHierarchyBuilders.size(); i++) {
            com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder = this.mDisplayAreaGroupHierarchyBuilders.get(i);
            hierarchyBuilder.build();
            arrayList.add(hierarchyBuilder.mRoot);
        }
        if (this.mSelectRootForWindowFunc == null) {
            this.mSelectRootForWindowFunc = new com.android.server.wm.DisplayAreaPolicyBuilder.DefaultSelectRootForWindowFunction(this.mRootHierarchyBuilder.mRoot, arrayList);
        }
        return new com.android.server.wm.DisplayAreaPolicyBuilder.Result(windowManagerService, this.mRootHierarchyBuilder.mRoot, arrayList, this.mSelectRootForWindowFunc, this.mSelectTaskDisplayAreaFunc);
    }

    private static class DefaultSelectRootForWindowFunction implements java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, com.android.server.wm.RootDisplayArea> {
        final java.util.List<com.android.server.wm.RootDisplayArea> mDisplayAreaGroupRoots;
        final com.android.server.wm.RootDisplayArea mDisplayRoot;

        DefaultSelectRootForWindowFunction(com.android.server.wm.RootDisplayArea rootDisplayArea, java.util.List<com.android.server.wm.RootDisplayArea> list) {
            this.mDisplayRoot = rootDisplayArea;
            this.mDisplayAreaGroupRoots = java.util.Collections.unmodifiableList(list);
        }

        @Override // java.util.function.BiFunction
        public com.android.server.wm.RootDisplayArea apply(java.lang.Integer num, android.os.Bundle bundle) {
            if (this.mDisplayAreaGroupRoots.isEmpty()) {
                return this.mDisplayRoot;
            }
            if (bundle != null && bundle.containsKey("root_display_area_id")) {
                int i = bundle.getInt("root_display_area_id");
                if (this.mDisplayRoot.mFeatureId == i) {
                    return this.mDisplayRoot;
                }
                for (int size = this.mDisplayAreaGroupRoots.size() - 1; size >= 0; size--) {
                    if (this.mDisplayAreaGroupRoots.get(size).mFeatureId == i) {
                        return this.mDisplayAreaGroupRoots.get(size);
                    }
                }
            }
            return this.mDisplayRoot;
        }
    }

    private static class DefaultSelectTaskDisplayAreaFunction implements java.util.function.Function<android.os.Bundle, com.android.server.wm.TaskDisplayArea> {
        private final com.android.server.wm.TaskDisplayArea mDefaultTaskDisplayArea;
        private final int mDisplayId;

        DefaultSelectTaskDisplayAreaFunction(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
            this.mDefaultTaskDisplayArea = taskDisplayArea;
            this.mDisplayId = taskDisplayArea.getDisplayId();
        }

        @Override // java.util.function.Function
        public com.android.server.wm.TaskDisplayArea apply(@android.annotation.Nullable android.os.Bundle bundle) {
            if (bundle == null) {
                return this.mDefaultTaskDisplayArea;
            }
            android.window.WindowContainerToken launchTaskDisplayArea = new android.app.ActivityOptions(bundle).getLaunchTaskDisplayArea();
            if (launchTaskDisplayArea == null) {
                return this.mDefaultTaskDisplayArea;
            }
            com.android.server.wm.TaskDisplayArea asTaskDisplayArea = com.android.server.wm.WindowContainer.fromBinder(launchTaskDisplayArea.asBinder()).asTaskDisplayArea();
            if (asTaskDisplayArea == null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_ORGANIZER, 4917824058925068521L, 0, null, java.lang.String.valueOf(launchTaskDisplayArea));
                return this.mDefaultTaskDisplayArea;
            }
            if (asTaskDisplayArea.getDisplayId() != this.mDisplayId) {
                throw new java.lang.IllegalArgumentException("The specified TaskDisplayArea must attach to Display#" + this.mDisplayId + ", but it is in Display#" + asTaskDisplayArea.getDisplayId());
            }
            return asTaskDisplayArea;
        }
    }

    static class HierarchyBuilder {
        private static final int LEAF_TYPE_IME_CONTAINERS = 2;
        private static final int LEAF_TYPE_TASK_CONTAINERS = 1;
        private static final int LEAF_TYPE_TOKENS = 0;

        @android.annotation.Nullable
        private com.android.server.wm.DisplayArea.Tokens mImeContainer;
        private final com.android.server.wm.RootDisplayArea mRoot;
        private final java.util.ArrayList<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> mFeatures = new java.util.ArrayList<>();
        private final java.util.ArrayList<com.android.server.wm.TaskDisplayArea> mTaskDisplayAreas = new java.util.ArrayList<>();

        HierarchyBuilder(com.android.server.wm.RootDisplayArea rootDisplayArea) {
            this.mRoot = rootDisplayArea;
        }

        com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder addFeature(com.android.server.wm.DisplayAreaPolicyBuilder.Feature feature) {
            this.mFeatures.add(feature);
            return this;
        }

        com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder setTaskDisplayAreas(java.util.List<com.android.server.wm.TaskDisplayArea> list) {
            this.mTaskDisplayAreas.clear();
            this.mTaskDisplayAreas.addAll(list);
            return this;
        }

        com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder setImeContainer(com.android.server.wm.DisplayArea.Tokens tokens) {
            this.mImeContainer = tokens;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void build() {
            build(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void build(@android.annotation.Nullable java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder> list) {
            com.android.server.policy.WindowManagerPolicy windowManagerPolicy = this.mRoot.mWmService.mPolicy;
            int maxWindowLayer = windowManagerPolicy.getMaxWindowLayer() + 1;
            com.android.server.wm.DisplayArea.Tokens[] tokensArr = new com.android.server.wm.DisplayArea.Tokens[maxWindowLayer];
            android.util.ArrayMap arrayMap = new android.util.ArrayMap(this.mFeatures.size());
            int i = 0;
            for (int i2 = 0; i2 < this.mFeatures.size(); i2++) {
                arrayMap.put(this.mFeatures.get(i2), new java.util.ArrayList());
            }
            com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea[] pendingAreaArr = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea[maxWindowLayer];
            com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea(null, 0, null);
            java.util.Arrays.fill(pendingAreaArr, pendingArea);
            int size = this.mFeatures.size();
            int i3 = 0;
            while (i3 < size) {
                com.android.server.wm.DisplayAreaPolicyBuilder.Feature feature = this.mFeatures.get(i3);
                com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea2 = null;
                for (int i4 = i; i4 < maxWindowLayer; i4++) {
                    if (feature.mWindowLayers[i4]) {
                        if (pendingArea2 == null || pendingArea2.mParent != pendingAreaArr[i4]) {
                            pendingArea2 = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea(feature, i4, pendingAreaArr[i4]);
                            pendingAreaArr[i4].mChildren.add(pendingArea2);
                        }
                        pendingAreaArr[i4] = pendingArea2;
                    } else {
                        pendingArea2 = null;
                    }
                }
                i3++;
                i = 0;
            }
            com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea3 = null;
            int i5 = 0;
            for (int i6 = 0; i6 < maxWindowLayer; i6++) {
                int typeOfLayer = typeOfLayer(windowManagerPolicy, i6);
                if (pendingArea3 == null || pendingArea3.mParent != pendingAreaArr[i6] || typeOfLayer != i5) {
                    pendingArea3 = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea(null, i6, pendingAreaArr[i6]);
                    pendingAreaArr[i6].mChildren.add(pendingArea3);
                    if (typeOfLayer == 1) {
                        addTaskDisplayAreasToApplicationLayer(pendingAreaArr[i6]);
                        addDisplayAreaGroupsToApplicationLayer(pendingAreaArr[i6], list);
                        pendingArea3.mSkipTokens = true;
                    } else if (typeOfLayer == 2) {
                        pendingArea3.mExisting = this.mImeContainer;
                        pendingArea3.mSkipTokens = true;
                    }
                    i5 = typeOfLayer;
                }
                pendingArea3.mMaxLayer = i6;
            }
            pendingArea.computeMaxLayer();
            pendingArea.instantiateChildren(this.mRoot, tokensArr, 0, arrayMap);
            this.mRoot.onHierarchyBuilt(this.mFeatures, tokensArr, arrayMap);
        }

        private void addTaskDisplayAreasToApplicationLayer(com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea) {
            int size = this.mTaskDisplayAreas.size();
            for (int i = 0; i < size; i++) {
                com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea2 = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea(null, 2, pendingArea);
                pendingArea2.mExisting = this.mTaskDisplayAreas.get(i);
                pendingArea2.mMaxLayer = 2;
                pendingArea.mChildren.add(pendingArea2);
            }
        }

        private void addDisplayAreaGroupsToApplicationLayer(com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea, @android.annotation.Nullable java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder> list) {
            if (list == null) {
                return;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea2 = new com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea(null, 2, pendingArea);
                pendingArea2.mExisting = list.get(i).mRoot;
                pendingArea2.mMaxLayer = 2;
                pendingArea.mChildren.add(pendingArea2);
            }
        }

        private static int typeOfLayer(com.android.server.policy.WindowManagerPolicy windowManagerPolicy, int i) {
            if (i == 2) {
                return 1;
            }
            if (i == windowManagerPolicy.getWindowLayerFromTypeLw(2011) || i == windowManagerPolicy.getWindowLayerFromTypeLw(2012)) {
                return 2;
            }
            return 0;
        }
    }

    static class Feature {
        private final int mId;
        private final java.lang.String mName;
        private final com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier mNewDisplayAreaSupplier;
        private final boolean[] mWindowLayers;

        private Feature(java.lang.String str, int i, boolean[] zArr, com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier newDisplayAreaSupplier) {
            this.mName = str;
            this.mId = i;
            this.mWindowLayers = zArr;
            this.mNewDisplayAreaSupplier = newDisplayAreaSupplier;
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String toString() {
            return "Feature(\"" + this.mName + "\", " + this.mId + '}';
        }

        static class Builder {
            private final int mId;
            private final boolean[] mLayers;
            private final java.lang.String mName;
            private final com.android.server.policy.WindowManagerPolicy mPolicy;
            private com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier mNewDisplayAreaSupplier = new com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier() { // from class: com.android.server.wm.DisplayAreaPolicyBuilder$Feature$Builder$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
                public final com.android.server.wm.DisplayArea create(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i) {
                    return new com.android.server.wm.DisplayArea(windowManagerService, type, str, i);
                }
            };
            private boolean mExcludeRoundedCorner = true;

            Builder(com.android.server.policy.WindowManagerPolicy windowManagerPolicy, java.lang.String str, int i) {
                this.mPolicy = windowManagerPolicy;
                this.mName = str;
                this.mId = i;
                this.mLayers = new boolean[this.mPolicy.getMaxWindowLayer() + 1];
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder all() {
                java.util.Arrays.fill(this.mLayers, true);
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder and(int... iArr) {
                for (int i : iArr) {
                    set(i, true);
                }
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder except(int... iArr) {
                for (int i : iArr) {
                    set(i, false);
                }
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder upTo(int i) {
                int layerFromType = layerFromType(i, false);
                for (int i2 = 0; i2 < layerFromType; i2++) {
                    this.mLayers[i2] = true;
                }
                set(i, true);
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder setNewDisplayAreaSupplier(com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier newDisplayAreaSupplier) {
                this.mNewDisplayAreaSupplier = newDisplayAreaSupplier;
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder setExcludeRoundedCornerOverlay(boolean z) {
                this.mExcludeRoundedCorner = z;
                return this;
            }

            com.android.server.wm.DisplayAreaPolicyBuilder.Feature build() {
                if (this.mExcludeRoundedCorner) {
                    this.mLayers[this.mPolicy.getMaxWindowLayer()] = false;
                }
                return new com.android.server.wm.DisplayAreaPolicyBuilder.Feature(this.mName, this.mId, (boolean[]) this.mLayers.clone(), this.mNewDisplayAreaSupplier);
            }

            private void set(int i, boolean z) {
                this.mLayers[layerFromType(i, true)] = z;
                if (i == 2038) {
                    this.mLayers[layerFromType(i, true)] = z;
                    this.mLayers[layerFromType(2003, false)] = z;
                    this.mLayers[layerFromType(2006, false)] = z;
                    this.mLayers[layerFromType(2010, false)] = z;
                }
            }

            private int layerFromType(int i, boolean z) {
                return this.mPolicy.getWindowLayerFromTypeLw(i, z);
            }
        }
    }

    static class Result extends com.android.server.wm.DisplayAreaPolicy {
        private final com.android.server.wm.TaskDisplayArea mDefaultTaskDisplayArea;
        final java.util.List<com.android.server.wm.RootDisplayArea> mDisplayAreaGroupRoots;
        final java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, com.android.server.wm.RootDisplayArea> mSelectRootForWindowFunc;
        private final java.util.function.Function<android.os.Bundle, com.android.server.wm.TaskDisplayArea> mSelectTaskDisplayAreaFunc;

        Result(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.RootDisplayArea rootDisplayArea, java.util.List<com.android.server.wm.RootDisplayArea> list, java.util.function.BiFunction<java.lang.Integer, android.os.Bundle, com.android.server.wm.RootDisplayArea> biFunction, java.util.function.Function<android.os.Bundle, com.android.server.wm.TaskDisplayArea> function) {
            super(windowManagerService, rootDisplayArea);
            this.mDisplayAreaGroupRoots = java.util.Collections.unmodifiableList(list);
            this.mSelectRootForWindowFunc = biFunction;
            this.mDefaultTaskDisplayArea = (com.android.server.wm.TaskDisplayArea) this.mRoot.getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayAreaPolicyBuilder$Result$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    com.android.server.wm.TaskDisplayArea lambda$new$0;
                    lambda$new$0 = com.android.server.wm.DisplayAreaPolicyBuilder.Result.lambda$new$0((com.android.server.wm.TaskDisplayArea) obj);
                    return lambda$new$0;
                }
            });
            if (this.mDefaultTaskDisplayArea == null) {
                throw new java.lang.IllegalStateException("No display area with FEATURE_DEFAULT_TASK_CONTAINER");
            }
            this.mSelectTaskDisplayAreaFunc = function == null ? new com.android.server.wm.DisplayAreaPolicyBuilder.DefaultSelectTaskDisplayAreaFunction(this.mDefaultTaskDisplayArea) : function;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.server.wm.TaskDisplayArea lambda$new$0(com.android.server.wm.TaskDisplayArea taskDisplayArea) {
            if (taskDisplayArea.mFeatureId == 1) {
                return taskDisplayArea;
            }
            return null;
        }

        @Override // com.android.server.wm.DisplayAreaPolicy
        public void addWindow(com.android.server.wm.WindowToken windowToken) {
            findAreaForToken(windowToken).addChild(windowToken);
        }

        @com.android.internal.annotations.VisibleForTesting
        com.android.server.wm.DisplayArea.Tokens findAreaForToken(com.android.server.wm.WindowToken windowToken) {
            return this.mSelectRootForWindowFunc.apply(java.lang.Integer.valueOf(windowToken.windowType), windowToken.mOptions).findAreaForTokenInLayer(windowToken);
        }

        @Override // com.android.server.wm.DisplayAreaPolicy
        public com.android.server.wm.DisplayArea.Tokens findAreaForWindowType(int i, android.os.Bundle bundle, boolean z, boolean z2) {
            return this.mSelectRootForWindowFunc.apply(java.lang.Integer.valueOf(i), bundle).findAreaForWindowTypeInLayer(i, z, z2);
        }

        @com.android.internal.annotations.VisibleForTesting
        java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> getFeatures() {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            arraySet.addAll(this.mRoot.mFeatures);
            for (int i = 0; i < this.mDisplayAreaGroupRoots.size(); i++) {
                arraySet.addAll(this.mDisplayAreaGroupRoots.get(i).mFeatures);
            }
            return new java.util.ArrayList(arraySet);
        }

        @Override // com.android.server.wm.DisplayAreaPolicy
        public java.util.List<com.android.server.wm.DisplayArea<? extends com.android.server.wm.WindowContainer>> getDisplayAreas(int i) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            getDisplayAreas(this.mRoot, i, arrayList);
            for (int i2 = 0; i2 < this.mDisplayAreaGroupRoots.size(); i2++) {
                getDisplayAreas(this.mDisplayAreaGroupRoots.get(i2), i, arrayList);
            }
            return arrayList;
        }

        private static void getDisplayAreas(com.android.server.wm.RootDisplayArea rootDisplayArea, int i, java.util.List<com.android.server.wm.DisplayArea<? extends com.android.server.wm.WindowContainer>> list) {
            java.util.List<com.android.server.wm.DisplayAreaPolicyBuilder.Feature> list2 = rootDisplayArea.mFeatures;
            for (int i2 = 0; i2 < list2.size(); i2++) {
                com.android.server.wm.DisplayAreaPolicyBuilder.Feature feature = list2.get(i2);
                if (feature.mId == i) {
                    list.addAll(rootDisplayArea.mFeatureToDisplayAreas.get(feature));
                }
            }
        }

        @Override // com.android.server.wm.DisplayAreaPolicy
        public com.android.server.wm.TaskDisplayArea getDefaultTaskDisplayArea() {
            return this.mDefaultTaskDisplayArea;
        }

        @Override // com.android.server.wm.DisplayAreaPolicy
        @android.annotation.NonNull
        public com.android.server.wm.TaskDisplayArea getTaskDisplayArea(@android.annotation.Nullable android.os.Bundle bundle) {
            return this.mSelectTaskDisplayAreaFunc.apply(bundle);
        }
    }

    static class PendingArea {

        @android.annotation.Nullable
        com.android.server.wm.DisplayArea mExisting;
        final com.android.server.wm.DisplayAreaPolicyBuilder.Feature mFeature;
        int mMaxLayer;
        final int mMinLayer;
        final com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea mParent;
        final java.util.ArrayList<com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea> mChildren = new java.util.ArrayList<>();
        boolean mSkipTokens = false;

        PendingArea(com.android.server.wm.DisplayAreaPolicyBuilder.Feature feature, int i, com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea) {
            this.mMinLayer = i;
            this.mFeature = feature;
            this.mParent = pendingArea;
        }

        int computeMaxLayer() {
            for (int i = 0; i < this.mChildren.size(); i++) {
                this.mMaxLayer = java.lang.Math.max(this.mMaxLayer, this.mChildren.get(i).computeMaxLayer());
            }
            return this.mMaxLayer;
        }

        void instantiateChildren(com.android.server.wm.DisplayArea<com.android.server.wm.DisplayArea> displayArea, com.android.server.wm.DisplayArea.Tokens[] tokensArr, int i, java.util.Map<com.android.server.wm.DisplayAreaPolicyBuilder.Feature, java.util.List<com.android.server.wm.DisplayArea<com.android.server.wm.WindowContainer>>> map) {
            this.mChildren.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.server.wm.DisplayAreaPolicyBuilder$PendingArea$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(java.lang.Object obj) {
                    int i2;
                    i2 = ((com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea) obj).mMinLayer;
                    return i2;
                }
            }));
            for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
                com.android.server.wm.DisplayAreaPolicyBuilder.PendingArea pendingArea = this.mChildren.get(i2);
                com.android.server.wm.DisplayArea createArea = pendingArea.createArea(displayArea, tokensArr);
                if (createArea != null) {
                    displayArea.addChild((com.android.server.wm.DisplayArea<com.android.server.wm.DisplayArea>) createArea, Integer.MAX_VALUE);
                    if (pendingArea.mFeature != null) {
                        map.get(pendingArea.mFeature).add(createArea);
                    }
                    pendingArea.instantiateChildren(createArea, tokensArr, i + 1, map);
                }
            }
        }

        @android.annotation.Nullable
        private com.android.server.wm.DisplayArea createArea(com.android.server.wm.DisplayArea<com.android.server.wm.DisplayArea> displayArea, com.android.server.wm.DisplayArea.Tokens[] tokensArr) {
            com.android.server.wm.DisplayArea.Type type;
            if (this.mExisting != null) {
                if (this.mExisting.asTokens() != null) {
                    fillAreaForLayers(this.mExisting.asTokens(), tokensArr);
                }
                return this.mExisting;
            }
            if (this.mSkipTokens) {
                return null;
            }
            if (this.mMinLayer > 2) {
                type = com.android.server.wm.DisplayArea.Type.ABOVE_TASKS;
            } else if (this.mMaxLayer < 2) {
                type = com.android.server.wm.DisplayArea.Type.BELOW_TASKS;
            } else {
                type = com.android.server.wm.DisplayArea.Type.ANY;
            }
            if (this.mFeature == null) {
                com.android.server.wm.DisplayArea.Tokens tokens = new com.android.server.wm.DisplayArea.Tokens(displayArea.mWmService, type, "Leaf:" + this.mMinLayer + ":" + this.mMaxLayer);
                fillAreaForLayers(tokens, tokensArr);
                return tokens;
            }
            return this.mFeature.mNewDisplayAreaSupplier.create(displayArea.mWmService, type, this.mFeature.mName + ":" + this.mMinLayer + ":" + this.mMaxLayer, this.mFeature.mId);
        }

        private void fillAreaForLayers(com.android.server.wm.DisplayArea.Tokens tokens, com.android.server.wm.DisplayArea.Tokens[] tokensArr) {
            for (int i = this.mMinLayer; i <= this.mMaxLayer; i++) {
                tokensArr[i] = tokens;
            }
        }
    }
}
