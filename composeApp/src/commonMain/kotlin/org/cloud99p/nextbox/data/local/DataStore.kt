package org.cloud99p.nextbox.data.local

import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal const val DATASTORE_FILENAME = "prefs.preferences_pb"

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

@Stable
data class DataStoreProperty<T : Any, S : Any>(
    private val key: Preferences.Key<S>,
    private val defaultValue: T,
    private val dataStore: DataStore<Preferences>,
    private val read: ((S) -> T)? = null,
    private val write: ((T) -> S)? = null
) : ReadWriteProperty<Any, T> {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _stateFlow = MutableStateFlow(defaultValue)
    val stateFlow = _stateFlow.asStateFlow()

    init {
        @Suppress("UNCHECKED_CAST")
        scope.launch {
            dataStore.data
                .map { prefs ->
                    prefs[key]?.let { v ->
                        runCatching { read?.invoke(v) ?: v as T }
                            .getOrDefault(defaultValue)
                    } ?: defaultValue
                }
                .collectLatest { v ->
                    _stateFlow.update { v }
                }
        }
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): T = stateFlow.value

    @Suppress("UNCHECKED_CAST")
    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: T
    ) {
        _stateFlow.update { value }
        scope.launch {
            dataStore.edit { prefs ->
                prefs[key] = write?.invoke(value) ?: value as S
            }
        }
    }
}

open class DataStoreHolder : KoinComponent {
    val dataStore by inject<DataStore<Preferences>>()

    fun boolean(
        name: String,
        defaultValue: Boolean
    ) = DataStoreProperty(
        key = booleanPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    fun int(
        name: String,
        defaultValue: Int
    ) = DataStoreProperty(
        key = intPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    fun float(
        name: String,
        defaultValue: Float
    ) = DataStoreProperty(
        key = floatPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    fun long(
        name: String,
        defaultValue: Long
    ) = DataStoreProperty(
        key = longPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    fun string(
        name: String,
        defaultValue: String
    ) = DataStoreProperty(
        key = stringPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    fun stringSet(
        name: String,
        defaultValue: Set<String>
    ) = DataStoreProperty(
        key = stringSetPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore
    )

    inline fun <reified T : Enum<T>> enum(
        name: String,
        defaultValue: T
    ) = DataStoreProperty(
        key = stringPreferencesKey(name),
        defaultValue = defaultValue,
        dataStore = dataStore,
        read = { s -> enumValueOf<T>(s) },
        write = { v -> v.name }
    )
}
