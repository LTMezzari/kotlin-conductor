package mezzari.torres.lucas.kotlin_conductor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_address.view.*
import kotlinx.android.synthetic.main.row_empty.view.*
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.model.Address

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
class AddressAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val inflater: LayoutInflater
    val addresses: ArrayList<Address>
    var onClickListener: ((Address) -> Unit)? = null

    constructor(context: Context) {
        this.inflater = LayoutInflater.from(context)
        this.addresses = arrayListOf()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        when (p1) {
            ADDRESS_VIEW_TYPE -> {
                return AddressViewHolder(inflater.inflate(R.layout.row_address, p0, false))
            }

            else -> {
                return EmptyViewHolder(inflater.inflate(R.layout.row_empty, p0, false))
            }
        }
    }

    override fun getItemCount(): Int {
        if (addresses.isEmpty()) {
            return 1
        } else {
            return addresses.size
        }
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val position: Int = p0.adapterPosition

        when (getItemViewType(position)) {
            ADDRESS_VIEW_TYPE -> {
                val viewHolder: AddressViewHolder = p0 as AddressViewHolder
                val address: Address = addresses[position]
                viewHolder.itemView.tvCep.text = address.cep
                viewHolder.itemView.tvStreet.text = address.street

                viewHolder.itemView.setOnClickListener {
                    onClickListener?.invoke(address)
                }
            }

            EMPTY_VIEW_TYPE -> {
                (p0 as EmptyViewHolder).itemView.tvEmptyMessage.text = "There are no addresses"
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (addresses.isEmpty()) {
            return EMPTY_VIEW_TYPE
        } else {
            return ADDRESS_VIEW_TYPE
        }
    }

    fun put(address: Address?) {
        address?.run {
            val position: Int = addresses.indexOfFirst { it.cep == cep }
            if (position < 0) {
                add(this)
            } else {
                update(address, position)
            }
        }
    }

    private fun add(address: Address) {
        addresses.add(address)
        if (addresses.isEmpty()) {
            notifyDataSetChanged()
        } else{
            notifyItemInserted(addresses.size -1)
        }
    }

    private fun update(address: Address, position: Int) {
        addresses[position] = address
        notifyItemChanged(position)
    }

    class AddressViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class EmptyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    companion object {
        private const val ADDRESS_VIEW_TYPE: Int = 0

        private const val EMPTY_VIEW_TYPE: Int = 1
    }
}