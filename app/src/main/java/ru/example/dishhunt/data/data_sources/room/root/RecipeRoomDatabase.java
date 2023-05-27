package ru.example.dishhunt.data.data_sources.room.root;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.example.dishhunt.R;
import ru.example.dishhunt.data.ImageStorage;
import ru.example.dishhunt.data.data_sources.room.dao.RecipeDao;
import ru.example.dishhunt.data.data_sources.room.entites.CommentEntity;
import ru.example.dishhunt.data.data_sources.room.entites.IngredientEntity;
import ru.example.dishhunt.data.data_sources.room.entites.ProductEntity;
import ru.example.dishhunt.data.data_sources.room.entites.RecipeEntity;
import ru.example.dishhunt.data.data_sources.room.entites.SlideEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserEntity;
import ru.example.dishhunt.data.data_sources.room.entites.UserSavedRecipes;

@Database(entities = {RecipeEntity.class, CommentEntity.class, UserSavedRecipes.class, UserEntity.class, IngredientEntity.class, ProductEntity.class, SlideEntity.class}, version = 1, exportSchema = false)
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
                fillDbWithStartData(dao);

            });
        }
    };

    private static void fillDbWithStartData(RecipeDao dao){
        for(int i = 1; i<15; i++){

            RecipeEntity recipeEntity = new RecipeEntity("Рецепт №"+i, "Давно выяснено, что при оценке дизайна и композиции читаемый текст мешает сосредоточиться. Lorem Ipsum используют потому, что тот обеспечивает более или менее стандартное заполнение шаблона, а также реальное распределение букв.",
                    i%7*10+i, i%3+1, i*100+200+i+i%5,
                    2, 2+i%2, (i*5+ i+ i%3)%10+1+".jpg", "Соль перец по вкусу");
            dao.insertRecipe(recipeEntity);
            CommentEntity commentEntity = new CommentEntity(i, 1, "Очень вкусно!",  12222);
            dao.insertComment(commentEntity);

            dao.insertSlide(new SlideEntity("11.jpg", "Затем на широкое блюдо выкладывать слои в указанной последовательности. Первым слоем выложить кусочки мелко нарезанной сельди уже без кости.\n" +
                    "На сельдь выложить измельченный лук. ", i, 1));
            dao.insertSlide(new SlideEntity("12.jpg", "Затем на широкое блюдо выкладывать слои в указанной последовательности. Первым слоем выложить кусочки мелко нарезанной сельди уже без кости.\n" +
                    "На сельдь выложить измельченный лук. ", i, 1));
            dao.insertSlide(new SlideEntity("13.jpg", "Затем на широкое блюдо выкладывать слои в указанной последовательности. Первым слоем выложить кусочки мелко нарезанной сельди уже без кости.\n" +
                    "На сельдь выложить измельченный лук. ", i, 1));

        }
        Log.e("qwe", "db generating2");

        dao.insertUserSavedRecipesIds(new UserSavedRecipes(1,1));
        dao.insertProduct(new ProductEntity("Морковь", "гр", 0.1f, 0.1f, 0.1f, 0.2f, 1, 3));
        dao.insertProduct(new ProductEntity("Картошка", "гр", 0.077f, 0.01f, 0.04f, 0.03f, 1, 0.017f));
        dao.insertProduct(new ProductEntity("Свекла", "гр", 0.1f, 0.2f, 0.1f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Селедка", "шт", 0.1f, 0.1f, 0.2f, 0.2f,20, 3));
        dao.insertProduct(new ProductEntity("Огурцы", "гр", 0.2f, 0.1f, 0.1f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Яблоко", "шт", 0.1f, 0.1f, 0.1f, 0.2f, 200, 3));
        dao.insertProduct(new ProductEntity("Мука", "гр", 0.1f, 0.1f, 0.2f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Вода", "мл", 0.2f, 0.1f, 0.1f, 0.1f,1, 3));
        dao.insertProduct(new ProductEntity("Баранина", "гр", 0.1f, 0.1f, 0.1f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Свинина", "гр", 0.1f, 0.2f, 0.1f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Курица", "гр", 0.2f, 0.1f, 0.1f, 0.1f, 1, 3));
        dao.insertProduct(new ProductEntity("Гречка", "гр", 0.1f, 0.1f, 0.1f, 0.1f,1, 3));
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
            dao.insertIngredient(new IngredientEntity(1,i, (i%3+1)*10));
            dao.insertIngredient(new IngredientEntity(2,i, (i%5+1)*10));
            if (i%2 == 0){
                dao.insertIngredient(new IngredientEntity(3,i, i%4+1));
            }
            if (i%3 == 0){
                dao.insertIngredient(new IngredientEntity(4,i, (i%6+1)*10));
            }
        }
        Log.e("qwe", "db generating");
    }
}
