package com.example.oplev.data.dto

import com.example.oplev.Model.Idea

class IdeaDto(
    var baseObject:Idea?
    ) {
    var parentFolder: MutableList<FolderDto>? = mutableListOf<FolderDto>()
}