package com.ccjeng.iwish.realm.table;

import android.util.Log;

import com.ccjeng.iwish.model.Story;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;


/**
 * Created by andycheng on 2016/4/8.
 */
public class Migration implements RealmMigration {

    private static final String TAG = Migration.class.getName();
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

        Log.d(TAG, "oldVersion = " + oldVersion);

        if (oldVersion == 0) {

            schema.create("Daily")
                    .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("name", String.class, FieldAttribute.REQUIRED);

            schema.create("Frequency")
                    .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("name", String.class, FieldAttribute.REQUIRED);

            oldVersion++;
        }

        if (oldVersion == 1) {

            schema.get("Daily")
                    .addField("datetime", long.class);

            schema.get("Frequency")
                    .addField("count", int.class);

            schema.get("Frequency")
                    .addIndex("name");

            oldVersion++;
        }


        if (oldVersion == 2) {

            schema.get("Category")
                    .addField("order", int.class);

            schema.get("Item")
                    .addField("order", int.class);

            oldVersion++;
        }

        if (oldVersion == 3) {

            if (schema.get("Introduce") == null) {

                schema.create("Introduce")
                        .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                        .addField("name", String.class, FieldAttribute.REQUIRED)
                        .addField("order", int.class);
            }

            if (schema.get("Selection") == null) {
                schema.create("Selection")
                        .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                        .addField("name", String.class, FieldAttribute.REQUIRED)
                        .addField("order", int.class);
            }
            oldVersion++;
        }

        if(oldVersion == 6) {

            if (schema.get("Story") == null) {
                schema.create("Story")
                        .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                        .addField("name", String.class, FieldAttribute.REQUIRED)
                        .addField("order", int.class)
                        .addField("datetime", long.class);
            }

            oldVersion++;
        }

        if(oldVersion == 7) {

            RealmObjectSchema realmStory = schema.get(Story.class.getSimpleName());
            if (realmStory.hasPrimaryKey() && !realmStory.isRequired("id")) {
                realmStory.removePrimaryKey();
                realmStory.setRequired("id", true);
                realmStory.addPrimaryKey("id");
            }


            oldVersion++;
        }

    }
}
