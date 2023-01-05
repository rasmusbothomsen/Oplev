package com.example.oplev.data.dataService

import com.example.oplev.MainActivity
import com.example.oplev.Model.Folder
import com.example.oplev.data.collections.FolderCollection
import com.example.oplev.data.dto.FolderDto

class FolderDataService {
    fun roomDataLink(journeyId:Int): List<FolderDto> {
        var folderDao = MainActivity.instance.FolderDao()
        val folderCollection = FolderCollection()
        var folders = folderDao.findFolderFromJourneyId(journeyId)
        var folderMap = HashMap<Folder,Int>()
        var abseluteParentFolder: Folder? = null
        for (folder in folders){
            if(folder.parentFolderId != folder.id) {
                folderMap.put(folder, folder.parentFolderId)
            }else{
                abseluteParentFolder = folder
            }
        }
        abseluteParentFolder?.let {
            return listOf(folderCollection.folderDtoFromMap(folderMap, abseluteParentFolder))
        }
        return emptyList()
    }
}