package gustavo.projects.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleImageView = findViewById<ImageView>(R.id.titleImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val overviewTextView = findViewById<TextView>(R.id.overviewTextView)
        val releaseDateTextView = findViewById<TextView>(R.id.releaseDateTextView)
        val genresTextView = findViewById<TextView>(R.id.genresTextView)
        val runtimeTextView = findViewById<TextView>(R.id.runtimeTextView)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        val movieDbService: MovieDbService = retrofit.create(MovieDbService::class.java)

        movieDbService.getMovieById(155).enqueue(object : Callback<GetMovieByIdResponse>{
            override fun onResponse(call: Call<GetMovieByIdResponse>, response: Response<GetMovieByIdResponse>) {

                if(!response.isSuccessful){
                    Toast.makeText(this@MainActivity, "Unsuccessful network call!", Toast.LENGTH_SHORT)
                    return
                }

                val body = response.body()!!

                titleTextView.text = body.original_title
                overviewTextView.text = body.overview
                releaseDateTextView.text = body.release_date

                val listOfGenres = body.genres

                var listOfGenresText: String = ""

                for((index, value) in listOfGenres!!.withIndex()){
                    if(index < listOfGenres.lastIndex){
                        listOfGenresText += value!!.name + ", "
                    }else{
                        listOfGenresText += value!!.name + "."
                    }
                }
                genresTextView.text = listOfGenresText

                var runtimeHours: Int = body.runtime!!/60
                var runtimeMinutes = body.runtime%60
                var runtimeText: String = runtimeHours.toString() + "h "+ runtimeMinutes + "min"

                runtimeTextView.text = runtimeText

                Picasso.get().load("https://image.tmdb.org/t/p/w500"+body.poster_path).into(titleImageView)

            }

            override fun onFailure(call: Call<GetMovieByIdResponse>, t: Throwable) {
                Log.i("print", t.message?: "Null message")
            }
        })
    }
}