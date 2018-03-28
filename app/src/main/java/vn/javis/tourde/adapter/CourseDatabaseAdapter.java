package vn.javis.tourde.adapter;

/**
 * Created by admin on 3/23/2018.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.javis.tourde.model.Course;

public class CourseDatabaseAdapter extends SQLiteOpenHelper {
    private String DB_PATH = "data/data/vn.javis.tourde/";
    private static String DB_NAME = "tour_demo_db.s3db";
    Context context;
    private SQLiteDatabase myDatabase;

    public CourseDatabaseAdapter(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        boolean dbexist = checkDatabse();

        if (dbexist) {
            openDatabase();
        } else {
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase getMyDatabase() {
        return myDatabase;
    }

    private boolean checkDatabse() {
        boolean checkDb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkDb = dbfile.exists();
            System.out.println("Databse  exist!" + checkDb);

        } catch (SQLiteException e) {

            System.out.println("Databse doesn't exist!");
        }
        return checkDb;

    }

    public void openDatabase() {
        String myPath = DB_PATH + DB_NAME;
        if (myDatabase != null && myDatabase.isOpen()) {
            return;
        }
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void closeDatabase() {
        if (myDatabase != null) {
            myDatabase.close();
            super.close();
        }

    }

    private void copyDatabase() throws IOException {
        try {
            AssetManager dirPath = context.getAssets();

            InputStream myinput = context.getAssets().open(DB_NAME);

            String outFileName = DB_PATH + DB_NAME;

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myinput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myinput.close();
            openDatabase();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Course getCourseByIndex(int index) {
        if (myDatabase == null || !myDatabase.isOpen())
            return null;
        Course this_course = null;
        return this_course;
    }

    public ArrayList<Course> getAllCourses() {
        try {
            ArrayList<Course> list_course = new ArrayList<Course>();
            Cursor cursor = myDatabase.query("tblCourses", null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Course mod = new Course(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getFloat(3), cursor.getInt(4), cursor.getInt(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9));
                list_course.add(mod);
                cursor.moveToNext();
            }
            cursor.close();
            closeDatabase();
            return list_course;
        } catch (Exception e) {
            System.out.println("getAllCourses" + e.getMessage());
        }
        return null;
    }

    public int getCommentByIdCourse(int course_id) {
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM tblComment WHERE id_course=" + course_id, null);
        return cursor.getCount();
    }
}
