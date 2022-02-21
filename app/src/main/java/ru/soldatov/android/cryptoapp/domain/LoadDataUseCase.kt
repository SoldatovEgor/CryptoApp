package ru.soldatov.android.cryptoapp.domain

class LoadDataUseCase(
    private val repository: CoinInfoRepository
) {

    suspend operator fun invoke() = repository.loadData()
}