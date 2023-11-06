## TextField

1. TextFieldDefaults 里的 Modifier.indicatorLine ,看下怎么实现 TextField 隐藏下划线
2. TextField 和 OutTextField 默认内间距无法去掉

## Modifier 修饰符

> 可通过定义 val myModifier=Modifier.xxx 来实现类似 Android 原生抽取style,方便复用

### 设置控件尺寸

- fillMaxSize:
  宽高都充满布局
- fillMaxWidth:
  只充满宽
  如果传参(0.0f~1.0f),代表当前组件的宽度占父容器宽度的百分比
- fillMaxHeight:
  只充满高
  如果传参(0.0f~1.0f),代表当前组件的高度占父容器高度的百分比
- size
  控件的宽高大小
  ps: 当传入Constraints可能会覆盖该值，强制内容变小或变大。
- requiredSize
  控件的宽高大小
  ps: 无论传入约束如何设置,都固定大小
- weight
  权重
- wrapContentSize
  宽高都包裹内容
- wrapContentHeight
  只包裹高
- wrapContentWidth
  只包裹宽

### 设置控件间距

- padding
  内边距

### 添加点击事件

- clickable

### 添加形状

- shadow 





