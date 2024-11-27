package com.android.server.people.prediction;

/* loaded from: classes2.dex */
class SharesheetModelScorer {
    private static final boolean DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    static final float FOREGROUND_APP_WEIGHT = 0.0f;
    private static final float FREQUENTLY_USED_APP_SCORE_INITIAL_DECAY = 0.3f;
    private static final float RECENCY_INITIAL_BASE_SCORE = 0.4f;
    private static final float RECENCY_SCORE_INITIAL_DECAY = 0.05f;
    private static final float RECENCY_SCORE_SUBSEQUENT_DECAY = 0.02f;
    private static final java.lang.String TAG = "SharesheetModelScorer";
    private static final float USAGE_STATS_CHOOSER_SCORE_INITIAL_DECAY = 0.9f;
    private static final java.lang.Integer RECENCY_SCORE_COUNT = 6;
    private static final long ONE_MONTH_WINDOW = java.util.concurrent.TimeUnit.DAYS.toMillis(30);
    private static final long FOREGROUND_APP_PROMO_TIME_WINDOW = java.util.concurrent.TimeUnit.MINUTES.toMillis(10);

    private SharesheetModelScorer() {
    }

    static void computeScore(java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> list, int i, long j) {
        float f;
        if (list.isEmpty()) {
            return;
        }
        java.util.PriorityQueue priorityQueue = new java.util.PriorityQueue(RECENCY_SCORE_COUNT.intValue(), java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda3
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(java.lang.Object obj) {
                long lambda$computeScore$0;
                lambda$computeScore$0 = com.android.server.people.prediction.SharesheetModelScorer.lambda$computeScore$0((android.util.Pair) obj);
                return lambda$computeScore$0;
            }
        }));
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        int i2 = 0;
        int i3 = 0;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget : list) {
            com.android.server.people.prediction.SharesheetModelScorer.ShareTargetRankingScore shareTargetRankingScore = new com.android.server.people.prediction.SharesheetModelScorer.ShareTargetRankingScore();
            arrayList.add(shareTargetRankingScore);
            if (shareTarget.getEventHistory() != null) {
                java.util.List<android.util.Range<java.lang.Long>> activeTimeSlots = shareTarget.getEventHistory().getEventIndex(com.android.server.people.data.Event.SHARE_EVENT_TYPES).getActiveTimeSlots();
                if (!activeTimeSlots.isEmpty()) {
                    java.util.Iterator<android.util.Range<java.lang.Long>> it = activeTimeSlots.iterator();
                    while (it.hasNext()) {
                        shareTargetRankingScore.incrementFrequencyScore(getFreqDecayedOnElapsedTime(j - it.next().getLower().longValue()));
                    }
                    f2 += shareTargetRankingScore.getFrequencyScore();
                    i2++;
                }
                java.util.List<android.util.Range<java.lang.Long>> activeTimeSlots2 = shareTarget.getEventHistory().getEventIndex(i).getActiveTimeSlots();
                if (!activeTimeSlots2.isEmpty()) {
                    java.util.Iterator<android.util.Range<java.lang.Long>> it2 = activeTimeSlots2.iterator();
                    while (it2.hasNext()) {
                        shareTargetRankingScore.incrementMimeFrequencyScore(getFreqDecayedOnElapsedTime(j - it2.next().getLower().longValue()));
                    }
                    f3 += shareTargetRankingScore.getMimeFrequencyScore();
                    i3++;
                }
                android.util.Range<java.lang.Long> mostRecentActiveTimeSlot = shareTarget.getEventHistory().getEventIndex(com.android.server.people.data.Event.SHARE_EVENT_TYPES).getMostRecentActiveTimeSlot();
                if (mostRecentActiveTimeSlot != null && (priorityQueue.size() < RECENCY_SCORE_COUNT.intValue() || mostRecentActiveTimeSlot.getUpper().longValue() > ((java.lang.Long) ((android.util.Range) ((android.util.Pair) priorityQueue.peek()).second).getUpper()).longValue())) {
                    if (priorityQueue.size() == RECENCY_SCORE_COUNT.intValue()) {
                        priorityQueue.poll();
                    }
                    priorityQueue.offer(new android.util.Pair(shareTargetRankingScore, mostRecentActiveTimeSlot));
                }
            }
        }
        while (!priorityQueue.isEmpty()) {
            if (priorityQueue.size() <= 1) {
                f = RECENCY_INITIAL_BASE_SCORE;
            } else {
                f = 0.35f - ((priorityQueue.size() - 2) * RECENCY_SCORE_SUBSEQUENT_DECAY);
            }
            ((com.android.server.people.prediction.SharesheetModelScorer.ShareTargetRankingScore) ((android.util.Pair) priorityQueue.poll()).first).setRecencyScore(f);
        }
        java.lang.Float valueOf = java.lang.Float.valueOf(i2 != 0 ? f2 / i2 : 0.0f);
        java.lang.Float valueOf2 = java.lang.Float.valueOf(i3 != 0 ? f3 / i3 : 0.0f);
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget2 = list.get(i4);
            com.android.server.people.prediction.SharesheetModelScorer.ShareTargetRankingScore shareTargetRankingScore2 = (com.android.server.people.prediction.SharesheetModelScorer.ShareTargetRankingScore) arrayList.get(i4);
            double d = 0.0d;
            shareTargetRankingScore2.setFrequencyScore(normalizeFreqScore(valueOf.equals(java.lang.Float.valueOf(0.0f)) ? 0.0d : shareTargetRankingScore2.getFrequencyScore() / valueOf.floatValue()));
            if (!valueOf2.equals(java.lang.Float.valueOf(0.0f))) {
                d = shareTargetRankingScore2.getMimeFrequencyScore() / valueOf2.floatValue();
            }
            shareTargetRankingScore2.setMimeFrequencyScore(normalizeMimeFreqScore(d));
            shareTargetRankingScore2.setTotalScore(probOR(probOR(shareTargetRankingScore2.getRecencyScore(), shareTargetRankingScore2.getFrequencyScore()), shareTargetRankingScore2.getMimeFrequencyScore()));
            shareTarget2.setScore(shareTargetRankingScore2.getTotalScore());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ long lambda$computeScore$0(android.util.Pair pair) {
        return ((java.lang.Long) ((android.util.Range) pair.second).getUpper()).longValue();
    }

    static void computeScoreForAppShare(java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> list, int i, int i2, long j, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i3, @android.annotation.Nullable java.lang.String str) {
        computeScore(list, i, j);
        postProcess(list, i2, dataManager, i3, str);
    }

    private static void postProcess(java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget> list, int i, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i2, @android.annotation.Nullable java.lang.String str) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget : list) {
            java.lang.String packageName = shareTarget.getAppTarget().getPackageName();
            arrayMap.computeIfAbsent(packageName, new java.util.function.Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.List lambda$postProcess$1;
                    lambda$postProcess$1 = com.android.server.people.prediction.SharesheetModelScorer.lambda$postProcess$1((java.lang.String) obj);
                    return lambda$postProcess$1;
                }
            });
            java.util.List list2 = (java.util.List) arrayMap.get(packageName);
            int i3 = 0;
            while (i3 < list2.size() && shareTarget.getScore() <= ((com.android.server.people.prediction.ShareTargetPredictor.ShareTarget) list2.get(i3)).getScore()) {
                i3++;
            }
            list2.add(i3, shareTarget);
        }
        promoteForegroundApp(arrayMap, dataManager, i2, str);
        promoteMostChosenAndFrequentlyUsedApps(arrayMap, i, dataManager, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.List lambda$postProcess$1(java.lang.String str) {
        return new java.util.ArrayList();
    }

    private static void promoteMostChosenAndFrequentlyUsedApps(java.util.Map<java.lang.String, java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget>> map, int i, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i2) {
        java.util.Iterator<java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget>> it = map.values().iterator();
        int i3 = 0;
        float f = 1.0f;
        while (it.hasNext()) {
            for (com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget : it.next()) {
                if (shareTarget.getScore() > 0.0f) {
                    i3++;
                    f = java.lang.Math.min(shareTarget.getScore(), f);
                }
            }
        }
        if (i3 >= i) {
            return;
        }
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.Map<java.lang.String, com.android.server.people.data.AppUsageStatsData> queryAppUsageStats = dataManager.queryAppUsageStats(i2, currentTimeMillis - ONE_MONTH_WINDOW, currentTimeMillis, map.keySet());
        float promoteApp = promoteApp(map, queryAppUsageStats, new java.util.function.Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((com.android.server.people.data.AppUsageStatsData) obj).getChosenCount());
            }
        }, USAGE_STATS_CHOOSER_SCORE_INITIAL_DECAY * f, f);
        promoteApp(map, queryAppUsageStats, new java.util.function.Function() { // from class: com.android.server.people.prediction.SharesheetModelScorer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((com.android.server.people.data.AppUsageStatsData) obj).getLaunchCount());
            }
        }, FREQUENTLY_USED_APP_SCORE_INITIAL_DECAY * promoteApp, promoteApp);
    }

    private static float promoteApp(java.util.Map<java.lang.String, java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget>> map, java.util.Map<java.lang.String, com.android.server.people.data.AppUsageStatsData> map2, java.util.function.Function<com.android.server.people.data.AppUsageStatsData, java.lang.Integer> function, float f, float f2) {
        java.util.Iterator<com.android.server.people.data.AppUsageStatsData> it = map2.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            i = java.lang.Math.max(i, function.apply(it.next()).intValue());
        }
        if (i > 0) {
            for (java.util.Map.Entry<java.lang.String, com.android.server.people.data.AppUsageStatsData> entry : map2.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget = map.get(entry.getKey()).get(0);
                    if (shareTarget.getScore() <= 0.0f) {
                        float intValue = (function.apply(entry.getValue()).intValue() * f) / i;
                        shareTarget.setScore(intValue);
                        if (intValue > 0.0f) {
                            f2 = java.lang.Math.min(f2, intValue);
                        }
                    }
                }
            }
        }
        return f2;
    }

    private static void promoteForegroundApp(java.util.Map<java.lang.String, java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget>> map, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i, @android.annotation.Nullable java.lang.String str) {
        java.lang.String findSharingForegroundApp = findSharingForegroundApp(map, dataManager, i, str);
        if (findSharingForegroundApp != null) {
            com.android.server.people.prediction.ShareTargetPredictor.ShareTarget shareTarget = map.get(findSharingForegroundApp).get(0);
            shareTarget.setScore(probOR(shareTarget.getScore(), 0.0f));
        }
    }

    @android.annotation.Nullable
    private static java.lang.String findSharingForegroundApp(java.util.Map<java.lang.String, java.util.List<com.android.server.people.prediction.ShareTargetPredictor.ShareTarget>> map, @android.annotation.NonNull com.android.server.people.data.DataManager dataManager, int i, @android.annotation.Nullable java.lang.String str) {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        java.util.List<android.app.usage.UsageEvents.Event> queryAppMovingToForegroundEvents = dataManager.queryAppMovingToForegroundEvents(i, currentTimeMillis - FOREGROUND_APP_PROMO_TIME_WINDOW, currentTimeMillis);
        java.lang.String str2 = null;
        for (int size = queryAppMovingToForegroundEvents.size() - 1; size >= 0; size--) {
            java.lang.String className = queryAppMovingToForegroundEvents.get(size).getClassName();
            java.lang.String packageName = queryAppMovingToForegroundEvents.get(size).getPackageName();
            if (packageName != null && (className == null || str == null || !className.contains(str))) {
                if (str2 == null) {
                    str2 = packageName;
                } else if (!packageName.equals(str2) && map.containsKey(packageName)) {
                    return packageName;
                }
            }
        }
        return null;
    }

    private static float probOR(float f, float f2) {
        return 1.0f - ((1.0f - f) * (1.0f - f2));
    }

    private static float getFreqDecayedOnElapsedTime(long j) {
        java.time.Duration ofMillis = java.time.Duration.ofMillis(j);
        if (ofMillis.compareTo(java.time.Duration.ofDays(1L)) <= 0) {
            return 1.0f;
        }
        if (ofMillis.compareTo(java.time.Duration.ofDays(3L)) <= 0) {
            return USAGE_STATS_CHOOSER_SCORE_INITIAL_DECAY;
        }
        if (ofMillis.compareTo(java.time.Duration.ofDays(7L)) <= 0) {
            return 0.8f;
        }
        if (ofMillis.compareTo(java.time.Duration.ofDays(14L)) <= 0) {
            return 0.7f;
        }
        return 0.6f;
    }

    private static float normalizeFreqScore(double d) {
        if (d >= 2.5d) {
            return 0.2f;
        }
        if (d >= 1.5d) {
            return 0.15f;
        }
        if (d >= 1.0d) {
            return 0.1f;
        }
        if (d >= 0.75d) {
            return RECENCY_SCORE_INITIAL_DECAY;
        }
        return 0.0f;
    }

    private static float normalizeMimeFreqScore(double d) {
        if (d >= 2.0d) {
            return 0.2f;
        }
        if (d >= 1.2d) {
            return 0.15f;
        }
        if (d > 0.0d) {
            return 0.1f;
        }
        return 0.0f;
    }

    private static class ShareTargetRankingScore {
        private float mFrequencyScore;
        private float mMimeFrequencyScore;
        private float mRecencyScore;
        private float mTotalScore;

        private ShareTargetRankingScore() {
            this.mRecencyScore = 0.0f;
            this.mFrequencyScore = 0.0f;
            this.mMimeFrequencyScore = 0.0f;
            this.mTotalScore = 0.0f;
        }

        float getTotalScore() {
            return this.mTotalScore;
        }

        void setTotalScore(float f) {
            this.mTotalScore = f;
        }

        float getRecencyScore() {
            return this.mRecencyScore;
        }

        void setRecencyScore(float f) {
            this.mRecencyScore = f;
        }

        float getFrequencyScore() {
            return this.mFrequencyScore;
        }

        void setFrequencyScore(float f) {
            this.mFrequencyScore = f;
        }

        void incrementFrequencyScore(float f) {
            this.mFrequencyScore += f;
        }

        float getMimeFrequencyScore() {
            return this.mMimeFrequencyScore;
        }

        void setMimeFrequencyScore(float f) {
            this.mMimeFrequencyScore = f;
        }

        void incrementMimeFrequencyScore(float f) {
            this.mMimeFrequencyScore += f;
        }
    }
}
