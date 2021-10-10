package com.example.myapplicationcats.data.mappers

import com.example.myapplicationcats.domain.models.CatModel
import com.example.myapplicationcats.data.responses.CatResponse

class CatMapper {

    operator fun invoke(catResponse: CatResponse): CatModel =
        with(catResponse) {
            CatModel(url = url ?: "")
        }
}