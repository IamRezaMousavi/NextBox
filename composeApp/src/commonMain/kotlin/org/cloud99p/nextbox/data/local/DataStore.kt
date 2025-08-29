package org.cloud99p.nextbox.data.local

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.Path.Companion.toPath
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal const val DATASTORE_FILENAME = "prefs.preferences_pb"

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

@Stable
data class DataStoreProperty<T>(
    private val key: Preferences.Key<T>,
    private val defaultValue: T,
    private val dataStore: DataStore<Preferences>
) : ReadWriteProperty<Any, T> {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val state = mutableStateOf(defaultValue)
    val flow = MutableStateFlow(defaultValue)

    private fun setState(newValue: T) {
        state.value = newValue
        flow.update { newValue }
    }

    init {
        val initial = runBlocking {
            dataStore.data.map { it[key] ?: defaultValue }.first()
        }
        setState(initial)

        scope.launch {
            dataStore.data
                .map { it[key] ?: defaultValue }
                .distinctUntilChanged()
                .collect { setState(it) }
        }
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): T = state.value

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: T
    ) {
        scope.launch {
            dataStore.edit { it[key] = value }
        }
        setState(value)
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

    inline fun <reified E : Enum<E>> enum(
        name: String,
        defaultValue: E
    ) = DataStoreProperty(
        key = stringPreferencesKey(name),
        defaultValue = defaultValue.name,
        dataStore = dataStore
    )
        .let { prop ->
            object : ReadWriteProperty<Any, E> {
                override fun getValue(
                    thisRef: Any,
                    property: KProperty<*>
                ) = runCatching {
                    enumValueOf<E>(prop.getValue(thisRef, property))
                }.getOrDefault(defaultValue)

                override fun setValue(
                    thisRef: Any,
                    property: KProperty<*>,
                    value: E
                ) {
                    prop.setValue(thisRef, property, value.name)
                }
            }
        }
}
