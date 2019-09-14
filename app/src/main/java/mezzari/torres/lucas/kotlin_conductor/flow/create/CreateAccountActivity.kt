package mezzari.torres.lucas.kotlin_conductor.flow.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_login.etUsername
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.flow.AnnotatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.ModulatedMainConductor
import mezzari.torres.lucas.kotlin_conductor.flow.SimpleMainConductor
import mezzari.torres.lucas.kotlin_conductor.model.User

class CreateAccountActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[ModulatedMainConductor::class]

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etUsername.observe {
            btnCreate.isEnabled = !it.isNullOrEmpty()
                    && !etPassword.isNullOrEmpty
                    && etConfirmPassword.text.toString() == etPassword.text.toString()
        }

        etPassword.observe {
            btnCreate.isEnabled = !it.isNullOrEmpty()
                    && !etUsername.isNullOrEmpty
                    && etConfirmPassword.text.toString() == etPassword.text.toString()
        }

        etConfirmPassword.observe {
            btnCreate.isEnabled = !it.isNullOrEmpty()
                    && !etPassword.isNullOrEmpty
                    && etConfirmPassword.text.toString() == etPassword.text.toString()
        }

        btnCreate.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            this.user = User(
                username = username,
                password = password
            )
            next()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        previous()
    }

    private fun EditText.observe(callback: (String?) -> Unit) {
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

    private val EditText?.isNullOrEmpty: Boolean get() {
        return this?.text.isNullOrEmpty()
    }
}
