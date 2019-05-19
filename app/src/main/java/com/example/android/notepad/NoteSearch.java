package com.example.android.notepad;


import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import static android.app.Activity.DEFAULT_KEYS_SHORTCUT;
import static android.content.Intent.getIntent;

public class NoteSearch extends Activity implements SearchView.OnQueryTextListener {//extends ListActivity

    private ListView listView=null;
    private static final String[] PROJECTION = new String[]{
            NotePad.Notes._ID,//0
            NotePad.Notes.COLUMN_NAME_TITLE,
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//显示修改的时间
            NotePad.Notes.COLUMN_NAME_BACK_COLOR

    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // The user does not need to hold down the key to use menu shortcuts.
        setContentView(R.layout.note_search);
        listView=(ListView)findViewById(R.id.list);

        Intent intent = getIntent();// Gets the intent that started this Activity.
        // If there is no data associated with the Intent, sets the data to the default URI, which
        // accesses a list of notes.
        if (intent.getData()==null){
            intent.setData(NotePad.Notes.CONTENT_URI);
        }

        listView.setOnCreateContextMenuListener(this);
        SearchView mSearchView = (SearchView)findViewById(R.id.search_view);//注册监听器
        //mSearchView.setOnQueryTextListener(NoteSearch.this);
        mSearchView.setIconifiedByDefault(false); //显示搜索的天幕，默认只有一个放大镜图标
        mSearchView.setSubmitButtonEnabled(true); //显示搜索按钮
        mSearchView.setBackgroundColor(getResources().getColor(R.color.blue_green)); //设置背景颜色
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) { //Test改变的时候执行的内容
        //Text发生改变时执行的内容
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " Like ? ";//查询条件
        String[] selectionArgs = { "%"+s+"%" };//查询条件参数，配合selection参数使用,%通配多个字符

        //查询数据库中的内容,当我们使用 SQLiteDatabase.query()方法时，就会得到Cursor对象， Cursor所指向的就是每一条数据。
        //managedQuery(Uri, String[], String, String[], String)等同于Context.getContentResolver().query()
        Cursor cursor = managedQuery(
                getIntent().getData(),            // Use the default content URI for the provider.用于ContentProvider查询的URI，从这个URI获取数据
                PROJECTION,                       // Return the note ID and title for each note. and modifcation date.用于标识uri中有哪些columns需要包含在返回的Cursor对象中
                selection,                        // 作为查询的过滤参数，也就是过滤出符合selection的数据，类似于SQL的Where语句之后的条件选择
                selectionArgs,                    // 查询条件参数，配合selection参数使用
                NotePad.Notes.DEFAULT_SORT_ORDER  // Use the default sort order.查询结果的排序方式，按照某个columns来排序，例：String sortOrder = NotePad.Notes.COLUMN_NAME_TITLE
        );

        //一个简单的适配器，将游标中的数据映射到布局文件中的TextView控件或者ImageView控件中
        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE ,  NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };
        int[] viewIDs = { android.R.id.text1 , R.id.text2 };
        MyCursorAdapter adapter = new MyCursorAdapter(
                this,                   //context:上下文
                R.layout.noteslist_item,         //layout:布局文件，至少有int[]的所有视图
                cursor,                          //cursor：游标
                dataColumns,                     //from：绑定到视图的数据
                viewIDs                          //to:用来展示from数组中数据的视图
                //flags：用来确定适配器行为的标志，Android3.0之后淘汰
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Constructs a new URI from the incoming URI and the row ID
                Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

                // Gets the action from the incoming Intent
                String action = getIntent().getAction();

                // Handles requests for note data
                if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {

                    // Sets the result to return to the component that called this Activity. The
                    // result contains the new URI
                    setResult(RESULT_OK, new Intent().setData(uri));
                } else {

                    // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
                    // Intent's data is the note ID URI. The effect is to call NoteEdit.
                    startActivity(new Intent(Intent.ACTION_EDIT, uri));
                }
            }
        });
        return true;
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//
//        // Constructs a new URI from the incoming URI and the row ID
//        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
//
//        // Gets the action from the incoming Intent
//        String action = getIntent().getAction();
//
//        // Handles requests for note data
//        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
//
//            // Sets the result to return to the component that called this Activity. The
//            // result contains the new URI
//            setResult(RESULT_OK, new Intent().setData(uri));
//        } else {
//
//            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
//            // Intent's data is the note ID URI. The effect is to call NoteEdit.
//            startActivity(new Intent(Intent.ACTION_EDIT, uri));
//        }
//    }


}




