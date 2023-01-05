package com.example.oplev.data.collections

import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.data.dto.FolderDto
import com.example.oplev.data.dto.IdeaDto
import com.example.oplev.data.roomDao.FolderDao
import com.example.oplev.data.roomDao.IdeaDao

class FolderCollection(
    val folderDao:FolderDao,
    val ideaDao: IdeaDao
): baseCollection<FolderDto>(){

    fun folderDtoFromMap(folderMap: HashMap<Folder, Int>, parentFolder:Folder): FolderDto {
    val folderDto = FolderDto(parentFolder)
    folderDto.ideas = getIdeasForFolder(parentFolder).toMutableList()
                 folderMap.entries.filter { it.value == parentFolder.id }
                     .forEach{ folderDto.childFolders?.add(folderDtoFromMap(folderMap,it.key)) }
                 return folderDto

     }

    override fun roomDataLink(): List<FolderDto> {
        folderDao.
        return super.roomDataLink()
    }

    fun getIdeasForFolder(folder:Folder):List<IdeaDto>{
        var ideaDtos = mutableListOf<IdeaDto>()
        var ideasForFolder = ideaDao.getIdeaFromFolderID(folder.id)
        for(idea in ideasForFolder){
            ideaDtos.add(IdeaDto(idea))
        }
        return ideaDtos

    }
}