# SuperItemDecoration
对RecyclerView的ItemDecoration进行的通用型封装。 包括分割线和分类title。

![demo1](https://github.com/cxystyle/SuperItemDecoration/blob/master/images/demo1.gif)

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
    implementation 'com.github.cxystyle:SuperItemDecoration:1.0.0'
}
```

## LinearLayoutManager
以下为食用方法:
```java
//括号内为Horizontal的情况
LinearItemDecoration.Builder builder = new LinearItemDecoration.Builder(this)
    //分割线高度(宽度)
    .setDivideHeight(8)
    //分割线颜色
    .setDivideColor(Color.GREEN)
    //分割线左右(上下)间距
    .setDividePadding(16)
    
    
    //以下为section设置，不需要可以不设置
    //是否显示section，默认false
    .setShowSection(true)
    //section颜色
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
    });
    
//添加ItemDecoration
mRv.addItemDecoration(builder.build());
```

## GridLayoutManager
后续添加啦

