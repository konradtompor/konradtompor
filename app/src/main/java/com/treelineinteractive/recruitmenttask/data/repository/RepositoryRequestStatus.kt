package com.treelineinteractive.recruitmenttask.data.repository

sealed class RepositoryRequestStatus {
    object FETCHING : RepositoryRequestStatus()
    object COMPLETE : RepositoryRequestStatus()
    class Error(val error: Exception) : RepositoryRequestStatus()
}