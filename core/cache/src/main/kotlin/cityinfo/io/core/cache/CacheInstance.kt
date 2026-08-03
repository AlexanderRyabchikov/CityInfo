@file:OptIn(ExperimentalUuidApi::class)

package cityinfo.io.core.cache

import io.github.reactivecircus.cache4k.Cache
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Обёртка над [Cache] из библиотеки Cache4k, предоставляющая удобный интерфейс для управления кэшем.
 *
 * Каждый экземпляр автоматически регистрируется в глобальном [cityinfo.io.core.cache.CacheManager] с уникальным идентификатором.
 * Дополнительно может быть отключён/включён динамически через [setEnabled].
 *
 * @param KEY Тип ключа кэша. Должен быть непустым (`non-nullable`).
 * @param VALUE Тип значения, хранимого в кэше. Должен быть непустым.
 * @param name Человеко-читаемое имя кэша, используемое, например, для отображения в интерфейсах или логах.
 * @param isSystem Параметр означающий что данный инстанс не может быть отключен, так как он системный.
 * @param cache Внутренний объект [Cache], созданный через [CacheBuilder].
 */
class CacheInstance<KEY : Any, VALUE : Any> internal constructor(
    val name: String,
    val description: String,
    val isSystem: Boolean,
    private val cache: Cache<KEY, VALUE>,
) {

    /**
     * Флаг, указывающий, активен ли кэш. Если `false`, операции чтения возвращают `null` или напрямую вызывают загрузчик,
     * а операции записи игнорируются.
     */
    var isEnabled: Boolean = true
        private set

    val id: Uuid = Uuid.random()

    init {
        // Регистрируем кэш-инстанс в глобальном менеджере по UUID
        CacheManager.instances[id] = this
    }

    /**
     * Включает или отключает кэш.
     *
     * @param isEnabled Если `false`, кэш будет временно отключён: чтения не возвращают данные, а записи игнорируются.
     */
    fun setEnabled(isEnabled: Boolean) {
        if (!isSystem) {
            this.isEnabled = isEnabled
        }
    }

    /**
     * Получает значение по ключу, если оно присутствует в кэше и кэш активен.
     *
     * @param key Ключ для поиска.
     * @return Значение, соответствующее ключу, либо `null`, если отсутствует или кэш отключён.
     */
    fun get(key: KEY): VALUE? {
        return if (isEnabled) {
            cache.get(key)
        } else {
            null
        }
    }

    /**
     * Получает значение по ключу. Если оно отсутствует и кэш активен — загружает его через [loader], кэширует и возвращает.
     * Если кэш отключён — просто вызывает [loader] без кэширования.
     *
     * @param key Ключ для поиска.
     * @param loader Функция загрузки значения, если оно не найдено в кэше.
     * @return Загруженное или закэшированное значение.
     */
    suspend fun get(key: KEY, loader: suspend () -> VALUE): VALUE {
        return if (isEnabled) {
            cache.get(key = key, loader = loader)
        } else {
            loader()
        }
    }

    /**
     * Добавляет или обновляет значение в кэше по заданному ключу.
     * Если кэш отключён — операция игнорируется.
     *
     * @param key Ключ, по которому будет сохранено значение.
     * @param value Значение для сохранения.
     */
    fun put(key: KEY, value: VALUE) {
        if (isEnabled) {
            cache.put(key = key, value = value)
        }
    }

    /**
     * Удаляет значение из кэша по заданному ключу.
     * Работает независимо от флага активности [isEnabled].
     *
     * @param key Ключ, который необходимо удалить из кэша.
     */
    fun invalidate(key: KEY) {
        cache.invalidate(key)
    }

    /**
     * Полностью очищает кэш.
     * Работает независимо от флага активности [isEnabled].
     */
    fun invalidateAll() {
        cache.invalidateAll()
    }
}
