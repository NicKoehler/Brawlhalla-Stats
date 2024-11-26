import com.nickoehler.brawlhalla.core.domain.util.NetworkError

sealed interface LegendsEvent {
    data class Error(val error: NetworkError) : LegendsEvent
}