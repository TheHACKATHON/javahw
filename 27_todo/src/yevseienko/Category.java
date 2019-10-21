package yevseienko;

import java.io.Serializable;

public class Category implements Serializable {
    private static int Id;

    private int _id;
    private String _name;

    static{
        Id = Settings.getMaxCategoryId();
    }

    public Category(String categoryName) {
        this._id = ++Id;
        Settings.newCategory();

        if(categoryName != null){
            categoryName = categoryName.trim();
        }

        if(categoryName == null || categoryName.isEmpty()){
            throw new IllegalArgumentException("Указано неверное название категории");
        }
        this._name = TextTransform.capitalize(categoryName);
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

