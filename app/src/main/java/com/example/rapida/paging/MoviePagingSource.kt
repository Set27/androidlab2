package com.example.rapida.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rapida.api.ApiRetrofit
import com.example.rapida.helper.Constants
import com.example.rapida.models.Movie
import retrofit2.HttpException
import java.io.IOException


// Объявление константы для начальной страницы
private const val MOVIES_STARTING_PAGE_INDEX = 1

// Объявление класса MoviePagingSource, который наследуется от PagingSource
class MoviePagingSource(): PagingSource<Int, Movie>() {
    // Метод для получения ключа обновления
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Возвращает ключ обновления на основе текущей позиции
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Метод для загрузки данных
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        // Попытка загрузить данные
        return try {
            // Получение текущей страницы
            val currentPageList = params.key ?: MOVIES_STARTING_PAGE_INDEX
            // Выполнение запроса к API
            val response = ApiRetrofit.movieService.getNowPlayingMovies(
                Constants.API_KEY,
                currentPageList
            )
            // Создание списка для хранения ответа
            val responseList = mutableListOf<Movie>()

            // Если ответ успешный, добавление данных в список
            if(response.isSuccessful){
                val data = response.body()?.movies ?: emptyList()
                responseList.addAll(data)
            }

            // Возвращение результата загрузки
            LoadResult.Page(
                data = responseList,
                prevKey = if (currentPageList == MOVIES_STARTING_PAGE_INDEX) null else currentPageList - 1,
                nextKey = currentPageList.plus(1)
            )
            // Обработка исключений
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}