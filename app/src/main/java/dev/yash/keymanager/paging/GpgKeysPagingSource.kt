package dev.yash.keymanager.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.yash.keymanager.api.GithubRepository
import dev.yash.keymanager.models.GpgKey

class GpgKeysPagingSource(private val repository: GithubRepository) : PagingSource<Int, GpgKey>() {

    override fun getRefreshKey(state: PagingState<Int, GpgKey>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GpgKey> {
        return try {
            val page = params.key ?: 1
            val articles = repository.getGpgKeys(page = page, perPage = params.loadSize)

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isNotEmpty()) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
