package com.example.pojecttest

import android.appwidget.AppWidgetHost
import android.content.Context

fun deCityListXml_fun(context: Context): List<cls> {

    var wData = context.assets.open("city_list.xml").bufferedReader().use { it.readText() }

    val reg =
        """<city(?: type="(.*?)")?>\s*<name>(.*?)</name>\s*<name_en>(.*?)</name_en>\s*<file_name>(.*?)</file_name>\s*</city>""".toRegex()
    val data = reg.findAll(wData)
    val list = data.map { i ->
        val type = i.groupValues[1]
        val name = i.groupValues[2]
        val name_en = i.groupValues[3]
        val file_name = i.groupValues[4]
        cls(type, name, name_en, file_name)
    }.toMutableList()
    while (list[0].type != "current") {
        list.add(list.removeAt(0))
    }
    return list
}

fun deWeather_fun(context: Context, file_name: String): city_weather {
    var wData = context.assets.open(file_name).bufferedReader().use { it.readText() }
    var reg_c = """<current_weather(?: type="(.*?)")?>\s*<city>(.*?)</city>\s*<latitude>(.*?)</latitude>\s*<longitude>(.*?)</longitude>\s*</current_weather>""".toRegex()
    var reg_h = """<hour>\s*<time>(.*?)</time>\s*<weather_condition>(.*?)</weather_condition>\s*<temperature>(.*?)</temperature>\s*</hour>""".toRegex()
    var reg_t = """<day>\s*<date>(.*?)</date>\s*<weather_condition>(.*?)</weather_condition>\s*<high_temperature>(.*?)</high_temperature>\s*<low_temperature>(.*?)</low_temperature>\s*</day>""".toRegex()
    var reg_a = """<air_quality_index>\s*<current_aqi>(.*?)</current_aqi>\s*</air_quality_index>""".toRegex()

    val data = reg_c.findAll(wData)

    val list = city_weather(
        data.map { i -> i.groupValues[1] }.toList()[0],
        data.map { i -> i.groupValues[2] }.toList()[0],
        data.map { i -> i.groupValues[3] }.toList()[0],
        data.map { i -> i.groupValues[4] }.toList()[0],
        reg_h.findAll(wData).map { i ->
            var time = i.groupValues[1]
            var weather_condition = i.groupValues[2]
            var temperature = i.groupValues[3]
            hour(time,weather_condition,temperature)
        }.toList(),
        reg_t.findAll(wData).map { i ->
            var date = i.groupValues[1]
            var weather_condition = i.groupValues[2]
            var high_temperature = i.groupValues[3]
            var low_temperature = i.groupValues[4]
            day(date,weather_condition,high_temperature,low_temperature)
        }.toList(),)

    return list
}


data class city_weather(
    var type : String? = "",
    var city : String? = "",
    var latitude : String = "",
    var longitude : String = "",
    var hourly_forecast : List<hour>,
    var ten_day_forecast : List<day>
)

data class hour(
    var time : String? = "",
    var weather_condition : String? = "",
    var temperature : String? = ""
)

data class day(
    var date : String? = "",
    var weather_condition : String? = "",
    var high_temperature : String? = "",
    var low_temperature : String? = "",
)

data class air_quality_index(
    var current_aqi : String? = ""
)


data class cls(
    var type: String? = "",
    var name: String? = "",
    var name_en: String? = "",
    var file_name: String,
)