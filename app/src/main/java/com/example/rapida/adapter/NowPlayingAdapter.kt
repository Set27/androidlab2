package com.example.rapida.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rapida.MovieDetailActivity
import com.example.rapida.R
import com.example.rapida.databinding.TvShowLayoutAdapterBinding
import com.example.rapida.helper.Constants
import com.example.rapida.models.Movie



class NowPlayingAdapter : PagingDataAdapter<Movie, NowPlayingAdapter.ViewHolder>(DiffUtilCallBack()) {

    // Метод для создания ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Инфляция макета элемента списка
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.tv_show_layout_adapter, parent,false)
        // Возвращение ViewHolder
        return ViewHolder(view)
    }

    // Метод для привязки данных к ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Получение элемента по позиции
        val item = getItem(position)
        // Если элемент не null, привязка данных
        if (item != null) {
            holder.bind(item)
        }

        // Установка слушателя кликов на элемент списка
        holder.itemView.setOnClickListener {
            // Создание интента для перехода на экран деталей фильма
            val intentDetail = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            // Добавление данных фильма в интент
            intentDetail.putExtra("movie", item)
            // Запуск активности
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    // Объявление ViewHolder
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // Привязка макета элемента списка к ViewHolder
        private val binding = TvShowLayoutAdapterBinding.bind(view)

        // Метод для привязки данных фильма к элементу списка
        fun bind(movie: Movie) {
            // Загрузка изображения постера фильма с помощью Glide
            Glide.with(itemView.context).load(Constants.urlBaseImage + movie.posterPath).centerCrop().into(binding.imageView)
        }
    }

    // Объявление DiffUtilCallBack для оптимизации обновления списка
    class DiffUtilCallBack: DiffUtil.ItemCallback<Movie>() {
        // Метод для проверки, являются ли два объекта Movie одинаковыми
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        // Метод для проверки, содержат ли два объекта Movie одинаковые данные
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.id == newItem.id
        }
    }
}





