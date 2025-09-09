package com.common



fun String.getFileNameFromUrl(): String {
    val lastSlashIndex = this.lastIndexOf('/')
    return if (lastSlashIndex != -1) {
        this.substring(lastSlashIndex + 1)
    } else {
        this
    }
}
