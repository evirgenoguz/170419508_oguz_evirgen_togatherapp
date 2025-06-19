package com.evirgenoguz.domain.util

interface ClearableCache {
    suspend fun clearExpiredCache()
    suspend fun clearAllCache()
}