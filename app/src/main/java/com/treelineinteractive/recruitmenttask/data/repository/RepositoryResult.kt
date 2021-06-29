package com.treelineinteractive.recruitmenttask.data.repository

data class RepositoryResult<T>(
    val data: T,
    val requestStatus: RepositoryRequestStatus
)