package com.noblelulu.andoid.softaphelper.util

import android.content.Context
import com.tencent.mmkv.MMKV

object MMKVManager {
    private var isInitialized = false
    private lateinit var mmkv: MMKV

    // 私有初始化方法
    private fun initialize(context: Context) {
        if (!isInitialized) {
            val rootDir = MMKV.initialize(context)
            println("MMKV root: $rootDir")
            mmkv = MMKV.mmkvWithID("SoftAPHelper")
            isInitialized = true
        }
    }

    // 检查并初始化（所有公开方法都应先调用此方法）
    private fun checkInit(context: Context) {
        if (!isInitialized) {
            initialize(context.applicationContext)
        }
    }

    // 存储字符串
    fun putString(context: Context, key: String, value: String) {
        checkInit(context)
        mmkv.encode(key, value)
    }

    // 获取字符串
    fun getString(context: Context, key: String, defaultValue: String = ""): String {
        checkInit(context)
        return mmkv.decodeString(key, defaultValue) ?: defaultValue
    }

    // 存储布尔值
    fun putBoolean(context: Context, key: String, value: Boolean) {
        checkInit(context)
        mmkv.encode(key, value)
    }

    // 获取布尔值
    fun getBoolean(context: Context, key: String, defaultValue: Boolean = false): Boolean {
        checkInit(context)
        return mmkv.decodeBool(key, defaultValue)
    }

    // 存储整数
    fun putInt(context: Context, key: String, value: Int) {
        checkInit(context)
        mmkv.encode(key, value)
    }

    // 获取整数
    fun getInt(context: Context, key: String, defaultValue: Int = 0): Int {
        checkInit(context)
        return mmkv.decodeInt(key, defaultValue)
    }

    // 存储长整数
    fun putLong(context: Context, key: String, value: Long) {
        checkInit(context)
        mmkv.encode(key, value)
    }

    // 获取长整数
    fun getLong(context: Context, key: String, defaultValue: Long = 0L): Long {
        checkInit(context)
        return mmkv.decodeLong(key, defaultValue)
    }

    // 存储浮点数
    fun putFloat(context: Context, key: String, value: Float) {
        checkInit(context)
        mmkv.encode(key, value)
    }

    // 获取浮点数
    fun getFloat(context: Context, key: String, defaultValue: Float = 0f): Float {
        checkInit(context)
        return mmkv.decodeFloat(key, defaultValue)
    }

    // 删除键值对
    fun remove(context: Context, key: String) {
        checkInit(context)
        mmkv.removeValueForKey(key)
    }

    // 清除所有数据
    fun clearAll(context: Context) {
        checkInit(context)
        mmkv.clearAll()
    }

    // 检查是否包含某个键
    fun contains(context: Context, key: String): Boolean {
        checkInit(context)
        return mmkv.containsKey(key)
    }
}