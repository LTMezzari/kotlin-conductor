package mezzari.torres.lucas.kotlin_conductor.model

import com.google.gson.annotations.SerializedName

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class Address {
    @SerializedName("cep")
    var cep: String? = ""

    @SerializedName("logradouro")
    var street: String? = ""

    @SerializedName("complemento")
    var complement: String? = ""

    @SerializedName("bairro")
    var neighborhood: String? = ""

    @SerializedName("localidade")
    var stateDescription: String? = ""

    @SerializedName("uf")
    var state: String? = ""

    @SerializedName("unidade")
    var unity: String? = ""

    @SerializedName("ibge")
    var ibge: String? = ""

    @SerializedName("gia")
    var gia: String? = ""
}