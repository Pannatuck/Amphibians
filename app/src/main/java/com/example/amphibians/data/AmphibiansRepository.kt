package com.example.amphibians.data

import com.example.amphibians.model.AmphibianCardItem
import com.example.amphibians.network.AmphibianApiService

interface AmphibiansRepository{
    suspend fun getAmphibians() : List<AmphibianCardItem>
}

class NetworkAmphibiansRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibiansRepository{
    override suspend fun getAmphibians(): List<AmphibianCardItem> = amphibianApiService.getAmphibians()
}