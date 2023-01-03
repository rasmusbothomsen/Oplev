package com.example.oplev.data.collections

import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.FolderDto

class FolderCollection {

     companion object {
         fun folderDtoFromMap(folderMap: HashMap<Int, Folder>, parentFolder:Folder): FolderDto {
             val folderDto = FolderDto(parentFolder)
             folderDto.childFolders?.addAll(folderMap.entries.filter { it.key == parentFolder.id }
                 .map { folderDtoFromMap(folderMap,it.value) })
             folderMap.remove(parentFolder.id)
             return folderDto
         }
     }
}