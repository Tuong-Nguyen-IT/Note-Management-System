package com.example.notemanagementsystem.ui.category;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.notemanagementsystem.database.Dbcate;
import com.example.notemanagementsystem.model.Category;

import java.util.ArrayList;

public class CategoryViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private Dbcate database;
    // Create a LiveData with a String
    private MutableLiveData<ArrayList<Category>> categories;

    public CategoryViewModel(Application application) {
        super(application);

        categories = new MutableLiveData<ArrayList<Category>>();

        database = new Dbcate(application);
        categories.setValue(getAllCategories());
    }

    // Get LiveData
    public MutableLiveData<ArrayList<Category>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<ArrayList<Category>>();
        }
        return categories;
    }

    // Get data for LiveData
    public ArrayList<Category> getAllCategories() {
        return database.getAllCategory();
    }

    // Add new category
    public long addCategory(Category category) {
        return database.insertCategory(category);
    }

    // update category
    public int updateCategory(String key, Category category) {
        return database.updateCategoryName(key, category);
    }

    /**
     * Delete category
     * @param key
     * @return number of records
     */
    public int deleteCategory(String key) {
        return database.deleteCategory(key);
    }
}
