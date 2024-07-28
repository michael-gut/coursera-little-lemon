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
    val menuCategory = MutableStateFlow("")

    fun getMenuItems() {
        viewModelScope.launch(IO) {
            menuRepository.getAllItemsStream().collectLatest {
                menuItemsFlow.tryEmit(it)
            }
        }
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

    fun setMenuCategory(category: String) {
        menuCategory.tryEmit(category)
    }
}
