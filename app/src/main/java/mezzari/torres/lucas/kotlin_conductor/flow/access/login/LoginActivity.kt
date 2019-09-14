package mezzari.torres.lucas.kotlin_conductor.flow.access.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import mezzari.torres.lucas.conductor.source.Conductor
import mezzari.torres.lucas.conductor.source.generic.implementation.BaseActivity
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.kotlin_conductor.R
import mezzari.torres.lucas.kotlin_conductor.archive.observe
import mezzari.torres.lucas.kotlin_conductor.flow.access.AccessConductor
import mezzari.torres.lucas.kotlin_conductor.model.User

class LoginActivity : BaseActivity() {
    override val conductor: Conductor = ConductorProvider[AccessConductor::class]

    private lateinit var _user: User

    var user: User get() {
        return _user
    } set(value) {
        _user = value
        etUsername.setText(value.username)
        etPassword.setText(value.password)
        cbRememberUser.isChecked = value.shouldRememberPassword
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername.observe {
            btnLogin.isEnabled = !etPassword.isNullOrEmpty && !it.isNullOrEmpty()
        }

        etPassword.observe {
            btnLogin.isEnabled = !etUsername.isNullOrEmpty && !it.isNullOrEmpty()
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val shouldRememberPassword = cbRememberUser.isChecked
            _user = User(username, password, shouldRememberPassword)
            next()
        }

        btnCreate.setOnClickListener {
            next(AccessConductor.AccessPath.CREATE_ACCOUNT)
        }
    }

    private val EditText?.isNullOrEmpty: Boolean get() {
        return this?.text.isNullOrEmpty()
    }
}
