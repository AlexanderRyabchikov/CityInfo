package cityinfo.io.core.cache

import io.github.reactivecircus.cache4k.Cache
import kotlin.time.Duration

/**
 * Строитель кэш-инстанса на основе библиотеки Cache4k.
 *
 * Класс предоставляет удобный интерфейс для конфигурации и создания экземпляра кэша с
 * возможностью установки срока жизни по времени записи, последнему доступу и максимального размера кэша.
 *
 * Используется для создания [cityinfo.io.core.cache.CacheInstance] с заданными параметрами.
 *
 * @param KEY Тип ключа кэша. Должен быть не-nullable.
 * @param VALUE Тип значения, хранимого в кэше. Должен быть не-nullable.
 *
 * ### Пример использования:
 * ```
 * val cache = CacheBuilder<String, MyData>()
 *     .apply {
 *         expireAfterWrite(10.minutes)
 *         maximumCacheSize(100)
 *     }
 *     .build()
 * ```
 *
 * @see Cache
 * @see cityinfo.io.core.cache.CacheInstance
 */
class CacheBuilder<KEY : Any, VALUE : Any> internal constructor() {

    private val builder = Cache.Builder<KEY, VALUE>()
    private var isSystem: Boolean = false

    /**
     * Устанавливает время жизни записи в кэше после её записи.
     *
     * @param duration Период времени, по истечении которого запись будет удалена из кэша.
     */
    fun expireAfterWrite(duration: Duration) {
        builder.expireAfterWrite(duration)
    }

    /**
     * Устанавливает время жизни записи в кэше после последнего доступа.
     *
     * @param duration Период времени, по истечении которого неиспользуемая запись будет удалена.
     */
    fun expireAfterAccess(duration: Duration) {
        builder.expireAfterAccess(duration)
    }

    /**
     * Устанавливает максимальное количество записей, которые могут храниться в кэше.
     * Если лимит превышен, наиболее "старые" записи будут удалены.
     *
     * @param size Максимальный размер кэша.
     */
    fun maximumCacheSize(size: Long) {
        builder.maximumCacheSize(size)
    }

    /**
     * Устанавливает флаг о том, что кеш становится системным
     */
    fun setSystem() {
        isSystem = true
    }

    /**
     * Создаёт и возвращает экземпляр [cityinfo.io.core.cache.CacheInstance] с заданной конфигурацией.
     *
     * @return [cityinfo.io.core.cache.CacheInstance] с параметрами, заданными через текущий [CacheBuilder].
     */
    fun build(name: String, description: String): CacheInstance<KEY, VALUE> {
        return CacheInstance(
            name = name,
            description = description,
            isSystem = isSystem,
            cache = builder.build(),
        )
    }
}
