package com.example.soapexample

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.soapexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            val input1 = binding.enterInput1.text.toString().trim()
            val input2 = binding.enterInput2.text.toString().trim()
            when {
                input1.length == 0 || input2.length == 0 -> Toast.makeText(this, getString(R.string.fill_field), Toast.LENGTH_SHORT).show()

                !Utils.isConnected(this@MainActivity) -> Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()

                else -> getCitiesOfCountry().execute(input1,input2)
            }
        }
    }

    inner class getCitiesOfCountry : AsyncTask<String, String, String>() {

        override fun doInBackground(vararg params: String?): String {
            val response = CallWebService().callApi(Utils.METHOD_ADD, params[0], params[1])
            Log.v("response", "response==" + response)
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.v("response", "OnPostresponse==" + result)
            try {
                binding.resultValue.text = getString(R.string.result)+" "+result
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}