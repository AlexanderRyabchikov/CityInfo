package cityinfo.io.core.cache

import kotlin.time.Duration

/**
 * Создаёт и конфигурирует экземпляр [cityinfo.io.core.cache.CacheInstance] с помощью лямбда-выражения,
 * предоставляя доступ к DSL-строителю [CacheBuilder].
 *
 * Является удобной точкой входа для декларативной конфигурации кэша.
 * Если не указано имя, будет использовано имя класса, из которого был вызван метод.
 *
 * ### Пример:
 * ```
 * val cache = cacheOf<String, Int> {
 *     expireAfterWrite(5.minutes)
 *     maximumCacheSize(100)
 * }
 * ```
 * или, если имя локализовано:
 * ```
 * val cache = cacheOf<String, Int>(localizedName = "CustomCache") {
 *     expireAfterWrite(10.minutes)
 *     maximumCacheSize(200)
 * }
 * ```
 *
 * @param builder Конфигурационный блок DSL, в котором настраивается [CacheBuilder].
 * @param name Локализованное имя кэша. Если не указано, будет использовано имя класса,
 *                      из которого был вызван метод.
 * @return Экземпляр [cityinfo.io.core.cache.CacheInstance] с заданными параметрами.
 */
@CacheMarker
fun <KEY : Any, VALUE : Any> cacheOf(
    name: String,
    description: String = "",
    builder: CacheBuilder<KEY, VALUE>.() -> Unit,
): CacheInstance<KEY, VALUE> {
    require(name.isNotEmpty()) { "Name is empty" }
    return CacheBuilder<KEY, VALUE>()
        .apply(builder)
        .build(name = name, description = description)
}

/**
 * Создаёт экземпляр [CacheInstance] с установленным временем истечения после записи.
 *
 * Удобен для быстрой инициализации кэша, когда не требуется детальная настройка.
 * Если не указано имя, будет использовано имя класса, из которого был вызван метод.
 *
 * ### Пример:
 * ```
 * val cache = cacheOf<String, Int>(expireAfterWrite = 10.minutes)
 * ```
 * или, если имя локализовано:
 * ```
 * val cache = cacheOf<String, Int>(expireAfterWrite = 10.minutes, localizedName = "CustomCache")
 * ```
 *
 * @param expireAfterWrite Время жизни записи после её добавления в кэш.
 * @param name Локализованное имя кэша. Если не указано, будет использовано имя класса,
 *                      из которого был вызван метод.
 * @return Экземпляр [CacheInstance] с заданным временем жизни.
 */
@CacheMarker
fun <KEY : Any, VALUE : Any> cacheOf(
    isSystem: Boolean = false,
    name: String,
    description: String = "",
    expireAfterWrite: Duration,
): CacheInstance<KEY, VALUE> {
    require(name.isNotEmpty()) { "Name is empty" }
    return CacheBuilder<KEY, VALUE>()
        .apply {
            expireAfterWrite(expireAfterWrite)
            if (isSystem) setSystem()
        }
        .build(name = name, description = description)
}
