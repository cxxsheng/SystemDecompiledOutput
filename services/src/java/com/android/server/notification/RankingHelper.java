package com.android.server.notification;

/* loaded from: classes2.dex */
public class RankingHelper {
    private static final java.lang.String TAG = "RankingHelper";
    private final android.content.Context mContext;
    private final com.android.server.notification.NotificationComparator mPreliminaryComparator;
    private final com.android.server.notification.RankingHandler mRankingHandler;
    private final com.android.server.notification.NotificationSignalExtractor[] mSignalExtractors;
    private final com.android.server.notification.GlobalSortKeyComparator mFinalComparator = new com.android.server.notification.GlobalSortKeyComparator();
    private final android.util.ArrayMap<java.lang.String, com.android.server.notification.NotificationRecord> mProxyByGroupTmp = new android.util.ArrayMap<>();

    public RankingHelper(android.content.Context context, com.android.server.notification.RankingHandler rankingHandler, com.android.server.notification.RankingConfig rankingConfig, com.android.server.notification.ZenModeHelper zenModeHelper, com.android.server.notification.NotificationUsageStats notificationUsageStats, java.lang.String[] strArr) {
        this.mContext = context;
        this.mRankingHandler = rankingHandler;
        this.mPreliminaryComparator = new com.android.server.notification.NotificationComparator(this.mContext);
        int length = strArr.length;
        this.mSignalExtractors = new com.android.server.notification.NotificationSignalExtractor[length];
        for (int i = 0; i < length; i++) {
            try {
                com.android.server.notification.NotificationSignalExtractor notificationSignalExtractor = (com.android.server.notification.NotificationSignalExtractor) this.mContext.getClassLoader().loadClass(strArr[i]).newInstance();
                notificationSignalExtractor.initialize(this.mContext, notificationUsageStats);
                notificationSignalExtractor.setConfig(rankingConfig);
                notificationSignalExtractor.setZenHelper(zenModeHelper);
                this.mSignalExtractors[i] = notificationSignalExtractor;
            } catch (java.lang.ClassNotFoundException e) {
                android.util.Slog.w(TAG, "Couldn't find extractor " + strArr[i] + ".", e);
            } catch (java.lang.IllegalAccessException e2) {
                android.util.Slog.w(TAG, "Problem accessing extractor " + strArr[i] + ".", e2);
            } catch (java.lang.InstantiationException e3) {
                android.util.Slog.w(TAG, "Couldn't instantiate extractor " + strArr[i] + ".", e3);
            }
        }
    }

    public <T extends com.android.server.notification.NotificationSignalExtractor> T findExtractor(java.lang.Class<T> cls) {
        int length = this.mSignalExtractors.length;
        for (int i = 0; i < length; i++) {
            T t = (T) this.mSignalExtractors[i];
            if (cls.equals(t.getClass())) {
                return t;
            }
        }
        return null;
    }

    public void extractSignals(com.android.server.notification.NotificationRecord notificationRecord) {
        int length = this.mSignalExtractors.length;
        for (int i = 0; i < length; i++) {
            try {
                com.android.server.notification.RankingReconsideration process = this.mSignalExtractors[i].process(notificationRecord);
                if (process != null) {
                    this.mRankingHandler.requestReconsideration(process);
                }
            } catch (java.lang.Throwable th) {
                android.util.Slog.w(TAG, "NotificationSignalExtractor failed.", th);
            }
        }
    }

    public void sort(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList) {
        java.lang.String str;
        int size = arrayList.size();
        for (int i = size - 1; i >= 0; i--) {
            arrayList.get(i).setGlobalSortKey(null);
        }
        synchronized (this.mPreliminaryComparator.mStateLock) {
            arrayList.sort(this.mPreliminaryComparator);
        }
        synchronized (this.mProxyByGroupTmp) {
            for (int i2 = 0; i2 < size; i2++) {
                try {
                    com.android.server.notification.NotificationRecord notificationRecord = arrayList.get(i2);
                    notificationRecord.setAuthoritativeRank(i2);
                    java.lang.String groupKey = notificationRecord.getGroupKey();
                    if (this.mProxyByGroupTmp.get(groupKey) == null) {
                        this.mProxyByGroupTmp.put(groupKey, notificationRecord);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            for (int i3 = 0; i3 < size; i3++) {
                com.android.server.notification.NotificationRecord notificationRecord2 = arrayList.get(i3);
                com.android.server.notification.NotificationRecord notificationRecord3 = this.mProxyByGroupTmp.get(notificationRecord2.getGroupKey());
                java.lang.String sortKey = notificationRecord2.getNotification().getSortKey();
                if (sortKey == null) {
                    str = "nsk";
                } else if (sortKey.equals("")) {
                    str = "esk";
                } else {
                    str = "gsk=" + sortKey;
                }
                boolean isGroupSummary = notificationRecord2.getNotification().isGroupSummary();
                java.lang.Integer valueOf = java.lang.Integer.valueOf(notificationRecord2.getCriticality());
                char c = '1';
                java.lang.Character valueOf2 = java.lang.Character.valueOf((!notificationRecord2.isRecentlyIntrusive() || notificationRecord2.getImportance() <= 1) ? '1' : '0');
                java.lang.Integer valueOf3 = java.lang.Integer.valueOf(notificationRecord3.getAuthoritativeRank());
                if (isGroupSummary) {
                    c = '0';
                }
                notificationRecord2.setGlobalSortKey(android.text.TextUtils.formatSimple("crtcl=0x%04x:intrsv=%c:grnk=0x%04x:gsmry=%c:%s:rnk=0x%04x", new java.lang.Object[]{valueOf, valueOf2, valueOf3, java.lang.Character.valueOf(c), str, java.lang.Integer.valueOf(notificationRecord2.getAuthoritativeRank())}));
            }
            this.mProxyByGroupTmp.clear();
        }
        java.util.Collections.sort(arrayList, this.mFinalComparator);
    }

    public int indexOf(java.util.ArrayList<com.android.server.notification.NotificationRecord> arrayList, com.android.server.notification.NotificationRecord notificationRecord) {
        return java.util.Collections.binarySearch(arrayList, notificationRecord, this.mFinalComparator);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        int length = this.mSignalExtractors.length;
        printWriter.print(str);
        printWriter.print("mSignalExtractors.length = ");
        printWriter.println(length);
        for (int i = 0; i < length; i++) {
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.println(this.mSignalExtractors[i].getClass().getSimpleName());
        }
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, @android.annotation.NonNull com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        int length = this.mSignalExtractors.length;
        for (int i = 0; i < length; i++) {
            protoOutputStream.write(2237677961217L, this.mSignalExtractors[i].getClass().getSimpleName());
        }
    }
}
