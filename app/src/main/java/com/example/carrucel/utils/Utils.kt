package com.example.carrucel.utils

import com.example.carrucel.core.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

fun <T> safeApiFlow(call: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    try {
        emit(Resource.Success(call()))
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        emit(Resource.Error("Sin conexión. Revisa tu internet."))
    } catch (e: HttpException) {
        emit(Resource.Error("Error del servidor (${e.code()})."))
    } catch (e: Exception) {
        emit(Resource.Error("Ocurrió un error inesperado."))
    }
}