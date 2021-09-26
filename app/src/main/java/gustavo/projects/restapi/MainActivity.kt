package gustavo.projects.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        val movieDbService: MovieDbService = retrofit.create(MovieDbService::class.java)

        movieDbService.getMovieById(156).enqueue(object : Callback<GetMovieByIdResponse>{
            override fun onResponse(call: Call<GetMovieByIdResponse>, response: Response<GetMovieByIdResponse>) {

                if(!response.isSuccessful){
                    Toast.makeText(this@MainActivity, "Unsuccessful network call!", Toast.LENGTH_SHORT)
                    return
                }

                val body = response.body()!!
                val title = body.original_title

                textView.text = title

            }

            override fun onFailure(call: Call<GetMovieByIdResponse>, t: Throwable) {
                Log.i("print", t.message?: "Null message")
            }
        })
    }
}