package com.example.recyclerview

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.adapter.UsersAdapter
import com.example.recyclerview.databinding.ActivityUsersBinding
import java.util.UUID

class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding
    private lateinit var adapter: UsersAdapter
    private var usersList = mutableListOf<Users>()
    private var recyclerViewPosition = 0

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val action = data?.getBooleanExtra("action_key", false)
            val user: Users? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra("return_key", Users::class.java)

            } else {
                data?.getParcelableExtra("return_key")
            }
            if (action == true) {
                user?.let {
                    updateUser(user)
                    adapter.updateUser(recyclerViewPosition, it)
                }
            }else {
                user?.let {
                    addUser(user)
                    adapter.setData(user)
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
        onClick()

    }

    private fun setUp() {
        val usersAdapter = binding.rcUsers
        adapter = UsersAdapter(
            update = {user, position ->
                recyclerViewPosition = position
                val intent = Intent(this, UserActivity::class.java).apply {
                    putExtra("return_key", user)
                    putExtra("update", true)
                }
                startForResult.launch(intent)
            }
        )
        usersAdapter.adapter = adapter
        usersAdapter.layoutManager = LinearLayoutManager(this)
    }

    private fun onClick() {
        binding.btnAddUser.setOnClickListener {
            startForResult.launch(clicked(UserActivity()))
        }
    }

    private fun clicked(targetActivity: Activity): Intent {
        return Intent(this, targetActivity::class.java)
    }

    private fun addUser(user: Users) {
        usersList.add(user)
    }

    private fun updateUser(users: Users) {
            usersList.remove(users)
            usersList.add(users)
    }
}