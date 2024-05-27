package com.example.telinhas.domain.mapper

interface IMapper<E, T> {
    fun transform(data: E): T
}