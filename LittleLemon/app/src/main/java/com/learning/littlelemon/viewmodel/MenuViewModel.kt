package com.learning.littlelemon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.littlelemon.repository.MenuItemEntity
import com.learning.littlelemon.repository.MenuRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MenuViewModel(private val menuRepository: MenuRepository) : ViewModel() {
    val menuItemsFlow = MutableStateFlow(emptyList<MenuItemEntity>())
    val menuCategoryFlow = MutableStateFlow(emptyList<String>())

    fun getMenuCategories() {
        viewModelScope.launch(IO) {
            menuRepository.getAllCategoriesStream().collectLatest {
                menuCategoryFlow.tryEmit(it)
            }
        }
    }

    fun getMenuItems(searchPhrase: String) {
        viewModelScope.launch(IO) {
            if (searchPhrase.isNotEmpty()) {
                menuRepository.getItemsStream(searchPhrase).collectLatest {
                    menuItemsFlow.tryEmit(it)
                }
            } else {
                menuRepository.getAllItemsStream().collectLatest {
                    menuItemsFlow.tryEmit(it)
                }
            }
        }
    }

    fun getMenuItemsForCategory(category: String) {
        viewModelScope.launch(IO) {
            menuRepository.getMenuCategory(category).collectLatest {
                menuItemsFlow.tryEmit(it)
            }
        }
    }

    fun setSearchPhrase(searchPhrase: String) {
        getMenuItems(searchPhrase)
    }

    fun updateMenuItem(menuItem: MenuItemEntity) {
        viewModelScope.launch(IO) {
            menuRepository.updateMenuItem(menuItem)
        }
    }

    fun insertMenuItem(menuItem: MenuItemEntity) {
        viewModelScope.launch(IO) {
            menuRepository.insertMenuItem(menuItem)
        }
    }
}
