package ru.soldatov.android.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import ru.soldatov.android.cryptoapp.domain.GetCoinInfoListUseCase
import ru.soldatov.android.cryptoapp.domain.GetCoinInfoUseCase
import ru.soldatov.android.cryptoapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fromSymbol = fSym)

    init {
        loadDataUseCase()
    }
}