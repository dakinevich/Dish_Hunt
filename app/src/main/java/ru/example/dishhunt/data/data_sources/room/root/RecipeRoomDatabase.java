package ru.example.dishhunt.data.data_sources.room.root;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.IngredientEntity;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;

@Database(entities = {RecipeEntity.class, CommentEntity.class, UserSavedRecipes.class, UserEntity.class, IngredientEntity.class, ProductEntity.class}, version = 1, exportSchema = false)
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

                    Log.e("qwe", "db not created Instance saved");
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
            Log.e("qwe", "db generating1");

            databaseWriteExecutor.execute(() -> {

                RecipeDao dao = INSTANCE.recipeDao();
                dao.deleteAll();
                for(int i = 1; i<15; i++){
                    int[] imgs = new int[]{R.drawable.sample_food_1, R.drawable.sample_food_2, R.drawable.sample_food_3, R.drawable.sample_food_4, R.drawable.sample_food_5};
                    RecipeEntity recipeEntity = new RecipeEntity("Рецепт №"+i, "Давно выяснено, что при оценке дизайна и композиции читаемый текст мешает сосредоточиться. Lorem Ipsum используют потому, что тот обеспечивает более или менее стандартное заполнение шаблона, а также реальное распределение букв.",
                            i%7*10+i, i%2+1, i*100+200+i+i%5,
                            2, 2+i%2, ""+ imgs[(i*5+ i+ i%3)%6], "Соль перец по вкусу");
                    dao.insertRecipe(recipeEntity);
                    CommentEntity commentEntity = new CommentEntity(i, 1, "Очень вкусно!",  12222);
                    dao.insertComment(commentEntity);

                }
                Log.e("qwe", "db generating2");

                dao.insertUserSavedRecipesIds(new UserSavedRecipes(1,1));
                dao.insertProduct(new ProductEntity("Морковь", "шт", 22, 23, 12, 10, 80, 28));
                dao.insertProduct(new ProductEntity("Картошка", "кг", 96, 10, 16, 11, 1000, 45));
                dao.insertProduct(new ProductEntity("Свекла", "шт", 23, 24, 10, 12, 93, 65));
                dao.insertProduct(new ProductEntity("Селедка", "шт", 67, 13, 16, 16,48, 37));
                Log.e("qwe", "db generating2.1");

                dao.insertUser(new UserEntity("Вася Оливкин", "Инфромация о пользователе", "f"));
                dao.insertUser(new UserEntity("Петя Оливкин", "Инфромация о пользователе", "f"));
                dao.insertUser(new UserEntity("Женя Оливкин", "Инфромация о пользователе", "f"));
                Log.e("qwe", "db generating2.2");

                /*int a = dao.getRecipe(2).getValue().recipeEntity.getAuthorId();
                LiveData<UserEntity> u = dao.getUser(1);
                String ur = u.getValue().getBio();*/

                Log.e("qwe", "db generating3");

                for(int i = 1; i<15; i++){
                    dao.insertIngredient(new IngredientEntity(1,i, i%3+1));
                    dao.insertIngredient(new IngredientEntity(2,i, i%5+1));
                    if (i%2 == 0){
                        dao.insertIngredient(new IngredientEntity(3,i, i%4+1));
                    }
                    if (i%3 == 0){
                        dao.insertIngredient(new IngredientEntity(4,i, i%6+1));
                    }
                }
                Log.e("qwe", "db generating");

            });
        }
    };
}
