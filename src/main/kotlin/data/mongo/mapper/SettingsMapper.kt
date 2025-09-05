package com.data.mapper

import com.data.entity.user.UserEntity.SettingsEntity
import domain.model.user.User.Settings

fun Settings.toSettingsEntity(): SettingsEntity{
    return SettingsEntity(
        enableQuizTime  = enableQuizTime,
        enableNotificationApp = enableNotificationApp,
        switchToCustomTimeInMin = switchToCustomTimeInMin,
        customQuizTimeInMin = customQuizTimeInMin
    )
}

fun SettingsEntity.toSettings(): Settings{
    return Settings(
        enableQuizTime  = enableQuizTime,
        enableNotificationApp = enableNotificationApp,
        switchToCustomTimeInMin = switchToCustomTimeInMin,
        customQuizTimeInMin = customQuizTimeInMin
    )
}


