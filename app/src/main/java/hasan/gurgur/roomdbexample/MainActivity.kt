package hasan.gurgur.roomdbexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import hasan.gurgur.roomdbexample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appDb: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appDb = AppDatabase.getDatabase(this)

        watchEdittext()
        binding.btnSave.setOnClickListener {


            saveData()
        }
        binding.btnSearch.setOnClickListener {
            searchData()
        }

    }

    private fun saveData() {

        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val tckNo = binding.etTckNo.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && tckNo.isNotEmpty()) {


            val user = User(
                null, firstName, lastName, tckNo.toLong()
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDb.userDao().insert(user)
            }
            binding.etFirstName.text.clear()
            binding.etLastName.text.clear()
            binding.etTckNo.text.clear()

            Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()

        }

    }

    private suspend fun displayData(user: User) {

        withContext(Dispatchers.Main) {
            binding.tvSonucFirstname.text = user.fistName
            binding.tvSonucLastname.text = user.lastName
            binding.tvSonucTckn.text = user.tcknNo.toString()
        }

    }

    private fun searchData() {

        val tcknNo = binding.etSearchTckNo.text.toString()
        if (tcknNo.isNotEmpty()) {
            lateinit var user: User
            GlobalScope.launch {
                user = appDb.userDao().findByRoll(tcknNo.toInt())
                displayData(user = user)
            }
        }
    }

    private fun checkEtx(){
        if (binding.etFirstName.text.isNotEmpty() && binding.etLastName.text.isNotEmpty() && binding.etTckNo.text.isNotEmpty()) {
            binding.btnSave.alpha = 1f
            binding.btnSave.isEnabled = true
        }else{
            binding.btnSave.alpha = 0.5f
            binding.btnSave.isEnabled = false
        }
    }

    private fun watchEdittext(){
        binding.etFirstName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEtx()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.etLastName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEtx()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.etTckNo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkEtx()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }


}