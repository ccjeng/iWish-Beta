package com.ccjeng.iwish.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by andycheng on 2016/3/27.
 */
public class SubItem extends RealmObject {

    @PrimaryKey @Required
    private String id;
    @Required
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
