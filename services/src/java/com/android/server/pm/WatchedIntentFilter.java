package com.android.server.pm;

/* loaded from: classes2.dex */
public class WatchedIntentFilter extends com.android.server.utils.WatchableImpl implements com.android.server.utils.Snappable<com.android.server.pm.WatchedIntentFilter> {
    protected android.content.IntentFilter mFilter;

    private class WatchedIterator<E> implements java.util.Iterator<E> {
        private final java.util.Iterator<E> mIterator;

        WatchedIterator(@android.annotation.NonNull java.util.Iterator<E> it) {
            this.mIterator = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.mIterator.hasNext();
        }

        @Override // java.util.Iterator
        public E next() {
            return this.mIterator.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.mIterator.remove();
            com.android.server.pm.WatchedIntentFilter.this.onChanged();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(java.util.function.Consumer<? super E> consumer) {
            this.mIterator.forEachRemaining(consumer);
            com.android.server.pm.WatchedIntentFilter.this.onChanged();
        }
    }

    private <E> java.util.Iterator<E> maybeWatch(java.util.Iterator<E> it) {
        return it == null ? it : new com.android.server.pm.WatchedIntentFilter.WatchedIterator(it);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onChanged() {
        dispatchChange(this);
    }

    protected WatchedIntentFilter() {
        this.mFilter = new android.content.IntentFilter();
    }

    public WatchedIntentFilter(android.content.IntentFilter intentFilter) {
        this.mFilter = new android.content.IntentFilter(intentFilter);
    }

    protected WatchedIntentFilter(com.android.server.pm.WatchedIntentFilter watchedIntentFilter) {
        this(watchedIntentFilter.getIntentFilter());
    }

    public WatchedIntentFilter(java.lang.String str) {
        this.mFilter = new android.content.IntentFilter(str);
    }

    public WatchedIntentFilter(java.lang.String str, java.lang.String str2) throws android.content.IntentFilter.MalformedMimeTypeException {
        this.mFilter = new android.content.IntentFilter(str, str2);
    }

    public com.android.server.pm.WatchedIntentFilter cloneFilter() {
        return new com.android.server.pm.WatchedIntentFilter(this.mFilter);
    }

    public android.content.IntentFilter getIntentFilter() {
        return this.mFilter;
    }

    public final void setPriority(int i) {
        this.mFilter.setPriority(i);
        onChanged();
    }

    public final int getPriority() {
        return this.mFilter.getPriority();
    }

    public final void setOrder(int i) {
        this.mFilter.setOrder(i);
        onChanged();
    }

    public final int getOrder() {
        return this.mFilter.getOrder();
    }

    public final boolean getAutoVerify() {
        return this.mFilter.getAutoVerify();
    }

    public final boolean handleAllWebDataURI() {
        return this.mFilter.handleAllWebDataURI();
    }

    public final boolean handlesWebUris(boolean z) {
        return this.mFilter.handlesWebUris(z);
    }

    public final boolean needsVerification() {
        return this.mFilter.needsVerification();
    }

    public void setVerified(boolean z) {
        this.mFilter.setVerified(z);
        onChanged();
    }

    public void setVisibilityToInstantApp(int i) {
        this.mFilter.setVisibilityToInstantApp(i);
        onChanged();
    }

    public int getVisibilityToInstantApp() {
        return this.mFilter.getVisibilityToInstantApp();
    }

    public boolean isVisibleToInstantApp() {
        return this.mFilter.isVisibleToInstantApp();
    }

    public boolean isExplicitlyVisibleToInstantApp() {
        return this.mFilter.isExplicitlyVisibleToInstantApp();
    }

    public boolean isImplicitlyVisibleToInstantApp() {
        return this.mFilter.isImplicitlyVisibleToInstantApp();
    }

    public final void addAction(java.lang.String str) {
        this.mFilter.addAction(str);
        onChanged();
    }

    public final int countActions() {
        return this.mFilter.countActions();
    }

    public final java.lang.String getAction(int i) {
        return this.mFilter.getAction(i);
    }

    public final boolean hasAction(java.lang.String str) {
        return this.mFilter.hasAction(str);
    }

    public final boolean matchAction(java.lang.String str) {
        return this.mFilter.matchAction(str);
    }

    public final java.util.Iterator<java.lang.String> actionsIterator() {
        return maybeWatch(this.mFilter.actionsIterator());
    }

    public final void addDataType(java.lang.String str) throws android.content.IntentFilter.MalformedMimeTypeException {
        this.mFilter.addDataType(str);
        onChanged();
    }

    public final void addDynamicDataType(java.lang.String str) throws android.content.IntentFilter.MalformedMimeTypeException {
        this.mFilter.addDynamicDataType(str);
        onChanged();
    }

    public final void clearDynamicDataTypes() {
        this.mFilter.clearDynamicDataTypes();
        onChanged();
    }

    public int countStaticDataTypes() {
        return this.mFilter.countStaticDataTypes();
    }

    public final boolean hasDataType(java.lang.String str) {
        return this.mFilter.hasDataType(str);
    }

    public final boolean hasExactDynamicDataType(java.lang.String str) {
        return this.mFilter.hasExactDynamicDataType(str);
    }

    public final boolean hasExactStaticDataType(java.lang.String str) {
        return this.mFilter.hasExactStaticDataType(str);
    }

    public final int countDataTypes() {
        return this.mFilter.countDataTypes();
    }

    public final java.lang.String getDataType(int i) {
        return this.mFilter.getDataType(i);
    }

    public final java.util.Iterator<java.lang.String> typesIterator() {
        return maybeWatch(this.mFilter.typesIterator());
    }

    public final java.util.List<java.lang.String> dataTypes() {
        return this.mFilter.dataTypes();
    }

    public final void addMimeGroup(java.lang.String str) {
        this.mFilter.addMimeGroup(str);
        onChanged();
    }

    public final boolean hasMimeGroup(java.lang.String str) {
        return this.mFilter.hasMimeGroup(str);
    }

    public final java.lang.String getMimeGroup(int i) {
        return this.mFilter.getMimeGroup(i);
    }

    public final int countMimeGroups() {
        return this.mFilter.countMimeGroups();
    }

    public final java.util.Iterator<java.lang.String> mimeGroupsIterator() {
        return maybeWatch(this.mFilter.mimeGroupsIterator());
    }

    public final void addDataScheme(java.lang.String str) {
        this.mFilter.addDataScheme(str);
        onChanged();
    }

    public final int countDataSchemes() {
        return this.mFilter.countDataSchemes();
    }

    public final java.lang.String getDataScheme(int i) {
        return this.mFilter.getDataScheme(i);
    }

    public final boolean hasDataScheme(java.lang.String str) {
        return this.mFilter.hasDataScheme(str);
    }

    public final java.util.Iterator<java.lang.String> schemesIterator() {
        return maybeWatch(this.mFilter.schemesIterator());
    }

    public final void addDataSchemeSpecificPart(java.lang.String str, int i) {
        this.mFilter.addDataSchemeSpecificPart(str, i);
        onChanged();
    }

    public final void addDataSchemeSpecificPart(android.os.PatternMatcher patternMatcher) {
        this.mFilter.addDataSchemeSpecificPart(patternMatcher);
        onChanged();
    }

    public final int countDataSchemeSpecificParts() {
        return this.mFilter.countDataSchemeSpecificParts();
    }

    public final android.os.PatternMatcher getDataSchemeSpecificPart(int i) {
        return this.mFilter.getDataSchemeSpecificPart(i);
    }

    public final boolean hasDataSchemeSpecificPart(java.lang.String str) {
        return this.mFilter.hasDataSchemeSpecificPart(str);
    }

    public final java.util.Iterator<android.os.PatternMatcher> schemeSpecificPartsIterator() {
        return maybeWatch(this.mFilter.schemeSpecificPartsIterator());
    }

    public final void addDataAuthority(java.lang.String str, java.lang.String str2) {
        this.mFilter.addDataAuthority(str, str2);
        onChanged();
    }

    public final void addDataAuthority(android.content.IntentFilter.AuthorityEntry authorityEntry) {
        this.mFilter.addDataAuthority(authorityEntry);
        onChanged();
    }

    public final int countDataAuthorities() {
        return this.mFilter.countDataAuthorities();
    }

    public final android.content.IntentFilter.AuthorityEntry getDataAuthority(int i) {
        return this.mFilter.getDataAuthority(i);
    }

    public final boolean hasDataAuthority(android.net.Uri uri) {
        return this.mFilter.hasDataAuthority(uri);
    }

    public final java.util.Iterator<android.content.IntentFilter.AuthorityEntry> authoritiesIterator() {
        return maybeWatch(this.mFilter.authoritiesIterator());
    }

    public final void addDataPath(java.lang.String str, int i) {
        this.mFilter.addDataPath(str, i);
        onChanged();
    }

    public final void addDataPath(android.os.PatternMatcher patternMatcher) {
        this.mFilter.addDataPath(patternMatcher);
        onChanged();
    }

    public final int countDataPaths() {
        return this.mFilter.countDataPaths();
    }

    public final android.os.PatternMatcher getDataPath(int i) {
        return this.mFilter.getDataPath(i);
    }

    public final boolean hasDataPath(java.lang.String str) {
        return this.mFilter.hasDataPath(str);
    }

    public final java.util.Iterator<android.os.PatternMatcher> pathsIterator() {
        return maybeWatch(this.mFilter.pathsIterator());
    }

    public final int matchDataAuthority(android.net.Uri uri) {
        return this.mFilter.matchDataAuthority(uri);
    }

    public final int matchDataAuthority(android.net.Uri uri, boolean z) {
        return this.mFilter.matchDataAuthority(uri, z);
    }

    public final int matchData(java.lang.String str, java.lang.String str2, android.net.Uri uri) {
        return this.mFilter.matchData(str, str2, uri);
    }

    public final void addCategory(java.lang.String str) {
        this.mFilter.addCategory(str);
    }

    public final int countCategories() {
        return this.mFilter.countCategories();
    }

    public final java.lang.String getCategory(int i) {
        return this.mFilter.getCategory(i);
    }

    public final boolean hasCategory(java.lang.String str) {
        return this.mFilter.hasCategory(str);
    }

    public final java.util.Iterator<java.lang.String> categoriesIterator() {
        return maybeWatch(this.mFilter.categoriesIterator());
    }

    public final java.lang.String matchCategories(java.util.Set<java.lang.String> set) {
        return this.mFilter.matchCategories(set);
    }

    public final int match(android.content.ContentResolver contentResolver, android.content.Intent intent, boolean z, java.lang.String str) {
        return this.mFilter.match(contentResolver, intent, z, str);
    }

    public final int match(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.util.Set<java.lang.String> set, java.lang.String str4) {
        return this.mFilter.match(str, str2, str3, uri, set, str4);
    }

    public final int match(java.lang.String str, java.lang.String str2, java.lang.String str3, android.net.Uri uri, java.util.Set<java.lang.String> set, java.lang.String str4, boolean z, java.util.Collection<java.lang.String> collection) {
        return this.mFilter.match(str, str2, str3, uri, set, str4, z, collection);
    }

    public void dump(android.util.Printer printer, java.lang.String str) {
        this.mFilter.dump(printer, str);
    }

    public final int describeContents() {
        return this.mFilter.describeContents();
    }

    public boolean debugCheck() {
        return this.mFilter.debugCheck();
    }

    public boolean checkDataPathAndSchemeSpecificParts() {
        return this.mFilter.checkDataPathAndSchemeSpecificParts();
    }

    public java.util.ArrayList<java.lang.String> getHostsList() {
        return this.mFilter.getHostsList();
    }

    public java.lang.String[] getHosts() {
        return this.mFilter.getHosts();
    }

    public static java.util.List<com.android.server.pm.WatchedIntentFilter> toWatchedIntentFilterList(java.util.List<android.content.IntentFilter> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(new com.android.server.pm.WatchedIntentFilter(list.get(i)));
        }
        return arrayList;
    }

    public static java.util.List<android.content.IntentFilter> toIntentFilterList(java.util.List<com.android.server.pm.WatchedIntentFilter> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(list.get(i).getIntentFilter());
        }
        return arrayList;
    }

    @Override // com.android.server.utils.Snappable
    public com.android.server.pm.WatchedIntentFilter snapshot() {
        return new com.android.server.pm.WatchedIntentFilter(this);
    }
}
