package ru.soldatov.android.cryptoapp.domain

class GetCoinInfoUseCase(
    private val repository: CoinInfoRepository
) {

    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}