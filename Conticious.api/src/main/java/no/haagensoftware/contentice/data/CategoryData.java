package no.haagensoftware.contentice.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joahaage
 * Date: 15.11.13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class CategoryData {
    private String id;
    private List<SubCategoryData> subcategories = new ArrayList<>();
    private List<CategoryField> defaultFields = new ArrayList<>();
    private boolean isPublic = false;

    public CategoryData() {

    }

    public CategoryData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SubCategoryData> getSubcategories() {
        return subcategories;
    }

    public void addSubcategory(SubCategoryData subcategoryData) {
        subcategories.add(subcategoryData);
    }

    public List<CategoryField> getDefaultFields() {
        return defaultFields;
    }

    public void setDefaultFields(List<CategoryField> defaultFields) {
        this.defaultFields = defaultFields;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
