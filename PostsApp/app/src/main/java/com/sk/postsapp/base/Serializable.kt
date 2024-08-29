package com.sk.postsapp.base

interface Serializable<ToType> {
    fun serialize(): ToType
}