# SuperItemDecoration
对RecyclerView的ItemDecoration进行的通用型封装。 包括分割线和分类title。

![LinearDemo](https://github.com/cxystyle/SuperItemDecoration/blob/master/images/linear_demo.gif)

![GridDemo](https://github.com/cxystyle/SuperItemDecoration/blob/master/images/grid_demo.gif)

## 引用方法
```gradle
项目的build.gradle文件中：
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}


module的build.gradle文件中：
dependencies {
    implementation 'com.github.cxystyle:SuperItemDecoration:3.0.0'
}
```

## 快速使用方法
```java
LinearBuilder builder = new LinearBuilder();
//GirdBuilder builder = new GirdBuilder();

mRv.addItemDecoration(builder.build());
```

## 完整调用方法

以下为食用方法:
```java

builder
    // LinearBuilder*********************
    //分割线高度(宽度)
    .setDivideWidth(8)
    //分割线左右(上下)间距
    .setDividePadding(16)
    //************************************
    
    //GridBuilder***********************
    //水平分割线宽
    .setHorDivideWidth(20)
    //垂直分割线宽
    .setVerDivideWidth(13)
    //**********************************
    
    // 通用*************
   
    //分割线颜色， linear默认灰色， grid默认白色
    .setDivideColor(Color.GREEN)

    
    //以下为section设置，不需要可以不设置
    //是否显示section，默认false
    .setShowSection(true)
    //section颜色， 默认灰色
    .setSectionColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary))
    //section高度(宽度)
    .setSectionHeight(32)
    //section左右(上下)间距
    .setSectionPadding(0)
    //section文字颜色
    .setSectionTextColor(Color.WHITE)
    //section文字离左(上)间距， 如果值小于0，则为居中
    .setSectionTextPadding(-1)
    //section 文字size
    .setSectionTextSize(16)
    //section是否吸顶
    .setStick(true)
    //section 文字回调，用于section分类
    .setCallback(new LinearItemDecoration.SectionCallback() {
      @Override public String getSectionTitle(int pos) {
        return list.get(pos).substring(0, 1);
      }
    })
    //section 点击监听， position为当前点击的item position
    .setOnSectionClickListener(new OnSectionClickListener() {
      @Override public void onSectionClick(int position) {
        Toast.makeText(DemoActivity.this, "click section: " + position, Toast.LENGTH_SHORT).show();
      }
    });
    
//添加ItemDecoration
mRv.addItemDecoration(builder.build());
```



