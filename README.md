# NotePad(期中作业)
## 期中作业：记事本NotePad
### 阅读NotePad的源代码并做扩展
### 基本要求： 
 NoteList中显示条目增加时间戳显示   
 添加笔记查询功能（根据标题查询）
### 附加功能：根据自身实际情况进行扩充，以下是建议的扩展功能 
 UI美化   
 更改记事本的背景   
 导出笔记，笔记的云备份和恢复  
 笔记排序  

## 代码及截图
### 主界面及时间戳
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/menu.PNG)

### UI美化
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/menu.PNG)
### 更改记事本的背景
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/change.PNG)
### 导出笔记，笔记的云备份和恢复
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/output1.PNG) 
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/output.PNG)![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/output2.PNG)
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/save_s.PNG)![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/save1.PNG)
### 笔记排序
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/sort.PNG) 
#### 按颜色
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/sort_color.PNG)
#### 按更新时间
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/sort_updatetime.PNG) 
#### 按创建时间
![image](https://github.com/Magicpanda-orz/NotePad2/blob/master/image/sort_createtime.PNG) 

### 主要代码：
### 显示时间戳
#### 在NotePadProvider中修改insert():
```
Date date = new Date(now);
SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
String dateTime = format.format(date);
```
#### 在notelist_item.xml中添加一个显示时间的TextView
```
<TextView
android:id="@+id/text2"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:textAppearance="?android:attr/textAppearanceLarge"
android:textSize="20sp"
    />
```
#### 在NotesList的数据定义中增加修改时间
```
private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//显示修改时间
            NotePad.Notes.COLUMN_NAME_BACK_COLOR,//扩展显示颜色
            //NotePad.Notes.COLUMN_NAME_TEXT_COLOR,//字体颜色
    };
```
#### 装配的时候需要装配相应的日期，在dataColumns,viewIDs这两个参数需要加入时间
```
String[] dataColumns = {
        NotePad.Notes.COLUMN_NAME_TITLE,
        NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
} ;
```
```
int[] viewIDs = { android.R.id.text1 ,R.id.text2};
```
#### 最后通过SimpleCursorAdapter来进行装配
```
SimpleCursorAdapter adapter
    = new SimpleCursorAdapter(
              this,                            
              R.layout.noteslist_item,          
              cursor,                           
              dataColumns,
              viewIDs
      );
```
### 笔记查询功能（根据标题查询）

### UI美化、修改背景颜色
#### 在AndroidManifest.xml中NotesList的Activity中添加：
```
android:theme="@android:style/Theme.Holo.Light"
```
#### 在NotePad.java中添加：
```
public static final String COLUMN_NAME_BACK_COLOR = "color";

public static final int DEFAULT_COLOR = 0; //白
public static final int YELLOW_COLOR = 1; //黄
public static final int BLUE_COLOR = 2; //蓝
public static final int GREEN_COLOR = 3; //绿
public static final int RED_COLOR = 4; //红
```
#### NotePadProvifer.java中添加：
```
sNotesProjectionMap.put(
        NotePad.Notes.COLUMN_NAME_BACK_COLOR,
        NotePad.Notes.COLUMN_NAME_BACK_COLOR);
```
```
if (values.containsKey(NotePad.Notes.COLUMN_NAME_BACK_COLOR) == false) {
        values.put(NotePad.Notes.COLUMN_NAME_BACK_COLOR, NotePad.Notes.DEFAULT_COLOR);
        }
```
#### MyCursorAdpater.java设置颜色
```
package com.example.android.notepad;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class MyCursorAdapter extends SimpleCursorAdapter {


    public MyCursorAdapter(Context context, int layout, Cursor c,
                           String[] from, int[] to) {
        super(context, layout, c, from, to);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        super.bindView(view, context, cursor);
        //从数据库中读取的cursor中获取笔记列表对应的颜色数据，并设置笔记颜色
        int x = cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
        
        switch (x){
            case NotePad.Notes.DEFAULT_COLOR:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
            case NotePad.Notes.YELLOW_COLOR:
                view.setBackgroundColor(Color.rgb(247, 216, 133));
                break;
            case NotePad.Notes.BLUE_COLOR:
                view.setBackgroundColor(Color.rgb(165, 202, 237));
                break;
            case NotePad.Notes.GREEN_COLOR:
                view.setBackgroundColor(Color.rgb(161, 214, 174));
                break;
            case NotePad.Notes.RED_COLOR:
                view.setBackgroundColor(Color.rgb(244, 149, 133));
                break;
            default:
                view.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
        }

//        int f = cursor.getInt(cursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TEXT_COLOR));
//
//        switch (f){
//            case NotePad.Notes.DEFAULT_FONT:
//                view.setBackgroundColor(Color.rgb(0,0,0));
//                break;
//            case NotePad.Notes.RED_FONT:
//                view.setBackgroundColor(Color.rgb(244,149,133));
//                break;
//            case NotePad.Notes.BLUE_FONT:
//                view.setBackgroundColor(Color.rgb(165,202,237));
//                break;
//                default:
//                view.setBackgroundColor(Color.rgb(0,0,0));
//                break;
//        }
    }
}

```
#### NoteList中的PROJECTION添加颜色项：
```
private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE,//显示修改时间
            NotePad.Notes.COLUMN_NAME_BACK_COLOR,//扩展显示颜色
            //NotePad.Notes.COLUMN_NAME_TEXT_COLOR,//字体颜色
    };
```
#### 将NoteList中用的SimpleCursorAdapter改使用MyCursorAdapter：
```
adapter = new MyCursorAdapter(
        this,
        R.layout.noteslist_item,
        cursor,
        dataColumns,
        viewIDs
    );
```

#### 在NoteEditor类中有onResume()中加入：
```
 //读取颜色数据
            int x = mCursor.getInt(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_BACK_COLOR));
            
            switch (x){
                case NotePad.Notes.DEFAULT_COLOR:
                    mText.setBackgroundColor(Color.rgb(255, 255, 255));
                    break;
                case NotePad.Notes.YELLOW_COLOR:
                    mText.setBackgroundColor(Color.rgb(247, 216, 133));
                    break;
                case NotePad.Notes.BLUE_COLOR:
                    mText.setBackgroundColor(Color.rgb(165, 202, 237));
                    break;
                case NotePad.Notes.GREEN_COLOR:
                    mText.setBackgroundColor(Color.rgb(161, 214, 174));
                    break;
                case NotePad.Notes.RED_COLOR:
                    mText.setBackgroundColor(Color.rgb(244, 149, 133));
                    break;
                default:
                    mText.setBackgroundColor(Color.rgb(255, 255, 255));
                    break;
            }
```
#### 在NoteEditor的onOptionsItemSelected()中加入：
```
//换背景颜色选项
    case R.id.menu_color:
        changeColor();
        break;
```
#### editor_options_menu.xml中加入：
```
<item android:id="@+id/menu_color"
        android:title="@string/menu_color"
        android:icon="@drawable/ic_menu_color"
        android:showAsAction="always"/>     
```
#### 在NoteEditor中添加函数changeColor()：
```
//跳转改变颜色的activity，将uri信息传到新的activity
    private final void changeColor() {
        Intent intent = new Intent(null,mUri);
        intent.setClass(NoteEditor.this,NoteColor.class);
        NoteEditor.this.startActivity(intent);
    }
```
#### note_color.xml布局
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="horizontal" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageButton
        android:id="@+id/color_white"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_weight="1"
        android:background="@color/colorWhite"
        android:onClick="white"
        />
    <ImageButton
        android:id="@+id/color_yellow"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_weight="1"
        android:background="@color/colorYellow"
        android:onClick="yellow"
        />
    <ImageButton
        android:id="@+id/color_blue"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_weight="1"
        android:background="@color/colorBlue"
        android:onClick="blue"
       />
    <ImageButton
        android:id="@+id/color_green"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_weight="1"
        android:background="@color/colorGreen"
        android:onClick="green"
        />
    <ImageButton
        android:id="@+id/color_red"
        android:layout_width="0dp"
        android:layout_height="50dp"
        android:layout_weight="1"
        android:background="@color/colorRed"
        android:onClick="red"
        />

</LinearLayout>
```
#### NoteColor.java
```
package com.example.android.notepad;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class NoteColor extends Activity {
    private Cursor mCursor;
    private Uri mUri;
    private int color;
    private static final int COLUMN_INDEX_TITLE = 1;
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_BACK_COLOR,
    };
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_color);
        //从NoteEditor传入的uri
        mUri = getIntent().getData();
        mCursor = managedQuery(
                mUri,        // The URI for the note that is to be retrieved.
                PROJECTION,  // The columns to retrieve
                null,        // No selection criteria are used, so no where columns are needed.
                null,        // No where columns are used, so no where values are needed.
                null         // No sort order is needed.
        );
    }
    @Override
    protected void onResume(){
        //执行顺序在onCreate之后
        if (mCursor != null) {
            mCursor.moveToFirst();
            color = mCursor.getInt(COLUMN_INDEX_TITLE);
        }
        super.onResume();
    }
    @Override
    protected void onPause() {
        //执行顺序在finish()之后，将选择的颜色存入数据库
        super.onPause();
        ContentValues values = new ContentValues();
        values.put(NotePad.Notes.COLUMN_NAME_BACK_COLOR, color);
        getContentResolver().update(mUri, values, null, null);
    }
    public void white(View view){
        color = NotePad.Notes.DEFAULT_COLOR;
        finish();
    }
    public void yellow(View view){
        color = NotePad.Notes.YELLOW_COLOR;
        finish();
    }
    public void blue(View view){
        color = NotePad.Notes.BLUE_COLOR;
        finish();
    }
    public void green(View view){
        color = NotePad.Notes.GREEN_COLOR;
        finish();
    }
    public void red(View view){
        color = NotePad.Notes.RED_COLOR;
        finish();
    }

}
```
#### 注入到AndroidManifest.xml中
```
<!--换背景色-->
<activity android:name="NoteColor"
    android:theme="@android:style/Theme.Holo.Light.Dialog"
    android:label="ChangeColor"
    android:windowSoftInputMode="stateVisible"/>
```
### 导出笔记
#### editor_options_menu.xml中添加
```
<item android:id="@+id/menu_output"
    android:title="@string/menu_output" />
```
#### 布局output_text.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical" android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:paddingLeft="6dip"
    android:paddingRight="6dip"
    android:paddingBottom="3dip">

    <EditText android:id="@+id/output_name"
        android:maxLines="1"
        android:layout_marginTop="2dp"
        android:layout_marginBottom="15dp"
        android:layout_width="wrap_content"
        android:ems="25"
        android:layout_height="wrap_content"
        android:autoText="true"
        android:capitalize="sentences"
        android:scrollHorizontally="true" />
    <Button android:id="@+id/output_ok"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right"
        android:text="@string/output_click"
        android:onClick="OutputClick" />

</LinearLayout>
```
#### 在NoteEditor中添加：
```
//跳转导出笔记的activity，将uri信息传到新的activity
    private final void outputNote() {
        Intent intent = new Intent(null,mUri);
        intent.setClass(NoteEditor.this,OutputText.class);
        NoteEditor.this.startActivity(intent);
    }
```
```
//导出笔记选项
   case R.id.menu_output:
        outputNote();
        break;
```
#### OutputText.java
```
package com.example.android.notepad;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class OutputText extends Activity {
    //要使用的数据库中笔记的信息
    private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID, // 0
            NotePad.Notes.COLUMN_NAME_TITLE, // 1
            NotePad.Notes.COLUMN_NAME_NOTE, // 2
            NotePad.Notes.COLUMN_NAME_CREATE_DATE, // 3
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, // 4
    };
    //读取出的值放入这些变量
    private String TITLE;
    private String NOTE;
    private String CREATE_DATE;
    private String MODIFICATION_DATE;
    //读取该笔记信息
    private Cursor mCursor;
    //导出文件的名字
    private EditText mName;
    //NoteEditor传入的uri，用于从数据库查出该笔记
    private Uri mUri;
    //关于返回与保存按钮的一个特殊标记，返回的话不执行导出，点击按钮才导出
    private boolean flag = false;
    private static final int COLUMN_INDEX_TITLE = 1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_text);
        mUri = getIntent().getData();
        mCursor = managedQuery(
                mUri,        // The URI for the note that is to be retrieved.
                PROJECTION,  // The columns to retrieve
                null,        // No selection criteria are used, so no where columns are needed.
                null,        // No where columns are used, so no where values are needed.
                null         // No sort order is needed.
        );
        mName = (EditText) findViewById(R.id.output_name);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if (mCursor != null) {
            // The Cursor was just retrieved, so its index is set to one record *before* the first
            // record retrieved. This moves it to the first record.
            mCursor.moveToFirst();
            //编辑框默认的文件名为标题，可自行更改
            mName.setText(mCursor.getString(COLUMN_INDEX_TITLE));
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mCursor != null) {
            //从mCursor读取对应值
            TITLE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_TITLE));
            NOTE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_NOTE));
            CREATE_DATE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_CREATE_DATE));
            MODIFICATION_DATE = mCursor.getString(mCursor.getColumnIndex(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE));
            //flag在点击导出按钮时会设置为true，执行写文件
            if (flag == true) {
                write();
            }
            flag = false;
        }
    }
    public void OutputClick(View v){
        flag = true;
        finish();
    }
    private void write()
    {
        try
        {
            // 如果手机插入了SD卡，而且应用程序具有访问SD的权限
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                // 获取SD卡的目录
                File sdCardDir = Environment.getExternalStorageDirectory();
                //创建文件目录
                File targetFile = new File(sdCardDir.getCanonicalPath() + "/" + mName.getText() + ".txt");
                //写文件
                PrintWriter ps = new PrintWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
                ps.println(TITLE);
                ps.println(NOTE);
                ps.println("创建时间：" + CREATE_DATE);
                ps.println("最后一次修改时间：" + MODIFICATION_DATE);
                ps.close();
                Toast.makeText(this, "保存成功,保存位置：" + sdCardDir.getCanonicalPath() + "/" + mName.getText() + ".txt", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
```
#### 注入到AndroidManifest.xml
```
<!--添加导出activity-->
        <activity android:name="OutputText"
            android:label="@string/output_name"
            android:theme="@android:style/Theme.Holo.Dialog"
            android:windowSoftInputMode="stateVisible">
        </activity>
```
```
<!-- 在SD卡中创建与删除文件权限 -->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <!-- 向SD卡写入数据权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```
### 笔记排序
#### list_options_menu.xml中添加：
```
<item
    android:id="@+id/menu_sort"
    android:title="@string/menu_sort"
    android:icon="@android:drawable/ic_menu_sort_by_size"
    android:showAsAction="always" >
    <menu>
        <item
            android:id="@+id/menu_sort1"
            android:title="@string/menu_sort1"/>
        <item
            android:id="@+id/menu_sort2"
            android:title="@string/menu_sort2"/>
        <item
            android:id="@+id/menu_sort3"
            android:title="@string/menu_sort3"/>
        </menu>
    </item>
```
#### 在NoteList.java中添加：
```
//创建时间排序
            case R.id.menu_sort1:
                cursor = managedQuery(
                        getIntent().getData(),
                        PROJECTION,
                        null,
                        null,
                        NotePad.Notes._ID
                );
                adapter = new MyCursorAdapter(
                        this,
                        R.layout.noteslist_item,
                        cursor,
                        dataColumns,
                        viewIDs
                );
                setListAdapter(adapter);
                return true;
            //修改时间排序
            case R.id.menu_sort2:
                cursor = managedQuery(
                        getIntent().getData(),
                        PROJECTION,
                        null,
                        null,
                        NotePad.Notes.DEFAULT_SORT_ORDER
                );
                adapter = new MyCursorAdapter(
                        this,
                        R.layout.noteslist_item,
                        cursor,
                        dataColumns,
                        viewIDs
                );
                setListAdapter(adapter);
                return true;
            //颜色排序
            case R.id.menu_sort3:
                cursor = managedQuery(
                        getIntent().getData(),
                        PROJECTION,
                        null,
                        null,
                        NotePad.Notes.COLUMN_NAME_BACK_COLOR
                );
                adapter = new MyCursorAdapter(
                        this,
                        R.layout.noteslist_item,
                        cursor,
                        dataColumns,
                        viewIDs
                );
                setListAdapter(adapter);
                return true;
```
### 另外：values中
#### colors.xml
```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="yellow">#ffff00</color>
    <color name="blue_green">#00cccc</color>
    <color name="colorWhite">#FFFFF0</color>
    <color name="colorYellow">#FFF68F</color>
    <color name="colorBlue">#C6E2FF</color>
    <color name="colorGreen">#B4EEB4</color>
    <color name="colorRed">#FFC0CB</color>
    <color name="gray">#ADADAD</color>

</resources>
```
#### strings.xml
```
<resources>
    <string name="app_name">NotePad</string>
    <string name="live_folder_name">Notes</string>

    <string name="title_edit_title">Note title:</string>
    <string name="title_create">New note</string>
    <string name="title_edit">Edit: %1$s</string>
    <string name="title_notes_list">Notes</string>

    <string name="menu_add">New note</string>
    <string name="menu_save">Save</string>
    <string name="menu_delete">删除</string>
    <string name="menu_open">Open</string>
    <string name="menu_revert">Revert changes</string>
    <string name="menu_copy">Copy</string>
    <string name="menu_paste">Paste</string>

    <string name="button_ok">OK</string>
    <string name="text_title">Title:</string>

    <string name="resolve_edit">Edit note</string>
    <string name="resolve_title">Edit title</string>

    <string name="error_title">Error</string>
    <string name="error_message">Error loading note</string>
    <string name="nothing_to_save">There is nothing to save</string>
    <string name="menu_search">menu_search</string>
    <string name="font_size">font_size</string>
    <string name="font10">font10</string>
    <string name="font16">font16</string>
    <string name="font20">font20</string>
    <string name="font_color">font_color</string>
    <string name="fontred">fontred</string>
    <string name="fontblue">fontblue</string>
    <string name="fontblack">fontblack</string>
    <string name="menu_color">menu_color</string>
    <string name="menu_output">文件导出</string>
    <string name="output_click">output_click</string>
    <string name="output_name">output_name</string>
    <string name="menu_sort">menu_sort</string>
    <string name="menu_sort1">创建时间</string>
    <string name="menu_sort2">更新时间</string>
    <string name="menu_sort3">背景颜色</string>
</resources>
```








