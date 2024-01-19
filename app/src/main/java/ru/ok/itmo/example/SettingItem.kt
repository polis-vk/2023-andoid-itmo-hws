package ru.ok.itmo.example
data class SettingCategory(val title: String, val items: List<SettingItem>)

data class SettingItem(val title: String, val subtitle: String?, val parameter: String?)