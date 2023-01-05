package com.example.oplev.data.collections

import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.Model.Journey
import com.example.oplev.data.dto.FolderDto
import com.example.oplev.data.dto.IdeaDto
import com.example.oplev.data.roomDao.FolderDao
import com.example.oplev.data.roomDao.IdeaDao

class FolderCollection(
    val folderDao:FolderDao,
    val ideaDao: IdeaDao,
    var journeyId:Int
): baseCollection<FolderDto>{
    var iterations = 0

    fun folderDtoFromMap(folderMap: HashMap<Folder, Int>, parentFolder:Folder): FolderDto {
    val folderDto = FolderDto(parentFolder)
    folderDto.ideas = getIdeasForFolder(parentFolder).toMutableList()
                 folderMap.entries.filter { it.value == parentFolder.id }
                     .forEach{ folderDto.childFolders?.add(folderDtoFromMap(folderMap,it.key)) }
        iterations++
        if(iterations>1000){

        }
                 return folderDto

     }

    override fun roomDataLink(): List<FolderDto> {
        var folders = folderDao.findFolderFromJourneyId(journeyId)
        var folderMap = HashMap<Folder,Int>()
        var abseluteParentFolder:Folder? = null
            for (folder in folders){
            if(folder.parentFolderId != folder.id) {
                folderMap.put(folder, folder.parentFolderId)
            }else{
                abseluteParentFolder = folder
            }
        }
        abseluteParentFolder?.let {
            return listOf(folderDtoFromMap(folderMap, abseluteParentFolder))
        }
        return emptyList()
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