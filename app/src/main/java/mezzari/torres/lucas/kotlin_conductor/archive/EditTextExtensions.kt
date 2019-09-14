package mezzari.torres.lucas.kotlin_conductor.archive

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * @author Lucas T. Mezzari
 * @since 14/09/2019
 */
fun EditText.observe(callback: (String?) -> Unit) {
    addTextChangedListener(object: TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            callback(p0?.toString())
        }
    })
}