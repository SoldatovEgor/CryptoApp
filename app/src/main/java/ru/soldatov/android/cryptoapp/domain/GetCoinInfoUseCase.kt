package ru.soldatov.android.cryptoapp.domain

import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val repository: CoinInfoRepository
) {

    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}