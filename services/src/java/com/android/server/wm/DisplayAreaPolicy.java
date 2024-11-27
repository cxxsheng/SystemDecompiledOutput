package com.android.server.wm;

/* loaded from: classes3.dex */
public abstract class DisplayAreaPolicy {
    protected final com.android.server.wm.RootDisplayArea mRoot;
    protected final com.android.server.wm.WindowManagerService mWmService;

    public abstract void addWindow(com.android.server.wm.WindowToken windowToken);

    public abstract com.android.server.wm.DisplayArea.Tokens findAreaForWindowType(int i, android.os.Bundle bundle, boolean z, boolean z2);

    public abstract com.android.server.wm.TaskDisplayArea getDefaultTaskDisplayArea();

    public abstract java.util.List<com.android.server.wm.DisplayArea<? extends com.android.server.wm.WindowContainer>> getDisplayAreas(int i);

    public abstract com.android.server.wm.TaskDisplayArea getTaskDisplayArea(@android.annotation.Nullable android.os.Bundle bundle);

    protected DisplayAreaPolicy(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.RootDisplayArea rootDisplayArea) {
        this.mWmService = windowManagerService;
        this.mRoot = rootDisplayArea;
    }

    static final class DefaultProvider implements com.android.server.wm.DisplayAreaPolicy.Provider {
        DefaultProvider() {
        }

        @Override // com.android.server.wm.DisplayAreaPolicy.Provider
        public com.android.server.wm.DisplayAreaPolicy instantiate(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, com.android.server.wm.RootDisplayArea rootDisplayArea, com.android.server.wm.DisplayArea.Tokens tokens) {
            com.android.server.wm.TaskDisplayArea taskDisplayArea = new com.android.server.wm.TaskDisplayArea(displayContent, windowManagerService, "DefaultTaskDisplayArea", 1);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(taskDisplayArea);
            com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder = new com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder(rootDisplayArea);
            hierarchyBuilder.setImeContainer(tokens).setTaskDisplayAreas(arrayList);
            if (displayContent.isTrusted()) {
                configureTrustedHierarchyBuilder(hierarchyBuilder, windowManagerService, displayContent);
            }
            return new com.android.server.wm.DisplayAreaPolicyBuilder().setRootHierarchy(hierarchyBuilder).build(windowManagerService);
        }

        private void configureTrustedHierarchyBuilder(com.android.server.wm.DisplayAreaPolicyBuilder.HierarchyBuilder hierarchyBuilder, com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
            hierarchyBuilder.addFeature(new com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder(windowManagerService.mPolicy, "WindowedMagnification", 4).upTo(2039).except(2039).setNewDisplayAreaSupplier(new com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier() { // from class: com.android.server.wm.DisplayAreaPolicy$DefaultProvider$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.DisplayAreaPolicyBuilder.NewDisplayAreaSupplier
                public final com.android.server.wm.DisplayArea create(com.android.server.wm.WindowManagerService windowManagerService2, com.android.server.wm.DisplayArea.Type type, java.lang.String str, int i) {
                    return new com.android.server.wm.DisplayArea.Dimmable(windowManagerService2, type, str, i);
                }
            }).build());
            if (displayContent.isDefaultDisplay) {
                hierarchyBuilder.addFeature(new com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder(windowManagerService.mPolicy, "HideDisplayCutout", 6).all().except(2019, 2024, 2000, 2040).build()).addFeature(new com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder(windowManagerService.mPolicy, "OneHanded", 3).all().except(2019, 2024, 2015).build());
            }
            hierarchyBuilder.addFeature(new com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder(windowManagerService.mPolicy, "FullscreenMagnification", 5).all().except(2039, 2011, 2012, 2027, 2019, 2024).build()).addFeature(new com.android.server.wm.DisplayAreaPolicyBuilder.Feature.Builder(windowManagerService.mPolicy, "ImePlaceholder", 7).and(2011, 2012).build());
        }
    }

    public interface Provider {
        com.android.server.wm.DisplayAreaPolicy instantiate(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent, com.android.server.wm.RootDisplayArea rootDisplayArea, com.android.server.wm.DisplayArea.Tokens tokens);

        static com.android.server.wm.DisplayAreaPolicy.Provider fromResources(android.content.res.Resources resources) {
            java.lang.String string = resources.getString(android.R.string.config_defaultWearableSensingService);
            if (android.text.TextUtils.isEmpty(string)) {
                return new com.android.server.wm.DisplayAreaPolicy.DefaultProvider();
            }
            try {
                return (com.android.server.wm.DisplayAreaPolicy.Provider) java.lang.Class.forName(string).newInstance();
            } catch (java.lang.ClassCastException | java.lang.ReflectiveOperationException e) {
                throw new java.lang.IllegalStateException("Couldn't instantiate class " + string + " for config_deviceSpecificDisplayAreaPolicyProvider: make sure it has a public zero-argument constructor and implements DisplayAreaPolicy.Provider", e);
            }
        }
    }
}
