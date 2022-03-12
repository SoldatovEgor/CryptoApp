package ru.soldatov.android.cryptoapp.domain

import javax.inject.Inject

class GetCoinInfoListUseCase @Inject constructor(
    private val repository: CoinInfoRepository
) {

    operator fun invoke() = repository.getCoinInfoList()
}