package com.rakib.mosjid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Mosjid";
    private static final String TABLE_NAME = "All_Info";
    private static final int VERSION = 1;
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String F_NAME = "F_Name";
    private static final String PHONE = "Phone";
    private static MyDataHelper mInstance = null;
    //private static final String STATUS = "Status";
    private static final String SELECT_SQL = "SELECT * FROM ";

    private SQLiteDatabase database;
    private Context context;
    public static MyDataHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new MyDataHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public MyDataHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                    NAME+" VARCHAR(50),"+F_NAME+" VARCHAR(50) , "+PHONE+" VARCHAR(15) );");
            Toast.makeText(context,"Table Created",Toast.LENGTH_SHORT).show();
        }
        catch (Exception E)
        {
            Toast.makeText(context,"Create Exception: "+E,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
            Toast.makeText(context,"OnUpgrade Successfully",Toast.LENGTH_SHORT).show();
        }
        catch (Exception E)
        {
            Toast.makeText(context,"UpgradeException: "+E,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method is called from AddPage for inserting people
     * to the database
     * @param name
     * @param father
     * @param phone
     * @return
     */
    public long insertPeopleDb(String name,String father,String phone)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(F_NAME,father);
        contentValues.put(PHONE,phone);
        //contentValues.put(STATUS,"due");
        long mid = 0;
        try
        {
            mid = db.insert(TABLE_NAME,null,contentValues);
            return mid;
        }
        catch(SQLException e)
        {
            return -1;
        }

    }

    /**
     * Get all data from database except null name
     * Because null name is the deleted person
     * We keep it null because the cash is always add with the balance
     * @param table_name
     * @return
     */
    public Cursor getDataDb(String table_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try
        {
            //Log.d("sql","Paisi");
            cursor = db.rawQuery(SELECT_SQL + table_name+" WHERE "+NAME+" IS NOT NULL ORDER BY NAME ASC ;", null);
            return cursor;
        }
        catch (Exception e)
        {
            Log.d("sql",e.toString());
            return cursor;
        }


    }

    /**
     * Get the list of the Due of the current month
     * @param table_name
     * @param col
     * @return
     */
    public Cursor getDataDue(String table_name,String col)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try
        {
            //Log.d("cursor",table_name+"     "+col);
            cursor = db.rawQuery(SELECT_SQL + table_name+" WHERE "+col+" IS NULL  AND Name IS NOT NULL ORDER BY NAME ASC ;", null);
            return cursor;
        }
        catch (Exception e)
        {
            return cursor;
        }


    }

    /**
     * Get the list of the paid of the current month
     * @param table_name
     * @param col
     * @return
     */
    public Cursor getDataPaid(String table_name,String col)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try
        {
            //Log.d("cursor",table_name+"     "+col);
            cursor = db.rawQuery(SELECT_SQL + table_name+" WHERE "+col+" IS NOT NULL  AND Name IS NOT NULL ORDER BY NAME ASC;", null);
            return cursor;
        }
        catch (Exception e)
        {
            return cursor;
        }

    }

    /**
     * Add the new Column to the table for the new month
     * @param month
     * @param year
     */

    public void addRowToTable(int month,int year)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<String> monthList = new ArrayList<>();
        monthList.add(0,"JAN");
        monthList.add(1,"FEB");
        monthList.add(2,"MAR");
        monthList.add(3,"APR");
        monthList.add(4,"MAY");
        monthList.add(5,"JUNE");
        monthList.add(6,"JULY");
        monthList.add(7,"AUG");
        monthList.add(8,"SEP");
        monthList.add(9,"OCT");
        monthList.add(10,"NOV");
        monthList.add(11,"DEC");

        String mmYY = monthList.get(month)+"_"+year;

        try{
            Cursor cursor = db.rawQuery("SELECT "+mmYY+" FROM "+TABLE_NAME,null);


        }
        catch (Exception e) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " +
                    mmYY + " VARCHAR(5);");

        }

    }

    /**
     * Update due list who have paid
     * @param col
     * @param id
     * @param money
     * @return
     */

    public boolean updateDueToPay(String col,String id,String money)
    {

        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            db.execSQL("UPDATE "+TABLE_NAME+ " SET "+col+" = "+money+" WHERE "+ID+" = "+id+" ;");
            //db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
            return true;
        }
        catch (Exception E)
        {
            return false;
        }

    }

    /**
     * These Fetch titiled for the search
     * @param inputText
     * @param Col
     * @return
     * @throws SQLException
     */

    public Cursor fetchPeopleByNameDue(String inputText,String Col) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = null;

            if (inputText == null || inputText.length() == 0) {
                mCursor = db.rawQuery(SELECT_SQL + TABLE_NAME + " ORDER BY NAME ASC ;", null);

            } else {

                mCursor=db.rawQuery("SELECT * FROM All_Info WHERE Name LIKE '%"+inputText+"%' and "+Col+" is null ORDER BY NAME ASC ;",null);
            }
            if (mCursor != null) {
                mCursor.moveToFirst();
            }

        return mCursor;

    }
    public Cursor fetchPeopleByNamePaid(String inputText,String Col) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = null;

            if (inputText == null || inputText.length() == 0) {
                mCursor = db.rawQuery(SELECT_SQL + TABLE_NAME + " WHERE Name IS NOT NULL ORDER BY NAME ASC ;", null);

            } else {

                mCursor=db.rawQuery("SELECT * FROM All_Info WHERE Name LIKE '%"+inputText+"%' and "+Col+" is not null  ORDER BY NAME ASC ;",null);
            }
            if (mCursor != null) {
                mCursor.moveToFirst();
            }

        return mCursor;

    }
    public Cursor fetchAllPeopleByName(String inputText) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = null;

            if (inputText == null || inputText.length() == 0) {
                mCursor = db.rawQuery(SELECT_SQL + TABLE_NAME + " WHERE Name IS NOT NULL ORDER BY NAME ASC ;", null);

            } else {

                mCursor=db.rawQuery("SELECT * FROM All_Info WHERE Name LIKE '%"+inputText+"%' ORDER BY NAME ASC ;",null);
            }
            if (mCursor != null) {
                mCursor.moveToFirst();
            }

        return mCursor;

    }

    /**
     * Get all column name from table
     * @return
     */

    public String[] getAllColumnName()
    {
        SQLiteDatabase mDataBase;
        //(some code here...)
        mDataBase = getReadableDatabase();
        Cursor dbCursor = mDataBase.rawQuery(SELECT_SQL+TABLE_NAME,null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }

    /**
     * Get data for the particular id
     * @param ID
     * @return
     * @throws SQLException
     */
    public Cursor getDataFromID(String ID) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = null;
        mCursor=db.rawQuery("SELECT * FROM All_Info WHERE _id = "+ID,null);

        return mCursor;
    }
    public boolean deleteData(String ID)
    {
        SQLiteDatabase db = getWritableDatabase();
        try
        {
            //Log.d("sql",ID);
            db.execSQL("update All_Info set Name = null where _id = "+ID);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * This function returns all value
     * @param table_name
     * @return
     */
    public Cursor getDataDbForBalance(String table_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try
        {
            //Log.d("sql","Paisi");
            cursor = db.rawQuery(SELECT_SQL + table_name+" ORDER BY NAME ASC ;", null);
            return cursor;
        }
        catch (Exception e)
        {
            Log.d("sql",e.toString());
            return cursor;
        }


    }
    public boolean updateEditedValues(String[] col,String[] value,String idd)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        try
        {
            String generate="";
            int size_of = col.length;
            generate+="UPDATE "+TABLE_NAME+" SET ";
            for(int i =0 ;i< size_of;i++)
            {
                if(value[i].equals("0"))
                {
                    generate+=col[i]+" = null ";
                }
                else {
                    generate += col[i] + " = '" + value[i] + "' ";

                }
                if(i<size_of-1)
                {
                    generate+=",";
                }
            }

            generate+= "WHERE _id = "+idd;
            Log.d("sql",generate);
            db.execSQL(generate);
            return true;
        }
        catch (Exception e)
        {
            Log.d("sql",e.toString());
            return false;
        }
    }
}
