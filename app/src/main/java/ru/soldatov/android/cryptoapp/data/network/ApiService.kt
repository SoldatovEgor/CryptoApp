package ru.soldatov.android.cryptoapp.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.soldatov.android.cryptoapp.data.models.CoinInfoListOfData
import ru.soldatov.android.cryptoapp.data.models.CoinPriceInfoRawData

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_API_KEY) apyKey: String = "",
        @Query(QUERY_LIMIT) limit: Int = LIMIT,
        @Query(QUERY_TO_SYMBOL) tSym: String = CURRENCY_SYMBOL
    ) : Single<CoinInfoListOfData>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_API_KEY) apyKey: String = "",
        @Query(QUERY_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_TO_SYMBOLS) tSyms: String = CURRENCY_SYMBOL
    ): Single<CoinPriceInfoRawData>

    companion object {

        private const val QUERY_API_KEY = "api_key"
        private const val QUERY_LIMIT = "limit"
        private const val QUERY_TO_SYMBOL = "tsym"
        private const val QUERY_FROM_SYMBOLS = "fsyms"
        private const val QUERY_TO_SYMBOLS = "tsyms"

        private const val CURRENCY_SYMBOL = "USD"

        private const val LIMIT = 10
        private const val API_KEY =
            "92a635085ff9e9680d9814b857bf0e3269bf3e773b92735084d9d91cef56e4b8"
    }

}