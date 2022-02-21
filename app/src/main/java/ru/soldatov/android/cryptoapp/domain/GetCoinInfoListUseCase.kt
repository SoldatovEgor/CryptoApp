package ru.soldatov.android.cryptoapp.domain

class GetCoinInfoListUseCase(
    private val repository: CoinInfoRepository
) {

    operator fun invoke() = repository.getCoinInfoList()
}