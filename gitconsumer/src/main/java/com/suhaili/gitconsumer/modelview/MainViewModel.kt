package com.suhaili.gitconsumer.modelview

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhaili.gitconsumer.R
import com.suhaili.gitconsumer.model.FindModel
import com.suhaili.gitconsumer.model.GitModel
import com.suhaili.gitconsumer.model.LoginModel
import com.suhaili.gitconsumer.serviceapi.APIRetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainViewModel : ViewModel() {


        private val URL = "https://api.github.com/"


    private val livedata = MutableLiveData<ArrayList<GitModel>>()
    private val temp : ArrayList<GitModel> = arrayListOf()

    fun getLiveData(): LiveData<ArrayList<GitModel>> = livedata

    fun getAllData(app: Context) {
        val retro = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retro.create(APIRetro::class.java)
        val getAllData = api.getAllData()
        getAllData.enqueue(object : Callback<ArrayList<LoginModel>> {
            override fun onResponse(
                    call: Call<ArrayList<LoginModel>>,
                    response: Response<ArrayList<LoginModel>>
            ) {
                Log.d("Status", "API Connect Successfully")
                Toast.makeText(app, "API Connect Successfully", Toast.LENGTH_SHORT).show()
                try {
                    val result = response.body()
                    if (result != null) {
                        temp.clear()
                        for (i in 0 until result.size) {
                            val jsonbj = result.get(i).username
                            getUserLogin(jsonbj!!, app)
                        }
                    }
                    Log.d("Status", "Get All Data Succesfully Loaded!")
                    Toast.makeText(app, "Get All Data Succesfully Loaded!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.d("Status", "Get All Data Fail!")
                    Log.d("status", e.message.toString())
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<LoginModel>>, t: Throwable) {
                Log.d("Status", "API Connect Fail")
                Log.d("Status", t.message.toString())
                Toast.makeText(app, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun getUserLogin(login: String, app: Context) {
        val retro = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retro.create(APIRetro::class.java)
        val data = api.getUserData(login)
        data.enqueue(object : Callback<GitModel> {
            override fun onResponse(call: Call<GitModel>, response: Response<GitModel>) {
                Log.d("Status", "API Connect Successfully")
                try {
                    var stat = false
                    val result = response.body()
                    if (result == null) {
                        Toast.makeText(app, R.string.peringatan.toString(), Toast.LENGTH_SHORT).show()
                    } else {
                        val dataGit = GitModel(
                                result.name.toString(),
                                result.avatar.toString(),
                                result.company.toString(),
                                result.follower.toString(),
                                result.following.toString(),
                                result.location.toString(),
                                result.repo.toString(),
                                result.username.toString())
                        for (i in 0 until temp.size) {
                            if (temp.get(i).username == dataGit.username) {
                                stat = true
                                break
                            }
                        }
                        if (stat == true) {

                        } else {
                            temp.add(dataGit)
                            livedata.postValue(temp)
                        }
                    }

                    Log.d("status", "Successful Save Data")
                } catch (e: Exception) {
                    Log.d("Status", "Fail to Save Data")
                    Log.d("Status", e.toString())
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GitModel>, t: Throwable) {
                Log.d("Status", "API Connect Fail!")
                Log.d("Status", t.message.toString())
                Toast.makeText(app, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    fun findPeople(finding: String, app: Context) {
        val retro = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retro.create(APIRetro::class.java)
        val data = api.getFindPeople(finding)
        data.enqueue(object : Callback<FindModel> {
            override fun onResponse(
                    call: Call<FindModel>,
                    response: Response<FindModel>
            ) {
                try {
                    Log.d("Status", "API Connect Succesfully")

                    val result = response.body()
                    if (result != null) {
                        temp.clear()
                        for (i in 0 until result.items.size) {
                            val data = result.items.get(i).username
                            getUserLogin(data!!, app)
                        }
                    }
                } catch (e: Exception) {
                    Log.d("Status", "Data Fail Loaded!")
                    Log.d("Status", e.message.toString())
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FindModel>, t: Throwable) {
                Log.d("Status", "API Connect Failure")
                Toast.makeText(app, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}