package com.example.oplev.data.collections

import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.FolderDto

class FolderCollection {

     companion object {
             fun folderDtoFromMap(folderMap: HashMap<Folder, Int>, parentFolder:Folder): FolderDto {
                 val folderDto = FolderDto(parentFolder)
                 folderMap.entries.filter { it.value == parentFolder.id }
                     .forEach{ folderDto.childFolders?.add(folderDtoFromMap(folderMap,it.key)) }
                 return folderDto
         }
     }
}