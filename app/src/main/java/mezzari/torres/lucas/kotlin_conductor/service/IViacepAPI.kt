package mezzari.torres.lucas.kotlin_conductor.service

import mezzari.torres.lucas.kotlin_conductor.model.Address
import mezzari.torres.lucas.network.annotation.Route
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
@Route("https://viacep.com.br/ws/")
interface IViacepAPI {
    @GET("{cep}/json")
    fun getAddress(
        @Path("cep") cep: String
    ): Call<Address>
}