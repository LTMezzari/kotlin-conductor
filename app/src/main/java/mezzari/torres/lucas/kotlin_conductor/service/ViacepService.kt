package mezzari.torres.lucas.kotlin_conductor.service

import mezzari.torres.lucas.kotlin_conductor.model.Address
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.promise.NetworkPromise

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class ViacepService {
    //Build a api instance for retrofit calls
    private val api: IViacepAPI = Network.build()

    fun getAddress(cep: String): NetworkPromise<Address> {
        //Return a new promise that will call the getAddress service
        return NetworkPromise {
            api.getAddress(cep).enqueue(this)
        }
    }
}