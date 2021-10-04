package gustavo.projects.restapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso



class MainActivity : AppCompatActivity() {

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleImageView = findViewById<ImageView>(R.id.titleImageView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val overviewTextView = findViewById<TextView>(R.id.overviewTextView)
        val releaseDateTextView = findViewById<TextView>(R.id.releaseDateTextView)
        val genresTextView = findViewById<TextView>(R.id.genresTextView)
        val runtimeTextView = findViewById<TextView>(R.id.runtimeTextView)



        viewModel.refreshMovie(155)
        viewModel.getMovieByIdLiveData.observe(this){ response ->

            if(response == null){
                Toast.makeText(this@MainActivity, "Unsuccessful network call!", Toast.LENGTH_SHORT)
                return@observe
            }

            titleTextView.text = response.original_title
            overviewTextView.text = response.overview
            releaseDateTextView.text = response.release_date

            val listOfGenres = response.genres

            var listOfGenresText: String = ""

            for((index, value) in listOfGenres!!.withIndex()){
                if(index < listOfGenres.lastIndex){
                    listOfGenresText += value!!.name + ", "
                }else{
                    listOfGenresText += value!!.name + "."
                }
            }
            genresTextView.text = listOfGenresText

            var runtimeHours: Int = response.runtime!!/60
            var runtimeMinutes = response.runtime%60
            var runtimeText: String = runtimeHours.toString() + "h "+ runtimeMinutes + "min"

            runtimeTextView.text = runtimeText

            Picasso.get().load("https://image.tmdb.org/t/p/w500"+ response.poster_path).into(titleImageView)
        }
    }
}