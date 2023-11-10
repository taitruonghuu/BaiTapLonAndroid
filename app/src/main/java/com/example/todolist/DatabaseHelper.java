package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "TaskManagement.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng my_task và các cột của nó.
    private static final String MY_TASK_TABLE_NAME = "my_task";
    private static final String MY_TASK_ID = "task_id";
    private static final String MY_TASK_TITLE = "task_title";
    private static final String MY_TASK_CONTENT = "task_content";
    private static final String MY_TASK_COMPLETE = "task_complete";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Hàm khởi tạo database gồm bảng: my_task.
    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String query = "CREATE TABLE " + MY_TASK_TABLE_NAME + " (" +
                MY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MY_TASK_TITLE + " TEXT, " +
                MY_TASK_CONTENT + " TEXT, " +
                MY_TASK_COMPLETE + " INTEGER);";

        db.execSQL(query);
    }

    // Hàm dùng để tạo lại database nếu có lỗi.
    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MY_TASK_TABLE_NAME);
        onCreate(db);
    }

    // Hàm dùng để lấy dữ liệu của bảng my_task.
    public Cursor getAllTasks() {
        String query = "SELECT * FROM " + MY_TASK_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            // Phân biệt giữa rawQuery và execSql.
            // rawQuery trả về Cursor chứa dữ liệu trong đó, thường dùng trong câu truy vấn SELECT,...
            // execSql thường sử dụng trong các câu truy vấn không trả về dữ liệu như INSERT, UPDATE, DELETE,...
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    // Thêm công việc
    public long addTask(String title, String content) {
        ContentValues newTask = new ContentValues();
        newTask.put(MY_TASK_TITLE, title);
        newTask.put(MY_TASK_CONTENT, content);
        newTask.put(MY_TASK_COMPLETE, 0);

        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(MY_TASK_TABLE_NAME, null, newTask);
    }

    // Cập nhật công việc.
    public long updateTask(@NonNull MyTask myTask) {
        ContentValues task = new ContentValues();
        task.put(MY_TASK_TITLE, myTask.getTitle());
        task.put(MY_TASK_CONTENT, myTask.getContent());
        task.put(MY_TASK_COMPLETE, myTask.getComplete());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(MY_TASK_TABLE_NAME, task, MY_TASK_ID + "=?", new String[]{String.valueOf(myTask.getId())});
    }

    // Xóa công việc.
    public long deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(MY_TASK_TABLE_NAME, MY_TASK_ID + "=?", new String[]{String.valueOf(id)});
    }
}
