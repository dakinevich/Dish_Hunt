package ru.example.dishhunt.data.data_sources.room.root;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;

@Database(entities = {RecipeEntity.class, CommentEntity.class, UserSavedRecipes.class}, version = 1, exportSchema = false)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

    private static volatile RecipeRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RecipeRoomDatabase.class, "recipe_database")
                            .addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                RecipeDao dao = INSTANCE.recipeDao();
                dao.deleteAll();
                for(int i = 1; i<15; i++){
                    RecipeEntity recipeEntity = new RecipeEntity("Default title "+i, "Давно выяснено, что при оценке дизайна и композиции читаемый текст мешает сосредоточиться. Lorem Ipsum используют потому, что тот обеспечивает более или менее стандартное заполнение шаблона, а также реальное распределение букв.",
                            55+i, 1, 3402, 231,
                            2, 2+i%2, ""+ R.drawable.sample_food);
                    dao.insertRecipe(recipeEntity);
                    CommentEntity commentEntity = new CommentEntity(i, 0, "baseComent",  12222);
                    dao.insertComment(commentEntity);
                }
                dao.insertUserSavedRecipesIds(new UserSavedRecipes(1,1));

            });
        }
    };
}
