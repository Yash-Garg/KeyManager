package dev.yash.keymanager.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.yash.keymanager.data.models.Key

class GithubPagingSource
@AssistedInject
constructor(@Assisted private val source: suspend (Int, Int) -> List<Key>) :
    PagingSource<Int, Key>() {

    override fun getRefreshKey(state: PagingState<Int, Key>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Key> {
        return try {
            val page = params.key ?: 1
            val keys = source(page, params.loadSize)

            LoadResult.Page(
                data = keys,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (keys.isNotEmpty() && keys.size >= params.loadSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(source: suspend (Int, Int) -> List<Key>): GithubPagingSource
    }
}
