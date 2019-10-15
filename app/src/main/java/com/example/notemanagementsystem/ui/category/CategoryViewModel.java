package com.example.notemanagementsystem.ui.category;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.notemanagementsystem.database.DatabaseHandler;
import com.example.notemanagementsystem.model.Category;

import java.util.ArrayList;

public class CategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DatabaseHandler database;
    private MutableLiveData<ArrayList<Category>> categories;

    public CategoryViewModel(Application application) {
        super(application);

        categories = new MutableLiveData<ArrayList<Category>>();

        database = new DatabaseHandler(application);
        categories.setValue(getAllCategories());
    }
    public MutableLiveData<ArrayList<Category>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<ArrayList<Category>>();
        }
        return categories;
    }
    public ArrayList<Category> getAllCategories() {
        return database.getAllCategory();
    }
    public long addCategory(Category category) {
        return database.insertCategory(category);
    }
    public int updateCategory(String key, Category category) {
        return database.updateCategoryName(key, category);
    }
    public int deleteCategory(String key) {
        return database.deleteCategory(key);
    }
}