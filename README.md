# CityInfo

Android-приложение: список городов с поиском и пагинацией, карточка города, карта с маркерами.

Проект собран как демонстрация архитектурного подхода — многомодульность с разделением `api` / `impl`, MVI, собственный слой пагинации и конфигурация сборки через convention-плагины.

## Возможности

- Список городов с постраничной подгрузкой и поиском по названию
- Карточка города: страна, население, координаты
- Карта с маркерами городов и кластеризацией, переход к карточке из маркера
- Pull-to-refresh, shimmer-загрузка, состояния пустого результата и ошибки с ретраем

## Стек

| Слой | Технологии |
|---|---|
| Язык | Kotlin 2.4, Coroutines, Flow, kotlinx.serialization |
| UI | Jetpack Compose (BOM 2026.06), Material 3, Navigation Compose |
| Состояние | Orbit MVI |
| DI | Koin 4 |
| Сеть | Ktor 3 (движок OkHttp), Content Negotiation, собственный маппинг ошибок |
| Карты | Yandex MapKit |
| Кэш | cache4k |
| Пагинация | androidx.paging 3 + собственная обёртка |
| Сборка | Gradle Kotlin DSL, version catalog, convention-плагины в `build-logic` |

minSdk 28, targetSdk 36, JVM 17.

## Структура

```
app/                     точка входа, корневая навигация, сборка DI-графа
build-logic/             convention-плагины Gradle
core/
  base/                  базовые Store / Handler / Screen
  cache/                 обёртка над cache4k и глобальный CacheManager
  map/api, map/widgets   абстракция карты и Compose-обёртка над MapKit
  mvi/                   MviViewModel и DSL поверх Orbit
  navigation/            общие элементы навигации
  network/               настройка Ktor-клиента, логирование, ошибки
  paging/                слой пагинации
  ui-kit/                дизайн-система: цвета, типографика, компоненты
  utils/
feature/
  city/api, city/impl
  map/api, map/impl
```

## Архитектурные решения

### Разделение api / impl

Каждая фича — два модуля. В `api` только контракты: модели, интерфейсы репозиториев и use case, точки входа в навигацию. В `impl` — реализация, экраны, DI-модуль.

Фичи видят друг друга **только через `api`**. Это даёт две вещи. Во-первых, фича не может случайно залезть во внутренности соседней. Во-вторых, правка реализации не заставляет Gradle пересобирать зависимые модули — меняется только `impl`, а контракт остаётся прежним.

### MVI: Store, Handler, Content

Экран разложен на три части:

- **Store** — состояние, обработка действий, side effects. Наследуется от `BaseAppStore` поверх Orbit.
- **Handler** — интерпретирует side effects: навигация, показ ошибок.
- **Screen / Content** — Compose-разметка, о бизнес-логике не знает.

Store не зависит от Compose, а Content не зависит от Store — за счёт этого разметку можно рендерить в превью с любым состоянием.

### Пагинация

Поверх `androidx.paging` написан делегат `pagingDataHandler`, спрятанный в `BasePagingStore`. Экранному Store остаётся описать только загрузку страницы:

```kotlin
class CitiesStore(
    interactor: CityListInteractor,
) : BasePagingStore<CitiesState, CitiesEffects, CitiesActions, City>(
    initialState = CitiesState(),
) {
    override suspend fun onLoadPage(page: Int?, state: CitiesState): PagedData<City> =
        loadCities(page = page ?: 1, query = state.query).data
}
```

Создание и инвалидацию `PagingSource`, счётчик элементов, разбор ошибок первой страницы и связывание потока с MVI-состоянием берёт на себя базовый класс. Поиск и pull-to-refresh — это `pagingData.reload()`, отдельной логики перезагрузки на экране нет.

### Маркеры карты из Compose

`core/map` разделён на `api` (интерфейс `MapManager`, модель `Marker`) и `widgets` — Compose-обёртку над Yandex MapKit.

MapKit ожидает маркеры в виде `ImageProvider`, то есть готовых bitmap. `ComposeImageProvider` отрисовывает произвольный composable в `Bitmap` и отдаёт его карте. За счёт этого маркеры и кластеры пишутся обычным Compose-кодом с темой и типографикой приложения, а не собираются из XML-drawable.

### Convention-плагины

Вместо копирования блоков `android {}` по четырнадцати модулям в `build-logic` лежат пять плагинов:

| Плагин | Назначение |
|---|---|
| `android-application-setup` | конфигурация приложения: SDK, Java 17, build types, подпись |
| `android-library-setup` | то же для библиотечных модулей |
| `compose-library-setup` | Compose BOM и базовый набор UI-зависимостей |
| `feature-setup-api` | минимальный набор для api-модуля |
| `feature-setup-ui` | api + Orbit + Koin для impl-модуля |

Подключение модуля выглядит так:

```kotlin
plugins {
    alias(libs.plugins.setup.feature.ui)
}
```

Версии зависимостей централизованы в `gradle/libs.versions.toml`, между модулями используются typesafe project accessors (`projects.core.uiKit`).

## Запуск

```bash
git clone https://github.com/AlexanderRyabchikov/CityInfo.git
cd CityInfo
./gradlew :app:assembleDebug
```

Перед сборкой нужно задать два параметра:

- **Ключ Yandex MapKit** — `feature/map/impl/.../interactors/MapManagerImpl.kt`
- **Базовый URL API** — `core/network/.../DiNetwork.kt`

Бэкенд в репозиторий не входит. Ожидаемый контракт:

```
GET /api/cities?page={int}&limit={int}&query={string}
```

Ответ — страница со списком городов: `id`, `name`, `country`, `latitude`, `longitude`, `population` и общее количество элементов.

## Что не сделано

Проект показывает архитектуру, а не готовый продукт: автотестов нет, бэкенд внешний.
