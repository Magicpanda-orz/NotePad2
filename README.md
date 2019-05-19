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
### 主界面
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







