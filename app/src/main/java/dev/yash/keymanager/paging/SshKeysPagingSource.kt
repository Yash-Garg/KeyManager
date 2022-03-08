package dev.yash.keymanager.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.yash.keymanager.api.GithubRepository
import dev.yash.keymanager.models.SshKey

class SshKeysPagingSource(private val repository: GithubRepository) : PagingSource<Int, SshKey>() {

    override fun getRefreshKey(state: PagingState<Int, SshKey>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SshKey> {
        return try {
            val page = params.key ?: 1
            val articles = repository.getSshKeys(page = page, perPage = params.loadSize)

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
