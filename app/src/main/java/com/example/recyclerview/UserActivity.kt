package com.example.recyclerview

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.recyclerview.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var users: Users
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClick()

    }

    private fun addUser() {
        val returnIntent = intent.apply {
            putExtra("return_key", users)
            putExtra("action_key", false)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun updateUser() {
        val returnIntent = intent.apply {
            putExtra("return_key", users)
            putExtra("action_key", true)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


    private fun onClick() {
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("return_key", Users::class.java)
        } else {
            intent.getParcelableExtra("return_key")
        }
        binding.apply {

        if (!user?.firstName.isNullOrEmpty() || !user?.lastName.isNullOrEmpty() || !user?.email.isNullOrEmpty()) {
            edFirstName.setText(user?.firstName)
            edLastName.setText(user?.lastName)
            edEmail.setText(user?.email)
            btnSave.text = getString(R.string.update)
        }


            btnSave.setOnClickListener {
                if (!edFirstName.text.isNullOrEmpty() && !edLastName.text.isNullOrEmpty() && !edEmail.text.isNullOrEmpty()) {

                    users = Users(
                        firstName = edFirstName.text.toString(),
                        lastName = edLastName.text.toString(),
                        email = edEmail.text.toString()
                    )

                    if (user?.firstName.isNullOrEmpty() || user?.lastName.isNullOrEmpty() || user?.email.isNullOrEmpty()) {
                        addUser()
                    } else {
                        if (user?.firstName == edFirstName.text.toString() &&
                            user?.lastName == edLastName.text.toString() &&
                            user?.email == edEmail.text.toString()
                        ) {
                            Toast.makeText(this@UserActivity, "No change", Toast.LENGTH_LONG).show()
                        } else {
                            updateUser()
                        }
                    }
                    Toast.makeText(this@UserActivity, "Success", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@UserActivity, "Please fill inputs", Toast.LENGTH_LONG)
                        .show()
                }
            }

            btnCancel.setOnClickListener {
                finish()
            }
        }
    }

    private fun clicked(targetActivity: Activity) {
        startActivity(Intent(this, targetActivity::class.java))
    }

}