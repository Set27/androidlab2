package com.example.rapida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.rapida.databinding.ActivityMovieDetailBinding
import com.example.rapida.helper.Constants
import com.example.rapida.models.Movie



// Объявление класса MovieDetailActivity, который наследуется от AppCompatActivity
class MovieDetailActivity : AppCompatActivity() {

    // Объявление переменных для привязки данных и хранения информации о фильме
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie

    // Метод, вызываемый при создании активности
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализация привязки данных
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Получение данных о фильме из интента
        movie = intent.getSerializableExtra("movie") as Movie

        // Установка заголовка и изображения в CollapsingToolbarLayout
        binding.collapsingToolbarMaterial.title = movie.title
        Glide.with(this).load(Constants.urlbackpost + movie.backdropPath)
            .centerCrop().into(binding.backdropImv)
        // Установка описания и даты выпуска фильма
        binding.OverviewTv.text = movie.overview
        binding.RelaseDateTv.text = movie.releaseDate
    }

    // Метод для обработки нажатия на элементы меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Если была нажата кнопка "Назад", возвращаемся на предыдущий экран
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}