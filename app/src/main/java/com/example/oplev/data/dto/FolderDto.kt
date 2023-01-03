package com.example.oplev.data.dto

import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea

class FolderDto (
    baseObject:Folder
        ){
    var ideas: MutableList<Idea>? = mutableListOf<Idea>()
    var childFolders: MutableList<FolderDto>? = mutableListOf<FolderDto>()

}