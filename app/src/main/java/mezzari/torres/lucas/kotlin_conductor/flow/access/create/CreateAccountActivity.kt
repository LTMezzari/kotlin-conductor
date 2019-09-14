package mezzari.torres.lucas.kotlin_conductor.flow.access.create

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
import mezzari.torres.lucas.kotlin_conductor.archive.observe
import mezzari.torres.lucas.kotlin_conductor.flow.access.AccessConductor
import mezzari.torres.lucas.kotlin_conductor.model.User

class CreateAccountActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[AccessConductor::class]

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

    private val EditText?.isNullOrEmpty: Boolean get() {
        return this?.text.isNullOrEmpty()
    }
}
