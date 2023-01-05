package com.example.oplev.data.dto

import com.example.oplev.Model.Folder
import com.example.oplev.Model.Idea

class FolderDto (
    baseObject:Folder
        ){
    var ideas: MutableList<IdeaDto>? = mutableListOf<IdeaDto>()
    var childFolders: MutableList<FolderDto>? = mutableListOf<FolderDto>()

}