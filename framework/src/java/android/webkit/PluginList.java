package android.webkit;

@java.lang.Deprecated
/* loaded from: classes4.dex */
public class PluginList {
    private java.util.ArrayList<android.webkit.Plugin> mPlugins = new java.util.ArrayList<>();

    @java.lang.Deprecated
    public PluginList() {
    }

    @java.lang.Deprecated
    public synchronized java.util.List getList() {
        return this.mPlugins;
    }

    @java.lang.Deprecated
    public synchronized void addPlugin(android.webkit.Plugin plugin) {
        if (!this.mPlugins.contains(plugin)) {
            this.mPlugins.add(plugin);
        }
    }

    @java.lang.Deprecated
    public synchronized void removePlugin(android.webkit.Plugin plugin) {
        int indexOf = this.mPlugins.indexOf(plugin);
        if (indexOf != -1) {
            this.mPlugins.remove(indexOf);
        }
    }

    @java.lang.Deprecated
    public synchronized void clear() {
        this.mPlugins.clear();
    }

    @java.lang.Deprecated
    public synchronized void pluginClicked(android.content.Context context, int i) {
        try {
            this.mPlugins.get(i).dispatchClickEvent(context);
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
    }
}
