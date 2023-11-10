## TextField

1. TextFieldDefaults 里的 Modifier.indicatorLine ,看下怎么实现 TextField 隐藏下划线
2. TextField 和 OutTextField 默认内间距无法去掉

## Modifier 修饰符

> 可通过定义 val myModifier=Modifier.xxx 来实现类似 Android 原生抽取style,方便复用
> 实际开发中建议:
>   1. 将Modifier抽取出去,当多个控件用同一个相同得修饰符,Compose会进行识别并优化
>   2. 对于如LazyColum,多控件嵌套等场景,Modifier应用于更高层级得容器组件,性能会更好

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

---

## PathEffect Canvas的一些形状效果

- cornerPathEffect
  将线段之间的锐角替换为指定半径的圆角
- dashPathEffect
  以给定间距绘制一系列虚线,并将其便宜到指定间距数组中
- chainPathEffect
  创建将内部效果应用于路径的PathEffect,然后将外部效果应用于内部效果的结果
- stampedPathEffect
  通过指定一些特定的形状,并将其标记来绘制路径(只能设置笔触形状)




